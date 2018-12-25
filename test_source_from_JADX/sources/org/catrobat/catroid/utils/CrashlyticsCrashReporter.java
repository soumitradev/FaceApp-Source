package org.catrobat.catroid.utils;

import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.Crashlytics.Builder;
import com.crashlytics.android.core.CrashlyticsCore;
import io.fabric.sdk.android.Fabric;
import org.catrobat.catroid.BuildConfig;

class CrashlyticsCrashReporter implements CrashReporterInterface {
    CrashlyticsCrashReporter() {
    }

    public void initialize(Context context) {
        Fabric.with(context, new Builder().core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build());
    }

    public void logException(Throwable exception) {
        Crashlytics.logException(exception);
    }
}
