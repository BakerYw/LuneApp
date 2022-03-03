package com.nyw.lune.app.network

import com.nyw.lune.data.model.ApiPagerResponse
import com.nyw.lune.data.model.ApiResponse
import com.nyw.lune.data.model.bean.response.*
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
    /***************************************查单词**********************************************/

    /**
     * 根据文本查询单词
     */
    @GET("/api/pad/dict/query/text")
    suspend fun getQueryWithText(@Query("word") word: String): ApiResponse<QueryWordResponse>

    /**
     * 根据语音二进制查询单词
     */
    @GET("/api/pad/dict/query/audio")
    suspend fun getQueryWithAudio(@Query("base64Acc") base64Acc: String): ApiResponse<QueryWordResponse>


    /**
     * 查询出来的单词，添加生词本
     */
    @POST("/api/pad/dict/newword/add")
    suspend fun addNewWord(@Body dictDto: RequestBody): ApiResponse<Any?>


    /**
     * 根据语音比对单词，获取读音评分
     */
    @POST("/api/pad/dict/compare")
    suspend fun dictCompare(@Body dto: RequestBody): ApiResponse<Any?>




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


    @GET("/api/pad/lib/detail")
    suspend fun getCourseDesc(
        @Query("libId") libId: Int
    ): ApiResponse<CourseDescResponse>

    @GET("/api/pad/lib/level/info")
    suspend fun getLevelDesc(
        @Query("libId") libId: Int
    ): ApiResponse<CourseLevelResponse>



    /***************************************参考资料*********************************************/
    /**
     *资料列表->类别分类
     */
    @GET("/api/pad/material/cate/list")
    suspend fun getMaterialCate(): ApiResponse<ArrayList<CateResponse>>

    /**
     *资料列表->分页查询
     */
    @GET("/api/pad/material/list/page")
    suspend fun getMaterialCateList(
            @Query("cateId") cateId: Int,
            @Query("keyword") keyword: String?,
            @Query("currentPage") currentPage: Int,
            @Query("pageSize") pageSize: Int
    ): ApiResponse<ApiPagerResponse<ArrayList<CateMaterialResponse>>>


    /**
     * 资料详情
     */
    @GET("/api/pad/material/detail")
    suspend fun getMaterialDetail(
        @Query("materialId") materialId: Int
    ): ApiResponse<CateMaterialResponse>

    /***************************************抗遗忘**********************************************/

    /**
     * 抗遗忘和训练->训练记录
     */
    @GET("/api/pad/plantrain/train/list")
    suspend fun getPlanTrain(): ApiResponse<PlanTrainResponse>

    /**
     * 考核题库
     */
    @GET("/api/pad/plantrain/train/topic/list")
    suspend fun getPlanTrainTopic(@Query("libId") libId: Int): ApiResponse<ArrayList<PlanTrainTopicResponse>>


    /***************************************xx管理**********************************************/


}