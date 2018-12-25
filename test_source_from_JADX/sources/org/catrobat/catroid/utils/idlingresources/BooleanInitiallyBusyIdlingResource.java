package org.catrobat.catroid.utils.idlingresources;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.IdlingResource.ResourceCallback;
import java.util.concurrent.atomic.AtomicBoolean;

public class BooleanInitiallyBusyIdlingResource implements IdlingResource {
    @Nullable
    private volatile ResourceCallback callback;
    private AtomicBoolean isIdleNow = new AtomicBoolean(false);

    public String getName() {
        return BooleanInitiallyBusyIdlingResource.class.getName();
    }

    public boolean isIdleNow() {
        return this.isIdleNow.get();
    }

    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        this.isIdleNow.set(isIdleNow);
        if (isIdleNow && this.callback != null) {
            this.callback.onTransitionToIdle();
        }
    }
}
