package io.github.ovso.systemreport.view.ui._base

import android.os.Build
import java.math.BigDecimal

fun Double.roundTo2DecimalPlaces() =
  BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()

fun Float.round1(): Float = Math.round(this * 10.0) / 10.0f

fun Double.round1(): Double = Math.round(this * 10.0) / 10.0

fun Float.round2(): Float = Math.round(this * 100.0) / 100.0f

fun Double.round2(): Double = Math.round(this * 100.0) / 100.0

inline fun runOnApiAbove(api: Int, f: () -> Unit) {
  if (Build.VERSION.SDK_INT > api) {
    f()
  }
}
