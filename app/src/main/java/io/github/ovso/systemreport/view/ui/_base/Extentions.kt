package io.github.ovso.systemreport.view.ui._base

import java.math.BigDecimal

fun Double.roundTo2DecimalPlaces() =
  BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()

fun Float.round2(): Float = Math.round(this * 100.0) / 100.0f