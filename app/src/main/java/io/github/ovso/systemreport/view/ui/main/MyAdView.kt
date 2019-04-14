package io.github.ovso.systemreport.view.ui.main

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import io.github.ovso.systemreport.utils.Ads

object MyAdView {
  fun getAdmobBannerView(context: Context): AdView {
    val adView = AdView(context)
    adView.adSize = AdSize.SMART_BANNER
    adView.adUnitId = Ads.ADMOB_BANNER_UNIT_ID.value
    val adRequest = AdRequest.Builder()
        .build()
    adView.loadAd(adRequest)
    return adView
  }

  fun getAdmobInterstitialAd(context: Context): InterstitialAd {
    val interstitialAd = InterstitialAd(context)
    interstitialAd.adUnitId = Ads.ADMOB_INTERSTITIAL_ID.value
    val adRequestBuilder = AdRequest.Builder()
    interstitialAd.loadAd(adRequestBuilder.build())
    return interstitialAd
  }
}