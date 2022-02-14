package com.nyw.lune.ui.fragment

import android.os.Bundle
import com.nyw.lib_base.ext.nav
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.app.ext.loadServiceInit
import com.nyw.lune.app.ext.showEmpty
import com.nyw.lune.app.ext.showLoading
import com.nyw.lune.app.weight.loadsir.core.LoadService
import com.nyw.lune.databinding.FragmentStudyPlanBinding
import com.nyw.lune.viewmodel.state.StudyPlanViewModel
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 学习计划
 */
class StudyPlanFragment : BaseFragment<StudyPlanViewModel, FragmentStudyPlanBinding>(){
    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    override fun layoutId() = R.layout.fragment_study_plan

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("学习计划"){
            nav().popBackStack()
        }
        //状态页配置
        loadsir = loadServiceInit(mDatabind.includeList.swipeRefresh) {
            //点击重试时触发的操作
            loadsir.showEmpty()
        }
        loadsir.showEmpty()
    }
}
