package dev.suai.randomcoffee.data.auth

import com.auth0.android.jwt.JWT
import dev.suai.randomcoffee.domain.AuthRepository
import dev.suai.randomcoffee.domain.JwtTokenManager
import dev.suai.randomcoffee.domain.entity.AuthResult
import dev.suai.randomcoffee.domain.entity.RegisterResult
import dev.suai.randomcoffee.schema.api.AuthApi
import dev.suai.randomcoffee.schema.model.LoginPostRequest
import dev.suai.randomcoffee.schema.model.UserRegister
import java.util.logging.Logger
import javax.inject.Inject


class AuthApiRepository @Inject constructor(
    private val api: AuthApi,
    private val jwtTokenManager: JwtTokenManager
) : AuthRepository {
    private val logger = Logger.getLogger(AuthApiRepository::class.java.name)

    override suspend fun authenticate(login: String, password: String): AuthResult {
        val loginDetails = LoginPostRequest(login, password)
        logger.info("Authenticating user: $login")

        try {
            val response = api.loginPost(loginDetails)
            if (response.isSuccessful && response.body() != null) {
                jwtTokenManager.saveAccessJwt(JWT(response.body()!!.accessToken!!))
                jwtTokenManager.saveRefreshJwt(JWT(response.body()!!.refreshToken!!))
                return AuthResult.Success
            } else {
                return AuthResult.Error("Auth failed:")
            }
        } catch (e: Exception) {
            logger.info("Auth: network error: ${e.message}")
            return AuthResult.NetworkError(e.message ?: " ")
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
                jwtTokenManager.saveAccessJwt(JWT(response.body()!!.accessToken!!))
                jwtTokenManager.saveRefreshJwt(JWT(response.body()!!.refreshToken!!))
                RegisterResult.Success
            } else {
                // TODO: Handle error appropriately
                RegisterResult.Error("Registration failed")
            }
        } catch (e: Exception) {
            logger.info("Register: network error: ${e.message}")
            // TODO: Handle network issues or unsuccessful responses
            RegisterResult.NetworkError(e.message ?: " ")
        }
    }
}