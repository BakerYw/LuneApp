package com.nyw.lune.ui.fragment.remember

import android.os.Bundle
import com.nyw.lib_base.ext.nav
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentSelectWordBinding
import com.nyw.lune.viewmodel.state.SelectWordViewModel
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 选择单词
 */
class SelectWordFragment : BaseFragment<SelectWordViewModel, FragmentSelectWordBinding>() {
    override fun layoutId() = R.layout.fragment_select_word

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("选择单词") {
            nav().navigateUp()
        }
    }

}