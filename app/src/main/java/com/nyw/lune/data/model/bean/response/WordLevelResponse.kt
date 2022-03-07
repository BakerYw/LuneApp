package com.nyw.lune.data.model.bean.response

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class WordLevelResponse(
        var level: Int,
        var words:MutableList<WordResponse>,
        var isSelect:Boolean
) : Parcelable