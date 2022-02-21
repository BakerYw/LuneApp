package com.nyw.lune.ui.adapter


import com.blankj.utilcode.util.ColorUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nyw.lune.R
import com.nyw.lune.data.model.bean.response.TagResponse

/**
 * @author NieYuWen
 * @date 2021/11/29 8:35 下午
 * @email：992116519@qq.com
 * @desc:
 */
class CategoryAdapter(data: ArrayList<TagResponse>) :
        BaseQuickAdapter<TagResponse, BaseViewHolder>(R.layout.item_menu_category, data) {
    var position = 0
    override fun convert(holder: BaseViewHolder, item: TagResponse) {
        item.run {
            holder.setText(R.id.category_name, tagName)
        }
        if (holder.position == position) {
            holder.setTextColor(R.id.category_name, ColorUtils.getColor(R.color.white))
            holder.setBackgroundColor(R.id.category_name, ColorUtils.getColor(R.color.colorAccent))
        } else {
            holder.setTextColor(R.id.category_name, ColorUtils.getColor(R.color.colorBlack333))
            holder.setBackgroundColor(R.id.category_name, ColorUtils.getColor(R.color.white))
        }
    }

    fun setSelect(position: Int) {
        this.position = position
        setList(data)
        notifyDataSetChanged()
    }
}