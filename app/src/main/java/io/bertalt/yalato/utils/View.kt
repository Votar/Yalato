package io.bertalt.yalato.utils

import android.view.View

fun View.beVisible() {
    visibility = View.VISIBLE
}

fun View.beGone() {
    visibility = View.GONE
}

fun View.beVisibleIf(visible: Boolean) {
    if (visible) beVisible() else beGone()
}