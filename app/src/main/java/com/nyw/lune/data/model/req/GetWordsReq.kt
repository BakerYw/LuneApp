package com.nyw.lune.data.model.req

data class GetWordsReq(
        var libId: Int,
        var levels: MutableList<Int>?,
        var wordIds: MutableList<Int>?
)