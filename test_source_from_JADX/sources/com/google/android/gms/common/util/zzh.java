package com.google.android.gms.common.util;

import android.database.CharArrayBuffer;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzh {
    public static void zza(String str, CharArrayBuffer charArrayBuffer) {
        if (TextUtils.isEmpty(str)) {
            charArrayBuffer.sizeCopied = 0;
        } else {
            if (charArrayBuffer.data != null) {
                if (charArrayBuffer.data.length >= str.length()) {
                    str.getChars(0, str.length(), charArrayBuffer.data, 0);
                }
            }
            charArrayBuffer.data = str.toCharArray();
        }
        charArrayBuffer.sizeCopied = str.length();
    }
}
