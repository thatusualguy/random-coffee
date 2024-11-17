package dev.suai.randomcoffee.domain.entity

sealed class RegisterResult {
    data object Success : RegisterResult()
    data class NetworkError(val msg: String) : RegisterResult()
    data class Error(val msg: String) : RegisterResult()
}

