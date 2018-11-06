/*
 * Created by web1n.
 * Project $PROJECT_NAME.
 * 18-11-6 下午10:51.
 * lizhik@yeah.net
 */

package com.web1n.stopappsdk;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.web1n.stopapp_sdk.StopApp;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!StopApp.isAppInstalled(this)) {
            Toast.makeText(this, "没装小黑屋", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!StopApp.isStopappDeviceOwner(this)) {
            Toast.makeText(this, "小黑屋不是 device owner", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!StopApp.checkPermission(this)) {
            StopApp.requestPermission(this, 0x233);
            return;
        }

        goDisable();
    }

    void goDisable() {
        StopApp.setAppEnabledSettings(this, false, "com.catchingnow.icebox");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != 0x233) return;
        if (StopApp.checkPermission(this)) goDisable();

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
