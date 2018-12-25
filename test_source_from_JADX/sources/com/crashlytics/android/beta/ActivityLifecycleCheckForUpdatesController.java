package com.crashlytics.android.beta;

import android.annotation.TargetApi;
import android.app.Activity;
import io.fabric.sdk.android.ActivityLifecycleManager;
import io.fabric.sdk.android.ActivityLifecycleManager.Callbacks;
import java.util.concurrent.ExecutorService;

@TargetApi(14)
class ActivityLifecycleCheckForUpdatesController extends AbstractCheckForUpdatesController {
    private final Callbacks callbacks = new C08031();
    private final ExecutorService executorService;

    /* renamed from: com.crashlytics.android.beta.ActivityLifecycleCheckForUpdatesController$1 */
    class C08031 extends Callbacks {

        /* renamed from: com.crashlytics.android.beta.ActivityLifecycleCheckForUpdatesController$1$1 */
        class C03701 implements Runnable {
            C03701() {
            }

            public void run() {
                ActivityLifecycleCheckForUpdatesController.this.checkForUpdates();
            }
        }

        C08031() {
        }

        public void onActivityStarted(Activity activity) {
            if (ActivityLifecycleCheckForUpdatesController.this.signalExternallyReady()) {
                ActivityLifecycleCheckForUpdatesController.this.executorService.submit(new C03701());
            }
        }
    }

    public ActivityLifecycleCheckForUpdatesController(ActivityLifecycleManager lifecycleManager, ExecutorService executorService) {
        this.executorService = executorService;
        lifecycleManager.registerCallbacks(this.callbacks);
    }

    public boolean isActivityLifecycleTriggered() {
        return true;
    }
}
