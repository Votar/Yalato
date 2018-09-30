package io.bertalt.yalato.utils

import android.view.View
import android.widget.Toast
import io.bertalt.yalato.R

fun View.showMessage(message: String?) {
    val text: String
    if (message.isNullOrEmpty()) {
        text = this.context.getString(R.string.common_error)
    } else
        text = message!!

    Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()

}

