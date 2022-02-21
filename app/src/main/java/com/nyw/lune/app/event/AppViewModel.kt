package com.nyw.lune.app.event

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.livedata.event.EventLiveData
import com.nyw.lune.app.util.CacheUtil
import com.nyw.lune.app.util.SettingUtil
import com.nyw.lune.data.model.bean.response.UserResponse

/**
 * 作者　: YuWen Nie
 * 时间　: 2019/12/23
 * 描述　:APP全局的ViewModel，可以存放公共数据，当他数据改变时，所有监听他的地方都会收到回调,也可以做发送消息
 * 比如 全局可使用的 地理位置信息，账户信息,App的基本配置等等，
 */
class AppViewModel : BaseViewModel() {

    //App的账户信息
    var userInfo = UnPeekLiveData.Builder<UserResponse>().setAllowNullValue(true).create()

    //App主题颜色 中大型项目不推荐以这种方式改变主题颜色，比较繁琐耦合，且容易有遗漏某些控件没有设置主题色
    var isMainTheme = EventLiveData<Boolean>()

    //App 列表动画
    var appAnimation = EventLiveData<Int>()

    init {
        //默认值保存的账户信息，没有登陆过则为null
        userInfo.value = CacheUtil.getUser()
        //默认值颜色
        isMainTheme.value = true
        //初始化列表动画
        appAnimation.value = SettingUtil.getListMode()
    }
}