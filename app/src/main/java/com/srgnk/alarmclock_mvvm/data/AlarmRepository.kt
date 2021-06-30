package com.srgnk.alarmclock_mvvm.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepository @Inject constructor(private val alarmDao: AlarmDao) : Repository {

    override suspend fun insert(alarm: Alarm): Long {
        return alarmDao.insert(alarm)
    }

    override suspend fun deleteById(alarmId: Long) {
        alarmDao.deleteById(alarmId)
    }

    override suspend fun updateIsActiveAlarmById(alarmId: Long, newIsActive: Boolean) {
        alarmDao.updateIsActiveAlarmById(alarmId, newIsActive)
    }

    override fun getAllAlarms() = alarmDao.getAllAlarms()

    override suspend fun getAlarmById(alarmId: Long) = alarmDao.getAlarmById(alarmId)

    override fun getAlarmByIdFlow(alarmId: Long) = alarmDao.getAlarmByIdFlow(alarmId)

}