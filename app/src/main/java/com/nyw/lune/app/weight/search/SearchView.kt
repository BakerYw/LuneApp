package com.nyw.lune.app.weight.search

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo.*
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.nyw.lune.R
import com.nyw.lune.app.ext.setOnClickListenerWithTrigger

/**
 * 搜索view
 */
class SearchView : RelativeLayout{

    private var searchEt: EditText? = null
    private var searchRightTv: TextView? = null
    private var content: String = ""
    private var onSearchOperationListener: OnSearchOperationListener? = null
    private var contentChangeFromClick: Boolean = false
    // 模糊搜索之后，点击列表中的内容
    // 列表消失，点击的内容填充到输入框中
    // 会引发第二次内容变化
    // 所以在处理内容变化之前，先判断此开关
    // 如果为true，就不需要触发内容变化的后续处理，并将字段反置

    fun init() {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.layout_search_view, this)
        searchRightTv = view.findViewById(R.id.tv_search_view)
        searchEt = view.findViewById(R.id.et_search_view)

        searchEt?.addTextChangedListener(defaultTextWatcher)
        searchEt?.setOnEditorActionListener(editorActionListener)

        searchRightTv?.setOnClickListenerWithTrigger() {
            dealSearch()
        }
    }

    fun getSearchContent(): String {
        return content
    }

    fun setOperationListener(onSearchOperationListener: OnSearchOperationListener) {
        this.onSearchOperationListener = onSearchOperationListener
    }


    private val defaultTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            // 如果输入框内容变化是由于点击
            // 不做处理
            if (contentChangeFromClick) {
                contentChangeFromClick = false
                return
            }

            if (TextUtils.isEmpty(s)) {
                content = ""
                onSearchOperationListener?.onContentChanged(content)
                return
            }
            content = s.toString()
            onSearchOperationListener?.onContentChanged(content)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }

    fun setEditTextHint(hint: String?) {
        searchEt?.let {
            if (TextUtils.isEmpty(hint)) {
                it.hint = ""
                return@setEditTextHint
            }
            it.hint = hint
        }
        invalidate()
    }

    fun setEditTextHint(resourceId: Int) {
        searchEt?.let {
            if (resourceId <= 0) {
                it.hint = ""
                return@setEditTextHint
            }
            it.setHint(resourceId)
        }
        invalidate()
    }

    private fun dealSearch() {
        onSearchOperationListener?.onSearchResult(content)
    }

    private val editorActionListener: TextView.OnEditorActionListener =
        object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (event == null || TextUtils.isEmpty(content)) {
                    return true
                }
                if (event.action == IME_ACTION_DONE ||
                    event.action == IME_ACTION_GO ||
                    event.action == IME_ACTION_NEXT ||
                    event.action == IME_ACTION_SEARCH ||
                    event.action == IME_ACTION_SEND
                ) {
                    dealSearch()
                }
                return true
            }

        }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        init()
    }
}