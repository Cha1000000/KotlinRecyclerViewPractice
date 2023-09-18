package com.kotlin.recyclerview.practice.common

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
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

fun CardView.setBackgroundColorRes(@ColorRes colorRes: Int) {
    setCardBackgroundColor(context.getColor(colorRes))
}


/**
 * Extension method to show a keyboard for View.
 */
fun View.showKeyboardImplicit() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (_: RuntimeException) { }
    return false
}

fun View.setVisible(visible: Boolean, invisibilityType: Int = View.GONE) {
    visibility = if (visible) View.VISIBLE else invisibilityType
}
