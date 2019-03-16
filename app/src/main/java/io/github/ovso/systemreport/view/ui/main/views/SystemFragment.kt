package io.github.ovso.systemreport.view.ui.main.views

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
import io.github.ovso.systemreport.service.model.NormalInfo
import io.github.ovso.systemreport.view.ui.main.views.adapter.NormalAdapter
import io.github.ovso.systemreport.viewmodels.fragment.SystemViewModel
import kotlinx.android.synthetic.main.fragment_system.recyclerview_system
import timber.log.Timber

class SystemFragment : Fragment() {

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
    recyclerview_system.adapter = NormalAdapter()
    viewModel.infoLiveData.observe(this, Observer {
      var normalAdapter = recyclerview_system.adapter as NormalAdapter
      normalAdapter.items.addAll(it)
    })
    viewModel.uptimeLiveData.observe(this, Observer {
      var normalAdapter = recyclerview_system.adapter as NormalAdapter
      normalAdapter.items.set(
          normalAdapter.items.lastIndex, NormalInfo("System uptime", viewModel.getUptime())
      )
      normalAdapter.notifyDataSetChanged()
      Timber.d(viewModel.getUptime())
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
