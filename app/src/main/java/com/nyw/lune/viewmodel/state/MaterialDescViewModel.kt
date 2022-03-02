package com.nyw.lune.viewmodel.state

import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.databind.IntObservableField
import com.nyw.lib_base.callback.databind.StringObservableField

class MaterialDescViewModel : BaseViewModel() {
    var id =IntObservableField()
    var content =StringObservableField()
    var coverImg =StringObservableField()
    var title =StringObservableField()


}