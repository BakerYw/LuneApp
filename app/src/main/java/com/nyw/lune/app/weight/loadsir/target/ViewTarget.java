package com.nyw.lune.app.weight.loadsir.target;

import android.view.View;
import android.view.ViewGroup;

import com.nyw.lune.app.weight.loadsir.callback.Callback;
import com.nyw.lune.app.weight.loadsir.callback.SuccessCallback;
import com.nyw.lune.app.weight.loadsir.core.LoadLayout;

/**
 * Description:View层面的替换方式
 * Create Time:2019/8/29 0029 下午 2:44
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ViewTarget implements ITarget {

    @Override
    public boolean equals(Object target) {
        return target instanceof View;
    }

    @Override
    public LoadLayout replaceView(Object target, Callback.OnReloadListener onReloadListener) {
        //对view的替换，需要再该view的父布局删除view本身，然后用loadLayout替换
        View oldContent = (View) target;
        //获取父布局
        ViewGroup contentParent = (ViewGroup) (oldContent.getParent());
        //删除原始view，记录子元素位置，方便后面添加
        int childIndex = 0;
        int childCount = contentParent == null ? 0 : contentParent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (contentParent.getChildAt(i) == oldContent) {
                childIndex = i;
                break;
            }
        }
        //删除操作
        if (contentParent != null) {
            contentParent.removeView(oldContent);
        }
        //获取layoutParams
        ViewGroup.LayoutParams oldLayoutParams = oldContent.getLayoutParams();
        //创建loadlayout
        LoadLayout loadLayout = new LoadLayout(oldContent.getContext(), onReloadListener);
        loadLayout.setupSuccessLayout(new SuccessCallback(oldContent, oldContent.getContext(),onReloadListener));
        //添加view
        if (contentParent != null) {
            contentParent.addView(loadLayout, childIndex, oldLayoutParams);
        }
        return loadLayout;
    }
}
