package com.nyw.lune.app.weight.loadsir.core;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nyw.lune.app.weight.loadsir.LoadSirUtil;
import com.nyw.lune.app.weight.loadsir.callback.Callback;
import com.nyw.lune.app.weight.loadsir.callback.SuccessCallback;

import java.util.List;

/**
 * Description:presenter层，方便外部使用，屏蔽部分逻辑代码
 * Create Time:2017/9/6 10:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoadService<T> {
    private final String TAG = getClass().getSimpleName();
    //实际layout
    private LoadLayout loadLayout;
    //状态机
    private Convertor<T> convertor;

    LoadService(Convertor<T> convertor,LoadLayout loadLayout,LoadSir.Builder builder) {
        this.convertor = convertor;
        this.loadLayout = loadLayout;
        initCallback(builder);
    }

    private void initCallback(LoadSir.Builder builder) {
        List<Callback> callbacks = builder.getCallbacks();
        final Class<? extends Callback> defalutCallback = builder.getDefaultCallback();
        if (callbacks != null && callbacks.size() > 0) {
            for (Callback callback : callbacks) {
                loadLayout.setupCallback(callback);
            }
        }
        if (defalutCallback != null) {
            if (LoadSirUtil.isMainThread()) {
                loadLayout.showCallback(defalutCallback);
            } else {
                new Handler().post(() -> loadLayout.showCallback(defalutCallback));
            }
        }
    }

    public void showSuccess() {
        loadLayout.showCallback(SuccessCallback.class);
    }

    public void showCallback(Class<? extends Callback> callback) {
        loadLayout.showCallback(callback);
    }

    public void showWithConvertor(T t) {
        if (convertor == null) {
            throw new IllegalArgumentException("You haven't set the Convertor.");
        }
        loadLayout.showCallback(convertor.map(t));
    }

    public LoadLayout getLoadLayout() {
        return loadLayout;
    }

    public Class<? extends Callback> getCurrentCallback() {
        return loadLayout.getCurrentCallback();
    }

    public Callback getCallback(Class<? extends Callback> clazz) {
        return loadLayout.getCallback(clazz);
    }

    /**
     * obtain rootView if you want keep the toolbar in Fragment
     *
     * @since 1.2.2
     * @deprecated
     */
    public LinearLayout getTitleLoadLayout(Context context, ViewGroup rootView, View titleView) {
        LinearLayout newRootView = new LinearLayout(context);
        newRootView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        newRootView.setLayoutParams(layoutParams);
        rootView.removeView(titleView);
        newRootView.addView(titleView);
        newRootView.addView(loadLayout, layoutParams);
        return newRootView;
    }

    /**
     * modify the callback dynamically
     *
     * @param callback  which callback you want modify(layout, event)
     * @param transport a interface include modify logic
     * @since 1.2.2
     */
    public LoadService<T> setCallBack(Class<? extends Callback> callback, Transport transport) {
        loadLayout.setCallBack(callback, transport);
        return this;
    }
}
