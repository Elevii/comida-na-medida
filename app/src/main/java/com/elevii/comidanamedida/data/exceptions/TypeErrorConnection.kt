package com.elevii.comidanamedida.data.exceptions

enum class TipoErroConexao {
    TIMEOUT,
    DNS,
    SEM_INTERNET,
    HTTP,
    DESCONHECIDO
}