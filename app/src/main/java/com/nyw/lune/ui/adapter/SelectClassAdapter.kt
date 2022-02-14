package com.nyw.lune.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.bar.TitleBar
import com.nyw.lune.R
import com.nyw.lune.data.model.bean.ClassMultiItem
import com.nyw.lune.data.model.bean.ClassMultiItem.Companion.TYPE_BODY
import com.nyw.lune.data.model.bean.ClassMultiItem.Companion.TYPE_HEAD


class SelectClassAdapter(data: MutableList<ClassMultiItem>, private val onBack: (str: String?) -> Unit) :
        BaseMultiItemQuickAdapter<ClassMultiItem, BaseViewHolder>(data) {
    private var selectedPosition = 1 //默认一个参数


    init {
        addItemType(TYPE_HEAD, R.layout.item_class_layout)
        addItemType(TYPE_BODY, R.layout.item_class_child_layout)
    }


    override fun convert(holder: BaseViewHolder, item: ClassMultiItem) {
        when (holder.itemViewType) {
            TYPE_HEAD -> {
                holder.getViewOrNull<TextView>(R.id.className)?.text = item.className
            }
            TYPE_BODY -> {
                val name = holder.getViewOrNull<TextView>(R.id.name)
                name?.text = item.name
                if (holder.adapterPosition == selectedPosition) {
                    name?.setBackgroundResource(R.drawable.selector_btn_select_class_bg_s)
                } else {
                    name?.setBackgroundResource(R.drawable.selector_btn_select_class_bg_n)
                }
                name?.setOnClickListener {
                    onBack.invoke(item.name)
                    selectedPosition = holder.adapterPosition
                    notifyDataSetChanged()
                }
            }
        }
    }


}