package com.google.android.gms.internal;

import android.support.v4.internal.view.SupportMenu;
import android.text.TextUtils;
import java.util.Locale;
import java.util.regex.Pattern;

public final class zzbdw {
    private static final Pattern zza = Pattern.compile("urn:x-cast:[-A-Za-z0-9_]+(\\.[-A-Za-z0-9_]+)*");

    public static String zza(Locale locale) {
        StringBuilder stringBuilder = new StringBuilder(20);
        stringBuilder.append(locale.getLanguage());
        Object country = locale.getCountry();
        if (!TextUtils.isEmpty(country)) {
            stringBuilder.append('-');
            stringBuilder.append(country);
        }
        Object variant = locale.getVariant();
        if (!TextUtils.isEmpty(variant)) {
            stringBuilder.append('-');
            stringBuilder.append(variant);
        }
        return stringBuilder.toString();
    }

    public static void zza(String str) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Namespace cannot be null or empty");
        } else if (str.length() > 128) {
            throw new IllegalArgumentException("Invalid namespace length");
        } else if (!str.startsWith("urn:x-cast:")) {
            throw new IllegalArgumentException("Namespace must begin with the prefix \"urn:x-cast:\"");
        } else if (str.length() == 11) {
            throw new IllegalArgumentException("Namespace must begin with the prefix \"urn:x-cast:\" and have non-empty suffix");
        }
    }

    public static <T> boolean zza(T t, T t2) {
        return (t == null && t2 == null) || !(t == null || t2 == null || !t.equals(t2));
    }

    public static String zzb(String str) {
        String valueOf = String.valueOf("urn:x-cast:");
        str = String.valueOf(str);
        return str.length() != 0 ? valueOf.concat(str) : new String(valueOf);
    }

    public static String zzc(String str) {
        if (zza.matcher(str).matches()) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder(str.length());
        int i = 0;
        while (i < str.length()) {
            Object obj;
            char charAt = str.charAt(i);
            if ((charAt < 'A' || charAt > 'Z') && ((charAt < 'a' || charAt > 'z') && ((charAt < '0' || charAt > '9') && charAt != '_'))) {
                if (charAt != '-') {
                    obj = null;
                    if (obj == null && charAt != '.') {
                        if (charAt == ':') {
                            stringBuilder.append(String.format("%%%04x", new Object[]{Integer.valueOf(charAt & SupportMenu.USER_MASK)}));
                            i++;
                        }
                    }
                    stringBuilder.append(charAt);
                    i++;
                }
            }
            obj = 1;
            if (charAt == ':') {
                stringBuilder.append(String.format("%%%04x", new Object[]{Integer.valueOf(charAt & SupportMenu.USER_MASK)}));
                i++;
            } else {
                stringBuilder.append(charAt);
                i++;
            }
        }
        return stringBuilder.toString();
    }
}
