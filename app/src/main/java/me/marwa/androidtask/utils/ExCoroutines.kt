package me.marwa.androidtask.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ui(invoke: suspend () -> Unit) = CoroutineScope(Dispatchers.Main).launch {
    invoke.invoke()
}