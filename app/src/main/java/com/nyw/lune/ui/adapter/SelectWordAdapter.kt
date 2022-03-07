package com.nyw.lune.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import com.nyw.lune.R
import com.nyw.lune.app.util.NumberUtils.toChineseNumUtill
import com.nyw.lune.data.model.bean.response.WordLevelResponse
import com.nyw.lune.data.model.bean.response.WordResponse

/**
 * @author NieYuWen
 * @date 2021/11/29 8:35 下午
 * @email：992116519@qq.com
 * @desc:
 */
class SelectWordAdapter(data: MutableList<WordLevelResponse>) :
        BaseQuickAdapter<WordLevelResponse, BaseViewHolder>(R.layout.item_select_word_layout, data) {

    override fun convert(holder: BaseViewHolder, item: WordLevelResponse) {
        val str= toChineseNumUtill.numberToChinese(item.level)
        holder.setText(R.id.level,"第${str}关")
        val boxContainer = holder.getViewOrNull<FlexboxLayout>(R.id.fl_container)
        boxContainer?.removeAllViews()
        for (index in 0 until item.words.size) {
            boxContainer?.addView(createItemTitleView(item.words[index]))
        }
    }

    private fun createItemTitleView(wordResponse: WordResponse): View? {
        val inflater = LayoutInflater.from(context)
        val wordView = inflater.inflate(R.layout.item_select_word_child_layout, null)
        val title = wordView.findViewById<TextView>(R.id.word)
        title.text = wordResponse.word
        return wordView
    }
}
