package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.systemreport.service.model.SocInfo
import io.github.ovso.systemreport.view.ui.main.CMDExecute
import timber.log.Timber

class SocViewModel(context: Context) : ViewModel() {
  var socInfoLiveData = MutableLiveData<ArrayList<SocInfo>>()

  fun fechList() {
    CMDExecute().run()
    var run2 = CMDExecute().run2()
    Timber.d("run2 = " + run2)

    Timber.d("cpu architecture = " + System.getProperty("os.arch"))
  }

}