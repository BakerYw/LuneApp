package com.nyw.lune.viewmodel.state

import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.databind.ByteObservableField
import com.nyw.lib_base.callback.databind.IntObservableField

class SearchViewModel : BaseViewModel() {
    var initPage = IntObservableField() //默认是未输入页面
    var voice = ByteObservableField()
    init {
        initPage.set(1)
    }
}