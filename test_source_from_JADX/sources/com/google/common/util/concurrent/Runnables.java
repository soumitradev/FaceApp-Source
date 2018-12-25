package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@Beta
public final class Runnables {
    private static final Runnable EMPTY_RUNNABLE = new C06021();

    /* renamed from: com.google.common.util.concurrent.Runnables$1 */
    static class C06021 implements Runnable {
        C06021() {
        }

        public void run() {
        }
    }

    public static Runnable doNothing() {
        return EMPTY_RUNNABLE;
    }

    private Runnables() {
    }
}
