package dev.suai.randomcoffee.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.data.auth.JwtTokenDataStore
import dev.suai.randomcoffee.domain.JwtTokenManager
import dev.suai.randomcoffee.schema.api.AuthApi
import dev.suai.randomcoffee.schema.api.InterestsApi
import dev.suai.randomcoffee.schema.api.UserApi
import org.openapitools.client.infrastructure.ApiClient
import javax.inject.Singleton





@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideAuthApi(@PublicClient api: ApiClient): AuthApi {
        return api.createService(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(@AuthenticatedClient api: ApiClient): UserApi {
        return api.createService(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideInterestsApi(@AuthenticatedClient api: ApiClient): InterestsApi {
        return api.createService(InterestsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideJwtTokenManager(@AuthDataStore dataStore: DataStore<Preferences>): JwtTokenManager {
        return JwtTokenDataStore(dataStore)
    }

    @Provides
    @Singleton
    @AuthenticatedClient
    fun provideApi(): ApiClient {
        val api = ApiClient(baseUrl = "https://ce5bd7b8-733f-49a4-ad6e-7ae79491dd22.mock.pstmn.io")
        return api
    }

    @Provides
    @Singleton
    @PublicClient
    fun provideNoAuthApi(): ApiClient {
        val api = ApiClient(baseUrl = "https://ce5bd7b8-733f-49a4-ad6e-7ae79491dd22.mock.pstmn.io")
        return api
    }

}