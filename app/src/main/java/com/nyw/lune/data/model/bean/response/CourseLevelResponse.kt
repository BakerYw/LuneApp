package com.nyw.lune.data.model.bean.response

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class CourseLevelResponse(
    var hasStudyCount: Int,
    var lastStudyTime: String,
    var studyLevels:Array<Int>,
    var totalLevel: Int,
    var wordCount: Int
    ) : Parcelable