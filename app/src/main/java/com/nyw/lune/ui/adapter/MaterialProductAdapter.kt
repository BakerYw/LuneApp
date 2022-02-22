package com.nyw.lune.ui.adapter


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nyw.lune.R
import com.nyw.lune.data.model.bean.response.CateMaterialResponse
import com.nyw.lune.data.model.bean.response.TagClassResponse
import com.nyw.lune.data.model.bean.response.TagResponse

/**
 * @author NieYuWen
 * @date 2021/11/29 8:35 下午
 * @email：992116519@qq.com
 * @desc:
 */
class MaterialProductAdapter(data: ArrayList<CateMaterialResponse>) :
    BaseQuickAdapter<CateMaterialResponse, BaseViewHolder>( R.layout.item_product, data) {
    override fun convert(holder: BaseViewHolder, item: CateMaterialResponse) {
        item.run {
            holder.getViewOrNull<ImageView>(R.id.product_img)?.let {
                Glide.with(context).load(coverImg)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(it)
            }
            holder.setText(R.id.product_name,materialTitle)
            holder.setText(R.id.product_price, "价格\t$price")//价格需要改为红色
            holder.setVisible(R.id.product_type,join)
        }
    }
}