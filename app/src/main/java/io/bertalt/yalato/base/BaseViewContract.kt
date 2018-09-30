package io.bertalt.yalato.base

import android.support.annotation.StringRes
import android.view.View


interface BaseViewContract {

    fun getRootView(): View

    fun showError(error: String?)

    fun showError(@StringRes stringResId: Int)

    fun showMessage(@StringRes srtResId: Int)

    fun showMessage(message: String)
}

