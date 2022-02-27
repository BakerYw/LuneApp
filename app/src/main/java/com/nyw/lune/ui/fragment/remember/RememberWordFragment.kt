package com.nyw.lune.ui.fragment.remember

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.*
import com.nyw.lune.app.weight.loadsir.core.LoadService
import com.nyw.lune.app.weight.recyclerview.DefineLoadMoreView
import com.nyw.lune.app.weight.recyclerview.SpaceItemDecoration
import com.nyw.lune.databinding.FragmentRememberWordBinding
import com.nyw.lune.ui.adapter.RememberCategoryAdapter
import com.nyw.lune.ui.adapter.RememberProductAdapter
import com.nyw.lune.viewmodel.request.RequestRememberViewModel
import com.nyw.lune.viewmodel.state.RememberWordViewModel
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.fragment_remember_word.category_list
import kotlinx.android.synthetic.main.fragment_remember_word.product_list
import kotlinx.android.synthetic.main.fragment_remember_word.product_swipeRefresh
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * 记单词
 */
class RememberWordFragment : BaseFragment<RememberWordViewModel, FragmentRememberWordBinding>(){
    private val requestViewModel: RequestRememberViewModel by viewModels()
    private val rememberCategoryAdapter: RememberCategoryAdapter by lazy { RememberCategoryAdapter(arrayListOf()) }
    private val rememberProductAdapter: RememberProductAdapter by lazy { RememberProductAdapter(arrayListOf()) }
    //recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
    private lateinit var footView: DefineLoadMoreView
    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    override fun layoutId() = R.layout.fragment_remember_word

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("记单词") {
            nav().navigateUp()
        }
        //状态页配置
        loadsir = loadServiceInit(product_swipeRefresh) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestViewModel.getTagClassDataList(true,mViewModel.tagId.get())
        }
        category_list.init(LinearLayoutManager(context), rememberCategoryAdapter)
        rememberCategoryAdapter.setOnItemClickListener { adapter, view, position ->
            rememberCategoryAdapter.setSelect(position)
            mViewModel.tagId.set(rememberCategoryAdapter.data[position].tagId)
            requestViewModel.getTagClassDataList(true,mViewModel.tagId.get())
        }
        product_list.init(GridLayoutManager(context,3), rememberProductAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            footView = it.initFooter(SwipeRecyclerView.LoadMoreListener {
                requestViewModel.getTagClassDataList(false, mViewModel.tagId.get())
            })
        }
        //初始化 SwipeRefreshLayout
        product_swipeRefresh.init {
            //触发刷新监听时请求数据
            requestViewModel.getTagClassDataList(true, mViewModel.tagId.get())
        }
        requestViewModel.getTag()
    }



    override fun createObserver() {
        requestViewModel.tagData.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, {
                rememberCategoryAdapter.setList(it)
                //设置界面 加载中
                loadsir.showLoading()
                mViewModel.tagId.set(rememberCategoryAdapter.data[0].tagId)
                requestViewModel.getTagClassDataList(true, mViewModel.tagId.get())
            }, {
                showToast(it.errorMsg)
            })
        })

        requestViewModel.mTagClassDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, rememberProductAdapter, loadsir, product_list, product_swipeRefresh)
        })
    }
}
