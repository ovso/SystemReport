package io.github.ovso.systemreport.view.bindings

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.google.android.material.tabs.TabLayout

object MainBindingAdapter {

  @JvmStatic
  @BindingAdapter("bind:currentItem") fun currentItem(
    tabLayout: TabLayout,
    index: Int
  ) {
    tabLayout.getTabAt(index)
        ?.select()
  }
}