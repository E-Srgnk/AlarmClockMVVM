package com.srgnk.alarmclock_mvvm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.srgnk.alarmclock_mvvm.databinding.StopAlarmActivityBinding
import com.srgnk.alarmclock_mvvm.services.AlarmService
import com.srgnk.alarmclock_mvvm.utilities.ALARM_TIME
import com.srgnk.alarmclock_mvvm.utilities.AlarmUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class StopAlarmActivity : AppCompatActivity() {

    private lateinit var binding: StopAlarmActivityBinding

    private var wakeLock: WakeLock? = null

    @Inject
    lateinit var alarmUtils: AlarmUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE,
            "SimpleAlarmClock:StopAlarmActivity"
        )
        wakeLock?.acquire(1 * 60 * 1000L)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)

        binding = StopAlarmActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar = GregorianCalendar()
        val time = intent.getLongExtra(ALARM_TIME, calendar.timeInMillis)
        calendar.timeInMillis = time

        binding.hours.text = calendar.get(Calendar.HOUR_OF_DAY).toString()
        binding.minutes.text = calendar.get(Calendar.MINUTE).toString()

        binding.stopAlarm.setOnClickListener {
            applicationContext.stopService(Intent(applicationContext, AlarmService::class.java))

            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
            finish()
        }

        Thread {
            val alarmClockTime = 60000L
            Thread.sleep(alarmClockTime)
            applicationContext.stopService(Intent(applicationContext, AlarmService::class.java))
            finish()
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        wakeLock?.release()
    }
}