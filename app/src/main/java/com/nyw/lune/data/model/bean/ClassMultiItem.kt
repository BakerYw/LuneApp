package com.nyw.lune.data.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity



data class ClassMultiItem(
        var className: String?="",
        var name: String?="",
        var isSelect: Boolean?=false,
        override val itemType: Int) : MultiItemEntity {
    companion object {
        const val TYPE_HEAD= 1
        const val TYPE_BODY = 2
    }
}


