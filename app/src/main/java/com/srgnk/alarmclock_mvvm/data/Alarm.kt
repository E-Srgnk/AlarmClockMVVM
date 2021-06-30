package com.srgnk.alarmclock_mvvm.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var time: Long,
    var isActive: Boolean
)