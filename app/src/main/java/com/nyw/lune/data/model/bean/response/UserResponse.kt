package com.nyw.lune.data.model.bean.response

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　: 账户信息
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class UserResponse(
        var accessToken: String,
        var account: String,
        var headImg: String,
        var mobile: String,
        var nickName: String,
        var realName: String,
        var userId: Int) : Parcelable
