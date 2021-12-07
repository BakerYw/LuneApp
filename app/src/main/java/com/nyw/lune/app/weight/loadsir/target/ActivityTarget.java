package com.nyw.lune.app.weight.loadsir.target;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.nyw.lune.app.weight.loadsir.callback.Callback;
import com.nyw.lune.app.weight.loadsir.callback.SuccessCallback;
import com.nyw.lune.app.weight.loadsir.core.LoadLayout;

/**
 * Description:Activity层面的替换
 * Create Time:2019/8/29 0029 下午 2:44
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ActivityTarget implements ITarget {

    @Override
    public boolean equals(Object target) {
        return target instanceof Activity;
    }

    @Override
    public LoadLayout replaceView(Object target, Callback.OnReloadListener onReloadListener) {
        Activity activity = (Activity) target;
        //TODO 不严谨，但没有特别合适的方法，当前ActivityTarget基本没有应用场景，暂时搁置
        //选取Activity默认的页面元素content,基本上没有特殊情况每个页面的最外层都是content
        ViewGroup contentParent = activity.findViewById(android.R.id.content);
        //针对Activity层面的操作方式，移除所有页面元素，重新创建
        int childIndex = 0;
        View oldContent = contentParent.getChildAt(childIndex);
        contentParent.removeView(oldContent);
        //用LoadLayout替代原始的页面控件元素，设置重试，正常布局，重新加入content布局下
        ViewGroup.LayoutParams oldLayoutParams = oldContent.getLayoutParams();
        LoadLayout loadLayout = new LoadLayout(activity, onReloadListener);
        loadLayout.setupSuccessLayout(new SuccessCallback(oldContent, activity,
                onReloadListener));
        contentParent.addView(loadLayout, childIndex, oldLayoutParams);
        return loadLayout;
    }
}
