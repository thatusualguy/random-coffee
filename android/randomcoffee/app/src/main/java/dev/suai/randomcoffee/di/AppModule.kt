package dev.suai.randomcoffee.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.data.auth.AuthApiRepository
import dev.suai.randomcoffee.data.interests.InterestsApiRepository
import dev.suai.randomcoffee.domain.AuthRepository
import dev.suai.randomcoffee.domain.InterestsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Suppress("unused")
@Module(includes = [ApiModule::class, CircuitModule::class])
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthApiRepository): AuthRepository

    @Binds
    @Singleton
    abstract fun bindInterestsRepository(impl: InterestsApiRepository): InterestsRepository

    companion object {
        @Provides
        @Singleton
        @AuthDataStore
        fun provideTokenDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                corruptionHandler = ReplaceFileCorruptionHandler(
                    produceNewData = { emptyPreferences() }
                ),
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                produceFile = {
                    appContext.preferencesDataStoreFile("AUTH_TOKENS")
                }
            )
        }

        @Provides
        @Singleton
        @SettingsDataStore
        fun provideSettingsDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                corruptionHandler = ReplaceFileCorruptionHandler(
                    produceNewData = { emptyPreferences() }
                ),
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                produceFile = {
                    appContext.preferencesDataStoreFile("USER_PREFERENCE")
                }
            )
        }
    }
}