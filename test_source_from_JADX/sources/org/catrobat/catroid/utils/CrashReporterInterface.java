package org.catrobat.catroid.utils;

import android.content.Context;

public interface CrashReporterInterface {
    void initialize(Context context);

    void logException(Throwable th);
}
