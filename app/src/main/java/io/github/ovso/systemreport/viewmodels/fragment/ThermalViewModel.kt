package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.systemreport.service.model.NormalInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit.MILLISECONDS

class ThermalViewModel(var context: Context) : ViewModel() {
  private val compositeDisposable = CompositeDisposable()

  val infoLiveData = MutableLiveData<ArrayList<NormalInfo>>()

  fun fetchData() {

    val subscribe = Observable.fromArray(createInfos())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(onError = {
          compositeDisposable.clear()
          Timber.e(it)
          val infos: ArrayList<NormalInfo> = ArrayList()
          infos.add(NormalInfo("Uknown", ""))
          infoLiveData.value = infos
        }, onNext = {
          infoLiveData.value = it
          startInterval();
        })

    compositeDisposable.add(subscribe);

  }

  private fun startInterval() {
    val d = Observable.interval(1000, MILLISECONDS)
        .map { t -> createInfos() }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(onError = {
          Timber.e(it)
          compositeDisposable.clear()
          infoLiveData.value = ArrayList()
          handleEmpty()
        }, onNext = {
          infoLiveData.value = it
        })
    compositeDisposable.add(d)

  }

  private fun handleEmpty() {

  }

  fun createInfos(): ArrayList<NormalInfo> {
    val infos = ArrayList<NormalInfo>()
    for (i in 0..16) {
      if (i == 13) continue
      val name = typeName(i)
      val value = tempValue(i).toString()
      infos.add(NormalInfo(name, value))
    }

    return infos
  }

  fun tempValue(path: Int): Float {
    val process: Process
    process = Runtime.getRuntime()
        .exec("cat sys/class/thermal/thermal_zone$path/temp")
    process.waitFor()
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val line = reader.readLine()
    return when (line != null) {
      true -> java.lang.Float.parseFloat(line)
      false -> 0.0f
    }
  }

  fun typeName(path: Int): String {
    Timber.d("typeName path = $path")
    val process: Process
    process = Runtime.getRuntime()
        .exec("cat sys/class/thermal/thermal_zone$path/type")
    process.waitFor()
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    return reader.readLine()
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}
