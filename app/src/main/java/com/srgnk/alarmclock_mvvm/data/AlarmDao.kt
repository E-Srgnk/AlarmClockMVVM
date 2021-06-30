package com.srgnk.alarmclock_mvvm.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarm: Alarm): Long

    @Query("DELETE FROM alarm WHERE id = :alarmId")
    suspend fun deleteById(alarmId: Long)

    @Query("UPDATE alarm SET isActive = :newIsActive WHERE id IN (:alarmId)")
    suspend fun updateIsActiveAlarmById(alarmId: Long, newIsActive: Boolean)

    @Query("SELECT * FROM alarm")
    fun getAllAlarms(): Flow<MutableList<Alarm>>

    @Query("SELECT * FROM alarm WHERE id = :alarmId")
    suspend fun getAlarmById(alarmId: Long): Alarm

    @Query("SELECT * FROM alarm WHERE id = :alarmId")
    fun getAlarmByIdFlow(alarmId: Long): Flow<Alarm>
}