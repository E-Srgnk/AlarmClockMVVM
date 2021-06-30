package com.srgnk.alarmclock_mvvm.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.switchmaterial.SwitchMaterial
import com.srgnk.alarmclock_mvvm.R
import com.srgnk.alarmclock_mvvm.adapters.AlarmsAdapter
import com.srgnk.alarmclock_mvvm.databinding.FragmentMainBinding
import com.srgnk.alarmclock_mvvm.utilities.ALARM_ID
import com.srgnk.alarmclock_mvvm.viewmodels.AlarmsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : Fragment(), AlarmsAdapter.ItemClickListener {

    private var mainBinding: FragmentMainBinding? = null
    private val binding get() = mainBinding!!

    private val viewModel: AlarmsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainBinding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AlarmsAdapter(this)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        subscribeUi(adapter)

        binding.addNewAlarm.setOnClickListener {
            navigateToAlarmScreen(null)
        }
    }

    private fun subscribeUi(adapter: AlarmsAdapter) {
        viewModel.alarms.observe(viewLifecycleOwner) {
            adapter.setValues(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun navigateToAlarmScreen(alarmId: Long?) {
        val bundle = Bundle()
        alarmId?.let {
            bundle.putLong(ALARM_ID, it)
        }
        findNavController().navigate(
            R.id.action_MainScreen_to_AlarmScreen,
            bundle
        )
    }

    override fun recyclerViewClickListener(view: View, alarmId: Long) {
        when (view.id) {
            R.id.alarm -> navigateToAlarmScreen(alarmId)
            R.id.turn_alarm -> {
                val isActive = (view as SwitchMaterial).isChecked
                viewModel.changeAlarmActivity(alarmId, isActive)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainBinding = null
    }
}