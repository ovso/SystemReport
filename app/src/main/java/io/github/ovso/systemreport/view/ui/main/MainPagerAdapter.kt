package io.github.ovso.systemreport.view.ui.main

import android.bluetooth.BluetoothClass.Device
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.ovso.systemreport.view.ui.main.views.BatteryFragment
import io.github.ovso.systemreport.view.ui.main.views.DeviceFragment
import io.github.ovso.systemreport.view.ui.main.views.SensorsFragment
import io.github.ovso.systemreport.view.ui.main.views.SocFragment
import io.github.ovso.systemreport.view.ui.main.views.SystemFragment
import io.github.ovso.systemreport.view.ui.main.views.ThermalFragment
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
    1 -> DeviceFragment.newInstance()
    2 -> SystemFragment.newInstance()
    3 -> BatteryFragment.newInstance()
    4 -> ThermalFragment.newInstance()
    5 -> SensorsFragment.newInstance()
    else -> throw UnsupportedOperationException("Unsupported fragment")
  }

}