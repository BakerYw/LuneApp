package com.nyw.lune.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class LevelItem(
    var level: Int,
    var text: String,
    var isStudy: Boolean,
    var isSelect: Boolean
): Parcelable