package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInAccount extends zzbgl implements ReflectedParcelable {
    public static final Creator<GoogleSignInAccount> CREATOR = new zzb();
    @Hide
    private static zze zza = zzi.zzd();
    @Hide
    private int zzb;
    private String zzc;
    private String zzd;
    private String zze;
    private String zzf;
    private Uri zzg;
    private String zzh;
    private long zzi;
    private String zzj;
    private List<Scope> zzk;
    private String zzl;
    private String zzm;
    private Set<Scope> zzn = new HashSet();

    GoogleSignInAccount(int i, String str, String str2, String str3, String str4, Uri uri, String str5, long j, String str6, List<Scope> list, String str7, String str8) {
        this.zzb = i;
        this.zzc = str;
        this.zzd = str2;
        this.zze = str3;
        this.zzf = str4;
        this.zzg = uri;
        this.zzh = str5;
        this.zzi = j;
        this.zzj = str6;
        this.zzk = list;
        this.zzl = str7;
        this.zzm = str8;
    }

    @Hide
    public static GoogleSignInAccount zza() {
        Account account = new Account("<<default account>>", "com.google");
        return zza(null, null, account.name, null, null, null, null, Long.valueOf(0), account.name, new HashSet());
    }

    @Nullable
    @Hide
    public static GoogleSignInAccount zza(@Nullable String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        Object optString = jSONObject.optString("photoUrl", null);
        Uri parse = !TextUtils.isEmpty(optString) ? Uri.parse(optString) : null;
        long parseLong = Long.parseLong(jSONObject.getString("expirationTime"));
        Set hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("grantedScopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        GoogleSignInAccount zza = zza(jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID), jSONObject.optString("tokenId", null), jSONObject.optString("email", null), jSONObject.optString("displayName", null), jSONObject.optString("givenName", null), jSONObject.optString("familyName", null), parse, Long.valueOf(parseLong), jSONObject.getString("obfuscatedIdentifier"), hashSet);
        zza.zzh = jSONObject.optString("serverAuthCode", null);
        return zza;
    }

    @Hide
    private static GoogleSignInAccount zza(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable Uri uri, @Nullable Long l, @NonNull String str7, @NonNull Set<Scope> set) {
        return new GoogleSignInAccount(3, str, str2, str3, str4, uri, null, (l == null ? Long.valueOf(zza.zza() / 1000) : l).longValue(), zzbq.zza(str7), new ArrayList((Collection) zzbq.zza(set)), str5, str6);
    }

    private final JSONObject zzf() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (getId() != null) {
                jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ID, getId());
            }
            if (getIdToken() != null) {
                jSONObject.put("tokenId", getIdToken());
            }
            if (getEmail() != null) {
                jSONObject.put("email", getEmail());
            }
            if (getDisplayName() != null) {
                jSONObject.put("displayName", getDisplayName());
            }
            if (getGivenName() != null) {
                jSONObject.put("givenName", getGivenName());
            }
            if (getFamilyName() != null) {
                jSONObject.put("familyName", getFamilyName());
            }
            if (getPhotoUrl() != null) {
                jSONObject.put("photoUrl", getPhotoUrl().toString());
            }
            if (getServerAuthCode() != null) {
                jSONObject.put("serverAuthCode", getServerAuthCode());
            }
            jSONObject.put("expirationTime", this.zzi);
            jSONObject.put("obfuscatedIdentifier", this.zzj);
            JSONArray jSONArray = new JSONArray();
            Scope[] scopeArr = (Scope[]) this.zzk.toArray(new Scope[this.zzk.size()]);
            Arrays.sort(scopeArr, zza.zza);
            for (Scope zza : scopeArr) {
                jSONArray.put(zza.zza());
            }
            jSONObject.put("grantedScopes", jSONArray);
            return jSONObject;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GoogleSignInAccount)) {
            return false;
        }
        GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) obj;
        return googleSignInAccount.zzj.equals(this.zzj) && googleSignInAccount.zzd().equals(zzd());
    }

    @Nullable
    public Account getAccount() {
        return this.zze == null ? null : new Account(this.zze, "com.google");
    }

    @Nullable
    public String getDisplayName() {
        return this.zzf;
    }

    @Nullable
    public String getEmail() {
        return this.zze;
    }

    @Nullable
    public String getFamilyName() {
        return this.zzm;
    }

    @Nullable
    public String getGivenName() {
        return this.zzl;
    }

    @NonNull
    public Set<Scope> getGrantedScopes() {
        return new HashSet(this.zzk);
    }

    @Nullable
    public String getId() {
        return this.zzc;
    }

    @Nullable
    public String getIdToken() {
        return this.zzd;
    }

    @Nullable
    public Uri getPhotoUrl() {
        return this.zzg;
    }

    @Nullable
    public String getServerAuthCode() {
        return this.zzh;
    }

    public int hashCode() {
        return ((this.zzj.hashCode() + 527) * 31) + zzd().hashCode();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zzb);
        zzbgo.zza(parcel, 2, getId(), false);
        zzbgo.zza(parcel, 3, getIdToken(), false);
        zzbgo.zza(parcel, 4, getEmail(), false);
        zzbgo.zza(parcel, 5, getDisplayName(), false);
        zzbgo.zza(parcel, 6, getPhotoUrl(), i, false);
        zzbgo.zza(parcel, 7, getServerAuthCode(), false);
        zzbgo.zza(parcel, 8, this.zzi);
        zzbgo.zza(parcel, 9, this.zzj, false);
        zzbgo.zzc(parcel, 10, this.zzk, false);
        zzbgo.zza(parcel, 11, getGivenName(), false);
        zzbgo.zza(parcel, 12, getFamilyName(), false);
        zzbgo.zza(parcel, zza);
    }

    @Hide
    public final GoogleSignInAccount zza(Scope... scopeArr) {
        if (scopeArr != null) {
            Collections.addAll(this.zzn, scopeArr);
        }
        return this;
    }

    @Hide
    public final boolean zzb() {
        return zza.zza() / 1000 >= this.zzi - 300;
    }

    @Hide
    @NonNull
    public final String zzc() {
        return this.zzj;
    }

    @Hide
    @NonNull
    public final Set<Scope> zzd() {
        Set<Scope> hashSet = new HashSet(this.zzk);
        hashSet.addAll(this.zzn);
        return hashSet;
    }

    @Hide
    public final String zze() {
        JSONObject zzf = zzf();
        zzf.remove("serverAuthCode");
        return zzf.toString();
    }
}
