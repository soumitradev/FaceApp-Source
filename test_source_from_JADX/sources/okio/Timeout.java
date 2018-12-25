package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

public class Timeout {
    public static final Timeout NONE = new C20841();
    private long deadlineNanoTime;
    private boolean hasDeadline;
    private long timeoutNanos;

    /* renamed from: okio.Timeout$1 */
    static class C20841 extends Timeout {
        C20841() {
        }

        public Timeout timeout(long timeout, TimeUnit unit) {
            return this;
        }

        public Timeout deadlineNanoTime(long deadlineNanoTime) {
            return this;
        }

        public void throwIfReached() throws IOException {
        }
    }

    public Timeout timeout(long timeout, TimeUnit unit) {
        if (timeout < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("timeout < 0: ");
            stringBuilder.append(timeout);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (unit == null) {
            throw new IllegalArgumentException("unit == null");
        } else {
            this.timeoutNanos = unit.toNanos(timeout);
            return this;
        }
    }

    public long timeoutNanos() {
        return this.timeoutNanos;
    }

    public boolean hasDeadline() {
        return this.hasDeadline;
    }

    public long deadlineNanoTime() {
        if (this.hasDeadline) {
            return this.deadlineNanoTime;
        }
        throw new IllegalStateException("No deadline");
    }

    public Timeout deadlineNanoTime(long deadlineNanoTime) {
        this.hasDeadline = true;
        this.deadlineNanoTime = deadlineNanoTime;
        return this;
    }

    public final Timeout deadline(long duration, TimeUnit unit) {
        if (duration <= 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("duration <= 0: ");
            stringBuilder.append(duration);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (unit != null) {
            return deadlineNanoTime(System.nanoTime() + unit.toNanos(duration));
        } else {
            throw new IllegalArgumentException("unit == null");
        }
    }

    public Timeout clearTimeout() {
        this.timeoutNanos = 0;
        return this;
    }

    public Timeout clearDeadline() {
        this.hasDeadline = false;
        return this;
    }

    public void throwIfReached() throws IOException {
        if (Thread.interrupted()) {
            throw new InterruptedIOException();
        } else if (this.hasDeadline && System.nanoTime() > this.deadlineNanoTime) {
            throw new IOException("deadline reached");
        }
    }
}
