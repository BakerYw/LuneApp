package com.nyw.lune.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nyw.lib_base.ext.nav
import com.nyw.lune.viewmodel.state.EvaluationViewModel
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentEvaluationBinding
import com.nyw.lune.databinding.FragmentFeedbackMessageBinding
import com.nyw.lune.viewmodel.state.FeedbackMessageViewModel
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 测评
 */

class EvaluationFragment : BaseFragment<EvaluationViewModel, FragmentEvaluationBinding>(){

    override fun layoutId() = R.layout.fragment_evaluation

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("测评") {
            nav().navigateUp()
        }
    }

}