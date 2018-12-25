package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfmt extends zzflm<zzfmt> implements Cloneable {
    private int zza;
    private int zzb;

    public zzfmt() {
        this.zza = -1;
        this.zzb = 0;
        this.zzax = null;
        this.zzay = -1;
    }

    private zzfmt zzb() {
        try {
            return (zzfmt) super.zzc();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    private final zzfmt zzb(zzflj zzflj) throws IOException {
        int zzm;
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            int zzc;
            StringBuilder stringBuilder;
            if (zza == 8) {
                zzm = zzflj.zzm();
                zzc = zzflj.zzc();
                switch (zzc) {
                    case -1:
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                        this.zza = zzc;
                        break;
                    default:
                        stringBuilder = new StringBuilder(43);
                        stringBuilder.append(zzc);
                        stringBuilder.append(" is not a valid enum NetworkType");
                        throw new IllegalArgumentException(stringBuilder.toString());
                }
            } else if (zza == 16) {
                zzm = zzflj.zzm();
                try {
                    zzc = zzflj.zzc();
                    if (zzc != 100) {
                        switch (zzc) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                            case 14:
                            case 15:
                            case 16:
                                break;
                            default:
                                stringBuilder = new StringBuilder(45);
                                stringBuilder.append(zzc);
                                stringBuilder.append(" is not a valid enum MobileSubtype");
                                throw new IllegalArgumentException(stringBuilder.toString());
                        }
                    }
                    this.zzb = zzc;
                } catch (IllegalArgumentException e) {
                    zzflj.zze(zzm);
                    zza(zzflj, zza);
                }
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzb();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfmt)) {
            return false;
        }
        zzfmt zzfmt = (zzfmt) obj;
        if (this.zza != zzfmt.zza || this.zzb != zzfmt.zzb) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzfmt.zzax);
            }
        }
        return zzfmt.zzax == null || zzfmt.zzax.zzb();
    }

    public final int hashCode() {
        int hashCode;
        int hashCode2 = (((((getClass().getName().hashCode() + 527) * 31) + this.zza) * 31) + this.zzb) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                hashCode = this.zzax.hashCode();
                return hashCode2 + hashCode;
            }
        }
        hashCode = 0;
        return hashCode2 + hashCode;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zza != -1) {
            zza += zzflk.zzb(1, this.zza);
        }
        return this.zzb != 0 ? zza + zzflk.zzb(2, this.zzb) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        return zzb(zzflj);
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != -1) {
            zzflk.zza(1, this.zza);
        }
        if (this.zzb != 0) {
            zzflk.zza(2, this.zzb);
        }
        super.zza(zzflk);
    }

    public final /* synthetic */ zzflm zzc() throws CloneNotSupportedException {
        return (zzfmt) clone();
    }

    public final /* synthetic */ zzfls zzd() throws CloneNotSupportedException {
        return (zzfmt) clone();
    }
}
