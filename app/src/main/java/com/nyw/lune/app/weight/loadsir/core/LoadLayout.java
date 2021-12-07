package com.nyw.lune.app.weight.loadsir.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;


import com.nyw.lune.app.weight.loadsir.LoadSirUtil;
import com.nyw.lune.app.weight.loadsir.callback.Callback;
import com.nyw.lune.app.weight.loadsir.callback.SuccessCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:使用这个自定义view替换需要替换的View，增加状态控制
 * 一个successlayout visible/invisible 其他layout直接替代，即该view最多有两个子view
 * Create Time:2017/9/2 17:02
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class LoadLayout extends FrameLayout {
    private final String TAG = getClass().getSimpleName();
    private Map<Class<? extends Callback>, Callback> callbacks = new HashMap<>();
    private Context context;
    private Callback.OnReloadListener onReloadListener;
    private Class<? extends Callback> preCallback;
    private Class<? extends Callback> curCallback;
    private static final int CALLBACK_CUSTOM_INDEX = 1;

    public LoadLayout(@NonNull Context context) {
        super(context);
    }

    public LoadLayout(@NonNull Context context, Callback.OnReloadListener onReloadListener) {
        this(context);
        this.context = context;
        this.onReloadListener = onReloadListener;
    }
    public void setupSuccessLayout(Callback callback) {
        addCallback(callback);
        View successView = callback.getRootView();
        successView.setVisibility(View.INVISIBLE);
        addView(successView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        curCallback = SuccessCallback.class;
    }

    public void setupCallback(Callback callback) {
        Callback cloneCallback = callback.copy();
        cloneCallback.setCallback(context, onReloadListener);
        addCallback(cloneCallback);
    }

    public void addCallback(Callback callback) {
        if (!callbacks.containsKey(callback.getClass())) {
            callbacks.put(callback.getClass(), callback);
        }
    }

    public void showCallback(final Class<? extends Callback> callback) {
        //检测判空trow exception
        checkCallbackExist(callback);
        if (LoadSirUtil.isMainThread()) {
            showCallbackView(callback);
        } else {
            postToMainThread(callback);
        }
    }

    public Class<? extends Callback> getCurrentCallback() {
        return curCallback;
    }

    private void postToMainThread(final Class<? extends Callback> status) {
        post(new Runnable() {
            @Override
            public void run() {
                showCallbackView(status);
            }
        });
    }

    private void showCallbackView(Class<? extends Callback> status) {
        if (preCallback != null) {
            if (preCallback == status) {
                return;
            }
            //取消animation,其他不变
            callbacks.get(preCallback).onDetach();
        }
        //loadLayout实际上只能最多同时存在两个layout,一个是成功时的状态，一个是其他加载状态，
        //其他状态的变更前需要先删除上一个状态管理视图View
        if (getChildCount() > 1) {
            removeViewAt(CALLBACK_CUSTOM_INDEX);
        }
        for (Class key : callbacks.keySet()) {
            if (key == status) {
                SuccessCallback successCallback = (SuccessCallback) callbacks.get(SuccessCallback.class);
                if (key == SuccessCallback.class) {
                    successCallback.show();
                } else {
                    //其他状态页面首先隐藏,需要处理的是如果loading状态，且页面拥有数据，应当显示
                    //这里设置了兼容模式，即loading和页面同时出来
                    successCallback.showWithCallback(callbacks.get(key).getSuccessVisible());
                    //获取当前布局
                    View rootView = callbacks.get(key).getRootView();
                    addView(rootView);
                    //将数据替换至前台
                    callbacks.get(key).onAttach(context, rootView);
                }
                preCallback = status;
            }
        }
        curCallback = status;
    }

    public void setCallBack(Class<? extends Callback> callback, Transport transport) {
        if (transport == null) {
            return;
        }
        checkCallbackExist(callback);
        transport.order(context, callbacks.get(callback).obtainRootView());
    }

    private void checkCallbackExist(Class<? extends Callback> callback) {
        if (!callbacks.containsKey(callback)) {
            throw new IllegalArgumentException(String.format("The Callback (%s) is nonexistent.", callback
                    .getSimpleName()));
        }
    }

    public Callback getCallback(Class<? extends Callback> clazz){
        if (callbacks != null && callbacks.containsKey(clazz)) {
            return callbacks.get(clazz);
        }
        return null;
    }
}
