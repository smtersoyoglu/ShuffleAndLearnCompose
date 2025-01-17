package com.smtersoyoglu.shuffleandlearncompose.data.network

import android.util.Log
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeCall(action: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(action())
    } catch (e: IOException) {
        Log.e("SafeCall", "Network error: ${e.message}")
        Resource.Error("Network error: ${e.message}")
    } catch (e: HttpException) {
        Log.e("SafeCall", "API error: ${e.message}")
        Resource.Error("API error: ${e.message}")
    } catch (e: Exception) {
        Log.e("SafeCall", "Unexpected error: ${e.message}")
        Resource.Error("Unexpected error: ${e.message}")
    }
}
