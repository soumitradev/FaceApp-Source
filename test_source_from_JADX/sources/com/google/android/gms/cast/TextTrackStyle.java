package com.google.android.gms.cast;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.view.accessibility.CaptioningManager;
import android.view.accessibility.CaptioningManager.CaptionStyle;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

public final class TextTrackStyle extends zzbgl {
    public static final int COLOR_UNSPECIFIED = 0;
    @Hide
    public static final Creator<TextTrackStyle> CREATOR = new zzbn();
    public static final float DEFAULT_FONT_SCALE = 1.0f;
    public static final int EDGE_TYPE_DEPRESSED = 4;
    public static final int EDGE_TYPE_DROP_SHADOW = 2;
    public static final int EDGE_TYPE_NONE = 0;
    public static final int EDGE_TYPE_OUTLINE = 1;
    public static final int EDGE_TYPE_RAISED = 3;
    public static final int EDGE_TYPE_UNSPECIFIED = -1;
    public static final int FONT_FAMILY_CASUAL = 4;
    public static final int FONT_FAMILY_CURSIVE = 5;
    public static final int FONT_FAMILY_MONOSPACED_SANS_SERIF = 1;
    public static final int FONT_FAMILY_MONOSPACED_SERIF = 3;
    public static final int FONT_FAMILY_SANS_SERIF = 0;
    public static final int FONT_FAMILY_SERIF = 2;
    public static final int FONT_FAMILY_SMALL_CAPITALS = 6;
    public static final int FONT_FAMILY_UNSPECIFIED = -1;
    public static final int FONT_STYLE_BOLD = 1;
    public static final int FONT_STYLE_BOLD_ITALIC = 3;
    public static final int FONT_STYLE_ITALIC = 2;
    public static final int FONT_STYLE_NORMAL = 0;
    public static final int FONT_STYLE_UNSPECIFIED = -1;
    public static final int WINDOW_TYPE_NONE = 0;
    public static final int WINDOW_TYPE_NORMAL = 1;
    public static final int WINDOW_TYPE_ROUNDED = 2;
    public static final int WINDOW_TYPE_UNSPECIFIED = -1;
    private float zza;
    private int zzb;
    private int zzc;
    private int zzd;
    private int zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private String zzi;
    private int zzj;
    private int zzk;
    private String zzl;
    private JSONObject zzm;

    public TextTrackStyle() {
        this(1.0f, 0, 0, -1, 0, -1, 0, 0, null, -1, -1, null);
    }

    TextTrackStyle(float f, int i, int i2, int i3, int i4, int i5, int i6, int i7, String str, int i8, int i9, String str2) {
        this.zza = f;
        this.zzb = i;
        this.zzc = i2;
        this.zzd = i3;
        this.zze = i4;
        this.zzf = i5;
        this.zzg = i6;
        this.zzh = i7;
        this.zzi = str;
        this.zzj = i8;
        this.zzk = i9;
        this.zzl = str2;
        if (this.zzl != null) {
            try {
                this.zzm = new JSONObject(this.zzl);
                return;
            } catch (JSONException e) {
                this.zzm = null;
                this.zzl = null;
                return;
            }
        }
        this.zzm = null;
    }

    @TargetApi(19)
    public static TextTrackStyle fromSystemSettings(Context context) {
        TextTrackStyle textTrackStyle = new TextTrackStyle();
        if (!zzs.zze()) {
            return textTrackStyle;
        }
        CaptioningManager captioningManager = (CaptioningManager) context.getSystemService("captioning");
        textTrackStyle.setFontScale(captioningManager.getFontScale());
        CaptionStyle userStyle = captioningManager.getUserStyle();
        textTrackStyle.setBackgroundColor(userStyle.backgroundColor);
        textTrackStyle.setForegroundColor(userStyle.foregroundColor);
        switch (userStyle.edgeType) {
            case 1:
                textTrackStyle.setEdgeType(1);
                break;
            case 2:
                textTrackStyle.setEdgeType(2);
                break;
            default:
                textTrackStyle.setEdgeType(0);
                break;
        }
        textTrackStyle.setEdgeColor(userStyle.edgeColor);
        Typeface typeface = userStyle.getTypeface();
        if (typeface != null) {
            if (Typeface.MONOSPACE.equals(typeface)) {
                textTrackStyle.setFontGenericFamily(1);
            } else if (Typeface.SANS_SERIF.equals(typeface) || !Typeface.SERIF.equals(typeface)) {
                textTrackStyle.setFontGenericFamily(0);
            } else {
                textTrackStyle.setFontGenericFamily(2);
            }
            boolean isBold = typeface.isBold();
            boolean isItalic = typeface.isItalic();
            if (isBold && isItalic) {
                textTrackStyle.setFontStyle(3);
                return textTrackStyle;
            } else if (isBold) {
                textTrackStyle.setFontStyle(1);
                return textTrackStyle;
            } else if (isItalic) {
                textTrackStyle.setFontStyle(2);
                return textTrackStyle;
            } else {
                textTrackStyle.setFontStyle(0);
            }
        }
        return textTrackStyle;
    }

    private static int zza(String str) {
        if (str != null && str.length() == 9 && str.charAt(0) == '#') {
            try {
                return Color.argb(Integer.parseInt(str.substring(7, 9), 16), Integer.parseInt(str.substring(1, 3), 16), Integer.parseInt(str.substring(3, 5), 16), Integer.parseInt(str.substring(5, 7), 16));
            } catch (NumberFormatException e) {
            }
        }
        return 0;
    }

    private static String zza(int i) {
        return String.format("#%02X%02X%02X%02X", new Object[]{Integer.valueOf(Color.red(i)), Integer.valueOf(Color.green(i)), Integer.valueOf(Color.blue(i)), Integer.valueOf(Color.alpha(i))});
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextTrackStyle)) {
            return false;
        }
        TextTrackStyle textTrackStyle = (TextTrackStyle) obj;
        return (this.zzm == null ? 1 : null) != (textTrackStyle.zzm == null ? 1 : null) ? false : (this.zzm == null || textTrackStyle.zzm == null || zzq.zza(this.zzm, textTrackStyle.zzm)) && this.zza == textTrackStyle.zza && this.zzb == textTrackStyle.zzb && this.zzc == textTrackStyle.zzc && this.zzd == textTrackStyle.zzd && this.zze == textTrackStyle.zze && this.zzf == textTrackStyle.zzf && this.zzh == textTrackStyle.zzh && zzbdw.zza(this.zzi, textTrackStyle.zzi) && this.zzj == textTrackStyle.zzj && this.zzk == textTrackStyle.zzk;
    }

    public final int getBackgroundColor() {
        return this.zzc;
    }

    public final JSONObject getCustomData() {
        return this.zzm;
    }

    public final int getEdgeColor() {
        return this.zze;
    }

    public final int getEdgeType() {
        return this.zzd;
    }

    public final String getFontFamily() {
        return this.zzi;
    }

    public final int getFontGenericFamily() {
        return this.zzj;
    }

    public final float getFontScale() {
        return this.zza;
    }

    public final int getFontStyle() {
        return this.zzk;
    }

    public final int getForegroundColor() {
        return this.zzb;
    }

    public final int getWindowColor() {
        return this.zzg;
    }

    public final int getWindowCornerRadius() {
        return this.zzh;
    }

    public final int getWindowType() {
        return this.zzf;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.zza), Integer.valueOf(this.zzb), Integer.valueOf(this.zzc), Integer.valueOf(this.zzd), Integer.valueOf(this.zze), Integer.valueOf(this.zzf), Integer.valueOf(this.zzg), Integer.valueOf(this.zzh), this.zzi, Integer.valueOf(this.zzj), Integer.valueOf(this.zzk), String.valueOf(this.zzm)});
    }

    public final void setBackgroundColor(int i) {
        this.zzc = i;
    }

    public final void setCustomData(JSONObject jSONObject) {
        this.zzm = jSONObject;
    }

    public final void setEdgeColor(int i) {
        this.zze = i;
    }

    public final void setEdgeType(int i) {
        if (i >= 0) {
            if (i <= 4) {
                this.zzd = i;
                return;
            }
        }
        throw new IllegalArgumentException("invalid edgeType");
    }

    public final void setFontFamily(String str) {
        this.zzi = str;
    }

    public final void setFontGenericFamily(int i) {
        if (i >= 0) {
            if (i <= 6) {
                this.zzj = i;
                return;
            }
        }
        throw new IllegalArgumentException("invalid fontGenericFamily");
    }

    public final void setFontScale(float f) {
        this.zza = f;
    }

    public final void setFontStyle(int i) {
        if (i >= 0) {
            if (i <= 3) {
                this.zzk = i;
                return;
            }
        }
        throw new IllegalArgumentException("invalid fontStyle");
    }

    public final void setForegroundColor(int i) {
        this.zzb = i;
    }

    public final void setWindowColor(int i) {
        this.zzg = i;
    }

    public final void setWindowCornerRadius(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("invalid windowCornerRadius");
        }
        this.zzh = i;
    }

    public final void setWindowType(int i) {
        if (i >= 0) {
            if (i <= 2) {
                this.zzf = i;
                return;
            }
        }
        throw new IllegalArgumentException("invalid windowType");
    }

    public final void writeToParcel(Parcel parcel, int i) {
        this.zzl = this.zzm == null ? null : this.zzm.toString();
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, getFontScale());
        zzbgo.zza(parcel, 3, getForegroundColor());
        zzbgo.zza(parcel, 4, getBackgroundColor());
        zzbgo.zza(parcel, 5, getEdgeType());
        zzbgo.zza(parcel, 6, getEdgeColor());
        zzbgo.zza(parcel, 7, getWindowType());
        zzbgo.zza(parcel, 8, getWindowColor());
        zzbgo.zza(parcel, 9, getWindowCornerRadius());
        zzbgo.zza(parcel, 10, getFontFamily(), false);
        zzbgo.zza(parcel, 11, getFontGenericFamily());
        zzbgo.zza(parcel, 12, getFontStyle());
        zzbgo.zza(parcel, 13, this.zzl, false);
        zzbgo.zza(parcel, i);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @com.google.android.gms.common.internal.Hide
    public final org.json.JSONObject zza() {
        /*
        r4 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = "fontScale";
        r2 = r4.zza;	 Catch:{ JSONException -> 0x00ef }
        r2 = (double) r2;	 Catch:{ JSONException -> 0x00ef }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00ef }
        r1 = r4.zzb;	 Catch:{ JSONException -> 0x00ef }
        if (r1 == 0) goto L_0x001c;
    L_0x0011:
        r1 = "foregroundColor";
        r2 = r4.zzb;	 Catch:{ JSONException -> 0x00ef }
        r2 = zza(r2);	 Catch:{ JSONException -> 0x00ef }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00ef }
    L_0x001c:
        r1 = r4.zzc;	 Catch:{ JSONException -> 0x00ef }
        if (r1 == 0) goto L_0x002b;
    L_0x0020:
        r1 = "backgroundColor";
        r2 = r4.zzc;	 Catch:{ JSONException -> 0x00ef }
        r2 = zza(r2);	 Catch:{ JSONException -> 0x00ef }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00ef }
    L_0x002b:
        r1 = r4.zzd;	 Catch:{ JSONException -> 0x00ef }
        switch(r1) {
            case 0: goto L_0x0048;
            case 1: goto L_0x0043;
            case 2: goto L_0x003e;
            case 3: goto L_0x0039;
            case 4: goto L_0x0031;
            default: goto L_0x0030;
        };	 Catch:{ JSONException -> 0x00ef }
    L_0x0030:
        goto L_0x004d;
    L_0x0031:
        r1 = "edgeType";
        r2 = "DEPRESSED";
    L_0x0035:
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00ef }
        goto L_0x004d;
    L_0x0039:
        r1 = "edgeType";
        r2 = "RAISED";
        goto L_0x0035;
    L_0x003e:
        r1 = "edgeType";
        r2 = "DROP_SHADOW";
        goto L_0x0035;
    L_0x0043:
        r1 = "edgeType";
        r2 = "OUTLINE";
        goto L_0x0035;
    L_0x0048:
        r1 = "edgeType";
        r2 = "NONE";
        goto L_0x0035;
    L_0x004d:
        r1 = r4.zze;	 Catch:{ JSONException -> 0x00ef }
        if (r1 == 0) goto L_0x005c;
    L_0x0051:
        r1 = "edgeColor";
        r2 = r4.zze;	 Catch:{ JSONException -> 0x00ef }
        r2 = zza(r2);	 Catch:{ JSONException -> 0x00ef }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00ef }
    L_0x005c:
        r1 = r4.zzf;	 Catch:{ JSONException -> 0x00ef }
        switch(r1) {
            case 0: goto L_0x006f;
            case 1: goto L_0x006a;
            case 2: goto L_0x0062;
            default: goto L_0x0061;
        };	 Catch:{ JSONException -> 0x00ef }
    L_0x0061:
        goto L_0x0074;
    L_0x0062:
        r1 = "windowType";
        r2 = "ROUNDED_CORNERS";
    L_0x0066:
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00ef }
        goto L_0x0074;
    L_0x006a:
        r1 = "windowType";
        r2 = "NORMAL";
        goto L_0x0066;
    L_0x006f:
        r1 = "windowType";
        r2 = "NONE";
        goto L_0x0066;
    L_0x0074:
        r1 = r4.zzg;	 Catch:{ JSONException -> 0x00ef }
        if (r1 == 0) goto L_0x0083;
    L_0x0078:
        r1 = "windowColor";
        r2 = r4.zzg;	 Catch:{ JSONException -> 0x00ef }
        r2 = zza(r2);	 Catch:{ JSONException -> 0x00ef }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00ef }
    L_0x0083:
        r1 = r4.zzf;	 Catch:{ JSONException -> 0x00ef }
        r2 = 2;
        if (r1 != r2) goto L_0x008f;
    L_0x0088:
        r1 = "windowRoundedCornerRadius";
        r2 = r4.zzh;	 Catch:{ JSONException -> 0x00ef }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00ef }
    L_0x008f:
        r1 = r4.zzi;	 Catch:{ JSONException -> 0x00ef }
        if (r1 == 0) goto L_0x009a;
    L_0x0093:
        r1 = "fontFamily";
        r2 = r4.zzi;	 Catch:{ JSONException -> 0x00ef }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00ef }
    L_0x009a:
        r1 = r4.zzj;	 Catch:{ JSONException -> 0x00ef }
        switch(r1) {
            case 0: goto L_0x00c1;
            case 1: goto L_0x00bc;
            case 2: goto L_0x00b7;
            case 3: goto L_0x00b2;
            case 4: goto L_0x00ad;
            case 5: goto L_0x00a8;
            case 6: goto L_0x00a0;
            default: goto L_0x009f;
        };	 Catch:{ JSONException -> 0x00ef }
    L_0x009f:
        goto L_0x00c6;
    L_0x00a0:
        r1 = "fontGenericFamily";
        r2 = "SMALL_CAPITALS";
    L_0x00a4:
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00ef }
        goto L_0x00c6;
    L_0x00a8:
        r1 = "fontGenericFamily";
        r2 = "CURSIVE";
        goto L_0x00a4;
    L_0x00ad:
        r1 = "fontGenericFamily";
        r2 = "CASUAL";
        goto L_0x00a4;
    L_0x00b2:
        r1 = "fontGenericFamily";
        r2 = "MONOSPACED_SERIF";
        goto L_0x00a4;
    L_0x00b7:
        r1 = "fontGenericFamily";
        r2 = "SERIF";
        goto L_0x00a4;
    L_0x00bc:
        r1 = "fontGenericFamily";
        r2 = "MONOSPACED_SANS_SERIF";
        goto L_0x00a4;
    L_0x00c1:
        r1 = "fontGenericFamily";
        r2 = "SANS_SERIF";
        goto L_0x00a4;
    L_0x00c6:
        r1 = r4.zzk;	 Catch:{ JSONException -> 0x00ef }
        switch(r1) {
            case 0: goto L_0x00de;
            case 1: goto L_0x00d9;
            case 2: goto L_0x00d4;
            case 3: goto L_0x00cc;
            default: goto L_0x00cb;
        };	 Catch:{ JSONException -> 0x00ef }
    L_0x00cb:
        goto L_0x00e3;
    L_0x00cc:
        r1 = "fontStyle";
        r2 = "BOLD_ITALIC";
    L_0x00d0:
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00ef }
        goto L_0x00e3;
    L_0x00d4:
        r1 = "fontStyle";
        r2 = "ITALIC";
        goto L_0x00d0;
    L_0x00d9:
        r1 = "fontStyle";
        r2 = "BOLD";
        goto L_0x00d0;
    L_0x00de:
        r1 = "fontStyle";
        r2 = "NORMAL";
        goto L_0x00d0;
    L_0x00e3:
        r1 = r4.zzm;	 Catch:{ JSONException -> 0x00ef }
        if (r1 == 0) goto L_0x00f0;
    L_0x00e7:
        r1 = "customData";
        r2 = r4.zzm;	 Catch:{ JSONException -> 0x00ef }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00ef }
        return r0;
    L_0x00ef:
        r1 = move-exception;
    L_0x00f0:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.TextTrackStyle.zza():org.json.JSONObject");
    }

    @Hide
    public final void zza(JSONObject jSONObject) throws JSONException {
        String string;
        this.zza = (float) jSONObject.optDouble("fontScale", 1.0d);
        this.zzb = zza(jSONObject.optString("foregroundColor"));
        this.zzc = zza(jSONObject.optString("backgroundColor"));
        if (jSONObject.has("edgeType")) {
            string = jSONObject.getString("edgeType");
            if ("NONE".equals(string)) {
                this.zzd = 0;
            } else if ("OUTLINE".equals(string)) {
                this.zzd = 1;
            } else if ("DROP_SHADOW".equals(string)) {
                this.zzd = 2;
            } else if ("RAISED".equals(string)) {
                this.zzd = 3;
            } else if ("DEPRESSED".equals(string)) {
                this.zzd = 4;
            }
        }
        this.zze = zza(jSONObject.optString("edgeColor"));
        if (jSONObject.has("windowType")) {
            string = jSONObject.getString("windowType");
            if ("NONE".equals(string)) {
                this.zzf = 0;
            } else if ("NORMAL".equals(string)) {
                this.zzf = 1;
            } else if ("ROUNDED_CORNERS".equals(string)) {
                this.zzf = 2;
            }
        }
        this.zzg = zza(jSONObject.optString("windowColor"));
        if (this.zzf == 2) {
            this.zzh = jSONObject.optInt("windowRoundedCornerRadius", 0);
        }
        this.zzi = jSONObject.optString("fontFamily", null);
        if (jSONObject.has("fontGenericFamily")) {
            string = jSONObject.getString("fontGenericFamily");
            if ("SANS_SERIF".equals(string)) {
                this.zzj = 0;
            } else if ("MONOSPACED_SANS_SERIF".equals(string)) {
                this.zzj = 1;
            } else if ("SERIF".equals(string)) {
                this.zzj = 2;
            } else if ("MONOSPACED_SERIF".equals(string)) {
                this.zzj = 3;
            } else if ("CASUAL".equals(string)) {
                this.zzj = 4;
            } else {
                int i;
                if ("CURSIVE".equals(string)) {
                    i = 5;
                } else if ("SMALL_CAPITALS".equals(string)) {
                    i = 6;
                }
                this.zzj = i;
            }
        }
        if (jSONObject.has("fontStyle")) {
            string = jSONObject.getString("fontStyle");
            if ("NORMAL".equals(string)) {
                this.zzk = 0;
            } else if ("BOLD".equals(string)) {
                this.zzk = 1;
            } else if ("ITALIC".equals(string)) {
                this.zzk = 2;
            } else if ("BOLD_ITALIC".equals(string)) {
                this.zzk = 3;
            }
        }
        this.zzm = jSONObject.optJSONObject("customData");
    }
}
