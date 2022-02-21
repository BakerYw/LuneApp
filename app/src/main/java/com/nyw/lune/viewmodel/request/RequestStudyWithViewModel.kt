package com.nyw.lune.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.ext.request
import com.nyw.lune.app.network.apiService
import com.nyw.lune.app.network.stateCallback.ListDataUiState
import com.nyw.lune.data.model.bean.response.TogetherResponse

class RequestStudyWithViewModel : BaseViewModel() {
    //体系子栏目列表数据
    var mChildDataState: MutableLiveData<ListDataUiState<TogetherResponse>> = MutableLiveData()

    var pageNo = 1

    fun getTogetherList(
        isRefresh: Boolean,
        queryType: Int
    ) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ apiService.getTogetherList(queryType, pageNo, pageSize = 10) }, {
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
            mChildDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<TogetherResponse>()
                )
            mChildDataState.value = listDataUiState
        })
    }


}