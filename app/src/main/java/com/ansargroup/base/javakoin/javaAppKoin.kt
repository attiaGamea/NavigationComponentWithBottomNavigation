package com.ansargroup.base.javakoin

import android.app.Application
import com.ansargroup.base.data.remote.di.remoteModule
import com.ansargroup.base.resources.di.resourcesModule
import com.ansargroup.add.selectcategory.di.selectCategoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Attia Gamea on 24/09/20.
 */
fun start(application: Application) {
    startKoin {
        androidContext(application)
        printLogger()
        modules(
            listOf(
                remoteModule
                , resourcesModule
                , selectCategoryModule
            )
        )
    }
}