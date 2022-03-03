package com.nyw.lune.viewmodel.state

import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.databind.BooleanObservableField
import com.nyw.lib_base.callback.databind.DoubleObservableField
import com.nyw.lib_base.callback.databind.IntObservableField
import com.nyw.lib_base.callback.databind.StringObservableField

class CourseDescViewModel : BaseViewModel() {
    var imageUrl = StringObservableField()
    var libName = StringObservableField()
    var nickName = StringObservableField()
    var isCollect = BooleanObservableField()
    var isBuy = BooleanObservableField()
    var wordCount = StringObservableField()
    var joinerCount = StringObservableField()
    var libDesc = StringObservableField()
}