package me.marwa.androidtask.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.marwa.androidtask.app.BaseException
import okhttp3.ResponseBody

inline fun <reified T : Any> T?.json() = this?.let { Gson().toJson(this, T::class.java) }

inline fun <reified T : Any> String?.fromJson(): T? = this?.let {
    val type = object : TypeToken<T>() {}.type
    Gson().fromJson(this, type)
}

fun ResponseBody.toApiErrorBody(): BaseException? {
    val gson = Gson()
    return gson.fromJson(string(), BaseException::class.java)
}