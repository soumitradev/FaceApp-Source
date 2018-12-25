package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import com.google.android.gms.internal.zzbp;
import com.google.android.gms.internal.zzdkf;
import com.google.android.gms.internal.zzdkh;
import com.google.android.gms.internal.zzdkl;
import com.google.android.gms.internal.zzdkp;
import com.google.android.gms.internal.zzflr;
import com.google.android.gms.internal.zzfls;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

final class zzey implements zzah {
    private final Context zza;
    private final String zzb;
    private final ExecutorService zzc = Executors.newSingleThreadExecutor();
    private zzdi<zzdkf> zzd;

    zzey(Context context, String str) {
        this.zza = context;
        this.zzb = str;
    }

    private static zzdkl zza(ByteArrayOutputStream byteArrayOutputStream) {
        try {
            return zzdb.zza(byteArrayOutputStream.toString("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            zzdj.zzd("Failed to convert binary resource to string for JSON parsing; the file format is not UTF-8 format.");
            return null;
        } catch (JSONException e2) {
            zzdj.zzb("Failed to extract the container from the resource file. Resource is a UTF-8 encoded string but doesn't contain a JSON container");
            return null;
        }
    }

    private static zzdkl zza(byte[] bArr) {
        try {
            zzdkl zza = zzdkh.zza((zzbp) zzfls.zza(new zzbp(), bArr));
            if (zza != null) {
                zzdj.zze("The container was successfully loaded from the resource (using binary file)");
            }
            return zza;
        } catch (zzflr e) {
            zzdj.zza("The resource file is corrupted. The container cannot be extracted from the binary file");
            return null;
        } catch (zzdkp e2) {
            zzdj.zzb("The resource file is invalid. The container from the binary file is invalid");
            return null;
        }
    }

    private final File zzc() {
        String valueOf = String.valueOf("resource_");
        String valueOf2 = String.valueOf(this.zzb);
        return new File(this.zza.getDir("google_tagmanager", 0), valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    public final synchronized void release() {
        this.zzc.shutdown();
    }

    public final zzdkl zza(int i) {
        String stringBuilder;
        try {
            InputStream openRawResource = this.zza.getResources().openRawResource(i);
            String resourceName = this.zza.getResources().getResourceName(i);
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(resourceName).length() + 66);
            stringBuilder2.append("Attempting to load a container from the resource ID ");
            stringBuilder2.append(i);
            stringBuilder2.append(" (");
            stringBuilder2.append(resourceName);
            stringBuilder2.append(")");
            zzdj.zze(stringBuilder2.toString());
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                zzdkh.zza(openRawResource, byteArrayOutputStream);
                zzdkl zza = zza(byteArrayOutputStream);
                if (zza == null) {
                    return zza(byteArrayOutputStream.toByteArray());
                }
                zzdj.zze("The container was successfully loaded from the resource (using JSON file format)");
                return zza;
            } catch (IOException e) {
                String resourceName2 = this.zza.getResources().getResourceName(i);
                StringBuilder stringBuilder3 = new StringBuilder(String.valueOf(resourceName2).length() + 67);
                stringBuilder3.append("Error reading the default container with resource ID ");
                stringBuilder3.append(i);
                stringBuilder3.append(" (");
                stringBuilder3.append(resourceName2);
                stringBuilder3.append(")");
                stringBuilder = stringBuilder3.toString();
                zzdj.zzb(stringBuilder);
                return null;
            }
        } catch (NotFoundException e2) {
            StringBuilder stringBuilder4 = new StringBuilder(98);
            stringBuilder4.append("Failed to load the container. No default container resource found with the resource ID ");
            stringBuilder4.append(i);
            stringBuilder = stringBuilder4.toString();
            zzdj.zzb(stringBuilder);
            return null;
        }
    }

    public final void zza() {
        this.zzc.execute(new zzez(this));
    }

    public final void zza(zzdkf zzdkf) {
        this.zzc.execute(new zzfa(this, zzdkf));
    }

    public final void zza(zzdi<zzdkf> zzdi) {
        this.zzd = zzdi;
    }

    final void zzb() {
        if (this.zzd == null) {
            throw new IllegalStateException("Callback must be set before execute");
        }
        this.zzd.zza();
        zzdj.zze("Attempting to load resource from disk");
        if ((zzei.zza().zzb() == zza.CONTAINER || zzei.zza().zzb() == zza.CONTAINER_DEBUG) && this.zzb.equals(zzei.zza().zzd())) {
            this.zzd.zza(zzda.zzd);
            return;
        }
        try {
            InputStream fileInputStream = new FileInputStream(zzc());
            try {
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                zzdkh.zza(fileInputStream, byteArrayOutputStream);
                Object obj = (zzdkf) zzfls.zza(new zzdkf(), byteArrayOutputStream.toByteArray());
                if (obj.zzb == null && obj.zzc == null) {
                    throw new IllegalArgumentException("Resource and SupplementedResource are NULL.");
                }
                this.zzd.zza(obj);
                zzdj.zze("The Disk resource was successfully read.");
            } catch (IOException e) {
                this.zzd.zza(zzda.zze);
                zzdj.zzb("Failed to read the resource from disk");
                zzdj.zze("The Disk resource was successfully read.");
            } catch (IllegalArgumentException e2) {
                this.zzd.zza(zzda.zze);
                zzdj.zzb("Failed to read the resource from disk. The resource is inconsistent");
                zzdj.zze("The Disk resource was successfully read.");
            } finally {
                try {
                    fileInputStream.close();
                } catch (IOException e3) {
                    zzdj.zzb("Error closing stream for reading resource from disk");
                }
            }
        } catch (FileNotFoundException e4) {
            zzdj.zzd("Failed to find the resource in the disk");
            this.zzd.zza(zzda.zzd);
        }
    }

    final boolean zzb(zzdkf zzdkf) {
        File zzc = zzc();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zzc);
            try {
                fileOutputStream.write(zzfls.zza((zzfls) zzdkf));
                return true;
            } catch (IOException e) {
                zzdj.zzb("Error writing resource to disk. Removing resource from disk.");
                zzc.delete();
                return false;
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e2) {
                    zzdj.zzb("error closing stream for writing resource to disk");
                }
            }
        } catch (FileNotFoundException e3) {
            zzdj.zza("Error opening resource file for writing");
            return false;
        }
    }
}
