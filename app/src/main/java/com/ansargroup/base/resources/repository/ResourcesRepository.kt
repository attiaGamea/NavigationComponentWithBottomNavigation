package com.ansargroup.base.resources.repository

import com.ansargroup.R
import com.ansargroup.base.resources.AppResources


/**
 * Created by Attia Gamea on 24/09/20.
 */
class ResourcesRepository(private val appResources: AppResources) {

    fun getNetworkExceptionMessage(): String = appResources.getString(R.string.no_internet_connection)
    fun getSocketTimeoutExceptionMessage(): String = appResources.getString(R.string.timeout_error_message)
    fun getGenericUnknownMessage(): String = appResources.getString(R.string.generic_unknown_error)
}