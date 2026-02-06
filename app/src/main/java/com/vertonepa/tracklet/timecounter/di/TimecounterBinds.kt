package com.vertonepa.tracklet.timecounter.di

import com.vertonepa.tracklet.timecounter.data.TimecounterRepository
import com.vertonepa.tracklet.timecounter.data.TimecounterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TimecounterBinds {

    @Binds
    abstract fun bindTimecounterRepository(
        timecounterRepositoryImpl: TimecounterRepositoryImpl
    ): TimecounterRepository
}