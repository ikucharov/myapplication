package com.oauth.app.activity.main

import android.annotation.SuppressLint
import android.security.keystore.UserPresenceUnavailableException
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.oauth.app.api.Api
import com.oauth.app.model.Errors
import com.oauth.app.mvp.Presenter
import com.oauth.app.util.C
import com.oauth.app.util.PreferencesUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class MainPresenter(private val gson: Gson,
                    private val preferencesUtil: PreferencesUtil,
                    private val api: Api) : Presenter<MainView>() {

    @SuppressLint("CheckResult")
    fun getCredentials() {
        getView()?.showProgress()
        api.getCredentials()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getView()?.hideProgress()
                    getView()?.setName(it.name)
                    getView()?.setRoles(it.roles?.get(0))
                }, { t ->
                    getView()?.hideProgress()
                    when (t) {
                        is IOException -> getView()?.showNetworkError()
                        is HttpException -> {
                            if (t.code() == C.HTTP_UNAUTHORIZED) {
                                refreshToken()
                            }

                            var errorMessage = mutableListOf<String>()
                            t.response().errorBody()?.let { responseBody ->
                                try {
                                    gson.fromJson(responseBody.string(), Errors::class.java)?.let {
                                        it.errors?.forEach {
                                            errorMessage.add("\n".plus(it))
                                        }
                                    }
                                } catch (e: IllegalStateException) {
                                    // no-op
                                } catch (e: JsonParseException) {
                                    // no-op
                                }
                            }
                            getView()?.showError(errorMessage.toString())
                        }
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun refreshToken() {
        api.refreshToken(preferencesUtil.refreshToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getView()?.hideProgress()
                    preferencesUtil.saveTokenData(it.access_token,
                            it.refresh_token, it.expires_in)
                }, { t ->
                    when (t) {
                        is IOException -> getView()?.showNetworkError()
                        is HttpException -> {
                            var errorMessage = mutableListOf<String>()
                            t.response().errorBody()?.let { responseBody ->
                                try {
                                    gson.fromJson(responseBody.string(), Errors::class.java)?.let {
                                        it.errors?.forEach {
                                            errorMessage.add("\n".plus(it))
                                        }
                                    }
                                } catch (e: IllegalStateException) {
                                    // no-op
                                } catch (e: JsonParseException) {
                                    // no-op
                                }
                            }
                            getView()?.showError(errorMessage.toString())
                        }
                    }
                })
    }
}