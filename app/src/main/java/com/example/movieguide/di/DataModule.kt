package com.example.movieguide.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.common.data.MovieApiService
import com.example.common.data.MovieLocalDataSource
import com.example.common.data.MovieLocalDataSourceImpl
import com.example.common.datafavorites.FavoritesDataSource
import com.example.common.datafavorites.FavoritesDataSourceImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object DataModule {

    @Singleton
    @Provides
    fun provideProductApiService(
        retrofit: Retrofit
    ): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }
    @Singleton
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "app")

    @Singleton
    @Provides
    fun provideDataStoreOfPreferences(
        applicationContext: Context
    ): DataStore<Preferences> {
        return applicationContext.appDataStore
    }

    @Singleton
    @Provides
    fun provideMovieLocalDataSource(
        dataStore: DataStore<Preferences>
    ): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(
            dataStore = dataStore
        )
    }

    @Singleton
    @Provides
    fun provideFavoriteDataSource(
        dataStore: DataStore<Preferences>,
    ): FavoritesDataSource {
        return FavoritesDataSourceImpl(
            dataStore = dataStore
        )
    }
}