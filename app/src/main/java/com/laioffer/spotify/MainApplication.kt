package com.laioffer.spotify

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// When app process starts, it creates Application class first
// Only 1 Hilt Android Application
// Singleton scope 的锚定物
@HiltAndroidApp
class MainApplication: Application() {

}
