package io.github.ovso.systemreport.view.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import io.github.ovso.systemreport.R

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

  companion object {
    fun newInstance() = SettingsFragment()
  }

  override fun onSharedPreferenceChanged(
    sharedPreferences: SharedPreferences?,
    key: String?
  ) {
  }

  override fun onCreatePreferences(
    savedInstanceState: Bundle?,
    rootKey: String?
  ) {
    addPreferencesFromResource(R.xml.preferences)
  }
}