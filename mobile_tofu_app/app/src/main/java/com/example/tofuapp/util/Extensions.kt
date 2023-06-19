package com.example.tofuapp.util

import android.view.View

fun View.toggleVisibility(value: Boolean){
    visibility =
        if(value) View.VISIBLE
        else View.INVISIBLE
}