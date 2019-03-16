package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod
import io.github.ovso.systemreport.service.model.NormalInfo
import timber.log.Timber
import java.math.BigDecimal

class DeviceViewModel(var context: Context) : ViewModel() {
  val infoLiveData = MutableLiveData<List<NormalInfo>>()
  private val easyDeviceMod = EasyDeviceMod(context)

  fun fetchList() {
    infoLiveData.value = provideInfos()
  }

  private fun provideInfos(): List<NormalInfo>? {
    val infos = ArrayList<NormalInfo>()
    infos.add(NormalInfo("Model", easyDeviceMod.model))
    infos.add(NormalInfo("Manufacturer", easyDeviceMod.manufacturer))
    infos.add(NormalInfo("Brand", easyDeviceMod.buildBrand))
    infos.add(NormalInfo("Hardware", easyDeviceMod.hardware))
    infos.add(NormalInfo("Board", easyDeviceMod.board))
    infos.add(NormalInfo("Display ID", easyDeviceMod.screenDisplayID))
    infos.add(NormalInfo("Fingerprint", easyDeviceMod.fingerprint))
    infos.add(NormalInfo("Build brand", easyDeviceMod.buildBrand))
    infos.add(NormalInfo("Build host", easyDeviceMod.buildHost))
    infos.add(NormalInfo("buildVersionCodename", easyDeviceMod.buildVersionCodename))
    infos.add(NormalInfo("buildVersionIncremental", easyDeviceMod.buildVersionIncremental))
    infos.add(NormalInfo("buildVersionRelease", easyDeviceMod.buildVersionRelease))
    infos.add(NormalInfo("device", easyDeviceMod.device))
    infos.add(NormalInfo("displayVersion", easyDeviceMod.displayVersion))
    infos.add(NormalInfo("language", easyDeviceMod.language))
    infos.add(NormalInfo("osCodename", easyDeviceMod.osCodename))
    infos.add(NormalInfo("osVersion", easyDeviceMod.osVersion))
    infos.add(NormalInfo("product", easyDeviceMod.product))
    infos.add(NormalInfo("radioVer", easyDeviceMod.radioVer))
    infos.add(NormalInfo("serial", easyDeviceMod.serial))
    infos.add(NormalInfo("buildTime", easyDeviceMod.buildTime.toString()))
    //infos.add(NormalInfo("Screen inches", getScreenInches()))
    infos.add(NormalInfo("Resolution", getResolution()))
    return infos
  }

  private fun getResolution(): String {
    var windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val d = windowManager.getDefaultDisplay()
    val metrics = DisplayMetrics()
    d.getMetrics(metrics)
    var widthPixels = metrics.widthPixels
    var heightPixels = metrics.heightPixels
    try {
      val realSize = Point()
      Display::class.java.getMethod("getRealSize", Point::class.java)
          .invoke(d, realSize)
      widthPixels = realSize.x
      heightPixels = realSize.y
    } catch (e: Exception) {
      Timber.e(e)
    }

    val x = Math.pow(widthPixels.toDouble() / metrics.xdpi, 2.0)
    val y = Math.pow(heightPixels.toDouble() / metrics.ydpi, 2.0)
    val screenInches = Math.sqrt(x + y)
    var roundTo2DecimalPlaces = screenInches.roundTo2DecimalPlaces()

    return "$widthPixels x $heightPixels ($roundTo2DecimalPlaces inches)"
  }

  fun Double.roundTo2DecimalPlaces() =
    BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()

}
