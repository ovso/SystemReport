package io.github.ovso.systemreport.view.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.InterstitialAd
import com.google.android.material.navigation.NavigationView
import io.github.ovso.systemreport.BuildConfig
import io.github.ovso.systemreport.R
import io.github.ovso.systemreport.databinding.ActivityMainBinding
import io.github.ovso.systemreport.view.ui.feature.battery.BatteryFragment
import io.github.ovso.systemreport.view.ui.feature.screen.ScreenFragment
import io.github.ovso.systemreport.view.ui.feature.settings.SettingsFragment
import io.github.ovso.systemreport.view.ui.feature.system.SystemFragment
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.framelayout_adcontainer
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import timber.log.Timber

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
  private lateinit var interstitialAd: InterstitialAd

  fun reloadInterstitialAd() {
    interstitialAd = MyAdView.getAdmobInterstitialAd(applicationContext)
  }

  private var viewModel: MainViewModel? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    interstitialAd = MyAdView.getAdmobInterstitialAd(applicationContext)
    setupBinding(savedInstanceState)
  }

  @Suppress("UNCHECKED_CAST")
  private fun setupViewModel() {
    viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(applicationContext) as T
      }
    })
        .get(MainViewModel::class.java)

  }

  private fun setupNavigation() {
    nav_view.setNavigationItemSelectedListener(this)
    val item = nav_view.menu.getItem(0)
    onNavigationItemSelected(item)
    item.isChecked = true
  }

  private fun setupDrawerLayout() {
    val toggle = ActionBarDrawerToggle(
        this, drawer_layout, toolbar, R.string.navigation_drawer_open,
        R.string.navigation_drawer_close
    )
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()
  }

  private fun setupToolbar() {
    setSupportActionBar(toolbar)
  }

  private fun setupBinding(savedInstanceState: Bundle?) {
    setupViewModel()
    val binding: ActivityMainBinding = DataBindingUtil.setContentView(
        this,
        R.layout.activity_main
    )

    if (savedInstanceState == null) {
      binding.viewModel = viewModel
    }

    setupToolbar()
    setupDrawerLayout()
    setupNavigation()
    showBannerAd()
  }

  private fun showBannerAd() {
    framelayout_adcontainer.addView(MyAdView.getAdmobBannerView(applicationContext))
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
    return when (item.itemId) {
      R.id.action_settings -> true
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    Timber.d("item check = ${item.isChecked}")
    when (item.itemId) {
      R.id.nav_screen -> {
        if (item.isChecked.not()) showScreenFragment()
      }
      R.id.nav_battery -> {
        if (item.isChecked.not()) showBatteryFragment()
      }
      R.id.nav_system -> {
        if (item.isChecked.not()) showSystemFragment()
      }
      R.id.nav_settings -> {
        if (item.isChecked.not()) showSettingsFragment()
      }
      R.id.nav_share -> {
        navigateToShare()
      }
      R.id.nav_review -> {
        navigateToReview()
      }
    }

    drawer_layout.closeDrawer(GravityCompat.START)
    return true
  }

  private fun navigateToReview() {
    try {
      startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?value=$packageName")))
    } catch (anfe: android.content.ActivityNotFoundException) {
      startActivity(
          Intent(
              Intent.ACTION_VIEW,
              Uri.parse("https://play.google.com/store/apps/details?value=$packageName")
          )
      )
    }

  }

  private fun navigateToShare() {
    val intent = Intent(Intent.ACTION_SEND)
    intent.addCategory(Intent.CATEGORY_DEFAULT)
    intent.putExtra(Intent.EXTRA_TITLE, "Share")
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, "market://details?value=$packageName")
    startActivity(Intent.createChooser(intent, "App share"))
  }

  private fun showSettingsFragment() {
    fun show() {
      supportFragmentManager.beginTransaction()
          .replace(R.id.framelayout_main_fcontainer, SettingsFragment.newInstance())
          .commitNow()
      reloadInterstitialAd()
    }
    if (interstitialAd.isLoaded) {
      interstitialAd.show()
      interstitialAd.adListener = object : AdListener() {
        override fun onAdClosed() {
          show()
        }
      }
    } else {
      show()
    }

  }

  private fun showScreenFragment() {
    fun show() {
      supportFragmentManager.beginTransaction()
          .replace(R.id.framelayout_main_fcontainer, ScreenFragment.newInstance())
          .commitNow()
      reloadInterstitialAd()
    }
    if (interstitialAd.isLoaded) {
      interstitialAd.show()
      interstitialAd.adListener = object : AdListener() {
        override fun onAdClosed() {
          show()
        }
      }
    } else {
      show()
    }
  }

  private fun showSystemFragment() {
    fun show() {
      supportFragmentManager.beginTransaction()
          .replace(R.id.framelayout_main_fcontainer, SystemFragment.newInstance())
          .commitNow()
      reloadInterstitialAd()
    }

    if (interstitialAd.isLoaded) {
      interstitialAd.show()
      interstitialAd.adListener = object : AdListener() {
        override fun onAdClosed() {
          show()
        }
      }
    } else {
      show()
    }

  }

  private fun showBatteryFragment() {
    fun show() {
      supportFragmentManager.beginTransaction()
          .replace(R.id.framelayout_main_fcontainer, BatteryFragment.newInstance())
          .commitNow()
      reloadInterstitialAd()
    }

    if (interstitialAd.isLoaded) {
      interstitialAd.show()
      interstitialAd.adListener = object : AdListener() {
        override fun onAdClosed() {
          show()
        }
      }
    } else {
      show()
    }

  }
}