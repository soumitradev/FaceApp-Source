package com.google.android.gms.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public abstract class zzfgj<MessageType extends zzfgj<MessageType, BuilderType>, BuilderType extends zzfgk<MessageType, BuilderType>> implements zzfjc {
    private static boolean zzb = false;
    protected int zza = 0;

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzfgk.zza((Iterable) iterable, (List) list);
    }

    public final void zza(OutputStream outputStream) throws IOException {
        zzfhg zza = zzfhg.zza(outputStream, zzfhg.zza(zza()));
        zza(zza);
        zza.zza();
    }

    public final zzfgs zzp() {
        try {
            zzfgx zzb = zzfgs.zzb(zza());
            zza(zzb.zzb());
            return zzb.zza();
        } catch (Throwable e) {
            String str = "ByteString";
            String name = getClass().getName();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(name).length() + 62) + String.valueOf(str).length());
            stringBuilder.append("Serializing ");
            stringBuilder.append(name);
            stringBuilder.append(" to a ");
            stringBuilder.append(str);
            stringBuilder.append(" threw an IOException (should never happen).");
            throw new RuntimeException(stringBuilder.toString(), e);
        }
    }

    public final byte[] zzq() {
        try {
            byte[] bArr = new byte[zza()];
            zzfhg zza = zzfhg.zza(bArr);
            zza(zza);
            zza.zzc();
            return bArr;
        } catch (Throwable e) {
            String str = "byte array";
            String name = getClass().getName();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(name).length() + 62) + String.valueOf(str).length());
            stringBuilder.append("Serializing ");
            stringBuilder.append(name);
            stringBuilder.append(" to a ");
            stringBuilder.append(str);
            stringBuilder.append(" threw an IOException (should never happen).");
            throw new RuntimeException(stringBuilder.toString(), e);
        }
    }
}
