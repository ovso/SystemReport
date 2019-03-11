package io.github.ovso.systemreport.view.ui.main

import timber.log.Timber
import java.io.IOException
import java.io.BufferedReader
import java.io.FileReader

class CMDExecute {
  @Synchronized @Throws(IOException::class)
  fun run(): String {
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
        var infos = string.split("\n")
        for (info in infos) {
          var split = info.split(":")
          Timber.d("split = " + split)
        }
        Timber.d("-------------------------------------")
      }
      inputStream.close()
    } catch (e: IOException) {
      Timber.e(e)
    }
    return result.toString()
  }

  @Throws(IOException::class)
  fun run2(): Map<String, String> {
    val br = BufferedReader(FileReader("/proc/cpuinfo"))
    var str: String? = null
    val output = HashMap<String, String>()
    fun read(): String? {
      str = br.readLine()
      return str
    }
    while (read() != null) {
      val data = str!!.split(":".toRegex())
          .dropLastWhile { it.isEmpty() }
          .toTypedArray()
      if (data.size > 1) {
        var key = data[0].trim { it <= ' ' }
            .replace(" ", "_")
        if (key == "model_name") key = "cpu_model"
        var value = data[1].trim { it <= ' ' }
        if (key == "cpu_model")
          value = value.replace("\\s+".toRegex(), " ")
        output.put(key, value)
      }
    }
    br.close()
    return output
  }
}