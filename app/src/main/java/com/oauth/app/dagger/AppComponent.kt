package com.oauth.app.dagger

import com.oauth.app.App
import com.oauth.app.activity.BaseActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class
])
interface AppComponent {

    fun plus(presenterModule: PresenterModule): PresenterComponent

    fun inject(app: App)

    fun inject(baseActivity: BaseActivity)

}
