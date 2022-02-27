package com.nyw.lune.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import com.blankj.utilcode.util.ScreenUtils
import com.kongzue.dialogx.dialogs.BottomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.nyw.lib_base.ext.nav
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentSearchBinding
import com.nyw.lune.viewmodel.state.SearchViewModel
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 查单词
 */
class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    override fun layoutId() = R.layout.fragment_search

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.initClose("查单词") {
            nav().popBackStack()
        }
    }

    inner class ProxyClick {
        fun showDialog() {
            BottomDialog.show(object :
                OnBindView<BottomDialog?>(R.layout.layout_search_bottom_dialog) {
                override fun onBind(dialog: BottomDialog?, v: View?) {
                    var imageView = v?.findViewById<ImageView>(R.id.iv_view)
                    var animationSet = AnimationSet(true)
                    val translateAnimation = TranslateAnimation(
                        // X轴的开始位置
                        Animation.RELATIVE_TO_SELF, -1f,
                        // X轴的结束位置
                        Animation.RELATIVE_TO_SELF, ScreenUtils.getScreenWidth().toFloat()/10,
                        // Y轴的开始位置
                        Animation.RELATIVE_TO_SELF, 0f,
                        // Y轴的结束位置
                        Animation.RELATIVE_TO_SELF, 0f)
                    translateAnimation.duration = 1000
                    translateAnimation.repeatCount = 2000  //  设置动画重复次数
//                    translateAnimation.repeatMode = Animation.REVERSE
                    translateAnimation.repeatMode = Animation.RESTART   //重新从头执行
                    translateAnimation.repeatMode = Animation.REVERSE  //反方向执行
                    animationSet.addAnimation(translateAnimation)
                    imageView?.animation = animationSet
                }
            })
        }
    }
}