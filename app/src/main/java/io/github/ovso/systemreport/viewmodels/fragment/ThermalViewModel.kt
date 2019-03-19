package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit.SECONDS

class ThermalViewModel(var context: Context) : ViewModel(), SensorEventListener {
  override fun onAccuracyChanged(
    sensor: Sensor?,
    accuracy: Int
  ) {

  }

  @Suppress("UNREACHABLE_CODE")
  override fun onSensorChanged(event: SensorEvent?) {
    //var sensor = event?.sensor
    val millibarsOfTemperature = event!!.values[0]
    var size = event!!.values.size
    //Timber.d("millibarsOfTemperature = $millibarsOfTemperature size = $size")
    //Timber.d(cpuTemperatureType())
  }

  fun fetchData() {

    var subscribe = Observable.interval(1, SECONDS)
        .subscribeBy { l ->
          var type = cpuTemperatureType()
          var temp = cpuTemperature().toString()

          Timber.d("$type : $temp")
        }
  }

  fun register() {
    //sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL)
  }

  fun unregister() {
    //sensorManager.unregisterListener(this)
  }

  fun cpuTemperature(): Float {
    val process: Process
    try {
      process = Runtime.getRuntime()
          .exec("cat sys/class/thermal/thermal_zone0/temp")
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

  fun cpuTemperatureType(): String {
    val process: Process
    try {
      process = Runtime.getRuntime()
          .exec("cat sys/class/thermal/thermal_zone0/type")
      process.waitFor()
      val reader = BufferedReader(InputStreamReader(process.inputStream))
      return reader.readLine()
    } catch (e: Exception) {
      //Timber.e(e)
      return "Unknown"
    }
  }

}
