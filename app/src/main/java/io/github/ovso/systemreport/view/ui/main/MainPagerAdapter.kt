package io.github.ovso.systemreport.view.ui.main

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

}