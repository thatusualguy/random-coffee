package dev.suai.randomcoffee.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.data.AuthApiRepository
import dev.suai.randomcoffee.domain.AuthRepository
import dev.suai.randomcoffee.schema.api.AuthApi
import dev.suai.randomcoffee.schema.api.InterestsApi
import dev.suai.randomcoffee.schema.api.UserApi
import org.openapitools.client.infrastructure.ApiClient
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: ApiClient): ApiClient {
        System.setProperty(ApiClient.defaultBasePath, "example.com")
        return ApiClient()
    }
}