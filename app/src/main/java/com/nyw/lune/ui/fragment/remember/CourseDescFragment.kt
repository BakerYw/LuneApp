package com.nyw.lune.ui.fragment.remember

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentCourseDescBinding
import com.nyw.lune.viewmodel.request.RequestRememberViewModel
import com.nyw.lune.viewmodel.state.CourseDescViewModel
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 课程详情
 */
class CourseDescFragment : BaseFragment<CourseDescViewModel, FragmentCourseDescBinding>(){
    private val requestViewModel: RequestRememberViewModel by viewModels()
    private var libId = 0
    override fun layoutId() = R.layout.fragment_course_desc

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        arguments?.let {
            libId = it.getInt("libId")
        }
        toolbar.initClose("词库详情") {
            nav().navigateUp()
        }
        requestViewModel.getCourseDesc(libId)
    }


    override fun createObserver() {
        requestViewModel.courseDescData.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, {
                it.run {
                    mViewModel.imageUrl.set(libImg)
                }

            }, {
                showToast(it.errorMsg)
            })
        })
    }


    inner class ProxyClick {
        fun buy(){
            showToast("点击我了")
        }
    }



}