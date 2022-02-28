package com.nyw.lune.data.model.bean.response

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class CourseDescResponse(
    var amount: Double,
    var coinPrice: Double,
    var isCollect: Boolean,
    var join: Boolean,
    var joinerCount: Int,
    var libDesc: String,
    var libId: Int,
    var libImg: String,
    var libName: String,
    var nickName: String,
    var price: Double,
    var wordCount: Int
) : Parcelable