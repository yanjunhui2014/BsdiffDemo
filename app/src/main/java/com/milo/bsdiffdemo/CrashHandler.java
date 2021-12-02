package com.milo.bsdiffdemo;

import android.content.Context;

import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author user
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    public interface OnUncaughtExceptionHappenListener {
        public void onUncaughtExceptionHappen(Thread thread, final Throwable ex);
    }

    // CrashHandler 实例
    private static CrashHandler INSTANCE = new CrashHandler();

    // 程序的 Context 对象
    private Context mContext;

    // 系统默认的 UncaughtException 处理类
    private UncaughtExceptionHandler mDefaultHandler;

    public void setListener(OnUncaughtExceptionHappenListener mListener) {
        this.mListener = mListener;
    }

    private OnUncaughtExceptionHappenListener mListener;

    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    //出现严重错误后重启所需要的入口Activity
    //private Activity mainActivity;

    //出现错误后保存错误日志到这个目录下
    private String rootPath;

    private boolean isDebug;

    /**
     * 保证只有一个 CrashHandler 实例
     */
    private CrashHandler() {
    }

    /**
     * 获取 CrashHandler 实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context, String directory) {
        init(context, directory, true);
    }

    public void init(Context context, String directory, boolean isDebug) {
        this.isDebug = isDebug;
        mContext = context;
        //mainActivity = activity;
        rootPath = directory;
        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, final Throwable ex) {
        if (mListener != null) {
            mListener.onUncaughtExceptionHappen(thread, ex);
        }
    }

    /**
     * 自定义错误处理，收集错误信息，并保存本地
     *
     * @param ex
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private boolean handleException(Thread thread, final Throwable ex) {
        if (ex == null) {
            return false;
        }

        return true;
    }
}