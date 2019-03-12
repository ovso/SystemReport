package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.systemreport.service.model.SocInfo
import io.github.ovso.systemreport.view.ui.main.CMDExecute
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.toObservable
import timber.log.Timber

class SocViewModel(context: Context) : ViewModel() {
  private var compositeDisposable: CompositeDisposable = CompositeDisposable()
  var socInfoLiveData = MutableLiveData<ArrayList<SocInfo>>()

  fun fechList() {
    var toObservable = CMDExecute().run2()
        .iterator()
        .toObservable()
        .map { t -> {
          Timber.d("dddddd")
        } }.subscribe()

    //socInfoLiveData.value = provideSocInfos()
    //var run2 = CMDExecute().run2()

    Timber.d("cpu architecture = " + System.getProperty("os.arch"))
  }

  private fun provideSocInfos(): ArrayList<SocInfo>? {
    val infos = ArrayList<SocInfo>()
    var toObservable = CMDExecute().run2()
        .iterator()
        .toObservable()
        .map { }

    return infos
  }
}