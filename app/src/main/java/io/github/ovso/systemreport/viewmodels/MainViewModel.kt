package io.github.ovso.systemreport.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import io.github.ovso.systemreport.view.ui.main.adapter.MainPagerAdapter
import timber.log.Timber

class MainViewModel : ViewModel() {
  var adapter: MainPagerAdapter? = MainPagerAdapter(this)
  var itemsLiveData = MutableLiveData<ArrayList<String>>()
  var titlesLiveData = MutableLiveData<ArrayList<String>>()
  fun fetchList() {
    var items = arrayListOf("1", "2", "3", "4", "5")
    itemsLiveData.value = items
    titlesLiveData.value = items
  }

  fun setItems(it: ArrayList<String>?) {
    adapter?.items = it!!
    adapter?.notifyDataSetChanged()
    Timber.d("setItems = ${it.size}")
  }

  fun onPageChangeCallback(): OnPageChangeCallback {
    return object : OnPageChangeCallback() {
      override fun onPageSelected(position: Int) {

        println("onPageSelected($position)")
      }
    }
  }

}