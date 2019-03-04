package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.nisrulz.easydeviceinfo.base.EasyBatteryMod
import io.github.ovso.systemreport.service.model.BatteryInfo

class BatteryViewModel(var context: Context) : ViewModel() {
  var batteryMod = EasyBatteryMod(context)
  var batteryInfoLiveData = MutableLiveData<ArrayList<BatteryInfo>>()
  var batteryInfoObField = ObservableField<ArrayList<BatteryInfo>>()
  fun fetchList() {
    batteryInfoLiveData.value = provideBatteryInfos()
    batteryInfoObField.set(provideBatteryInfos())
  }

  private fun provideBatteryInfos(): ArrayList<BatteryInfo>? {
    var infos = ArrayList<BatteryInfo>()
    infos.add(BatteryInfo("Health", getHealth()))
    infos.add(BatteryInfo("Percentage", getPercentage()))
    infos.add(BatteryInfo("Power source", getCharger()))
    infos.add(BatteryInfo("Status", getStatus()))
    infos.add(BatteryInfo("Technology", getTechnology()))
    infos.add(BatteryInfo("Temperature", getTemperature()))
    infos.add(BatteryInfo("Voltage", getVoltage()))
    return infos
  }

  private fun getVoltage(): String {
    return "${batteryMod.batteryVoltage} mV"
  }

  private fun getTemperature(): String {
    var temp = 0.0f
    val batteryStatus = getBatteryStatusIntent()
    batteryStatus?.let {
      temp = (it.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10.0).toFloat()
    }

    return "$temp \u2103"

  }

  fun getTechnology(): String {
    var batteryStatus = getBatteryStatusIntent()
    batteryStatus?.let {
      return it.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)
    }
    return "Unknown"
  }

  private fun getStatus(): String {
    val batteryStatus = getBatteryStatusIntent()
    batteryStatus?.let {
      var status = it.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
      when (status) {
        BatteryManager.BATTERY_STATUS_CHARGING -> return "Charging"
        BatteryManager.BATTERY_STATUS_DISCHARGING -> return "Discharging"
        BatteryManager.BATTERY_STATUS_FULL -> return "Full"
        BatteryManager.BATTERY_STATUS_NOT_CHARGING -> return "Not charging"
        else -> return "Unknown"
      }
    }
    return "Unknown"
  }

  fun getCharger(): String {
    val batteryStatus = getBatteryStatusIntent()
    batteryStatus?.let {
      var chargePlug = it.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
      when (chargePlug) {
        BatteryManager.BATTERY_PLUGGED_AC -> return "AC"
        BatteryManager.BATTERY_PLUGGED_USB -> return "USB Port"
        BatteryManager.BATTERY_PLUGGED_WIRELESS -> return "WIRELESS"
        else -> return "Battery"
      }
    }
    return "Battery"
  }

  fun getPercentage(): String {
    var percentage = 0
    val batteryStatus = getBatteryStatusIntent()
    batteryStatus?.let {
      val level = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
      val scale = it.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
      percentage = (level / scale.toFloat() * 100).toInt()
    }

    return "${percentage}%"
  }

  private fun getBatteryStatusIntent(): Intent? {
    val batFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
    return context.registerReceiver(null, batFilter)
  }

  private fun getHealth(): String {
    val batteryStatus = getBatteryStatusIntent()
    batteryStatus?.let {
      var health: Int = it.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)
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
    return "Unknown"
  }
}
