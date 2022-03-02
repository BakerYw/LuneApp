package com.nyw.lune.ui.fragment.search

import android.Manifest
import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.kongzue.dialogx.dialogs.BottomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.nyw.lib_base.ext.nav
import com.nyw.lib_base.ext.parseState
import com.nyw.lune.R
import com.nyw.lune.app.base.BaseFragment
import com.nyw.lune.app.ext.*
import com.nyw.lune.app.util.FileBase64
import com.nyw.lune.app.weight.WaveView
import com.nyw.lune.app.weight.loadsir.core.LoadService
import com.nyw.lune.app.weight.recyclerview.SpaceItemDecoration
import com.nyw.lune.app.weight.search.OnSearchOperationListener
import com.nyw.lune.databinding.FragmentSearchBinding
import com.nyw.lune.ui.adapter.ExplainItemAdapter
import com.nyw.lune.ui.adapter.MainItemAdapter
import com.nyw.lune.viewmodel.request.RequestSearchViewModel
import com.nyw.lune.viewmodel.state.SearchViewModel
import kotlinx.android.synthetic.main.fragment_main_layout.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*
import tech.oom.idealrecorder.IdealRecorder
import tech.oom.idealrecorder.IdealRecorder.RecordConfig
import tech.oom.idealrecorder.StatusListener
import java.io.File

/**
 * 查单词
 */
class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {
    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>
    private val requestViewModel: RequestSearchViewModel by viewModels()
    private var idealRecorder: IdealRecorder? = null
    private var recordConfig: RecordConfig? = null
    private val mAdapter: ExplainItemAdapter by lazy { ExplainItemAdapter(arrayListOf()) }

    override fun layoutId() = R.layout.fragment_search

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.initClose("查单词") {
            nav().popBackStack()
        }
        //状态页配置
        loadsir = loadServiceInit(main_view) {}
        idealRecorder = IdealRecorder.getInstance()
        recordConfig = RecordConfig(MediaRecorder.AudioSource.MIC, 48000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)
        loadsir.showEmpty("还没输入单词哦", false)
        search_view.setOperationListener(onOperationListener)
        explains_recycle.init(
                LinearLayoutManager(context, RecyclerView.VERTICAL, false),
                mAdapter
        ).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(5f)))
        }
    }

    override fun createObserver() {
        requestViewModel.wordResult.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, {
                loadsir.showSuccess()
                it.run {
                    val translationStr = StringBuffer()
                    for (s in translation) {
                        translationStr.append(s)
                    }
                    mViewModel.word.set(word)
                    mViewModel.translation.set(translationStr.toString())
                    mViewModel.phonetic.set("国际：/$phonetic/")
                    mViewModel.ukPhonetic.set("英式：/$ukPhonetic/")
                    mViewModel.usPhonetic.set("美式：/$usPhonetic/")
                    mViewModel.speakUrl.set(speakUrl)
                    mViewModel.ukSpeakUrl.set(ukSpeech)
                    mViewModel.usSpeakUrl.set(usSpeech)
                    mAdapter.setList(explains.toList() as ArrayList<String>)
                }
            }, {
                loadsir.showEmpty(it.errorMsg, false)
            })
        })
    }


    inner class ProxyClick {
        fun showDialog() {
            checkPermissions()
        }

        fun addToNewWord(){

        }


        fun play(){

        }

        fun playUk(){

        }


        fun playUs(){

        }
    }

    private fun checkPermissions() {
        if (PermissionUtils.isGranted(Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showRecordDialog()
        } else {
            PermissionUtils.permissionGroup(PermissionConstants.MICROPHONE, PermissionConstants.STORAGE)
                    .rationale { activity, shouldRequest ->
                        PermissionHelper.showRationaleDialog(activity, shouldRequest)
                    }
                    .callback(object : PermissionUtils.FullCallback {
                        override fun onGranted(permissionsGranted: List<String>) {
                            showRecordDialog()
                        }

                        override fun onDenied(permissionsDeniedForever: List<String>,
                                              permissionsDenied: List<String>) {
                            if (permissionsDeniedForever.isNotEmpty()) {
                                showToast("权限被永久拒绝，请手动设置")
                            } else {
                                showToast("权限被拒绝")
                            }
                        }
                    })
                    .request()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showRecordDialog() {
        BottomDialog.show(object :
                OnBindView<BottomDialog?>(R.layout.layout_search_bottom_dialog) {
            override fun onBind(dialog: BottomDialog?, v: View?) {
                val title = v?.findViewById<TextView>(R.id.tv_title)
                val tip = v?.findViewById<TextView>(R.id.tv_tip)
                val recordBtn = v?.findViewById<TextView>(R.id.circle_btn)
                val waveView = v?.findViewById<WaveView>(R.id.wave_view)
                title?.text = "请长按按钮，开始语音搜索"
                tip?.visibility = View.INVISIBLE
                recordBtn?.setOnLongClickListener {
                    record(waveView, dialog)
                    title?.text = "请读出单词，我正在聆听…"
                    tip?.visibility = View.VISIBLE
                    true
                }
                recordBtn?.setOnTouchListener(OnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_UP -> {
                            stopRecord()
                            return@OnTouchListener false
                        }
                    }
                    false
                })
            }
        })
    }


    /**
     * 开始录音
     */
    private fun record(waveView: WaveView?, dialog: BottomDialog?) {
        //如果需要保存录音文件  设置好保存路径就会自动保存  也可以通过onRecordData 回调自己保存  不设置 不会保存录音
        idealRecorder?.setRecordFilePath(getSaveFilePath())
        //        idealRecorder.setWavFormat(false);
        //设置录音配置 最长录音时长 以及音量回调的时间间隔
        idealRecorder?.setRecordConfig(recordConfig)?.setMaxRecordTime(60000)?.setVolumeInterval(200)
        //设置录音时各种状态的监听
        idealRecorder?.setStatusListener(object : StatusListener() {
            override fun onStartRecording() {
                LogUtils.e("正在录音")
            }

            override fun onRecordData(data: ShortArray, length: Int) {
                var i = 0
                while (i < length) {
                    waveView?.addData(data[i])
                    i += 60
                }
            }

            override fun onRecordError(code: Int, errorMsg: String) {
                LogUtils.e("录音错误$errorMsg")
            }

            override fun onFileSaveFailed(error: String) {
                LogUtils.e("文件保存失败")
            }

            override fun onFileSaveSuccess(fileUri: String) {
                LogUtils.e("文件保存成功,路径是$fileUri")
            }

            override fun onStopRecording() {
                LogUtils.e("录音结束")
                dialog?.dismiss()
                //关闭弹窗后走 接口查询
                val str = FileBase64.encodeBase64File(getSaveFilePath())
                Log.e("nie", "转换后得到：$str")
            }
        })
        idealRecorder?.start() //开始录音
    }


    /**
     * 获取文件保存路径
     *
     * @return
     */
    private fun getSaveFilePath(): String? {
        val file = File(Environment.getExternalStorageDirectory(), "Audio")
        if (!file.exists()) {
            file.mkdirs()
        }
        val wavFile = File(file, "lune.wav")
        return wavFile.absolutePath
    }

    /**
     * 停止录音
     */
    private fun stopRecord() {
        //停止录音
        idealRecorder?.stop()
    }

    private val onOperationListener: OnSearchOperationListener =
            object : OnSearchOperationListener {
                override fun onSearchResult(content: String) {
                    if (content.isNullOrEmpty()) {
                        showToast("请输入单词")
                        return
                    }
                    mViewModel.keyWord.set(content)
                    requestViewModel.getQueryWithText(mViewModel.keyWord.get())
                }

                override fun onContentChanged(content: String) {

                }
            }

}