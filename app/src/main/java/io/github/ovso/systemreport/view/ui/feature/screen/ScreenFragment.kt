package io.github.ovso.systemreport.view.ui.feature.screen

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
import io.github.ovso.systemreport.databinding.FragmentScreenBinding
import io.github.ovso.systemreport.utils.DividerItemDecoration
import io.github.ovso.systemreport.view.ui._base.NormalAdapter
import kotlinx.android.synthetic.main.fragment_screen.recyclerview_device

class ScreenFragment : Fragment() {

  companion object {
    fun newInstance() = ScreenFragment()
  }

  private lateinit var viewModel: ScreenViewModel

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
    val dataBinding: FragmentScreenBinding =
      DataBindingUtil.inflate(inflater, R.layout.fragment_screen, container, false)
    dataBinding.viewModel = provideViewModel()
    return dataBinding.root
  }

  @Suppress("UNCHECKED_CAST")
  private fun provideViewModel(): ScreenViewModel {
    viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ScreenViewModel(context!!) as T
    })
        .get(ScreenViewModel::class.java)
    return viewModel
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setup()
    activity!!.title = "Screen"
  }

  private fun setup() {
    recyclerview_device.addItemDecoration(
        DividerItemDecoration(requireContext(), android.R.color.black)
    )
    recyclerview_device.adapter = NormalAdapter()
    viewModel.infoLiveData.observe(this, Observer {
      (recyclerview_device.adapter as NormalAdapter).items.addAll(it)
    })
    viewModel.fetchList();
  }
}