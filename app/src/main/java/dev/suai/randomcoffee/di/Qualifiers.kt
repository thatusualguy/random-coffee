package dev.suai.randomcoffee.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthenticatedClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TokenRefreshClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PublicClient


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthDataStore

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingsDataStore
