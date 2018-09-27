package com.oauth.app.dagger

import com.oauth.app.BuildConfig
import com.oauth.app.api.Api
import com.oauth.app.util.C
import com.oauth.app.util.PreferencesUtil
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private val AUTH_HEADER_EXCEPTED_LIST = arrayOf(
                "oauth/p/token"
        )
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(preferencesUtil: PreferencesUtil): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        val authInterceptor = Interceptor { chain ->
            val original = chain.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()

            val url = original.url().toString()
            if (AUTH_HEADER_EXCEPTED_LIST.none { url.contains(it) }) {
                if (preferencesUtil.hasAuthToken()) {
                    requestBuilder.header("Authorization", "Bearer ".plus(preferencesUtil.authToken))
                }
            }

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(C.BASE_API)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providesOauthApi(retrofit: Retrofit): Api =
            retrofit.create(Api::class.java)


}

