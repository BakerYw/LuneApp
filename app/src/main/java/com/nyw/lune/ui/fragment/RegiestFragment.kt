package com.nyw.lune.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ResourceUtils
import com.google.gson.reflect.TypeToken
import com.kongzue.dialogx.dialogs.CustomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.init
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.data.model.bean.ClassInfo
import com.nyw.lune.data.model.bean.ClassMultiItem
import com.nyw.lune.databinding.FragmentRegiestLayoutBinding
import com.nyw.lune.ui.adapter.SelectClassAdapter
import com.nyw.lune.viewmodel.request.RequestUserManageViewModel
import com.nyw.lune.viewmodel.state.RegiestViewModel
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 注册
 */
class RegiestFragment : BaseFragment<RegiestViewModel, FragmentRegiestLayoutBinding>() {
    private val requestViewModel: RequestUserManageViewModel by viewModels()
    private var countDownTimer: CountDownTimer? = null
    private var dialog: CustomDialog? = null

    override fun layoutId() = R.layout.fragment_regiest_layout

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.initClose("注册") {
            nav().navigateUp()
        }
        initCodeText()
        //获取成功开始倒计时
        countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {  //正在计时，按钮不可点击；
                mViewModel.codetip.set(if (millisUntilFinished / 1000 < 10) "0" + millisUntilFinished / 1000 + "s" else "" + millisUntilFinished / 1000 + "s")
                mViewModel.canGetCode.set(true)
                mViewModel.mCodeColor.set(ColorUtils.getColor(R.color.colorGray))
            }

            override fun onFinish() { //计时完成，按钮状态恢复可点击；
                initCodeText()
            }
        }
        initDialog()
    }

    private fun initDialog() {
        dialog = CustomDialog.build()
                .setMaskColor(Color.parseColor("#4D000000"))
                .setCustomView(object : OnBindView<CustomDialog>(R.layout.dialog_layout_select_class) {
                    override fun onBind(dialog: CustomDialog, v: View) {
                        val btnOk: TextView = v.findViewById(R.id.btn_ok)
                        val btnCancel: TextView = v.findViewById(R.id.btn_cancel)
                        val recyclerView: RecyclerView = v.findViewById(R.id.recyclerView)
                        val list = GsonUtils.fromJson<MutableList<ClassInfo>>(ResourceUtils.readAssets2String("class.json"),
                                object : TypeToken<List<ClassInfo>>() {}.type)
                        val data= mutableListOf<ClassMultiItem>()
                        for (i in 0 until list.size){
                            data.add(i, ClassMultiItem(list[i].className,list[i].name,list[i].isSelect,list[i].type))
                        }
                        var str="学龄前"
                        val adapter = SelectClassAdapter(data){
                            str=it.toString()
                        }
                        val layoutManage = GridLayoutManager(context, 3)
                        layoutManage.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return if (adapter.getItemViewType(position) == 2){
                                    1
                                }else{
                                    3
                                }
                            }
                        }
                        recyclerView.init(layoutManage, adapter, false)
                        btnOk.setOnClickListener {
                            mViewModel.clbum.set(str)
                            dialog.dismiss()
                        }
                        btnCancel.setOnClickListener { dialog.dismiss() }
                    }
                })
    }


    private fun initCodeText() {
        mViewModel.mCodeColor.set(ColorUtils.getColor(R.color.colorAccent))
        mViewModel.codetip.set("获取验证码")
        mViewModel.canGetCode.set(true)
    }


    override fun createObserver() {
        requestViewModel.commonResult.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                showToast("获取成功")
                countDownTimer?.start()
            }, {
                showToast(it.errorMsg)
            })
        })
        requestViewModel.registerResult.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                showToast("注册成功")
                nav().popBackStack()
            }, {
                showToast(it.errorMsg)
            })
        })
    }


    inner class ProxyClick {
        fun regiest() {
            requestViewModel.register(
                    mViewModel.mobile.get(),//账户名默认为手机号
                    mViewModel.clbum.get(),
                    mViewModel.school.get(),
                    mViewModel.mobile.get(),
                    mViewModel.orgCode.get(),
                    mViewModel.password.get(),
                    mViewModel.realName.get(),
                    mViewModel.school.get(),
                    mViewModel.score.get(),
                    mViewModel.smsCode.get())
        }

        fun getCode() {
            if (mViewModel.mobile.get().isNullOrEmpty()) {
                showToast("手机号不能为空")
                return
            }
            if (mViewModel.mobile.get().length != 11) {
                showToast("请输入正确的手机号")
                return
            }
            requestViewModel.getCode(mViewModel.mobile.get())
        }

        fun select() {
            if (dialog != null) {
                dialog?.show()
            } else {
                initDialog()
                dialog?.show()
            }
        }

        var onCheckedChangeListener =
                CompoundButton.OnCheckedChangeListener { _, isChecked ->
                    mViewModel.isShowPwd.set(isChecked)
                }
    }

}