package com.nyw.lune.app.network

import com.nyw.lune.app.util.CacheUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 自定义头部参数拦截器，传入heads
 */
class MyHeadInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val userId=CacheUtil.getUser()?.userId
        builder.addHeader("accessToken", CacheUtil.getToken()).build()
        builder.addHeader("userId", "$userId").build()
        return chain.proceed(builder.build())
    }

}