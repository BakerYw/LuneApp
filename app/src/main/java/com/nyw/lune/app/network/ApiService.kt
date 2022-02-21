package com.nyw.lune.app.network

import com.nyw.lune.data.model.ApiPagerResponse
import com.nyw.lune.data.model.ApiResponse
import com.nyw.lune.data.model.bean.response.TagClassResponse
import com.nyw.lune.data.model.bean.response.TagResponse
import com.nyw.lune.data.model.bean.response.TogetherResponse
import com.nyw.lune.data.model.bean.response.UserResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


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

    /***************************************用户管理**********************************************/
    /**
     * 获取手机验证码
     */
    @POST("/api/sms/send")
    suspend fun getCode(@Body smsDTO: RequestBody): ApiResponse<Any?>

    /**
     * 注册
     */
    @POST("/api/pad/user/register")
    suspend fun register(@Body registerDTO: RequestBody): ApiResponse<Any?>

    /**
     * 登录
     */
    @POST("/api/pad/user/login")
    suspend fun login(@Body loginDTO: RequestBody): ApiResponse<UserResponse>

    /**
     * 退出登录
     */
    @POST("/api/pad/user/exitLogin")
    suspend fun exitLogin(): ApiResponse<Any?>

    /**
     * 忘记密码第一步
     */
    @POST("/api/pad/user/password/valid")
    suspend fun validPassWord(@Body forgetPasswordDTO: RequestBody): ApiResponse<Any?>


    /**
     * 忘记密码第一步
     */
    @POST("/api/pad/user/password/update")
    suspend fun update(@Body forgetPasswordDTO: RequestBody): ApiResponse<Any?>


    /**
     * 忘记密码第一步
     */
    @POST("/api/pad/user/info/update")
    suspend fun updateUser(@Body wxUserDTO: RequestBody): ApiResponse<Any?>

    /**
     * 获取我的信息
     */
    @GET("/api/pad/me/data")
    suspend fun getMineData(): ApiResponse<Any?>
    /***************************************一起学**********************************************/
    /**
     * 一起学列表
     */
    @GET("/api/pad/together/list/page")
    suspend fun getTogetherList(
            @Query("queryType") queryType: Int,
            @Query("currentPage") currentPage: Int,
            @Query("pageSize") pageSize: Int
    ): ApiResponse<ApiPagerResponse<ArrayList<TogetherResponse>>>

    /**
     * 我的->组内成员
     */
    @GET("/api/pad/together/member/list")
    suspend fun getMember(
            @Query("shareId") shareId: Int
    ): ApiResponse<Any?>

    /***************************************记单词**********************************************/
    /**
     * 课程分类
     */
    @GET("/api/pad/lib/tag/list")
    suspend fun getTag(
            @Query("tagType") tagType: Int
    ): ApiResponse<ArrayList<TagResponse>>


    /**
     * 课程分页查询
     */
    @POST("/api/pad/lib/list/page")
    suspend fun getTagClassList(
            @Body libDto: RequestBody
    ): ApiResponse<ApiPagerResponse<ArrayList<TagClassResponse>>>

    /***************************************xx管理**********************************************/


}