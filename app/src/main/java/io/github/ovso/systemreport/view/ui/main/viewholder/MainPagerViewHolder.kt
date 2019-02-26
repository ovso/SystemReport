package io.github.ovso.systemreport.view.ui.main.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.systemreport.R

class MainPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(position: Int) {
  }

  companion object {
    fun create(parent: ViewGroup): MainPagerViewHolder {
      var view: View = LayoutInflater.from(parent.context)
          .inflate(R.layout.item_main_viewpager, parent, false)
      return MainPagerViewHolder(view)
    }
  }
}