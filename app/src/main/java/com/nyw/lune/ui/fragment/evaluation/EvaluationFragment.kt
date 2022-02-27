package com.nyw.lune.ui.fragment.evaluation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ResourceUtils
import com.google.gson.reflect.TypeToken
import com.kongzue.dialogx.dialogs.CustomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.nyw.lib_base.base.viewmodel.BaseViewModel
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.navigateAction
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.init
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.data.model.bean.ClassMultiItem
import com.nyw.lune.data.model.bean.response.ClassResponse
import com.nyw.lune.databinding.FragmentEvaluationBinding
import com.nyw.lune.ui.adapter.SelectClassAdapter
import com.nyw.lune.viewmodel.state.EvaluationViewModel
import kotlinx.android.synthetic.main.fragment_evaluation.*
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 测评
 */
class EvaluationFragment : BaseFragment<EvaluationViewModel, FragmentEvaluationBinding>() {
    private var gradeDialog: CustomDialog? = null
    private var sexDialog: CustomDialog? = null

    override fun layoutId() = R.layout.fragment_evaluation

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.initClose("测评") {
            when (mViewModel.initPage.get()) {
                1 -> nav().navigateUp()
                2 -> mViewModel.initPage.set(1)
            }

        }
        initGradeDialog()
        initSexDialog()
    }

    inner class ProxyClick {

        fun submit() {
            when (mViewModel.initPage.get()) {
                1 -> initThePage(2)
                2 -> nav().navigateAction(R.id.action_evaluationFragment_to_testingFragment)
            }

        }


        fun cancel() {
            initThePage(1)
        }


        fun selectSex() {
            if (sexDialog != null) {
                sexDialog?.show()
            } else {
                initSexDialog()
                sexDialog?.show()
            }
        }

        fun select() {
            if (gradeDialog != null) {
                gradeDialog?.show()
            } else {
                initGradeDialog()
                gradeDialog?.show()
            }
        }

    }

    private fun initSexDialog() {
        sexDialog = CustomDialog.build()
            .setMaskColor(Color.parseColor("#4D000000"))
            .setCustomView(object : OnBindView<CustomDialog>(R.layout.dialog_layout_select_sex) {
                override fun onBind(dialog: CustomDialog, v: View) {
                    val btnMan: ImageView = v.findViewById(R.id.btn_man)
                    val btnWoman: ImageView = v.findViewById(R.id.btn_woman)
                    btnMan.setOnClickListener {
                        mViewModel.sex.set("男")
                        dialog.dismiss()
                    }
                    btnWoman.setOnClickListener {
                        mViewModel.sex.set("女")
                        dialog.dismiss()
                    }
                }
            })
    }

    private fun initGradeDialog() {
        gradeDialog = CustomDialog.build()
            .setMaskColor(Color.parseColor("#4D000000"))
            .setCustomView(object : OnBindView<CustomDialog>(R.layout.dialog_layout_select_class) {
                override fun onBind(dialog: CustomDialog, v: View) {
                    val btnOk: TextView = v.findViewById(R.id.btn_ok)
                    val btnCancel: TextView = v.findViewById(R.id.btn_cancel)
                    val recyclerView: RecyclerView = v.findViewById(R.id.recyclerView)
                    val list =
                        GsonUtils.fromJson<MutableList<ClassResponse>>(
                            ResourceUtils.readAssets2String(
                                "class.json"
                            ),
                            object : TypeToken<List<ClassResponse>>() {}.type
                        )
                    val data = mutableListOf<ClassMultiItem>()
                    for (i in 0 until list.size) {
                        data.add(
                            i,
                            ClassMultiItem(
                                list[i].className,
                                list[i].name,
                                list[i].isSelect,
                                list[i].type
                            )
                        )
                    }
                    var str = "学龄前"
                    val adapter = SelectClassAdapter(data) {
                        str = it.toString()
                    }
                    val layoutManage = GridLayoutManager(context, 3)
                    layoutManage.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (adapter.getItemViewType(position) == 2) {
                                1
                            } else {
                                3
                            }
                        }
                    }
                    recyclerView.init(layoutManage, adapter, false)
                    btnOk.setOnClickListener {
                        mViewModel.grade.set(str)
                        dialog.dismiss()
                    }
                    btnCancel.setOnClickListener { dialog.dismiss() }
                }
            })
    }

    private fun initThePage(page: Int) {
        if (page == 1) {
            mViewModel.initPage.set(page)
            mViewModel.submitTip.set("开始检测")
        } else {
            mViewModel.initPage.set(page)
            mViewModel.submitTip.set("下一步")
        }
    }

}