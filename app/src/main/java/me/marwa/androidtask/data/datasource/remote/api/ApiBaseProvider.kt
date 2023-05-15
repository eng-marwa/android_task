package me.marwa.androidtask.data.datasource.remote.api

import android.util.Log
import me.marwa.androidtask.app.BaseException
import me.marwa.androidtask.utils.toApiErrorBody
import retrofit2.Response

abstract class BaseApiProvider {
    private val TAG = "BaseApiProvider"
    suspend fun <T : Any> apiRequest(
        call: suspend () -> Response<T>, apiResponseCallbacks: ApiResponseCallbacks<T>
    ) {
        Log.d(TAG, "BaseApiProvider")
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d(TAG, "apiRequest: ")
                    apiResponseCallbacks.onResult(body)

                } else {
                    Log.d(TAG, "apiRequest: ${response.code()}")
                    Log.d(TAG, "apiRequest: ${response.errorBody().toString()}")

                }
            } else {
                val apiErrorBody = response.errorBody()?.toApiErrorBody()
                if (apiErrorBody != null) {
                    if (apiErrorBody != null) {
                        apiResponseCallbacks.onError(
                            BaseException(
                                statusCode = response.code(),
                                message = apiErrorBody.getMessage(),
                            )
                        )
                    } else {
                        apiResponseCallbacks.onError(
                            BaseException(
                                statusCode = response.code(),
                                message = apiErrorBody.getMessage()
                            )
                        )
                    }
                }


            }


        } catch (e: Throwable) {
            e.printStackTrace()
            return apiResponseCallbacks.onError(BaseException(e))
        }
    }

}