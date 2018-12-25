package org.catrobat.catroid.ui;

import android.util.Log;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ViewSwitchLock implements Lock {
    private static final String TAG = ViewSwitchLock.class.getSimpleName();
    private static final long UNLOCK_TIMEOUT = 200;
    private boolean locked = false;

    /* renamed from: org.catrobat.catroid.ui.ViewSwitchLock$1 */
    class C19131 implements Runnable {
        C19131() {
        }

        public void run() {
            try {
                Thread.sleep(ViewSwitchLock.UNLOCK_TIMEOUT);
            } catch (InterruptedException interruptedException) {
                Log.e(ViewSwitchLock.TAG, Log.getStackTraceString(interruptedException));
            }
            ViewSwitchLock.this.unlock();
        }
    }

    public synchronized boolean tryLock() {
        if (this.locked) {
            return false;
        }
        this.locked = true;
        new Thread(new C19131()).start();
        return true;
    }

    public synchronized void unlock() {
        this.locked = false;
    }

    public void lock() {
        throw new UnsupportedOperationException("Unsupported Method");
    }

    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("Unsupported Method");
    }

    public Condition newCondition() {
        throw new UnsupportedOperationException("Unsupported Method");
    }

    public boolean tryLock(long arg0, TimeUnit arg1) throws InterruptedException {
        throw new UnsupportedOperationException("Unsupported Method");
    }
}
