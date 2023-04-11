package com.peacemaker.android.spare.ui.util

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView

fun TextView.changeTextColor(text: String, color: Int) {
    val startIndex = this.text.indexOf(text)
    val endIndex = startIndex + text.length
    val spannableString = SpannableString(this.text)
    spannableString.setSpan(ForegroundColorSpan(color), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = spannableString
}
