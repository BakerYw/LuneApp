package com.nyw.lune.ui.activity

import android.content.ClipData
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.blankj.utilcode.util.ToastUtils
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.ext.util.clipboardManager
import com.nyw.lib_base.ext.view.clickNoRepeat
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseActivity
import com.nyw.lune.app.ext.init
import com.nyw.lune.app.ext.showMessage
import com.nyw.lune.app.util.SettingUtil
import com.nyw.lune.app.util.StatusBarUtil
import com.nyw.lune.databinding.ActivityErrorBinding
import kotlinx.android.synthetic.main.activity_error.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * 作者　: YuWen Nie
 * 时间　: 2020/3/12
 * 描述　:
 */
class ErrorActivity : BaseActivity<BaseViewModel, ActivityErrorBinding>() {

    override fun layoutId() = R.layout.activity_error

    override fun initView(savedInstanceState: Bundle?)  {
        toolbar.init("发生错误")
        supportActionBar?.setBackgroundDrawable(ColorDrawable(SettingUtil.getColor(this)))
        StatusBarUtil.setColor(this, SettingUtil.getColor(this), 0)
        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        errorRestart.clickNoRepeat{
            config?.run {
                CustomActivityOnCrash.restartApplication(this@ErrorActivity, this)
            }
        }
        errorSendError.clickNoRepeat {
            CustomActivityOnCrash.getStackTraceFromIntent(intent)?.let {
                showMessage(it,"发现有Bug不去打作者脸？","必须打",{
                    val mClipData = ClipData.newPlainText("errorLog",it)
                    // 将ClipData内容放到系统剪贴板里。
                    clipboardManager?.setPrimaryClip(mClipData)
                    ToastUtils.showShort("已复制错误日志")
                    try {
                        val url = "mqqwpa://im/chat?chat_type=wpa&uin=992116519"
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                        ToastUtils.showShort("请先安装QQ")
                    }
                },"我不敢")
            }
        }
    }
}