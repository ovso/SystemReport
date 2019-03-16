package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod
import io.github.ovso.systemreport.service.model.NormalInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.Locale
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS

class SystemViewModel(var context: Context) : ViewModel() {
  val infoLiveData = MutableLiveData<ArrayList<NormalInfo>>()
  val compositeDisposable = CompositeDisposable()
  val uptimeLiveData = MutableLiveData<String>()
  fun fetchList() {
    infoLiveData.value = getInfos()
    startIntervalForUptime()
  }

  private fun startIntervalForUptime() {
    val subscribeBy = Observable.interval(1, 1, SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy {
          uptimeLiveData.value = getUptime()
        }
    compositeDisposable.add(subscribeBy)
  }

  private fun getInfos(): ArrayList<NormalInfo>? {
    val easyDeviceMod = EasyDeviceMod(context);
    val osName = getOsVersionName()
    val infos = ArrayList<NormalInfo>()
    infos.add(NormalInfo("OS version", easyDeviceMod.osVersion + " ( $osName )"))
    if (VERSION.SDK_INT >= VERSION_CODES.M) {
      infos.add(NormalInfo("Security patch", android.os.Build.VERSION.SECURITY_PATCH))
    }
    infos.add(NormalInfo("Bootloader", easyDeviceMod.bootloader))
    infos.add(NormalInfo("Build id", easyDeviceMod.buildID))
    infos.add(NormalInfo("API level", easyDeviceMod.buildVersionSDK.toString()))
    infos.add(NormalInfo("Rooted", getRooted(easyDeviceMod.isDeviceRooted)))

    // last
    infos.add(NormalInfo("System uptime", getUptime()))
    return infos;
  }

  private fun getRooted(isRooted: Boolean): String = when (isRooted) {
    true -> "YES"
    false -> "NO"
  }

  private fun getOsVersionName(): String {
    try {
      val fields = Build.VERSION_CODES::class.java.fields
      val osName = fields[Build.VERSION.SDK_INT].name
      return osName;
    } catch (e: Exception) {
      Timber.e(e)
      return "Unknown"
    }
  }

  fun getUptime(): String {
    val uptimeMillis = SystemClock.elapsedRealtime()
    val wholeUptime = String.format(
        Locale.getDefault(),
        "%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toHours(uptimeMillis),
        TimeUnit.MILLISECONDS.toMinutes(uptimeMillis) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(uptimeMillis)
        ),
        TimeUnit.MILLISECONDS.toSeconds(uptimeMillis) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(uptimeMillis)
        )
    )

    return wholeUptime

  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}
