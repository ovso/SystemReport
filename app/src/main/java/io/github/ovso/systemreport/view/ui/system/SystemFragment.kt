package io.github.ovso.systemreport.view.ui.system

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
import io.github.ovso.systemreport.databinding.FragmentSystemBinding
import io.github.ovso.systemreport.view.ui._base.NormalAdapter
import kotlinx.android.synthetic.main.fragment_system.recyclerview_system

class SystemFragment : Fragment() {
  val adapter = NormalAdapter()

  companion object {
    fun newInstance() = SystemFragment()
  }

  private lateinit var viewModel: SystemViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    var databinding = DataBindingUtil.inflate<FragmentSystemBinding>(
        inflater, R.layout.fragment_system, container, false
    )
    databinding.viewModel = provideViewModel()

    return databinding.root
  }

  @Suppress("UNCHECKED_CAST")
  private fun provideViewModel(): SystemViewModel? {
    viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        SystemViewModel(context!!) as T
    })
        .get(SystemViewModel::class.java)
    return viewModel
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    recyclerview_system.adapter = adapter
    viewModel.infoLiveData.observe(this, Observer {
      adapter.items.addAll(it)
      adapter.notifyDataSetChanged()
    })
    viewModel.fetchList();
  }

  override fun onPause() {
    super.onPause()

  }

  override fun onResume() {
    super.onResume()
  }
}
