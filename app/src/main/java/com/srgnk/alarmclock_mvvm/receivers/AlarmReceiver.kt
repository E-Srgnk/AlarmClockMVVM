package com.srgnk.alarmclock_mvvm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.srgnk.alarmclock_mvvm.StopAlarmActivity
import com.srgnk.alarmclock_mvvm.services.AlarmService
import com.srgnk.alarmclock_mvvm.utilities.ALARM_TIME


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val intentStopAlarm = Intent(context, StopAlarmActivity::class.java)
        intentStopAlarm.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intentStopAlarm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intentStopAlarm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val time = intent?.getLongExtra(ALARM_TIME, 0L) ?: 0L
        intentStopAlarm.putExtra(ALARM_TIME, time)

        context?.startActivity(intentStopAlarm)
        context?.startService(Intent(context, AlarmService::class.java))
    }
}