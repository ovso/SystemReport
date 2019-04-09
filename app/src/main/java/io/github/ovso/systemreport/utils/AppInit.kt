package io.github.ovso.systemreport.utils

import android.content.Context
import android.content.ContextWrapper
import com.pixplicity.easyprefs.library.Prefs
import io.github.ovso.systemreport.BuildConfig
import timber.log.Timber

object AppInit {

  fun timber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

  fun prefs(context: Context) {
    Prefs.Builder()
        .setContext(context)
        .setMode(ContextWrapper.MODE_PRIVATE)
        .setPrefsName(context.packageName)
        .setUseDefaultSharedPreference(true)
        .build()
  }
}