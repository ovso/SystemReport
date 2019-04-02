package io.github.ovso.systemreport.view.ui.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.systemreport.service.model.NormalInfo
import io.github.ovso.systemreport.view.ui._base.round1

class SensorsViewModel(
  val context: Context,
  private var sensorManager: SensorManager
) : ViewModel(), SensorEventListener {

  val infoLiveData = MutableLiveData<ArrayList<NormalInfo>>(ArrayList())
  val updatedRowIdLiveData = MutableLiveData<Int>()
  private val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

  override fun onAccuracyChanged(
    sensor: Sensor?,
    accuracy: Int
  ) {
  }

  override fun onSensorChanged(event: SensorEvent) {
    updateSensorInfo(event)
  }

  @Synchronized
  private fun updateSensorInfo(event: SensorEvent) {
    val updatedRowId = sensorList.indexOf(event.sensor)
    infoLiveData.value!![updatedRowId] = NormalInfo(event.sensor.name, getSensorData(event))
    updatedRowIdLiveData.value = updatedRowId
  }

  /**
   * Detect sensor type for passed [SensorEvent] and format it to the correct unit
   */
  @Suppress("DEPRECATION")
  private fun getSensorData(event: SensorEvent): String {
    var data = " "

    val sensorType = event.sensor.type
    when (sensorType) {
      Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_GRAVITY, Sensor.TYPE_LINEAR_ACCELERATION ->
        data = "X=${event.values[0].round1()}m/s²  Y=${
        event.values[1].round1()}m/s²  Z=${event.values[2].round1()}m/s²"
      Sensor.TYPE_GYROSCOPE ->
        data = "X=${event.values[0].round1()}rad/s  Y=${
        event.values[1].round1()}rad/s  Z=${event.values[2].round1()}rad/s"
      Sensor.TYPE_ROTATION_VECTOR ->
        data = "X=${event.values[0].round1()}  Y=${
        event.values[1].round1()}  Z=${event.values[2].round1()}"
      Sensor.TYPE_MAGNETIC_FIELD ->
        data = "X=${event.values[0].round1()}μT  Y=${
        event.values[1].round1()}μT  Z=${event.values[2].round1()}μT"
      Sensor.TYPE_ORIENTATION ->
        data = "Azimuth=${event.values[0].round1()}°  Pitch=${
        event.values[1].round1()}°  Roll=${event.values[2].round1()}°"
      Sensor.TYPE_PROXIMITY ->
        data = "Distance=${event.values[0].round1()}cm"
      Sensor.TYPE_AMBIENT_TEMPERATURE ->
        data = "Air temperature=${event.values[0].round1()}°C"
      Sensor.TYPE_LIGHT ->
        data = "Illuminance=${event.values[0].round1()}lx"
      Sensor.TYPE_PRESSURE ->
        data = "Air pressure=${event.values[0].round1()}hPa"
      Sensor.TYPE_RELATIVE_HUMIDITY ->
        data = "Relative humidity=${event.values[0].round1()}%"
      Sensor.TYPE_TEMPERATURE ->
        data = "Device temperature=${event.values[0].round1()}°C"
      Sensor.TYPE_GYROSCOPE_UNCALIBRATED ->
        data = "X=${event.values[0].round1()}rad/s  Y=${
        event.values[1].round1()}rad/s  Z=${
        event.values[2].round1()}rad/s"
      Sensor.TYPE_GAME_ROTATION_VECTOR ->
        data = "X=${event.values[0].round1()}  Y=${
        event.values[1].round1()}  Z=${event.values[2].round1()}"
      Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED ->
        data = "X=${event.values[0].round1()}μT  Y=${
        event.values[1].round1()}μT  Z=${
        event.values[2].round1()}μT"
      Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR ->
        data = "X=${event.values[0].round1()}  Y=${
        event.values[1].round1()}  Z=${event.values[2].round1()}"
    }

    return data
  }

  fun startProvidingData() {
    if (infoLiveData.value!!.isEmpty()) {
      infoLiveData.value!!.addAll(sensorList.map { NormalInfo(it.name, " ") })
    }

    Thread {
      for (sensor in sensorList) {
        sensorManager.registerListener(
            this, sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
      }
    }.start()
  }

  fun stopProvidingData() {
    Thread {
      sensorManager.unregisterListener(this)
    }.start()
  }

}
