package io.github.ovso.systemreport.view.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.systemreport.viewmodels.MainViewModel

class MainPagerAdapter(
  private var viewModel: MainViewModel
) : RecyclerView.Adapter<MainPagerViewHolder>() {
  var items = arrayListOf<String>()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): MainPagerViewHolder {
    return MainPagerViewHolder.create(parent)
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(
    holder: MainPagerViewHolder,
    position: Int
  ) {
    holder.bind(position)
  }
}