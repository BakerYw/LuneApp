package com.nyw.lune.ui.fragment.remember

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.init
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.app.util.NumberUtils.toChineseNumUtill
import com.nyw.lune.app.weight.recyclerview.SpaceItemDecoration
import com.nyw.lune.data.model.bean.LevelItem
import com.nyw.lune.databinding.FragmentSelectWordBinding
import com.nyw.lune.ui.adapter.SelectLevelAdapter
import com.nyw.lune.ui.adapter.SelectWordAdapter
import com.nyw.lune.viewmodel.request.RequestRememberViewModel
import com.nyw.lune.viewmodel.state.SelectWordViewModel
import kotlinx.android.synthetic.main.fragment_select_level.*
import kotlinx.android.synthetic.main.fragment_select_word.*
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 选择单词
 */
class SelectWordFragment : BaseFragment<SelectWordViewModel, FragmentSelectWordBinding>() {
    private var libId = 0
    private var levels:ArrayList<Int>?=ArrayList()
    private val requestViewModel: RequestRememberViewModel by viewModels()
    private val mAdapter: SelectWordAdapter by lazy { SelectWordAdapter(arrayListOf()) }
    override fun layoutId() = R.layout.fragment_select_word
    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("选择单词") {
            nav().navigateUp()
        }
        arguments?.let {
            libId = it.getInt("libId")
            levels = it.getIntegerArrayList("levels")
        }
        recycler_view.init(LinearLayoutManager(context), mAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(10f)))
        }
        requestViewModel.getLevelWords(libId, levels)
    }


    override fun createObserver() {
        requestViewModel.levelWordsResult.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, {
                mAdapter.setList(it)
            }, {
                showToast(it.errorMsg)
            })
        })
    }
}