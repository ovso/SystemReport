package io.github.ovso.systemreport.view.ui.main.views

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import github.nisrulz.easydeviceinfo.base.ChargingVia
import github.nisrulz.easydeviceinfo.base.EasyBatteryMod
import io.github.ovso.systemreport.R
import io.github.ovso.systemreport.viewmodels.fragment.BatteryViewModel
import timber.log.Timber

class BatteryFragment : Fragment() {

  companion object {
    fun newInstance() = BatteryFragment()
  }

  private lateinit var viewModel: BatteryViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_battery, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupViewModel()
    updateList()
    //output()
  }

  @Suppress("UNCHECKED_CAST")
  private fun setupViewModel() {
    viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        BatteryViewModel(context!!) as T
    })
        .get(BatteryViewModel::class.java)
  }

  private fun updateList() {

    viewModel.fetchList()
  }

  @SuppressLint("WrongConstant")
  private fun output() {
    var battery = EasyBatteryMod(context);
    var infos = arrayListOf<String>()
    infos.add("batteryHealth = ${battery.batteryHealth}")
    infos.add("batteryPercentage = ${battery.batteryPercentage}")
    infos.add("batteryTechnology = ${battery.batteryTechnology}")
    infos.add("batteryTemperature = ${battery.batteryTemperature}")
    infos.add("batteryVoltage = ${battery.batteryVoltage}")
    infos.add("chargingSource = ${battery.chargingSource}")
    for (info in infos) {
      Timber.d("info = %s", info)
      var view = TextView(context)
      view.setText(info)
      view.layoutParams = LayoutParams(
          LayoutParams.MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.all_text_item_56)
      )
    }
  }

  enum class ChargingPlug(var source: Int) {
    AC(ChargingVia.AC),
    USB(ChargingVia.USB),
    WIRELESS(ChargingVia.WIRELESS),
    UNKNOWN(ChargingVia.UNKNOWN_SOURCE);

    companion object {
      fun fromSource(source: Int): ChargingPlug {
        for (value in values()) {
          if (value.source == source) {
            return value
          }
        }
        return UNKNOWN
      }

    }
  }

}
