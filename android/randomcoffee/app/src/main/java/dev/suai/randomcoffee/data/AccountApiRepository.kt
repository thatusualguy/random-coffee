package dev.suai.randomcoffee.data

import dev.suai.randomcoffee.domain.AuthRepository
import dev.suai.randomcoffee.domain.entity.AuthResult
import dev.suai.randomcoffee.domain.entity.RegisterResult
import dev.suai.randomcoffee.schema.api.AuthApi
import dev.suai.randomcoffee.schema.model.LoginDetails
import dev.suai.randomcoffee.schema.model.UserRegister
import java.util.logging.Logger
import javax.inject.Inject


class AuthApiRepository @Inject constructor(private val api: AuthApi) : AuthRepository {
    private val logger = Logger.getLogger(AuthApiRepository::class.java.name)

    override suspend fun authenticate(login: String, password: String): AuthResult {
        val loginDetails = LoginDetails(login, password)
        logger.info("Authenticating user: $login")

        return try {
            val response = api.loginPost(loginDetails)
            if (response.isSuccessful && response.body() != null) {
                AuthResult.Success(response.body()!!)
            } else {
                // TODO: Handle error appropriately
                AuthResult.Error("Auth failed")
            }
        } catch (e: Exception) {
            // TODO: Handle network issues or unsuccessful responses
            AuthResult.NetworkError(e.message ?: " ")
        }
    }

    override suspend fun register(
        login: String,
        password: String,
        tgHandle: String,
        name: String
    ): RegisterResult {
        val userRegister = UserRegister(login, password, tgHandle, name)
        logger.info("Registering user: $login, $name")

        return try {
            val response = api.registerPost(userRegister)
            if (response.isSuccessful && response.body() != null) {
                RegisterResult.Success(response.body()!!)
            } else {
                // TODO: Handle error appropriately
                RegisterResult.Error("Registration failed")
            }
        } catch (e: Exception) {
            // TODO: Handle network issues or unsuccessful responses
            RegisterResult.NetworkError(e.message ?: " ")
        }
    }
}