package com.google.android.gms.auth;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public class AccountChangeEventsRequest extends zzbgl {
    public static final Creator<AccountChangeEventsRequest> CREATOR = new zzb();
    private int zza;
    private int zzb;
    @Deprecated
    private String zzc;
    private Account zzd;

    public AccountChangeEventsRequest() {
        this.zza = 1;
    }

    AccountChangeEventsRequest(int i, int i2, String str, Account account) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = str;
        if (account != null || TextUtils.isEmpty(str)) {
            this.zzd = account;
        } else {
            this.zzd = new Account(str, "com.google");
        }
    }

    public Account getAccount() {
        return this.zzd;
    }

    @Deprecated
    public String getAccountName() {
        return this.zzc;
    }

    public int getEventIndex() {
        return this.zzb;
    }

    public AccountChangeEventsRequest setAccount(Account account) {
        this.zzd = account;
        return this;
    }

    @Deprecated
    public AccountChangeEventsRequest setAccountName(String str) {
        this.zzc = str;
        return this;
    }

    public AccountChangeEventsRequest setEventIndex(int i) {
        this.zzb = i;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb);
        zzbgo.zza(parcel, 3, this.zzc, false);
        zzbgo.zza(parcel, 4, this.zzd, i, false);
        zzbgo.zza(parcel, zza);
    }
}
