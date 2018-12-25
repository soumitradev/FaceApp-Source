package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.Cast$CastApi;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerClient.GameManagerInstanceResult;
import com.google.android.gms.cast.games.GameManagerClient.GameManagerResult;
import com.google.android.gms.cast.games.GameManagerClient.Listener;
import com.google.android.gms.cast.games.GameManagerState;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzbcl extends zzbdg {
    private static String zzd = zzbdw.zzb("com.google.cast.games");
    private static final zzbei zze = new zzbei("GameManagerChannel");
    private final Map<String, String> zzf = new ConcurrentHashMap();
    private final List<zzbeo> zzg;
    private final SharedPreferences zzh;
    private final String zzi;
    private final Cast$CastApi zzj;
    private final GoogleApiClient zzk;
    private zzbcy zzl;
    private boolean zzm = false;
    private GameManagerState zzn;
    private GameManagerState zzo;
    private String zzp;
    private JSONObject zzq;
    private long zzr = 0;
    private Listener zzs;
    private final zze zzt;
    private String zzu;

    public zzbcl(GoogleApiClient googleApiClient, String str, Cast$CastApi cast$CastApi) throws IllegalArgumentException, IllegalStateException {
        super(zzd, "CastGameManagerChannel", null);
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("castSessionId cannot be null.");
        }
        if (googleApiClient != null && googleApiClient.isConnected()) {
            if (googleApiClient.hasConnectedApi(Cast.API)) {
                this.zzt = zzi.zzd();
                this.zzg = new ArrayList();
                this.zzi = str;
                this.zzj = cast$CastApi;
                this.zzk = googleApiClient;
                this.zzh = r12.getApplicationContext().getSharedPreferences(String.format(Locale.ROOT, "%s.%s", new Object[]{googleApiClient.zzb().getApplicationContext().getPackageName(), "game_manager_channel_data"}), 0);
                this.zzo = null;
                this.zzn = new zzbda(0, 0, "", null, new ArrayList(), "", -1);
                return;
            }
        }
        throw new IllegalArgumentException("googleApiClient needs to be connected and contain the Cast.API API.");
    }

    private final JSONObject zza(long j, String str, int i, JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("requestId", j);
            jSONObject2.put("type", i);
            jSONObject2.put("extraMessageData", jSONObject);
            jSONObject2.put("playerId", str);
            jSONObject2.put("playerToken", zzc(str));
            return jSONObject2;
        } catch (JSONException e) {
            zze.zzc("JSONException when trying to create a message: %s", new Object[]{e.getMessage()});
            return null;
        }
    }

    private final void zza(long j, int i, Object obj) {
        Iterator it = this.zzg.iterator();
        while (it.hasNext()) {
            if (((zzbeo) it.next()).zza(j, i, obj)) {
                it.remove();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final synchronized void zza(com.google.android.gms.internal.zzbcz r10) {
        /*
        r9 = this;
        monitor-enter(r9);
        r0 = r10.zza;	 Catch:{ all -> 0x008b }
        r1 = 1;
        if (r0 != r1) goto L_0x0007;
    L_0x0006:
        goto L_0x0008;
    L_0x0007:
        r1 = 0;
    L_0x0008:
        r0 = r9.zzn;	 Catch:{ all -> 0x008b }
        r9.zzo = r0;	 Catch:{ all -> 0x008b }
        if (r1 == 0) goto L_0x0016;
    L_0x000e:
        r0 = r10.zzm;	 Catch:{ all -> 0x008b }
        if (r0 == 0) goto L_0x0016;
    L_0x0012:
        r0 = r10.zzm;	 Catch:{ all -> 0x008b }
        r9.zzl = r0;	 Catch:{ all -> 0x008b }
    L_0x0016:
        r0 = r9.zzi();	 Catch:{ all -> 0x008b }
        if (r0 != 0) goto L_0x001e;
    L_0x001c:
        monitor-exit(r9);
        return;
    L_0x001e:
        r6 = new java.util.ArrayList;	 Catch:{ all -> 0x008b }
        r6.<init>();	 Catch:{ all -> 0x008b }
        r0 = r10.zzg;	 Catch:{ all -> 0x008b }
        r0 = r0.iterator();	 Catch:{ all -> 0x008b }
    L_0x0029:
        r1 = r0.hasNext();	 Catch:{ all -> 0x008b }
        if (r1 == 0) goto L_0x0050;
    L_0x002f:
        r1 = r0.next();	 Catch:{ all -> 0x008b }
        r1 = (com.google.android.gms.internal.zzbdc) r1;	 Catch:{ all -> 0x008b }
        r2 = r1.zzc();	 Catch:{ all -> 0x008b }
        r3 = new com.google.android.gms.internal.zzbdb;	 Catch:{ all -> 0x008b }
        r4 = r1.zza();	 Catch:{ all -> 0x008b }
        r1 = r1.zzb();	 Catch:{ all -> 0x008b }
        r5 = r9.zzf;	 Catch:{ all -> 0x008b }
        r5 = r5.containsKey(r2);	 Catch:{ all -> 0x008b }
        r3.<init>(r2, r4, r1, r5);	 Catch:{ all -> 0x008b }
        r6.add(r3);	 Catch:{ all -> 0x008b }
        goto L_0x0029;
    L_0x0050:
        r0 = new com.google.android.gms.internal.zzbda;	 Catch:{ all -> 0x008b }
        r2 = r10.zzf;	 Catch:{ all -> 0x008b }
        r3 = r10.zze;	 Catch:{ all -> 0x008b }
        r4 = r10.zzi;	 Catch:{ all -> 0x008b }
        r5 = r10.zzh;	 Catch:{ all -> 0x008b }
        r1 = r9.zzl;	 Catch:{ all -> 0x008b }
        r7 = r1.zza();	 Catch:{ all -> 0x008b }
        r1 = r9.zzl;	 Catch:{ all -> 0x008b }
        r8 = r1.zzb();	 Catch:{ all -> 0x008b }
        r1 = r0;
        r1.<init>(r2, r3, r4, r5, r6, r7, r8);	 Catch:{ all -> 0x008b }
        r9.zzn = r0;	 Catch:{ all -> 0x008b }
        r0 = r9.zzn;	 Catch:{ all -> 0x008b }
        r1 = r10.zzj;	 Catch:{ all -> 0x008b }
        r0 = r0.getPlayer(r1);	 Catch:{ all -> 0x008b }
        if (r0 == 0) goto L_0x0089;
    L_0x0076:
        r0 = r0.isControllable();	 Catch:{ all -> 0x008b }
        if (r0 == 0) goto L_0x0089;
    L_0x007c:
        r0 = r10.zza;	 Catch:{ all -> 0x008b }
        r1 = 2;
        if (r0 != r1) goto L_0x0089;
    L_0x0081:
        r0 = r10.zzj;	 Catch:{ all -> 0x008b }
        r9.zzp = r0;	 Catch:{ all -> 0x008b }
        r10 = r10.zzd;	 Catch:{ all -> 0x008b }
        r9.zzq = r10;	 Catch:{ all -> 0x008b }
    L_0x0089:
        monitor-exit(r9);
        return;
    L_0x008b:
        r10 = move-exception;
        monitor-exit(r9);
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbcl.zza(com.google.android.gms.internal.zzbcz):void");
    }

    private final void zza(String str, int i, JSONObject jSONObject, zzben zzben) {
        long j = this.zzr + 1;
        this.zzr = j;
        JSONObject zza = zza(j, str, i, jSONObject);
        if (zza == null) {
            zzben.zza(-1, CastStatusCodes.INVALID_REQUEST, null);
            zze.zzc("Not sending request because it was invalid.", new Object[0]);
            return;
        }
        zzbeo zzbeo = new zzbeo(this.zzt, 30000);
        zzbeo.zza(j, zzben);
        this.zzg.add(zzbeo);
        zza(true);
        this.zzj.sendMessage(this.zzk, zzg(), zza.toString()).setResultCallback(new zzbcq(this, j));
    }

    private final synchronized String zzc(String str) throws IllegalStateException {
        if (str == null) {
            return null;
        }
        return (String) this.zzf.get(str);
    }

    private final synchronized boolean zzi() {
        return this.zzl != null;
    }

    private final synchronized void zzj() throws IllegalStateException {
        if (!zzi()) {
            throw new IllegalStateException("Attempted to perform an operation on the GameManagerChannel before it is initialized.");
        } else if (zzd()) {
            throw new IllegalStateException("Attempted to perform an operation on the GameManagerChannel after it has been disposed.");
        }
    }

    private final synchronized void zzk() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("castSessionId", this.zzi);
            jSONObject.put("playerTokenMap", new JSONObject(this.zzf));
            this.zzh.edit().putString("save_data", jSONObject.toString()).commit();
        } catch (JSONException e) {
            zze.zzc("Error while saving data: %s", new Object[]{e.getMessage()});
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final synchronized void zzl() {
        /*
        r5 = this;
        monitor-enter(r5);
        r0 = r5.zzh;	 Catch:{ all -> 0x005b }
        r1 = "save_data";
        r2 = 0;
        r0 = r0.getString(r1, r2);	 Catch:{ all -> 0x005b }
        if (r0 != 0) goto L_0x000e;
    L_0x000c:
        monitor-exit(r5);
        return;
    L_0x000e:
        r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0047 }
        r1.<init>(r0);	 Catch:{ JSONException -> 0x0047 }
        r0 = "castSessionId";
        r0 = r1.getString(r0);	 Catch:{ JSONException -> 0x0047 }
        r2 = r5.zzi;	 Catch:{ JSONException -> 0x0047 }
        r0 = r2.equals(r0);	 Catch:{ JSONException -> 0x0047 }
        if (r0 == 0) goto L_0x0045;
    L_0x0021:
        r0 = "playerTokenMap";
        r0 = r1.getJSONObject(r0);	 Catch:{ JSONException -> 0x0047 }
        r1 = r0.keys();	 Catch:{ JSONException -> 0x0047 }
    L_0x002b:
        r2 = r1.hasNext();	 Catch:{ JSONException -> 0x0047 }
        if (r2 == 0) goto L_0x0041;
    L_0x0031:
        r2 = r1.next();	 Catch:{ JSONException -> 0x0047 }
        r2 = (java.lang.String) r2;	 Catch:{ JSONException -> 0x0047 }
        r3 = r5.zzf;	 Catch:{ JSONException -> 0x0047 }
        r4 = r0.getString(r2);	 Catch:{ JSONException -> 0x0047 }
        r3.put(r2, r4);	 Catch:{ JSONException -> 0x0047 }
        goto L_0x002b;
    L_0x0041:
        r0 = 0;
        r5.zzr = r0;	 Catch:{ JSONException -> 0x0047 }
    L_0x0045:
        monitor-exit(r5);
        return;
    L_0x0047:
        r0 = move-exception;
        r1 = zze;	 Catch:{ all -> 0x005b }
        r2 = "Error while loading data: %s";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x005b }
        r4 = 0;
        r0 = r0.getMessage();	 Catch:{ all -> 0x005b }
        r3[r4] = r0;	 Catch:{ all -> 0x005b }
        r1.zzc(r2, r3);	 Catch:{ all -> 0x005b }
        monitor-exit(r5);
        return;
    L_0x005b:
        r0 = move-exception;
        monitor-exit(r5);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbcl.zzl():void");
    }

    public final synchronized PendingResult<GameManagerInstanceResult> zza(GameManagerClient gameManagerClient) throws IllegalArgumentException {
        return this.zzk.zzb(new zzbcm(this, gameManagerClient));
    }

    public final synchronized PendingResult<GameManagerResult> zza(String str, int i, JSONObject jSONObject) throws IllegalStateException {
        zzj();
        return this.zzk.zzb(new zzbco(this, i, str, jSONObject));
    }

    public final synchronized void zza() throws IllegalStateException {
        if (!this.zzm) {
            this.zzn = null;
            this.zzo = null;
            this.zzp = null;
            this.zzq = null;
            this.zzm = true;
            try {
                this.zzj.removeMessageReceivedCallbacks(this.zzk, zzg());
            } catch (IOException e) {
                zze.zzc("Exception while detaching game manager channel.", new Object[]{e});
            }
        }
    }

    public final void zza(long j, int i) {
        zza(j, i, null);
    }

    public final synchronized void zza(Listener listener) {
        this.zzs = listener;
    }

    public final void zza(String str) {
        Object[] objArr = new Object[1];
        int i = 0;
        objArr[0] = str;
        zze.zza("message received: %s", objArr);
        try {
            Object zza = zzbcz.zza(new JSONObject(str));
            if (zza == null) {
                zze.zzc("Could not parse game manager message from string: %s", new Object[]{str});
            } else if ((zzi() || zza.zzm != null) && !zzd()) {
                Object obj = zza.zza == 1 ? 1 : null;
                if (!(obj == null || TextUtils.isEmpty(zza.zzl))) {
                    this.zzf.put(zza.zzj, zza.zzl);
                    zzk();
                }
                if (zza.zzb == 0) {
                    zza((zzbcz) zza);
                } else {
                    zze.zzc("Not updating from game message because the message contains error code: %d", new Object[]{Integer.valueOf(zza.zzb)});
                }
                int i2 = zza.zzb;
                switch (i2) {
                    case 0:
                        break;
                    case 1:
                        i = CastStatusCodes.INVALID_REQUEST;
                        break;
                    case 2:
                        i = CastStatusCodes.NOT_ALLOWED;
                        break;
                    case 3:
                        i = GameManagerClient.STATUS_INCORRECT_VERSION;
                        break;
                    case 4:
                        i = GameManagerClient.STATUS_TOO_MANY_PLAYERS;
                        break;
                    default:
                        zzbei zzbei = zze;
                        StringBuilder stringBuilder = new StringBuilder(53);
                        stringBuilder.append("Unknown GameManager protocol status code: ");
                        stringBuilder.append(i2);
                        zzbei.zzc(stringBuilder.toString(), new Object[0]);
                        i = 13;
                        break;
                }
                if (obj != null) {
                    zza(zza.zzk, i, zza);
                }
                if (zzi() && i == 0) {
                    if (this.zzs != null) {
                        if (!(this.zzo == null || this.zzn.equals(this.zzo))) {
                            this.zzs.onStateChanged(this.zzn, this.zzo);
                        }
                        if (!(this.zzq == null || this.zzp == null)) {
                            this.zzs.onGameMessageReceived(this.zzp, this.zzq);
                        }
                    }
                    this.zzo = null;
                    this.zzp = null;
                    this.zzq = null;
                }
            }
        } catch (JSONException e) {
            zze.zzc("Message is malformed (%s); ignoring: %s", new Object[]{e.getMessage(), str});
        }
    }

    public final synchronized void zza(String str, JSONObject jSONObject) throws IllegalStateException {
        zzj();
        long j = this.zzr + 1;
        this.zzr = j;
        JSONObject zza = zza(j, str, 7, jSONObject);
        if (zza != null) {
            this.zzj.sendMessage(this.zzk, zzg(), zza.toString());
        }
    }

    protected final boolean zza(long j) {
        Iterator it = this.zzg.iterator();
        while (it.hasNext()) {
            if (((zzbeo) it.next()).zza(j, 15)) {
                it.remove();
            }
        }
        boolean z = false;
        synchronized (zzbeo.zza) {
            for (zzbeo zzb : this.zzg) {
                if (zzb.zzb()) {
                    z = true;
                    break;
                }
            }
        }
        return z;
    }

    public final synchronized GameManagerState zzb() throws IllegalStateException {
        zzj();
        return this.zzn;
    }

    public final synchronized PendingResult<GameManagerResult> zzb(String str, JSONObject jSONObject) throws IllegalStateException {
        zzj();
        return this.zzk.zzb(new zzbcp(this, str, jSONObject));
    }

    public final synchronized String zzc() throws IllegalStateException {
        zzj();
        return this.zzu;
    }

    public final synchronized boolean zzd() {
        return this.zzm;
    }
}
