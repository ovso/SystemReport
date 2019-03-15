package io.github.ovso.systemreport.viewmodels.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod
import io.github.ovso.systemreport.service.model.NormalInfo

class DeviceViewModel(var context: Context) : ViewModel() {
  val infoLiveData = MutableLiveData<List<NormalInfo>>()
  private val easyDeviceMod = EasyDeviceMod(context)

  fun fetchList() {
    infoLiveData.value = provideInfos()
  }

  private fun provideInfos(): List<NormalInfo>? {
    val infos = ArrayList<NormalInfo>()
    infos.add(NormalInfo("Model", easyDeviceMod.model))
    infos.add(NormalInfo("Manufacturer", easyDeviceMod.manufacturer))
    infos.add(NormalInfo("Brand", easyDeviceMod.buildBrand))
    infos.add(NormalInfo("Hardware", easyDeviceMod.hardware))
    infos.add(NormalInfo("Board", easyDeviceMod.board))
    infos.add(NormalInfo("Display ID", easyDeviceMod.screenDisplayID))
    infos.add(NormalInfo("Fingerprint", easyDeviceMod.fingerprint))
    infos.add(NormalInfo("Build brand", easyDeviceMod.buildBrand))
    infos.add(NormalInfo("Bootloader", easyDeviceMod.bootloader))
    infos.add(NormalInfo("Build host", easyDeviceMod.buildHost))
    infos.add(NormalInfo("buildID", easyDeviceMod.buildID))
    infos.add(NormalInfo("buildTags", easyDeviceMod.buildTags))
    infos.add(NormalInfo("buildUser", easyDeviceMod.buildUser))
    infos.add(NormalInfo("buildVersionCodename", easyDeviceMod.buildVersionCodename))
    infos.add(NormalInfo("buildVersionIncremental", easyDeviceMod.buildVersionIncremental))
    infos.add(NormalInfo("buildVersionRelease", easyDeviceMod.buildVersionRelease))
    infos.add(NormalInfo("device", easyDeviceMod.device))
    infos.add(NormalInfo("displayVersion", easyDeviceMod.displayVersion))
    infos.add(NormalInfo("language", easyDeviceMod.language))
    infos.add(NormalInfo("osCodename", easyDeviceMod.osCodename))
    infos.add(NormalInfo("osVersion", easyDeviceMod.osVersion))
    infos.add(NormalInfo("product", easyDeviceMod.product))
    infos.add(NormalInfo("radioVer", easyDeviceMod.radioVer))
    infos.add(NormalInfo("serial", easyDeviceMod.serial))
    infos.add(NormalInfo("buildTime", easyDeviceMod.buildTime.toString()))
    infos.add(NormalInfo("buildVersionSDK", easyDeviceMod.buildVersionSDK.toString()))
    infos.add(NormalInfo("isDeviceRooted", easyDeviceMod.isDeviceRooted.toString()))
    return infos
  }
}
