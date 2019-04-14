package io.github.ovso.systemreport.utils

import io.github.ovso.systemreport.BuildConfig

enum class AdsId(val id: String) {
  ADMOB_APP(
      if (BuildConfig.DEBUG) "ca-app-pub-3940256099942544~3347511713" else "ca-app-pub-8679189423397017~5847805742"
  ),
  ADMOB_UNIT(
      if (BuildConfig.DEBUG) "ca-app-pub-3940256099942544/6300978111" else "ca-app-pub-8679189423397017/8581780296"
  ),
  ADMOB_INTERSTITIAL(
      if (BuildConfig.DEBUG) "ca-app-pub-3940256099942544/1033173712" else "ca-app-pub-8679189423397017/1866352229"
  )
}