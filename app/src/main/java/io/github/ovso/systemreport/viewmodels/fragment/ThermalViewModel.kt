package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel;
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader

class ThermalViewModel(var context: Context) : ViewModel(), SensorEventListener {
  private lateinit var temperatureSensor: Sensor
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
    //Timber.d(cpuTemperatureTest())
  }

  private lateinit var sensorManager: SensorManager
  fun fetchData() {
    sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
  }

  fun register() {
    sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL)
  }

  fun unregister() {
    sensorManager.unregisterListener(this)
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
        return temp / 1000.0f
      } else {
        return 51.0f
      }
    } catch (e: Exception) {
      e.printStackTrace()
      return 0.0f
    }

  }

  fun cpuTemperatureTest(): String {
    val process: Process
    try {
      process = Runtime.getRuntime()
          .exec("cat sys/class/thermal/thermal_zone0/type")
      process.waitFor()
      val reader = BufferedReader(InputStreamReader(process.inputStream))
      return reader.readLine()
    } catch (e: Exception) {
      Timber.e(e)
      return "Unknown"
    }
  }

}
