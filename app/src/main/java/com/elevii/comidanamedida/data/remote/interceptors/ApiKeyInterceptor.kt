package com.elevii.comidanamedida.data.remote.interceptors

import com.elevii.comidanamedida.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithApiKey = originalRequest.newBuilder()
            .addHeader("api-key", BuildConfig.API_KEY)
            .build()

        return chain.proceed(requestWithApiKey)
    }
}