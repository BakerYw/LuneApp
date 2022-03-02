package com.nyw.lune.ui.fragment.study

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.navigateAction
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.eventViewModel
import com.nyw.lune.app.ext.*
import com.nyw.lune.app.weight.loadsir.core.LoadService
import com.nyw.lune.app.weight.recyclerview.DefineLoadMoreView
import com.nyw.lune.app.weight.recyclerview.SpaceItemDecoration
import com.nyw.lune.databinding.FragmentStudyWithChildBinding
import com.nyw.lune.ui.adapter.StudyWithAdapter
import com.nyw.lune.viewmodel.request.RequestStudyWithViewModel
import com.nyw.lune.viewmodel.state.StudyWithChildViewModel
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.include_recyclerview.*

class StudyWithChildFragment : BaseFragment<StudyWithChildViewModel, FragmentStudyWithChildBinding>() {
    private var queryType = 1

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
    private lateinit var footView: DefineLoadMoreView

    //适配器
    private val mAdapter: StudyWithAdapter by lazy { StudyWithAdapter(arrayListOf()) }
    private val requestViewModel: RequestStudyWithViewModel by viewModels()

    companion object {
        fun newInstance(queryType: Int): StudyWithChildFragment {
            return StudyWithChildFragment().apply {
                arguments = Bundle().apply {
                    putInt("queryType",queryType)
                }
            }
        }
    }

    override fun layoutId() = R.layout.fragment_study_with_child

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            queryType = it.getInt("queryType", 1)
            Log.e("nie","$queryType")
        }
        //状态页配置
        loadsir = loadServiceInit(swipeRefresh) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestViewModel.getTogetherList(true, queryType)
        }
        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), mAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            footView = it.initFooter(SwipeRecyclerView.LoadMoreListener {
                requestViewModel.getTogetherList(false, queryType)
            })
        }
        //初始化 SwipeRefreshLayout
        swipeRefresh.init {
            //触发刷新监听时请求数据
            requestViewModel.getTogetherList(true, queryType)
        }
        mAdapter.run {
//            setCollectClick { item, v, position ->
//                if (v.isChecked) {
//                    requestCollectViewModel.uncollect(item.id)
//                } else {
//                    requestCollectViewModel.collect(item.id)
//                }
//            }
            setOnItemClickListener { adapter, view, position ->

            }
        }
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        requestViewModel.getTogetherList(true, queryType)
    }


    override fun createObserver() {
        //默认监听
        eventViewModel.toLoginEvent.observeInFragment(this, Observer {
            if (it) {
                nav().navigateAction(R.id.action_to_loginfragment)
            }
        })
        requestViewModel.mChildDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, mAdapter, loadsir, recyclerView, swipeRefresh)
        })
    }
}