package com.valentinerutto.weather.ui

import androidx.annotation.StringRes
import com.valentinerutto.weather.R
import com.valentinerutto.weather.core.ErrorType

@StringRes
fun ErrorType.toResourceId(): Int = when (this) {
    ErrorType.SERVER -> R.string.error_server
    ErrorType.GENERIC -> R.string.error_generic
    ErrorType.IO_CONNECTION -> R.string.error_connection
    ErrorType.UNAUTHORIZED -> R.string.error_unauthorized
    ErrorType.CLIENT -> R.string.error_client
}
