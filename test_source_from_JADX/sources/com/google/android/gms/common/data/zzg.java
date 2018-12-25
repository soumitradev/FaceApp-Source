package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.Hide;
import java.util.ArrayList;

@Hide
public abstract class zzg<T> extends AbstractDataBuffer<T> {
    private boolean zzb = false;
    private ArrayList<Integer> zzc;

    protected zzg(DataHolder dataHolder) {
        super(dataHolder);
    }

    private final int zza(int i) {
        if (i >= 0) {
            if (i < this.zzc.size()) {
                return ((Integer) this.zzc.get(i)).intValue();
            }
        }
        StringBuilder stringBuilder = new StringBuilder(53);
        stringBuilder.append("Position ");
        stringBuilder.append(i);
        stringBuilder.append(" is out of bounds for this buffer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private final void zzc() {
        synchronized (this) {
            if (!this.zzb) {
                int i = this.zza.zza;
                this.zzc = new ArrayList();
                if (i > 0) {
                    this.zzc.add(Integer.valueOf(0));
                    String zzb = zzb();
                    Object zzc = this.zza.zzc(zzb, 0, this.zza.zza(0));
                    for (int i2 = 1; i2 < i; i2++) {
                        int zza = this.zza.zza(i2);
                        String zzc2 = this.zza.zzc(zzb, i2, zza);
                        if (zzc2 == null) {
                            StringBuilder stringBuilder = new StringBuilder(String.valueOf(zzb).length() + 78);
                            stringBuilder.append("Missing value for markerColumn: ");
                            stringBuilder.append(zzb);
                            stringBuilder.append(", at row: ");
                            stringBuilder.append(i2);
                            stringBuilder.append(", for window: ");
                            stringBuilder.append(zza);
                            throw new NullPointerException(stringBuilder.toString());
                        }
                        if (!zzc2.equals(zzc)) {
                            this.zzc.add(Integer.valueOf(i2));
                            zzc = zzc2;
                        }
                    }
                }
                this.zzb = true;
            }
        }
    }

    public final T get(int i) {
        int intValue;
        zzc();
        int zza = zza(i);
        if (i >= 0) {
            if (i != this.zzc.size()) {
                intValue = (i == this.zzc.size() - 1 ? this.zza.zza : ((Integer) this.zzc.get(i + 1)).intValue()) - ((Integer) this.zzc.get(i)).intValue();
                if (intValue == 1) {
                    this.zza.zza(zza(i));
                }
                return zza(zza, intValue);
            }
        }
        intValue = 0;
        return zza(zza, intValue);
    }

    public int getCount() {
        zzc();
        return this.zzc.size();
    }

    @Hide
    protected abstract T zza(int i, int i2);

    @Hide
    protected abstract String zzb();
}
