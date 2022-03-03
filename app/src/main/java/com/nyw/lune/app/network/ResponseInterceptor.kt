package com.nyw.lune.app.network


import com.google.gson.Gson
import com.kongzue.dialogx.dialogs.MessageDialog
import com.nyw.lune.app.eventViewModel
import com.nyw.lune.app.util.CacheUtil
import com.nyw.lune.data.model.ApiErrorResponse
import com.nyw.lune.data.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException


/**
 * 作者　: YuWen Nie
 * 时间　: 2022/1/13
 * 描述　: token过期/未登录 ->拦截器
 */

class ResponseInterceptor : Interceptor {

    private val gson:Gson by lazy { Gson() }

    @kotlin.jvm.Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return if (response.body()!=null && response.body()?.contentType()!=null){
            val contentType =response.body()?.contentType()
            val str=response.body()?.string()
            val responseBody=ResponseBody.create(contentType, str)
            val apiResponse =gson.fromJson(str, ApiResponse::class.java)
            //判断逻辑
            when(apiResponse.code){
                993 -> goLogin()
            }
            response.newBuilder().body(responseBody).build()
        }else{
            response
        }
    }

    private fun goLogin() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(300)
            MessageDialog.show("提示", "检测到未登录或Token已经过期,是否立即去登录呢？", "确定","取消").setOkButton { baseDialog, v ->
                eventViewModel.toLoginEvent.value=true
                CacheUtil.setUser(null)
                false
            }
        }
//        CacheUtil.setIsLogin(false)
    }
}