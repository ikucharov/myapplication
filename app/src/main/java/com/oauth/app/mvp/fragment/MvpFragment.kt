package com.oauth.app.mvp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.oauth.app.mvp.BaseView
import com.oauth.app.mvp.Presenter

/**
 * Base mvp fragment
 */
abstract class MvpFragment<V : BaseView, P : Presenter<V>> : Fragment() {

    protected lateinit var presenter: P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = createPresenter()
        presenter.attachView(getMvpView())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    abstract fun createPresenter(): P

    abstract fun getMvpView(): V
}
