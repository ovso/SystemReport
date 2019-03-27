package io.github.ovso.systemreport.view.ui.sensor

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.nisrulz.easydeviceinfo.base.EasySensorMod

import io.github.ovso.systemreport.R
import timber.log.Timber

class SensorsFragment : Fragment() {

  companion object {
    fun newInstance() = SensorsFragment()
  }

  private lateinit var viewModel: SensorsViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_sensors, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this)
        .get(SensorsViewModel::class.java)
    // TODO: Use the ViewModel
    var easySensorMod = EasySensorMod(context)
    var allSensors = easySensorMod.allSensors
    Timber.d(allSensors.toString())
  }

}
