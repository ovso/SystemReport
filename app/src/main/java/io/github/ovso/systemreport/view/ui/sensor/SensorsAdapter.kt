package io.github.ovso.systemreport.view.ui.sensor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.systemreport.R
import io.github.ovso.systemreport.service.model.NormalInfo
import io.github.ovso.systemreport.view.ui.sensor.SensorsAdapter.MyViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_sensors.textview_sensorsitem_name
import kotlinx.android.synthetic.main.item_sensors.textview_sensorsitem_value

class SensorsAdapter : RecyclerView.Adapter<MyViewHolder>() {
  val items = ArrayList<NormalInfo>()
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): MyViewHolder = MyViewHolder.create(parent)

  override fun getItemCount() = items.size
  override fun onBindViewHolder(
    holder: MyViewHolder,
    position: Int
  ) {
    holder.bind(items[position])
  }

  class MyViewHolder(
    override val containerView: View?
  ) : RecyclerView.ViewHolder(containerView!!), LayoutContainer {

    fun bind(item: NormalInfo) {
      textview_sensorsitem_name.text = item.name
      textview_sensorsitem_value.text = item.value
    }

    companion object {
      fun create(parent: ViewGroup): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sensors, parent, false)
        )
    }
  }
}