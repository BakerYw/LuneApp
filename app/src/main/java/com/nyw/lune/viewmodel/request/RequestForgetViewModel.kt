package com.nyw.lune.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.ext.request
import com.nyw.lib_base.state.ResultState
import com.nyw.lune.app.network.apiService
import com.nyw.lune.data.model.bean.response.PlanTrainResponse

/**
 * 抗遗忘和训练
 */
class RequestForgetViewModel : BaseViewModel() {
    var planTrainResult = MutableLiveData<ResultState<PlanTrainResponse>>()
    fun getPlanTrain() {
        request(
                { apiService.getPlanTrain() }//请求体
                , planTrainResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
                true,//是否显示等待框，，默认false不显示 可以默认不传
                "加载中..."//等待框内容，可以默认不填请求网络中...
        )
    }


}