package io.github.ovso.systemreport.utils

import io.github.ovso.systemreport.BuildConfig
import timber.log.Timber

object AppInit {

  fun timber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}