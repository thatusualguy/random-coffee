package dev.suai.randomcoffee.data.auth

import dev.suai.randomcoffee.domain.AuthRepository
import dev.suai.randomcoffee.domain.entity.AuthResult
import dev.suai.randomcoffee.domain.entity.RegisterResult
import javax.inject.Inject

class AuthMockRepository @Inject constructor(): AuthRepository {
    override suspend fun authenticate(login: String, password: String): AuthResult {
        return AuthResult.Success
    }

    override suspend fun register(
        login: String,
        password: String,
        tgHandle: String,
        name: String
    ): RegisterResult {
        return RegisterResult.Success
    }
}