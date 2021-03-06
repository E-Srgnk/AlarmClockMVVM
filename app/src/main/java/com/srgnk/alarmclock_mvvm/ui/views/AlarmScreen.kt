package com.srgnk.alarmclock_mvvm.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.srgnk.alarmclock_mvvm.R
import com.srgnk.alarmclock_mvvm.ui.activities.AppActivity
import com.srgnk.alarmclock_mvvm.databinding.FragmentAlarmBinding
import com.srgnk.alarmclock_mvvm.utilities.AlarmState
import com.srgnk.alarmclock_mvvm.viewmodels.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        binding.saveAlarm.setOnClickListener {
            viewModel.clickedSaveAlarm()
            closeScreen()
        }

        binding.deleteAlarm.setOnClickListener {
            viewModel.clickedDeleteAlarm()
            closeScreen()
        }

        viewModel.fetchAlarm()

        binding.viewModel = viewModel

        viewModel.alarmState.observe(viewLifecycleOwner) {
            when (it) {
                AlarmState.ALARM_SAVED -> showMessage(R.string.alarm_saved)
                AlarmState.ALARM_DELETED -> showMessage(R.string.alarm_deleted)
            }
        }
    }

    private fun showMessage(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun closeScreen() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        alarmBinding = null
    }
}