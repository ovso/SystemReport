package io.github.ovso.systemreport.view.ui.feature.screen

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.systemreport.service.model.NormalInfo
import io.github.ovso.systemreport.view.ui._base.round2
import io.github.ovso.systemreport.view.ui._base.roundTo2DecimalPlaces
import timber.log.Timber

class ScreenViewModel(var context: Context) : ViewModel() {
  val infoLiveData = MutableLiveData<List<NormalInfo>>()

  fun fetchList() {
    infoLiveData.value = provideInfos()
  }

  private fun provideInfos(): List<NormalInfo>? {
    val infos = ArrayList<NormalInfo>()
    infos.add(NormalInfo("Inches", getInches()))
    infos.add(NormalInfo("Density class", getDpi()))
    infos.add(NormalInfo("Screen layout", getScreenLayout()))
    infos.addAll(getInfoFromDisplayMetrics())
    return infos
  }

  private fun getScreenLayout(): String {
    val screenSize =
      context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK

    val layoutSize: String

    layoutSize = when (screenSize) {
      Configuration.SCREENLAYOUT_SIZE_LARGE -> "Large"
      Configuration.SCREENLAYOUT_SIZE_NORMAL -> "Normal"
      Configuration.SCREENLAYOUT_SIZE_SMALL -> "Small"
      else -> "Unknown"
    }

    return layoutSize
  }

  private fun getInches(): String {
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

  private fun getDensityDpi(): String {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.getDefaultDisplay()
    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    return "${metrics.xdpi.toInt()} dpi"
  }

  private fun getDpi(): String {
    val densityDpi = context.resources.displayMetrics.densityDpi

    val densityClass: String
    when (densityDpi) {
      DisplayMetrics.DENSITY_LOW -> {
        densityClass = "ldpi"
      }

      DisplayMetrics.DENSITY_MEDIUM -> {
        densityClass = "mdpi"
      }

      DisplayMetrics.DENSITY_TV, DisplayMetrics.DENSITY_HIGH -> {
        densityClass = "hdpi"
      }

      DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_280 -> {
        densityClass = "xhdpi"
      }

      DisplayMetrics.DENSITY_XXHIGH, DisplayMetrics.DENSITY_360, DisplayMetrics.DENSITY_400,
      DisplayMetrics.DENSITY_420 -> {
        densityClass = "xxhdpi"
      }

      DisplayMetrics.DENSITY_XXXHIGH, DisplayMetrics.DENSITY_560 -> {
        densityClass = "xxxhdpi"
      }

      else -> {
        densityClass = "Unknown"
      }
    }

    return densityClass
  }

  private fun getStatusBarHeight(): String {

    val res = context.resources
    val height = res.getIdentifier(
        "status_bar_height", "dimen", "android"
    )
    if (height > 0) {
      val pixel = res.getDimensionPixelSize(height);
      Timber.d("height = $height")
      return "$pixel px"
    } else {
      return "Unknown"
    }

  }

  private fun getInfoFromDisplayMetrics(): ArrayList<NormalInfo> {
    val infos = ArrayList<NormalInfo>()
    val windowManager = (context as Activity).windowManager
    val display = windowManager.defaultDisplay

    val metrics = DisplayMetrics()
    try {
      display.getRealMetrics(metrics)
      infos.add(NormalInfo("Real Width", "${metrics.widthPixels}px"))
      infos.add(NormalInfo("Real Height", "${metrics.heightPixels}px"))

      val density = metrics.density
      val dpHeight = metrics.heightPixels / density
      val dpWidth = metrics.widthPixels / density

      infos.add(NormalInfo("Dp width", "${dpWidth.toInt()} dp"))
      infos.add(NormalInfo("Dp height", "${dpHeight.toInt()} dp"))
      infos.add(NormalInfo("Density", "$density"))
      infos.add(NormalInfo("Density DPI", getDensityDpi()))
    } catch (e: Exception) {
      e.printStackTrace()
    }

    display.getMetrics(metrics)
    infos.add(
        NormalInfo("Width", "${metrics.widthPixels} px")
    )
    infos.add(
        NormalInfo("Height", "${metrics.heightPixels} px")
    )
    infos.addAll(getBarHeight())
    val refreshRate = display.refreshRate
    infos.add(NormalInfo("Refresh rate", "${refreshRate.round2()}"))

    return infos
  }

  fun getDensity(): Float {
    val windowManager = (context as Activity).windowManager
    val display = windowManager.defaultDisplay
    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    return metrics.density
  }

  fun getBarHeight(): ArrayList<NormalInfo> {

    val res = context.resources
    val infos = ArrayList<NormalInfo>()
    var statusBarHeight = 0
    val statusbarHeightResId = res.getIdentifier("status_bar_height", "dimen", "android")
    if (statusbarHeightResId > 0) {
      statusBarHeight = res.getDimensionPixelSize(statusbarHeightResId)
    }
    infos.add(
        NormalInfo(
            "Statusbar height", if (statusbarHeightResId == 0) "Unknown" else "$statusBarHeight px"
        )
    )
    if (statusBarHeight > 0) {
      infos.add(
          NormalInfo(
              "Statusbar height DP",
              if (statusbarHeightResId == 0) "Unknown" else "${(statusBarHeight / getDensity()).toInt()} dp"
          )
      )
    }
    // action bar height
    var actionBarHeight = 0
    val styledAttributes = (context as Activity).getTheme()
        .obtainStyledAttributes(
            intArrayOf(android.R.attr.actionBarSize)
        )
    actionBarHeight = styledAttributes.getDimension(0, 0f)
        .toInt()
    styledAttributes.recycle()

    infos.add(
        NormalInfo(
            "Actionbar height", "$actionBarHeight px"
        )
    )
    if (actionBarHeight > 0) {
      infos.add(
          NormalInfo(
              "Actionbar height dp", "${(actionBarHeight / getDensity()).toInt()} dp"
          )
      )
    }

    // navigation bar height
    var navigationBarHeight = 0
    val navbarHeightResId = res.getIdentifier("navigation_bar_height", "dimen", "android")
    if (navbarHeightResId > 0) {
      navigationBarHeight = res.getDimensionPixelSize(navbarHeightResId)
    }

    infos.add(
        NormalInfo(
            "Navigationbar height",
            if (navbarHeightResId == 0) "Unknown" else "$navigationBarHeight px"
        )
    )
    if (navigationBarHeight > 0) {
      infos.add(
          NormalInfo(
              "Navigationbar height dp",
              if (navbarHeightResId == 0) "Unknown" else "${(navigationBarHeight / getDensity()).toInt()} dp"
          )
      )
    }

    return infos
  }

}
