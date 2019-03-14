package io.github.ovso.systemreport.view.ui.main.views.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.systemreport.service.model.SocInfo

class SocAdapter : RecyclerView.Adapter<SocViewHolder>() {

  var items = ArrayList<SocInfo>()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): SocViewHolder {
    return SocViewHolder.create(parent);
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(
    holder: SocViewHolder,
    position: Int
  ) {
    holder.bind(getItem(position))
  }

  private fun getItem(position: Int) = items.get(position)

  fun addItems(it: ArrayList<SocInfo>) {
    this.items.clear()
    this.items.addAll(it)
  }

}
