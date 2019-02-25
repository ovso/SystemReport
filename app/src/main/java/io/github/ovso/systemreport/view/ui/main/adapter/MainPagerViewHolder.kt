package io.github.ovso.systemreport.view.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.systemreport.R
import timber.log.Timber

class MainPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(position: Int) {
    //Timber.d("bind($position)")
  }

  companion object {
    fun create(parent: ViewGroup): MainPagerViewHolder {
      var view: View = LayoutInflater.from(parent.context)
          .inflate(R.layout.item_main_viewpager, parent, false)
      return MainPagerViewHolder(view)
    }
  }
}