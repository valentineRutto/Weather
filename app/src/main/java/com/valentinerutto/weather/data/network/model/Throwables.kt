package com.valentinerutto.weather.data.network.model


data class ClientException(override val message: String) : Throwable(message = message)

data class ServerException(override val message: String) : Throwable(message = message)

data class UnauthorizedException(override val message: String) : Throwable(message = message)

data class GenericException(override val message: String) : Throwable(message = message)
