package dev.suai.randomcoffee.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.auth0.android.jwt.JWT
import dev.suai.randomcoffee.domain.JwtTokenManager
import kotlinx.coroutines.flow.firstOrNull
import java.util.Date
import javax.inject.Inject

val ACCESS_JWT_KEY_NAME = "access_jwt"
val REFRESH_JWT_KEY_NAME = "refresh_jwt"


class JwtTokenDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) :
    JwtTokenManager {


    companion object {
        val ACCESS_JWT_KEY = stringPreferencesKey(ACCESS_JWT_KEY_NAME)
        val REFRESH_JWT_KEY = stringPreferencesKey(REFRESH_JWT_KEY_NAME)
    }

    override suspend fun saveAccessJwt(token: JWT) {
        save(ACCESS_JWT_KEY, token)
    }

    override suspend fun saveRefreshJwt(token: JWT) {
        save(REFRESH_JWT_KEY, token)
    }


    override suspend fun getAccessJwt(): JWT? {
        return get(ACCESS_JWT_KEY)
    }

    override suspend fun getRefreshJwt(): JWT? {
        return get(REFRESH_JWT_KEY)
    }

    override suspend fun isAccessValid(): Boolean {
        val token = getAccessJwt()
        return token!=null && isTokenValid(token)
    }

    override suspend fun isRefreshValid(): Boolean {
        val token = getRefreshJwt()
        return token!=null && isTokenValid(token)
    }

    private fun isTokenValid(token: JWT): Boolean{
        val expiresAt = token.expiresAt ?: return false
        val currentTime = Date()
        return expiresAt <= currentTime
    }


    override suspend fun clearAllTokens() {
        dataStore.updateData {
            it.toMutablePreferences().apply {
                remove(ACCESS_JWT_KEY)
                remove(REFRESH_JWT_KEY)
            }
        }
    }

    private suspend fun get(key: Preferences.Key<String>): JWT? {
        val saved = dataStore.data.firstOrNull()?.get(key)
        return if (!saved.isNullOrBlank())
            JWT(saved)
        else
            null
    }

    private suspend fun save(key: Preferences.Key<String>, token: JWT) {
        dataStore.updateData {
            it.toMutablePreferences().apply {
                putAll(key to token.toString())
            }
        }
    }
}
