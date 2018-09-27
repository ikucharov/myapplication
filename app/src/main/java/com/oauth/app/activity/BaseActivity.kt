package com.oauth.app.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.oauth.app.App
import com.oauth.app.util.PreferencesUtil
import javax.inject.Inject

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var preferencesUtil: PreferencesUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        val app = application as App
        super.onCreate(savedInstanceState)

        App.getAppComponent().inject(this)
    }
}
