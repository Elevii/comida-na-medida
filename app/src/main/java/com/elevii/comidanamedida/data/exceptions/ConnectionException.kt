package com.elevii.comidanamedida.data.exceptions

class ConnectionException(
    val typeError: TypeErrorConnection,
    override val message: String,
    val codeHttp: Int? = null,
    cause: Throwable? = null
) : Exception(message, cause)
