package com.nyw.lune.ui.fragment.feedback

import android.os.Bundle
import com.nyw.lib_base.ext.nav
import com.nyw.lune.viewmodel.state.FeedbackMessageViewModel
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentFeedbackMessageBinding
import kotlinx.android.synthetic.main.include_toolbar.*

class FeedbackMessageFragment : BaseFragment<FeedbackMessageViewModel, FragmentFeedbackMessageBinding>(){

    override fun layoutId() = R.layout.fragment_feedback_message

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("意见反馈") {
            nav().navigateUp()
        }
    }

}


