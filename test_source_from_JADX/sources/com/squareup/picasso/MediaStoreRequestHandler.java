package com.squareup.picasso;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Thumbnails;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import com.squareup.picasso.RequestHandler.Result;
import java.io.IOException;

class MediaStoreRequestHandler extends ContentStreamRequestHandler {
    private static final String[] CONTENT_ORIENTATION = new String[]{"orientation"};

    enum PicassoKind {
        MICRO(3, 96, 96),
        MINI(1, 512, 384),
        FULL(2, -1, -1);
        
        final int androidKind;
        final int height;
        final int width;

        private PicassoKind(int androidKind, int width, int height) {
            this.androidKind = androidKind;
            this.width = width;
            this.height = height;
        }
    }

    MediaStoreRequestHandler(Context context) {
        super(context);
    }

    public boolean canHandleRequest(Request data) {
        Uri uri = data.uri;
        return FirebaseAnalytics$Param.CONTENT.equals(uri.getScheme()) && "media".equals(uri.getAuthority());
    }

    public Result load(Request request, int networkPolicy) throws IOException {
        Request request2 = request;
        ContentResolver contentResolver = this.context.getContentResolver();
        int exifOrientation = getExifOrientation(contentResolver, request2.uri);
        String mimeType = contentResolver.getType(request2.uri);
        boolean z = true;
        boolean z2 = mimeType != null && mimeType.startsWith("video/");
        boolean isVideo = z2;
        if (request.hasSize()) {
            PicassoKind picassoKind = getPicassoKind(request2.targetWidth, request2.targetHeight);
            if (!isVideo && picassoKind == PicassoKind.FULL) {
                return new Result(null, getInputStream(request), Picasso$LoadedFrom.DISK, exifOrientation);
            }
            Bitmap bitmap;
            long id = ContentUris.parseId(request2.uri);
            Options options = RequestHandler.createBitmapOptions(request);
            options.inJustDecodeBounds = true;
            long id2 = id;
            RequestHandler.calculateInSampleSize(request2.targetWidth, request2.targetHeight, picassoKind.width, picassoKind.height, options, request2);
            if (isVideo) {
                if (picassoKind != PicassoKind.FULL) {
                    z = picassoKind.androidKind;
                }
                bitmap = Thumbnails.getThumbnail(contentResolver, id2, z, options);
            } else {
                bitmap = Images.Thumbnails.getThumbnail(contentResolver, id2, picassoKind.androidKind, options);
            }
            if (bitmap != null) {
                return new Result(bitmap, null, Picasso$LoadedFrom.DISK, exifOrientation);
            }
        }
        return new Result(null, getInputStream(request), Picasso$LoadedFrom.DISK, exifOrientation);
    }

    static PicassoKind getPicassoKind(int targetWidth, int targetHeight) {
        if (targetWidth <= PicassoKind.MICRO.width && targetHeight <= PicassoKind.MICRO.height) {
            return PicassoKind.MICRO;
        }
        if (targetWidth > PicassoKind.MINI.width || targetHeight > PicassoKind.MINI.height) {
            return PicassoKind.FULL;
        }
        return PicassoKind.MINI;
    }

    static int getExifOrientation(ContentResolver contentResolver, Uri uri) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(uri, CONTENT_ORIENTATION, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int i = cursor.getInt(0);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return i;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return 0;
        } catch (RuntimeException e) {
            if (cursor != null) {
                cursor.close();
            }
            return 0;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }
}
