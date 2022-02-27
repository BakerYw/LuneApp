package com.nyw.lune.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.nyw.lib_base.ext.nav
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.init
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.data.model.bean.MainItem
import com.nyw.lune.databinding.FragmentUserCenterBinding
import com.nyw.lune.ui.adapter.MineItemAdapter
import com.nyw.lune.viewmodel.state.UserCenterViewModel
import kotlinx.android.synthetic.main.fragment_user_center.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * 个人中心
 */
class UserCenterFragment : BaseFragment<UserCenterViewModel, FragmentUserCenterBinding>() {
    private val mAdapter: MineItemAdapter by lazy { MineItemAdapter(arrayListOf()) }

    override fun layoutId() = R.layout.fragment_user_center

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("个人中心") {
            nav().popBackStack()
        }
        recycler_view.init(
                GridLayoutManager(context, 3),
                mAdapter
        )
        val list = listOf(
                MainItem(0, "抗遗忘", R.drawable.ic_mine_forget),
                MainItem(1, "生词本", R.drawable.ic_mine_newword),
                MainItem(2, "一起学", R.drawable.ic_mine_studywith),
                MainItem(3, "我的词库", R.drawable.ic_mine_words),
                MainItem(4, "我的训练", R.drawable.ic_mine_test),
                MainItem(5, "我的检测", R.drawable.ic_mine_check),
                MainItem(6, "我的收藏", R.drawable.ic_mine_collection)
        )
        mAdapter.setList(list)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            showToast(mAdapter.data[position].title)
        }

    }
}
