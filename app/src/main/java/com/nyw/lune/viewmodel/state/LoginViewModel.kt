package com.nyw.lune.viewmodel.state

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.databind.BooleanObservableField
import com.nyw.lib_base.callback.databind.StringObservableField

class LoginViewModel : BaseViewModel() {
    //用户名
    var username = StringObservableField()

    //密码(登录注册界面)
    var password = StringObservableField()

    //是否显示明文密码（登录注册界面）
    var isShowPwd = BooleanObservableField()


    //用户名清除按钮是否显示   不要在 xml 中写逻辑 所以逻辑判断放在这里
    var clearVisible = object : ObservableInt(username){
        override fun get(): Int {
            return if(username.get().isEmpty()){
                View.GONE
            }else{
                View.VISIBLE
            }
        }
    }


    var loginEnabled= object :ObservableBoolean(password){
        override fun get(): Boolean {
            return password.get().isNotEmpty() && password.get().length>5
        }
    }


    //密码显示按钮是否显示   不要在 xml 中写逻辑 所以逻辑判断放在这里
    var passwordVisible = object :ObservableInt(password){
        override fun get(): Int {
            return if(password.get().isEmpty()){
                View.GONE
            }else{
                View.VISIBLE
            }
        }
    }

}