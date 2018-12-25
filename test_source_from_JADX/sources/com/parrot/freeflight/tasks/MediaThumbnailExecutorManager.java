package com.parrot.freeflight.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.Log;
import android.widget.ImageView;
import com.parrot.freeflight.utils.ImageUtils;
import com.parrot.freeflight.utils.ThumbnailUtils;
import com.parrot.freeflight.vo.MediaVO;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MediaThumbnailExecutorManager {
    private ThumbnailWorkerTaskDelegate delegate;
    private final ExecutorService execture = Executors.newSingleThreadExecutor();
    private Handler handler;

    private final class OnThumbnailReadyMessage implements Runnable {
        public String key;
        public BitmapDrawable thumbnail;
        public ImageView view;

        public OnThumbnailReadyMessage(ImageView view, String key, BitmapDrawable thumbnail) {
            this.thumbnail = thumbnail;
            this.key = key;
            this.view = view;
        }

        public void run() {
            MediaThumbnailExecutorManager.this.onThumbnailReady(this.view, this.key, this.thumbnail);
        }
    }

    public MediaThumbnailExecutorManager(Context context, ThumbnailWorkerTaskDelegate delegate) {
        this.delegate = delegate;
        this.handler = new Handler();
    }

    public final void execute(MediaVO media, ImageView imageView) {
        this.execture.execute(getThumbnailRunnable(media, imageView));
    }

    public final void stop() {
        this.execture.shutdownNow();
    }

    private Runnable getThumbnailRunnable(final MediaVO media, final ImageView imageView) {
        return new Runnable() {
            public void run() {
                Bitmap bitmap;
                Context context = imageView.getContext();
                if (media.isVideo()) {
                    bitmap = Thumbnails.getThumbnail(context.getContentResolver(), (long) media.getId(), 3, null);
                } else {
                    bitmap = Images.Thumbnails.getThumbnail(context.getContentResolver(), (long) media.getId(), 3, null);
                }
                if (bitmap == null) {
                    if (media.isVideo()) {
                        bitmap = ThumbnailUtils.createVideoThumbnail(media.getPath(), 3);
                    } else {
                        bitmap = ImageUtils.decodeBitmapFromFile(media.getPath(), imageView.getWidth(), imageView.getHeight());
                    }
                }
                if (bitmap != null) {
                    MediaThumbnailExecutorManager.this.handler.post(new OnThumbnailReadyMessage(imageView, media.getKey(), new BitmapDrawable(context.getResources(), bitmap)));
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder("Can't load thumbnail for file ");
                stringBuilder.append(media.getPath());
                Log.w("ThumbnailWorker", stringBuilder.toString());
            }
        };
    }

    protected void onThumbnailReady(ImageView view, String key, BitmapDrawable thumbnail) {
        if (this.delegate != null) {
            this.delegate.onThumbnailReady(view, key, thumbnail);
        }
    }
}
