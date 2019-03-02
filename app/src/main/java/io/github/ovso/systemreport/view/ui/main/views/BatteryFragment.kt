package io.github.ovso.systemreport.view.ui.main.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import github.nisrulz.easydeviceinfo.base.ChargingVia
import github.nisrulz.easydeviceinfo.base.EasyBatteryMod
import io.github.ovso.systemreport.R
import io.github.ovso.systemreport.viewmodels.fragment.BatteryViewModel
import kotlinx.android.synthetic.main.fragment_battery.linearlayout_battery_itemcontainer
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
    viewModel = ViewModelProviders.of(this)
        .get(BatteryViewModel::class.java)
    // TODO: Use the ViewModel

    output()
  }

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
      linearlayout_battery_itemcontainer.addView(view)
    }
  }

  enum class ChargingSource(var source: Int) {
    AC(ChargingVia.AC),
    USB(ChargingVia.USB),
    WIRELESS(ChargingVia.WIRELESS),
    UNKNOWN(ChargingVia.UNKNOWN_SOURCE);

    companion object {
      fun toPlug(source: Int): ChargingSource {
        for (value in values()) {
          if (value.source == source) {
            return value
          }
        }
        return UNKNOWN
      }

    }
  }

  /*
    @ChargingVia
  public final int getChargingSource() {
    Intent batteryStatus = getBatteryStatusIntent();
    int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);

    switch (chargePlug) {
      case BatteryManager.BATTERY_PLUGGED_AC:
        return ChargingVia.AC;
      case BatteryManager.BATTERY_PLUGGED_USB:
        return ChargingVia.USB;
      case BatteryManager.BATTERY_PLUGGED_WIRELESS:
        return ChargingVia.WIRELESS;
      default:
        return ChargingVia.UNKNOWN_SOURCE;
    }
  }

   */
}
