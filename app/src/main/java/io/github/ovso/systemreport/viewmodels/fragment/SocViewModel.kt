package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.systemreport.service.model.SocInfo
import timber.log.Timber
import java.io.IOException

class SocViewModel(context: Context) : ViewModel() {
  var socInfoLiveData = MutableLiveData<ArrayList<SocInfo>>()

  fun fechList() {
    Timber.d("cpu into = " + readSocInfo())
  }

  private fun readSocInfo(): String {
    val cmd: ProcessBuilder?
    val result = StringBuilder()
    try {
      val args = listOf("/system/bin/cat", "/proc/cpuinfo")
      cmd = ProcessBuilder(args)
      val process = cmd.start()
      val inputStream = process.inputStream
      val bytes = ByteArray(1024)
      while (inputStream.read(bytes) != -1) {
        var string = String(bytes)
        result.append(string)
        //var infos = string.split("\n")
      }
      inputStream.close()
    } catch (e: IOException) {
      Timber.e(e)
    }
    return result.toString()
  }

}