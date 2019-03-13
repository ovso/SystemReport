package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.systemreport.service.model.SocInfo
import io.github.ovso.systemreport.view.ui.main.CMDExecute
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SocViewModel(context: Context) : ViewModel() {
  private var compositeDisposable: CompositeDisposable = CompositeDisposable()
  var socInfoLiveData = MutableLiveData<ArrayList<SocInfo>>()
  var cpuNameObField = ObservableField<String>()
  fun fechList() {
    cpuNameObField.set(CMDExecute().run2()["cpu_model"])
    Observable.fromCallable { provideSocInfos() }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe()
    //socInfoLiveData.value = provideSocInfos()
  }

  private fun provideSocInfos(): ArrayList<SocInfo>? {
    val infos = ArrayList<SocInfo>()
    var infoMap = CMDExecute().run2()
    for (entry in CMDExecute().run2()) {
      infos.add(SocInfo(entry.key, entry.value))
    }
    infos.add(SocInfo("Architecture", System.getProperty("os.arch")!!))
    return infos
  }
}