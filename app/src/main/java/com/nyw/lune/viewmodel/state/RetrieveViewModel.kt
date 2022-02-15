package com.nyw.lune.viewmodel.state

import android.view.View
import androidx.databinding.ObservableInt
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.databind.BooleanObservableField
import com.nyw.lib_base.callback.databind.IntObservableField
import com.nyw.lib_base.callback.databind.StringObservableField

class RetrieveViewModel : BaseViewModel() {

    //手机号
    var mobile = StringObservableField()
    //密码(登录注册界面)
    var password = StringObservableField()
    //密码(登录注册界面)
    var password2 = StringObservableField()
    //验证码
    var smsCode = StringObservableField()
    //获取验证码提示语
    var codetip = StringObservableField()

    //是否显示明文密码（登录注册界面）
    var isShowPwd = BooleanObservableField()

    var mCodeColor = IntObservableField()


    var canGetCode = BooleanObservableField()


    //密码显示按钮是否显示   不要在 xml 中写逻辑 所以逻辑判断放在这里
    var passwordVisible = object : ObservableInt(password){
        override fun get(): Int {
            return if(password.get().isEmpty()){
                View.GONE
            }else{
                View.VISIBLE
            }
        }
    }
}