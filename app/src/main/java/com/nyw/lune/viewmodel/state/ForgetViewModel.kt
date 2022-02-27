package com.nyw.lune.viewmodel.state

import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.databind.StringObservableField

class ForgetViewModel : BaseViewModel() {
    var coin =StringObservableField()
    var hasStudyWord =StringObservableField()
    var hasKnowWord =StringObservableField()
}