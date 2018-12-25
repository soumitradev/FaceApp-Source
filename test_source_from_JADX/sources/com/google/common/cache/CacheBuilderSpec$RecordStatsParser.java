package com.google.common.cache;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;

class CacheBuilderSpec$RecordStatsParser implements CacheBuilderSpec$ValueParser {
    CacheBuilderSpec$RecordStatsParser() {
    }

    public void parse(CacheBuilderSpec spec, String key, @Nullable String value) {
        boolean z = false;
        Preconditions.checkArgument(value == null, "recordStats does not take values");
        if (spec.recordStats == null) {
            z = true;
        }
        Preconditions.checkArgument(z, "recordStats already set");
        spec.recordStats = Boolean.valueOf(true);
    }
}
