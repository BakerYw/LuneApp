package com.nyw.lune.app.weight.loadsir;

import android.os.Looper;

import com.nyw.lune.app.weight.loadsir.target.ITarget;

import java.util.List;

/**
 * Description:工具类，判读属于哪种适配器方案
 * Create Time:2017/9/4 16:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoadSirUtil {

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static ITarget getTargetContext(Object target, List<ITarget> targetContextList) {
        for (ITarget targetContext : targetContextList) {
            //此处对target进行判断，target可能会是Activity,也可能传入的是view
            //此处的equals具体见ITarget,Activity的判断是target instanseof Activity
            //view的判断是target instanceof view
            if (targetContext.equals(target)) {
                return targetContext;
            }

        }
        //当前只支持传入Activity,View，其他情况的target暂不支持，暂时也没有那么多的使用场景，针对于View的写法基本能适配所有情况
        throw new IllegalArgumentException("No TargetContext fit it");
    }
}
