package com.google.android.gms.internal;

import java.util.Map.Entry;

final class zzfkd implements Comparable<zzfkd>, Entry<K, V> {
    private final K zza;
    private V zzb;
    private /* synthetic */ zzfjy zzc;

    zzfkd(zzfjy zzfjy, K k, V v) {
        this.zzc = zzfjy;
        this.zza = k;
        this.zzb = v;
    }

    zzfkd(zzfjy zzfjy, Entry<K, V> entry) {
        this(zzfjy, (Comparable) entry.getKey(), entry.getValue());
    }

    private static boolean zza(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    public final /* synthetic */ int compareTo(Object obj) {
        return ((Comparable) getKey()).compareTo((Comparable) ((zzfkd) obj).getKey());
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Entry)) {
            return false;
        }
        Entry entry = (Entry) obj;
        return zza(this.zza, entry.getKey()) && zza(this.zzb, entry.getValue());
    }

    public final /* synthetic */ Object getKey() {
        return this.zza;
    }

    public final V getValue() {
        return this.zzb;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = this.zza == null ? 0 : this.zza.hashCode();
        if (this.zzb != null) {
            i = this.zzb.hashCode();
        }
        return hashCode ^ i;
    }

    public final V setValue(V v) {
        this.zzc.zze();
        V v2 = this.zzb;
        this.zzb = v;
        return v2;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zza);
        String valueOf2 = String.valueOf(this.zzb);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(valueOf2).length());
        stringBuilder.append(valueOf);
        stringBuilder.append("=");
        stringBuilder.append(valueOf2);
        return stringBuilder.toString();
    }
}
