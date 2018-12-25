package com.google.android.gms.internal;

import java.util.List;

public final class zzfkm extends RuntimeException {
    private final List<String> zza = null;

    public zzfkm(zzfjc zzfjc) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
    }

    public final zzfie zza() {
        return new zzfie(getMessage());
    }
}
