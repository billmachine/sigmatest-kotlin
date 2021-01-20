package com.example.sigmatest.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {
    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return AppDatabase.getInstance(app)
    }
}