package com.nyw.lune.ui.fragment.scan

import android.os.Bundle
import com.nyw.lib_base.ext.nav
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.databinding.FragmentScanBinding
import com.nyw.lune.viewmodel.state.ScanViewModel
import com.zhangke.qrcodeview.QRCodeView.OnQRCodeRecognitionListener
import kotlinx.android.synthetic.main.fragment_scan.*
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 二维码扫码
 */
class ScanFragment : BaseFragment<ScanViewModel, FragmentScanBinding>(){

    override fun layoutId() = R.layout.fragment_scan

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("创建词库"){
            nav().popBackStack()
        }
        codeView?.onQRCodeListener = OnQRCodeRecognitionListener { result ->
          showToast(result.toString())
        }
        selectView.setOnClickListener {

        }
    }


    override fun onResume() {
        super.onResume()
        if (codeView!=null){
            codeView?.startPreview()
        }
    }



    override fun onPause() {
        if (codeView!=null){
            codeView?.stopPreview()
        }
        super.onPause()
    }
}