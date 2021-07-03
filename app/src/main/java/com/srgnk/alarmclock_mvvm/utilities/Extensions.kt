package com.srgnk.alarmclock_mvvm.utilities

import java.util.*

fun GregorianCalendar.getHours() = this.get(Calendar.HOUR_OF_DAY)

fun GregorianCalendar.getMinutes() = this.get(Calendar.MINUTE)