package com.pokedex.utils.extensions

import android.view.View
import com.pokedex.utils.OnSingleClickListener

fun View.setOnSingleClickListener(clickListener: View.OnClickListener?) {
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}