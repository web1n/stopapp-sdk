#StopApp-Disable-SDK
此份 sdk 仅限于小黑屋设备管理员模式使用,其他模式暂无(也不需要)可用 sdk.
小黑屋支持版本:1.8.8(200)以上.

###添加依赖
```
//Todo
```

###调用
####1.判断小黑屋是否已安装
```StopApp.isAppInstalled(Context))```
####2.判断小黑屋是否为 device owner
```StopApp.isStopappDeviceOwner(Context)) ```
####3.判断是否有权限调用 sdk
```
 if (!StopApp.checkPermission(this)) {
        StopApp.requestPermission(Activity, 0x233); //没有权限,去申请.
  }
 ```
####4.冻结 or 解冻
```
冻结 "com.catchingnow.icebox":
StopApp.setAppEnabledSettings(Context, false, "com.catchingnow.icebox");
解冻 "com.catchingnow.icebox":
StopApp.setAppEnabledSettings(Context, true, "com.catchingnow.icebox");
```