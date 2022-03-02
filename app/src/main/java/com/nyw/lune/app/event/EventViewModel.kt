package com.nyw.lune.app.event

import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.livedata.event.EventLiveData

/**
 * 作者　: YuWen Nie
 * 时间　: 2019/5/2
 * 描述　:APP全局的ViewModel，可以在这里发送全局通知替代EventBus，LiveDataBus等
 */
class EventViewModel : BaseViewModel() {

    //全局进入登录界面
    val toLoginEvent = EventLiveData<Boolean>()

}