package io.github.ovso.systemreport.view.ui.main

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import github.nisrulz.easydeviceinfo.base.EasyCpuMod
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod
import io.github.ovso.systemreport.R
import io.github.ovso.systemreport.R.id
import io.github.ovso.systemreport.R.layout
import io.github.ovso.systemreport.R.string
import io.github.ovso.systemreport.databinding.ActivityMainBinding
import io.github.ovso.systemreport.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.tablayout_main
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
  private var viewModel: MainViewModel? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupBinding(savedInstanceState)
    test()
  }

  private fun test() {

//    println("SERIAL: " + Build.SERIAL);
//    println("MODEL: " + Build.MODEL);
//    println("ID: " + Build.ID);
//    println("Manufacture: " + Build.MANUFACTURER);
//    println("brand: " + Build.BRAND);
//    println("type: " + Build.TYPE);
//    println("user: " + Build.USER);
//    println("BASE: " + Build.VERSION_CODES.BASE);
//    println("INCREMENTAL " + Build.VERSION.INCREMENTAL);
//    println("SDK  " + Build.VERSION.SDK);
//    println("BOARD: " + Build.BOARD);
//    println("BRAND " + Build.BRAND);
//    println("HOST " + Build.HOST);
//    println("FINGERPRINT: " + Build.FINGERPRINT);
//    println("Version Code: " + Build.VERSION.RELEASE);
//
//    var easyCpuMod = EasyCpuMod()
//    println(easyCpuMod.stringSupported32bitABIS)
//    var easyDeviceMod = EasyDeviceMod(this)
//    println(EasyDeviceMod(this).model)

    println(getCPUDetails())
  }

  private fun setupViewModel() {
    viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        .create(MainViewModel::class.java)
  }

  private fun setupNavigation() {
    nav_view.setNavigationItemSelectedListener(this)
  }

  private fun setupDrawerLayout() {
    val toggle = ActionBarDrawerToggle(
        this, drawer_layout, toolbar, string.navigation_drawer_open,
        string.navigation_drawer_close
    )
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()
  }

  private fun setupToolbar() {
    setSupportActionBar(toolbar)
  }

  private fun setupBinding(savedInstanceState: Bundle?) {
    setupViewModel()
    var binding: ActivityMainBinding = DataBindingUtil.setContentView(
        this,
        layout.activity_main
    )
    binding.viewModel = viewModel

    setupToolbar()
    setupDrawerLayout()
    setupNavigation()

    updatePagerList()
  }

  private fun updatePagerList() {
    viewModel?.itemsLiveData?.observe(this, Observer {
      viewModel!!.setItems(it)
    })
    viewModel?.titlesLiveData?.observe(this, Observer {
      for (title in it) {
        var newTab = tablayout_main.newTab()
        newTab.text = title
        tablayout_main.addTab(newTab)
      }
    })
    viewModel?.fetchList()
  }

  override fun onBackPressed() {
    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
      drawer_layout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    when (item.itemId) {
      id.action_settings -> return true
      else -> return super.onOptionsItemSelected(item)
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    when (item.itemId) {
      id.nav_camera -> {
        // Handle the camera action
      }
      id.nav_gallery -> {

      }
      id.nav_slideshow -> {

      }
      id.nav_manage -> {

      }
      id.nav_share -> {

      }
      id.nav_send -> {

      }
    }

    drawer_layout.closeDrawer(GravityCompat.START)
    return true
  }

  fun getCPUDetails(): String {

    /*
     *Created By Atiar Talukdar
     * 01/01/2018
     * contact@atiar.info
     */

    val processBuilder: ProcessBuilder
    var cpuDetails = ""
    val DATA = arrayOf("/system/bin/cat", "/proc/cpuinfo")
    val `is`: InputStream
    val process: Process
    val bArray: ByteArray
    bArray = ByteArray(1024)

    try {
      processBuilder = ProcessBuilder(*DATA)

      process = processBuilder.start()

      `is` = process.inputStream

      while (`is`.read(bArray) !== -1) {
        cpuDetails = cpuDetails + String(bArray)   //Stroing all the details in cpuDetails
      }
      `is`.close()

    } catch (ex: IOException) {
      ex.printStackTrace()
    }

    return cpuDetails
  }

}