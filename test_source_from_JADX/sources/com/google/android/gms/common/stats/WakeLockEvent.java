package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.internal.zzbgo;
import java.util.List;
import org.catrobat.catroid.physics.PhysicsCollision;

public final class WakeLockEvent extends StatsEvent {
    public static final Creator<WakeLockEvent> CREATOR = new zzd();
    private int zza;
    private final long zzb;
    private int zzc;
    private final String zzd;
    private final String zze;
    private final String zzf;
    private final int zzg;
    private final List<String> zzh;
    private final String zzi;
    private final long zzj;
    private int zzk;
    private final String zzl;
    private final float zzm;
    private final long zzn;
    private long zzo;

    WakeLockEvent(int i, long j, int i2, String str, int i3, List<String> list, String str2, long j2, int i4, String str3, String str4, float f, long j3, String str5) {
        this.zza = i;
        this.zzb = j;
        this.zzc = i2;
        this.zzd = str;
        this.zze = str3;
        this.zzf = str5;
        this.zzg = i3;
        this.zzo = -1;
        this.zzh = list;
        this.zzi = str2;
        this.zzj = j2;
        this.zzk = i4;
        this.zzl = str4;
        this.zzm = f;
        this.zzn = j3;
    }

    public WakeLockEvent(long j, int i, String str, int i2, List<String> list, String str2, long j2, int i3, String str3, String str4, float f, long j3, String str5) {
        this(2, j, i, str, i2, list, str2, j2, i3, str3, str4, f, j3, str5);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, zza());
        zzbgo.zza(parcel, 4, this.zzd, false);
        zzbgo.zza(parcel, 5, this.zzg);
        zzbgo.zzb(parcel, 6, this.zzh, false);
        zzbgo.zza(parcel, 8, this.zzj);
        zzbgo.zza(parcel, 10, this.zze, false);
        zzbgo.zza(parcel, 11, zzb());
        zzbgo.zza(parcel, 12, this.zzi, false);
        zzbgo.zza(parcel, 13, this.zzl, false);
        zzbgo.zza(parcel, 14, this.zzk);
        zzbgo.zza(parcel, 15, this.zzm);
        zzbgo.zza(parcel, 16, this.zzn);
        zzbgo.zza(parcel, 17, this.zzf, false);
        zzbgo.zza(parcel, i);
    }

    public final long zza() {
        return this.zzb;
    }

    public final int zzb() {
        return this.zzc;
    }

    public final long zzc() {
        return this.zzo;
    }

    public final String zzd() {
        String str = this.zzd;
        int i = this.zzg;
        String join = this.zzh == null ? "" : TextUtils.join(",", this.zzh);
        int i2 = this.zzk;
        String str2 = this.zze == null ? "" : this.zze;
        String str3 = this.zzl == null ? "" : this.zzl;
        float f = this.zzm;
        String str4 = this.zzf == null ? "" : this.zzf;
        StringBuilder stringBuilder = new StringBuilder(((((String.valueOf(str).length() + 45) + String.valueOf(join).length()) + String.valueOf(str2).length()) + String.valueOf(str3).length()) + String.valueOf(str4).length());
        stringBuilder.append(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR);
        stringBuilder.append(str);
        stringBuilder.append(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR);
        stringBuilder.append(i);
        stringBuilder.append(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR);
        stringBuilder.append(join);
        stringBuilder.append(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR);
        stringBuilder.append(i2);
        stringBuilder.append(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR);
        stringBuilder.append(str2);
        stringBuilder.append(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR);
        stringBuilder.append(str3);
        stringBuilder.append(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR);
        stringBuilder.append(f);
        stringBuilder.append(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR);
        stringBuilder.append(str4);
        return stringBuilder.toString();
    }
}
