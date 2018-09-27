package com.oauth.app.activity.login

import com.oauth.app.mvp.BaseView

interface LoginView : BaseView {

    fun showProgress()

    fun hideProgress()

    fun openMainScreen()

    fun showNetworkError()

    fun showError(errorMessage: String?)
}