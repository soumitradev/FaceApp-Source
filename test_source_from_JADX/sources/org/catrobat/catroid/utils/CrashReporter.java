package org.catrobat.catroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.google.gson.Gson;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public final class CrashReporter {
    public static final String EXCEPTION_FOR_REPORT = "EXCEPTION_FOR_REPORT";
    private static final String TAG = CrashReporter.class.getSimpleName();
    private static boolean isCrashReportEnabled = true;
    protected static SharedPreferences preferences;
    private static CrashReporterInterface reporter = new CrashlyticsCrashReporter();

    private CrashReporter() {
    }

    private static boolean isReportingEnabled() {
        return preferences != null && preferences.getBoolean(SettingsFragment.SETTINGS_CRASH_REPORTS, true) && isCrashReportEnabled;
    }

    public static void initialize(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (isReportingEnabled()) {
            Log.d(TAG, "Initializing Crash Reporter");
            reporter.initialize(context);
            return;
        }
        Log.d(TAG, "Crash reporting is disabled. Skipping initializing");
    }

    public static void logException(Throwable exception) {
        if (isReportingEnabled()) {
            reporter.logException(exception);
        }
    }

    public static void logUnhandledException() {
        if (isReportingEnabled() && hasStoredException()) {
            Log.d(TAG, "Reporting stored exception");
            logException(getStoredException());
            removeStoredException();
        }
    }

    public static void storeUnhandledException(Throwable exception) {
        if (isReportingEnabled() && !hasStoredException()) {
            Log.d(TAG, "Storing unhandled exception");
            storeException(exception);
        }
    }

    private static void storeException(Throwable exception) {
        preferences.edit().putString(EXCEPTION_FOR_REPORT, serializeException(exception)).commit();
    }

    private static Throwable getStoredException() {
        return deserializeException(preferences.getString(EXCEPTION_FOR_REPORT, ""));
    }

    private static boolean hasStoredException() {
        return getStoredException() != null;
    }

    private static void removeStoredException() {
        preferences.edit().remove(EXCEPTION_FOR_REPORT).commit();
    }

    private static String serializeException(Throwable exception) {
        return new Gson().toJson(exception);
    }

    private static Throwable deserializeException(String exception) {
        return (Throwable) new Gson().fromJson(exception, Throwable.class);
    }

    @VisibleForTesting
    public static void setCrashReporterInterface(CrashReporterInterface crashReporterInterface) {
        reporter = crashReporterInterface;
    }

    @VisibleForTesting
    public static void setIsCrashReportEnabled(boolean isEnabled) {
        isCrashReportEnabled = isEnabled;
    }
}
