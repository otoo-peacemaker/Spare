package com.peacemaker.android.spare.ui.util

import android.app.Activity
import android.graphics.PorterDuff
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.ActionBar
import androidx.core.content.res.ResourcesCompat
import com.peacemaker.android.spare.R

fun TextView.changeTextColor(text: String, color: Int) {
    val startIndex = this.text.indexOf(text)
    val endIndex = startIndex + text.length
    val spannableString = SpannableString(this.text)
    spannableString.setSpan(ForegroundColorSpan(color), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = spannableString
}

fun Activity.changeBackNavigationIcon(actionBar: ActionBar?, @DrawableRes iconResId: Int?= R.drawable.navigate_up) {
    actionBar?.apply {
        setDisplayHomeAsUpEnabled(true)
        setHomeAsUpIndicator(
            ResourcesCompat.getDrawable(
                resources,
                iconResId!!, // replace with your own back arrow icon
                null
            )
        )
    }
    invalidateOptionsMenu()
}




