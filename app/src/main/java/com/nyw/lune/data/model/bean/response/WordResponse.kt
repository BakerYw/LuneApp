package com.nyw.lune.data.model.bean.response

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class WordResponse(
        var level: Int,
        var speakUrl: String? = "",
        var translation: String? = "",
        var word: String? = "",
        var wordId: Int,
        var isSelect:Int
) : Parcelable