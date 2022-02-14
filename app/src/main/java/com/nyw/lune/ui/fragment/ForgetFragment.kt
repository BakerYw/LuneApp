package com.nyw.lune.ui.fragment

import android.os.Bundle
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.ForgetFragmentBinding
import com.nyw.lune.viewmodel.state.ForgetViewModel
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 抗遗忘
 */
class ForgetFragment : BaseFragment<ForgetViewModel, ForgetFragmentBinding>(){
    override fun layoutId() = R.layout.forget_fragment

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("抗遗忘","什么是21天抗遗忘？"){

        }
    }

}