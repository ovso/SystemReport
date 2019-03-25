package io.github.ovso.systemreport.view.ui.thermal

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
import io.github.ovso.systemreport.databinding.FragmentThermalBinding
import io.github.ovso.systemreport.view.ui._base.NormalAdapter
import kotlinx.android.synthetic.main.fragment_thermal.recyclerview_thermal

class ThermalFragment : Fragment() {
  private val adapter = NormalAdapter()

  companion object {
    fun newInstance() = ThermalFragment()
  }

  private lateinit var viewModel: ThermalViewModel

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
  ): View? {
    val dataBinding = DataBindingUtil.inflate<FragmentThermalBinding>(
        inflater, R.layout.fragment_thermal, container, false
    )
    dataBinding.viewModel = provideViewModel()
    return dataBinding.root
  }

  @Suppress("UNCHECKED_CAST") fun provideViewModel(): ThermalViewModel {
    viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ThermalViewModel(context!!) as T
      }
    })
        .get(ThermalViewModel::class.java)

    return viewModel
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    recyclerview_thermal.adapter = adapter
    viewModel.infoLiveData.observe(this, Observer {
      adapter.addItems(it)
      adapter.notifyDataSetChanged()
    })
    viewModel.fetchData()
  }

}
