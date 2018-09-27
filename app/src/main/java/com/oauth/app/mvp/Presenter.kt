package com.oauth.app.mvp

import java.lang.ref.WeakReference


/**
 * Base presenter
 */
open class Presenter<V : BaseView> {
    var viewRef: WeakReference<V>? = null

    fun attachView(view: V) {
        viewRef = WeakReference(view)
    }

    fun getView(): V? {
        return viewRef?.get()
    }

    fun detachView() {
        viewRef?.clear()
        viewRef = null
    }


    open fun onDestroy() {
    }
}
