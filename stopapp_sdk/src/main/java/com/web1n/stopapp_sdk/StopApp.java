/*
 * Created by web1n.
 * Project $PROJECT_NAME.
 * 18-11-6 下午10:32.
 * lizhik@yeah.net
 */

package com.web1n.stopapp_sdk;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;

/**
 * Project stopappsdk.
 * Created by web1n.
 * at 2018/11/6.
 */
public class StopApp {

    private static final String PERMISSION = "web1n.stopapp.permission.disable_api";
    private static final String PACKAGE_NAME = "web1n.stopapp";
    private static final String ADMIN_CLASS_NAME = "web1n.stopapp.receiver.AdminReceiver";
    private static final int APP_MIN_VERSION = 200;
    private static final Uri SDK_URI = Uri.parse("content://web1n.stopapp.DISABLE_SDK");

    /**
     * 请求用户授予权限
     *
     * @param activity    activity
     * @param requestCode reqcode..
     */
    public static void requestPermission(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            activity.requestPermissions(new String[]{PERMISSION}, requestCode);
    }

    /**
     * 判断应用是否安装 && 是否支持调用 sdk
     *
     * @param context context
     * @return boolean
     */
    public static boolean isAppInstalled(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(PACKAGE_NAME, 0).versionCode >= APP_MIN_VERSION;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 获取小黑屋是否为 device owner
     * 目前 sdk 仅支持调用小黑屋 device owner 引擎冻结
     *
     * @param context context
     * @return boolean
     */
    public static boolean isStopappDeviceOwner(Context context) {
        ComponentName componentName = new ComponentName(PACKAGE_NAME, ADMIN_CLASS_NAME);
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        return devicePolicyManager != null && devicePolicyManager.isDeviceOwnerApp(componentName.getPackageName());
    }

    /**
     * 查询应用是否拥有调用 sdk 的权限
     *
     * @param context context
     * @return boolean
     */
    public static boolean checkPermission(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || (context.checkSelfPermission(PERMISSION) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * 冻结应用
     *
     * @param context      context
     * @param enable       是否启用
     * @param packageNames 包名(s)
     */
    @RequiresPermission(PERMISSION)
    public static void setAppEnabledSettings(Context context, boolean enable, String... packageNames) {
        Bundle extra = new Bundle();
        extra.putStringArray("packages", packageNames);
        extra.putBoolean("enable", enable);
        context.getContentResolver().call(SDK_URI, StopApp.class.toString(), null, extra);
    }

}
