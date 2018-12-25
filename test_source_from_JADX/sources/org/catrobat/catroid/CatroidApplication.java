package org.catrobat.catroid;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import java.util.Locale;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.CrashReporter;

public class CatroidApplication extends MultiDexApplication {
    public static final String OS_ARCH = System.getProperty("os.arch");
    private static final String TAG = CatroidApplication.class.getSimpleName();
    private static Context context;
    public static String defaultSystemLanguage;
    private static GoogleAnalytics googleAnalytics;
    private static Tracker googleTracker;
    public static boolean parrotJSLibrariesLoaded = false;
    public static boolean parrotLibrariesLoaded = false;

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "CatroidApplication onCreate");
        CrashReporter.initialize(this);
        context = getApplicationContext();
        defaultSystemLanguage = Locale.getDefault().getLanguage();
        googleAnalytics = GoogleAnalytics.getInstance(this);
        googleAnalytics.setDryRun(BuildConfig.DEBUG);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public synchronized Tracker getDefaultTracker() {
        if (googleTracker == null) {
            googleTracker = googleAnalytics.newTracker((int) R.xml.global_tracker);
        }
        return googleTracker;
    }

    public static synchronized boolean loadNativeLibs() {
        synchronized (CatroidApplication.class) {
            if (parrotLibrariesLoaded) {
                return true;
            }
            try {
                System.loadLibrary("avutil");
                System.loadLibrary("swscale");
                System.loadLibrary("avcodec");
                System.loadLibrary("avfilter");
                System.loadLibrary("avformat");
                System.loadLibrary("avdevice");
                System.loadLibrary("adfreeflight");
                parrotLibrariesLoaded = true;
            } catch (UnsatisfiedLinkError e) {
                Log.e(TAG, Log.getStackTraceString(e));
                parrotLibrariesLoaded = false;
            }
            boolean z = parrotLibrariesLoaded;
            return z;
        }
    }

    public static synchronized boolean loadSDKLib() {
        synchronized (CatroidApplication.class) {
            if (parrotJSLibrariesLoaded) {
                return true;
            }
            try {
                System.loadLibrary("curl");
                System.loadLibrary("json");
                System.loadLibrary("arsal");
                System.loadLibrary("arsal_android");
                System.loadLibrary("arnetworkal");
                System.loadLibrary("arnetworkal_android");
                System.loadLibrary("arnetwork");
                System.loadLibrary("arnetwork_android");
                System.loadLibrary("arcommands");
                System.loadLibrary("arcommands_android");
                System.loadLibrary("arstream");
                System.loadLibrary("arstream_android");
                System.loadLibrary("arstream2");
                System.loadLibrary("arstream2_android");
                System.loadLibrary("ardiscovery");
                System.loadLibrary("ardiscovery_android");
                System.loadLibrary("arutils");
                System.loadLibrary("arutils_android");
                System.loadLibrary("ardatatransfer");
                System.loadLibrary("ardatatransfer_android");
                System.loadLibrary("armedia");
                System.loadLibrary("armedia_android");
                System.loadLibrary("arupdater");
                System.loadLibrary("arupdater_android");
                System.loadLibrary("armavlink");
                System.loadLibrary("armavlink_android");
                System.loadLibrary("arcontroller");
                System.loadLibrary("arcontroller_android");
                parrotJSLibrariesLoaded = true;
            } catch (UnsatisfiedLinkError e) {
                Log.e(TAG, Log.getStackTraceString(e));
                parrotJSLibrariesLoaded = false;
            }
            boolean z = parrotJSLibrariesLoaded;
            return z;
        }
    }

    public static Context getAppContext() {
        return context;
    }
}
