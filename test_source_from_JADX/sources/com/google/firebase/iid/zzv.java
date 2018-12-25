package com.google.firebase.iid;

import android.os.Bundle;
import com.facebook.share.internal.ShareConstants;

final class zzv extends zzt<Bundle> {
    zzv(int i, int i2, Bundle bundle) {
        super(i, 1, bundle);
    }

    final void zza(Bundle bundle) {
        Object bundle2 = bundle.getBundle(ShareConstants.WEB_DIALOG_PARAM_DATA);
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        zza(bundle2);
    }

    final boolean zza() {
        return false;
    }
}
