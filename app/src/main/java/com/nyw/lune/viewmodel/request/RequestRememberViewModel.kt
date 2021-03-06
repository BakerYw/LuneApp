package com.nyw.lune.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.ext.request
import com.nyw.lib_base.state.ResultState
import com.nyw.lune.app.network.apiService
import com.nyw.lune.app.network.stateCallback.ListDataUiState
import com.nyw.lune.data.model.bean.response.*
import com.nyw.lune.data.model.req.GetTagClassReq
import com.nyw.lune.data.model.req.GetWordsReq
import okhttp3.MediaType
import okhttp3.RequestBody

class RequestRememberViewModel : BaseViewModel() {
    val mediaType = MediaType.parse("application/json; charset=UTF-8")

    //体系子栏目列表数据
    var tagData: MutableLiveData<ResultState<ArrayList<TagResponse>>> = MutableLiveData()
    var mTagClassDataState: MutableLiveData<ListDataUiState<TagClassResponse>> = MutableLiveData()
    var pageNo = 1

    //课程详情
    var courseDescData: MutableLiveData<ResultState<CourseDescResponse>> = MutableLiveData()

    //课程列表
    var courseLevelData: MutableLiveData<ResultState<CourseLevelResponse>> = MutableLiveData()

    var levelWordsResult :MutableLiveData<ResultState<MutableList<WordLevelResponse>>>  = MutableLiveData()

    var wordResult: MutableLiveData<ResultState<WordResponse>> = MutableLiveData()

    /**
     * 获取分类
     */
    fun getTag() {
        request({ apiService.getTag(1) }, tagData, true, "获取中...")
    }

    /**
     * 获取该分类的课程列表
     */
    fun getTagClassDataList(
            isRefresh: Boolean,
            tagId: Int,
            keyword: String? = "",
            libName: String? = ""
    ) {
        if (isRefresh) {
            pageNo = 1
        }
        val str: String = GsonUtils.toJson(
                GetTagClassReq(
                        pageNo, keyword, libName, pageSize = 20, tagId = tagId, tagIds = arrayListOf()
                ), GetTagClassReq::class.java
        )
        val requestBody = RequestBody.create(mediaType, str)
        request({ apiService.getTagClassList(requestBody) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                    ListDataUiState(
                            isSuccess = true,
                            isRefresh = isRefresh,
                            isEmpty = it.isEmpty(),
                            hasMore = it.hasMore(),
                            isFirstEmpty = isRefresh && it.isEmpty(),
                            listData = it.list
                    )
            mTagClassDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                    ListDataUiState(
                            isSuccess = false,
                            errMessage = it.errorMsg,
                            isRefresh = isRefresh,
                            listData = arrayListOf<TagClassResponse>()
                    )
            mTagClassDataState.value = listDataUiState
        })
    }


    /**
     * 获取该课程详情
     */
    fun getCourseDesc(libId: Int) {
        request({ apiService.getCourseDesc(libId) }, courseDescData, true, "获取中...")
    }


    /**
     * 获取该课程关卡
     */

    fun getLevelDesc(libId: Int) {
        request({ apiService.getLevelDesc(libId) }, courseLevelData, true, "获取中...")
    }


    fun getLevelWords(libId: Int, levels: ArrayList<Int>?) {
        val str: String = GsonUtils.toJson(
                GetWordsReq(libId,levels,wordIds = mutableListOf()), GetWordsReq::class.java
        )
        val requestBody = RequestBody.create(mediaType, str)
        request({ apiService.getLevelWords(requestBody) }, levelWordsResult, true, "获取中...")
    }


    fun getLevelWordsWithWorkId(libId: Int,levels:MutableList<Int>?,wordIds: MutableList<Int>?) {
        val str: String = GsonUtils.toJson(
                GetWordsReq(libId,levels,wordIds), GetWordsReq::class.java
        )
        val requestBody = RequestBody.create(mediaType, str)
        request({ apiService.getLevelWordsWithWorkId(requestBody) }, levelWordsResult, true, "获取中...")
    }


    /**
     * 查看单词的详细信息
     */
    fun getWordDetail(wordId: Int) {
        request({ apiService.getWordDetail(wordId) }, wordResult, true, "获取中...")
    }


}