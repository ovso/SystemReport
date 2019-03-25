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
import java.io.RandomAccessFile
import java.util.concurrent.TimeUnit.MILLISECONDS

class ThermalViewModel(var context: Context) : ViewModel() {
  private val compositeDisposable = CompositeDisposable()

  val infoLiveData = MutableLiveData<ArrayList<NormalInfo>>()

  fun fetchData() {
    compositeDisposable.add(
        Observable.fromCallable { createInfos() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { Timber.e(it) },
                onNext = {
                  infoLiveData.value = it
                  startInterval()
                })
    )

  }

  private fun startInterval() {
    val d = Observable.interval(3000, MILLISECONDS)
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

  private fun getTempSensorList(): List<String> {
    try {
      val exec = Runtime.getRuntime()
          .exec(arrayOf("ls", "sys/class/thermal/"))
      exec.waitFor()
      val reader = BufferedReader(InputStreamReader(exec.inputStream))
      return reader.readLines()
          .sorted()
    } catch (e: Exception) {
      Timber.e(e)
      return ArrayList()
    }
  }

  private fun handleEmpty() {

  }

  fun createInfos(): ArrayList<NormalInfo> {
    val infos = ArrayList<NormalInfo>()
    val sensors = getTempSensorList();
    for (sensor in sensors) {
      infos.add(NormalInfo(getSensorType(sensor), getTemp(sensor)))
    }
    return infos
  }

  fun getTemp(type: String): String {
    val fp = RandomAccessFile("/sys/class/thermal/$type/temp", "r")
    val str = fp.readLine()
    fp.close()

    return str;

  }

  fun getSensorType(type: String): String {
    val raf = RandomAccessFile("sys/class/thermal/$type/type", "r")
    val readLine = raf.readLine()
    raf.close();
    return readLine;
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}
