package com.google.android.gms.internal;

import android.os.Process;
import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.BlockingQueue;

final class zzcki extends Thread {
    private final Object zza = new Object();
    private final BlockingQueue<zzckh<?>> zzb;
    private /* synthetic */ zzcke zzc;

    public zzcki(zzcke zzcke, String str, BlockingQueue<zzckh<?>> blockingQueue) {
        this.zzc = zzcke;
        zzbq.zza(str);
        zzbq.zza(blockingQueue);
        this.zzb = blockingQueue;
        setName(str);
    }

    private final void zza(InterruptedException interruptedException) {
        this.zzc.zzt().zzaa().zza(String.valueOf(getName()).concat(" was interrupted"), interruptedException);
    }

    public final void run() {
        Object obj = null;
        while (obj == null) {
            try {
                this.zzc.zzi.acquire();
                obj = 1;
            } catch (InterruptedException e) {
                zza(e);
            }
        }
        try {
            int threadPriority = Process.getThreadPriority(Process.myTid());
            while (true) {
                zzckh zzckh = (zzckh) this.zzb.poll();
                if (zzckh != null) {
                    Process.setThreadPriority(zzckh.zza ? threadPriority : 10);
                    zzckh.run();
                } else {
                    synchronized (this.zza) {
                        if (this.zzb.peek() == null && !this.zzc.zzj) {
                            try {
                                this.zza.wait(30000);
                            } catch (InterruptedException e2) {
                                zza(e2);
                            }
                        }
                    }
                    synchronized (this.zzc.zzh) {
                        if (this.zzb.peek() == null) {
                            break;
                        }
                    }
                }
            }
            synchronized (this.zzc.zzh) {
                this.zzc.zzi.release();
                this.zzc.zzh.notifyAll();
                if (this == this.zzc.zzb) {
                    this.zzc.zzb = null;
                } else if (this == this.zzc.zzc) {
                    this.zzc.zzc = null;
                } else {
                    this.zzc.zzt().zzy().zza("Current scheduler thread is neither worker nor network");
                }
            }
        } catch (Throwable th) {
            synchronized (this.zzc.zzh) {
                this.zzc.zzi.release();
                this.zzc.zzh.notifyAll();
                if (this == this.zzc.zzb) {
                    this.zzc.zzb = null;
                } else if (this == this.zzc.zzc) {
                    this.zzc.zzc = null;
                } else {
                    this.zzc.zzt().zzy().zza("Current scheduler thread is neither worker nor network");
                }
            }
        }
    }

    public final void zza() {
        synchronized (this.zza) {
            this.zza.notifyAll();
        }
    }
}
