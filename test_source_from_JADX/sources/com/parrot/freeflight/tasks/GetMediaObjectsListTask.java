package com.parrot.freeflight.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.parrot.freeflight.utils.ARDroneMediaGallery;
import com.parrot.freeflight.vo.MediaVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetMediaObjectsListTask extends AsyncTask<Void, Void, List<MediaVO>> {
    private static final String TAG = GetMediaObjectsListTask.class.getSimpleName();
    private final MediaFilter filter;
    private final ARDroneMediaGallery gallery;

    public enum MediaFilter {
        IMAGES,
        VIDEOS,
        ALL
    }

    public GetMediaObjectsListTask(Context context, MediaFilter filter) {
        this.filter = filter;
        this.gallery = new ARDroneMediaGallery(context);
    }

    protected List<MediaVO> doInBackground(Void... params) {
        ArrayList<MediaVO> mediaList = new ArrayList();
        if (this.filter == MediaFilter.IMAGES) {
            mediaList.addAll(this.gallery.getMediaImageList());
        } else if (this.filter == MediaFilter.VIDEOS) {
            mediaList.addAll(this.gallery.getMediaVideoList());
        } else if (this.filter == MediaFilter.ALL) {
            mediaList.addAll(this.gallery.getMediaImageList());
            if (!isCancelled()) {
                mediaList.addAll(this.gallery.getMediaVideoList());
            }
            if (!isCancelled()) {
                Collections.sort(mediaList);
            }
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder("Total files in gallery ");
        stringBuilder.append(mediaList.size());
        Log.d(str, stringBuilder.toString());
        return mediaList;
    }
}
