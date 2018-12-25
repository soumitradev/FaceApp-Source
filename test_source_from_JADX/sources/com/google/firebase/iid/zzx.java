package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.iid.zzi.zza;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class zzx {
    private static int zza = 0;
    private static PendingIntent zzb;
    private final SimpleArrayMap<String, TaskCompletionSource<Bundle>> zzc = new SimpleArrayMap();
    private final Context zzd;
    private final zzw zze;
    private Messenger zzf;
    private Messenger zzg;
    private zzi zzh;

    public zzx(Context context, zzw zzw) {
        this.zzd = context;
        this.zze = zzw;
        this.zzf = new Messenger(new zzy(this, Looper.getMainLooper()));
    }

    private static synchronized String zza() {
        String num;
        synchronized (zzx.class) {
            int i = zza;
            zza = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    @Hide
    private static synchronized void zza(Context context, Intent intent) {
        synchronized (zzx.class) {
            if (zzb == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                zzb = PendingIntent.getBroadcast(context, 0, intent2, 0);
            }
            intent.putExtra(SettingsJsonConstants.APP_KEY, zzb);
        }
    }

    private final void zza(Intent intent) {
        String stringExtra = intent.getStringExtra("error");
        if (stringExtra == null) {
            String valueOf = String.valueOf(intent.getExtras());
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 49);
            stringBuilder.append("Unexpected response, no error or registration id ");
            stringBuilder.append(valueOf);
            Log.w("FirebaseInstanceId", stringBuilder.toString());
            return;
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String str = "FirebaseInstanceId";
            String str2 = "Received InstanceID error ";
            String valueOf2 = String.valueOf(stringExtra);
            Log.d(str, valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
        }
        if (stringExtra.startsWith("|")) {
            String[] split = stringExtra.split("\\|");
            if (split.length > 2) {
                if ("ID".equals(split[1])) {
                    stringExtra = split[2];
                    str = split[3];
                    if (str.startsWith(":")) {
                        str = str.substring(1);
                    }
                    zza(stringExtra, intent.putExtra("error", str).getExtras());
                    return;
                }
            }
            valueOf = "FirebaseInstanceId";
            str = "Unexpected structured response ";
            stringExtra = String.valueOf(stringExtra);
            Log.w(valueOf, stringExtra.length() != 0 ? str.concat(stringExtra) : new String(str));
            return;
        }
        synchronized (this.zzc) {
            for (int i = 0; i < this.zzc.size(); i++) {
                zza((String) this.zzc.keyAt(i), intent.getExtras());
            }
        }
    }

    private final void zza(Message message) {
        if (message == null || !(message.obj instanceof Intent)) {
            Log.w("FirebaseInstanceId", "Dropping invalid message");
            return;
        }
        Intent intent = (Intent) message.obj;
        intent.setExtrasClassLoader(new zza());
        if (intent.hasExtra("google.messenger")) {
            Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
            if (parcelableExtra instanceof zzi) {
                this.zzh = (zzi) parcelableExtra;
            }
            if (parcelableExtra instanceof Messenger) {
                this.zzg = (Messenger) parcelableExtra;
            }
        }
        Intent intent2 = (Intent) message.obj;
        String action = intent2.getAction();
        String group;
        if ("com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
            CharSequence stringExtra = intent2.getStringExtra("registration_id");
            if (stringExtra == null) {
                stringExtra = intent2.getStringExtra("unregistered");
            }
            if (stringExtra == null) {
                zza(intent2);
                return;
            }
            Matcher matcher = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.*)").matcher(stringExtra);
            if (matcher.matches()) {
                action = matcher.group(1);
                group = matcher.group(2);
                Bundle extras = intent2.getExtras();
                extras.putString("registration_id", group);
                zza(action, extras);
                return;
            }
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String str = "FirebaseInstanceId";
                group = "Unexpected response string: ";
                action = String.valueOf(stringExtra);
                Log.d(str, action.length() != 0 ? group.concat(action) : new String(group));
            }
            return;
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            str = "FirebaseInstanceId";
            group = "Unexpected response action: ";
            action = String.valueOf(action);
            Log.d(str, action.length() != 0 ? group.concat(action) : new String(group));
        }
    }

    private final void zza(String str, Bundle bundle) {
        synchronized (this.zzc) {
            TaskCompletionSource taskCompletionSource = (TaskCompletionSource) this.zzc.remove(str);
            if (taskCompletionSource == null) {
                String str2 = "FirebaseInstanceId";
                String str3 = "Missing callback for ";
                str = String.valueOf(str);
                Log.w(str2, str.length() != 0 ? str3.concat(str) : new String(str3));
                return;
            }
            taskCompletionSource.setResult(bundle);
        }
    }

    private final Bundle zzb(Bundle bundle) throws IOException {
        Bundle zzc = zzc(bundle);
        if (zzc == null || !zzc.containsKey("google.messenger")) {
            return zzc;
        }
        zzc = zzc(bundle);
        return (zzc == null || !zzc.containsKey("google.messenger")) ? zzc : null;
    }

    private final Bundle zzc(Bundle bundle) throws IOException {
        String zza = zza();
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        synchronized (this.zzc) {
            this.zzc.put(zza, taskCompletionSource);
        }
        if (this.zze.zza() == 0) {
            throw new IOException("MISSING_INSTANCEID_SERVICE");
        }
        Intent intent = new Intent();
        intent.setPackage("com.google.android.gms");
        intent.setAction(this.zze.zza() == 2 ? "com.google.iid.TOKEN_REQUEST" : "com.google.android.c2dm.intent.REGISTER");
        intent.putExtras(bundle);
        zza(this.zzd, intent);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(zza).length() + 5);
        stringBuilder.append("|ID|");
        stringBuilder.append(zza);
        stringBuilder.append("|");
        intent.putExtra("kid", stringBuilder.toString());
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String valueOf = String.valueOf(intent.getExtras());
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(valueOf).length() + 8);
            stringBuilder2.append("Sending ");
            stringBuilder2.append(valueOf);
            Log.d("FirebaseInstanceId", stringBuilder2.toString());
        }
        intent.putExtra("google.messenger", this.zzf);
        if (!(this.zzg == null && this.zzh == null)) {
            Message obtain = Message.obtain();
            obtain.obj = intent;
            try {
                if (this.zzg != null) {
                    this.zzg.send(obtain);
                } else {
                    this.zzh.zza(obtain);
                }
            } catch (RemoteException e) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    Log.d("FirebaseInstanceId", "Messenger failed, fallback to startService");
                }
            }
            bundle = (Bundle) Tasks.await(taskCompletionSource.getTask(), 30000, TimeUnit.MILLISECONDS);
            synchronized (this.zzc) {
                this.zzc.remove(zza);
            }
            return bundle;
        }
        if (this.zze.zza() == 2) {
            this.zzd.sendBroadcast(intent);
        } else {
            this.zzd.startService(intent);
        }
        try {
            bundle = (Bundle) Tasks.await(taskCompletionSource.getTask(), 30000, TimeUnit.MILLISECONDS);
            synchronized (this.zzc) {
                this.zzc.remove(zza);
            }
            return bundle;
        } catch (InterruptedException e2) {
            Log.w("FirebaseInstanceId", "No response");
            throw new IOException("TIMEOUT");
        } catch (Throwable e3) {
            throw new IOException(e3);
        } catch (Throwable th) {
            synchronized (this.zzc) {
                this.zzc.remove(zza);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final android.os.Bundle zza(android.os.Bundle r6) throws java.io.IOException {
        /*
        r5 = this;
        r0 = r5.zze;
        r0 = r0.zzd();
        r1 = 12000000; // 0xb71b00 float:1.6815582E-38 double:5.9287878E-317;
        if (r0 < r1) goto L_0x0067;
    L_0x000b:
        r0 = r5.zzd;
        r0 = com.google.firebase.iid.zzk.zza(r0);
        r1 = 1;
        r0 = r0.zzb(r1, r6);
        r0 = com.google.android.gms.tasks.Tasks.await(r0);	 Catch:{ InterruptedException -> 0x001d, InterruptedException -> 0x001d }
        r0 = (android.os.Bundle) r0;	 Catch:{ InterruptedException -> 0x001d, InterruptedException -> 0x001d }
        return r0;
    L_0x001d:
        r0 = move-exception;
        r1 = "FirebaseInstanceId";
        r2 = 3;
        r1 = android.util.Log.isLoggable(r1, r2);
        if (r1 == 0) goto L_0x004b;
    L_0x0027:
        r1 = "FirebaseInstanceId";
        r2 = java.lang.String.valueOf(r0);
        r3 = java.lang.String.valueOf(r2);
        r3 = r3.length();
        r3 = r3 + 22;
        r4 = new java.lang.StringBuilder;
        r4.<init>(r3);
        r3 = "Error making request: ";
        r4.append(r3);
        r4.append(r2);
        r2 = r4.toString();
        android.util.Log.d(r1, r2);
    L_0x004b:
        r1 = r0.getCause();
        r1 = r1 instanceof com.google.firebase.iid.zzu;
        if (r1 == 0) goto L_0x0065;
    L_0x0053:
        r0 = r0.getCause();
        r0 = (com.google.firebase.iid.zzu) r0;
        r0 = r0.zza();
        r1 = 4;
        if (r0 != r1) goto L_0x0065;
    L_0x0060:
        r6 = r5.zzb(r6);
        return r6;
    L_0x0065:
        r6 = 0;
        return r6;
    L_0x0067:
        r6 = r5.zzb(r6);
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzx.zza(android.os.Bundle):android.os.Bundle");
    }
}
