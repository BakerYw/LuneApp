package com.nyw.lune.ui.fragment.login

import android.os.Bundle
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.navigateAction
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.appViewModel
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.hideSoftKeyboard
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.app.ext.showMessage
import com.nyw.lune.app.util.CacheUtil
import com.nyw.lune.databinding.FragmentLoginLayoutBinding
import com.nyw.lune.viewmodel.request.RequestUserManageViewModel
import com.nyw.lune.viewmodel.state.LoginViewModel
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 登录
 */
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginLayoutBinding>() {
    private val requestViewModel: RequestUserManageViewModel by viewModels()
    override fun layoutId()=R.layout.fragment_login_layout
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.initClose("登录") {
            nav().navigateUp()
        }
    }

    override fun createObserver() {
        requestViewModel.loginResult.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                showToast("登录成功")
                CacheUtil.setIsLogin(true)
                CacheUtil.setToken(it.accessToken)
                CacheUtil.setUser(it)
                appViewModel.userInfo.value = it
                nav().navigateAction(R.id.action_loginfragment_to_mainfragment)
            }, {
                showToast(it.errorMsg)
            })
        })
    }


    inner class ProxyClick {
        fun clear() {
            mViewModel.username.set("")
        }

        fun login() {
            when {
                mViewModel.username.get().isEmpty() -> showMessage("请填写账号")
                mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                else ->requestViewModel.login(
                        mViewModel.username.get(),
                        mViewModel.password.get()
                )

            }
        }

        fun goRegister() {
            hideSoftKeyboard(activity)
            nav().navigateAction(R.id.action_loginfragment_to_regiestFragment)
        }


        fun goForgetPwd(){
            hideSoftKeyboard(activity)
            nav().navigateAction(R.id.action_loginfragment_to_forgetPwdFragment)
        }

        var onCheckedChangeListener =
                CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                    mViewModel.isShowPwd.set(isChecked)
                }
    }

}