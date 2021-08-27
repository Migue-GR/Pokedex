package com.pokedex.utils.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun CoroutineScope.launchWithDelay(delay: Long, block: suspend () -> Unit) = launch {
    delay(delay)
    block()
}