package com.google.common.cache;

import com.google.common.base.Preconditions;
import java.util.concurrent.TimeUnit;

abstract class CacheBuilderSpec$DurationParser implements CacheBuilderSpec$ValueParser {
    protected abstract void parseDuration(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit);

    CacheBuilderSpec$DurationParser() {
    }

    public void parse(CacheBuilderSpec spec, String key, String value) {
        boolean z = (value == null || value.isEmpty()) ? false : true;
        Preconditions.checkArgument(z, "value of key %s omitted", key);
        try {
            TimeUnit timeUnit;
            char lastChar = value.charAt(value.length() - '\u0001');
            if (lastChar == 'd') {
                timeUnit = TimeUnit.DAYS;
            } else if (lastChar == 'h') {
                timeUnit = TimeUnit.HOURS;
            } else if (lastChar == 'm') {
                timeUnit = TimeUnit.MINUTES;
            } else if (lastChar != 's') {
                throw new IllegalArgumentException(CacheBuilderSpec.access$000("key %s invalid format.  was %s, must end with one of [dDhHmMsS]", new Object[]{key, value}));
            } else {
                timeUnit = TimeUnit.SECONDS;
            }
            parseDuration(spec, Long.parseLong(value.substring(0, value.length() - 1)), timeUnit);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(CacheBuilderSpec.access$000("key %s value set to %s, must be integer", new Object[]{key, value}));
        }
    }
}
