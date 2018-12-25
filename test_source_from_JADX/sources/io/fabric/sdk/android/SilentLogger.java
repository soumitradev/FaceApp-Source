package io.fabric.sdk.android;

public class SilentLogger implements Logger {
    private int logLevel = 7;

    public boolean isLoggable(String tag, int level) {
        return false;
    }

    /* renamed from: d */
    public void mo4810d(String tag, String text, Throwable throwable) {
    }

    /* renamed from: v */
    public void mo4821v(String tag, String text, Throwable throwable) {
    }

    /* renamed from: i */
    public void mo4815i(String tag, String text, Throwable throwable) {
    }

    /* renamed from: w */
    public void mo4823w(String tag, String text, Throwable throwable) {
    }

    /* renamed from: e */
    public void mo4812e(String tag, String text, Throwable throwable) {
    }

    /* renamed from: d */
    public void mo4809d(String tag, String text) {
    }

    /* renamed from: v */
    public void mo4820v(String tag, String text) {
    }

    /* renamed from: i */
    public void mo4814i(String tag, String text) {
    }

    /* renamed from: w */
    public void mo4822w(String tag, String text) {
    }

    /* renamed from: e */
    public void mo4811e(String tag, String text) {
    }

    public void log(int priority, String tag, String msg) {
    }

    public void log(int priority, String tag, String msg, boolean forceLog) {
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(int logLevel) {
    }
}
