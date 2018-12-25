package com.google.android.gms.internal;

import java.io.IOException;

public class zzfie extends IOException {
    private zzfjc zza = null;

    public zzfie(String str) {
        super(str);
    }

    static zzfie zza() {
        return new zzfie("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzfie zzb() {
        return new zzfie("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzfie zzc() {
        return new zzfie("CodedInputStream encountered a malformed varint.");
    }

    static zzfie zzd() {
        return new zzfie("Protocol message contained an invalid tag (zero).");
    }

    static zzfie zze() {
        return new zzfie("Protocol message end-group tag did not match expected tag.");
    }

    static zzfif zzf() {
        return new zzfif("Protocol message tag had invalid wire type.");
    }

    static zzfie zzg() {
        return new zzfie("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    static zzfie zzh() {
        return new zzfie("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit.");
    }

    static zzfie zzi() {
        return new zzfie("Protocol message had invalid UTF-8.");
    }

    public final zzfie zza(zzfjc zzfjc) {
        this.zza = zzfjc;
        return this;
    }
}
