package com.ansargroup.base.data.remote.di

import com.ansargroup.BuildConfig
import com.ansargroup.base.data.remote.RemoteConstants
import com.ansargroup.base.data.remote.interceptor.ErrorMappingInterceptor
import com.ansargroup.base.data.remote.interceptor.HeadersInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Attia Gamea on 24/09/20.
 */
val remoteModule = module {
    single { HeadersInterceptor() }
    single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
    single { ErrorMappingInterceptor(get(), get()) }
    single {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(get<HeadersInterceptor>())
            .addInterceptor(get<ErrorMappingInterceptor>())
            .connectTimeout(RemoteConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(RemoteConstants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(RemoteConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(get<HttpLoggingInterceptor>())
        }
        builder.build()
    }

    single { GsonBuilder().create() }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }
}