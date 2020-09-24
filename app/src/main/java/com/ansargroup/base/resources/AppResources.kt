package com.ansargroup.base.resources

import android.app.Application

/**
 * Created by Attia Gamea on 24/09/20.
 */
class AppResources(private val application: Application) {

    fun getString(resId: Int): String = application.getString(resId)
}