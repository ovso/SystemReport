package io.github.ovso.systemreport.view.ui.thermal

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
                onError = {
                  Timber.e(it)
                  handleEmpty()
                },
                onNext = {
                  if (!it.isEmpty()) {
                    infoLiveData.value = it
                    startInterval()
                  }
                })
    )
  }

  private fun handleEmpty() {
    val unknowns = ArrayList<NormalInfo>()
    unknowns.add(NormalInfo("Unknown", ""))
    infoLiveData.value = unknowns
  }

  private fun startInterval() {
    val d = Observable.interval(3000, MILLISECONDS)
        .map {
          createInfos()
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(onError = {
          Timber.e(it)
          handleEmpty()
        }, onNext = {
          infoLiveData.value = it
        })
    compositeDisposable.add(d)
  }

  private fun getTempSensorPaths(): List<String> = try {
    val exec = Runtime.getRuntime()
        .exec(arrayOf("ls", "sys/class/thermal/"))
    exec.waitFor()
    val reader = BufferedReader(InputStreamReader(exec.inputStream))
    reader.readLines()
        .sorted()
  } catch (e: Exception) {
    Timber.e(e)
    ArrayList()
  }

  private fun createInfos(): ArrayList<NormalInfo> {
    val infos = ArrayList<NormalInfo>()
    val paths = getTempSensorPaths()
    val temps = ArrayList<Int>()
    for (path in paths) {
      val name = getName(path)
      val tempInt = getTemp(path)
      if (isTemperatureValid(tempInt)) {
        infos.add(NormalInfo(name, "$tempInt\u00B0C"))
        temps.add(tempInt)
      }
    }
    infos.add(0, getTempAverage(temps))
    return infos
  }

  private fun getTempAverage(temps: ArrayList<Int>): NormalInfo {
    var sum = 0
    for (temp in temps) sum += temp

    val avg = sum / temps.size
    return NormalInfo("- Total Average -", "$avg\u00B0C")
  }

  private fun isTemperatureValid(temp: Int): Boolean = temp in 0..250

  private fun getTemp(type: String): Int {
    val fp = RandomAccessFile("/sys/class/thermal/$type/temp", "r")
    val str = fp.readLine()
    fp.close()
    return str.toInt()
  }

  private fun getName(type: String): String {
    val raf = RandomAccessFile("sys/class/thermal/$type/type", "r")
    val readLine = raf.readLine()
    raf.close()
    return readLine
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}