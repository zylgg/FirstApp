package com.example.jhzyl.firstapp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

import java.util.ArrayList;
import java.util.List;

public class MySophixApplication extends SophixApplication {

    private final String TAG = "SophixStubApplication";

    public interface MsgDisplayListener {
        void handle(String msg);
    }

    public static MsgDisplayListener msgDisplayListener = null;
    public static StringBuilder cacheMsg = new StringBuilder();

    @Keep
    @SophixEntry(MyRealApplication.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initHotfix();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void initHotfix() {
        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.0";
        }
        List<String> tags = new ArrayList<>();
        tags.add("1.8");
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
//                .setAppVersion("tags")//与控制台创建的版本号对应，否则拉取不到补丁
                .setTags(tags)
                .setAesKey(null)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        String msg = new StringBuilder("").append("Mode:").append(mode).append("\n")
                                .append(" Code:").append(code).append("\n")
                                .append(" Info:").append(info).append("\n")
                                .append(" HandlePatchVersion:").append(handlePatchVersion).toString();
                        if (msgDisplayListener != null) {
                            msgDisplayListener.handle(msg);
                        } else {
                            cacheMsg.append("\n").append(msg);
                        }
                    }
                }).initialize();
    }

}
