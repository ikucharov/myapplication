package com.oauth.app.activity.main

import android.os.Bundle
import android.view.View
import com.oauth.app.App
import com.oauth.app.R
import com.oauth.app.activity.BaseMvpActivity
import dagger.Lazy
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseMvpActivity<MainView, MainPresenter>(), MainView {

    @Inject
    lateinit var presenterProvider: Lazy<MainPresenter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.app_name)

        buttonRefresh.setOnClickListener {
            presenter.getCredentials()
        }
    }

    override fun setName(name: String?) {
        nameView.text = name
    }

    override fun setRoles(roles: String?) {
        rolesView.text = roles
    }

    override fun showProgress() {
        buttonRefresh.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        buttonRefresh.visibility = View.VISIBLE
    }

    override fun showNetworkError() {
        showNoNetworkAlert()
    }

    override fun showError(errorMessage: String?) {
        showAlert(null, errorMessage)
    }

    override fun createPresenter(): MainPresenter = presenterProvider.get()

    override fun getMvpView(): MainView = this

    override fun dependencyInject() {
        App.getPresenterComponent().inject(this)
    }
}