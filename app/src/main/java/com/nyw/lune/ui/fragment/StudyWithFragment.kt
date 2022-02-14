package com.nyw.lune.ui.fragment

import android.os.Bundle
import com.nyw.lune.R


import com.nyw.lib_base.ext.nav
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentStudyWithBinding
import com.nyw.lune.viewmodel.state.SearchViewModel
import com.nyw.lune.viewmodel.state.StudyWithViewModel
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * 一起学
 */
class StudyWithFragment : BaseFragment<StudyWithViewModel, FragmentStudyWithBinding>(){

    override fun layoutId() = R.layout.fragment_study_with

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("一起学"){
            nav().popBackStack()
        }
    }
}
