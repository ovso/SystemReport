package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.systemreport.service.model.NormalInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit.SECONDS

class ThermalViewModel(var context: Context) : ViewModel() {
  companion object {
    val maxSize = 0..12;
  }

  val infoLiveData = MutableLiveData<List<NormalInfo>>()

  fun fetchData() {

    var subscribe = Observable.interval(1, SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy { l ->
          var infos = ArrayList<NormalInfo>()
          for (i in 0..16) {
            if (i == 13) continue
            val name = typeName(i);
            val value = tempName(i).toString()
            infos.add(NormalInfo(name, value))

          }
          infoLiveData.value = infos
          Timber.d(infos.toString())
        }
  }

  fun register() {
    //sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL)
  }

  fun unregister() {
    //sensorManager.unregisterListener(this)
  }

  fun tempName(path: Int): Float {
    val process: Process
    try {
      process = Runtime.getRuntime()
          .exec("cat sys/class/thermal/thermal_zone$path/temp")
      process.waitFor()
      val reader = BufferedReader(InputStreamReader(process.inputStream))
      val line = reader.readLine()
      if (line != null) {
        val temp = java.lang.Float.parseFloat(line)
        return temp;
      } else {
        return 0.0f
      }
    } catch (e: Exception) {
      e.printStackTrace()
      return 0.0f
    }

  }

  fun typeName(path: Int): String {
    val process: Process
    try {
      process = Runtime.getRuntime()
          .exec("cat sys/class/thermal/thermal_zone$path/type")
      process.waitFor()
      val reader = BufferedReader(InputStreamReader(process.inputStream))
      return reader.readLine()
    } catch (e: Exception) {
      //Timber.e(e)
      return "Unknown"
    }
  }

}
