package me.marwa.androidtask.app

import android.util.Log
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class BaseException(
    private val throwable: Throwable? = null,
    private val statusCode: Int? = 0,
    private var message: String? = "",

    ) {

    private val type: ExceptionType
    var messageId: Int? = null

    fun getExceptionType() = type

    open fun getMessage() = message
    open fun getStatusCode() = statusCode


    init {
        type = getTypeFromThrowable()
        if (message == null) {
            Log.d("MESSAGE_BASEEX", "$message: ")
            message = when (type) {
                ExceptionType.SERVER_DOWN -> "SERVER_DOWN"
                ExceptionType.TIME_OUT -> "TIME_OUT"
                ExceptionType.UNAUTHORIZED -> "UNAUTHORIZED"
                ExceptionType.UNKNOWN -> "UNKNOWN"
                ExceptionType.FILE_NOT_FOUND -> "FILE_NOT_FOUND"
                ExceptionType.HTTP -> "HTTP"
                ExceptionType.NETWORK -> "NETWORK"
                ExceptionType.UN_PROCESSABLE_ENTITY -> "message $message"
                else -> message
            }
        }

    }


    private fun getTypeFromThrowable(): ExceptionType {
        return when (throwable) {
            is HttpException, null -> {
                when (statusCode) {
                    500, 502 -> ExceptionType.SERVER_DOWN
                    408 -> ExceptionType.TIME_OUT
                    401 -> ExceptionType.UNAUTHORIZED
                    404 -> ExceptionType.FILE_NOT_FOUND
                    422 -> ExceptionType.UN_PROCESSABLE_ENTITY

                    else -> ExceptionType.UNKNOWN
                }
            }
            is TimeoutException, is SocketTimeoutException -> ExceptionType.TIME_OUT
            is ConnectException -> ExceptionType.SERVER_DOWN
            is IOException, is UnknownHostException -> ExceptionType.NETWORK
            else -> ExceptionType.UNKNOWN
        }
    }

    enum class ExceptionType {
        HTTP, //Status is not 200,
        NETWORK, //Internet Error
        UNKNOWN,
        SERVER_DOWN, //Server didn't respond properly
        TIME_OUT,
        UNAUTHORIZED,
        FILE_NOT_FOUND,
        UN_PROCESSABLE_ENTITY
    }


}