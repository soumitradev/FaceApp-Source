package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import org.catrobat.catroid.common.BrickValues;

@GwtCompatible
public final class CacheStats {
    private final long evictionCount;
    private final long hitCount;
    private final long loadExceptionCount;
    private final long loadSuccessCount;
    private final long missCount;
    private final long totalLoadTime;

    public CacheStats(long hitCount, long missCount, long loadSuccessCount, long loadExceptionCount, long totalLoadTime, long evictionCount) {
        CacheStats cacheStats = this;
        long j = hitCount;
        long j2 = missCount;
        long j3 = loadSuccessCount;
        long j4 = loadExceptionCount;
        long j5 = totalLoadTime;
        long j6 = evictionCount;
        Preconditions.checkArgument(j >= 0);
        Preconditions.checkArgument(j2 >= 0);
        Preconditions.checkArgument(j3 >= 0);
        Preconditions.checkArgument(j4 >= 0);
        Preconditions.checkArgument(j5 >= 0);
        Preconditions.checkArgument(j6 >= 0);
        cacheStats.hitCount = j;
        cacheStats.missCount = j2;
        cacheStats.loadSuccessCount = j3;
        cacheStats.loadExceptionCount = j4;
        cacheStats.totalLoadTime = j5;
        cacheStats.evictionCount = j6;
    }

    public long requestCount() {
        return this.hitCount + this.missCount;
    }

    public long hitCount() {
        return this.hitCount;
    }

    public double hitRate() {
        long requestCount = requestCount();
        return requestCount == 0 ? 1.0d : ((double) this.hitCount) / ((double) requestCount);
    }

    public long missCount() {
        return this.missCount;
    }

    public double missRate() {
        long requestCount = requestCount();
        return requestCount == 0 ? BrickValues.SET_COLOR_TO : ((double) this.missCount) / ((double) requestCount);
    }

    public long loadCount() {
        return this.loadSuccessCount + this.loadExceptionCount;
    }

    public long loadSuccessCount() {
        return this.loadSuccessCount;
    }

    public long loadExceptionCount() {
        return this.loadExceptionCount;
    }

    public double loadExceptionRate() {
        long totalLoadCount = this.loadSuccessCount + this.loadExceptionCount;
        return totalLoadCount == 0 ? BrickValues.SET_COLOR_TO : ((double) this.loadExceptionCount) / ((double) totalLoadCount);
    }

    public long totalLoadTime() {
        return this.totalLoadTime;
    }

    public double averageLoadPenalty() {
        long totalLoadCount = this.loadSuccessCount + this.loadExceptionCount;
        return totalLoadCount == 0 ? BrickValues.SET_COLOR_TO : ((double) this.totalLoadTime) / ((double) totalLoadCount);
    }

    public long evictionCount() {
        return this.evictionCount;
    }

    public CacheStats minus(CacheStats other) {
        CacheStats cacheStats = other;
        long max = Math.max(0, this.hitCount - cacheStats.hitCount);
        long max2 = Math.max(0, this.missCount - cacheStats.missCount);
        long max3 = Math.max(0, this.loadSuccessCount - cacheStats.loadSuccessCount);
        return new CacheStats(max, max2, max3, Math.max(0, this.loadExceptionCount - cacheStats.loadExceptionCount), Math.max(0, this.totalLoadTime - cacheStats.totalLoadTime), Math.max(0, this.evictionCount - cacheStats.evictionCount));
    }

    public CacheStats plus(CacheStats other) {
        CacheStats cacheStats = other;
        return new CacheStats(this.hitCount + cacheStats.hitCount, this.missCount + cacheStats.missCount, this.loadSuccessCount + cacheStats.loadSuccessCount, this.loadExceptionCount + cacheStats.loadExceptionCount, this.totalLoadTime + cacheStats.totalLoadTime, this.evictionCount + cacheStats.evictionCount);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{Long.valueOf(this.hitCount), Long.valueOf(this.missCount), Long.valueOf(this.loadSuccessCount), Long.valueOf(this.loadExceptionCount), Long.valueOf(this.totalLoadTime), Long.valueOf(this.evictionCount)});
    }

    public boolean equals(@Nullable Object object) {
        boolean z = false;
        if (!(object instanceof CacheStats)) {
            return false;
        }
        CacheStats other = (CacheStats) object;
        if (this.hitCount == other.hitCount && this.missCount == other.missCount && this.loadSuccessCount == other.loadSuccessCount && this.loadExceptionCount == other.loadExceptionCount && this.totalLoadTime == other.totalLoadTime && this.evictionCount == other.evictionCount) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("hitCount", this.hitCount).add("missCount", this.missCount).add("loadSuccessCount", this.loadSuccessCount).add("loadExceptionCount", this.loadExceptionCount).add("totalLoadTime", this.totalLoadTime).add("evictionCount", this.evictionCount).toString();
    }
}
