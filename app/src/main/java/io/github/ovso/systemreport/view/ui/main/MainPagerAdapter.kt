package io.github.ovso.systemreport.view.ui.main

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.ovso.systemreport.view.ui.battery.BatteryFragment
import io.github.ovso.systemreport.view.ui.screen.ScreenFragment
import io.github.ovso.systemreport.view.ui.sensor.SensorsFragment
import io.github.ovso.systemreport.view.ui.soc.SocFragment
import io.github.ovso.systemreport.view.ui.system.SystemFragment
import io.github.ovso.systemreport.view.ui.thermal.ThermalFragment
import java.lang.UnsupportedOperationException

class MainPagerAdapter(
  fragmentManager: FragmentManager,
  var titles: Array<String>
) : FragmentPagerAdapter(fragmentManager) {

  override fun getCount() = titles.size

  override fun getItem(position: Int): Fragment = provideFragments(position)

  override fun getPageTitle(position: Int) = titles.get(position)

  override fun destroyItem(
    container: ViewGroup,
    position: Int,
    `object`: Any
  ) {
    if (position >= count) {
      val manager = (`object` as Fragment).fragmentManager
      val trans = manager!!.beginTransaction()
      trans.remove(`object`)
      trans.commit()
    }
  }

  fun provideFragments(position: Int) = when (position) {
    0 -> SocFragment.newInstance()
    1 -> ScreenFragment.newInstance()
    2 -> SystemFragment.newInstance()
    3 -> BatteryFragment.newInstance()
    4 -> ThermalFragment.newInstance()
    5 -> SensorsFragment.newInstance()
    else -> throw UnsupportedOperationException("Unsupported fragment")
  }

}