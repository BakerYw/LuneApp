package com.nyw.lune.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nyw.lune.R
import com.nyw.lune.data.model.bean.response.TogetherResponse

/**
 * @author NieYuWen
 * @date 2021/11/29 8:35 下午
 * @email：992116519@qq.com
 * @desc:
 */
class StudyWithAdapter(data: ArrayList<TogetherResponse>) :
    BaseQuickAdapter<TogetherResponse, BaseViewHolder>( R.layout.item_study_with, data) {
    override fun convert(holder: BaseViewHolder, item: TogetherResponse) {
        item.run {
            holder.setText(R.id.title, "学生读本—$libName")
            holder.setText(R.id.author, "作\t\t\t\t者\t\t$author")
            holder.setText(R.id.people, "参与人数\t\t\t\t$memberCount")
            holder.setText(R.id.price, "价\t\t\t\t格\t\t$price")//价格需要改为红色
            holder.getViewOrNull<ImageView>(R.id.icon)?.let {
                Glide.with(context).load(libImg)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(it)
            }
        }
    }
}