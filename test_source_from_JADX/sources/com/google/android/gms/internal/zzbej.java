package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLoadOptions;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.cast.zzbl;
import com.google.android.gms.common.util.zze;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.catrobat.catroid.common.BrickValues;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzbej extends zzbdg {
    public static final String zzd = zzbdw.zzb("com.google.cast.media");
    private long zze;
    private MediaStatus zzf;
    private final List<zzbeo> zzg = new ArrayList();
    private zzbek zzh;
    private final zzbeo zzi = new zzbeo(this.zza, 86400000);
    private final zzbeo zzj = new zzbeo(this.zza, 86400000);
    private final zzbeo zzk = new zzbeo(this.zza, 86400000);
    private final zzbeo zzl = new zzbeo(this.zza, 86400000);
    private final zzbeo zzm = new zzbeo(this.zza, 86400000);
    private final zzbeo zzn = new zzbeo(this.zza, 86400000);
    private final zzbeo zzo = new zzbeo(this.zza, 86400000);
    private final zzbeo zzp = new zzbeo(this.zza, 86400000);
    private final zzbeo zzq = new zzbeo(this.zza, 86400000);
    private final zzbeo zzr = new zzbeo(this.zza, 86400000);
    private final zzbeo zzs = new zzbeo(this.zza, 86400000);
    private final zzbeo zzt = new zzbeo(this.zza, 86400000);
    private final zzbeo zzu = new zzbeo(this.zza, 86400000);
    private final zzbeo zzv = new zzbeo(this.zza, 86400000);
    private final zzbeo zzw = new zzbeo(this.zza, 86400000);

    public zzbej(String str, zze zze) {
        super(zzd, zze, "MediaControlChannel", null, 1000);
        this.zzg.add(this.zzi);
        this.zzg.add(this.zzj);
        this.zzg.add(this.zzk);
        this.zzg.add(this.zzl);
        this.zzg.add(this.zzm);
        this.zzg.add(this.zzn);
        this.zzg.add(this.zzo);
        this.zzg.add(this.zzp);
        this.zzg.add(this.zzq);
        this.zzg.add(this.zzr);
        this.zzg.add(this.zzs);
        this.zzg.add(this.zzt);
        this.zzg.add(this.zzu);
        this.zzg.add(this.zzv);
        this.zzg.add(this.zzw);
        zzm();
    }

    private static String zza(String str, List<zzbl> list, long j) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("requestId", j);
            jSONObject.put("type", "PRECACHE");
            if (str != null) {
                jSONObject.put("precacheData", str);
            }
            if (!(list == null || list.isEmpty())) {
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    jSONArray.put(i, ((zzbl) list.get(i)).zza());
                }
                jSONObject.put("requestItems", jSONArray);
            }
        } catch (JSONException e) {
        }
        return jSONObject.toString();
    }

    private final void zza(long j, JSONObject jSONObject) throws JSONException {
        int zza;
        boolean zza2 = this.zzi.zza(j);
        Object obj = 1;
        Object obj2 = (!this.zzm.zzb() || this.zzm.zza(j)) ? null : 1;
        if (!this.zzn.zzb() || this.zzn.zza(j)) {
            if (!this.zzo.zzb() || this.zzo.zza(j)) {
                obj = null;
            }
        }
        int i = obj2 != null ? 2 : 0;
        if (obj != null) {
            i |= 1;
        }
        if (!zza2) {
            if (this.zzf != null) {
                zza = this.zzf.zza(jSONObject, i);
                if ((zza & 1) != 0) {
                    this.zze = this.zza.zzb();
                    zzi();
                }
                if ((zza & 2) != 0) {
                    this.zze = this.zza.zzb();
                    zzi();
                }
                if ((zza & 4) != 0) {
                    zzj();
                }
                if ((zza & 8) != 0) {
                    zzk();
                }
                if ((zza & 16) != 0) {
                    zzl();
                }
                if ((zza & 32) != 0) {
                    this.zze = this.zza.zzb();
                    if (this.zzh != null) {
                        this.zzh.zze();
                    }
                }
                if ((zza & 64) != 0) {
                    this.zze = this.zza.zzb();
                    zzi();
                }
                for (zzbeo zza3 : this.zzg) {
                    zza3.zza(j, 0, null);
                }
            }
        }
        this.zzf = new MediaStatus(jSONObject);
        this.zze = this.zza.zzb();
        zza = MetaEvent.SEQUENCER_SPECIFIC;
        if ((zza & 1) != 0) {
            this.zze = this.zza.zzb();
            zzi();
        }
        if ((zza & 2) != 0) {
            this.zze = this.zza.zzb();
            zzi();
        }
        if ((zza & 4) != 0) {
            zzj();
        }
        if ((zza & 8) != 0) {
            zzk();
        }
        if ((zza & 16) != 0) {
            zzl();
        }
        if ((zza & 32) != 0) {
            this.zze = this.zza.zzb();
            if (this.zzh != null) {
                this.zzh.zze();
            }
        }
        if ((zza & 64) != 0) {
            this.zze = this.zza.zzb();
            zzi();
        }
        while (r8.hasNext()) {
            zza3.zza(j, 0, null);
        }
    }

    private final long zze() throws zzbel {
        if (this.zzf != null) {
            return this.zzf.zza();
        }
        throw new zzbel();
    }

    private final void zzi() {
        if (this.zzh != null) {
            this.zzh.zza();
        }
    }

    private final void zzj() {
        if (this.zzh != null) {
            this.zzh.zzb();
        }
    }

    private final void zzk() {
        if (this.zzh != null) {
            this.zzh.zzc();
        }
    }

    private final void zzl() {
        if (this.zzh != null) {
            this.zzh.zzd();
        }
    }

    private final void zzm() {
        this.zze = 0;
        this.zzf = null;
        for (zzbeo zza : this.zzg) {
            zza.zza();
        }
    }

    public final long zza() {
        MediaInfo zzd = zzd();
        if (zzd == null || this.zze == 0) {
            return 0;
        }
        double playbackRate = this.zzf.getPlaybackRate();
        long streamPosition = this.zzf.getStreamPosition();
        int playerState = this.zzf.getPlayerState();
        if (playbackRate == BrickValues.SET_COLOR_TO || playerState != 2) {
            return streamPosition;
        }
        long streamDuration = zzd.getStreamDuration();
        long zzb = this.zza.zzb() - this.zze;
        if (zzb < 0) {
            zzb = 0;
        }
        if (zzb == 0) {
            return streamPosition;
        }
        long j = streamPosition + ((long) (((double) zzb) * playbackRate));
        return (streamDuration <= 0 || j <= streamDuration) ? j < 0 ? 0 : j : streamDuration;
    }

    public final long zza(zzben zzben) throws IllegalStateException {
        JSONObject jSONObject = new JSONObject();
        long zzh = zzh();
        this.zzp.zza(zzh, zzben);
        zza(true);
        try {
            jSONObject.put("requestId", zzh);
            jSONObject.put("type", "GET_STATUS");
            if (this.zzf != null) {
                jSONObject.put("mediaSessionId", this.zzf.zza());
            }
        } catch (JSONException e) {
        }
        zza(jSONObject.toString(), zzh, null);
        return zzh;
    }

    public final long zza(zzben zzben, double d, JSONObject jSONObject) throws IllegalStateException, zzbel, IllegalArgumentException {
        if (!Double.isInfinite(d)) {
            if (!Double.isNaN(d)) {
                JSONObject jSONObject2 = new JSONObject();
                long zzh = zzh();
                this.zzn.zza(zzh, zzben);
                zza(true);
                try {
                    jSONObject2.put("requestId", zzh);
                    jSONObject2.put("type", "SET_VOLUME");
                    jSONObject2.put("mediaSessionId", zze());
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put(FirebaseAnalytics$Param.LEVEL, d);
                    jSONObject2.put(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, jSONObject3);
                    if (jSONObject != null) {
                        jSONObject2.put("customData", jSONObject);
                    }
                } catch (JSONException e) {
                }
                zza(jSONObject2.toString(), zzh, null);
                return zzh;
            }
        }
        StringBuilder stringBuilder = new StringBuilder(41);
        stringBuilder.append("Volume cannot be ");
        stringBuilder.append(d);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final long zza(zzben zzben, int i, long j, MediaQueueItem[] mediaQueueItemArr, int i2, Integer num, JSONObject jSONObject) throws IllegalArgumentException, IllegalStateException, zzbel {
        if (j == -1 || j >= 0) {
            JSONObject jSONObject2 = new JSONObject();
            long zzh = zzh();
            this.zzt.zza(zzh, zzben);
            zza(true);
            try {
                jSONObject2.put("requestId", zzh);
                jSONObject2.put("type", "QUEUE_UPDATE");
                jSONObject2.put("mediaSessionId", zze());
                if (i != 0) {
                    jSONObject2.put("currentItemId", i);
                }
                if (i2 != 0) {
                    jSONObject2.put("jump", i2);
                }
                if (mediaQueueItemArr != null && mediaQueueItemArr.length > 0) {
                    JSONArray jSONArray = new JSONArray();
                    for (i = 0; i < mediaQueueItemArr.length; i++) {
                        jSONArray.put(i, mediaQueueItemArr[i].zzb());
                    }
                    jSONObject2.put("items", jSONArray);
                }
                if (num != null) {
                    String str;
                    Object obj;
                    switch (num.intValue()) {
                        case 0:
                            str = "repeatMode";
                            obj = "REPEAT_OFF";
                            break;
                        case 1:
                            str = "repeatMode";
                            obj = "REPEAT_ALL";
                            break;
                        case 2:
                            str = "repeatMode";
                            obj = "REPEAT_SINGLE";
                            break;
                        case 3:
                            str = "repeatMode";
                            obj = "REPEAT_ALL_AND_SHUFFLE";
                            break;
                        default:
                            break;
                    }
                    jSONObject2.put(str, obj);
                }
                if (j != -1) {
                    jSONObject2.put("currentTime", ((double) j) / 1000.0d);
                }
                if (jSONObject != null) {
                    jSONObject2.put("customData", jSONObject);
                }
            } catch (JSONException e) {
            }
            zza(jSONObject2.toString(), zzh, null);
            return zzh;
        }
        StringBuilder stringBuilder = new StringBuilder(53);
        stringBuilder.append("playPosition cannot be negative: ");
        stringBuilder.append(j);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final long zza(zzben zzben, long j, int i, JSONObject jSONObject) throws IllegalStateException, zzbel {
        JSONObject jSONObject2 = new JSONObject();
        long zzh = zzh();
        this.zzm.zza(zzh, zzben);
        zza(true);
        try {
            String str;
            Object obj;
            jSONObject2.put("requestId", zzh);
            jSONObject2.put("type", "SEEK");
            jSONObject2.put("mediaSessionId", zze());
            jSONObject2.put("currentTime", ((double) j) / 1000.0d);
            if (i == 1) {
                str = "resumeState";
                obj = "PLAYBACK_START";
            } else {
                if (i == 2) {
                    str = "resumeState";
                    obj = "PLAYBACK_PAUSE";
                }
                if (jSONObject != null) {
                    jSONObject2.put("customData", jSONObject);
                }
                zza(jSONObject2.toString(), zzh, null);
                return zzh;
            }
            jSONObject2.put(str, obj);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzh, null);
        return zzh;
    }

    public final long zza(@NonNull zzben zzben, @NonNull MediaInfo mediaInfo, @NonNull MediaLoadOptions mediaLoadOptions) throws IllegalStateException, IllegalArgumentException {
        JSONObject jSONObject = new JSONObject();
        long zzh = zzh();
        this.zzi.zza(zzh, zzben);
        zza(true);
        try {
            jSONObject.put("requestId", zzh);
            jSONObject.put("type", "LOAD");
            jSONObject.put("media", mediaInfo.zza());
            jSONObject.put("autoplay", mediaLoadOptions.getAutoplay());
            jSONObject.put("currentTime", ((double) mediaLoadOptions.getPlayPosition()) / 1000.0d);
            jSONObject.put("playbackRate", mediaLoadOptions.getPlaybackRate());
            if (mediaLoadOptions.getCredentials() != null) {
                jSONObject.put("credentials", mediaLoadOptions.getCredentials());
            }
            if (mediaLoadOptions.getCredentialsType() != null) {
                jSONObject.put("credentialsType", mediaLoadOptions.getCredentialsType());
            }
            long[] activeTrackIds = mediaLoadOptions.getActiveTrackIds();
            if (activeTrackIds != null) {
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < activeTrackIds.length; i++) {
                    jSONArray.put(i, activeTrackIds[i]);
                }
                jSONObject.put("activeTrackIds", jSONArray);
            }
            JSONObject customData = mediaLoadOptions.getCustomData();
            if (customData != null) {
                jSONObject.put("customData", customData);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject.toString(), zzh, null);
        return zzh;
    }

    public final long zza(zzben zzben, TextTrackStyle textTrackStyle) throws IllegalStateException, zzbel {
        JSONObject jSONObject = new JSONObject();
        long zzh = zzh();
        this.zzr.zza(zzh, zzben);
        zza(true);
        try {
            jSONObject.put("requestId", zzh);
            jSONObject.put("type", "EDIT_TRACKS_INFO");
            if (textTrackStyle != null) {
                jSONObject.put("textTrackStyle", textTrackStyle.zza());
            }
            jSONObject.put("mediaSessionId", zze());
        } catch (JSONException e) {
        }
        zza(jSONObject.toString(), zzh, null);
        return zzh;
    }

    public final long zza(zzben zzben, JSONObject jSONObject) throws IllegalStateException, zzbel {
        JSONObject jSONObject2 = new JSONObject();
        long zzh = zzh();
        this.zzj.zza(zzh, zzben);
        zza(true);
        try {
            jSONObject2.put("requestId", zzh);
            jSONObject2.put("type", "PAUSE");
            jSONObject2.put("mediaSessionId", zze());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzh, null);
        return zzh;
    }

    public final long zza(zzben zzben, boolean z, JSONObject jSONObject) throws IllegalStateException, zzbel {
        JSONObject jSONObject2 = new JSONObject();
        long zzh = zzh();
        this.zzo.zza(zzh, zzben);
        zza(true);
        try {
            jSONObject2.put("requestId", zzh);
            jSONObject2.put("type", "SET_VOLUME");
            jSONObject2.put("mediaSessionId", zze());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("muted", z);
            jSONObject2.put(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzh, null);
        return zzh;
    }

    public final long zza(zzben zzben, int[] iArr, int i, JSONObject jSONObject) throws IllegalStateException, zzbel, IllegalArgumentException {
        if (iArr != null) {
            if (iArr.length != 0) {
                JSONObject jSONObject2 = new JSONObject();
                long zzh = zzh();
                this.zzv.zza(zzh, zzben);
                zza(true);
                try {
                    jSONObject2.put("requestId", zzh);
                    jSONObject2.put("type", "QUEUE_REORDER");
                    jSONObject2.put("mediaSessionId", zze());
                    JSONArray jSONArray = new JSONArray();
                    for (int i2 = 0; i2 < iArr.length; i2++) {
                        jSONArray.put(i2, iArr[i2]);
                    }
                    jSONObject2.put("itemIds", jSONArray);
                    if (i != 0) {
                        jSONObject2.put("insertBefore", i);
                    }
                    if (jSONObject != null) {
                        jSONObject2.put("customData", jSONObject);
                    }
                } catch (JSONException e) {
                }
                zza(jSONObject2.toString(), zzh, null);
                return zzh;
            }
        }
        throw new IllegalArgumentException("itemIdsToReorder must not be null or empty.");
    }

    public final long zza(zzben zzben, int[] iArr, JSONObject jSONObject) throws IllegalStateException, zzbel, IllegalArgumentException {
        if (iArr != null) {
            if (iArr.length != 0) {
                JSONObject jSONObject2 = new JSONObject();
                long zzh = zzh();
                this.zzu.zza(zzh, zzben);
                zza(true);
                try {
                    jSONObject2.put("requestId", zzh);
                    jSONObject2.put("type", "QUEUE_REMOVE");
                    jSONObject2.put("mediaSessionId", zze());
                    JSONArray jSONArray = new JSONArray();
                    for (int i = 0; i < iArr.length; i++) {
                        jSONArray.put(i, iArr[i]);
                    }
                    jSONObject2.put("itemIds", jSONArray);
                    if (jSONObject != null) {
                        jSONObject2.put("customData", jSONObject);
                    }
                } catch (JSONException e) {
                }
                zza(jSONObject2.toString(), zzh, null);
                return zzh;
            }
        }
        throw new IllegalArgumentException("itemIdsToRemove must not be null or empty.");
    }

    public final long zza(zzben zzben, long[] jArr) throws IllegalStateException, zzbel {
        JSONObject jSONObject = new JSONObject();
        long zzh = zzh();
        this.zzq.zza(zzh, zzben);
        zza(true);
        try {
            jSONObject.put("requestId", zzh);
            jSONObject.put("type", "EDIT_TRACKS_INFO");
            jSONObject.put("mediaSessionId", zze());
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < jArr.length; i++) {
                jSONArray.put(i, jArr[i]);
            }
            jSONObject.put("activeTrackIds", jSONArray);
        } catch (JSONException e) {
        }
        zza(jSONObject.toString(), zzh, null);
        return zzh;
    }

    public final long zza(zzben zzben, MediaQueueItem[] mediaQueueItemArr, int i, int i2, int i3, long j, JSONObject jSONObject) throws IllegalStateException, zzbel, IllegalArgumentException {
        zzbdo zzbdo = this;
        MediaQueueItem[] mediaQueueItemArr2 = mediaQueueItemArr;
        int i4 = i;
        int i5 = i3;
        long j2 = j;
        JSONObject jSONObject2 = jSONObject;
        if (mediaQueueItemArr2 != null) {
            if (mediaQueueItemArr2.length != 0) {
                if (i5 != -1 && (i5 < 0 || i5 >= mediaQueueItemArr2.length)) {
                    throw new IllegalArgumentException(String.format(Locale.ROOT, "currentItemIndexInItemsToInsert %d out of range [0, %d).", new Object[]{Integer.valueOf(i3), Integer.valueOf(mediaQueueItemArr2.length)}));
                } else if (j2 == -1 || j2 >= 0) {
                    JSONObject jSONObject3 = new JSONObject();
                    long zzh = zzh();
                    zzbdo.zzs.zza(zzh, zzben);
                    zza(true);
                    try {
                        jSONObject3.put("requestId", zzh);
                        jSONObject3.put("type", "QUEUE_INSERT");
                        jSONObject3.put("mediaSessionId", zze());
                        JSONArray jSONArray = new JSONArray();
                        for (int i6 = 0; i6 < mediaQueueItemArr2.length; i6++) {
                            jSONArray.put(i6, mediaQueueItemArr2[i6].zzb());
                        }
                        jSONObject3.put("items", jSONArray);
                        if (i4 != 0) {
                            jSONObject3.put("insertBefore", i4);
                        }
                        if (i5 != -1) {
                            jSONObject3.put("currentItemIndex", i5);
                        }
                        if (j2 != -1) {
                            jSONObject3.put("currentTime", ((double) j2) / 1000.0d);
                        }
                        if (jSONObject2 != null) {
                            jSONObject3.put("customData", jSONObject2);
                        }
                    } catch (JSONException e) {
                    }
                    zza(jSONObject3.toString(), zzh, null);
                    return zzh;
                } else {
                    StringBuilder stringBuilder = new StringBuilder(54);
                    stringBuilder.append("playPosition can not be negative: ");
                    stringBuilder.append(j2);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
        }
        throw new IllegalArgumentException("itemsToInsert must not be null or empty.");
    }

    public final long zza(zzben zzben, MediaQueueItem[] mediaQueueItemArr, int i, int i2, long j, JSONObject jSONObject) throws IllegalStateException, IllegalArgumentException {
        if (mediaQueueItemArr != null) {
            if (mediaQueueItemArr.length != 0) {
                if (i >= 0) {
                    if (i < mediaQueueItemArr.length) {
                        StringBuilder stringBuilder;
                        if (j == -1 || j >= 0) {
                            JSONObject jSONObject2 = new JSONObject();
                            long zzh = zzh();
                            this.zzi.zza(zzh, zzben);
                            zza(true);
                            try {
                                String str;
                                Object obj;
                                jSONObject2.put("requestId", zzh);
                                jSONObject2.put("type", "QUEUE_LOAD");
                                JSONArray jSONArray = new JSONArray();
                                for (int i3 = 0; i3 < mediaQueueItemArr.length; i3++) {
                                    jSONArray.put(i3, mediaQueueItemArr[i3].zzb());
                                }
                                jSONObject2.put("items", jSONArray);
                                switch (i2) {
                                    case 0:
                                        str = "repeatMode";
                                        obj = "REPEAT_OFF";
                                        break;
                                    case 1:
                                        str = "repeatMode";
                                        obj = "REPEAT_ALL";
                                        break;
                                    case 2:
                                        str = "repeatMode";
                                        obj = "REPEAT_SINGLE";
                                        break;
                                    case 3:
                                        str = "repeatMode";
                                        obj = "REPEAT_ALL_AND_SHUFFLE";
                                        break;
                                    default:
                                        stringBuilder = new StringBuilder(32);
                                        stringBuilder.append("Invalid repeat mode: ");
                                        stringBuilder.append(i2);
                                        throw new IllegalArgumentException(stringBuilder.toString());
                                }
                                jSONObject2.put(str, obj);
                                jSONObject2.put("startIndex", i);
                                if (j != -1) {
                                    jSONObject2.put("currentTime", ((double) j) / 1000.0d);
                                }
                                if (jSONObject != null) {
                                    jSONObject2.put("customData", jSONObject);
                                }
                            } catch (JSONException e) {
                            }
                            zza(jSONObject2.toString(), zzh, null);
                            return zzh;
                        }
                        stringBuilder = new StringBuilder(54);
                        stringBuilder.append("playPosition can not be negative: ");
                        stringBuilder.append(j);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                }
                StringBuilder stringBuilder2 = new StringBuilder(31);
                stringBuilder2.append("Invalid startIndex: ");
                stringBuilder2.append(i);
                throw new IllegalArgumentException(stringBuilder2.toString());
            }
        }
        throw new IllegalArgumentException("items must not be null or empty.");
    }

    public final long zza(String str, List<zzbl> list) throws IllegalStateException {
        long zzh = zzh();
        zza(zza(str, (List) list, zzh), zzh, null);
        return zzh;
    }

    public final void zza(long j, int i) {
        for (zzbeo zza : this.zzg) {
            zza.zza(j, i, null);
        }
    }

    public final void zza(zzbek zzbek) {
        this.zzh = zzbek;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(java.lang.String r11) {
        /*
        r10 = this;
        r0 = r10.zzc;
        r1 = "message received: %s";
        r2 = 1;
        r3 = new java.lang.Object[r2];
        r4 = 0;
        r3[r4] = r11;
        r0.zza(r1, r3);
        r0 = 2;
        r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x00f1 }
        r1.<init>(r11);	 Catch:{ JSONException -> 0x00f1 }
        r3 = "type";
        r3 = r1.getString(r3);	 Catch:{ JSONException -> 0x00f1 }
        r5 = "requestId";
        r6 = -1;
        r5 = r1.optLong(r5, r6);	 Catch:{ JSONException -> 0x00f1 }
        r7 = -1;
        r8 = r3.hashCode();	 Catch:{ JSONException -> 0x00f1 }
        switch(r8) {
            case -1830647528: goto L_0x0052;
            case -1125000185: goto L_0x0048;
            case -262628938: goto L_0x003e;
            case 431600379: goto L_0x0034;
            case 823510221: goto L_0x002a;
            default: goto L_0x0029;
        };	 Catch:{ JSONException -> 0x00f1 }
    L_0x0029:
        goto L_0x005c;
    L_0x002a:
        r8 = "MEDIA_STATUS";
        r3 = r3.equals(r8);	 Catch:{ JSONException -> 0x00f1 }
        if (r3 == 0) goto L_0x005c;
    L_0x0032:
        r3 = 0;
        goto L_0x005d;
    L_0x0034:
        r8 = "INVALID_PLAYER_STATE";
        r3 = r3.equals(r8);	 Catch:{ JSONException -> 0x00f1 }
        if (r3 == 0) goto L_0x005c;
    L_0x003c:
        r3 = 1;
        goto L_0x005d;
    L_0x003e:
        r8 = "LOAD_FAILED";
        r3 = r3.equals(r8);	 Catch:{ JSONException -> 0x00f1 }
        if (r3 == 0) goto L_0x005c;
    L_0x0046:
        r3 = 2;
        goto L_0x005d;
    L_0x0048:
        r8 = "INVALID_REQUEST";
        r3 = r3.equals(r8);	 Catch:{ JSONException -> 0x00f1 }
        if (r3 == 0) goto L_0x005c;
    L_0x0050:
        r3 = 4;
        goto L_0x005d;
    L_0x0052:
        r8 = "LOAD_CANCELLED";
        r3 = r3.equals(r8);	 Catch:{ JSONException -> 0x00f1 }
        if (r3 == 0) goto L_0x005c;
    L_0x005a:
        r3 = 3;
        goto L_0x005d;
    L_0x005c:
        r3 = -1;
    L_0x005d:
        r7 = 2100; // 0x834 float:2.943E-42 double:1.0375E-320;
        switch(r3) {
            case 0: goto L_0x00c8;
            case 1: goto L_0x00a2;
            case 2: goto L_0x0096;
            case 3: goto L_0x0088;
            case 4: goto L_0x0063;
            default: goto L_0x0062;
        };	 Catch:{ JSONException -> 0x00f1 }
    L_0x0062:
        return;
    L_0x0063:
        r3 = r10.zzc;	 Catch:{ JSONException -> 0x00f1 }
        r8 = "received unexpected error: Invalid Request.";
        r9 = new java.lang.Object[r4];	 Catch:{ JSONException -> 0x00f1 }
        r3.zzc(r8, r9);	 Catch:{ JSONException -> 0x00f1 }
        r3 = "customData";
        r1 = r1.optJSONObject(r3);	 Catch:{ JSONException -> 0x00f1 }
        r3 = r10.zzg;	 Catch:{ JSONException -> 0x00f1 }
        r3 = r3.iterator();	 Catch:{ JSONException -> 0x00f1 }
    L_0x0078:
        r8 = r3.hasNext();	 Catch:{ JSONException -> 0x00f1 }
        if (r8 == 0) goto L_0x00f0;
    L_0x007e:
        r8 = r3.next();	 Catch:{ JSONException -> 0x00f1 }
        r8 = (com.google.android.gms.internal.zzbeo) r8;	 Catch:{ JSONException -> 0x00f1 }
        r8.zza(r5, r7, r1);	 Catch:{ JSONException -> 0x00f1 }
        goto L_0x0078;
    L_0x0088:
        r3 = "customData";
        r1 = r1.optJSONObject(r3);	 Catch:{ JSONException -> 0x00f1 }
        r3 = r10.zzi;	 Catch:{ JSONException -> 0x00f1 }
        r7 = 2101; // 0x835 float:2.944E-42 double:1.038E-320;
        r3.zza(r5, r7, r1);	 Catch:{ JSONException -> 0x00f1 }
        return;
    L_0x0096:
        r3 = "customData";
        r1 = r1.optJSONObject(r3);	 Catch:{ JSONException -> 0x00f1 }
        r3 = r10.zzi;	 Catch:{ JSONException -> 0x00f1 }
        r3.zza(r5, r7, r1);	 Catch:{ JSONException -> 0x00f1 }
        return;
    L_0x00a2:
        r3 = r10.zzc;	 Catch:{ JSONException -> 0x00f1 }
        r8 = "received unexpected error: Invalid Player State.";
        r9 = new java.lang.Object[r4];	 Catch:{ JSONException -> 0x00f1 }
        r3.zzc(r8, r9);	 Catch:{ JSONException -> 0x00f1 }
        r3 = "customData";
        r1 = r1.optJSONObject(r3);	 Catch:{ JSONException -> 0x00f1 }
        r3 = r10.zzg;	 Catch:{ JSONException -> 0x00f1 }
        r3 = r3.iterator();	 Catch:{ JSONException -> 0x00f1 }
    L_0x00b7:
        r8 = r3.hasNext();	 Catch:{ JSONException -> 0x00f1 }
        if (r8 == 0) goto L_0x00c7;
    L_0x00bd:
        r8 = r3.next();	 Catch:{ JSONException -> 0x00f1 }
        r8 = (com.google.android.gms.internal.zzbeo) r8;	 Catch:{ JSONException -> 0x00f1 }
        r8.zza(r5, r7, r1);	 Catch:{ JSONException -> 0x00f1 }
        goto L_0x00b7;
    L_0x00c7:
        return;
    L_0x00c8:
        r3 = "status";
        r1 = r1.getJSONArray(r3);	 Catch:{ JSONException -> 0x00f1 }
        r3 = r1.length();	 Catch:{ JSONException -> 0x00f1 }
        if (r3 <= 0) goto L_0x00dc;
    L_0x00d4:
        r1 = r1.getJSONObject(r4);	 Catch:{ JSONException -> 0x00f1 }
        r10.zza(r5, r1);	 Catch:{ JSONException -> 0x00f1 }
        return;
    L_0x00dc:
        r1 = 0;
        r10.zzf = r1;	 Catch:{ JSONException -> 0x00f1 }
        r10.zzi();	 Catch:{ JSONException -> 0x00f1 }
        r10.zzj();	 Catch:{ JSONException -> 0x00f1 }
        r10.zzk();	 Catch:{ JSONException -> 0x00f1 }
        r10.zzl();	 Catch:{ JSONException -> 0x00f1 }
        r3 = r10.zzp;	 Catch:{ JSONException -> 0x00f1 }
        r3.zza(r5, r4, r1);	 Catch:{ JSONException -> 0x00f1 }
    L_0x00f0:
        return;
    L_0x00f1:
        r1 = move-exception;
        r3 = r10.zzc;
        r5 = "Message is malformed (%s); ignoring: %s";
        r0 = new java.lang.Object[r0];
        r1 = r1.getMessage();
        r0[r4] = r1;
        r0[r2] = r11;
        r3.zzc(r5, r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbej.zza(java.lang.String):void");
    }

    protected final boolean zza(long j) {
        for (zzbeo zza : this.zzg) {
            zza.zza(j, (int) RemoteMediaPlayer.STATUS_TIMED_OUT);
        }
        boolean z = false;
        synchronized (zzbeo.zza) {
            for (zzbeo zza2 : this.zzg) {
                if (zza2.zzb()) {
                    z = true;
                    break;
                }
            }
        }
        return z;
    }

    public final long zzb() {
        MediaInfo zzd = zzd();
        return zzd != null ? zzd.getStreamDuration() : 0;
    }

    public final long zzb(zzben zzben, double d, JSONObject jSONObject) throws IllegalStateException, zzbel {
        if (this.zzf == null) {
            throw new zzbel();
        }
        JSONObject jSONObject2 = new JSONObject();
        long zzh = zzh();
        this.zzw.zza(zzh, zzben);
        zza(true);
        try {
            jSONObject2.put("requestId", zzh);
            jSONObject2.put("type", "SET_PLAYBACK_RATE");
            jSONObject2.put("playbackRate", d);
            jSONObject2.put("mediaSessionId", this.zzf.zza());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzh, null);
        return zzh;
    }

    public final long zzb(zzben zzben, JSONObject jSONObject) throws IllegalStateException, zzbel {
        JSONObject jSONObject2 = new JSONObject();
        long zzh = zzh();
        this.zzl.zza(zzh, zzben);
        zza(true);
        try {
            jSONObject2.put("requestId", zzh);
            jSONObject2.put("type", "STOP");
            jSONObject2.put("mediaSessionId", zze());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzh, null);
        return zzh;
    }

    public final long zzc(zzben zzben, JSONObject jSONObject) throws IllegalStateException, zzbel {
        JSONObject jSONObject2 = new JSONObject();
        long zzh = zzh();
        this.zzk.zza(zzh, zzben);
        zza(true);
        try {
            jSONObject2.put("requestId", zzh);
            jSONObject2.put("type", "PLAY");
            jSONObject2.put("mediaSessionId", zze());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        zza(jSONObject2.toString(), zzh, null);
        return zzh;
    }

    public final MediaStatus zzc() {
        return this.zzf;
    }

    public final MediaInfo zzd() {
        return this.zzf == null ? null : this.zzf.getMediaInfo();
    }

    public final void zzf() {
        super.zzf();
        zzm();
    }
}
