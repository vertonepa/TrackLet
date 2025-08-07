package com.vertonepa.tracklet.tickets.di

import com.vertonepa.tracklet.tickets.data.repository.TicketsRepositoryImpl
import com.vertonepa.tracklet.tickets.domain.repository.ITicketsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TicketsBindingModule {

    @Binds
    @Singleton
    abstract fun bindsTicketsRepository(repository: TicketsRepositoryImpl): ITicketsRepository
}