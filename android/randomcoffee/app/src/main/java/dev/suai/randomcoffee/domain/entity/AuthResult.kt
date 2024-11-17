package dev.suai.randomcoffee.domain.entity

sealed class AuthResult {
    data object Success : AuthResult()
    data class NetworkError(val msg: String) : AuthResult()
    data class Error(val msg: String) : AuthResult()

    data object WrongPassword : AuthResult()
    data object WrongLogin : AuthResult()
}