package com.nyw.lune.ui.fragment.remember

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.navigateAction
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.app.ext.showMessage
import com.nyw.lune.app.weight.CollectView
import com.nyw.lune.databinding.FragmentCourseDescBinding
import com.nyw.lune.viewmodel.request.RequestRememberViewModel
import com.nyw.lune.viewmodel.state.CourseDescViewModel
import kotlinx.android.synthetic.main.fragment_course_desc.*
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 课程详情
 */
class CourseDescFragment : BaseFragment<CourseDescViewModel, FragmentCourseDescBinding>() {
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
                    mViewModel.libName.set(libName)
                    mViewModel.nickName.set("作者：$nickName")
                    mViewModel.wordCount.set("单词个数：$wordCount")
                    mViewModel.joinerCount.set("参与人数：$joinerCount")
                    val text =
                        "<font color='#999999'>词库价格</font>&nbsp&nbsp<font color='red'>$price</font>"
                    tv_price.text = Html.fromHtml(text)
                    mViewModel.libDesc.set("词库说明：$libDesc")
                    mViewModel.isCollect.set(isCollect)
                    collect.isChecked = isCollect
                    mViewModel.isBuy.set(join)
                    tv_buy.text =
                        if (join) getString(R.string.course_study) else getString(R.string.course_buy)
                }
            }, {
                showToast(it.errorMsg)
            })
        })
    }


    inner class ProxyClick {
        fun buyAndStudy() {
            if (mViewModel.isBuy.get()) {
                nav().navigateAction(
                    R.id.action_courseDescFragment_to_selectLevelFragment,
                    Bundle().apply {
                        putInt("libId", libId)
                    })
            }else{
                showMessage("支付正在开发中")
            }
        }
    }


}