package com.nyw.lune.app.weight.loadsir.loadCallBack


import com.nyw.lune.R
import com.nyw.lune.app.weight.loadsir.callback.Callback


class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}