package com.nyw.lune.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.JsonObject
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.ext.request
import com.nyw.lib_base.state.ResultState
import com.nyw.lune.app.network.apiService
import com.nyw.lune.data.model.bean.response.UserResponse
import com.nyw.lune.data.model.req.RegisterReq
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * 用户管理
 */
class RequestUserManageViewModel : BaseViewModel()  {
    private val mediaType = MediaType.parse("application/json; charset=UTF-8")
    var commonResult = MutableLiveData<ResultState<Any?>>()
    var registerResult = MutableLiveData<ResultState<Any?>>()
    var retrieveResult = MutableLiveData<ResultState<Any?>>()
    var loginResult = MutableLiveData<ResultState<UserResponse>>()


    /**
     * 获取手机验证码
     */
    fun getCode(phone: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("mobile", phone)
        jsonObject.addProperty("type", 1)
        val requestBody = RequestBody.create(mediaType, jsonObject.toString())
        request(
            { apiService.getCode(requestBody) },
                commonResult,
            true,
            "获取中..."
        )
    }

    /**
     * 注册
     */
    fun register(account: String?="",
                 clbum: String?="",
                 grade: String,
                 mobile: String,
                 orgCode: String,//开发时填写【1234】
                 password: String,
                 realName: String,
                 school: String,
                 score: String,
                 smsCode: String//开发时填写【1234】
    ) {
        val str: String = GsonUtils.toJson(RegisterReq(
                account,
                clbum,
                grade,
                mobile,
                orgCode,
                password,
                realName,
                school,
                score,
                smsCode), RegisterReq::class.java)
        val requestBody = RequestBody.create(mediaType, str)
        request(
                { apiService.register(requestBody) }//请求体
                , registerResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
                true,//是否显示等待框，，默认false不显示 可以默认不传
                "注册中..."//等待框内容，可以默认不填请求网络中...
        )
    }


    /**
     * 登录
     */
    fun login(account: String,  password: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("account", account)
        jsonObject.addProperty("password", password)
        val requestBody = RequestBody.create(mediaType, jsonObject.toString())
        request(
                { apiService.login(requestBody) }//请求体
                , loginResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
                true,//是否显示等待框，，默认false不显示 可以默认不传
                "登录中..."//等待框内容，可以默认不填请求网络中...
        )
    }

    /**
     * 退出登录
     */
    fun exitLogin(){
        request(
                { apiService.exitLogin() }//请求体
                , commonResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
                true,//是否显示等待框，，默认false不显示 可以默认不传
                "退出中..."//等待框内容，可以默认不填请求网络中...
        )
    }


    /**
     * 忘记密码-第一步
     */
    fun validPassWord(account: String,  password: String,smsCode:String){
        val jsonObject = JsonObject()
        jsonObject.addProperty("account", account)
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("smsCode", smsCode)
        val requestBody = RequestBody.create(mediaType, jsonObject.toString())
        request(
                { apiService.validPassWord(requestBody) }//请求体
                , commonResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
                true,//是否显示等待框，，默认false不显示 可以默认不传
                "加载中..."//等待框内容，可以默认不填请求网络中...
        )
    }


    /**
     * 忘记密码-第二步
     */
    fun update(account: String,  password: String,smsCode:String){
        val jsonObject = JsonObject()
        jsonObject.addProperty("account", account)
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("smsCode", smsCode)
        val requestBody = RequestBody.create(mediaType, jsonObject.toString())
        request(
                { apiService.update(requestBody) }//请求体
                , commonResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
                true,//是否显示等待框，，默认false不显示 可以默认不传
                "加载中..."//等待框内容，可以默认不填请求网络中...
        )
    }


    /**
     * 更新用户头像
     */
    fun updateUser(headImg: String,  userId: Int){
        val jsonObject = JsonObject()
        jsonObject.addProperty("headImg", headImg)
        jsonObject.addProperty("userId", userId)
        val requestBody = RequestBody.create(mediaType, jsonObject.toString())
        request(
                { apiService.updateUser(requestBody) }//请求体
                , commonResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
                true,//是否显示等待框，，默认false不显示 可以默认不传
                "更新中..."//等待框内容，可以默认不填请求网络中...
        )
    }


    /**
     * 我的数据
     */
    fun getMineData(){
        request(
                { apiService.getMineData() }//请求体
                , commonResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
                true,//是否显示等待框，，默认false不显示 可以默认不传
                "更新中..."//等待框内容，可以默认不填请求网络中...
        )
    }


}