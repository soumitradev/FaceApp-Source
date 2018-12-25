package com.parrot.freeflight.tasks;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.provider.MediaStore.Video.Thumbnails;
import android.widget.ImageView;
import com.parrot.freeflight.utils.ImageUtils;
import com.parrot.freeflight.utils.ThumbnailUtils;
import com.parrot.freeflight.vo.MediaVO;

public class LoadMediaThumbTask extends AsyncTask<Void, Void, Drawable> {
    private final ImageView imageView;
    private final MediaVO media;
    private Resources res;

    public LoadMediaThumbTask(MediaVO media, ImageView imageView) {
        this.media = media;
        this.imageView = imageView;
        this.res = imageView.getContext().getResources();
    }

    protected Drawable doInBackground(Void... params) {
        Bitmap bitmap;
        ContentResolver contentResolver = this.imageView.getContext().getContentResolver();
        if (this.media.isVideo()) {
            bitmap = Thumbnails.getThumbnail(contentResolver, (long) this.media.getId(), 1, null);
            if (bitmap == null && this.media.isVideo()) {
                bitmap = ThumbnailUtils.createVideoThumbnail(this.media.getPath(), 1);
            }
        } else {
            bitmap = ImageUtils.decodeBitmapFromFile(this.media.getPath(), this.imageView.getWidth(), this.imageView.getHeight());
        }
        if (bitmap != null) {
            return new BitmapDrawable(this.res, bitmap);
        }
        return null;
    }

    protected void onPostExecute(Drawable result) {
        this.imageView.setImageDrawable(result);
    }
}
