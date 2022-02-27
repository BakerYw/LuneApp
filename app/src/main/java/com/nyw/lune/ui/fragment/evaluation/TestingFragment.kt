package com.nyw.lune.ui.fragment.evaluation

import android.os.Bundle
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.navigateAction
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentTestingBinding
import com.nyw.lune.viewmodel.state.TestingViewModel
import kotlinx.android.synthetic.main.fragment_testing.*
import kotlinx.android.synthetic.main.include_toolbar.*

class TestingFragment : BaseFragment<TestingViewModel, FragmentTestingBinding>() {

    override fun layoutId() = R.layout.fragment_testing

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("测评") {
            nav().popBackStack()
        }
        goResult.setOnClickListener {
            nav().navigateAction(R.id.action_testingFragment_to_testResultFragment)
        }
    }
}