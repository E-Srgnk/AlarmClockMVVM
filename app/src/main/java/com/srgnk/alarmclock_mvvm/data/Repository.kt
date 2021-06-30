package com.srgnk.alarmclock_mvvm.data

import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun insert(alarm: Alarm): Long

    suspend fun deleteById(alarmId: Long)

    suspend fun updateIsActiveAlarmById(alarmId: Long, newIsActive: Boolean)

    fun getAllAlarms(): Flow<MutableList<Alarm>>

    suspend fun getAlarmById(alarmId: Long): Alarm

    fun getAlarmByIdFlow(alarmId: Long): Flow<Alarm>
}