package com.google.android.gms.tagmanager;

final class zzgj extends Number implements Comparable<zzgj> {
    private double zza;
    private long zzb;
    private boolean zzc = false;

    private zzgj(double d) {
        this.zza = d;
    }

    private zzgj(long j) {
        this.zzb = j;
    }

    public static zzgj zza(long j) {
        return new zzgj(j);
    }

    public static zzgj zza(Double d) {
        return new zzgj(d.doubleValue());
    }

    public static zzgj zza(String str) throws NumberFormatException {
        try {
            return new zzgj(Long.parseLong(str));
        } catch (NumberFormatException e) {
            try {
                return new zzgj(Double.parseDouble(str));
            } catch (NumberFormatException e2) {
                throw new NumberFormatException(String.valueOf(str).concat(" is not a valid TypedNumber"));
            }
        }
    }

    public final byte byteValue() {
        return (byte) ((int) longValue());
    }

    public final /* synthetic */ int compareTo(Object obj) {
        return zza((zzgj) obj);
    }

    public final double doubleValue() {
        return this.zzc ? (double) this.zzb : this.zza;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof zzgj) && zza((zzgj) obj) == 0;
    }

    public final float floatValue() {
        return (float) doubleValue();
    }

    public final int hashCode() {
        return new Long(longValue()).hashCode();
    }

    public final int intValue() {
        return (int) longValue();
    }

    public final long longValue() {
        return this.zzc ? this.zzb : (long) this.zza;
    }

    public final short shortValue() {
        return (short) ((int) longValue());
    }

    public final String toString() {
        return this.zzc ? Long.toString(this.zzb) : Double.toString(this.zza);
    }

    public final int zza(zzgj zzgj) {
        return (this.zzc && zzgj.zzc) ? new Long(this.zzb).compareTo(Long.valueOf(zzgj.zzb)) : Double.compare(doubleValue(), zzgj.doubleValue());
    }

    public final boolean zza() {
        return !this.zzc;
    }

    public final boolean zzb() {
        return this.zzc;
    }
}
