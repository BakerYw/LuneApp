package com.nyw.lune.ui.adapter


import android.os.Build
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nyw.lune.R
import com.nyw.lune.app.util.player.MediaPlayerHelper
import com.nyw.lune.data.model.bean.response.WordResponse

/**
 * @author NieYuWen
 * @date 2021/11/29 8:35 下午
 * @email：992116519@qq.com
 * @desc:
 */
class RememberWordAdapter(data: ArrayList<WordResponse>, var isLast: Boolean, val onBack: (complete:Boolean,isEnd:Boolean) -> Unit) :
        BaseQuickAdapter<WordResponse, BaseViewHolder>(R.layout.item_remember_layout, data), MediaPlayerHelper.OnStatusCallbackListener, OnItemClickListener {
    private var pos = 0
    private var count = 0
    private var pointer = 0
    private var flashback = false //倒序
    private var mediaPlayer: MediaPlayerHelper? = null

    init {
        mediaPlayer = MediaPlayerHelper.getInstance().setOnStatusCallbackListener(this)
        setOnItemClickListener(this)
        onBack.invoke(false,false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun convert(holder: BaseViewHolder, item: WordResponse) {
        holder.setText(R.id.word, item.word)
        holder.setText(R.id.translation, item.translation)
        if (pos == holder.position) {
            holder.setTextColor(R.id.word, context.getColor(R.color.white))
            holder.setVisible(R.id.claw, true)
            holder.setBackgroundResource(R.id.main_view, R.drawable.btn_pressed_primary_ripple)
        } else {
            holder.setTextColor(R.id.word, context.getColor(R.color.colorBlack333))
            holder.setVisible(R.id.claw, false)
            holder.setBackgroundResource(R.id.main_view, R.drawable.btn_normal)
        }
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (!flashback) {
            //正序逻辑
            tojustStudy(position, view)
        } else {
            //倒序逻辑
            toBackStudy(position)
        }
    }


    private fun tojustStudy(position: Int, view: View) {
        if (data[position].isJustStudy) {
            ToastUtils.showShort("已经读过了,请点击爪子处！")
            return
        }
        if (position < pointer) {
            if (position == 0 || data[position - 1].isJustStudy) {
                if (!data[position].isJustStudy) {
                    mediaPlayer?.playUrl(context, data[position].speakUrl, false)
                    data[position].isJustStudy = true
                    flashback = data[itemCount - 1].isJustStudy
                    notifyDataSetChanged()
                    pos++
                    setPos(pos)
                    if (flashback) {
                        setPos(itemCount - 1)
                        pointer--
                    }
                }
            } else {
                ToastUtils.showShort("前面还有没读的！")
            }
            return
        }
        if (position == pointer) {
            if (position == 0 || data[position - 1].isJustStudy) {
                count++
                when (count) {
                    1 -> mediaPlayer?.playUrl(context, data[position].speakUrl, false)

                    2 -> {
                        mediaPlayer?.playUrl(context, data[position].speakUrl, false)
                        (view as RelativeLayout).getChildAt(2).visibility = View.VISIBLE
                    }
                    3 -> {
                        mediaPlayer?.playUrl(context, data[position].speakUrl, false)
                        (view as RelativeLayout).getChildAt(2).visibility = View.GONE
                        var i = 0
                        while (i < itemCount - 1) {
                            data[i].isJustStudy = false
                            i++
                        }
                        pointer++
                        count = 0
                        pos = 0
                        setPos(pos)
                    }
                }
            } else {
                ToastUtils.showShort("前面还有没读完的！")
            }
            return
        }
    }

    private fun toBackStudy(position: Int) {
        if (data[position].isBackStudy) {
            ToastUtils.showShort("已经读过了")
            return
        }
        if (position > pointer) {
            if (position == itemCount - 1 || data[position + 1].isBackStudy) {
                if (data[position].isBackStudy) {
                    ToastUtils.showShort("第 " + (position + 1) + " 个已经读过了")
                } else {
                    data[position].isBackStudy=true
                    mediaPlayer?.playUrl(context, data[position].speakUrl, false)
                    pos--
                    setPos(pos)
                }
            } else {
                ToastUtils.showShort("前面还未读完的")
            }
            return
        }

        if (position == pointer) {
            if (position == itemCount - 1 || data[position + 1].isBackStudy) {
                mediaPlayer?.playUrl(context, data[position].speakUrl, false)
                pointer--
                count = 0
                for (i in itemCount - 1 downTo 0) {
                    data[i].isBackStudy=false
                }
                //这里判断一下是否是倒序第一个单词了，如果是则跳转页面
                if (position == 0) {
                    setPos(-1)
                    onBack.invoke(true, isLast)
                } else {
                    setPos(itemCount - 1)
                }
            } else {
                ToastUtils.showShort("前面还未读完的")
            }
        }
    }

    private fun setPos(position: Int) {
        this.pos = position
        notifyDataSetChanged()
    }


    override fun onStatusonStatusCallbackNext(status: MediaPlayerHelper.CallBackState?, vararg args: Any?) {
        if (status === MediaPlayerHelper.CallBackState.EXCEPTION) {
            if (args.isNotEmpty()) {
                ToastUtils.showShort("播放异常")
            }
        } else if (status === MediaPlayerHelper.CallBackState.ERROR) {
            if (args.isNotEmpty()) {
                ToastUtils.showShort("播放错误")
            }
        }
    }


}