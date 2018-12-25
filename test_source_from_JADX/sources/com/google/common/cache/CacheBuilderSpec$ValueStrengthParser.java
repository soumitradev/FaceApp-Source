package com.google.common.cache;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;

class CacheBuilderSpec$ValueStrengthParser implements CacheBuilderSpec$ValueParser {
    private final LocalCache$Strength strength;

    public CacheBuilderSpec$ValueStrengthParser(LocalCache$Strength strength) {
        this.strength = strength;
    }

    public void parse(CacheBuilderSpec spec, String key, @Nullable String value) {
        Preconditions.checkArgument(value == null, "key %s does not take values", key);
        Preconditions.checkArgument(spec.valueStrength == null, "%s was already set to %s", key, spec.valueStrength);
        spec.valueStrength = this.strength;
    }
}
