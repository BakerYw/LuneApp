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
import com.nyw.lune.data.model.req.RegisterReq
import okhttp3.MediaType
import okhttp3.RequestBody

class RequestMaterialViewModel : BaseViewModel() {

    //体系子栏目列表数据
    var materialData: MutableLiveData<ResultState<ArrayList<CateResponse>>> = MutableLiveData()

    var mMaterialDataListState: MutableLiveData<ListDataUiState<CateMaterialResponse>> = MutableLiveData()

    var pageNo = 1


    /**
     * 获取分类
     */
    fun getCate() {
        request({ apiService.getCate() }, materialData, true, "获取中...")
    }

    fun getCateList(
            isRefresh: Boolean,
            cateId: Int,
            keyword: String? = ""
    ) {
        if (isRefresh) {
            pageNo = 1
        }

        request({ apiService.getCateList(cateId,keyword,pageNo,pageSize = 10) }, {
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
            mMaterialDataListState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                    ListDataUiState(
                            isSuccess = false,
                            errMessage = it.errorMsg,
                            isRefresh = isRefresh,
                            listData = arrayListOf<CateMaterialResponse>()
                    )
            mMaterialDataListState.value = listDataUiState
        })
    }


}