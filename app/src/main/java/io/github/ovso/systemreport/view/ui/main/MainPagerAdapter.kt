package io.github.ovso.systemreport.view.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.ovso.systemreport.view.ui.main.views.BatteryFragment
import io.github.ovso.systemreport.view.ui.main.views.DeviceFragment
import io.github.ovso.systemreport.view.ui.main.views.SensorsFragment
import io.github.ovso.systemreport.view.ui.main.views.SocFragment
import io.github.ovso.systemreport.view.ui.main.views.SystemFragment
import io.github.ovso.systemreport.view.ui.main.views.ThermalFragment
import io.github.ovso.systemreport.viewmodels.MainViewModel

class MainPagerAdapter(
  private var viewModel: MainViewModel,
  fragmentManager: FragmentManager
) : FragmentStateAdapter(fragmentManager) {
  override fun getItem(position: Int): Fragment = provideFragment(position)!!

  var items = arrayListOf<String>()

  override fun getItemCount() = items.size

  companion object {
    private fun provideFragment(position: Int) =
      when (position) {
        0 -> SocFragment.newInstance()
        1 -> DeviceFragment.newInstance()
        2 -> SystemFragment.newInstance()
        3 -> BatteryFragment.newInstance()
        4 -> SensorsFragment.newInstance()
        5 -> ThermalFragment.newInstance()
        else -> throw RuntimeException("지원하지 않음")
      }
  }

}