package io.github.ovso.systemreport.view.ui.main.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.systemreport.R
import io.github.ovso.systemreport.service.model.BatteryInfo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_battery.textview_batteryviewholder_name
import kotlinx.android.synthetic.main.item_battery.textview_batteryviewholder_value

class SocViewHolder(
  override val containerView: View?
) : RecyclerView.ViewHolder(containerView!!), LayoutContainer {

  fun bind(item: BatteryInfo) {
    textview_batteryviewholder_name.text = item.name
    textview_batteryviewholder_value.text = item.value
  }

  companion object {
    fun create(parent: ViewGroup): SocViewHolder {
      var view = LayoutInflater.from(parent.context)
          .inflate(R.layout.item_battery, parent, false)
      return SocViewHolder(view)
    }
  }
}