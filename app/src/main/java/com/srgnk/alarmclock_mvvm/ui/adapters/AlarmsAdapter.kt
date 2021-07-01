package com.srgnk.alarmclock_mvvm.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.srgnk.alarmclock_mvvm.data.Alarm
import com.srgnk.alarmclock_mvvm.databinding.AlarmBinding

class AlarmsAdapter(
    private val listener: ItemClickListener
) : RecyclerView.Adapter<AlarmsAdapter.ViewHolder>() {

    private var values: MutableList<Alarm>? = null

    interface ItemClickListener {
        fun recyclerViewClickListener(view: View, alarmId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = AlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        values?.let {
            holder.bind(it[position])
        }
    }

    fun setValues(values: MutableList<Alarm>) {
        this.values = values
    }

    override fun getItemCount() = values?.size ?: 0

    inner class ViewHolder(private val binding: AlarmBinding, private val listener: ItemClickListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.mainLayout.setOnClickListener(this)
            binding.turnAlarm.setOnClickListener(this)
        }

        fun bind(alarm: Alarm) {
            binding.alarm = alarm
            binding.executePendingBindings()
        }

        override fun onClick(view: View) {
            values?.let {
                listener.recyclerViewClickListener(view, it[adapterPosition].id)
            }
        }
    }

}