package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbge;
import com.google.android.gms.internal.zzbgj;
import java.lang.ref.WeakReference;

public final class zzc extends zza {
    private WeakReference<ImageView> zzc;

    public zzc(ImageView imageView, int i) {
        super(null, i);
        com.google.android.gms.common.internal.zzc.zza((Object) imageView);
        this.zzc = new WeakReference(imageView);
    }

    public zzc(ImageView imageView, Uri uri) {
        super(uri, 0);
        com.google.android.gms.common.internal.zzc.zza((Object) imageView);
        this.zzc = new WeakReference(imageView);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ImageView imageView = (ImageView) this.zzc.get();
        ImageView imageView2 = (ImageView) ((zzc) obj).zzc.get();
        return (imageView2 == null || imageView == null || !zzbg.zza(imageView2, imageView)) ? false : true;
    }

    public final int hashCode() {
        return 0;
    }

    protected final void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
        ImageView imageView = (ImageView) this.zzc.get();
        if (imageView != null) {
            int i = 0;
            Object obj = (z2 || z3) ? null : 1;
            if (obj != null && (imageView instanceof zzbgj)) {
                int zza = zzbgj.zza();
                if (this.zzb != 0 && zza == this.zzb) {
                    return;
                }
            }
            z = zza(z, z2);
            Uri uri = null;
            if (z) {
                Drawable drawable2 = imageView.getDrawable();
                if (drawable2 == null) {
                    drawable2 = null;
                } else if (drawable2 instanceof zzbge) {
                    drawable2 = ((zzbge) drawable2).zza();
                }
                drawable = new zzbge(drawable2, drawable);
            }
            imageView.setImageDrawable(drawable);
            if (imageView instanceof zzbgj) {
                if (z3) {
                    uri = this.zza.zza;
                }
                zzbgj.zza(uri);
                if (obj != null) {
                    i = this.zzb;
                }
                zzbgj.zza(i);
            }
            if (z) {
                ((zzbge) drawable).zza(250);
            }
        }
    }
}
