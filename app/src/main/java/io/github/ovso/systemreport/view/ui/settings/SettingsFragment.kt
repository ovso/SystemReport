package io.github.ovso.systemreport.view.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
  override fun onSharedPreferenceChanged(
    sharedPreferences: SharedPreferences?,
    key: String?
  ) {
  }

  override fun onCreatePreferences(
    savedInstanceState: Bundle?,
    rootKey: String?
  ) {

  }
}