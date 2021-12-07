package com.nyw.lune.app.weight.loadsir.loadCallBack

import com.nyw.lune.app.weight.loadsir.callback.Callback
import android.content.Context
import android.view.View
import com.nyw.lune.R


class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }

    override fun onReloadEvent(context: Context, view: View): Boolean {
        return true
    }

    override fun getRetryView(view: View): View? {
        return view.findViewById(com.nyw.lune.R.id.error_btn)
    }

}