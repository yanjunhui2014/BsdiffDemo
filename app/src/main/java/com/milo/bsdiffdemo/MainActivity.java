package com.milo.bsdiffdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.milo.bsdiffdemo.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
        String oldApk = Environment.getExternalStorageDirectory() + "/1test/old.apk";
        String patchApk = Environment.getExternalStorageDirectory() + "/1test/patch.apk";
        String newApk = Environment.getExternalStorageDirectory() + "/1test/new.apk";

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
    }

    private boolean checkFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, String.format("%s不存在", path), Toast.LENGTH_SHORT).show();
        }
        return file.exists();
    }

}