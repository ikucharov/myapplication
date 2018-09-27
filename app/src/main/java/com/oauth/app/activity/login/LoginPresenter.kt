package com.oauth.app.activity.login

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.oauth.app.api.Api
import com.oauth.app.model.Errors
import com.oauth.app.mvp.Presenter
import com.oauth.app.util.PreferencesUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class LoginPresenter(private val gson: Gson,
                     private val preferencesUtil: PreferencesUtil,
                     private val api: Api) : Presenter<LoginView>() {

    @SuppressLint("CheckResult")
    fun login(email: String, password: String) {
        getView()?.showProgress()
        api.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getView()?.hideProgress()
                    preferencesUtil.saveTokenData(it.access_token,
                            it.refresh_token, it.expires_in)
                    getView()?.openMainScreen()
                }, { t ->
                    getView()?.hideProgress()
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