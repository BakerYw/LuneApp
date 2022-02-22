package com.nyw.lune.data.model.bean.response

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class CateMaterialResponse(
        var content: String,
        var coverImg: String,
        var id: Int,
        var materialTitle: String,
        var join: Boolean=false,
        var price: Int=0
) : Parcelable