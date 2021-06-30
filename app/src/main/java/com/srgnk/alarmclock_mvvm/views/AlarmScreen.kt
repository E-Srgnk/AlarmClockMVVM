package com.srgnk.alarmclock_mvvm.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.srgnk.alarmclock_mvvm.AppActivity
import com.srgnk.alarmclock_mvvm.databinding.FragmentAlarmBinding
import com.srgnk.alarmclock_mvvm.viewmodels.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AlarmScreen : Fragment() {

    private var alarmBinding: FragmentAlarmBinding? = null
    private val binding get() = alarmBinding!!

    private val viewModel: AlarmViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        alarmBinding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppActivity).setSupportActionBar(binding.toolbar)
        (activity as AppActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.hourPicker.maxValue = 23
        binding.hourPicker.minValue = 0

        binding.minutePicker.maxValue = 59
        binding.minutePicker.minValue = 0

        binding.saveAlarm.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, binding.hourPicker.value)
            calendar.set(Calendar.MINUTE, binding.minutePicker.value)
            calendar.set(Calendar.SECOND, 0)

            viewModel.clickedSaveAlarm(calendar)
            closeScreen()
        }

        binding.deleteAlarm.setOnClickListener {
            viewModel.clickedDeleteAlarm()
            closeScreen()
        }

        arguments?.getLong("alarmId")

        subscribe()
    }

    private fun subscribe() {
        viewModel.alarm.observe(viewLifecycleOwner) { alarm ->
            val calendar = GregorianCalendar().also { it.timeInMillis = alarm.time }
            binding.hourPicker.value = calendar.get(Calendar.HOUR_OF_DAY)
            binding.minutePicker.value = calendar.get(Calendar.MINUTE)
        }
    }

    private fun closeScreen() {
        findNavController().popBackStack()
    }
//
//    override fun showMessage(message: Int) {
//        Toast.makeText(context, getString(message), Toast.LENGTH_SHORT).show()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        alarmBinding = null
    }
}