package com.nyw.lune.data.model.bean.response

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class QueryWordResponse(
    var cnItems: String,
    var explains: MutableList<String>,
    var phonetic: String,
    var speakUrl: String,
    var translation:MutableList<String>,
    var ukPhonetic: String,
    var ukSpeech: String,
    var usPhonetic: String,
    var usSpeech: String,
    var word: String,
    var wordLogId: Int
) : Parcelable