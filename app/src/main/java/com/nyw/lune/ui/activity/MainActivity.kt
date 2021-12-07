package com.nyw.lune.ui.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ConvertUtils
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseActivity
import com.nyw.lune.app.ext.init
import com.nyw.lune.app.util.SettingUtil
import com.nyw.lune.app.util.StatusBarUtil
import com.nyw.lune.app.weight.recyclerview.SpaceItemDecoration
import com.nyw.lune.data.model.bean.MainItem
import com.nyw.lune.databinding.ActivityErrorBinding
import com.nyw.lune.ui.adapter.MainItemAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<BaseViewModel, ActivityErrorBinding>() {
    override fun layoutId(): Int = R.layout.activity_main
    private val mainItemAdapter: MainItemAdapter by lazy { MainItemAdapter(arrayListOf()) }
    override fun initView(savedInstanceState: Bundle?) {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(SettingUtil.getColor(this)))
        StatusBarUtil.setColor(this, SettingUtil.getColor(this), 0)
        recycler_main.init(
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false),
            mainItemAdapter
        ).let {
            it.addItemDecoration(SpaceItemDecoration( ConvertUtils.dp2px(40f),0))
        }
        val list = listOf(
            MainItem(0, "系统消息", R.drawable.ic_menu_notice, R.drawable.shape_menu_notice),
            MainItem(1, "学习计划", R.drawable.ic_menu_study, R.drawable.shape_menu_study),
            MainItem(2, "词典搜索", R.drawable.ic_menu_search, R.drawable.shape_menu_search),
            MainItem(3, "反馈留言", R.drawable.ic_menu_feedback, R.drawable.shape_menu_feedback),
            MainItem(4, "添加",0,  R.drawable.ic_menu_add)
        )
        mainItemAdapter.setList(list)
    }
}