package com.srgnk.alarmclock_mvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.srgnk.alarmclock_mvvm.data.Alarm
import com.srgnk.alarmclock_mvvm.databinding.AlarmBinding
import java.util.*

class AlarmsAdapter(
    private val listener: ItemClickListener
) : RecyclerView.Adapter<AlarmsAdapter.ViewHolder>() {

    private var values: MutableList<Alarm>? = null

    private val calendar = GregorianCalendar()

    interface ItemClickListener {
        fun recyclerViewClickListener(view: View, alarmId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmsAdapter.ViewHolder {
        val itemBinding = AlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        values?.let {
            calendar.timeInMillis = it[position].time
            val hours = calendar.get(Calendar.HOUR_OF_DAY)
            val minutes = calendar.get(Calendar.MINUTE)
            holder.hours.text = if (hours < 10) "0$hours" else "$hours"
            holder.minutes.text = if (minutes < 10) "0$minutes" else "$minutes"
            holder.turnAlarm.isChecked = it[position].isActive
        }
    }

    fun setValues(values: MutableList<Alarm>) {
        this.values = values
    }

    override fun getItemCount() = values?.size ?: 0

    inner class ViewHolder(binding: AlarmBinding, private val listener: ItemClickListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        var hours = binding.hours
        var minutes = binding.minutes
        var turnAlarm = binding.turnAlarm

        init {
            binding.alarm.setOnClickListener(this)
            binding.turnAlarm.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            values?.let {
                listener.recyclerViewClickListener(view, it[adapterPosition].id)
            }
        }
    }

}