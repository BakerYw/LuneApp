package com.nyw.lune.app.weight.loadsir.loadCallBack

import android.content.Context
import android.view.View
import com.nyw.lune.R
import com.nyw.lune.app.weight.loadsir.callback.Callback


class LoadingCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}