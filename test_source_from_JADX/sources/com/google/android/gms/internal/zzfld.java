package com.google.android.gms.internal;

import org.catrobat.catroid.common.BrickValues;

public enum zzfld {
    INT(Integer.valueOf(0)),
    LONG(Long.valueOf(0)),
    FLOAT(Float.valueOf(0.0f)),
    DOUBLE(Double.valueOf(BrickValues.SET_COLOR_TO)),
    BOOLEAN(Boolean.valueOf(false)),
    STRING(""),
    BYTE_STRING(zzfgs.zza),
    ENUM(null),
    MESSAGE(null);
    
    private final Object zzj;

    private zzfld(Object obj) {
        this.zzj = obj;
    }
}
