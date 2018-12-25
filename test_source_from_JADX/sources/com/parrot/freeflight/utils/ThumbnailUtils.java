package com.parrot.freeflight.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build.VERSION;
import android.util.Log;

public class ThumbnailUtils {
    public static final int OPTIONS_RECYCLE_INPUT = 2;
    public static final int TARGET_SIZE_MICRO_THUMBNAIL = 96;
    public static final int TARGET_SIZE_MINI_THUMBNAIL = 320;

    @SuppressLint({"NewApi"})
    public static Bitmap createVideoThumbnail(String filePath, int kind) {
        RuntimeException ex;
        String str;
        StringBuilder stringBuilder;
        int max;
        float scale;
        Bitmap bitmap = null;
        if (VERSION.SDK_INT < 10) {
            return android.media.ThumbnailUtils.createVideoThumbnail(filePath, kind);
        }
        int width;
        int height;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime(0);
            try {
                retriever.release();
            } catch (RuntimeException e) {
                ex = e;
                str = "ThumbnailUtils";
                stringBuilder = new StringBuilder("MediaMetadataRetriever failed with exception: ");
                stringBuilder.append(ex);
                Log.w(str, stringBuilder.toString());
                if (bitmap != null) {
                    return null;
                }
                if (kind != 1) {
                    width = bitmap.getWidth();
                    height = bitmap.getHeight();
                    max = Math.max(width, height);
                    if (max > 512) {
                        scale = 512.0f / ((float) max);
                        bitmap = Bitmap.createScaledBitmap(bitmap, Math.round(((float) width) * scale), Math.round(((float) height) * scale), true);
                    }
                } else if (kind == 3) {
                    bitmap = android.media.ThumbnailUtils.extractThumbnail(bitmap, 96, 96, 2);
                }
                return bitmap;
            }
        } catch (IllegalArgumentException e2) {
            try {
                retriever.release();
            } catch (RuntimeException e3) {
                ex = e3;
                str = "ThumbnailUtils";
                stringBuilder = new StringBuilder("MediaMetadataRetriever failed with exception: ");
                stringBuilder.append(ex);
                Log.w(str, stringBuilder.toString());
                if (bitmap != null) {
                    return null;
                }
                if (kind != 1) {
                    width = bitmap.getWidth();
                    height = bitmap.getHeight();
                    max = Math.max(width, height);
                    if (max > 512) {
                        scale = 512.0f / ((float) max);
                        bitmap = Bitmap.createScaledBitmap(bitmap, Math.round(((float) width) * scale), Math.round(((float) height) * scale), true);
                    }
                } else if (kind == 3) {
                    bitmap = android.media.ThumbnailUtils.extractThumbnail(bitmap, 96, 96, 2);
                }
                return bitmap;
            }
        } catch (RuntimeException e4) {
            try {
                retriever.release();
            } catch (RuntimeException e5) {
                ex = e5;
                str = "ThumbnailUtils";
                stringBuilder = new StringBuilder("MediaMetadataRetriever failed with exception: ");
                stringBuilder.append(ex);
                Log.w(str, stringBuilder.toString());
                if (bitmap != null) {
                    return null;
                }
                if (kind != 1) {
                    width = bitmap.getWidth();
                    height = bitmap.getHeight();
                    max = Math.max(width, height);
                    if (max > 512) {
                        scale = 512.0f / ((float) max);
                        bitmap = Bitmap.createScaledBitmap(bitmap, Math.round(((float) width) * scale), Math.round(((float) height) * scale), true);
                    }
                } else if (kind == 3) {
                    bitmap = android.media.ThumbnailUtils.extractThumbnail(bitmap, 96, 96, 2);
                }
                return bitmap;
            }
        } catch (Throwable th) {
            try {
                retriever.release();
            } catch (RuntimeException ex2) {
                StringBuilder stringBuilder2 = new StringBuilder("MediaMetadataRetriever failed with exception: ");
                stringBuilder2.append(ex2);
                Log.w("ThumbnailUtils", stringBuilder2.toString());
            }
            throw th;
        }
        if (bitmap != null) {
            return null;
        }
        if (kind != 1) {
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            max = Math.max(width, height);
            if (max > 512) {
                scale = 512.0f / ((float) max);
                bitmap = Bitmap.createScaledBitmap(bitmap, Math.round(((float) width) * scale), Math.round(((float) height) * scale), true);
            }
        } else if (kind == 3) {
            bitmap = android.media.ThumbnailUtils.extractThumbnail(bitmap, 96, 96, 2);
        }
        return bitmap;
    }

    public static Bitmap extractThumbnail(Bitmap source, int width, int height) {
        return android.media.ThumbnailUtils.extractThumbnail(source, 96, 96, 2);
    }

    public static Bitmap extractThumbnail(Bitmap source, int width, int height, int options) {
        return android.media.ThumbnailUtils.extractThumbnail(source, 96, 96, options);
    }
}
