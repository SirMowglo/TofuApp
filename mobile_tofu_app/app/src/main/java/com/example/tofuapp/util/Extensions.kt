package com.example.tofuapp.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.tofuapp.R

fun View.toggleVisibility(value: Boolean) {
    visibility =
        if (value) View.VISIBLE
        else View.INVISIBLE
}

fun ImageView.renderUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.mipmap.ic_launcher)
        .into(this)
}

fun ImageView.renderUrl(url: GlideUrl) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.mipmap.ic_launcher)
        .into(this)
}

// VALIDATION
fun String.isLongEnough() = length >= 8
fun String.hasEnoughDigits() = count(Char::isDigit) > 0
fun String.isMixedCase() = any(Char::isLowerCase) && any(Char::isUpperCase)
fun String.hasSpecialChar() = any { it in "!,+^" }
fun String.isValidEmail(): Boolean = this.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex())
