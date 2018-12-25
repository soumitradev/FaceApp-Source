package com.google.android.gms.analytics;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.LogPrinter;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.Hide;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Hide
public final class zze implements zzo {
    private static final Uri zza;
    private final LogPrinter zzb = new LogPrinter(4, "GA/LogCatTransport");

    static {
        Builder builder = new Builder();
        builder.scheme(ShareConstants.MEDIA_URI);
        builder.authority("local");
        zza = builder.build();
    }

    public final Uri zza() {
        return zza;
    }

    public final void zza(zzg zzg) {
        List arrayList = new ArrayList(zzg.zzb());
        Collections.sort(arrayList, new zzf(this));
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList arrayList2 = (ArrayList) arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            obj = ((zzi) obj).toString();
            if (!TextUtils.isEmpty(obj)) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(obj);
            }
        }
        this.zzb.println(stringBuilder.toString());
    }
}
