package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod
import io.github.ovso.systemreport.service.model.NormalInfo
import timber.log.Timber
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.logging.SimpleFormatter

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
    infos.add(NormalInfo("Language", Locale.getDefault().getDisplayLanguage()))
    infos.add(NormalInfo("Build time", getBuildTime()))
    infos.add(NormalInfo("Resolution", getResolution()))
    infos.add(NormalInfo("Screen size", getScreenSize()))
    infos.add(NormalInfo("Screen density", getDensity()))
    return infos
  }

  private fun getBuildTime(): String {
    val buildTime = easyDeviceMod.buildTime
    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return simpleDateFormat.format(Date(buildTime))
  }

  private fun getResolution(): String {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.getDefaultDisplay()
    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    val outSize = Point()
    display.getRealSize(outSize);
    val widthPixels = outSize.x
    val heightPixels = outSize.y
    return "$widthPixels x $heightPixels"
  }

  private fun getScreenSize(): String {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.getDefaultDisplay()
    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    val outSize = Point()
    display.getRealSize(outSize);
    val widthPixels = outSize.x
    val heightPixels = outSize.y
    val x = Math.pow(widthPixels.toDouble() / metrics.xdpi, 2.0)
    val y = Math.pow(heightPixels.toDouble() / metrics.ydpi, 2.0)
    val screenInches = Math.sqrt(x + y)
    val roundTo2DecimalPlaces = screenInches.roundTo2DecimalPlaces()

    return "$roundTo2DecimalPlaces inches"
  }

  private fun getDensity(): String {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.getDefaultDisplay()
    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    return "${metrics.xdpi.toInt()} dpi"
  }

  fun Double.roundTo2DecimalPlaces() =
    BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()

}
