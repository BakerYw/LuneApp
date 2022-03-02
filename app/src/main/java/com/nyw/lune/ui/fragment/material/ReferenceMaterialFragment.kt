package com.nyw.lune.ui.fragment.material

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.navigateAction
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.*
import com.nyw.lune.app.weight.loadsir.core.LoadService
import com.nyw.lune.app.weight.recyclerview.DefineLoadMoreView
import com.nyw.lune.app.weight.recyclerview.SpaceItemDecoration
import com.nyw.lune.databinding.FragmentReferenceMaterialBinding
import com.nyw.lune.ui.adapter.MaterialCategoryAdapter
import com.nyw.lune.ui.adapter.MaterialProductAdapter
import com.nyw.lune.viewmodel.request.RequestMaterialViewModel
import com.nyw.lune.viewmodel.state.ReferenceMaterialViewModel
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.fragment_reference_material.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * 参考资料
 */
class ReferenceMaterialFragment : BaseFragment<ReferenceMaterialViewModel, FragmentReferenceMaterialBinding>() {
    private val requestViewModel: RequestMaterialViewModel by viewModels()
    private val categoryAdapter: MaterialCategoryAdapter by lazy { MaterialCategoryAdapter(arrayListOf()) }
    private val productAdapter: MaterialProductAdapter by lazy { MaterialProductAdapter(arrayListOf()) }

    //recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
    private lateinit var footView: DefineLoadMoreView

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    override fun layoutId() = R.layout.fragment_reference_material

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("参考资料") {
            nav().navigateUp()
        }
        //状态页配置
        loadsir = loadServiceInit(product_swipeRefresh) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestViewModel.getMaterialCateList(true, mViewModel.cateId.get())
        }
        search_view?.setEditTextHint(R.string.material_search_hint)
        category_list.init(LinearLayoutManager(context), categoryAdapter)
        categoryAdapter.setOnItemClickListener { adapter, view, position ->
            mViewModel.cateId.set(categoryAdapter.data[position].cateId)
            categoryAdapter.setSelect(position)
            requestViewModel.getMaterialCateList(true, mViewModel.cateId.get())
        }
        requestViewModel.getMaterialCate()
        product_list.init(GridLayoutManager(context, 3), productAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            footView = it.initFooter(SwipeRecyclerView.LoadMoreListener {
                requestViewModel.getMaterialCateList(false, mViewModel.cateId.get())
            })
        }
        //初始化 SwipeRefreshLayout
        product_swipeRefresh.init {
            //触发刷新监听时请求数据
            requestViewModel.getMaterialCateList(true, mViewModel.cateId.get())
        }
        productAdapter.setOnItemClickListener { adapter, view, position ->
            nav().navigateAction(R.id.action_referenceMaterialFragment_to_materialDescFragment, Bundle().apply {
                putParcelable("data", productAdapter.data[position])
            })
        }
    }


    override fun createObserver() {
        requestViewModel.materialData.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, {
                categoryAdapter.setList(it)
                categoryAdapter.notifyDataSetChanged()
                mViewModel.cateId.set(categoryAdapter.data[0].cateId)
                //设置界面 加载中
                loadsir.showLoading()
                requestViewModel.getMaterialCateList(true, mViewModel.cateId.get())
            }, {
                showToast(it.errorMsg)
            })
        })

        requestViewModel.mMaterialDataListState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, productAdapter, loadsir, product_list, product_swipeRefresh)
        })
    }

}