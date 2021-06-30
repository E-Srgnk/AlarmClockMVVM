package com.srgnk.alarmclock_mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.srgnk.alarmclock_mvvm.data.Alarm
import com.srgnk.alarmclock_mvvm.data.AlarmRepository
import com.srgnk.alarmclock_mvvm.utilities.AlarmUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmsListViewModel @Inject constructor(
    private val alarmUtils: AlarmUtils,
    private val repository: AlarmRepository
) : ViewModel() {

    val alarms: LiveData<MutableList<Alarm>> =
        repository.getAllAlarms().asLiveData()

    fun changeAlarmActivity(alarmId: Long, isActive: Boolean) {
        viewModelScope.launch {
            repository.updateIsActiveAlarmById(alarmId, isActive)

            val alarm = repository.getAlarmById(alarmId)
            when (isActive) {
                true -> alarmUtils.setAlarmOn(alarm)
                false -> alarmUtils.setAlarmOff(alarmId)
            }
        }
    }
}