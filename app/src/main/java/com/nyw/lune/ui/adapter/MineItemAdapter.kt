package com.nyw.lune.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nyw.lune.R
import com.nyw.lune.data.model.bean.MainItem

/**
 * @author NieYuWen
 * @date 2021/11/29 8:35 下午
 * @email：992116519@qq.com
 * @desc:
 */
class MineItemAdapter(data: ArrayList<MainItem>) :
    BaseQuickAdapter<MainItem, BaseViewHolder>( R.layout.item_mine_item, data) {
    override fun convert(holder: BaseViewHolder, item: MainItem) {
        val iv=holder.getViewOrNull<ImageView>(R.id.iv_item)
        item.run {
            holder.setText(R.id.tv_item,title)
            iv?.setImageResource(imgRes)
        }
    }

}