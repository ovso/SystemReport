package io.github.ovso.systemreport.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import io.github.ovso.systemreport.view.ui.main.MainPagerAdapter
import io.github.ovso.systemreport.view.ui.main.SimpleOnTabSelectedListener

class MainViewModel : ViewModel() {
  var adapter: MainPagerAdapter? =
    MainPagerAdapter(this)
  var itemsLiveData = MutableLiveData<ArrayList<String>>()
  var titlesLiveData = MutableLiveData<ArrayList<String>>()
  var selectedObField = ObservableField(0)
  fun fetchList() {
    var items = arrayListOf("1", "2", "3", "4", "5")
    itemsLiveData.value = items
    titlesLiveData.value = items
  }

  fun setItems(it: ArrayList<String>?) {
    adapter?.items = it!!
    adapter?.notifyDataSetChanged()
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