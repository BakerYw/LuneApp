package com.nyw.lune.ui.fragment.material

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.initClose
import com.nyw.lune.data.model.bean.response.CateMaterialResponse
import com.nyw.lune.databinding.FragmentMaterialDescBinding
import com.nyw.lune.viewmodel.request.RequestMaterialViewModel
import com.nyw.lune.viewmodel.state.MaterialDescViewModel
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_material_desc.*
import kotlinx.android.synthetic.main.include_toolbar.*


/**
 * 资料详情
 */
class MaterialDescFragment : BaseFragment<MaterialDescViewModel, FragmentMaterialDescBinding>() {
    private val requestViewModel: RequestMaterialViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_material_desc

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.run {
            getParcelable<CateMaterialResponse>("data")?.let {
                mViewModel.id.set(it.id)
                mViewModel.content.set(it.content)
                mViewModel.coverImg.set(it.coverImg)
                mViewModel.title.set(it.materialTitle)
            }
        }
        toolbar.initClose(mViewModel.title.get()) {
            nav().navigateUp()
        }


        requestViewModel.getMaterialDetail(mViewModel.id.get())


    }


    override fun createObserver() {
        requestViewModel.materialDetailState.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, {
                mViewModel.content.set(it.content)
                RichText
                    .fromHtml(mViewModel.content.get()) // 数据源
                    .autoFix(true) // 是否自动修复，默认true
                    .autoPlay(true) // gif图片是否自动播放
                    .scaleType(ImageHolder.ScaleType.fit_xy) // 图片缩放方式
                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT) // 图片占位区域的宽高
                    .into(tv_content)
            }, {
                showToast(it.errorMsg)
            })
        })
    }

    /**
     * 在应用退出时调用RichText.recycle()
     */
    override fun onDestroy() {
        super.onDestroy()
        RichText.clear(mActivity)
    }

}