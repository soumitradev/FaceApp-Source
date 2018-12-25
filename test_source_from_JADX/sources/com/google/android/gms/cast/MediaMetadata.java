package com.google.android.gms.cast;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbep;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class MediaMetadata extends zzbgl {
    @Hide
    public static final Creator<MediaMetadata> CREATOR = new zzag();
    public static final String KEY_ALBUM_ARTIST = "com.google.android.gms.cast.metadata.ALBUM_ARTIST";
    public static final String KEY_ALBUM_TITLE = "com.google.android.gms.cast.metadata.ALBUM_TITLE";
    public static final String KEY_ARTIST = "com.google.android.gms.cast.metadata.ARTIST";
    public static final String KEY_BROADCAST_DATE = "com.google.android.gms.cast.metadata.BROADCAST_DATE";
    public static final String KEY_COMPOSER = "com.google.android.gms.cast.metadata.COMPOSER";
    public static final String KEY_CREATION_DATE = "com.google.android.gms.cast.metadata.CREATION_DATE";
    public static final String KEY_DISC_NUMBER = "com.google.android.gms.cast.metadata.DISC_NUMBER";
    public static final String KEY_EPISODE_NUMBER = "com.google.android.gms.cast.metadata.EPISODE_NUMBER";
    public static final String KEY_HEIGHT = "com.google.android.gms.cast.metadata.HEIGHT";
    public static final String KEY_LOCATION_LATITUDE = "com.google.android.gms.cast.metadata.LOCATION_LATITUDE";
    public static final String KEY_LOCATION_LONGITUDE = "com.google.android.gms.cast.metadata.LOCATION_LONGITUDE";
    public static final String KEY_LOCATION_NAME = "com.google.android.gms.cast.metadata.LOCATION_NAME";
    public static final String KEY_RELEASE_DATE = "com.google.android.gms.cast.metadata.RELEASE_DATE";
    public static final String KEY_SEASON_NUMBER = "com.google.android.gms.cast.metadata.SEASON_NUMBER";
    public static final String KEY_SERIES_TITLE = "com.google.android.gms.cast.metadata.SERIES_TITLE";
    public static final String KEY_STUDIO = "com.google.android.gms.cast.metadata.STUDIO";
    public static final String KEY_SUBTITLE = "com.google.android.gms.cast.metadata.SUBTITLE";
    public static final String KEY_TITLE = "com.google.android.gms.cast.metadata.TITLE";
    public static final String KEY_TRACK_NUMBER = "com.google.android.gms.cast.metadata.TRACK_NUMBER";
    public static final String KEY_WIDTH = "com.google.android.gms.cast.metadata.WIDTH";
    public static final int MEDIA_TYPE_GENERIC = 0;
    public static final int MEDIA_TYPE_MOVIE = 1;
    public static final int MEDIA_TYPE_MUSIC_TRACK = 3;
    public static final int MEDIA_TYPE_PHOTO = 4;
    public static final int MEDIA_TYPE_TV_SHOW = 2;
    public static final int MEDIA_TYPE_USER = 100;
    private static final String[] zza = new String[]{null, "String", "int", "double", "ISO-8601 date String"};
    private static final zza zzb = new zza().zza(KEY_CREATION_DATE, "creationDateTime", 4).zza(KEY_RELEASE_DATE, "releaseDate", 4).zza(KEY_BROADCAST_DATE, "originalAirdate", 4).zza(KEY_TITLE, "title", 1).zza(KEY_SUBTITLE, "subtitle", 1).zza(KEY_ARTIST, "artist", 1).zza(KEY_ALBUM_ARTIST, "albumArtist", 1).zza(KEY_ALBUM_TITLE, "albumName", 1).zza(KEY_COMPOSER, "composer", 1).zza(KEY_DISC_NUMBER, "discNumber", 2).zza(KEY_TRACK_NUMBER, "trackNumber", 2).zza(KEY_SEASON_NUMBER, "season", 2).zza(KEY_EPISODE_NUMBER, "episode", 2).zza(KEY_SERIES_TITLE, "seriesTitle", 1).zza(KEY_STUDIO, "studio", 1).zza(KEY_WIDTH, SettingsJsonConstants.ICON_WIDTH_KEY, 2).zza(KEY_HEIGHT, SettingsJsonConstants.ICON_HEIGHT_KEY, 2).zza(KEY_LOCATION_NAME, FirebaseAnalytics$Param.LOCATION, 1).zza(KEY_LOCATION_LATITUDE, "latitude", 3).zza(KEY_LOCATION_LONGITUDE, "longitude", 3);
    private final List<WebImage> zzc;
    private Bundle zzd;
    private int zze;

    static class zza {
        private final Map<String, String> zza = new HashMap();
        private final Map<String, String> zzb = new HashMap();
        private final Map<String, Integer> zzc = new HashMap();

        public final zza zza(String str, String str2, int i) {
            this.zza.put(str, str2);
            this.zzb.put(str2, str);
            this.zzc.put(str, Integer.valueOf(i));
            return this;
        }

        public final String zza(String str) {
            return (String) this.zza.get(str);
        }

        public final String zzb(String str) {
            return (String) this.zzb.get(str);
        }

        public final int zzc(String str) {
            Integer num = (Integer) this.zzc.get(str);
            return num != null ? num.intValue() : 0;
        }
    }

    public MediaMetadata() {
        this(0);
    }

    public MediaMetadata(int i) {
        this(new ArrayList(), new Bundle(), i);
    }

    MediaMetadata(List<WebImage> list, Bundle bundle, int i) {
        this.zzc = list;
        this.zzd = bundle;
        this.zze = i;
    }

    private static void zza(String str, int i) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("null and empty keys are not allowed");
        }
        int zzc = zzb.zzc(str);
        if (zzc != i && zzc != 0) {
            String str2 = zza[i];
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 21) + String.valueOf(str2).length());
            stringBuilder.append("Value for ");
            stringBuilder.append(str);
            stringBuilder.append(" must be a ");
            stringBuilder.append(str2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    private final void zza(JSONObject jSONObject, String... strArr) {
        try {
            for (String str : strArr) {
                if (this.zzd.containsKey(str)) {
                    switch (zzb.zzc(str)) {
                        case 1:
                        case 4:
                            jSONObject.put(zzb.zza(str), this.zzd.getString(str));
                            break;
                        case 2:
                            jSONObject.put(zzb.zza(str), this.zzd.getInt(str));
                            break;
                        case 3:
                            jSONObject.put(zzb.zza(str), this.zzd.getDouble(str));
                            break;
                        default:
                            break;
                    }
                }
            }
            for (String str2 : this.zzd.keySet()) {
                if (!str2.startsWith("com.google.")) {
                    Object obj = this.zzd.get(str2);
                    if (!(obj instanceof String)) {
                        if (!(obj instanceof Integer)) {
                            if (obj instanceof Double) {
                            }
                        }
                    }
                    jSONObject.put(str2, obj);
                }
            }
        } catch (JSONException e) {
        }
    }

    private final boolean zza(Bundle bundle, Bundle bundle2) {
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            Object obj2 = bundle2.get(str);
            if ((obj instanceof Bundle) && (obj2 instanceof Bundle) && !zza((Bundle) obj, (Bundle) obj2)) {
                return false;
            }
            if (obj == null) {
                if (obj2 != null || !bundle2.containsKey(str)) {
                    return false;
                }
            } else if (!obj.equals(obj2)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzb(org.json.JSONObject r7, java.lang.String... r8) {
        /*
        r6 = this;
        r0 = new java.util.HashSet;
        r8 = java.util.Arrays.asList(r8);
        r0.<init>(r8);
        r8 = r7.keys();	 Catch:{ JSONException -> 0x00b4 }
    L_0x000d:
        r1 = r8.hasNext();	 Catch:{ JSONException -> 0x00b4 }
        if (r1 == 0) goto L_0x00b3;
    L_0x0013:
        r1 = r8.next();	 Catch:{ JSONException -> 0x00b4 }
        r1 = (java.lang.String) r1;	 Catch:{ JSONException -> 0x00b4 }
        r2 = "metadataType";
        r2 = r2.equals(r1);	 Catch:{ JSONException -> 0x00b4 }
        if (r2 != 0) goto L_0x000d;
    L_0x0021:
        r2 = zzb;	 Catch:{ JSONException -> 0x00b4 }
        r2 = r2.zzb(r1);	 Catch:{ JSONException -> 0x00b4 }
        if (r2 == 0) goto L_0x0080;
    L_0x0029:
        r3 = r0.contains(r2);	 Catch:{ JSONException -> 0x00b4 }
        if (r3 == 0) goto L_0x000d;
    L_0x002f:
        r1 = r7.get(r1);	 Catch:{ JSONException -> 0x007e }
        if (r1 != 0) goto L_0x0036;
    L_0x0035:
        goto L_0x000d;
    L_0x0036:
        r3 = zzb;	 Catch:{ JSONException -> 0x007e }
        r3 = r3.zzc(r2);	 Catch:{ JSONException -> 0x007e }
        switch(r3) {
            case 1: goto L_0x0075;
            case 2: goto L_0x0065;
            case 3: goto L_0x0055;
            case 4: goto L_0x0040;
            default: goto L_0x003f;
        };	 Catch:{ JSONException -> 0x007e }
    L_0x003f:
        goto L_0x000d;
    L_0x0040:
        r3 = r1 instanceof java.lang.String;	 Catch:{ JSONException -> 0x007e }
        if (r3 == 0) goto L_0x000d;
    L_0x0044:
        r3 = r1;
        r3 = (java.lang.String) r3;	 Catch:{ JSONException -> 0x007e }
        r3 = com.google.android.gms.internal.zzbep.zza(r3);	 Catch:{ JSONException -> 0x007e }
        if (r3 == 0) goto L_0x000d;
    L_0x004d:
        r3 = r6.zzd;	 Catch:{ JSONException -> 0x007e }
        r1 = (java.lang.String) r1;	 Catch:{ JSONException -> 0x007e }
    L_0x0051:
        r3.putString(r2, r1);	 Catch:{ JSONException -> 0x007e }
        goto L_0x000d;
    L_0x0055:
        r3 = r1 instanceof java.lang.Double;	 Catch:{ JSONException -> 0x007e }
        if (r3 == 0) goto L_0x000d;
    L_0x0059:
        r3 = r6.zzd;	 Catch:{ JSONException -> 0x007e }
        r1 = (java.lang.Double) r1;	 Catch:{ JSONException -> 0x007e }
        r4 = r1.doubleValue();	 Catch:{ JSONException -> 0x007e }
        r3.putDouble(r2, r4);	 Catch:{ JSONException -> 0x007e }
        goto L_0x000d;
    L_0x0065:
        r3 = r1 instanceof java.lang.Integer;	 Catch:{ JSONException -> 0x007e }
        if (r3 == 0) goto L_0x000d;
    L_0x0069:
        r3 = r6.zzd;	 Catch:{ JSONException -> 0x007e }
        r1 = (java.lang.Integer) r1;	 Catch:{ JSONException -> 0x007e }
        r1 = r1.intValue();	 Catch:{ JSONException -> 0x007e }
        r3.putInt(r2, r1);	 Catch:{ JSONException -> 0x007e }
        goto L_0x000d;
    L_0x0075:
        r3 = r1 instanceof java.lang.String;	 Catch:{ JSONException -> 0x007e }
        if (r3 == 0) goto L_0x000d;
    L_0x0079:
        r3 = r6.zzd;	 Catch:{ JSONException -> 0x007e }
        r1 = (java.lang.String) r1;	 Catch:{ JSONException -> 0x007e }
        goto L_0x0051;
    L_0x007e:
        r1 = move-exception;
        goto L_0x000d;
    L_0x0080:
        r2 = r7.get(r1);	 Catch:{ JSONException -> 0x00b4 }
        r3 = r2 instanceof java.lang.String;	 Catch:{ JSONException -> 0x00b4 }
        if (r3 == 0) goto L_0x0091;
    L_0x0088:
        r3 = r6.zzd;	 Catch:{ JSONException -> 0x00b4 }
        r2 = (java.lang.String) r2;	 Catch:{ JSONException -> 0x00b4 }
        r3.putString(r1, r2);	 Catch:{ JSONException -> 0x00b4 }
        goto L_0x000d;
    L_0x0091:
        r3 = r2 instanceof java.lang.Integer;	 Catch:{ JSONException -> 0x00b4 }
        if (r3 == 0) goto L_0x00a2;
    L_0x0095:
        r3 = r6.zzd;	 Catch:{ JSONException -> 0x00b4 }
        r2 = (java.lang.Integer) r2;	 Catch:{ JSONException -> 0x00b4 }
        r2 = r2.intValue();	 Catch:{ JSONException -> 0x00b4 }
        r3.putInt(r1, r2);	 Catch:{ JSONException -> 0x00b4 }
        goto L_0x000d;
    L_0x00a2:
        r3 = r2 instanceof java.lang.Double;	 Catch:{ JSONException -> 0x00b4 }
        if (r3 == 0) goto L_0x000d;
    L_0x00a6:
        r3 = r6.zzd;	 Catch:{ JSONException -> 0x00b4 }
        r2 = (java.lang.Double) r2;	 Catch:{ JSONException -> 0x00b4 }
        r4 = r2.doubleValue();	 Catch:{ JSONException -> 0x00b4 }
        r3.putDouble(r1, r4);	 Catch:{ JSONException -> 0x00b4 }
        goto L_0x000d;
    L_0x00b3:
        return;
    L_0x00b4:
        r7 = move-exception;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.MediaMetadata.zzb(org.json.JSONObject, java.lang.String[]):void");
    }

    public void addImage(WebImage webImage) {
        this.zzc.add(webImage);
    }

    public void clear() {
        this.zzd.clear();
        this.zzc.clear();
    }

    public void clearImages() {
        this.zzc.clear();
    }

    public boolean containsKey(String str) {
        return this.zzd.containsKey(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaMetadata)) {
            return false;
        }
        MediaMetadata mediaMetadata = (MediaMetadata) obj;
        return zza(this.zzd, mediaMetadata.zzd) && this.zzc.equals(mediaMetadata.zzc);
    }

    public Calendar getDate(String str) {
        zza(str, 4);
        str = this.zzd.getString(str);
        return str != null ? zzbep.zza(str) : null;
    }

    public String getDateAsString(String str) {
        zza(str, 4);
        return this.zzd.getString(str);
    }

    public double getDouble(String str) {
        zza(str, 3);
        return this.zzd.getDouble(str);
    }

    public List<WebImage> getImages() {
        return this.zzc;
    }

    public int getInt(String str) {
        zza(str, 2);
        return this.zzd.getInt(str);
    }

    public int getMediaType() {
        return this.zze;
    }

    public String getString(String str) {
        zza(str, 1);
        return this.zzd.getString(str);
    }

    public boolean hasImages() {
        return (this.zzc == null || this.zzc.isEmpty()) ? false : true;
    }

    public int hashCode() {
        int i = 17;
        for (String str : this.zzd.keySet()) {
            i = (i * 31) + this.zzd.get(str).hashCode();
        }
        return (i * 31) + this.zzc.hashCode();
    }

    public Set<String> keySet() {
        return this.zzd.keySet();
    }

    public void putDate(String str, Calendar calendar) {
        zza(str, 4);
        this.zzd.putString(str, zzbep.zza(calendar));
    }

    public void putDouble(String str, double d) {
        zza(str, 3);
        this.zzd.putDouble(str, d);
    }

    public void putInt(String str, int i) {
        zza(str, 2);
        this.zzd.putInt(str, i);
    }

    public void putString(String str, String str2) {
        zza(str, 1);
        this.zzd.putString(str, str2);
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zzc(parcel, 2, getImages(), false);
        zzbgo.zza(parcel, 3, this.zzd, false);
        zzbgo.zza(parcel, 4, getMediaType());
        zzbgo.zza(parcel, i);
    }

    @com.google.android.gms.common.internal.Hide
    public final org.json.JSONObject zza() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:17:0x0066 in {3, 4, 7, 9, 10, 11, 12, 13, 14, 15, 16} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r10 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = "metadataType";	 Catch:{ JSONException -> 0x000d }
        r2 = r10.zze;	 Catch:{ JSONException -> 0x000d }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x000d }
        goto L_0x000e;
    L_0x000d:
        r1 = move-exception;
    L_0x000e:
        r1 = r10.zzc;
        com.google.android.gms.internal.zzbep.zza(r0, r1);
        r1 = r10.zze;
        switch(r1) {
            case 0: goto L_0x0059;
            case 1: goto L_0x0054;
            case 2: goto L_0x0045;
            case 3: goto L_0x0030;
            case 4: goto L_0x001f;
            default: goto L_0x0018;
        };
    L_0x0018:
        r1 = 0;
        r1 = new java.lang.String[r1];
    L_0x001b:
        r10.zza(r0, r1);
        return r0;
    L_0x001f:
        r2 = "com.google.android.gms.cast.metadata.TITLE";
        r3 = "com.google.android.gms.cast.metadata.ARTIST";
        r4 = "com.google.android.gms.cast.metadata.LOCATION_NAME";
        r5 = "com.google.android.gms.cast.metadata.LOCATION_LATITUDE";
        r6 = "com.google.android.gms.cast.metadata.LOCATION_LONGITUDE";
        r7 = "com.google.android.gms.cast.metadata.WIDTH";
        r8 = "com.google.android.gms.cast.metadata.HEIGHT";
        r9 = "com.google.android.gms.cast.metadata.CREATION_DATE";
        goto L_0x0040;
    L_0x0030:
        r2 = "com.google.android.gms.cast.metadata.TITLE";
        r3 = "com.google.android.gms.cast.metadata.ARTIST";
        r4 = "com.google.android.gms.cast.metadata.ALBUM_TITLE";
        r5 = "com.google.android.gms.cast.metadata.ALBUM_ARTIST";
        r6 = "com.google.android.gms.cast.metadata.COMPOSER";
        r7 = "com.google.android.gms.cast.metadata.TRACK_NUMBER";
        r8 = "com.google.android.gms.cast.metadata.DISC_NUMBER";
        r9 = "com.google.android.gms.cast.metadata.RELEASE_DATE";
    L_0x0040:
        r1 = new java.lang.String[]{r2, r3, r4, r5, r6, r7, r8, r9};
        goto L_0x001b;
    L_0x0045:
        r1 = "com.google.android.gms.cast.metadata.TITLE";
        r2 = "com.google.android.gms.cast.metadata.SERIES_TITLE";
        r3 = "com.google.android.gms.cast.metadata.SEASON_NUMBER";
        r4 = "com.google.android.gms.cast.metadata.EPISODE_NUMBER";
        r5 = "com.google.android.gms.cast.metadata.BROADCAST_DATE";
        r1 = new java.lang.String[]{r1, r2, r3, r4, r5};
        goto L_0x001b;
    L_0x0054:
        r1 = "com.google.android.gms.cast.metadata.TITLE";
        r2 = "com.google.android.gms.cast.metadata.STUDIO";
        goto L_0x005d;
    L_0x0059:
        r1 = "com.google.android.gms.cast.metadata.TITLE";
        r2 = "com.google.android.gms.cast.metadata.ARTIST";
    L_0x005d:
        r3 = "com.google.android.gms.cast.metadata.SUBTITLE";
        r4 = "com.google.android.gms.cast.metadata.RELEASE_DATE";
        r1 = new java.lang.String[]{r1, r2, r3, r4};
        goto L_0x001b;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.MediaMetadata.zza():org.json.JSONObject");
    }

    @Hide
    public final void zza(JSONObject jSONObject) {
        String str;
        String str2;
        String[] strArr;
        clear();
        this.zze = 0;
        try {
            this.zze = jSONObject.getInt("metadataType");
        } catch (JSONException e) {
        }
        zzbep.zza(this.zzc, jSONObject);
        switch (this.zze) {
            case 0:
                str = KEY_TITLE;
                str2 = KEY_ARTIST;
                break;
            case 1:
                str = KEY_TITLE;
                str2 = KEY_STUDIO;
                break;
            case 2:
                strArr = new String[]{KEY_TITLE, KEY_SERIES_TITLE, KEY_SEASON_NUMBER, KEY_EPISODE_NUMBER, KEY_BROADCAST_DATE};
                break;
            case 3:
                strArr = new String[]{KEY_TITLE, KEY_ALBUM_TITLE, KEY_ARTIST, KEY_ALBUM_ARTIST, KEY_COMPOSER, KEY_TRACK_NUMBER, KEY_DISC_NUMBER, KEY_RELEASE_DATE};
                break;
            case 4:
                strArr = new String[]{KEY_TITLE, KEY_ARTIST, KEY_LOCATION_NAME, KEY_LOCATION_LATITUDE, KEY_LOCATION_LONGITUDE, KEY_WIDTH, KEY_HEIGHT, KEY_CREATION_DATE};
                break;
            default:
                strArr = new String[0];
                break;
        }
        strArr = new String[]{str, str2, KEY_SUBTITLE, KEY_RELEASE_DATE};
        zzb(jSONObject, strArr);
    }
}
