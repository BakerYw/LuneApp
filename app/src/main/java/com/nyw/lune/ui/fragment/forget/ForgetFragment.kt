package com.nyw.lune.ui.fragment.forget

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kongzue.dialogx.dialogs.CustomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.navigateAction
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.appViewModel
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.app.util.CacheUtil
import com.nyw.lune.databinding.FragmentForgetLayoutBinding
import com.nyw.lune.viewmodel.request.RequestForgetViewModel
import com.nyw.lune.viewmodel.request.RequestMaterialViewModel
import com.nyw.lune.viewmodel.state.ForgetViewModel
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 抗遗忘
 */
class ForgetFragment : BaseFragment<ForgetViewModel, FragmentForgetLayoutBinding>() {
    private val requestViewModel: RequestForgetViewModel by viewModels()
    override fun layoutId() = R.layout.fragment_forget_layout

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        toolbar.initClose("抗遗忘", "什么是21天抗遗忘？") {
            when(it){
                0 -> nav().popBackStack()
                1 -> showDialog()
            }
        }
        requestViewModel.getPlanTrain()
    }


    override fun createObserver() {
        requestViewModel.planTrainResult.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                mViewModel.coin.set("${it.coin}")
                mViewModel.hasKnowWord.set("${it.hasKnowWord}")
                mViewModel.hasStudyWord.set("${it.hasStudyWord}")
            }, {
                showToast(it.errorMsg)
            })
        })
    }


    private fun showDialog() {
        CustomDialog.build().setMaskColor(Color.parseColor("#4D000000"))
                .setCustomView(object : OnBindView<CustomDialog>(R.layout.dialog_layout_forget) {
                    override fun onBind(dialog: CustomDialog, v: View) {
                        val btnOk: TextView = v.findViewById(R.id.btn_ok)
                        btnOk.setOnClickListener { dialog.dismiss() }
                    }
                }).show()
    }


}