package com.nyw.lune.viewmodel.state

import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.databind.StringObservableField

class MainViewModel : BaseViewModel() {
    var userTip= StringObservableField()
    init {
        userTip.set("登录/注册")
    }
}