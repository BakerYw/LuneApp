package com.nyw.lune.data.model

import java.io.Serializable

/**
 *  分页数据的基类
 */
data class ApiPagerResponse<T>(
    var list: T,
    var currentPage: Int,
    var totalPage: Int,
    var pageSize: Int,
    var totalResult: Int
) : Serializable {
    /**
     * 数据是否为空
     */
    fun isEmpty() = (list as List<*>).size == 0

    /**
     * 是否为刷新
     */
    fun isRefresh() = currentPage == 1

    /**
     * 是否还有更多数据
     */
    fun hasMore() = pageSize == totalResult
}