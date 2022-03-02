package com.nyw.lune.viewmodel.state

import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.databind.ByteObservableField
import com.nyw.lib_base.callback.databind.IntObservableField
import com.nyw.lib_base.callback.databind.StringObservableField

class SearchViewModel : BaseViewModel() {
    var word=StringObservableField()
    var translation=StringObservableField()
    var phonetic =StringObservableField()
    var ukPhonetic =StringObservableField()
    var usPhonetic =StringObservableField()
    var speakUrl =StringObservableField()
    var ukSpeakUrl =StringObservableField()
    var usSpeakUrl =StringObservableField()
    var keyWord=StringObservableField()
    var voice = ByteObservableField()
}