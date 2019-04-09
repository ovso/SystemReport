package io.github.ovso.systemreport.view.ui._base

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.github.ovso.systemreport.R
import io.github.ovso.systemreport.service.model.NormalInfo
import io.github.ovso.systemreport.view.ui._base.NormalAdapter.NormalViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_all.textview_allviewholder_name
import kotlinx.android.synthetic.main.item_all.textview_allviewholder_value

class NormalAdapter : RecyclerView.Adapter<NormalViewHolder>() {

  val items = ArrayList<NormalInfo>()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): NormalViewHolder {
    return NormalViewHolder.create(parent);
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(
    holder: NormalViewHolder,
    position: Int
  ) {
    holder.bind(getItem(position))
  }

  private fun getItem(position: Int) = items.get(position)

  fun addItems(it: ArrayList<NormalInfo>) {
    this.items.clear()
    this.items.addAll(it)
  }

  class NormalViewHolder(
    override val containerView: View?
  ) : RecyclerView.ViewHolder(containerView!!), LayoutContainer {

    fun bind(item: NormalInfo) {
      textview_allviewholder_name.text = item.name
      textview_allviewholder_name.isSelected = true
      textview_allviewholder_value.text = item.value
      textview_allviewholder_value.isSelected = true
      itemView.setOnLongClickListener {
        val context = it.context
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE)
            as ClipboardManager
        val clip = ClipData.newPlainText(
            context.getString(R.string.app_name),
            item.value
        )
        clipboard.primaryClip = clip
        Snackbar.make(itemView,"복사했어요.",Snackbar.LENGTH_SHORT).show()
        true
      }
    }

    companion object {
      fun create(parent: ViewGroup): NormalViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_all, parent, false)
        return NormalViewHolder(view)
      }
    }
  }

}