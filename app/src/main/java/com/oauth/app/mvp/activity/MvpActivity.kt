package com.oauth.app.mvp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.oauth.app.mvp.Presenter
import com.oauth.app.mvp.BaseView

/**
 * Base mvp activity
 */
abstract class MvpActivity<V : BaseView, P : Presenter<V>> : AppCompatActivity() {

    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        presenter.attachView(getMvpView())
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    abstract fun createPresenter(): P

    abstract fun getMvpView(): V
}
