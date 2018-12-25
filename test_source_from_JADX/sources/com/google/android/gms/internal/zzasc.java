package com.google.android.gms.internal;

import android.content.Context;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Hide
public final class zzasc extends zzari {
    private volatile String zza;
    private Future<String> zzb;

    protected zzasc(zzark zzark) {
        super(zzark);
    }

    private final String zza(Context context) {
        Object e;
        Throwable th;
        zzbq.zzc("ClientId should be loaded from worker thread");
        FileInputStream openFileInput;
        try {
            openFileInput = context.openFileInput("gaClientId");
            try {
                byte[] bArr = new byte[36];
                int read = openFileInput.read(bArr, 0, 36);
                if (openFileInput.available() > 0) {
                    zze("clientId file seems corrupted, deleting it.");
                    openFileInput.close();
                    context.deleteFile("gaClientId");
                    if (openFileInput != null) {
                        try {
                            openFileInput.close();
                            return null;
                        } catch (IOException e2) {
                            zze("Failed to close client id reading stream", e2);
                        }
                    }
                    return null;
                } else if (read < 14) {
                    zze("clientId file is empty, deleting it.");
                    openFileInput.close();
                    context.deleteFile("gaClientId");
                    if (openFileInput != null) {
                        try {
                            openFileInput.close();
                            return null;
                        } catch (IOException e22) {
                            zze("Failed to close client id reading stream", e22);
                        }
                    }
                    return null;
                } else {
                    openFileInput.close();
                    String str = new String(bArr, 0, read);
                    zza("Read client id from disk", str);
                    if (openFileInput != null) {
                        try {
                            openFileInput.close();
                            return str;
                        } catch (IOException e222) {
                            zze("Failed to close client id reading stream", e222);
                        }
                    }
                    return str;
                }
            } catch (FileNotFoundException e3) {
                if (openFileInput != null) {
                    try {
                        openFileInput.close();
                        return null;
                    } catch (IOException e2222) {
                        zze("Failed to close client id reading stream", e2222);
                        return null;
                    }
                }
                return null;
            } catch (IOException e4) {
                e = e4;
                try {
                    zze("Error reading client id file, deleting it", e);
                    context.deleteFile("gaClientId");
                    if (openFileInput != null) {
                        try {
                            openFileInput.close();
                            return null;
                        } catch (IOException e22222) {
                            zze("Failed to close client id reading stream", e22222);
                            return null;
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (openFileInput != null) {
                        try {
                            openFileInput.close();
                        } catch (IOException e5) {
                            zze("Failed to close client id reading stream", e5);
                        }
                    }
                    throw th;
                }
            }
        } catch (FileNotFoundException e6) {
            openFileInput = null;
            if (openFileInput != null) {
                openFileInput.close();
                return null;
            }
            return null;
        } catch (IOException e7) {
            e = e7;
            openFileInput = null;
            zze("Error reading client id file, deleting it", e);
            context.deleteFile("gaClientId");
            if (openFileInput != null) {
                openFileInput.close();
                return null;
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            openFileInput = null;
            if (openFileInput != null) {
                openFileInput.close();
            }
            throw th;
        }
    }

    private final boolean zza(Context context, String str) {
        Object e;
        Throwable th;
        zzbq.zza(str);
        zzbq.zzc("ClientId should be saved from worker thread");
        FileOutputStream fileOutputStream = null;
        try {
            zza("Storing clientId", str);
            FileOutputStream openFileOutput = context.openFileOutput("gaClientId", 0);
            try {
                openFileOutput.write(str.getBytes());
                if (openFileOutput != null) {
                    try {
                        openFileOutput.close();
                    } catch (IOException e2) {
                        zze("Failed to close clientId writing stream", e2);
                    }
                }
                return true;
            } catch (FileNotFoundException e3) {
                e = e3;
                fileOutputStream = openFileOutput;
                zze("Error creating clientId file", e);
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                        return false;
                    } catch (IOException e22) {
                        zze("Failed to close clientId writing stream", e22);
                        return false;
                    }
                }
                return false;
            } catch (IOException e4) {
                e = e4;
                fileOutputStream = openFileOutput;
                try {
                    zze("Error writing to clientId file", e);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                            return false;
                        } catch (IOException e222) {
                            zze("Failed to close clientId writing stream", e222);
                            return false;
                        }
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e2222) {
                            zze("Failed to close clientId writing stream", e2222);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = openFileOutput;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e5) {
            e = e5;
            zze("Error creating clientId file", e);
            if (fileOutputStream != null) {
                fileOutputStream.close();
                return false;
            }
            return false;
        } catch (IOException e6) {
            e = e6;
            zze("Error writing to clientId file", e);
            if (fileOutputStream != null) {
                fileOutputStream.close();
                return false;
            }
            return false;
        }
    }

    private final String zze() {
        String toLowerCase = UUID.randomUUID().toString().toLowerCase();
        try {
            if (!zza(zzn().zzc(), toLowerCase)) {
                toLowerCase = AppEventsConstants.EVENT_PARAM_VALUE_NO;
            }
            return toLowerCase;
        } catch (Exception e) {
            zze("Error saving clientId file", e);
            return AppEventsConstants.EVENT_PARAM_VALUE_NO;
        }
    }

    protected final void zza() {
    }

    public final String zzb() {
        String str;
        zzz();
        synchronized (this) {
            if (this.zza == null) {
                this.zzb = zzn().zza(new zzasd(this));
            }
            if (this.zzb != null) {
                try {
                    this.zza = (String) this.zzb.get();
                } catch (InterruptedException e) {
                    zzd("ClientId loading or generation was interrupted", e);
                    str = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                    this.zza = str;
                    if (this.zza == null) {
                        this.zza = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                    }
                    zza("Loaded clientId", this.zza);
                    this.zzb = null;
                    str = this.zza;
                    return str;
                } catch (ExecutionException e2) {
                    zze("Failed to load or generate client id", e2);
                    str = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                    this.zza = str;
                    if (this.zza == null) {
                        this.zza = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                    }
                    zza("Loaded clientId", this.zza);
                    this.zzb = null;
                    str = this.zza;
                    return str;
                }
                if (this.zza == null) {
                    this.zza = AppEventsConstants.EVENT_PARAM_VALUE_NO;
                }
                zza("Loaded clientId", this.zza);
                this.zzb = null;
            }
            str = this.zza;
        }
        return str;
    }

    final String zzc() {
        synchronized (this) {
            this.zza = null;
            this.zzb = zzn().zza(new zzase(this));
        }
        return zzb();
    }

    final String zzd() {
        String zza = zza(zzn().zzc());
        return zza == null ? zze() : zza;
    }
}
