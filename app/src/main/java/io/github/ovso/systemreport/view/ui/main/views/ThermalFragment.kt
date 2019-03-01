package io.github.ovso.systemreport.view.ui.main.views

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.ovso.systemreport.R
import io.github.ovso.systemreport.viewmodels.fragment.ThermalViewModel

class ThermalFragment : Fragment() {

  companion object {
    fun newInstance() = ThermalFragment()
  }

  private lateinit var viewModel: ThermalViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_thermal, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this)
        .get(ThermalViewModel::class.java)
    // TODO: Use the ViewModel
  }

}
