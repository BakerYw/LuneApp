package com.nyw.lune.data.model.bean.response

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class PlanTrainResponse(
        var coin: Int,
        var hasKnowWord: Int,
        var hasStudyWord: Int,
        var items:ArrayList<TrainItems>
) : Parcelable

