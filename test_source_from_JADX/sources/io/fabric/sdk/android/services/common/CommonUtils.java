package io.fabric.sdk.android.services.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import javax.crypto.Cipher;

public class CommonUtils {
    static final int BYTES_IN_A_GIGABYTE = 1073741824;
    static final int BYTES_IN_A_KILOBYTE = 1024;
    static final int BYTES_IN_A_MEGABYTE = 1048576;
    private static final String CLS_SHARED_PREFERENCES_NAME = "com.crashlytics.prefs";
    static final boolean CLS_TRACE_DEFAULT = false;
    static final String CLS_TRACE_PREFERENCE_NAME = "com.crashlytics.Trace";
    static final String CRASHLYTICS_BUILD_ID = "com.crashlytics.android.build_id";
    public static final int DEVICE_STATE_BETAOS = 8;
    public static final int DEVICE_STATE_COMPROMISEDLIBRARIES = 32;
    public static final int DEVICE_STATE_DEBUGGERATTACHED = 4;
    public static final int DEVICE_STATE_ISSIMULATOR = 1;
    public static final int DEVICE_STATE_JAILBROKEN = 2;
    public static final int DEVICE_STATE_VENDORINTERNAL = 16;
    static final String FABRIC_BUILD_ID = "io.fabric.android.build_id";
    public static final Comparator<File> FILE_MODIFIED_COMPARATOR = new CommonUtils$1();
    public static final String GOOGLE_SDK = "google_sdk";
    private static final char[] HEX_VALUES = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String LOG_PRIORITY_NAME_ASSERT = "A";
    private static final String LOG_PRIORITY_NAME_DEBUG = "D";
    private static final String LOG_PRIORITY_NAME_ERROR = "E";
    private static final String LOG_PRIORITY_NAME_INFO = "I";
    private static final String LOG_PRIORITY_NAME_UNKNOWN = "?";
    private static final String LOG_PRIORITY_NAME_VERBOSE = "V";
    private static final String LOG_PRIORITY_NAME_WARN = "W";
    public static final String MD5_INSTANCE = "MD5";
    public static final String SDK = "sdk";
    public static final String SHA1_INSTANCE = "SHA-1";
    private static final long UNCALCULATED_TOTAL_RAM = -1;
    private static Boolean clsTrace = null;
    private static long totalRamInBytes = -1;

    public static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences(CLS_SHARED_PREFERENCES_NAME, 0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String extractFieldFromSystemFile(java.io.File r7, java.lang.String r8) {
        /*
        r0 = 0;
        r1 = r7.exists();
        if (r1 == 0) goto L_0x0063;
    L_0x0007:
        r1 = 0;
        r2 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x0041 }
        r3 = new java.io.FileReader;	 Catch:{ Exception -> 0x0041 }
        r3.<init>(r7);	 Catch:{ Exception -> 0x0041 }
        r4 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r2.<init>(r3, r4);	 Catch:{ Exception -> 0x0041 }
        r1 = r2;
    L_0x0015:
        r2 = r1.readLine();	 Catch:{ Exception -> 0x0041 }
        r3 = r2;
        if (r2 == 0) goto L_0x0039;
    L_0x001c:
        r2 = "\\s*:\\s*";
        r2 = java.util.regex.Pattern.compile(r2);	 Catch:{ Exception -> 0x0041 }
        r4 = 2;
        r4 = r2.split(r3, r4);	 Catch:{ Exception -> 0x0041 }
        r5 = r4.length;	 Catch:{ Exception -> 0x0041 }
        r6 = 1;
        if (r5 <= r6) goto L_0x0038;
    L_0x002b:
        r5 = 0;
        r5 = r4[r5];	 Catch:{ Exception -> 0x0041 }
        r5 = r5.equals(r8);	 Catch:{ Exception -> 0x0041 }
        if (r5 == 0) goto L_0x0038;
    L_0x0034:
        r5 = r4[r6];	 Catch:{ Exception -> 0x0041 }
        r0 = r5;
        goto L_0x0039;
    L_0x0038:
        goto L_0x0015;
    L_0x0039:
        r2 = "Failed to close system file reader.";
        closeOrLog(r1, r2);
        goto L_0x0063;
    L_0x003f:
        r2 = move-exception;
        goto L_0x005d;
    L_0x0041:
        r2 = move-exception;
        r3 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ all -> 0x003f }
        r4 = "Fabric";
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x003f }
        r5.<init>();	 Catch:{ all -> 0x003f }
        r6 = "Error parsing ";
        r5.append(r6);	 Catch:{ all -> 0x003f }
        r5.append(r7);	 Catch:{ all -> 0x003f }
        r5 = r5.toString();	 Catch:{ all -> 0x003f }
        r3.e(r4, r5, r2);	 Catch:{ all -> 0x003f }
        goto L_0x0039;
    L_0x005d:
        r3 = "Failed to close system file reader.";
        closeOrLog(r1, r3);
        throw r2;
    L_0x0063:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.services.common.CommonUtils.extractFieldFromSystemFile(java.io.File, java.lang.String):java.lang.String");
    }

    public static int getCpuArchitectureInt() {
        return CommonUtils$Architecture.getValue().ordinal();
    }

    public static synchronized long getTotalRamInBytes() {
        long bytes;
        synchronized (CommonUtils.class) {
            if (totalRamInBytes == -1) {
                bytes = 0;
                String result = extractFieldFromSystemFile(new File("/proc/meminfo"), "MemTotal");
                if (!TextUtils.isEmpty(result)) {
                    result = result.toUpperCase(Locale.US);
                    try {
                        if (result.endsWith("KB")) {
                            bytes = convertMemInfoToBytes(result, "KB", 1024);
                        } else if (result.endsWith("MB")) {
                            bytes = convertMemInfoToBytes(result, "MB", 1048576);
                        } else if (result.endsWith("GB")) {
                            bytes = convertMemInfoToBytes(result, "GB", 1073741824);
                        } else {
                            Logger logger = Fabric.getLogger();
                            String str = Fabric.TAG;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unexpected meminfo format while computing RAM: ");
                            stringBuilder.append(result);
                            logger.d(str, stringBuilder.toString());
                        }
                    } catch (NumberFormatException e) {
                        Logger logger2 = Fabric.getLogger();
                        String str2 = Fabric.TAG;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Unexpected meminfo format while computing RAM: ");
                        stringBuilder2.append(result);
                        logger2.e(str2, stringBuilder2.toString(), e);
                    }
                }
                totalRamInBytes = bytes;
            }
            bytes = totalRamInBytes;
        }
        return bytes;
    }

    static long convertMemInfoToBytes(String memInfo, String notation, int notationMultiplier) {
        return Long.parseLong(memInfo.split(notation)[0].trim()) * ((long) notationMultiplier);
    }

    public static RunningAppProcessInfo getAppProcessInfo(String packageName, Context context) {
        List<RunningAppProcessInfo> processes = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (processes == null) {
            return null;
        }
        for (RunningAppProcessInfo info : processes) {
            if (info.processName.equals(packageName)) {
                return info;
            }
        }
        return null;
    }

    public static String streamToString(InputStream is) throws IOException {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String md5(String source) {
        return hash(source, MD5_INSTANCE);
    }

    public static String md5(byte[] source) {
        return hash(source, MD5_INSTANCE);
    }

    public static String sha1(String source) {
        return hash(source, SHA1_INSTANCE);
    }

    public static String sha1(byte[] source) {
        return hash(source, SHA1_INSTANCE);
    }

    public static String sha1(InputStream source) {
        return hash(source, SHA1_INSTANCE);
    }

    private static String hash(InputStream source, String sha1Instance) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA1_INSTANCE);
            byte[] buffer = new byte[1024];
            while (true) {
                int read = source.read(buffer);
                int length = read;
                if (read == -1) {
                    return hexify(digest.digest());
                }
                digest.update(buffer, 0, length);
            }
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, "Could not calculate hash for app icon.", e);
            return "";
        }
    }

    private static String hash(byte[] bytes, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(bytes);
            return hexify(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            Logger logger = Fabric.getLogger();
            String str = Fabric.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not create hashing algorithm: ");
            stringBuilder.append(algorithm);
            stringBuilder.append(", returning empty string.");
            logger.e(str, stringBuilder.toString(), e);
            return "";
        }
    }

    private static String hash(String s, String algorithm) {
        return hash(s.getBytes(), algorithm);
    }

    public static String createInstanceIdFrom(String... sliceIds) {
        String str = null;
        if (sliceIds != null) {
            if (sliceIds.length != 0) {
                List<String> sliceIdList = new ArrayList();
                for (String id : sliceIds) {
                    if (id != null) {
                        sliceIdList.add(id.replace("-", "").toLowerCase(Locale.US));
                    }
                }
                Collections.sort(sliceIdList);
                StringBuilder sb = new StringBuilder();
                for (String id2 : sliceIdList) {
                    sb.append(id2);
                }
                String concatValue = sb.toString();
                if (concatValue.length() > 0) {
                    str = sha1(concatValue);
                }
                return str;
            }
        }
        return null;
    }

    public static long calculateFreeRamInBytes(Context context) {
        MemoryInfo mi = new MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(mi);
        return mi.availMem;
    }

    public static long calculateUsedDiskSpaceInBytes(String path) {
        StatFs statFs = new StatFs(path);
        long blockSizeBytes = (long) statFs.getBlockSize();
        return (((long) statFs.getBlockCount()) * blockSizeBytes) - (((long) statFs.getAvailableBlocks()) * blockSizeBytes);
    }

    public static Float getBatteryLevel(Context context) {
        Intent battery = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (battery == null) {
            return null;
        }
        return Float.valueOf(((float) battery.getIntExtra(FirebaseAnalytics$Param.LEVEL, -1)) / ((float) battery.getIntExtra("scale", -1)));
    }

    public static boolean getProximitySensorEnabled(Context context) {
        boolean z = false;
        if (isEmulator(context)) {
            return false;
        }
        if (((SensorManager) context.getSystemService("sensor")).getDefaultSensor(8) != null) {
            z = true;
        }
        return z;
    }

    public static void logControlled(Context context, String msg) {
        if (isClsTrace(context)) {
            Fabric.getLogger().d(Fabric.TAG, msg);
        }
    }

    public static void logControlledError(Context context, String msg, Throwable tr) {
        if (isClsTrace(context)) {
            Fabric.getLogger().e(Fabric.TAG, msg);
        }
    }

    public static void logControlled(Context context, int level, String tag, String msg) {
        if (isClsTrace(context)) {
            Fabric.getLogger().log(level, Fabric.TAG, msg);
        }
    }

    @Deprecated
    public static boolean isLoggingEnabled(Context context) {
        return false;
    }

    public static boolean isClsTrace(Context context) {
        if (clsTrace == null) {
            clsTrace = Boolean.valueOf(getBooleanResourceValue(context, CLS_TRACE_PREFERENCE_NAME, false));
        }
        return clsTrace.booleanValue();
    }

    public static boolean getBooleanResourceValue(Context context, String key, boolean defaultValue) {
        if (context != null) {
            Resources resources = context.getResources();
            if (resources != null) {
                int id = getResourcesIdentifier(context, key, "bool");
                if (id > 0) {
                    return resources.getBoolean(id);
                }
                id = getResourcesIdentifier(context, key, "string");
                if (id > 0) {
                    return Boolean.parseBoolean(context.getString(id));
                }
            }
        }
        return defaultValue;
    }

    public static int getResourcesIdentifier(Context context, String key, String resourceType) {
        return context.getResources().getIdentifier(key, resourceType, getResourcePackageName(context));
    }

    public static boolean isEmulator(Context context) {
        String androidId = Secure.getString(context.getContentResolver(), "android_id");
        if (!("sdk".equals(Build.PRODUCT) || GOOGLE_SDK.equals(Build.PRODUCT))) {
            if (androidId != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isRooted(Context context) {
        boolean isEmulator = isEmulator(context);
        String buildTags = Build.TAGS;
        if ((!isEmulator && buildTags != null && buildTags.contains("test-keys")) || new File("/system/app/Superuser.apk").exists()) {
            return true;
        }
        File file = new File("/system/xbin/su");
        if (isEmulator || !file.exists()) {
            return false;
        }
        return true;
    }

    public static boolean isDebuggerAttached() {
        if (!Debug.isDebuggerConnected()) {
            if (!Debug.waitingForDebugger()) {
                return false;
            }
        }
        return true;
    }

    public static int getDeviceState(Context context) {
        int deviceState = 0;
        if (isEmulator(context)) {
            deviceState = 0 | 1;
        }
        if (isRooted(context)) {
            deviceState |= 2;
        }
        if (isDebuggerAttached()) {
            return deviceState | 4;
        }
        return deviceState;
    }

    public static int getBatteryVelocity(Context context, boolean powerConnected) {
        Float batteryLevel = getBatteryLevel(context);
        if (powerConnected) {
            if (batteryLevel != null) {
                if (((double) batteryLevel.floatValue()) >= 99.0d) {
                    return 3;
                }
                if (((double) batteryLevel.floatValue()) < 99.0d) {
                    return 2;
                }
                return 0;
            }
        }
        return 1;
    }

    @Deprecated
    public static Cipher createCipher(int mode, String key) throws InvalidKeyException {
        throw new InvalidKeyException("This method is deprecated");
    }

    public static String hexify(byte[] bytes) {
        char[] hexChars = new char[(bytes.length * 2)];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 255;
            hexChars[i * 2] = HEX_VALUES[v >>> 4];
            hexChars[(i * 2) + 1] = HEX_VALUES[v & 15];
        }
        return new String(hexChars);
    }

    public static byte[] dehexify(String string) {
        int len = string.length();
        byte[] data = new byte[(len / 2)];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4) + Character.digit(string.charAt(i + 1), 16));
        }
        return data;
    }

    public static boolean isAppDebuggable(Context context) {
        return (context.getApplicationInfo().flags & 2) != 0;
    }

    public static String getStringsFileValue(Context context, String key) {
        int id = getResourcesIdentifier(context, key, "string");
        if (id > 0) {
            return context.getString(id);
        }
        return "";
    }

    public static void closeOrLog(Closeable c, String message) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                Fabric.getLogger().e(Fabric.TAG, message, e);
            }
        }
    }

    public static void flushOrLog(Flushable f, String message) {
        if (f != null) {
            try {
                f.flush();
            } catch (IOException e) {
                Fabric.getLogger().e(Fabric.TAG, message, e);
            }
        }
    }

    public static boolean isNullOrEmpty(String s) {
        if (s != null) {
            if (s.length() != 0) {
                return false;
            }
        }
        return true;
    }

    public static String padWithZerosToMaxIntWidth(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("value must be zero or greater");
        }
        return String.format(Locale.US, "%1$10s", new Object[]{Integer.valueOf(value)}).replace(' ', '0');
    }

    public static boolean stringsEqualIncludingNull(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if (s1 != null) {
            return s1.equals(s2);
        }
        return false;
    }

    public static String getResourcePackageName(Context context) {
        int iconId = context.getApplicationContext().getApplicationInfo().icon;
        if (iconId > 0) {
            return context.getResources().getResourcePackageName(iconId);
        }
        return context.getPackageName();
    }

    public static void copyStream(InputStream is, OutputStream os, byte[] buffer) throws IOException {
        while (true) {
            int read = is.read(buffer);
            int count = read;
            if (read != -1) {
                os.write(buffer, 0, count);
            } else {
                return;
            }
        }
    }

    public static String logPriorityToString(int priority) {
        switch (priority) {
            case 2:
                return LOG_PRIORITY_NAME_VERBOSE;
            case 3:
                return LOG_PRIORITY_NAME_DEBUG;
            case 4:
                return LOG_PRIORITY_NAME_INFO;
            case 5:
                return LOG_PRIORITY_NAME_WARN;
            case 6:
                return LOG_PRIORITY_NAME_ERROR;
            case 7:
                return "A";
            default:
                return LOG_PRIORITY_NAME_UNKNOWN;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getAppIconHashOrNull(android.content.Context r6) {
        /*
        r0 = 0;
        r1 = r0;
        r2 = r6.getResources();	 Catch:{ Exception -> 0x0023 }
        r3 = getAppIconResourceId(r6);	 Catch:{ Exception -> 0x0023 }
        r2 = r2.openRawResource(r3);	 Catch:{ Exception -> 0x0023 }
        r1 = r2;
        r2 = sha1(r1);	 Catch:{ Exception -> 0x0023 }
        r3 = isNullOrEmpty(r2);	 Catch:{ Exception -> 0x0023 }
        if (r3 == 0) goto L_0x001a;
    L_0x0019:
        goto L_0x001b;
    L_0x001a:
        r0 = r2;
    L_0x001b:
        r3 = "Failed to close icon input stream.";
        closeOrLog(r1, r3);
        return r0;
    L_0x0021:
        r0 = move-exception;
        goto L_0x0036;
    L_0x0023:
        r2 = move-exception;
        r3 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ all -> 0x0021 }
        r4 = "Fabric";
        r5 = "Could not calculate hash for app icon.";
        r3.e(r4, r5, r2);	 Catch:{ all -> 0x0021 }
        r2 = "Failed to close icon input stream.";
        closeOrLog(r1, r2);
        return r0;
    L_0x0036:
        r2 = "Failed to close icon input stream.";
        closeOrLog(r1, r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.services.common.CommonUtils.getAppIconHashOrNull(android.content.Context):java.lang.String");
    }

    public static int getAppIconResourceId(Context context) {
        return context.getApplicationContext().getApplicationInfo().icon;
    }

    public static String resolveBuildId(Context context) {
        int id = getResourcesIdentifier(context, FABRIC_BUILD_ID, "string");
        if (id == 0) {
            id = getResourcesIdentifier(context, CRASHLYTICS_BUILD_ID, "string");
        }
        if (id == 0) {
            return null;
        }
        String buildId = context.getResources().getString(id);
        Logger logger = Fabric.getLogger();
        String str = Fabric.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Build ID is: ");
        stringBuilder.append(buildId);
        logger.d(str, stringBuilder.toString());
        return buildId;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception e) {
            }
        }
    }

    public static boolean checkPermission(Context context, String permission) {
        return context.checkCallingOrSelfPermission(permission) == 0;
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService("input_method");
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void openKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService("input_method");
        if (imm != null) {
            imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
        }
    }

    @TargetApi(16)
    public static void finishAffinity(Context context, int resultCode) {
        if (context instanceof Activity) {
            finishAffinity((Activity) context, resultCode);
        }
    }

    @TargetApi(16)
    public static void finishAffinity(Activity activity, int resultCode) {
        if (activity != null) {
            if (VERSION.SDK_INT >= 16) {
                activity.finishAffinity();
            } else {
                activity.setResult(resultCode);
                activity.finish();
            }
        }
    }

    public static boolean canTryConnection(Context context) {
        boolean isConnected = true;
        if (!checkPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return true;
        }
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
            isConnected = false;
        }
        return isConnected;
    }

    public static void logOrThrowIllegalStateException(String logTag, String errorMsg) {
        if (Fabric.isDebuggable()) {
            throw new IllegalStateException(errorMsg);
        }
        Fabric.getLogger().w(logTag, errorMsg);
    }

    public static void logOrThrowIllegalArgumentException(String logTag, String errorMsg) {
        if (Fabric.isDebuggable()) {
            throw new IllegalArgumentException(errorMsg);
        }
        Fabric.getLogger().w(logTag, errorMsg);
    }
}
