package com.nyw.lune.ui.fragment.login

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ColorUtils
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentRetrievePwdLayoutBinding
import com.nyw.lune.viewmodel.request.RequestUserManageViewModel
import com.nyw.lune.viewmodel.state.RetrieveViewModel
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * 找回密码
 */
class RetrievePwdFragment : BaseFragment<RetrieveViewModel, FragmentRetrievePwdLayoutBinding>(){
    private val requestViewModel: RequestUserManageViewModel by viewModels()
    private var countDownTimer: CountDownTimer? = null
    override fun layoutId() = R.layout.fragment_retrieve_pwd_layout

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.initClose("找回密码") {
            nav().navigateUp()
        }
        initCodeText()
        //获取成功开始倒计时
        countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {  //正在计时，按钮不可点击；
                mViewModel.codetip.set(if (millisUntilFinished / 1000 < 10) "0" + millisUntilFinished / 1000 + "s" else "" + millisUntilFinished / 1000 + "s")
                mViewModel.canGetCode.set(true)
                mViewModel.mCodeColor.set(ColorUtils.getColor(R.color.colorGray))
            }

            override fun onFinish() { //计时完成，按钮状态恢复可点击；
                initCodeText()
            }
        }
    }

    private fun initCodeText() {
        mViewModel.mCodeColor.set(ColorUtils.getColor(R.color.colorAccent))
        mViewModel.codetip.set("获取验证码")
        mViewModel.canGetCode.set(true)
    }


    override fun createObserver() {
        requestViewModel.commonResult.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                showToast("获取成功")
                countDownTimer?.start()
            }, {
                showToast(it.errorMsg)
            })
        })
        requestViewModel.retrieveResult.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                showToast("修改成功")
                nav().popBackStack()
            }, {
                showToast(it.errorMsg)
            })
        })
    }

    inner class ProxyClick {
        fun submit(){

        }
        fun getCode() {
            if (mViewModel.mobile.get().isNullOrEmpty()) {
                showToast("手机号不能为空")
                return
            }
            if (mViewModel.mobile.get().length != 11) {
                showToast("请输入正确的手机号")
                return
            }
            requestViewModel.getCode(mViewModel.mobile.get())
        }

        var onCheckedChangeListener =
                CompoundButton.OnCheckedChangeListener { _, isChecked ->
                    mViewModel.isShowPwd.set(isChecked)
                }
    }

}