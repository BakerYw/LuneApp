package com.nyw.lune.app.weight.loadsir.target;


import com.nyw.lune.app.weight.loadsir.callback.Callback;
import com.nyw.lune.app.weight.loadsir.core.LoadLayout;

/**
 * Description:替换功能适配器，查看是否满足适配方案，并进行单独的替换操作
 * Create Time:2019/8/29 0029 下午 2:43
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface ITarget {
    /**
     *
     * @param target
     * @return
     * v1.3.8
     */
    boolean equals(Object target);
    /**
     * 1.removeView 2.确定LP 3.addView
     * @param target
     * @param onReloadListener
     * @return
     * v1.3.8
     */
    LoadLayout replaceView(Object target, Callback.OnReloadListener onReloadListener);
}
