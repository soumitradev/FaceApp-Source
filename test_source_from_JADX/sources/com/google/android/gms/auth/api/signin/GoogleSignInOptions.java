package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.zzo;
import com.google.android.gms.auth.api.signin.internal.zzq;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api$ApiOptions$Optional;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInOptions extends zzbgl implements Api$ApiOptions$Optional, ReflectedParcelable {
    public static final Creator<GoogleSignInOptions> CREATOR = new zze();
    public static final GoogleSignInOptions DEFAULT_GAMES_SIGN_IN = new Builder().requestScopes(zzd, new Scope[0]).build();
    public static final GoogleSignInOptions DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
    @Hide
    public static final Scope zza = new Scope(Scopes.PROFILE);
    @Hide
    public static final Scope zzb = new Scope("email");
    @Hide
    public static final Scope zzc = new Scope("openid");
    @Hide
    public static final Scope zzd = new Scope("https://www.googleapis.com/auth/games_lite");
    @Hide
    public static final Scope zze = new Scope(Scopes.GAMES);
    private static Comparator<Scope> zzp = new zzd();
    private int zzf;
    private final ArrayList<Scope> zzg;
    private Account zzh;
    private boolean zzi;
    private final boolean zzj;
    private final boolean zzk;
    private String zzl;
    private String zzm;
    private ArrayList<zzo> zzn;
    private Map<Integer, zzo> zzo;

    public static final class Builder {
        private Set<Scope> zza = new HashSet();
        private boolean zzb;
        private boolean zzc;
        private boolean zzd;
        private String zze;
        private Account zzf;
        private String zzg;
        private Map<Integer, zzo> zzh = new HashMap();

        public Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
            zzbq.zza(googleSignInOptions);
            this.zza = new HashSet(googleSignInOptions.zzg);
            this.zzb = googleSignInOptions.zzj;
            this.zzc = googleSignInOptions.zzk;
            this.zzd = googleSignInOptions.zzi;
            this.zze = googleSignInOptions.zzl;
            this.zzf = googleSignInOptions.zzh;
            this.zzg = googleSignInOptions.zzm;
            this.zzh = GoogleSignInOptions.zzb(googleSignInOptions.zzn);
        }

        private final String zza(String str) {
            boolean z;
            zzbq.zza(str);
            if (this.zze != null) {
                if (!this.zze.equals(str)) {
                    z = false;
                    zzbq.zzb(z, "two different server client ids provided");
                    return str;
                }
            }
            z = true;
            zzbq.zzb(z, "two different server client ids provided");
            return str;
        }

        public final Builder addExtension(GoogleSignInOptionsExtension googleSignInOptionsExtension) {
            if (this.zzh.containsKey(Integer.valueOf(googleSignInOptionsExtension.getExtensionType()))) {
                throw new IllegalStateException("Only one extension per type may be added");
            }
            if (googleSignInOptionsExtension.getImpliedScopes() != null) {
                this.zza.addAll(googleSignInOptionsExtension.getImpliedScopes());
            }
            this.zzh.put(Integer.valueOf(googleSignInOptionsExtension.getExtensionType()), new zzo(googleSignInOptionsExtension));
            return this;
        }

        public final GoogleSignInOptions build() {
            if (this.zza.contains(GoogleSignInOptions.zze) && this.zza.contains(GoogleSignInOptions.zzd)) {
                this.zza.remove(GoogleSignInOptions.zzd);
            }
            if (this.zzd && (this.zzf == null || !this.zza.isEmpty())) {
                requestId();
            }
            return new GoogleSignInOptions(new ArrayList(this.zza), this.zzf, this.zzd, this.zzb, this.zzc, this.zze, this.zzg, this.zzh);
        }

        public final Builder requestEmail() {
            this.zza.add(GoogleSignInOptions.zzb);
            return this;
        }

        public final Builder requestId() {
            this.zza.add(GoogleSignInOptions.zzc);
            return this;
        }

        public final Builder requestIdToken(String str) {
            this.zzd = true;
            this.zze = zza(str);
            return this;
        }

        public final Builder requestProfile() {
            this.zza.add(GoogleSignInOptions.zza);
            return this;
        }

        public final Builder requestScopes(Scope scope, Scope... scopeArr) {
            this.zza.add(scope);
            this.zza.addAll(Arrays.asList(scopeArr));
            return this;
        }

        public final Builder requestServerAuthCode(String str) {
            return requestServerAuthCode(str, false);
        }

        public final Builder requestServerAuthCode(String str, boolean z) {
            this.zzb = true;
            this.zze = zza(str);
            this.zzc = z;
            return this;
        }

        public final Builder setAccountName(String str) {
            this.zzf = new Account(zzbq.zza(str), "com.google");
            return this;
        }

        public final Builder setHostedDomain(String str) {
            this.zzg = zzbq.zza(str);
            return this;
        }
    }

    GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, ArrayList<zzo> arrayList2) {
        this(i, (ArrayList) arrayList, account, z, z2, z3, str, str2, zzb((List) arrayList2));
    }

    private GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2, Map<Integer, zzo> map) {
        this.zzf = i;
        this.zzg = arrayList;
        this.zzh = account;
        this.zzi = z;
        this.zzj = z2;
        this.zzk = z3;
        this.zzl = str;
        this.zzm = str2;
        this.zzn = new ArrayList(map.values());
        this.zzo = map;
    }

    @Nullable
    @Hide
    public static GoogleSignInOptions zza(@Nullable String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        Collection hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("scopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        Object optString = jSONObject.optString("accountName", null);
        return new GoogleSignInOptions(3, new ArrayList(hashSet), !TextUtils.isEmpty(optString) ? new Account(optString, "com.google") : null, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", null), jSONObject.optString("hostedDomain", null), new HashMap());
    }

    private static Map<Integer, zzo> zzb(@Nullable List<zzo> list) {
        Map<Integer, zzo> hashMap = new HashMap();
        if (list == null) {
            return hashMap;
        }
        for (zzo zzo : list) {
            hashMap.put(Integer.valueOf(zzo.zza()), zzo);
        }
        return hashMap;
    }

    private final JSONObject zzg() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zzg, zzp);
            ArrayList arrayList = this.zzg;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                jSONArray.put(((Scope) obj).zza());
            }
            jSONObject.put("scopes", jSONArray);
            if (this.zzh != null) {
                jSONObject.put("accountName", this.zzh.name);
            }
            jSONObject.put("idTokenRequested", this.zzi);
            jSONObject.put("forceCodeForRefreshToken", this.zzk);
            jSONObject.put("serverAuthRequested", this.zzj);
            if (!TextUtils.isEmpty(this.zzl)) {
                jSONObject.put("serverClientId", this.zzl);
            }
            if (!TextUtils.isEmpty(this.zzm)) {
                jSONObject.put("hostedDomain", this.zzm);
            }
            return jSONObject;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r4) {
        /*
        r3 = this;
        r0 = 0;
        if (r4 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r4 = (com.google.android.gms.auth.api.signin.GoogleSignInOptions) r4;	 Catch:{ ClassCastException -> 0x0077 }
        r1 = r3.zzn;	 Catch:{ ClassCastException -> 0x0077 }
        r1 = r1.size();	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 > 0) goto L_0x0076;
    L_0x000e:
        r1 = r4.zzn;	 Catch:{ ClassCastException -> 0x0077 }
        r1 = r1.size();	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 <= 0) goto L_0x0017;
    L_0x0016:
        return r0;
    L_0x0017:
        r1 = r3.zzg;	 Catch:{ ClassCastException -> 0x0077 }
        r1 = r1.size();	 Catch:{ ClassCastException -> 0x0077 }
        r2 = r4.zza();	 Catch:{ ClassCastException -> 0x0077 }
        r2 = r2.size();	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 != r2) goto L_0x0076;
    L_0x0027:
        r1 = r3.zzg;	 Catch:{ ClassCastException -> 0x0077 }
        r2 = r4.zza();	 Catch:{ ClassCastException -> 0x0077 }
        r1 = r1.containsAll(r2);	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 != 0) goto L_0x0034;
    L_0x0033:
        return r0;
    L_0x0034:
        r1 = r3.zzh;	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 != 0) goto L_0x003d;
    L_0x0038:
        r1 = r4.zzh;	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 != 0) goto L_0x0076;
    L_0x003c:
        goto L_0x0047;
    L_0x003d:
        r1 = r3.zzh;	 Catch:{ ClassCastException -> 0x0077 }
        r2 = r4.zzh;	 Catch:{ ClassCastException -> 0x0077 }
        r1 = r1.equals(r2);	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 == 0) goto L_0x0076;
    L_0x0047:
        r1 = r3.zzl;	 Catch:{ ClassCastException -> 0x0077 }
        r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 == 0) goto L_0x0058;
    L_0x004f:
        r1 = r4.zzl;	 Catch:{ ClassCastException -> 0x0077 }
        r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 == 0) goto L_0x0076;
    L_0x0057:
        goto L_0x0062;
    L_0x0058:
        r1 = r3.zzl;	 Catch:{ ClassCastException -> 0x0077 }
        r2 = r4.zzl;	 Catch:{ ClassCastException -> 0x0077 }
        r1 = r1.equals(r2);	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 == 0) goto L_0x0076;
    L_0x0062:
        r1 = r3.zzk;	 Catch:{ ClassCastException -> 0x0077 }
        r2 = r4.zzk;	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 != r2) goto L_0x0076;
    L_0x0068:
        r1 = r3.zzi;	 Catch:{ ClassCastException -> 0x0077 }
        r2 = r4.zzi;	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 != r2) goto L_0x0076;
    L_0x006e:
        r1 = r3.zzj;	 Catch:{ ClassCastException -> 0x0077 }
        r4 = r4.zzj;	 Catch:{ ClassCastException -> 0x0077 }
        if (r1 != r4) goto L_0x0076;
    L_0x0074:
        r4 = 1;
        return r4;
    L_0x0076:
        return r0;
    L_0x0077:
        r4 = move-exception;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.GoogleSignInOptions.equals(java.lang.Object):boolean");
    }

    public Scope[] getScopeArray() {
        return (Scope[]) this.zzg.toArray(new Scope[this.zzg.size()]);
    }

    public int hashCode() {
        Object arrayList = new ArrayList();
        ArrayList arrayList2 = this.zzg;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            arrayList.add(((Scope) obj).zza());
        }
        Collections.sort(arrayList);
        return new zzq().zza(arrayList).zza(this.zzh).zza(this.zzl).zza(this.zzk).zza(this.zzi).zza(this.zzj).zza();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zzf);
        zzbgo.zzc(parcel, 2, zza(), false);
        zzbgo.zza(parcel, 3, this.zzh, i, false);
        zzbgo.zza(parcel, 4, this.zzi);
        zzbgo.zza(parcel, 5, this.zzj);
        zzbgo.zza(parcel, 6, this.zzk);
        zzbgo.zza(parcel, 7, this.zzl, false);
        zzbgo.zza(parcel, 8, this.zzm, false);
        zzbgo.zzc(parcel, 9, this.zzn, false);
        zzbgo.zza(parcel, zza);
    }

    @Hide
    public final ArrayList<Scope> zza() {
        return new ArrayList(this.zzg);
    }

    @Hide
    public final Account zzb() {
        return this.zzh;
    }

    @Hide
    public final boolean zzc() {
        return this.zzi;
    }

    @Hide
    public final boolean zzd() {
        return this.zzj;
    }

    @Hide
    public final String zze() {
        return this.zzl;
    }

    @Hide
    public final String zzf() {
        return zzg().toString();
    }
}
