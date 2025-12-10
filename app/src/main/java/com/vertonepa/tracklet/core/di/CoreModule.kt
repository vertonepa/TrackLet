package com.vertonepa.tracklet.core.di

import android.content.Context
import androidx.room.Room
import com.vertonepa.tracklet.core.database.AppDatabase
import com.vertonepa.tracklet.tickets.data.local.typeconverter.Converter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Singleton
    @Provides
    fun providesRoomDB(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "database")
            .addTypeConverter(Converter())
            .build()
}