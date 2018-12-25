package com.google.android.gms.tagmanager;

import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.Hide;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;

@Hide
public final class zzo {
    public static String zza(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bArr) {
            if ((b & SysexMessageWriter.COMMAND_START) == 0) {
                stringBuilder.append(AppEventsConstants.EVENT_PARAM_VALUE_NO);
            }
            stringBuilder.append(Integer.toHexString(b & 255));
        }
        return stringBuilder.toString().toUpperCase();
    }

    public static byte[] zza(String str) {
        int length = str.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("purported base16 string has odd number of characters");
        }
        byte[] bArr = new byte[(length / 2)];
        int i = 0;
        while (i < length) {
            int digit = Character.digit(str.charAt(i), 16);
            int digit2 = Character.digit(str.charAt(i + 1), 16);
            if (digit != -1) {
                if (digit2 != -1) {
                    bArr[i / 2] = (byte) ((digit << 4) + digit2);
                    i += 2;
                }
            }
            throw new IllegalArgumentException("purported base16 string has illegal char");
        }
        return bArr;
    }
}
