package com.srgnk.alarmclock_mvvm.viewmodels

import androidx.lifecycle.*
import com.srgnk.alarmclock_mvvm.data.Alarm
import com.srgnk.alarmclock_mvvm.data.AlarmRepository
import com.srgnk.alarmclock_mvvm.utilities.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val alarmUtils: AlarmUtils,
    private val repository: AlarmRepository
) : ViewModel() {

    private val calendar = GregorianCalendar()

    private var id: Long = savedStateHandle.get<Long>(ALARM_ID) ?: 0L

    val alarm: LiveData<Alarm> = MutableLiveData(Alarm(0, 0, 0, 0, true))

    val alarmState: MutableLiveData<AlarmState> = MutableLiveData()

    fun fetchAlarm() {
        if (isNotNewAlarm()) {
            viewModelScope.launch {
                val a = repository.getAlarmById(id)
                initAlarm(a.id, a.hour, a.minute, a.time, a.isActive)
            }
        } else {
            initAlarm(
                hour = calendar.getHours(),
                minute = calendar.getMinutes(),
                time = calendar.timeInMillis
            )
        }
    }

    private fun initAlarm(
        id: Long = 0,
        hour: Int,
        minute: Int,
        time: Long,
        isActive: Boolean = true
    ) {
        alarm.value?.id = id
        alarm.value?.hour = hour
        alarm.value?.minute = minute
        alarm.value?.time = time
        alarm.value?.isActive = isActive
    }

    private fun isNotNewAlarm() = id != 0L

    fun clickedSaveAlarm() {
        alarm.value?.let {
            calendar.set(Calendar.HOUR_OF_DAY, it.hour)
            calendar.set(Calendar.MINUTE, it.minute)
            calendar.set(Calendar.SECOND, 0)

            if (calendar.timeInMillis < System.currentTimeMillis()) {
                calendar.add(Calendar.HOUR_OF_DAY, 24)
            }

            it.time = calendar.timeInMillis
            saveAlarm(it)
            alarmUtils.setAlarmOn(it)

            alarmState.postValue(AlarmState.ALARM_SAVED)
        }
    }

    private fun saveAlarm(alarm: Alarm) {
        viewModelScope.launch {
            repository.insert(alarm)
        }
    }

    fun clickedDeleteAlarm() {
        deleteAlarm(id)
        alarmUtils.setAlarmOff(id)

        alarmState.postValue(AlarmState.ALARM_DELETED)
    }

    private fun deleteAlarm(id: Long) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}