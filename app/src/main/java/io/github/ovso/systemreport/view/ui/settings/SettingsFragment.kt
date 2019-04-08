package io.github.ovso.systemreport.view.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceClickListener
import androidx.preference.PreferenceFragmentCompat
import de.psdev.licensesdialog.LicensesDialog
import io.github.ovso.systemreport.R

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener, OnPreferenceClickListener {
  override fun onPreferenceClick(preference: Preference?): Boolean {
    LicensesDialog.Builder(activity!!)
        .setNotices(io.github.ovso.systemreport.R.raw.notices)
        .build()
        .show()
    return true
  }

  private lateinit var tempUnitPreference: ListPreference

  companion object {
    fun newInstance() = SettingsFragment()
    const val KEY_TEMPERATURE_UNIT = "temperature_unit"
    const val KEY_OPENSOURCE = "opensource"
  }

  override fun onSharedPreferenceChanged(
    sharedPreferences: SharedPreferences?,
    key: String?
  ) {
    when (key) {
      KEY_TEMPERATURE_UNIT ->
        tempUnitPreference.summary = tempUnitPreference.entry.toString()
    }
  }

  override fun onCreatePreferences(
    savedInstanceState: Bundle?,
    rootKey: String?
  ) {
    addPreferencesFromResource(R.xml.preferences)

    tempUnitPreference = preferenceScreen.findPreference(KEY_TEMPERATURE_UNIT) as ListPreference
    (preferenceScreen.findPreference(KEY_OPENSOURCE) as Preference).onPreferenceClickListener = this
  }

  override fun onResume() {
    super.onResume()
    tempUnitPreference.summary = tempUnitPreference.entry.toString()
    preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
  }

  override fun onPause() {
    super.onPause()
    preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = super.onCreateView(inflater, container, savedInstanceState)
    view?.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.black))
    return view
  }
}