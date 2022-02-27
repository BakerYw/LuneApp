package com.nyw.lune.viewmodel.state

import android.view.View
import androidx.databinding.ObservableInt
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.callback.databind.BooleanObservableField
import com.nyw.lib_base.callback.databind.IntObservableField
import com.nyw.lib_base.callback.databind.StringObservableField

class EvaluationViewModel : BaseViewModel() {
    var initPage=IntObservableField() //1代表默认第一个页面  2代表第二页面
    var submitTip = StringObservableField()
    var cancelTip = StringObservableField()
    //姓名
    var realName = StringObservableField()
    var sex = StringObservableField()
    var grade = StringObservableField()
    //学校
    var school = StringObservableField()
    //分数
    var score = StringObservableField()

    //用户名清除按钮是否显示   不要在 xml 中写逻辑 所以逻辑判断放在这里
    var firstVisible = object : ObservableInt(initPage){
        override fun get(): Int {
            return if(initPage.get()==1){
                View.VISIBLE
            }else{
                View.GONE
            }
        }
    }


    var secondVisible = object : ObservableInt(initPage){
        override fun get(): Int {
            return if(initPage.get()==2){
                View.VISIBLE
            }else{
                View.GONE
            }
        }
    }

    init {
        initPage.set(1)
        submitTip.set("开始检测")
        cancelTip.set("取消")
    }
}