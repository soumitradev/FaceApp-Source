package com.squareup.picasso;

import android.net.NetworkInfo;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class PicassoExecutorService extends ThreadPoolExecutor {
    private static final int DEFAULT_THREAD_COUNT = 3;

    private static final class PicassoFutureTask extends FutureTask<BitmapHunter> implements Comparable<PicassoFutureTask> {
        private final BitmapHunter hunter;

        public PicassoFutureTask(BitmapHunter hunter) {
            super(hunter, null);
            this.hunter = hunter;
        }

        public int compareTo(PicassoFutureTask other) {
            int i;
            int i2;
            Picasso$Priority p1 = this.hunter.getPriority();
            Picasso$Priority p2 = other.hunter.getPriority();
            if (p1 == p2) {
                i = this.hunter.sequence;
                i2 = other.hunter.sequence;
            } else {
                i = p2.ordinal();
                i2 = p1.ordinal();
            }
            return i - i2;
        }
    }

    PicassoExecutorService() {
        super(3, 3, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new PicassoThreadFactory());
    }

    void adjustThreadCount(NetworkInfo info) {
        if (info != null) {
            if (info.isConnectedOrConnecting()) {
                int type = info.getType();
                if (!(type == 6 || type == 9)) {
                    switch (type) {
                        case 0:
                            type = info.getSubtype();
                            switch (type) {
                                case 1:
                                case 2:
                                    setThreadCount(1);
                                    break;
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                    setThreadCount(2);
                                    break;
                                default:
                                    switch (type) {
                                        case 12:
                                            break;
                                        case 13:
                                        case 14:
                                        case 15:
                                            setThreadCount(3);
                                            break;
                                        default:
                                            setThreadCount(3);
                                            break;
                                    }
                                    setThreadCount(2);
                                    break;
                            }
                        case 1:
                            break;
                        default:
                            setThreadCount(3);
                            break;
                    }
                }
                setThreadCount(4);
                return;
            }
        }
        setThreadCount(3);
    }

    private void setThreadCount(int threadCount) {
        setCorePoolSize(threadCount);
        setMaximumPoolSize(threadCount);
    }

    public Future<?> submit(Runnable task) {
        PicassoFutureTask ftask = new PicassoFutureTask((BitmapHunter) task);
        execute(ftask);
        return ftask;
    }
}
