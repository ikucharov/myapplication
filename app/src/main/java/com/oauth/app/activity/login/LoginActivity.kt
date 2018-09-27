package com.oauth.app.activity.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.oauth.app.App
import com.oauth.app.R
import com.oauth.app.activity.BaseMvpActivity
import com.oauth.app.activity.main.MainActivity
import dagger.Lazy
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseMvpActivity<LoginView, LoginPresenter>(), LoginView {

    @Inject
    lateinit var presenterProvider: Lazy<LoginPresenter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener {
            hideKeyboard()
            presenter.login(inputEmail.text.toString(),
                    inputPassword.text.toString())
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            hideKeyboard()
        }
        return super.dispatchTouchEvent(event)
    }

    override fun showProgress() {
        buttonLogin.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        buttonLogin.visibility = View.VISIBLE
    }

    override fun openMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showNetworkError() {
        showNoNetworkAlert()
    }

    override fun showError(errorMessage: String?) {
        showAlert(null, errorMessage)
    }

    override fun createPresenter(): LoginPresenter = presenterProvider.get()

    override fun getMvpView(): LoginView = this

    override fun dependencyInject() {
        App.getPresenterComponent().inject(this)
    }
}