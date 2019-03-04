package io.github.ovso.systemreport.view.ui.main.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.systemreport.R

class BatteryViewHolder(private var itemView: View) : RecyclerView.ViewHolder(itemView) {
  companion object {
    fun create(parent: ViewGroup): BatteryViewHolder {
      var inflate = LayoutInflater.from(parent.context)
          .inflate(R.layout.item_battery, parent, false)
      return BatteryViewHolder(inflate)
    }
  }
}