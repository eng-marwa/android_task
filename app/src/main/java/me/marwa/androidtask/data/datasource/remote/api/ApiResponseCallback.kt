package me.marwa.androidtask.data.model

import me.marwa.androidtask.app.BaseException

data class ApiResponseCallbacks<T>(
    val onResult: (response: T?) -> Unit,
    val onError: (error: BaseException) -> Unit,
)