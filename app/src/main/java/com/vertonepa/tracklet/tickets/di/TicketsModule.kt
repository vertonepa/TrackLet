package com.vertonepa.tracklet.tickets.di

import com.vertonepa.tracklet.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TicketsModule {
    @Singleton
    @Provides
    fun provideTicketsDao(db: AppDatabase) = db.ticketsDao()
}