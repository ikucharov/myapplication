package com.oauth.app.dagger

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.oauth.app.BuildConfig
import com.oauth.app.util.PreferencesUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var application: Application) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun providesApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun providesPreferences(context: Context,
                            gson: Gson): PreferencesUtil {

        val sharedPreferences = context.getSharedPreferences("OauthPrefs", Context.MODE_PRIVATE)
        val securePreferencesName = BuildConfig.APPLICATION_ID + "_preferences"
        return PreferencesUtil(context, sharedPreferences, securePreferencesName)
    }
}
