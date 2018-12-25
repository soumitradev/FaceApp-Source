package com.google.common.cache;

import com.google.common.base.Preconditions;

class CacheBuilderSpec$ConcurrencyLevelParser extends CacheBuilderSpec$IntegerParser {
    CacheBuilderSpec$ConcurrencyLevelParser() {
    }

    protected void parseInteger(CacheBuilderSpec spec, int value) {
        Preconditions.checkArgument(spec.concurrencyLevel == null, "concurrency level was already set to ", spec.concurrencyLevel);
        spec.concurrencyLevel = Integer.valueOf(value);
    }
}
