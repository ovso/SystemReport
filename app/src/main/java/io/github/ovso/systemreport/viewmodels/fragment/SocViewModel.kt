package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.systemreport.service.model.SocInfo
import io.github.ovso.systemreport.view.ui.main.CMDExecute
import io.reactivex.disposables.CompositeDisposable

class SocViewModel(context: Context) : ViewModel() {
  private var compositeDisposable: CompositeDisposable = CompositeDisposable()
  var socInfoLiveData = MutableLiveData<ArrayList<SocInfo>>()

  fun fechList() {
    socInfoLiveData.value = provideSocInfos()
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