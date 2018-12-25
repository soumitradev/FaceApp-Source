package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.data.DataHolder.zza;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgp;

@Hide
public class zzd<T extends zzbgp> extends AbstractDataBuffer<T> {
    private static final String[] zzb = new String[]{ShareConstants.WEB_DIALOG_PARAM_DATA};
    private final Creator<T> zzc;

    public zzd(DataHolder dataHolder, Creator<T> creator) {
        super(dataHolder);
        this.zzc = creator;
    }

    public static <T extends zzbgp> void zza(zza zza, T t) {
        Parcel obtain = Parcel.obtain();
        t.writeToParcel(obtain, 0);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShareConstants.WEB_DIALOG_PARAM_DATA, obtain.marshall());
        zza.zza(contentValues);
        obtain.recycle();
    }

    public static zza zzb() {
        return DataHolder.zza(zzb);
    }

    public /* synthetic */ Object get(int i) {
        return zza(i);
    }

    public T zza(int i) {
        byte[] zzf = this.zza.zzf(ShareConstants.WEB_DIALOG_PARAM_DATA, i, this.zza.zza(i));
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(zzf, 0, zzf.length);
        obtain.setDataPosition(0);
        zzbgp zzbgp = (zzbgp) this.zzc.createFromParcel(obtain);
        obtain.recycle();
        return zzbgp;
    }
}
