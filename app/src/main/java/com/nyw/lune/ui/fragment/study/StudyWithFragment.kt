package com.nyw.lune.ui.fragment.study


import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nyw.lib_base.ext.nav
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.bindViewPager2
import com.nyw.lune.app.ext.init
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentStudyWithBinding
import com.nyw.lune.viewmodel.state.StudyWithViewModel
import kotlinx.android.synthetic.main.fragment_study_with.*
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 一起学
 */
class StudyWithFragment : BaseFragment<StudyWithViewModel, FragmentStudyWithBinding>() {
    var titleData = arrayListOf("全部", "我分享", "我加入")

    private var fragments: ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(StudyWithChildFragment.newInstance(1))
        fragments.add(StudyWithChildFragment.newInstance(2))
        fragments.add(StudyWithChildFragment.newInstance(3))
    }

    override fun layoutId() = R.layout.fragment_study_with

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("一起学") {
            nav().navigateUp()
        }
        //初始化viewpager2
        view_pager.init(this, fragments).offscreenPageLimit = fragments.size
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager, mStringList = titleData)
    }
}
