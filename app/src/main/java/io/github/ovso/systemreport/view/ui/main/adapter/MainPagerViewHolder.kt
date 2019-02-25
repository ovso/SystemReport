package io.github.ovso.systemreport.view.ui.main.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.systemreport.R

class MainPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  fun bind(position: Int) {
    //Timber.d("bind($position)")
    if (position == 0) {
      println("SERIAL: " + Build.SERIAL);
      println("MODEL: " + Build.MODEL);
      println("ID: " + Build.ID);
      println("Manufacture: " + Build.MANUFACTURER);
      println("brand: " + Build.BRAND);
      println("type: " + Build.TYPE);
      println("user: " + Build.USER);
      println("BASE: " + Build.VERSION_CODES.BASE);
      println("INCREMENTAL " + Build.VERSION.INCREMENTAL);
      println("SDK  " + Build.VERSION.SDK);
      println("BOARD: " + Build.BOARD);
      println("BRAND " + Build.BRAND);
      println("HOST " + Build.HOST);
      println("FINGERPRINT: " + Build.FINGERPRINT);
      println("Version Code: " + Build.VERSION.RELEASE);

    }
  }

  companion object {
    fun create(parent: ViewGroup): MainPagerViewHolder {
      var view: View = LayoutInflater.from(parent.context)
          .inflate(R.layout.item_main_viewpager, parent, false)
      return MainPagerViewHolder(view)
    }
  }
}