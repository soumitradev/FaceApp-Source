package com.google.android.gms.common.images;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgk;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {
    private static final Object zza = new Object();
    private static HashSet<Uri> zzb = new HashSet();
    private static ImageManager zzc;
    private final Context zzd;
    private final Handler zze = new Handler(Looper.getMainLooper());
    private final ExecutorService zzf = Executors.newFixedThreadPool(4);
    private final zza zzg = null;
    private final zzbgk zzh = new zzbgk();
    private final Map<zza, ImageReceiver> zzi = new HashMap();
    private final Map<Uri, ImageReceiver> zzj = new HashMap();
    private final Map<Uri, Long> zzk = new HashMap();

    @KeepName
    final class ImageReceiver extends ResultReceiver {
        private final Uri zza;
        private final ArrayList<zza> zzb = new ArrayList();
        private /* synthetic */ ImageManager zzc;

        ImageReceiver(ImageManager imageManager, Uri uri) {
            this.zzc = imageManager;
            super(new Handler(Looper.getMainLooper()));
            this.zza = uri;
        }

        public final void onReceiveResult(int i, Bundle bundle) {
            this.zzc.zzf.execute(new zzb(this.zzc, this.zza, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }

        public final void zza() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", this.zza);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            this.zzc.zzd.sendBroadcast(intent);
        }

        public final void zza(zza zza) {
            com.google.android.gms.common.internal.zzc.zza("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zzb.add(zza);
        }

        public final void zzb(zza zza) {
            com.google.android.gms.common.internal.zzc.zza("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zzb.remove(zza);
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    final class zzb implements Runnable {
        private final Uri zza;
        private final ParcelFileDescriptor zzb;
        private /* synthetic */ ImageManager zzc;

        public zzb(ImageManager imageManager, Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.zzc = imageManager;
            this.zza = uri;
            this.zzb = parcelFileDescriptor;
        }

        public final void run() {
            String str = "LoadBitmapFromDiskRunnable can't be executed in the main thread";
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                String valueOf = String.valueOf(Thread.currentThread());
                String valueOf2 = String.valueOf(Looper.getMainLooper().getThread());
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 56) + String.valueOf(valueOf2).length());
                stringBuilder.append("checkNotMainThread: current thread ");
                stringBuilder.append(valueOf);
                stringBuilder.append(" IS the main thread ");
                stringBuilder.append(valueOf2);
                stringBuilder.append("!");
                Log.e("Asserts", stringBuilder.toString());
                throw new IllegalStateException(str);
            }
            boolean z;
            Bitmap bitmap;
            boolean z2 = false;
            Bitmap bitmap2 = null;
            if (this.zzb != null) {
                try {
                    bitmap2 = BitmapFactory.decodeFileDescriptor(this.zzb.getFileDescriptor());
                } catch (Throwable e) {
                    String valueOf3 = String.valueOf(this.zza);
                    StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(valueOf3).length() + 34);
                    stringBuilder2.append("OOM while loading bitmap for uri: ");
                    stringBuilder2.append(valueOf3);
                    Log.e("ImageManager", stringBuilder2.toString(), e);
                    z2 = true;
                }
                try {
                    this.zzb.close();
                } catch (Throwable e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
                z = z2;
                bitmap = bitmap2;
            } else {
                bitmap = null;
                z = false;
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.zzc.zze.post(new zzd(this.zzc, this.zza, bitmap, z, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException e3) {
                String valueOf4 = String.valueOf(this.zza);
                StringBuilder stringBuilder3 = new StringBuilder(String.valueOf(valueOf4).length() + 32);
                stringBuilder3.append("Latch interrupted while posting ");
                stringBuilder3.append(valueOf4);
                Log.w("ImageManager", stringBuilder3.toString());
            }
        }
    }

    final class zzc implements Runnable {
        private final zza zza;
        private /* synthetic */ ImageManager zzb;

        public zzc(ImageManager imageManager, zza zza) {
            this.zzb = imageManager;
            this.zza = zza;
        }

        public final void run() {
            com.google.android.gms.common.internal.zzc.zza("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) this.zzb.zzi.get(this.zza);
            if (imageReceiver != null) {
                this.zzb.zzi.remove(this.zza);
                imageReceiver.zzb(this.zza);
            }
            zzb zzb = this.zza.zza;
            if (zzb.zza == null) {
                this.zza.zza(this.zzb.zzd, this.zzb.zzh, true);
                return;
            }
            Bitmap zza = this.zzb.zza(zzb);
            if (zza != null) {
                this.zza.zza(this.zzb.zzd, zza, true);
                return;
            }
            Long l = (Long) this.zzb.zzk.get(zzb.zza);
            if (l != null) {
                if (SystemClock.elapsedRealtime() - l.longValue() < 3600000) {
                    this.zza.zza(this.zzb.zzd, this.zzb.zzh, true);
                    return;
                }
                this.zzb.zzk.remove(zzb.zza);
            }
            this.zza.zza(this.zzb.zzd, this.zzb.zzh);
            ImageReceiver imageReceiver2 = (ImageReceiver) this.zzb.zzj.get(zzb.zza);
            if (imageReceiver2 == null) {
                imageReceiver2 = new ImageReceiver(this.zzb, zzb.zza);
                this.zzb.zzj.put(zzb.zza, imageReceiver2);
            }
            imageReceiver2.zza(this.zza);
            if (!(this.zza instanceof zzd)) {
                this.zzb.zzi.put(this.zza, imageReceiver2);
            }
            synchronized (ImageManager.zza) {
                if (!ImageManager.zzb.contains(zzb.zza)) {
                    ImageManager.zzb.add(zzb.zza);
                    imageReceiver2.zza();
                }
            }
        }
    }

    final class zzd implements Runnable {
        private final Uri zza;
        private final Bitmap zzb;
        private final CountDownLatch zzc;
        private boolean zzd;
        private /* synthetic */ ImageManager zze;

        public zzd(ImageManager imageManager, Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.zze = imageManager;
            this.zza = uri;
            this.zzb = bitmap;
            this.zzd = z;
            this.zzc = countDownLatch;
        }

        public final void run() {
            com.google.android.gms.common.internal.zzc.zza("OnBitmapLoadedRunnable must be executed in the main thread");
            Object obj = this.zzb != null ? 1 : null;
            if (this.zze.zzg != null) {
                if (this.zzd) {
                    this.zze.zzg.evictAll();
                    System.gc();
                    this.zzd = false;
                    this.zze.zze.post(this);
                    return;
                } else if (obj != null) {
                    this.zze.zzg.put(new zzb(this.zza), this.zzb);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) this.zze.zzj.remove(this.zza);
            if (imageReceiver != null) {
                ArrayList zza = imageReceiver.zzb;
                int size = zza.size();
                for (int i = 0; i < size; i++) {
                    zza zza2 = (zza) zza.get(i);
                    if (obj != null) {
                        zza2.zza(this.zze.zzd, this.zzb, false);
                    } else {
                        this.zze.zzk.put(this.zza, Long.valueOf(SystemClock.elapsedRealtime()));
                        zza2.zza(this.zze.zzd, this.zze.zzh, false);
                    }
                    if (!(zza2 instanceof zzd)) {
                        this.zze.zzi.remove(zza2);
                    }
                }
            }
            this.zzc.countDown();
            synchronized (ImageManager.zza) {
                ImageManager.zzb.remove(this.zza);
            }
        }
    }

    static final class zza extends LruCache<zzb, Bitmap> {
        protected final /* synthetic */ void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
            super.entryRemoved(z, (zzb) obj, (Bitmap) obj2, (Bitmap) obj3);
        }

        protected final /* synthetic */ int sizeOf(Object obj, Object obj2) {
            Bitmap bitmap = (Bitmap) obj2;
            return bitmap.getHeight() * bitmap.getRowBytes();
        }
    }

    private ImageManager(Context context, boolean z) {
        this.zzd = context.getApplicationContext();
    }

    public static ImageManager create(Context context) {
        if (zzc == null) {
            zzc = new ImageManager(context, false);
        }
        return zzc;
    }

    private final Bitmap zza(zzb zzb) {
        return this.zzg == null ? null : (Bitmap) this.zzg.get(zzb);
    }

    @Hide
    private final void zza(zza zza) {
        com.google.android.gms.common.internal.zzc.zza("ImageManager.loadImage() must be called in the main thread");
        new zzc(this, zza).run();
    }

    public final void loadImage(ImageView imageView, int i) {
        zza(new zzc(imageView, i));
    }

    public final void loadImage(ImageView imageView, Uri uri) {
        zza(new zzc(imageView, uri));
    }

    public final void loadImage(ImageView imageView, Uri uri, int i) {
        zza zzc = new zzc(imageView, uri);
        zzc.zzb = i;
        zza(zzc);
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        zza(new zzd(onImageLoadedListener, uri));
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri, int i) {
        zza zzd = new zzd(onImageLoadedListener, uri);
        zzd.zzb = i;
        zza(zzd);
    }
}
