package com.oauth.app.dagger

import com.oauth.app.activity.login.LoginActivity
import com.oauth.app.activity.main.MainActivity
import dagger.Subcomponent

@PresenterScope
@Subcomponent(modules = [
    PresenterModule::class
])
interface PresenterComponent {

    fun inject(loginActivity: LoginActivity)

    fun inject(mainActivity: MainActivity)
}
