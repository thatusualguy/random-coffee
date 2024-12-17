package dev.suai.randomcoffee.domain

import dev.suai.randomcoffee.domain.entity.AuthResult
import dev.suai.randomcoffee.domain.entity.RegisterResult


interface AuthRepository {
    suspend fun authenticate(login: String, password: String): AuthResult
    suspend fun register(
        login: String, password: String,
        tgHandle: String, name: String
    ): RegisterResult
}