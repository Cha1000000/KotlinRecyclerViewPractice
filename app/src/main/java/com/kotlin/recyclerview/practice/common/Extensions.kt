package com.kotlin.recyclerview.practice.common

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.showAlertDialog(
    @StringRes
    titleRes: Int? = null,
    title: String? = null,
    @StringRes
    msgRes: Int? = null,
    msg: String? = null,
    @StringRes
    okRes: Int = android.R.string.ok,
    @StringRes
    cancelRes: Int? = android.R.string.cancel,
    okCallback: (() -> Unit)? = null,
) {
    MaterialAlertDialogBuilder(this)
        .setPositiveButton(okRes) { dialog, _ ->
            okCallback?.invoke()
            dialog.dismiss()
        }.apply {
            titleRes?.let { setTitle(it) }
            title?.let { setTitle(it) }
            msgRes?.let { setMessage(it) }
            msg?.let { setMessage(it) }
            cancelRes?.let { setNegativeButton(it) { dialog, _ -> dialog.dismiss() } }
            setOnKeyListener { dialog, keyCode, _ ->
                onExitConfirmKeyPressed(
                    okCallback,
                    dialog,
                    keyCode
                )
            }
            show()
        }
}

private fun onExitConfirmKeyPressed(
    okCallback: (() -> Unit)?,
    dialog: DialogInterface,
    keyCode: Int,
): Boolean {
    if (keyCode == FIRE_BUTTON_KEY_CODE) {
        okCallback?.invoke()
        dialog.dismiss()
    }
    return true
}