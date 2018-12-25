package com.parrot.freeflight.tasks;

import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

public interface ThumbnailWorkerTaskDelegate {
    void onThumbnailReady(ImageView imageView, String str, BitmapDrawable bitmapDrawable);
}
