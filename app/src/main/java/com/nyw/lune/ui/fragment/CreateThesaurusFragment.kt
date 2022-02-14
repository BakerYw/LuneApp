package com.nyw.lune.ui.fragment

import android.os.Bundle
import com.nyw.lib_base.ext.nav
import com.nyw.lune.viewmodel.state.CreateThesaurusViewModel
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentCreateThesaurusBinding
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 创建词库
 */
class CreateThesaurusFragment : BaseFragment<CreateThesaurusViewModel, FragmentCreateThesaurusBinding>(){

    override fun layoutId() = R.layout.fragment_create_thesaurus

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("创建词库") {
            nav().navigateUp()
        }
    }

}