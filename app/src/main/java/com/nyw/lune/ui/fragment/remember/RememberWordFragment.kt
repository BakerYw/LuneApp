package com.nyw.lune.ui.fragment.remember

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.init
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.app.weight.recyclerview.SpaceItemDecoration
import com.nyw.lune.data.model.bean.PageItem
import com.nyw.lune.data.model.bean.response.WordResponse
import com.nyw.lune.databinding.FragmentRememberWordBinding
import com.nyw.lune.ui.adapter.RememberWordAdapter
import com.nyw.lune.viewmodel.request.RequestRememberViewModel
import com.nyw.lune.viewmodel.state.RememberWordViewModel
import kotlinx.android.synthetic.main.fragment_remember_word.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlin.math.ceil

/**
 * 记单词
 */
class RememberWordFragment : BaseFragment<RememberWordViewModel, FragmentRememberWordBinding>() {
    private var libId = 0
    private var pageType = "level"
    private var levels: ArrayList<Int>? = ArrayList()
    private val requestViewModel: RequestRememberViewModel by viewModels()
    private var mAdapter: RememberWordAdapter?=null
    private var data: ArrayList<WordResponse>? = ArrayList()
    private var mItemCount = 7 //每页单词的总数
    private var mCurrentPage = 0 //当前第几页

    override fun layoutId() = R.layout.fragment_remember_word

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        toolbar.initClose("单词学习") {
            nav().navigateUp()
        }

        arguments?.let {
            pageType = it.getString("pageType", "")
            libId = it.getInt("libId", 0)
            levels = it.getIntegerArrayList("levels")
        }
        // pageType=="level" 代表直接点击关卡进入，反则代表选词进入
        if (pageType == "level") {
            requestViewModel.getLevelWords(libId, levels)
        } else {

        }

    }


    fun init(words: MutableList<WordResponse>) {
        data?.clear()
        data?.addAll(words)
        val pageItem = PageItem(data, mItemCount)
        mViewModel.pageStr.set("第" + (mCurrentPage + 1) + "页/共"
                + ceil(data?.size?.toDouble() as Double / 7).toInt() + "页")
        mAdapter= RememberWordAdapter(arrayListOf(), isLastOne()){ complete: Boolean, end: Boolean ->
            //代表学习完成了这也单词
            if (complete) {
                showToast("跳转到复习页面去")
            }
        }
        mAdapter?.let {
            recycler_view.init(LinearLayoutManager(context), it).let { recycle->
                recycle.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(10f)))
            }
        }
        mAdapter?.setList(pageItem.fistPage)
    }




    override fun createObserver() {
        requestViewModel.levelWordsResult.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, {
                init(it[0].words)
            }, {
                showToast(it.errorMsg)
            })
        })
    }

    /**
     * 判断是否为最后一页
     *
     * @return
     */
    private fun isLastOne(): Boolean {
        if (data.isNullOrEmpty()){
            return false
        }
        val i: Int = data?.size?.rem(mItemCount) ?: 0
        return if (i == 0) {
            mCurrentPage == (data?.size?.div(mItemCount) ?:0 ) - 1
        } else {
            mCurrentPage == data?.size?.div(mItemCount) ?:0
        }
    }

}