package com.elevii.comidanamedida.data.exceptions

object ErrorHandler {

    fun handleError(error: Throwable): ConnectionException {
        return when (error) {
            is java.net.SocketTimeoutException -> {
                ConnectionException(TipoErroConexao.TIMEOUT, "Tempo de conexão esgotado.", cause = error)
            }
            is java.net.UnknownHostException -> {
                ConnectionException(TipoErroConexao.DNS, "Servidor não encontrado.", cause = error)
            }
            is java.io.IOException -> {
                ConnectionException(TipoErroConexao.SEM_INTERNET, "Sem conexão com a internet.", cause = error)
            }
            is retrofit2.HttpException -> {
                val code = error.code()
                val message = try {
                    error.response()?.errorBody()?.string() ?: "Erro HTTP $code"
                } catch (e: Exception) {
                    "Erro HTTP $code"
                }
                ConnectionException(TipoErroConexao.HTTP, message, codeHttp = code, cause = error)
            }
            else -> {
                ConnectionException(TipoErroConexao.DESCONHECIDO, "Erro inesperado: ${error.message}", cause = error)
            }
        }
    }

    fun customMessageError(e: ConnectionException): String {
        return when (e.typeError) {
            TipoErroConexao.TIMEOUT -> "Tempo de conexão esgotado. Tente novamente."
            TipoErroConexao.DNS -> "Servidor não encontrado. Verifique sua conexão."
            TipoErroConexao.SEM_INTERNET -> "Você está offline. Verifique sua internet."
            TipoErroConexao.HTTP -> "Erro do servidor (${e.codeHttp}): ${e.message}"
            TipoErroConexao.DESCONHECIDO -> "Erro inesperado: ${e.message}"
        }
    }
}
