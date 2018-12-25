package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzccn;

@Hide
@DynamiteApi
public class FlagProviderImpl extends zzccn {
    private boolean zza = false;
    private SharedPreferences zzb;

    public boolean getBooleanFlagValue(String str, boolean z, int i) {
        return !this.zza ? z : zzb.zza(this.zzb, str, Boolean.valueOf(z)).booleanValue();
    }

    public int getIntFlagValue(String str, int i, int i2) {
        return !this.zza ? i : zzd.zza(this.zzb, str, Integer.valueOf(i)).intValue();
    }

    public long getLongFlagValue(String str, long j, int i) {
        return !this.zza ? j : zzf.zza(this.zzb, str, Long.valueOf(j)).longValue();
    }

    public String getStringFlagValue(String str, String str2, int i) {
        return !this.zza ? str2 : zzh.zza(this.zzb, str, str2);
    }

    public void init(IObjectWrapper iObjectWrapper) {
        Context context = (Context) zzn.zza(iObjectWrapper);
        if (!this.zza) {
            try {
                this.zzb = zzj.zza(context.createPackageContext("com.google.android.gms", 0));
                this.zza = true;
            } catch (NameNotFoundException e) {
            } catch (Exception e2) {
                String str = "FlagProviderImpl";
                String str2 = "Could not retrieve sdk flags, continuing with defaults: ";
                String valueOf = String.valueOf(e2.getMessage());
                Log.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
        }
    }
}
