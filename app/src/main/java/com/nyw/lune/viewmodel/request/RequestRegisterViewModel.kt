package com.nyw.lune.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.JsonObject
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.ext.request
import com.nyw.lib_base.ext.requestNoCheck
import com.nyw.lib_base.state.ResultState
import com.nyw.lune.app.network.apiService
import com.nyw.lune.app.network.stateCallback.CollectUiState
import com.nyw.lune.app.network.stateCallback.UpdateUiState
import com.nyw.lune.data.model.bean.UserInfo
import com.nyw.lune.data.model.req.RegisterAndLoginReq
import com.nyw.lune.data.repository.request.HttpRequestCoroutine
import okhttp3.MediaType
import okhttp3.RequestBody

class RequestRegisterViewModel : BaseViewModel()  {
    var commonResult = MutableLiveData<ResultState<Any?>>()
    var loginResult = MutableLiveData<ResultState<UserInfo>>()

    /**
     * 获取手机验证码
     */
    fun getCode(phone: String, type: Int) {
        val mediaType = MediaType.parse("application/json; charset=UTF-8")
        val jsonObject = JsonObject()
        jsonObject.addProperty("mobile", phone)
        jsonObject.addProperty("type", type)
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

    fun registerAndLogin(username: String, phone: String, password: String,code: String) {
        val mediaType = MediaType.parse("application/json; charset=UTF-8")
        val str: String = GsonUtils.toJson(RegisterAndLoginReq(username, phone,password,code), RegisterAndLoginReq::class.java)
        val requestBody = RequestBody.create(mediaType, str)
        request(
                { apiService.register(requestBody) }//请求体
                , loginResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
                true,//是否显示等待框，，默认false不显示 可以默认不传
                "注册中..."//等待框内容，可以默认不填请求网络中...
        )
    }
}