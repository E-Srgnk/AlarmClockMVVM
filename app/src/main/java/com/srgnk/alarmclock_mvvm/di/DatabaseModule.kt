package com.srgnk.alarmclock_mvvm.di

import android.content.Context
import androidx.room.Room
import com.srgnk.alarmclock_mvvm.data.AlarmDao
import com.srgnk.alarmclock_mvvm.data.AlarmDatabase
import com.srgnk.alarmclock_mvvm.utilities.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AlarmDatabase =
        Room.databaseBuilder(context, AlarmDatabase::class.java, DATABASE_NAME).build()

    @Provides
    fun provideNoteDao(alarmDatabase: AlarmDatabase): AlarmDao = alarmDatabase.alarmDao()
}