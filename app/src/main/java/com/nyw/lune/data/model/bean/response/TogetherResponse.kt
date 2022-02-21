package com.nyw.lune.data.model.bean.response

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class TogetherResponse(
        var author: String,
        var isBuy: Boolean,
        var isMaster: Int,
        var libId: Int,
        var libImg: String,
        var libName: String,
        var memberCount: Int,
        var price: Double,
        var shareId: Int,
        var wordCount: Int
) : Parcelable