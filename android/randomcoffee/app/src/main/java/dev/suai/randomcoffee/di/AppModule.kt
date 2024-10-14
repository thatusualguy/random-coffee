package dev.suai.randomcoffee.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.data.AuthApiRepository
import dev.suai.randomcoffee.domain.AuthRepository
import dev.suai.randomcoffee.schema.api.AuthApi
import org.openapitools.client.infrastructure.ApiClient
import javax.inject.Singleton


@Suppress("unused")
@Module(includes = [ApiModule::class, CircuitModule::class])
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthApiRepository): AuthRepository


}