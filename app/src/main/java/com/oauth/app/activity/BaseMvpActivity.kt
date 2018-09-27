package com.oauth.app.activity

import android.app.AlertDialog
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.oauth.app.App
import com.oauth.app.R
import com.oauth.app.mvp.BaseView
import com.oauth.app.mvp.Presenter
import com.oauth.app.mvp.activity.MvpActivity

/**
 * Created by Bakhrom Sarimsakov
 * 10:54  07.11.2017
 */
abstract class BaseMvpActivity<V : BaseView, P : Presenter<V>> : MvpActivity<V, P>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val app = application as App
        dependencyInject()
        super.onCreate(savedInstanceState)
    }

    fun setHomeAsUp() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!super.onSupportNavigateUp()) {
            supportFinishAfterTransition()
        }
        return true
    }

    fun showNoNetworkAlert() {
        showAlert(R.string.no_internet)
    }

    fun showAlert(message: String) {
        showAlert(null, message)
    }

    fun showAlert(@StringRes messageRes: Int) {
        showAlert(null, getString(messageRes))
    }

    fun showAlert(title: String?, message: String?) {
        if (isFinishing.not()) {
            AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("ok", null)
                    .show()
        }
    }


    fun showToast(toastMessage: String) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
    }

    fun hideKeyboard() {
        // Check if no view has focus:
        this.currentFocus?.let {
            it.clearFocus()
            val imm: InputMethodManager? = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun getApp(): App {
        return application as App
    }

    abstract fun dependencyInject()
}
