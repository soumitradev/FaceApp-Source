package com.google.android.gms.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

final class zzfjz extends zzfjy<FieldDescriptorType, Object> {
    zzfjz(int i) {
        super(i);
    }

    public final void zza() {
        if (!zzb()) {
            Entry zzb;
            for (int i = 0; i < zzc(); i++) {
                zzb = zzb(i);
                if (((zzfhs) zzb.getKey()).zzd()) {
                    zzb.setValue(Collections.unmodifiableList((List) zzb.getValue()));
                }
            }
            for (Entry zzb2 : zzd()) {
                if (((zzfhs) zzb2.getKey()).zzd()) {
                    zzb2.setValue(Collections.unmodifiableList((List) zzb2.getValue()));
                }
            }
        }
        super.zza();
    }
}
