package com.nyw.lune.app.weight.loadsir.callback;

import android.content.Context;
import android.view.View;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Description:基础Callback类，状态管理，每个状态对应一种页面
 * Create Time:2017/9/2 17:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class Callback implements Serializable {
    private View rootView;
    private Context context;
    private OnReloadListener onReloadListener;
    private boolean successViewVisible;

    public Callback() {
    }

    Callback(View view, Context context, OnReloadListener onReloadListener) {
        this.rootView = view;
        this.context = context;
        this.onReloadListener = onReloadListener;
    }

    public Callback setCallback(Context context, OnReloadListener onReloadListener) {
        this.context = context;
        this.onReloadListener = onReloadListener;
        return this;
    }

    //获取此状态下的布局
    public View getRootView() {
        //除SuccessCallback不推荐使用
        int resId = onCreateView();
        if (resId == 0 && rootView != null) {
            //SuccessCallback这里直接return，返回的是被移除的原始布局
            return rootView;
        }

        //是否再套一层，如果没有特殊需求不推荐使用
        if (onBuildView(context) != null) {
            rootView = onBuildView(context);
        }

        //初始化页面根据资源id
        if (rootView == null) {
            rootView = View.inflate(context, onCreateView(), null);

        }
        //设置全局的重试
        rootView.setOnClickListener(v -> {
            //某些不需要重试的Callback或者其他特定场景，可以重写onReloadEvent为true
            if (onReloadEvent(context, rootView)) {
                return;
            }
            //reload回调
            if (onReloadListener != null) {
                onReloadListener.onReload(v);
            }
        });

        View retryView=getRetryView(rootView);
        if (retryView!=null){
            retryView.setOnClickListener(v->{
                //reload回调
                if (onReloadListener != null) {
                    onReloadListener.onReload(v);
                }
            });
        }
        onViewCreate(context, rootView);
        return rootView;
    }

    protected View getRetryView(View view) { return null; }

    protected View onBuildView(Context context) {
        return null;
    }

    /**
     * if return true, the successView will be visible when the view of callback is attached.
     */
    public boolean getSuccessVisible() {
        return successViewVisible;
    }

    public void setSuccessVisible(boolean visible) {
        this.successViewVisible = visible;
    }

    /**
     * @deprecated Use {@link #onReloadEvent(Context context, View view)} instead.
     */
    protected boolean onRetry(Context context, View view) {
        return false;
    }

    protected boolean onReloadEvent(Context context, View view) {
        return false;
    }

    /**
     * 以类名为基础，但是通过这种形式，避免了所有页面使用某几个类造成的混乱
     */
    public Callback copy() {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        Object obj = null;
        try {
            oos = new ObjectOutputStream(bao);
            oos.writeObject(this);
            oos.close();
            ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Callback) obj;
    }

    /**
     * @since 1.2.2
     * 获取状态管理类对应的页面布局View，如果为空，重新创建
     * 成功页则rootView不为空
     */
    public View obtainRootView() {
        if (rootView == null) {
            rootView = View.inflate(context, onCreateView(), null);
        }
        return rootView;
    }

    public interface OnReloadListener extends Serializable {
        void onReload(View v);
    }

    protected abstract int onCreateView();

    /**
     * Called immediately after {@link #onCreateView()}
     *
     * @since 1.2.2
     */
    protected void onViewCreate(Context context, View view) {
    }

    /**
     * Called when the rootView of Callback is attached to its LoadLayout.
     *
     * @since 1.2.2
     */
    public void onAttach(Context context, View view) {
    }

    /**
     * Called when the rootView of Callback is removed from its LoadLayout.
     *
     * @since 1.2.2
     */
    public void onDetach() {
    }

}
