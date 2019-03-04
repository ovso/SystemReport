package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.nisrulz.easydeviceinfo.base.EasyBatteryMod
import io.github.ovso.systemreport.service.model.BatteryInfo
import android.os.Build

class BatteryViewModel(var context: Context) : ViewModel() {
  var batteryMod = EasyBatteryMod(context)
  var batteryInfoLiveData = MutableLiveData<ArrayList<BatteryInfo>>()
  fun fetchList() {
    batteryInfoLiveData.value = provideBatteryInfos()
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
    infos.add(BatteryInfo("Capacity", getCapacity()))
    batteryMod.isDeviceCharging

    print("capacity = ${getBatteryCapacity(context)}")
    for (info in infos) {
      println("${info.name} = {${info.value}}")
    }
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

  fun getBatteryCapacity(ctx: Context): Long {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      val mBatteryManager = ctx.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
      val chargeCounter =
        mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
      val capacity = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

      if (chargeCounter != null && capacity != null) {
        return (chargeCounter as Float / capacity as Float * 100f).toLong()
      }
    }

    return 0
  }

  fun getCapacity(): String {

    // Power profile class instance
    var mPowerProfile_: Any? = null

    // Reset variable for battery capacity
    var batteryCapacity = 0.0

    // Power profile class name
    val POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile"

    try {

      // Get power profile class and create instance. We have to do this
      // dynamically because android.internal package is not part of public API
      mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS)
          .getConstructor(Context::class.java)
          .newInstance(this)

    } catch (e: Exception) {

      // Class not found?
      e.printStackTrace()
    }

    try {

      // Invoke PowerProfile method "getAveragePower" with param "battery.capacity"
      batteryCapacity = Class
          .forName(POWER_PROFILE_CLASS)
          .getMethod("getAveragePower", java.lang.String::class.java)
          .invoke(mPowerProfile_, "battery.capacity") as Double

    } catch (e: Exception) {

      // Something went wrong
      e.printStackTrace()
    }

    return "$batteryCapacity"
  }
}
