package com.elevii.comidanamedida.data.exceptions

import android.util.Log

object ErrorHandler {

    fun handleError(error: Throwable): ConnectionException {
        return when (error) {
            is java.net.SocketTimeoutException -> {
                ConnectionException(TypeErrorConnection.TIMEOUT, "Tempo de conexão esgotado.", cause = error)
            }
            is java.net.UnknownHostException -> {
                ConnectionException(TypeErrorConnection.DNS, "Servidor não encontrado.", cause = error)
            }
            is java.io.IOException -> {
                ConnectionException(TypeErrorConnection.SEM_INTERNET, "Sem conexão com a internet.", cause = error)
            }
            is retrofit2.HttpException -> {
                val code = error.code()
                val message = try {
                    error.response()?.errorBody()?.string() ?: "Erro HTTP $code"
                } catch (e: Exception) {
                    Log.e("ErrorHandler", "Erro ao tentar ler corpo do erro HTTP", e)
                    "Erro HTTP $code: falha ao obter o corpo do erro (${e.message})"
                }
                ConnectionException(TypeErrorConnection.HTTP, message, codeHttp = code, cause = error)
            }
            else -> {
                ConnectionException(
                    TypeErrorConnection.DESCONHECIDO,
                    "Erro inesperado: ${error.message}",
                    cause = error
                )
            }
        }
    }

    fun customMessageError(e: ConnectionException): String {
        return when (e.typeError) {
            TypeErrorConnection.TIMEOUT -> "Tempo de conexão esgotado. Tente novamente."
            TypeErrorConnection.DNS -> "Servidor não encontrado. Verifique sua conexão."
            TypeErrorConnection.SEM_INTERNET -> "Você está offline. Verifique sua internet."
            TypeErrorConnection.HTTP -> "Erro do servidor (${e.codeHttp}): ${e.message}"
            TypeErrorConnection.DESCONHECIDO -> "Erro inesperado: ${e.message}"
        }
    }
}
