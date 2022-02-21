package com.nyw.lune.data.model.bean.response

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class TagClassResponse(
        var join: Boolean,
        var joinerCount: Int,
        var libId: Int,
        var libImg: String,
        var libName: String,
        var nickName: String,
        var price: Int
) : Parcelable