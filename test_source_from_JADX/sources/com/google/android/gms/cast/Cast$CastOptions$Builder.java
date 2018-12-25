package com.google.android.gms.cast;

import android.os.Bundle;
import com.google.android.gms.cast.Cast.Listener;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

public final class Cast$CastOptions$Builder {
    CastDevice zza;
    Listener zzb;
    private int zzc = 0;
    private Bundle zzd;

    public Cast$CastOptions$Builder(CastDevice castDevice, Listener listener) {
        zzbq.zza(castDevice, "CastDevice parameter cannot be null");
        zzbq.zza(listener, "CastListener parameter cannot be null");
        this.zza = castDevice;
        this.zzb = listener;
    }

    public final Cast$CastOptions build() {
        return new Cast$CastOptions(this, null);
    }

    public final com.google.android.gms.cast.Cast$CastOptions$Builder setVerboseLoggingEnabled(boolean r1) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:5:0x000e in {1, 3, 4} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r0 = this;
        if (r1 == 0) goto L_0x0009;
    L_0x0002:
        r1 = r0.zzc;
        r1 = r1 | 1;
    L_0x0006:
        r0.zzc = r1;
        return r0;
    L_0x0009:
        r1 = r0.zzc;
        r1 = r1 & -2;
        goto L_0x0006;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.Cast$CastOptions$Builder.setVerboseLoggingEnabled(boolean):com.google.android.gms.cast.Cast$CastOptions$Builder");
    }

    @Hide
    public final Cast$CastOptions$Builder zza(Bundle bundle) {
        this.zzd = bundle;
        return this;
    }
}
