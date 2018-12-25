package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.util.concurrent.Callable;

class CrashlyticsCore$3 implements Callable<Boolean> {
    final /* synthetic */ CrashlyticsCore this$0;

    CrashlyticsCore$3(CrashlyticsCore this$0) {
        this.this$0 = this$0;
    }

    public Boolean call() throws Exception {
        try {
            boolean removed = CrashlyticsCore.access$100(this.this$0).remove();
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Initialization marker file removed: ");
            stringBuilder.append(removed);
            logger.d(str, stringBuilder.toString());
            return Boolean.valueOf(removed);
        } catch (Exception e) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Problem encountered deleting Crashlytics initialization marker.", e);
            return Boolean.valueOf(false);
        }
    }
}
