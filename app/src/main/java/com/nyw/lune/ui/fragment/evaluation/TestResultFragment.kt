package com.nyw.lune.ui.fragment.evaluation

import android.os.Bundle
import com.nyw.lib_base.ext.nav
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentTestResultBinding
import com.nyw.lune.viewmodel.state.TestResultViewModel
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 测试结果
 */
class TestResultFragment  : BaseFragment<TestResultViewModel, FragmentTestResultBinding>()  {
    override fun layoutId() = R.layout.fragment_test_result
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.initClose("测评") {
            nav().navigateUp()
        }
    }

    inner class ProxyClick {
        fun complete(){

        }

        fun comeAgain(){

        }
    }

}