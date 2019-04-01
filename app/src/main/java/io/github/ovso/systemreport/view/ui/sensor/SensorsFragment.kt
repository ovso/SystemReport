package io.github.ovso.systemreport.view.ui.sensor

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.github.ovso.systemreport.R
import io.github.ovso.systemreport.databinding.FragmentSensorsBinding
import io.github.ovso.systemreport.view.ui._base.NormalAdapter
import kotlinx.android.synthetic.main.fragment_sensors.recyclerview_sensors
import timber.log.Timber

class SensorsFragment : Fragment() {

  companion object {
    fun newInstance() = SensorsFragment()
  }

  private lateinit var adapter: NormalAdapter
  private lateinit var viewModel: SensorsViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    var databinding = DataBindingUtil.inflate<FragmentSensorsBinding>(
        inflater, R.layout.fragment_sensors, container, false
    )
    viewModel = provideViewModel();
    databinding.viewModel = viewModel
    return databinding.root
  }

  private fun setupRecyclerView() {

    viewModel.infoLiveData.observe(viewLifecycleOwner, Observer {
      adapter.items.addAll(it)
      adapter.notifyDataSetChanged()
    })
    viewModel.updatedRowIdLiveData.observe(viewLifecycleOwner, Observer {
      adapter.items[it] = viewModel.infoLiveData.value!!.get(it)
      adapter.notifyItemChanged(it)
    })
    adapter = NormalAdapter()
    recyclerview_sensors.adapter = adapter
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupRecyclerView()
  }

  @Suppress("UNCHECKED_CAST")
  private fun provideViewModel() =
    ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      private fun getSensorManager(): SensorManager {
        return context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
      }

      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SensorsViewModel(context!!, getSensorManager()) as T
      }
    })
        .get(SensorsViewModel::class.java)

  override fun onStart() {
    super.onStart()
    viewModel.startProvidingData()
  }

  override fun onStop() {
    super.onStop()
    viewModel.stopProvidingData()
  }

}
