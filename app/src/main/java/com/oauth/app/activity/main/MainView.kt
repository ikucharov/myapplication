package com.oauth.app.activity.main

import com.oauth.app.mvp.BaseView

interface MainView : BaseView {

    fun setName(name: String?)

    fun setRoles(roles: String?)

    fun showProgress()

    fun hideProgress()

    fun showNetworkError()

    fun showError(errorMessage: String?)
}