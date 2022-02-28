package com.nyw.lune.ui.fragment.message

import android.os.Bundle
import com.nyw.lib_base.ext.nav
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentSystemMessageBinding
import com.nyw.lune.viewmodel.state.SystemMessageViewModel
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * 系统消息
 */
class SystemMessageFragment : BaseFragment<SystemMessageViewModel, FragmentSystemMessageBinding>(){

    override fun layoutId() = R.layout.fragment_system_message

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("系统消息"){
            nav().popBackStack()
        }
    }
}
