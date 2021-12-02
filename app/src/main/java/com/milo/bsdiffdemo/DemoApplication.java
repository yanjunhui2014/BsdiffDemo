package com.milo.bsdiffdemo;

import android.app.Application;

import com.fz.lib.trans.FZTransSDK;

/**
 * Title：
 * Describe：
 * Remark：
 * <p>
 * Created by Milo
 * E-Mail : 303767416@qq.com
 * 2021/12/2
 */
public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FZTransSDK.getInstance().setApplication(this);
    }
}
