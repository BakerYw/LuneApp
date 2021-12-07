package com.nyw.lune.app.weight.loadsir.core;

import androidx.annotation.NonNull;

import com.nyw.lune.app.weight.loadsir.LoadSirUtil;
import com.nyw.lune.app.weight.loadsir.callback.Callback;
import com.nyw.lune.app.weight.loadsir.target.ActivityTarget;
import com.nyw.lune.app.weight.loadsir.target.ITarget;
import com.nyw.lune.app.weight.loadsir.target.ViewTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/9/2 16:36
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoadSir {
    private static volatile LoadSir loadSir;
    private Builder builder;

    public static LoadSir getDefault() {
        if (loadSir == null) {
            synchronized (LoadSir.class) {
                if (loadSir == null) {
                    loadSir = new LoadSir();
                }
            }
        }
        return loadSir;
    }

    private LoadSir() {
        this.builder = new Builder();
    }

    private void setBuilder(@NonNull Builder builder) {
        this.builder = builder;
    }

    private LoadSir(Builder builder) {
        this.builder = builder;
    }

    public LoadService register(@NonNull Object target) {
        return register(target, null, null);
    }

    public LoadService register(Object target, Callback.OnReloadListener onReloadListener) {
        return register(target, onReloadListener, null);
    }

    public <T> LoadService register(Object target, Callback.OnReloadListener onReloadListener, Convertor<T>
            convertor) {
        //是基于Activity层面的替换还是只是替换某个View
        ITarget targetContext = LoadSirUtil.getTargetContext(target, builder.getTargetContextList());
        //具体替换操作
        LoadLayout loadLayout = targetContext.replaceView(target, onReloadListener);
        //创建loadService
        return new LoadService<>(convertor,loadLayout,  builder);
    }

    public static Builder beginBuilder() {
        return new Builder();
    }

    public static class Builder {
        //所有的默认行为页面
        private List<Callback> callbacks = new ArrayList<>();
        //适配方案
        private List<ITarget> targetContextList = new ArrayList<>();
        private Class<? extends Callback> defaultCallback;

        {
            targetContextList.add(new ActivityTarget());
            targetContextList.add(new ViewTarget());
        }

        public Builder addCallback(@NonNull Callback callback) {
            callbacks.add(callback);
            return this;
        }

        /**
         * @param targetContext
         * @return Builder
         * @since 1.3.8
         */
        public Builder addTargetContext(ITarget targetContext) {
            targetContextList.add(targetContext);
            return this;
        }

        public List<ITarget> getTargetContextList() {
            return targetContextList;
        }

        public Builder setDefaultCallback(@NonNull Class<? extends Callback> defaultCallback) {
            this.defaultCallback = defaultCallback;
            return this;
        }

        List<Callback> getCallbacks() {
            return callbacks;
        }

        Class<? extends Callback> getDefaultCallback() {
            return defaultCallback;
        }

        public void commit() {
            getDefault().setBuilder(this);
        }

        public LoadSir build() {
            return new LoadSir(this);
        }

    }
}
