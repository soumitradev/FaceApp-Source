package com.google.android.gms.cast;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CastDevice extends zzbgl implements ReflectedParcelable {
    public static final int CAPABILITY_AUDIO_IN = 8;
    public static final int CAPABILITY_AUDIO_OUT = 4;
    public static final int CAPABILITY_MULTIZONE_GROUP = 32;
    public static final int CAPABILITY_VIDEO_IN = 2;
    public static final int CAPABILITY_VIDEO_OUT = 1;
    @Hide
    public static final Creator<CastDevice> CREATOR = new zzn();
    private String zza;
    private String zzb;
    private Inet4Address zzc;
    private String zzd;
    private String zze;
    private String zzf;
    private int zzg;
    private List<WebImage> zzh;
    private int zzi;
    private int zzj;
    private String zzk;
    private String zzl;
    private int zzm;
    private String zzn;
    private byte[] zzo;

    CastDevice(String str, String str2, String str3, String str4, String str5, int i, List<WebImage> list, int i2, int i3, String str6, String str7, int i4, String str8, byte[] bArr) {
        this.zza = zza(str);
        this.zzb = zza(str2);
        if (!TextUtils.isEmpty(this.zzb)) {
            try {
                InetAddress byName = InetAddress.getByName(r1.zzb);
                if (byName instanceof Inet4Address) {
                    r1.zzc = (Inet4Address) byName;
                }
            } catch (UnknownHostException e) {
                UnknownHostException unknownHostException = e;
                String str9 = r1.zzb;
                String message = unknownHostException.getMessage();
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str9).length() + 48) + String.valueOf(message).length());
                stringBuilder.append("Unable to convert host address (");
                stringBuilder.append(str9);
                stringBuilder.append(") to ipaddress: ");
                stringBuilder.append(message);
                Log.i("CastDevice", stringBuilder.toString());
            }
        }
        r1.zzd = zza(str3);
        r1.zze = zza(str4);
        r1.zzf = zza(str5);
        r1.zzg = i;
        r1.zzh = list != null ? list : new ArrayList();
        r1.zzi = i2;
        r1.zzj = i3;
        r1.zzk = zza(str6);
        r1.zzl = str7;
        r1.zzm = i4;
        r1.zzn = str8;
        r1.zzo = bArr;
    }

    public static CastDevice getFromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        bundle.setClassLoader(CastDevice.class.getClassLoader());
        return (CastDevice) bundle.getParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE");
    }

    private static String zza(String str) {
        return str == null ? "" : str;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CastDevice)) {
            return false;
        }
        CastDevice castDevice = (CastDevice) obj;
        return this.zza == null ? castDevice.zza == null : zzbdw.zza(this.zza, castDevice.zza) && zzbdw.zza(this.zzc, castDevice.zzc) && zzbdw.zza(this.zze, castDevice.zze) && zzbdw.zza(this.zzd, castDevice.zzd) && zzbdw.zza(this.zzf, castDevice.zzf) && this.zzg == castDevice.zzg && zzbdw.zza(this.zzh, castDevice.zzh) && this.zzi == castDevice.zzi && this.zzj == castDevice.zzj && zzbdw.zza(this.zzk, castDevice.zzk) && zzbdw.zza(Integer.valueOf(this.zzm), Integer.valueOf(castDevice.zzm)) && zzbdw.zza(this.zzn, castDevice.zzn) && zzbdw.zza(this.zzl, castDevice.zzl) && zzbdw.zza(this.zzf, castDevice.getDeviceVersion()) && this.zzg == castDevice.getServicePort() && ((this.zzo == null && castDevice.zzo == null) || Arrays.equals(this.zzo, castDevice.zzo));
    }

    public String getDeviceId() {
        return this.zza.startsWith("__cast_nearby__") ? this.zza.substring(16) : this.zza;
    }

    public String getDeviceVersion() {
        return this.zzf;
    }

    public String getFriendlyName() {
        return this.zzd;
    }

    public WebImage getIcon(int i, int i2) {
        WebImage webImage = null;
        if (this.zzh.isEmpty()) {
            return null;
        }
        if (i > 0) {
            if (i2 > 0) {
                WebImage webImage2 = null;
                for (WebImage webImage3 : this.zzh) {
                    int width = webImage3.getWidth();
                    int height = webImage3.getHeight();
                    if (width < i || height < i2) {
                        if (width < i && height < i2) {
                            if (webImage2 == null || (webImage2.getWidth() < width && webImage2.getHeight() < height)) {
                                webImage2 = webImage3;
                            }
                        }
                    } else if (webImage == null || (webImage.getWidth() > width && webImage.getHeight() > height)) {
                        webImage = webImage3;
                    }
                }
                return webImage != null ? webImage : webImage2 != null ? webImage2 : (WebImage) this.zzh.get(0);
            }
        }
        return (WebImage) this.zzh.get(0);
    }

    public List<WebImage> getIcons() {
        return Collections.unmodifiableList(this.zzh);
    }

    public Inet4Address getIpAddress() {
        return this.zzc;
    }

    public String getModelName() {
        return this.zze;
    }

    public int getServicePort() {
        return this.zzg;
    }

    public boolean hasCapabilities(int[] iArr) {
        if (iArr == null) {
            return false;
        }
        for (int hasCapability : iArr) {
            if (!hasCapability(hasCapability)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasCapability(int i) {
        return (this.zzi & i) == i;
    }

    public boolean hasIcons() {
        return !this.zzh.isEmpty();
    }

    public int hashCode() {
        return this.zza == null ? 0 : this.zza.hashCode();
    }

    public boolean isOnLocalNetwork() {
        return !this.zza.startsWith("__cast_nearby__");
    }

    public boolean isSameDevice(CastDevice castDevice) {
        if (castDevice == null) {
            return false;
        }
        Object deviceId;
        Object deviceId2;
        if (!TextUtils.isEmpty(getDeviceId()) && !getDeviceId().startsWith("__cast_ble__") && !TextUtils.isEmpty(castDevice.getDeviceId()) && !castDevice.getDeviceId().startsWith("__cast_ble__")) {
            deviceId = getDeviceId();
            deviceId2 = castDevice.getDeviceId();
        } else if (TextUtils.isEmpty(this.zzn) || TextUtils.isEmpty(castDevice.zzn)) {
            return false;
        } else {
            deviceId = this.zzn;
            deviceId2 = castDevice.zzn;
        }
        return zzbdw.zza(deviceId, deviceId2);
    }

    public void putInBundle(Bundle bundle) {
        if (bundle != null) {
            bundle.putParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE", this);
        }
    }

    public String toString() {
        return String.format("\"%s\" (%s)", new Object[]{this.zzd, this.zza});
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, this.zza, false);
        zzbgo.zza(parcel, 3, this.zzb, false);
        zzbgo.zza(parcel, 4, getFriendlyName(), false);
        zzbgo.zza(parcel, 5, getModelName(), false);
        zzbgo.zza(parcel, 6, getDeviceVersion(), false);
        zzbgo.zza(parcel, 7, getServicePort());
        zzbgo.zzc(parcel, 8, getIcons(), false);
        zzbgo.zza(parcel, 9, this.zzi);
        zzbgo.zza(parcel, 10, this.zzj);
        zzbgo.zza(parcel, 11, this.zzk, false);
        zzbgo.zza(parcel, 12, this.zzl, false);
        zzbgo.zza(parcel, 13, this.zzm);
        zzbgo.zza(parcel, 14, this.zzn, false);
        zzbgo.zza(parcel, 15, this.zzo, false);
        zzbgo.zza(parcel, i);
    }
}
