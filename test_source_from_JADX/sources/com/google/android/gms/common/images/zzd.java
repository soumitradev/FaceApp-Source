package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.images.ImageManager.OnImageLoadedListener;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzc;
import java.lang.ref.WeakReference;
import java.util.Arrays;

public final class zzd extends zza {
    private WeakReference<OnImageLoadedListener> zzc;

    public zzd(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        super(uri, 0);
        zzc.zza((Object) onImageLoadedListener);
        this.zzc = new WeakReference(onImageLoadedListener);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzd)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        zzd zzd = (zzd) obj;
        OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.zzc.get();
        OnImageLoadedListener onImageLoadedListener2 = (OnImageLoadedListener) zzd.zzc.get();
        return onImageLoadedListener2 != null && onImageLoadedListener != null && zzbg.zza(onImageLoadedListener2, onImageLoadedListener) && zzbg.zza(zzd.zza, this.zza);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza});
    }

    protected final void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
        if (!z2) {
            OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.zzc.get();
            if (onImageLoadedListener != null) {
                onImageLoadedListener.onImageLoaded(this.zza.zza, drawable, z3);
            }
        }
    }
}
