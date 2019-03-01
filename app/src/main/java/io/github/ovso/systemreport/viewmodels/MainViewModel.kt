package io.github.ovso.systemreport.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import io.github.ovso.systemreport.R
import io.github.ovso.systemreport.utils.ResourceProvider
import io.github.ovso.systemreport.view.ui.main.SimpleOnTabSelectedListener

class MainViewModel constructor(private var application: Application) : ViewModel() {
  var itemsLiveData = MutableLiveData<ArrayList<String>>()
  var titlesLiveData = MutableLiveData<ArrayList<String>>()
  var selectedObField = ObservableField(0)
  var resProvider: ResourceProvider = ResourceProvider(application.applicationContext)
  fun fetchList() {
    var items = resProvider.getStringArray(R.array.info_type)
        .toCollection(ArrayList())
    itemsLiveData.value = items
    titlesLiveData.value = items
  }

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