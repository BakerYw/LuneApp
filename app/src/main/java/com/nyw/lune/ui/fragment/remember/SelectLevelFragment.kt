package com.nyw.lune.ui.fragment.remember

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.navigateAction
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.init
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.app.util.NumberUtils.toChineseNumUtill
import com.nyw.lune.app.weight.recyclerview.SpaceItemDecoration
import com.nyw.lune.data.model.bean.LevelItem
import com.nyw.lune.databinding.FragmentSelectLevelBinding
import com.nyw.lune.ui.adapter.SelectLevelAdapter
import com.nyw.lune.viewmodel.request.RequestRememberViewModel
import com.nyw.lune.viewmodel.state.SelectLevelViewModel
import kotlinx.android.synthetic.main.fragment_select_level.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * 选择关卡
 */
class SelectLevelFragment : BaseFragment<SelectLevelViewModel, FragmentSelectLevelBinding>() {
    private var libId = 0
    private val requestViewModel: RequestRememberViewModel by viewModels()
    override fun layoutId() = R.layout.fragment_select_level
    private val mAdapter: SelectLevelAdapter by lazy { SelectLevelAdapter(arrayListOf()) }
    private val mList: ArrayList<LevelItem> = ArrayList()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.initClose("选择关卡") {
            nav().navigateUp()
        }
        arguments?.let {
            libId = it.getInt("libId")
        }
        requestViewModel.getLevelDesc(libId)
        level_recycle.init(LinearLayoutManager(context), mAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(10f)))
        }
    }

    override fun createObserver() {
        requestViewModel.courseLevelData.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, {
                it.run {
                    mViewModel.totalLevel.set(totalLevel.toString())
                    mViewModel.wordCount.set(wordCount.toString())
                    mViewModel.studyLevels.set(studyLevels.size.toString())
                    mList.clear()
                    for (index in 1..totalLevel) {
                        val text: String = toChineseNumUtill.numberToChinese(index)
                        mList.add(LevelItem(index, text, isStudy = false, isSelect = false))
                    }
                    mAdapter.setList(mList)
                }
            }, {
                showToast(it.errorMsg)
            })
        })
    }

    inner class ProxyClick {
        fun toSelectWord(){
            nav().navigateAction(
                R.id.action_selectLevelFragment_to_selectWordFragment,
                Bundle().apply {
                    putInt("libId", libId)
                })
        }
    }

}