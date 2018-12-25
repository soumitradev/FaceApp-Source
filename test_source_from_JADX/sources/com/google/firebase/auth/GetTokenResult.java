package com.google.firebase.auth;

import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;

public class GetTokenResult {
    private String zza;

    @Hide
    public GetTokenResult(String str) {
        this.zza = str;
    }

    @Nullable
    public String getToken() {
        return this.zza;
    }
}
