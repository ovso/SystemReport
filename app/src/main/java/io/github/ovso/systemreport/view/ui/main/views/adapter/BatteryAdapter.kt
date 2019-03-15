package io.github.ovso.systemreport.view.ui.main.views.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.systemreport.service.model.BatteryInfo

class BatteryAdapter : RecyclerView.Adapter<AllViewHolder>() {

  var items = ArrayList<BatteryInfo>()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): AllViewHolder {
    return AllViewHolder.create(parent);
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(
    holder: AllViewHolder,
    position: Int
  ) {
    holder.bind(getItem(position))
  }

  private fun getItem(position: Int) = items.get(position)

  fun addItems(it: ArrayList<BatteryInfo>) {
    this.items.clear()
    this.items.addAll(it)
  }

}
