package com.nyw.lune.viewmodel.state

import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.databind.StringObservableField

class SelectLevelViewModel : BaseViewModel() {
    var totalLevel = StringObservableField()
    var wordCount = StringObservableField()
    var studyLevels = StringObservableField()
}