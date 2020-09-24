package com.ansargroup.base.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Attia Gamea on 24/09/20.
 */
class HeadersInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(setupRequestHeaders(chain.request()))
    }

    private fun setupRequestHeaders(request: Request): Request {
        val requestBuilder = request.newBuilder()
        return requestBuilder.build()
    }

}