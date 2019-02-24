package io.github.ovso.systemreport.viewmodels

import androidx.lifecycle.ViewModel
import io.github.ovso.systemreport.view.ui.main.adapter.MainPagerAdapter

class MainViewModel : ViewModel() {
  var adapter: MainPagerAdapter? = null
  fun init() {
    adapter = MainPagerAdapter(this)
  }
}