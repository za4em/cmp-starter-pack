package com.cmp.starter_pack.common.exception

open class ServerException(
    message: String? = null,
    cause: Throwable? = null,
    val errorCode: String? = null,
    val httpCode: Int? = null,
) : AppException(message, cause)
