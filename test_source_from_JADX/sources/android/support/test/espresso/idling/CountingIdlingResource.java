package android.support.test.espresso.idling;

import android.os.SystemClock;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.IdlingResource.ResourceCallback;
import android.text.TextUtils;
import android.util.Log;
import java.util.concurrent.atomic.AtomicInteger;

public final class CountingIdlingResource implements IdlingResource {
    private static final String TAG = "CountingIdlingResource";
    private volatile long becameBusyAt;
    private volatile long becameIdleAt;
    private final AtomicInteger counter;
    private final boolean debugCounting;
    private volatile ResourceCallback resourceCallback;
    private final String resourceName;

    public CountingIdlingResource(String resourceName) {
        this(resourceName, false);
    }

    public CountingIdlingResource(String resourceName, boolean debugCounting) {
        this.counter = new AtomicInteger(0);
        this.becameBusyAt = 0;
        this.becameIdleAt = 0;
        if (TextUtils.isEmpty(resourceName)) {
            throw new IllegalArgumentException("resourceName cannot be empty or null!");
        }
        this.resourceName = resourceName;
        this.debugCounting = debugCounting;
    }

    public String getName() {
        return this.resourceName;
    }

    public boolean isIdleNow() {
        return this.counter.get() == 0;
    }

    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    public void increment() {
        int counterVal = this.counter.getAndIncrement();
        if (counterVal == 0) {
            this.becameBusyAt = SystemClock.uptimeMillis();
        }
        if (this.debugCounting) {
            String str = TAG;
            String str2 = this.resourceName;
            int i = counterVal + 1;
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 51);
            stringBuilder.append("Resource: ");
            stringBuilder.append(str2);
            stringBuilder.append(" in-use-count incremented to: ");
            stringBuilder.append(i);
            Log.i(str, stringBuilder.toString());
        }
    }

    public void decrement() {
        int counterVal = this.counter.decrementAndGet();
        if (counterVal == 0) {
            if (this.resourceCallback != null) {
                this.resourceCallback.onTransitionToIdle();
            }
            this.becameIdleAt = SystemClock.uptimeMillis();
        }
        if (this.debugCounting) {
            String str;
            String str2;
            StringBuilder stringBuilder;
            if (counterVal == 0) {
                str = TAG;
                str2 = this.resourceName;
                long j = this.becameIdleAt - this.becameBusyAt;
                stringBuilder = new StringBuilder(String.valueOf(str2).length() + 65);
                stringBuilder.append("Resource: ");
                stringBuilder.append(str2);
                stringBuilder.append(" went idle! (Time spent not idle: ");
                stringBuilder.append(j);
                stringBuilder.append(")");
                Log.i(str, stringBuilder.toString());
            } else {
                str = TAG;
                str2 = this.resourceName;
                stringBuilder = new StringBuilder(String.valueOf(str2).length() + 51);
                stringBuilder.append("Resource: ");
                stringBuilder.append(str2);
                stringBuilder.append(" in-use-count decremented to: ");
                stringBuilder.append(counterVal);
                Log.i(str, stringBuilder.toString());
            }
        }
        if (counterVal <= -1) {
            StringBuilder stringBuilder2 = new StringBuilder(50);
            stringBuilder2.append("Counter has been corrupted! counterVal=");
            stringBuilder2.append(counterVal);
            throw new IllegalStateException(stringBuilder2.toString());
        }
    }

    public void dumpStateToLogs() {
        StringBuilder message = new StringBuilder("Resource: ");
        message.append(this.resourceName);
        message.append(" inflight transaction count: ");
        message = message.append(this.counter.get());
        if (0 == this.becameBusyAt) {
            String str = TAG;
            message.append(" and has never been busy!");
            Log.i(str, message.toString());
            return;
        }
        message.append(" and was last busy at: ");
        message.append(this.becameBusyAt);
        if (0 == this.becameIdleAt) {
            str = TAG;
            message.append(" AND NEVER WENT IDLE!");
            Log.w(str, message.toString());
            return;
        }
        message.append(" and last went idle at: ");
        message.append(this.becameIdleAt);
        Log.i(TAG, message.toString());
    }
}
