package com.ansargroup

import android.app.Application
import com.ansargroup.base.javakoin.start

/**
 * Created by Attia Gamea on 9/24/20.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        start(this)
    }
}