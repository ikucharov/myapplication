package com.oauth.app.dagger

import com.google.gson.Gson
import com.oauth.app.activity.login.LoginPresenter
import com.oauth.app.activity.main.MainPresenter
import com.oauth.app.api.Api
import com.oauth.app.util.PreferencesUtil
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun providesLoginPresenter(gson: Gson, preferencesUtil: PreferencesUtil,
                                    api: Api): LoginPresenter =
            LoginPresenter(gson, preferencesUtil, api)

    @Provides
    fun providesMainPresenter(gson: Gson, preferencesUtil: PreferencesUtil,
                                    api: Api): MainPresenter =
            MainPresenter(gson, preferencesUtil, api)

}
