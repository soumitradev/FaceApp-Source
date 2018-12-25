package io.fabric.sdk.android;

public interface Logger {
    /* renamed from: d */
    void mo4809d(String str, String str2);

    /* renamed from: d */
    void mo4810d(String str, String str2, Throwable th);

    /* renamed from: e */
    void mo4811e(String str, String str2);

    /* renamed from: e */
    void mo4812e(String str, String str2, Throwable th);

    int getLogLevel();

    /* renamed from: i */
    void mo4814i(String str, String str2);

    /* renamed from: i */
    void mo4815i(String str, String str2, Throwable th);

    boolean isLoggable(String str, int i);

    void log(int i, String str, String str2);

    void log(int i, String str, String str2, boolean z);

    void setLogLevel(int i);

    /* renamed from: v */
    void mo4820v(String str, String str2);

    /* renamed from: v */
    void mo4821v(String str, String str2, Throwable th);

    /* renamed from: w */
    void mo4822w(String str, String str2);

    /* renamed from: w */
    void mo4823w(String str, String str2, Throwable th);
}
