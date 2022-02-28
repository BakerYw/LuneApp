package com.nyw.lune.app.base

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.ToastUtils
import com.nyw.lib_base.base.fragment.BaseVmDbFragment
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lune.R
import com.nyw.lune.app.ext.dismissLoadingExt
import com.nyw.lune.app.ext.hideSoftKeyboard
import com.nyw.lune.app.ext.showLoadingExt


/**
 * 作者　: YuWen Nie
 * 时间　: 2019/12/21
 * 描述　: 你项目中的Fragment基类，在这里实现显示弹窗，吐司，还有自己的需求操作 ，如果不想用Databind，请继承
 * BaseVmFragment例如
 * abstract class BaseFragment<VM : BaseViewModel> : BaseVmFragment<VM>() {
 */
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbFragment<VM, DB>() {

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract override fun layoutId(): Int


    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法
     */
    override fun lazyLoadData() {}

    /**
     * 创建LiveData观察者 Fragment执行onViewCreated后触发
     */
    override fun createObserver() {}

    /**
     * Fragment执行onViewCreated后触发
     */
    override fun initData() {

    }

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dismissLoadingExt()
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(activity)
    }

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    override fun lazyLoadTime(): Long {
        return 300
    }


    open fun showToast(message: String) {
        ToastUtils.make().setGravity(Gravity.CENTER, 0, 0)
            .setTextSize(20)
            .setBgResource(R.drawable.shape_toast_bg)
            .setTextColor(Color.WHITE)
            .setDurationIsLong(false).show(message)
    }
}