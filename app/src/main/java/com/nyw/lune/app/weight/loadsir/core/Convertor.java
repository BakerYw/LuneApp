package com.nyw.lune.app.weight.loadsir.core;


import com.nyw.lune.app.weight.loadsir.callback.Callback;

/**
 * Description:自定义数据类型处理状态机，对该数据自动解析，决定最后的状态管理类
 * Create Time:2017/9/4 8:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface Convertor<T> {
   Class<?extends Callback> map(T t);
}
