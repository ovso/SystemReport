package io.github.ovso.systemreport.view.ui.main

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.navigation.NavigationView
import io.github.ovso.systemreport.R
import io.github.ovso.systemreport.R.id
import io.github.ovso.systemreport.R.layout
import io.github.ovso.systemreport.R.string
import io.github.ovso.systemreport.databinding.ActivityMainBinding
import io.github.ovso.systemreport.view.ui.main.views.BatteryFragment
import io.github.ovso.systemreport.view.ui.main.views.DeviceFragment
import io.github.ovso.systemreport.view.ui.main.views.SensorsFragment
import io.github.ovso.systemreport.view.ui.main.views.SocFragment
import io.github.ovso.systemreport.view.ui.main.views.SystemFragment
import io.github.ovso.systemreport.view.ui.main.views.ThermalFragment
import io.github.ovso.systemreport.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.tablayout_main
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import kotlinx.android.synthetic.main.content_main.viewpager_main
import java.io.File
import java.util.Scanner
import kotlin.collections.set

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
  private var viewModel: MainViewModel? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupBinding(savedInstanceState)
  }

  @Suppress("UNCHECKED_CAST")
  private fun setupViewModel() {
    viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
      }
    })
        .get(MainViewModel::class.java)

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
    setupViewPager()
    setupToolbar()
    setupDrawerLayout()
    setupNavigation()

    updatePagerList()
  }

  private fun setupViewPager() {
    viewpager_main.adapter = provideAdapter()
    tablayout_main.setupWithViewPager(viewpager_main)
  }

  private fun provideAdapter(): PagerAdapter {
    var fragments = provideFragments()
    var adapter = MainPagerAdapter(supportFragmentManager)
    adapter!!.items = fragments
    adapter!!.titles = provideTitles(fragments.size);
    return adapter
  }

  private fun provideTitles(size: Int): ArrayList<String> {
    val titles = ArrayList<String>()
    for (i in IntRange(0, size - 1)) {
      var title = resources.getStringArray(R.array.info_type)
          .get(i)
      titles.add(title)
    }
    return titles
  }

  fun provideFragments(): ArrayList<Fragment> {
    var fragments = ArrayList<Fragment>()
    fragments.add(SocFragment.newInstance())
    fragments.add(DeviceFragment.newInstance())
    fragments.add(SystemFragment.newInstance())
    fragments.add(BatteryFragment.newInstance())
    fragments.add(SensorsFragment.newInstance())
    fragments.add(ThermalFragment.newInstance())
    return fragments
  }

  private fun updatePagerList() {
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

  fun getCpuInfoMap(): Map<String, String> {
    val map = HashMap<String, String>()
    try {
      val s = Scanner(File("/proc/cpuinfo"))
      while (s.hasNextLine()) {
        val vals = s.nextLine()
            .split(": ")
        if (vals.size > 1) {
          map[vals[0].trim({ it <= ' ' })] = vals[1].trim({ it <= ' ' })
        }
      }
    } catch (e: Exception) {
      //Log.e("getCpuInfoMap", Log.getStackTraceString(e))
    }

    return map
  }

  fun printDeviceInfo() {
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