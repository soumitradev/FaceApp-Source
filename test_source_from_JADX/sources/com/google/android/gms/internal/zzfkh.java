package com.google.android.gms.internal;

final class zzfkh {
    static String zza(zzfgs zzfgs) {
        zzfkj zzfki = new zzfki(zzfgs);
        StringBuilder stringBuilder = new StringBuilder(zzfki.zza());
        for (int i = 0; i < zzfki.zza(); i++) {
            String str;
            int zza = zzfki.zza(i);
            if (zza == 34) {
                str = "\\\"";
            } else if (zza == 39) {
                str = "\\'";
            } else if (zza != 92) {
                switch (zza) {
                    case 7:
                        str = "\\a";
                        break;
                    case 8:
                        str = "\\b";
                        break;
                    case 9:
                        str = "\\t";
                        break;
                    case 10:
                        str = "\\n";
                        break;
                    case 11:
                        str = "\\v";
                        break;
                    case 12:
                        str = "\\f";
                        break;
                    case 13:
                        str = "\\r";
                        break;
                    default:
                        if (zza < 32 || zza > 126) {
                            stringBuilder.append('\\');
                            stringBuilder.append((char) (((zza >>> 6) & 3) + 48));
                            stringBuilder.append((char) (((zza >>> 3) & 7) + 48));
                            zza = (zza & 7) + 48;
                        }
                        stringBuilder.append((char) zza);
                        continue;
                }
            } else {
                str = "\\\\";
            }
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }
}
