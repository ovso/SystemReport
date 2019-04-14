package io.github.ovso.systemreport

import android.app.Application
import io.github.ovso.systemreport.utils.AppInit

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    AppInit.timber()
    AppInit.prefs(this)
    AppInit.ad(this)
  }

}