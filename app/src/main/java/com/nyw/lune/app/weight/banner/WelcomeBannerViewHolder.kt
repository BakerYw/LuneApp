package com.nyw.lune.app.weight.banner


import android.view.View
import android.widget.TextView
import com.nyw.lune.R
import com.zhpan.bannerview.BaseViewHolder

class WelcomeBannerViewHolder(view: View) : BaseViewHolder<String>(view) {
    override fun bindData(data: String?, position: Int, pageSize: Int) {
        val textView = findView<TextView>(R.id.banner_text)
        textView.text = data
    }

}
