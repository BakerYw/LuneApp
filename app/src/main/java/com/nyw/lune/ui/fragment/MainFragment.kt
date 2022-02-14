package com.nyw.lune.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ConvertUtils
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.navigateAction
import com.nyw.lune.R
import com.nyw.lune.app.appViewModel
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.init
import com.nyw.lune.app.weight.recyclerview.SpaceItemDecoration
import com.nyw.lune.data.model.bean.MainItem
import com.nyw.lune.databinding.FragmentMainLayoutBinding
import com.nyw.lune.ui.adapter.MainItemAdapter
import com.nyw.lune.viewmodel.state.MainViewModel
import kotlinx.android.synthetic.main.fragment_main_layout.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * 主页面
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainLayoutBinding>() {
    private val mainItemAdapter: MainItemAdapter by lazy { MainItemAdapter(arrayListOf()) }

    override fun layoutId() = R.layout.fragment_main_layout


    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        recycler_main.init(
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false),
            mainItemAdapter
        )
        val list = listOf(
            MainItem(0, "系统消息", R.drawable.ic_menu_notice),
            MainItem(1, "学习计划", R.drawable.ic_menu_study),
            MainItem(2, "词典搜索", R.drawable.ic_menu_search),
            MainItem(3, "反馈留言", R.drawable.ic_menu_feedback),
            MainItem(4, "添加", R.drawable.ic_menu_add)
        )
        mainItemAdapter.setList(list)
        mainItemAdapter.setOnItemClickListener { adapter, view, position ->
            when(position){
                0->nav().navigateAction(R.id.action_mainfragment_to_systemMessageFragment)
                1->nav().navigateAction(R.id.action_mainfragment_to_studyPlanFragment)
                2->nav().navigateAction(R.id.action_mainfragment_to_searchFragment)
                3->nav().navigateAction(R.id.action_mainfragment_to_feedbackMessageFragment)
                4->showToast("正在开发中")
            }
        }
    }

    override fun createObserver() {
        appViewModel.run {
            //监听账户信息是否改变 有值时(登录)将设置为主页，为空时(退出登录)，设置为登陆页面
            userInfo.observeInFragment(this@MainFragment, Observer {
                if (it != null) {

                }else{
                    nav().navigateAction(R.id.action_mainfragment_to_loginfragment)
                }
            })
        }
    }


    inner class ProxyClick {

        fun update(){
            showToast("正在更新中")
        }

        fun goLogin(){
            nav().navigateAction(R.id.action_mainfragment_to_loginfragment)
        }

        fun goScan(){
            nav().navigateAction(R.id.action_mainfragment_to_scanFragment)
        }

        fun goSearch(){
            nav().navigateAction(R.id.action_mainfragment_to_searchFragment)
        }

        fun goStudyWith(){
            nav().navigateAction(R.id.action_mainfragment_to_studyWithFragment)
        }

        fun goRememberWord(){
            nav().navigateAction(R.id.action_mainfragment_to_rememberWordFragment)
        }


        fun goForgetWord(){
            nav().navigateAction(R.id.action_mainfragment_to_forgetFragment)
        }

        fun goCreate(){
            nav().navigateAction(R.id.action_mainfragment_to_createThesaurusFragment)
        }


        fun goEvaluation(){
            nav().navigateAction(R.id.action_mainfragment_to_evaluationFragment)
        }


        fun goReference(){
            nav().navigateAction(R.id.action_mainfragment_to_referenceMaterialFragment)
        }


        fun goUserCenter(){
            nav().navigateAction(R.id.action_mainfragment_to_userCenterFragment)
        }
    }

}