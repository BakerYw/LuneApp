package com.nyw.lune.app.network

import com.nyw.lune.data.model.ApiResponse
import com.nyw.lune.data.model.bean.UserInfo
import com.nyw.lune.data.model.req.RegisterAndLoginReq
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * 作者　: YuWen Nie
 * 时间　: 2019/12/23
 * 描述　: 网络API
 */
interface ApiService {

    companion object {
        const val TEST_URL = "http://luen.api.ssfast.cn"
        const val PROD_URL = "https://luen.api.ssfast.cn"
    }


    /**
     * 获取手机验证码
     */
    @POST("/api/sms/send")
    suspend fun getCode(@Body smsDTO: RequestBody): ApiResponse<Any?>

    /**
     * 注册
     */
    @POST("/api/pad/user/register")
    suspend fun register(@Body registerDTO: RequestBody): ApiResponse<UserInfo>

    /**
     * 登录
     */
    @POST("/api/pad/user/login")
    suspend fun login(@Body loginDTO: RequestBody): ApiResponse<UserInfo>



}