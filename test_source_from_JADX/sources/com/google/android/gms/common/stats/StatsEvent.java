package com.google.android.gms.common.stats;

import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbgl;
import org.catrobat.catroid.physics.PhysicsCollision;

public abstract class StatsEvent extends zzbgl implements ReflectedParcelable {
    public String toString() {
        long zza = zza();
        int zzb = zzb();
        long zzc = zzc();
        String zzd = zzd();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(zzd).length() + 53);
        stringBuilder.append(zza);
        stringBuilder.append(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR);
        stringBuilder.append(zzb);
        stringBuilder.append(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR);
        stringBuilder.append(zzc);
        stringBuilder.append(zzd);
        return stringBuilder.toString();
    }

    public abstract long zza();

    public abstract int zzb();

    public abstract long zzc();

    public abstract String zzd();
}
