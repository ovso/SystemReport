package io.github.ovso.systemreport.view.ui.main

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainPagerAdapter(
  fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

  var items = ArrayList<Fragment>()
  var titles = ArrayList<String>()

  override fun getCount() = items.size

  override fun getItem(position: Int): Fragment = items.get(position)

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

}