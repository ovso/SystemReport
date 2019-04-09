package io.github.ovso.systemreport.view.ui.feature.soc

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
import io.github.ovso.systemreport.databinding.FragmentSocBinding
import io.github.ovso.systemreport.view.ui._base.NormalAdapter
import kotlinx.android.synthetic.main.fragment_soc.recyclerview_soc

class SocFragment : Fragment() {

  companion object {
    fun newInstance() = SocFragment()
  }

  private lateinit var viewModel: SocViewModel

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
    viewModel = provideViewModel()
    var binding = dataBindingInflate(inflater, container)!!
    binding.viewModel = viewModel
    return binding.root
  }

  private fun dataBindingInflate(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentSocBinding? {
    return DataBindingUtil.inflate(
        inflater, R.layout.fragment_soc, container, false
    )
  }

  @Suppress("UNCHECKED_CAST")
  private fun provideViewModel(): SocViewModel {
    return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        SocViewModel(context!!) as T
    })
        .get(SocViewModel::class.java)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupRecyclerView()
    updateList()
  }

  private fun setupRecyclerView() {
    recyclerview_soc.adapter = NormalAdapter()
  }

  private fun updateList() {
    viewModel.socInfoLiveData.observe(this, Observer {
      var socAdapter = recyclerview_soc.adapter as NormalAdapter
      socAdapter.addItems(it)
    })
    viewModel.fechList()
  }
}