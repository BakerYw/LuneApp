package com.nyw.lune.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nyw.lib_base.ext.nav
import com.nyw.lune.viewmodel.state.FeedbackMessageViewModel
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentFeedbackMessageBinding
import com.nyw.lune.databinding.FragmentReferenceMaterialBinding
import com.nyw.lune.viewmodel.state.ReferenceMaterialViewModel
import kotlinx.android.synthetic.main.include_toolbar.*

class FeedbackMessageFragment : BaseFragment<FeedbackMessageViewModel, FragmentFeedbackMessageBinding>(){

    override fun layoutId() = R.layout.fragment_feedback_message

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("意见反馈") {
            nav().navigateUp()
        }
    }

}


