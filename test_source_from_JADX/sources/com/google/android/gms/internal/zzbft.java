package com.google.android.gms.internal;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.clearcut.ClearcutLogger$zza;
import com.google.android.gms.phenotype.Phenotype;
import com.google.android.gms.phenotype.PhenotypeFlag;
import com.google.android.gms.phenotype.PhenotypeFlag$Factory;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public final class zzbft implements ClearcutLogger$zza {
    private static final Charset zza = Charset.forName("UTF-8");
    private static final PhenotypeFlag$Factory zzb = new PhenotypeFlag$Factory(Phenotype.getContentProviderUri("com.google.android.gms.clearcut.public")).withGservicePrefix("gms:playlog:service:sampling_").withPhenotypePrefix("LogSampling__");
    private static Map<String, PhenotypeFlag<String>> zzd = null;
    private static Boolean zze = null;
    private static Long zzf = null;
    private final Context zzc;

    public zzbft(Context context) {
        this.zzc = context;
        if (zzd == null) {
            zzd = new HashMap();
        }
        if (this.zzc != null) {
            PhenotypeFlag.maybeInit(this.zzc);
        }
    }

    private static zzbfu zza(String str) {
        if (str == null) {
            return null;
        }
        String str2 = "";
        int indexOf = str.indexOf(44);
        int i = 0;
        if (indexOf >= 0) {
            str2 = str.substring(0, indexOf);
            i = indexOf + 1;
        }
        String str3 = str2;
        int indexOf2 = str.indexOf(47, i);
        if (indexOf2 <= 0) {
            str2 = "LogSamplerImpl";
            String str4 = "Failed to parse the rule: ";
            str = String.valueOf(str);
            Log.e(str2, str.length() != 0 ? str4.concat(str) : new String(str4));
            return null;
        }
        try {
            long parseLong = Long.parseLong(str.substring(i, indexOf2));
            long parseLong2 = Long.parseLong(str.substring(indexOf2 + 1));
            if (parseLong >= 0) {
                if (parseLong2 >= 0) {
                    return new zzbfu(str3, parseLong, parseLong2);
                }
            }
            StringBuilder stringBuilder = new StringBuilder(72);
            stringBuilder.append("negative values not supported: ");
            stringBuilder.append(parseLong);
            stringBuilder.append("/");
            stringBuilder.append(parseLong2);
            Log.e("LogSamplerImpl", stringBuilder.toString());
            return null;
        } catch (Throwable e) {
            str4 = "LogSamplerImpl";
            String str5 = "parseLong() failed while parsing: ";
            str = String.valueOf(str);
            Log.e(str4, str.length() != 0 ? str5.concat(str) : new String(str5), e);
            return null;
        }
    }

    private static boolean zza(Context context) {
        if (zze == null) {
            zze = Boolean.valueOf(zzbih.zza(context).zza("com.google.android.providers.gsf.permission.READ_GSERVICES") == 0);
        }
        return zze.booleanValue();
    }

    public final boolean zza(String str, int i) {
        zzbft zzbft = this;
        String str2 = null;
        String valueOf = (str == null || str.isEmpty()) ? i >= 0 ? String.valueOf(i) : null : str;
        if (valueOf == null) {
            return true;
        }
        if (zzbft.zzc != null) {
            if (zza(zzbft.zzc)) {
                PhenotypeFlag phenotypeFlag = (PhenotypeFlag) zzd.get(valueOf);
                if (phenotypeFlag == null) {
                    phenotypeFlag = zzb.createFlag(valueOf, null);
                    zzd.put(valueOf, phenotypeFlag);
                }
                str2 = (String) phenotypeFlag.get();
            }
        }
        zzbfu zza = zza(str2);
        if (zza == null) {
            return true;
        }
        long j;
        byte[] bytes;
        long j2;
        long j3;
        StringBuilder stringBuilder;
        valueOf = zza.zza;
        Context context = zzbft.zzc;
        if (zzf == null) {
            if (context != null) {
                zzf = zza(context) ? Long.valueOf(zzdnm.zza(context.getContentResolver(), "android_id", 0)) : Long.valueOf(0);
            } else {
                j = 0;
                if (valueOf != null) {
                    if (valueOf.isEmpty()) {
                        bytes = valueOf.getBytes(zza);
                        ByteBuffer allocate = ByteBuffer.allocate(bytes.length + 8);
                        allocate.put(bytes);
                        allocate.putLong(j);
                        bytes = allocate.array();
                        j = zzbfo.zza(bytes);
                        j2 = zza.zzb;
                        j3 = zza.zzc;
                        if (j2 >= 0) {
                            if (j3 >= 0) {
                                if (j3 > 0) {
                                    if (j < 0) {
                                        j = ((Long.MAX_VALUE % j3) + 1) + ((j & Long.MAX_VALUE) % j3);
                                    }
                                    if (j % j3 < j2) {
                                        return true;
                                    }
                                }
                                return false;
                            }
                        }
                        stringBuilder = new StringBuilder(72);
                        stringBuilder.append("negative values not supported: ");
                        stringBuilder.append(j2);
                        stringBuilder.append("/");
                        stringBuilder.append(j3);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                }
                bytes = ByteBuffer.allocate(8).putLong(j).array();
                j = zzbfo.zza(bytes);
                j2 = zza.zzb;
                j3 = zza.zzc;
                if (j2 >= 0) {
                    if (j3 >= 0) {
                        if (j3 > 0) {
                            if (j < 0) {
                                j = ((Long.MAX_VALUE % j3) + 1) + ((j & Long.MAX_VALUE) % j3);
                            }
                            if (j % j3 < j2) {
                                return true;
                            }
                        }
                        return false;
                    }
                }
                stringBuilder = new StringBuilder(72);
                stringBuilder.append("negative values not supported: ");
                stringBuilder.append(j2);
                stringBuilder.append("/");
                stringBuilder.append(j3);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        j = zzf.longValue();
        if (valueOf != null) {
            if (valueOf.isEmpty()) {
                bytes = valueOf.getBytes(zza);
                ByteBuffer allocate2 = ByteBuffer.allocate(bytes.length + 8);
                allocate2.put(bytes);
                allocate2.putLong(j);
                bytes = allocate2.array();
                j = zzbfo.zza(bytes);
                j2 = zza.zzb;
                j3 = zza.zzc;
                if (j2 >= 0) {
                    if (j3 >= 0) {
                        if (j3 > 0) {
                            if (j < 0) {
                                j = ((Long.MAX_VALUE % j3) + 1) + ((j & Long.MAX_VALUE) % j3);
                            }
                            if (j % j3 < j2) {
                                return true;
                            }
                        }
                        return false;
                    }
                }
                stringBuilder = new StringBuilder(72);
                stringBuilder.append("negative values not supported: ");
                stringBuilder.append(j2);
                stringBuilder.append("/");
                stringBuilder.append(j3);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        bytes = ByteBuffer.allocate(8).putLong(j).array();
        j = zzbfo.zza(bytes);
        j2 = zza.zzb;
        j3 = zza.zzc;
        if (j2 >= 0) {
            if (j3 >= 0) {
                if (j3 > 0) {
                    if (j < 0) {
                        j = ((Long.MAX_VALUE % j3) + 1) + ((j & Long.MAX_VALUE) % j3);
                    }
                    if (j % j3 < j2) {
                        return true;
                    }
                }
                return false;
            }
        }
        stringBuilder = new StringBuilder(72);
        stringBuilder.append("negative values not supported: ");
        stringBuilder.append(j2);
        stringBuilder.append("/");
        stringBuilder.append(j3);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
