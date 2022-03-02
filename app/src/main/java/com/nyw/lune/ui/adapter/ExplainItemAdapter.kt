package com.nyw.lune.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nyw.lune.R

/**
 * @author NieYuWen
 * @date 2021/11/29 8:35 下午
 * @email：992116519@qq.com
 * @desc:
 */
class ExplainItemAdapter(data: ArrayList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>( R.layout.item_explain_item, data) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_explain,item)
    }

}