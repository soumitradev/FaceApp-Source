package com.google.android.gms.internal;

import java.io.IOException;

public class zzfhg$zzc extends IOException {
    zzfhg$zzc() {
        super("CodedOutputStream was writing to a flat byte array and ran out of space.");
    }

    zzfhg$zzc(String str, Throwable th) {
        String valueOf = String.valueOf("CodedOutputStream was writing to a flat byte array and ran out of space.: ");
        str = String.valueOf(str);
        super(str.length() != 0 ? valueOf.concat(str) : new String(valueOf), th);
    }

    zzfhg$zzc(Throwable th) {
        super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
    }
}
