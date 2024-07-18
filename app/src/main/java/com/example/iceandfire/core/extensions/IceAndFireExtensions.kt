package com.example.iceandfire.core.extensions

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.example.iceandfire.R

private const val KEY_ARGUMENTS_DEFAULT = "DATA_KEY"

fun Intent.putArguments(data: Parcelable): Intent {
    putExtra(KEY_ARGUMENTS_DEFAULT, data)
    return this
}

fun Intent.getArguments(): Parcelable? {
    return getParcelableExtra(KEY_ARGUMENTS_DEFAULT)
}

fun Context.showDialogError(errorMessage: String, onButtonClickListener: () -> Unit) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(getString(R.string.message_generic_error, errorMessage))
    builder.setPositiveButton(getString(R.string.text_try_again)) { dialog, _ ->
        onButtonClickListener.invoke()
        dialog.dismiss()
    }

    val dialog = builder.create()
    dialog.setCancelable(false)
    dialog.show()
}