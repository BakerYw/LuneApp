package com.nyw.lune.data.model.req

data class GetTagClassReq(
        var currentPage: Int,
        var keyword: String?,
        var libName: String?,
        var pageSize: Int,
        var tagId: Int,
        var tagIds: MutableList<Int>?
)