package io.github.ovso.systemreport.view.ui.battery

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
import io.github.ovso.systemreport.databinding.FragmentBatteryBinding
import io.github.ovso.systemreport.view.ui._base.NormalAdapter
import io.github.ovso.systemreport.viewmodels.fragment.BatteryViewModel
import kotlinx.android.synthetic.main.fragment_battery.recyclerview_battery

class BatteryFragment : Fragment() {
  var adapter = NormalAdapter()

  companion object {
    fun newInstance() = BatteryFragment()
  }

  private lateinit var viewModel: BatteryViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return dataBinding(inflater, container)
  }

  private fun dataBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): View {
    setupViewModel()
    var binding = DataBindingUtil.inflate<FragmentBatteryBinding>(
        inflater, R.layout.fragment_battery, container, false
    )
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupRecyclerView()
  }

  private fun setupRecyclerView() {
    recyclerview_battery.adapter = adapter
    updateList()
  }

  @Suppress("UNCHECKED_CAST")
  private fun setupViewModel() {
    viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        BatteryViewModel(context!!) as T
    })
        .get(BatteryViewModel::class.java)
  }

  private fun updateList() {
    viewModel.batteryInfoLiveData.observe(this, Observer {
      adapter.addItems(it)
      adapter.notifyDataSetChanged()
    })
    viewModel.fetchList()
  }

}
