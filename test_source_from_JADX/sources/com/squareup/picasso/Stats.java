package com.squareup.picasso;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

class Stats {
    private static final int BITMAP_DECODE_FINISHED = 2;
    private static final int BITMAP_TRANSFORMED_FINISHED = 3;
    private static final int CACHE_HIT = 0;
    private static final int CACHE_MISS = 1;
    private static final int DOWNLOAD_FINISHED = 4;
    private static final String STATS_THREAD_NAME = "Picasso-Stats";
    long averageDownloadSize;
    long averageOriginalBitmapSize;
    long averageTransformedBitmapSize;
    final Cache cache;
    long cacheHits;
    long cacheMisses;
    int downloadCount;
    final Handler handler;
    int originalBitmapCount;
    final HandlerThread statsThread = new HandlerThread(STATS_THREAD_NAME, 10);
    long totalDownloadSize;
    long totalOriginalBitmapSize;
    long totalTransformedBitmapSize;
    int transformedBitmapCount;

    private static class StatsHandler extends Handler {
        private final Stats stats;

        public StatsHandler(Looper looper, Stats stats) {
            super(looper);
            this.stats = stats;
        }

        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case 0:
                    this.stats.performCacheHit();
                    return;
                case 1:
                    this.stats.performCacheMiss();
                    return;
                case 2:
                    this.stats.performBitmapDecoded((long) msg.arg1);
                    return;
                case 3:
                    this.stats.performBitmapTransformed((long) msg.arg1);
                    return;
                case 4:
                    this.stats.performDownloadFinished((Long) msg.obj);
                    return;
                default:
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unhandled stats message.");
                            stringBuilder.append(msg.what);
                            throw new AssertionError(stringBuilder.toString());
                        }
                    });
                    return;
            }
        }
    }

    Stats(Cache cache) {
        this.cache = cache;
        this.statsThread.start();
        Utils.flushStackLocalLeaks(this.statsThread.getLooper());
        this.handler = new StatsHandler(this.statsThread.getLooper(), this);
    }

    void dispatchBitmapDecoded(Bitmap bitmap) {
        processBitmap(bitmap, 2);
    }

    void dispatchBitmapTransformed(Bitmap bitmap) {
        processBitmap(bitmap, 3);
    }

    void dispatchDownloadFinished(long size) {
        this.handler.sendMessage(this.handler.obtainMessage(4, Long.valueOf(size)));
    }

    void dispatchCacheHit() {
        this.handler.sendEmptyMessage(0);
    }

    void dispatchCacheMiss() {
        this.handler.sendEmptyMessage(1);
    }

    void shutdown() {
        this.statsThread.quit();
    }

    void performCacheHit() {
        this.cacheHits++;
    }

    void performCacheMiss() {
        this.cacheMisses++;
    }

    void performDownloadFinished(Long size) {
        this.downloadCount++;
        this.totalDownloadSize += size.longValue();
        this.averageDownloadSize = getAverage(this.downloadCount, this.totalDownloadSize);
    }

    void performBitmapDecoded(long size) {
        this.originalBitmapCount++;
        this.totalOriginalBitmapSize += size;
        this.averageOriginalBitmapSize = getAverage(this.originalBitmapCount, this.totalOriginalBitmapSize);
    }

    void performBitmapTransformed(long size) {
        this.transformedBitmapCount++;
        this.totalTransformedBitmapSize += size;
        this.averageTransformedBitmapSize = getAverage(this.originalBitmapCount, this.totalTransformedBitmapSize);
    }

    StatsSnapshot createSnapshot() {
        int maxSize = this.cache.maxSize();
        int size = this.cache.size();
        long j = this.cacheHits;
        long j2 = this.cacheMisses;
        long j3 = this.totalDownloadSize;
        long j4 = this.totalOriginalBitmapSize;
        long j5 = this.totalTransformedBitmapSize;
        long j6 = this.averageDownloadSize;
        long j7 = this.averageOriginalBitmapSize;
        long j8 = this.averageTransformedBitmapSize;
        int i = this.downloadCount;
        long j9 = j8;
        int i2 = this.originalBitmapCount;
        int i3 = i2;
        return new StatsSnapshot(maxSize, size, j, j2, j3, j4, j5, j6, j7, j9, i, i3, this.transformedBitmapCount, System.currentTimeMillis());
    }

    private void processBitmap(Bitmap bitmap, int what) {
        this.handler.sendMessage(this.handler.obtainMessage(what, Utils.getBitmapBytes(bitmap), 0));
    }

    private static long getAverage(int count, long totalSize) {
        return totalSize / ((long) count);
    }
}
