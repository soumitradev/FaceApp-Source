package com.google.common.cache;

import com.google.common.base.Preconditions;

class CacheBuilderSpec$MaximumSizeParser extends CacheBuilderSpec$LongParser {
    CacheBuilderSpec$MaximumSizeParser() {
    }

    protected void parseLong(CacheBuilderSpec spec, long value) {
        Preconditions.checkArgument(spec.maximumSize == null, "maximum size was already set to ", spec.maximumSize);
        Preconditions.checkArgument(spec.maximumWeight == null, "maximum weight was already set to ", spec.maximumWeight);
        spec.maximumSize = Long.valueOf(value);
    }
}
