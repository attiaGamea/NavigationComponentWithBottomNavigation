package com.ansargroup.base.resources.di

import com.ansargroup.base.resources.AppResources
import com.ansargroup.base.resources.repository.ResourcesRepository
import org.koin.dsl.module

/**
 * Created by Attia Gamea on 24/09/20.
 */
val resourcesModule = module {
    single { AppResources(get()) }
    single { ResourcesRepository(get()) }
}