package com.nyw.lune.ui.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nyw.lune.R
import com.nyw.lune.data.model.bean.LevelItem

/**
 * @author NieYuWen
 * @date 2021/11/29 8:35 下午
 * @email：992116519@qq.com
 * @desc:
 */
class SelectLevelAdapter(data: ArrayList<LevelItem>) :
    BaseQuickAdapter<LevelItem, BaseViewHolder>(R.layout.item_level_layout, data) {
    override fun convert(holder: BaseViewHolder, item: LevelItem) {
        holder.setText(R.id.title,"第${item.text}关")
    }
}