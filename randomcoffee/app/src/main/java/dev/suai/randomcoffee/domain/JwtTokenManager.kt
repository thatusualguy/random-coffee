package dev.suai.randomcoffee.domain

import com.auth0.android.jwt.JWT

interface JwtTokenManager {
    suspend fun saveAccessJwt(token: JWT)
    suspend fun saveRefreshJwt(token: JWT)
    suspend fun getAccessJwt(): JWT?
    suspend fun getRefreshJwt(): JWT?

    suspend fun isAccessValid(): Boolean
    suspend fun isRefreshValid(): Boolean

    suspend fun clearAllTokens()
}
