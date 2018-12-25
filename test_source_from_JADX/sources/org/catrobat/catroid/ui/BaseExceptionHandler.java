package org.catrobat.catroid.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.preference.PreferenceManager;
import android.util.Log;
import java.lang.Thread.UncaughtExceptionHandler;
import org.catrobat.catroid.utils.CrashReporter;

public class BaseExceptionHandler implements UncaughtExceptionHandler {
    private static final int EXIT_CODE = 10;
    private static final String TAG = BaseExceptionHandler.class.getSimpleName();
    private final SharedPreferences preferences;

    public BaseExceptionHandler(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        Log.e(TAG, "uncaughtException: ", exception);
        CrashReporter.storeUnhandledException(exception);
        this.preferences.edit().putBoolean(BaseActivity.RECOVERED_FROM_CRASH, true).commit();
        exit();
    }

    protected void exit() {
        System.exit(10);
        Process.killProcess(Process.myPid());
    }
}
