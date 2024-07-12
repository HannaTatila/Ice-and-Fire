package com.example.iceandfire.core.extensions

import android.accounts.NetworkErrorException
import android.content.Intent
import android.os.Parcelable
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import java.io.IOException

private const val KEY_ARGUMENTS_DEFAULT = "DATA_KEY"

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        val response = apiCall()
        Result.success(response)
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> Result.failure(throwable)
            is HttpException -> Result.failure(throwable)
            is NetworkErrorException -> Result.failure(throwable)
            else -> Result.failure(Exception("Unknown error occurred"))
        }
    }
}

fun Intent.putArguments(data: Parcelable): Intent {
    putExtra(KEY_ARGUMENTS_DEFAULT, data)
    return this
}

fun Intent.getArguments(): Parcelable? {
    return getParcelableExtra(KEY_ARGUMENTS_DEFAULT)
}