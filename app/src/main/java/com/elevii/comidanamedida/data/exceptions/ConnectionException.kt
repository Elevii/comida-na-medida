package com.elevii.comidanamedida.data.exceptions

class ConnectionException (
    val typeError: TipoErroConexao,
    override val message: String,
    val codeHttp: Int? = null,
    cause: Throwable? = null
) : Exception(message, cause)