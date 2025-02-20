package com.smtersoyoglu.shuffleandlearncompose.data.network

import android.util.Log
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import retrofit2.HttpException

inline fun <T> safeFlow(crossinline action: () -> Flow<T>): Flow<Resource<T>> {
    return action()
        .map { Resource.Success(it) as Resource<T> }
        .catch { e ->
            val errorMessage = when (e) {
                is IOException -> "Network error: ${e.message}"
                is HttpException -> "API error: ${e.message}"
                else -> "Unexpected error: ${e.message}"
            }
            Log.e("SafeFlow", errorMessage)
            emit(Resource.Error(errorMessage))
        }
}