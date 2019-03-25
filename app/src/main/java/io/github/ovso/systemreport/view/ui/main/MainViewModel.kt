package io.github.ovso.systemreport.view.ui.main

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback

class MainViewModel constructor(private var application: Application) : ViewModel() {
  var selectedObField = ObservableField(0)

  var onPageChangeCallback = object : OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
      selectedObField.set(position)
    }
  }

  var simpleOnTabSelectedListener = object : SimpleOnTabSelectedListener() {
    override fun onTabSelected(position: Int) {
      selectedObField.set(position)
    }
  }
}