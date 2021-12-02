package com.milo.bsdiffdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fz.lib.trans.FZTransSDK;
import com.fz.lib.trans.download3.DownloadListener;
import com.fz.lib.trans.download3.FZFileDownloader;
import com.milo.bsdiffdemo.databinding.ActivityMainBinding;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    static {
        System.loadLibrary("bsdiffdemo");
    }

    private ActivityMainBinding binding;


    /**
     * A native method that is implemented by the 'bsdiffdemo' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native boolean diffapk(String oldApk, String patch, String newApk);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CrashHandler.getInstance().init(this, null);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.btnBsdiff.setOnClickListener(this);
        setContentView(binding.getRoot());

        TextView tv = binding.sampleText;
        tv.setText("你好 c++");
    }


    @Override
    public void onClick(View v) {
        String oldApk = this.getExternalFilesDir("bsdiff") + "/old.apk";
        String patchApk = this.getExternalFilesDir("bsdiff") + "/patch.apk";
        String newApk = this.getExternalFilesDir("bsdiff") + "/new.apk";

        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        } else {
            if (checkFile(oldApk) && checkFile(patchApk)) {
                if (diffapk(oldApk, patchApk, newApk)) {
                    Toast.makeText(this, "新包合成成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "新包合成失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

//        FZFileDownloader.getInstance().getFileDownloadTaskBuilder()
//                .withDownloadUrl("https://peiyinimg.qupeiyin.cn/2017-02-15/58a3aa129be63.jpg")
//                .withSavePath(this.getExternalFilesDir("download") + "/a.png")
//                .withListener(new DownloadListener() {
//                    @Override
//                    public void onConnectIng() {
//
//                    }
//
//                    @Override
//                    public void onGetSize(long totalSize) {
//
//                    }
//
//                    @Override
//                    public void onDownloadIng(float progressF, int progress) {
//
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//
//                    @Override
//                    public void onError(String error) {
//                        Log.d(TAG, "下载错误" + error);
//                    }
//
//                    @Override
//                    public void onRepeatTask() {
//
//                    }
//
//                    @Override
//                    public void onDone(String localPath) {
//                        Log.d(TAG, "下载完成 : " + localPath);
//                    }
//                }).build().start();
    }

    private boolean checkFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, String.format("%s不存在", path), Toast.LENGTH_SHORT).show();
        }
        return file.exists();
    }

}