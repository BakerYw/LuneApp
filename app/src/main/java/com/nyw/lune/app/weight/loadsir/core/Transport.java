package com.nyw.lune.app.weight.loadsir.core;

import android.content.Context;
import android.view.View;

/**
 * Description：因为现行代码只加载一个布局，布局内的元素可以通过这种情况修改
 * Create Time:2017/9/28 6:45
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface Transport {
    void order(Context context, View view);
}
