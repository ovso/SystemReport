package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import android.graphics.Point
import android.os.Environment
import android.os.StatFs
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod
import github.nisrulz.easydeviceinfo.base.EasyMemoryMod
import io.github.ovso.systemreport.service.model.NormalInfo
import timber.log.Timber
import java.math.BigDecimal
import java.util.Locale

class DeviceViewModel(var context: Context) : ViewModel() {
  val infoLiveData = MutableLiveData<List<NormalInfo>>()
  private val easyDeviceMod = EasyDeviceMod(context)

  fun fetchList() {
    infoLiveData.value = provideInfos()
    storage()
  }

  private fun storage() {

    val stat = StatFs(Environment.getExternalStorageDirectory().getPath())
    val bytesAvailable: Long
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
      bytesAvailable = stat.blockSizeLong * stat.availableBlocksLong
    } else {
      bytesAvailable = stat.blockSize.toLong() * stat.availableBlocks.toLong()
    }
    val megAvailable = bytesAvailable / (1024 * 1024)
    Timber.e("Available MB : $megAvailable")
  }

  private fun provideInfos(): List<NormalInfo>? {
    var easyMemoryMod = EasyMemoryMod(context)

    Timber.d(easyMemoryMod.convertToGb(easyMemoryMod.availableExternalMemorySize).toString())
    Timber.d(easyMemoryMod.convertToGb(easyMemoryMod.availableInternalMemorySize).toString())
    Timber.d(easyMemoryMod.convertToGb(easyMemoryMod.totalExternalMemorySize).toString())
    Timber.d(easyMemoryMod.convertToGb(easyMemoryMod.totalInternalMemorySize).toString())
    Timber.d(easyMemoryMod.convertToGb(easyMemoryMod.totalRAM).toString())

    val infos = ArrayList<NormalInfo>()
    infos.add(NormalInfo("Model", easyDeviceMod.model))
    infos.add(NormalInfo("Manufacturer", easyDeviceMod.manufacturer))
    infos.add(NormalInfo("Brand", easyDeviceMod.buildBrand))
    infos.add(NormalInfo("Hardware", easyDeviceMod.hardware))
    infos.add(NormalInfo("Board", easyDeviceMod.board))
    infos.add(NormalInfo("Language", Locale.getDefault().getDisplayLanguage()))
    infos.add(NormalInfo("Resolution", getResolution()))
    infos.add(NormalInfo("Screen size", getScreenSize()))
    infos.add(NormalInfo("Screen density", getDensity()))
    infos.add(NormalInfo("Internal storage", getInternalStorage()))
    infos.add(NormalInfo("Available storage", getAvailableStorage()))
    return infos
  }

  private fun getAvailableStorage(): String {
    val easyMemoryMod = EasyMemoryMod(context)
    val gb = easyMemoryMod.convertToGb(easyMemoryMod.availableInternalMemorySize)
    return gb.toDouble().roundTo2DecimalPlaces().toString() + " GB"
  }

  private fun getInternalStorage(): String {
    val easyMemoryMod = EasyMemoryMod(context)
    val gb = easyMemoryMod.convertToGb(easyMemoryMod.totalInternalMemorySize)
    return gb.toDouble().roundTo2DecimalPlaces().toString() + " GB"
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
