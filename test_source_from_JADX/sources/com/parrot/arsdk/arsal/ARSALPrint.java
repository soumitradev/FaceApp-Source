package com.parrot.arsdk.arsal;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

public class ARSALPrint {
    private static native int nativeGetMinLevel();

    private static native void nativePrint(int i, String str, String str2);

    private static native boolean nativeSetMinLevel(int i);

    private ARSALPrint() {
    }

    public static boolean setMinimumLogLevel(ARSAL_PRINT_LEVEL_ENUM level) {
        return nativeSetMinLevel(level.getValue());
    }

    public static ARSAL_PRINT_LEVEL_ENUM getMinimumLogLevel() {
        return ARSAL_PRINT_LEVEL_ENUM.getFromValue(nativeGetMinLevel());
    }

    private static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        for (Throwable t = tr; t != null; t = t.getCause()) {
            if (t instanceof UnknownHostException) {
                return "";
            }
        }
        StringWriter sw = new StringWriter();
        tr.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    /* renamed from: v */
    public static void m537v(String tag, String message, Throwable t) {
        ARSAL_PRINT_LEVEL_ENUM arsal_print_level_enum = ARSAL_PRINT_LEVEL_ENUM.ARSAL_PRINT_VERBOSE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(message);
        stringBuilder.append('\n');
        stringBuilder.append(getStackTraceString(t));
        print(arsal_print_level_enum, tag, stringBuilder.toString());
    }

    public static void wtf(String tag, String message, Throwable t) {
        ARSAL_PRINT_LEVEL_ENUM arsal_print_level_enum = ARSAL_PRINT_LEVEL_ENUM.ARSAL_PRINT_FATAL;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(message);
        stringBuilder.append('\n');
        stringBuilder.append(getStackTraceString(t));
        print(arsal_print_level_enum, tag, stringBuilder.toString());
    }

    /* renamed from: i */
    public static void m535i(String tag, String message, Throwable t) {
        ARSAL_PRINT_LEVEL_ENUM arsal_print_level_enum = ARSAL_PRINT_LEVEL_ENUM.ARSAL_PRINT_INFO;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(message);
        stringBuilder.append('\n');
        stringBuilder.append(getStackTraceString(t));
        print(arsal_print_level_enum, tag, stringBuilder.toString());
    }

    /* renamed from: d */
    public static void m531d(String tag, String message, Throwable t) {
        ARSAL_PRINT_LEVEL_ENUM arsal_print_level_enum = ARSAL_PRINT_LEVEL_ENUM.ARSAL_PRINT_DEBUG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(message);
        stringBuilder.append('\n');
        stringBuilder.append(getStackTraceString(t));
        print(arsal_print_level_enum, tag, stringBuilder.toString());
    }

    /* renamed from: w */
    public static void m539w(String tag, String message, Throwable t) {
        ARSAL_PRINT_LEVEL_ENUM arsal_print_level_enum = ARSAL_PRINT_LEVEL_ENUM.ARSAL_PRINT_WARNING;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(message);
        stringBuilder.append('\n');
        stringBuilder.append(getStackTraceString(t));
        print(arsal_print_level_enum, tag, stringBuilder.toString());
    }

    /* renamed from: e */
    public static void m533e(String tag, String message, Throwable t) {
        ARSAL_PRINT_LEVEL_ENUM arsal_print_level_enum = ARSAL_PRINT_LEVEL_ENUM.ARSAL_PRINT_ERROR;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(message);
        stringBuilder.append('\n');
        stringBuilder.append(getStackTraceString(t));
        print(arsal_print_level_enum, tag, stringBuilder.toString());
    }

    /* renamed from: v */
    public static void m536v(String tag, String message) {
        print(ARSAL_PRINT_LEVEL_ENUM.ARSAL_PRINT_VERBOSE, tag, message);
    }

    public static void wtf(String tag, String message) {
        print(ARSAL_PRINT_LEVEL_ENUM.ARSAL_PRINT_FATAL, tag, message);
    }

    /* renamed from: i */
    public static void m534i(String tag, String message) {
        print(ARSAL_PRINT_LEVEL_ENUM.ARSAL_PRINT_INFO, tag, message);
    }

    /* renamed from: d */
    public static void m530d(String tag, String message) {
        print(ARSAL_PRINT_LEVEL_ENUM.ARSAL_PRINT_DEBUG, tag, message);
    }

    /* renamed from: w */
    public static void m538w(String tag, String message) {
        print(ARSAL_PRINT_LEVEL_ENUM.ARSAL_PRINT_WARNING, tag, message);
    }

    /* renamed from: e */
    public static void m532e(String tag, String message) {
        print(ARSAL_PRINT_LEVEL_ENUM.ARSAL_PRINT_ERROR, tag, message);
    }

    public static void print(ARSAL_PRINT_LEVEL_ENUM level, String tag, String message) {
        nativePrint(level.getValue(), tag, message);
    }
}
