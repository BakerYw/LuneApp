package com.nyw.lune.ui.fragment

import android.os.Bundle
import com.nyw.lib_base.ext.nav
import com.nyw.lune.viewmodel.state.ForgetPwdViewModel
import com.nyw.lune.R
import com.nyw.lune.app.appViewModel
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentRetrievePwdLayoutBinding
import com.nyw.lune.viewmodel.state.RetrieveViewModel
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * 找回密码
 */
class RetrievePwdFragment : BaseFragment<RetrieveViewModel, FragmentRetrievePwdLayoutBinding>(){

    override fun layoutId() = R.layout.fragment_retrieve_pwd_layout

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("找回密码") {
            nav().navigateUp()
        }
    }

}