package com.valentinerutto.weather.utils


data class Resource<out T>(val status: ResourceStatus, val data: T?, val errorType: ErrorType) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(ResourceStatus.SUCCESS, data, ErrorType.NONE)
        }

        fun <T> error(errorType: ErrorType, data: T?): Resource<T> {
            return Resource(ResourceStatus.ERROR, data, errorType)
        }
    }
}
enum class ResourceStatus {
    SUCCESS,
    ERROR
}
enum class ErrorType {
    CLIENT,
    NETWORK,
    NONE
}