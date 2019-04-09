package io.github.ovso.systemreport.utils

import com.pixplicity.easyprefs.library.Prefs
import io.github.ovso.systemreport.view.ui._base.round2
import io.github.ovso.systemreport.view.ui.feature.settings.SettingsFragment

class TempFormatter {

  companion object {
    const val CELSIUS = 0
    const val FAHRENHEIT = 1
  }

  fun format(temp: Float): String {
    val tempUnit = Prefs.getString(SettingsFragment.KEY_TEMPERATURE_UNIT, CELSIUS.toString()).toInt()
    return if (tempUnit == FAHRENHEIT) {
      val fahrenheit = temp * 9 / 5 + 32
      "${fahrenheit.round2()}\u00B0F"
    } else {
      val tempFormatted = "${temp.toInt()}\u00B0C"
      tempFormatted
    }
  }
}