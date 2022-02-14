package com.nyw.lune.ui.fragment

import android.os.Bundle
import com.nyw.lib_base.ext.nav
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentRememberWordBinding
import com.nyw.lune.viewmodel.state.RememberWordViewModel
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * 记单词
 */
class RememberWordFragment : BaseFragment<RememberWordViewModel, FragmentRememberWordBinding>(){

    override fun layoutId() = R.layout.fragment_remember_word

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("记单词") {
            nav().navigateUp()
        }
    }

}
