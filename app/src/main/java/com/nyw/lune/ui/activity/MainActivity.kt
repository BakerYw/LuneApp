package com.nyw.lune.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ToastUtils
import com.nyw.lib_base.network.manager.NetState
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseActivity
import com.nyw.lune.app.util.StatusBarUtil
import com.nyw.lune.databinding.ActivityMainBinding
import com.nyw.lune.viewmodel.state.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    var exitTime = 0L
    var navHostController: NavController? = null
    override fun layoutId(): Int = R.layout.activity_main
    override fun initView(savedInstanceState: Bundle?) {

        navHostController = Navigation.findNavController(this, R.id.host_fragment)
        // 设置监听
        navHostController?.addOnDestinationChangedListener(onDestinationChangedListener)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity, R.id.host_fragment)
                if (nav.currentDestination != null
                        && nav.currentDestination!!.id != R.id.mainfragment) {
                    //如果当前界面不是主页，或者说不是登陆页面。那么直接调用返回即可
                    nav.navigateUp()
                } else {
                    //是主页
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtils.showShort("再按一次退出程序")
                        exitTime = System.currentTimeMillis()
                    } else {
                        finish()
                    }
                }
            }
        })
    }

    private val onDestinationChangedListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.mainfragment) {
                    StatusBarUtil.setLightMode(this)
                    StatusBarUtil.setColor(this, ColorUtils.getColor(R.color.colorPrimary), 1)
                } else {
                    StatusBarUtil.setDarkMode(this)
                    StatusBarUtil.setColor(this, ColorUtils.getColor(R.color.colorEF), 1)
                }
            }

    override fun onNetworkStateChanged(netState: NetState) {
        super.onNetworkStateChanged(netState)
        if (netState.isSuccess) {
            Toast.makeText(applicationContext, "我特么终于有网了啊!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "我特么怎么断网了!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除监听
        navHostController?.removeOnDestinationChangedListener(onDestinationChangedListener)
    }
}