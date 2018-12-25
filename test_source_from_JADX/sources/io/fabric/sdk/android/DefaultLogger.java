package io.fabric.sdk.android;

import android.util.Log;

public class DefaultLogger implements Logger {
    private int logLevel;

    public DefaultLogger(int logLevel) {
        this.logLevel = logLevel;
    }

    public DefaultLogger() {
        this.logLevel = 4;
    }

    public boolean isLoggable(String tag, int level) {
        return this.logLevel <= level;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    /* renamed from: d */
    public void mo4810d(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 3)) {
            Log.d(tag, text, throwable);
        }
    }

    /* renamed from: v */
    public void mo4821v(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 2)) {
            Log.v(tag, text, throwable);
        }
    }

    /* renamed from: i */
    public void mo4815i(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 4)) {
            Log.i(tag, text, throwable);
        }
    }

    /* renamed from: w */
    public void mo4823w(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 5)) {
            Log.w(tag, text, throwable);
        }
    }

    /* renamed from: e */
    public void mo4812e(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 6)) {
            Log.e(tag, text, throwable);
        }
    }

    /* renamed from: d */
    public void mo4809d(String tag, String text) {
        mo4810d(tag, text, null);
    }

    /* renamed from: v */
    public void mo4820v(String tag, String text) {
        mo4821v(tag, text, null);
    }

    /* renamed from: i */
    public void mo4814i(String tag, String text) {
        mo4815i(tag, text, null);
    }

    /* renamed from: w */
    public void mo4822w(String tag, String text) {
        mo4823w(tag, text, null);
    }

    /* renamed from: e */
    public void mo4811e(String tag, String text) {
        mo4812e(tag, text, null);
    }

    public void log(int priority, String tag, String msg) {
        log(priority, tag, msg, false);
    }

    public void log(int priority, String tag, String msg, boolean forceLog) {
        if (forceLog || isLoggable(tag, priority)) {
            Log.println(priority, tag, msg);
        }
    }
}
