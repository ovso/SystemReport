package io.github.ovso.systemreport.view.bindings

import androidx.databinding.BindingAdapter
import com.google.android.material.tabs.TabLayout

object MainBindingAdapter {
  @JvmStatic
  @BindingAdapter("bind:titles") fun bindTabs(
    tabLayout: TabLayout,
    titles: ArrayList<String>
  ) {
    for (title in titles) {
      var newTab = tabLayout.newTab()
      newTab.text = title
      tabLayout.addTab(newTab)
    }
  }
}