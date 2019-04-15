package io.github.ovso.systemreport.utils

import io.github.ovso.systemreport.BuildConfig

enum class Ads(val value: String) {
  ADMOB_APP_ID(
      if (BuildConfig.DEBUG) "ca-app-pub-3940256099942544~3347511713" else "ca-app-pub-8679189423397017~5847805742"
  ),
  ADMOB_BANNER_UNIT_ID(
      if (BuildConfig.DEBUG) "ca-app-pub-3940256099942544/6300978111" else "ca-app-pub-8679189423397017/8581780296"
  ),
  ADMOB_INTERSTITIAL_ID(
      if (BuildConfig.DEBUG) "ca-app-pub-3940256099942544/1033173712" else "ca-app-pub-8679189423397017/1866352229"
  )
}