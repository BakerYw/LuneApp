package com.nyw.lune.ui.activity

import android.content.Intent
import com.nyw.lune.R


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.ext.view.gone
import com.nyw.lib_base.ext.view.visible
import com.nyw.lune.app.base.BaseActivity
import com.nyw.lune.app.util.CacheUtil
import com.nyw.lune.app.util.SettingUtil
import com.nyw.lune.app.weight.banner.WelcomeBannerAdapter
import com.nyw.lune.app.weight.banner.WelcomeBannerViewHolder
import com.nyw.lune.databinding.ActivityWelBinding
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.activity_wel.*

/**
 * 启动页
 */
class WelActivity : BaseActivity<BaseViewModel, ActivityWelBinding>() {
    private var resList = arrayOf("读", "写", "单词狗")
    override fun layoutId() = R.layout.activity_wel
    private lateinit var mViewPager: BannerViewPager<String, WelcomeBannerViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        //防止出现按Home键回到桌面时，再次点击重新进入该界面bug
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT !== 0) {
            finish()
            return
        }
        mDatabind.click = ProxyClick()
        mViewPager = findViewById(R.id.banner_view)
        if (CacheUtil.isFirst()) {
            //是第一次打开App 显示引导页
            welcome_image.gone()
            mViewPager.apply {
                adapter = WelcomeBannerAdapter()
                setLifecycleRegistry(lifecycle)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        if (position == resList.size - 1) {
                            welcomeJoin.visible()
                        } else {
                            welcomeJoin.gone()
                        }
                    }
                })
                create(resList.toList())
            }
        } else {
            //不是第一次打开App 0.3秒后自动跳转到主页
            welcome_image.visible()
            mViewPager.postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                //带点渐变动画
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }, 300)
        }
    }

    inner class ProxyClick {
        fun toMain() {
            CacheUtil.setFirst(false)
            startActivity(Intent(this@WelActivity, MainActivity::class.java))
            finish()
            //带点渐变动画
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}