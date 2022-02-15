package com.nyw.lune.viewmodel.state

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.databind.BooleanObservableField
import com.nyw.lib_base.callback.databind.IntObservableField
import com.nyw.lib_base.callback.databind.StringObservableField

class RegiestViewModel : BaseViewModel() {
    //班级
    var clbum = StringObservableField()
    //手机号
    var mobile = StringObservableField()
    //机构码
    var orgCode = StringObservableField()
    //密码(登录注册界面)
    var password = StringObservableField()
    //姓名
    var realName = StringObservableField()
    //学校
    var school = StringObservableField()
    //分数
    var score = StringObservableField()
    //验证码
    var smsCode = StringObservableField()
    //获取验证码提示语
    var codetip = StringObservableField()

    //是否显示明文密码（登录注册界面）
    var isShowPwd = BooleanObservableField()

    var mCodeColor = IntObservableField()


    var canGetCode = BooleanObservableField()


    var loginEnabled= object : ObservableBoolean(password){
        override fun get(): Boolean {
            return password.get().isNotEmpty() && password.get().length>5
        }
    }


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