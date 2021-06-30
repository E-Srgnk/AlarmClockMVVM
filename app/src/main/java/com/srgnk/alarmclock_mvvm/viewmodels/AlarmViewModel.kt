package com.srgnk.alarmclock_mvvm.viewmodels

import androidx.lifecycle.*
import com.srgnk.alarmclock_mvvm.data.Alarm
import com.srgnk.alarmclock_mvvm.data.AlarmRepository
import com.srgnk.alarmclock_mvvm.utilities.ALARM_ID
import com.srgnk.alarmclock_mvvm.utilities.AlarmUtils
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

    private val time: Long by lazy { GregorianCalendar().timeInMillis }

    private var id: Long = savedStateHandle.get<Long>(ALARM_ID) ?: 0

    var alarm: LiveData<Alarm> = MutableLiveData(Alarm(0, time, false))

    init {
        if (isNotNewAlarm()) {
            alarm = repository.getAlarmByIdFlow(id).asLiveData()
        }
    }

    private fun isNotNewAlarm() = id != 0L

    fun clickedSaveAlarm(calendar: Calendar) {
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.HOUR_OF_DAY, 24)
        }

        val alarm = Alarm(id, calendar.timeInMillis, true)
        saveAlarm(alarm)
    }

    private fun saveAlarm(alarm: Alarm) {
        viewModelScope.launch {
            repository.insert(alarm)
            alarmUtils.setAlarmOn(alarm)
        }
    }

    fun clickedDeleteAlarm() {
        deleteAlarm(id)
        alarmUtils.setAlarmOff(id)
    }

    private fun deleteAlarm(id: Long) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}