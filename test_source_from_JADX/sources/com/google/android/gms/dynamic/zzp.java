package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.zzs;

public abstract class zzp<T> {
    private final String zza;
    private T zzb;

    protected zzp(String str) {
        this.zza = str;
    }

    protected abstract T zza(IBinder iBinder);

    protected final T zzb(Context context) throws zzq {
        if (this.zzb == null) {
            zzbq.zza((Object) context);
            context = zzs.getRemoteContext(context);
            if (context == null) {
                throw new zzq("Could not get remote context.");
            }
            try {
                this.zzb = zza((IBinder) context.getClassLoader().loadClass(this.zza).newInstance());
            } catch (Throwable e) {
                throw new zzq("Could not load creator class.", e);
            } catch (Throwable e2) {
                throw new zzq("Could not instantiate creator.", e2);
            } catch (Throwable e22) {
                throw new zzq("Could not access creator.", e22);
            }
        }
        return this.zzb;
    }
}
