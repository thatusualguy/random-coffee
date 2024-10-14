package dev.suai.randomcoffee.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun provideAccountApi(api: ApiClient): AuthApi {
        return api.createService(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(api: ApiClient): UserApi {
        return api.createService(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideInterestsApi(api: ApiClient): InterestsApi {
        return api.createService(InterestsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApi(): ApiClient {
        System.setProperty(ApiClient.defaultBasePath, "example.com")
        return ApiClient()
    }
}