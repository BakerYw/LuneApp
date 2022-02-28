package com.nyw.lune.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.ext.request
import com.nyw.lib_base.state.ResultState
import com.nyw.lune.app.network.apiService
import com.nyw.lune.data.model.bean.response.PlanTrainResponse
import com.nyw.lune.data.model.bean.response.QueryWordResponse
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.*

/**
 * 抗遗忘和训练
 */
class RequestSearchViewModel : BaseViewModel() {
    val mediaType = MediaType.parse("application/json; charset=UTF-8")
    var wordResult = MutableLiveData<ResultState<QueryWordResponse>>()
    var commonResult = MutableLiveData<ResultState<Any?>>()

    /**
     * 根据文本搜索
     */
    fun getQueryWithText(str: String) {
        request(
            { apiService.getQueryWithText(str) }//请求体
            , wordResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
            true,//是否显示等待框，，默认false不显示 可以默认不传
            "加载中..."//等待框内容，可以默认不填请求网络中...
        )
    }

    /**
     * 根据base64搜索
     */
    fun getQueryWithAudio(base64Str: String) {
        request(
            { apiService.getQueryWithAudio(base64Str) }//请求体
            , wordResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
            true,//是否显示等待框，，默认false不显示 可以默认不传
            "加载中..."//等待框内容，可以默认不填请求网络中...
        )
    }


    /**
     * 查询出来的单词，添加生词本
     */
    fun addNewWord(word: String, wordLogId: Int) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("word", word)
        jsonObject.addProperty("wordLogId", wordLogId)
        val requestBody = RequestBody.create(mediaType, jsonObject.toString())
        request(
            { apiService.addNewWord(requestBody) }//请求体
            , commonResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
            true,//是否显示等待框，，默认false不显示 可以默认不传
            "添加中..."//等待框内容，可以默认不填请求网络中...
        )
    }


    /**
     * 根据语音比对单词，获取读音评分
     */
    fun dictCompare(base64: String, word: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("base64", base64)
        jsonObject.addProperty("word", word)
        val requestBody = RequestBody.create(mediaType, jsonObject.toString())
        request(
            { apiService.dictCompare(requestBody) }//请求体
            , commonResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
            true,//是否显示等待框，，默认false不显示 可以默认不传
            "添加中..."//等待框内容，可以默认不填请求网络中...
        )
    }


}