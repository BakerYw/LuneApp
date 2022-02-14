package com.nyw.lune.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nyw.lib_base.ext.nav
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentReferenceMaterialBinding
import com.nyw.lune.databinding.FragmentRememberWordBinding
import com.nyw.lune.viewmodel.state.ReferenceMaterialViewModel
import com.nyw.lune.viewmodel.state.RememberWordViewModel
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * 参考资料
 */
class ReferenceMaterialFragment : BaseFragment<ReferenceMaterialViewModel, FragmentReferenceMaterialBinding>(){

    override fun layoutId() = R.layout.fragment_reference_material

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("参考资料") {
            nav().navigateUp()
        }
    }

}