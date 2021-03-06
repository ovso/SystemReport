package io.github.ovso.systemreport.view.ui.feature.battery

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.nisrulz.easydeviceinfo.base.EasyBatteryMod
import io.github.ovso.systemreport.service.model.NormalInfo
import io.github.ovso.systemreport.utils.TempFormatter
import timber.log.Timber

class BatteryViewModel(var context: Context) : ViewModel() {
  var batteryMod = EasyBatteryMod(context)
  var batteryInfoLiveData = MutableLiveData<ArrayList<NormalInfo>>()
  fun changeBattery(intent: Intent) {
    batteryInfoLiveData.value = provideNormalInfos(intent)
  }

  fun fetchList() {
    registerBattery()
  }

  private fun registerBattery() {
    val batFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
    context.registerReceiver(broadcastReceiver, batFilter)
  }

  private fun provideNormalInfos(intent: Intent): ArrayList<NormalInfo>? {
    var infos = ArrayList<NormalInfo>()
    infos.add(NormalInfo("Health", getHealth(intent)))
    infos.add(NormalInfo("Level", getLevel(intent)))
    infos.add(NormalInfo("Power source", getCharger(intent)))
    infos.add(NormalInfo("Status", getStatus(intent)))
    infos.add(NormalInfo("Technology", getTechnology(intent)))
    infos.add(NormalInfo("Temperature", getTemperature(intent)))
    infos.add(NormalInfo("Voltage", getVoltage()))
    infos.add(NormalInfo("Capacity", getBatteryCapacity()))
    return infos
  }

  private fun getVoltage(): String {
    return "${batteryMod.batteryVoltage / 1000.0}V"
  }

  private fun getTemperature(intent: Intent): String {
    var temp = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10.0).toFloat()
    return TempFormatter().format(temp)
  }

  fun getTechnology(intent: Intent) = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)

  private fun getStatus(intent: Intent): String {
    var status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
    when (status) {
      BatteryManager.BATTERY_STATUS_CHARGING -> return "Charging"
      BatteryManager.BATTERY_STATUS_DISCHARGING -> return "Discharging"
      BatteryManager.BATTERY_STATUS_FULL -> return "Full"
      BatteryManager.BATTERY_STATUS_NOT_CHARGING -> return "Not charging"
      else -> return "Unknown"
    }
  }

  fun getCharger(intent: Intent): String {
    var chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
    when (chargePlug) {
      BatteryManager.BATTERY_PLUGGED_AC -> return "AC powered"
      BatteryManager.BATTERY_PLUGGED_USB -> return "USB powered"
      BatteryManager.BATTERY_PLUGGED_WIRELESS -> return "WIRELESS powered"
      else -> return "Battery powered"
    }
  }

  fun getLevel(intent: Intent): String {
    var percentage: Int
    val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
    val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
    percentage = (level / scale.toFloat() * 100).toInt()

    return "${percentage}%"
  }

  var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(
      context: Context?,
      intent: Intent?
    ) {
      changeBattery(intent!!)
    }
  }

  private fun getHealth(intent: Intent): String {
    val health: Int = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)
    when (health) {
      BatteryManager.BATTERY_HEALTH_GOOD -> return "Good"
      BatteryManager.BATTERY_HEALTH_COLD -> return "Cold"
      BatteryManager.BATTERY_HEALTH_DEAD -> return "Dead"
      BatteryManager.BATTERY_HEALTH_OVERHEAT -> return "Overheat"
      BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> return "Over voltage"
      BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> return "Unspecified failure"
      else -> return "Unknown" // BatteryManager.BATTERY_HEALTH_UNKNOWN
    }
  }

  /**
   * @return battery capacity from private API. In case of error it will return -1.
   */
  @SuppressLint("PrivateApi")
  fun getBatteryCapacity(): String {
    var capacity = -1.0
    try {
      val powerProfile = Class.forName("com.android.internal.os.PowerProfile")
          .getConstructor(Context::class.java)
          .newInstance(context)
      capacity = Class
          .forName("com.android.internal.os.PowerProfile")
          .getMethod("getAveragePower", String::class.java)
          .invoke(powerProfile, "battery.capacity") as Double
    } catch (e: Exception) {
      Timber.e(e)
    }
    if (capacity != -1.0) {
      return "${capacity}mAh"
    } else {
      return "Unknown"
    }
  }

  //        val capacity = batteryStatusProvider.getBatteryCapacity().round2()
//        if (capacity != -1.0) {
//            functionsList.add(Pair(resources.getString(R.string.capacity), "${capacity}mAh"))
//        }
  override fun onCleared() {
    super.onCleared()
    context.unregisterReceiver(broadcastReceiver)
  }
}
