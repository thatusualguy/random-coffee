package dev.suai.randomcoffee.domain.entity

sealed class RegisterResult {
    data class Success(val jwt: String) : RegisterResult()
    data class NetworkError(val msg: String) : RegisterResult()
    data class Error(val msg: String) : RegisterResult()
}

sealed class AuthResult {
    data class Success(val jwt: String) : AuthResult()
    data class NetworkError(val msg: String) : AuthResult()
    data class Error(val msg: String) : AuthResult()

    data object WrongPassword : AuthResult()
    data object WrongLogin : AuthResult()
}