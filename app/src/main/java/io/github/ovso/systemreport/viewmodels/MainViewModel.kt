package io.github.ovso.systemreport.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.systemreport.view.ui.main.adapter.MainPagerAdapter
import timber.log.Timber

class MainViewModel : ViewModel() {
  fun fetchList() {
    mutableLiveData.value = arrayListOf("1", "2", "3", "4", "5")
  }

  fun setItems(it: ArrayList<String>?) {
    adapter?.items = it!!
    adapter?.notifyDataSetChanged()
    Timber.d("setItems = ${it.size}")
  }

  var adapter: MainPagerAdapter? = MainPagerAdapter(this)
  var mutableLiveData = MutableLiveData<ArrayList<String>>()
}