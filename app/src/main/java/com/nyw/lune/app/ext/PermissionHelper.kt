package com.nyw.lune.app.ext

import android.content.Context
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.kongzue.dialogx.dialogs.MessageDialog


object PermissionHelper {

    fun request(
        context: Context, callback: PermissionUtils.SimpleCallback,
        @PermissionConstants.PermissionGroup vararg permissions: String
    ) {
        PermissionUtils.permission(*permissions)
            .rationale { activity, shouldRequest -> showRationaleDialog(activity, shouldRequest) }
            .callback(object : PermissionUtils.SingleCallback {
                override fun callback(
                    isAllGranted: Boolean, granted: MutableList<String>,
                    deniedForever: MutableList<String>, denied: MutableList<String>
                ) {
                    LogUtils.d(isAllGranted, granted, deniedForever, denied)
                    if (isAllGranted) {
                        callback.onGranted()
                        return
                    }
                    if (deniedForever.isNotEmpty()) {
                        showOpenAppSettingDialog(context)
                        return
                    }
                    val activity = ActivityUtils.getActivityByContext(context)
                    if (activity != null) {
                        SnackbarUtils.with(activity.findViewById(android.R.id.content))
                            .setMessage("Permission denied: ${permissions2String(denied)}")
                            .showError(true)
                    }
                    callback.onDenied()
                }

                fun permissions2String(permissions: MutableList<String>): String {
                    if (permissions.isEmpty()) return "[]"
                    val sb: StringBuilder = StringBuilder()
                    for (permission in permissions) {
                        sb.append(", " + permission.substring(permission.lastIndexOf('.') + 1))
                    }
                    return "[${sb.substring(2)}]"
                }
            })
            .request()
    }

    fun showRationaleDialog(
        context: Context,
        shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest
    ) {
        MessageDialog.show("提示", "立即允许这些权限？", "确定","取消").setOkButton { baseDialog, v ->
            shouldRequest.again(true)
            false
        }.setCancelButton{ baseDialog, v ->
            shouldRequest.again(false)
            false
        }
    }

    fun showExplainDialog(
        context: Context,
        denied: List<String>,
        shouldRequest: PermissionUtils.OnExplainListener.ShouldRequest
    ) {
        MessageDialog.show("提示", "立即允许这些权限？", "确定","取消").setOkButton { baseDialog, v ->
            shouldRequest.start(true)
            false
        }.setCancelButton{ baseDialog, v ->
            shouldRequest.start(false)
            false
        }
    }

    fun showOpenAppSettingDialog(context: Context) {
        MessageDialog.show("提示", "立即允许这些权限？", "确定").setOkButton { baseDialog, v ->
            PermissionUtils.launchAppDetailsSettings()
            false
        }
    }
}
