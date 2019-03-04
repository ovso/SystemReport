package io.github.ovso.systemreport.view.ui.main.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.systemreport.R

class BatteryAdapter : RecyclerView.Adapter<BatteryViewHolder>() {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BatteryViewHolder {
    TODO("not implemented")
  }

  override fun getItemCount(): Int {
    TODO("not implemented")
  }

  override fun onBindViewHolder(
    holder: BatteryViewHolder,
    position: Int
  ) {
    TODO("not implemented")
  }

  class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
      fun create(parent: ViewGroup): MyViewHolder {
        var inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_battery, parent, false)
        return MyViewHolder(parent)
      }
    }
  }

}
