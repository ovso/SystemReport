package io.github.ovso.systemreport.view.ui.feature.soc

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.systemreport.service.model.NormalInfo
import io.github.ovso.systemreport.view.ui.main.CMDExecute

class SocViewModel(context: Context) : ViewModel() {
  var socInfoLiveData = MutableLiveData<ArrayList<NormalInfo>>()
  var cpuNameObField = ObservableField<String>()
  fun fechList() {
    cpuNameObField.set(CMDExecute().run2()["cpu_model"])
    socInfoLiveData.value = provideSocInfos()
  }

  private fun provideSocInfos(): ArrayList<NormalInfo>? {
    val infos = ArrayList<NormalInfo>()
    infos.add(NormalInfo("Architecture", System.getProperty("os.arch")!!))
    var run2 = CMDExecute().run2()
    for (entry in run2) {
      if (checkAddToList(entry.key)) {
        infos.add(NormalInfo(entry.key, entry.value))
      }
    }

    return infos
  }

  private fun checkAddToList(key: String): Boolean {
    var keyList = listOf("cpu_cores", "cpu_MHz", "vendor_id", "Hardware", "Processor", "Serial")
    return keyList.indexOf(key) != -1
  }


}