package com.parrot.freeflight.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import android.util.Log;
import com.parrot.freeflight.vo.MediaVO;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ARDroneMediaGallery implements MediaScannerConnectionClient {
    private static final String TAG = ARDroneMediaGallery.class.getSimpleName();
    private MediaScannerConnection connection;
    private ContentResolver contentResolver;
    private List<String> mediaToScan = new Vector();

    public ARDroneMediaGallery(Context context) {
        this.contentResolver = context.getContentResolver();
        this.connection = new MediaScannerConnection(context, this);
    }

    @Deprecated
    public void scanMediaFile(File file) {
        scanMediaFile(file.getAbsolutePath());
    }

    @Deprecated
    public void scanMediaFile(String file) {
        if (this.connection.isConnected()) {
            this.connection.scanFile(file, null);
            return;
        }
        synchronized (this.mediaToScan) {
            this.mediaToScan.add(file);
        }
        this.connection.connect();
    }

    @SuppressLint({"NewApi"})
    public void insertMedia(File file) throws RuntimeException {
        String filename = file.getName();
        Uri uri;
        ContentValues values;
        if (filename.endsWith("jpg")) {
            if (FileUtils.isExtStorgAvailable()) {
                uri = Media.EXTERNAL_CONTENT_URI;
            } else {
                uri = Media.INTERNAL_CONTENT_URI;
            }
            values = new ContentValues();
            values.put("title", filename);
            values.put("_display_name", filename);
            values.put("bucket_display_name", FileUtils.MEDIA_PUBLIC_FOLDER_NAME);
            values.put("_data", file.getAbsolutePath());
            values.put("mime_type", "image/jpg");
            values.put("date_added", Long.valueOf(System.currentTimeMillis() / 1000));
            try {
                ExifInterface exif = new ExifInterface(file.getAbsolutePath());
                float[] latlon = new float[2];
                if (exif.getLatLong(latlon)) {
                    values.put("latitude", Float.valueOf(latlon[0]));
                    values.put("longitude", Float.valueOf(latlon[1]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.contentResolver.insert(uri, values);
        } else if (filename.endsWith("mp4")) {
            if (FileUtils.isExtStorgAvailable()) {
                uri = Video.Media.EXTERNAL_CONTENT_URI;
            } else {
                uri = Video.Media.INTERNAL_CONTENT_URI;
            }
            values = new ContentValues();
            values.put("_display_name", filename);
            values.put("title", filename);
            values.put("bucket_display_name", FileUtils.MEDIA_PUBLIC_FOLDER_NAME);
            values.put("_data", file.getAbsolutePath());
            values.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            values.put("date_added", Long.valueOf(System.currentTimeMillis()));
            values.put("mime_type", "video/mp4");
            values.put("isprivate", Integer.valueOf(0));
            values.put("artist", "");
            values.put("album", FileUtils.MEDIA_PUBLIC_FOLDER_NAME);
            if (VERSION.SDK_INT >= 10) {
                try {
                    MediaMetadataRetriever metadata = new MediaMetadataRetriever();
                    metadata.setDataSource(file.getAbsolutePath());
                    String videoWidth = metadata.extractMetadata(18);
                    String videoHeight = metadata.extractMetadata(19);
                    values.put("mime_type", metadata.extractMetadata(12));
                    values.put("duration", Integer.valueOf(Integer.parseInt(metadata.extractMetadata(9))));
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(videoWidth));
                    stringBuilder.append("x");
                    stringBuilder.append(videoHeight);
                    values.put("resolution", stringBuilder.toString());
                    values.put(SettingsJsonConstants.ICON_WIDTH_KEY, videoWidth);
                    values.put(SettingsJsonConstants.ICON_HEIGHT_KEY, videoHeight);
                } catch (RuntimeException e2) {
                    Log.w(TAG, "Can't get metadata from the file. Looks like it is corrupted.");
                }
            }
            this.contentResolver.insert(uri, values);
        }
    }

    public void deleteMedia(File file) {
        String filename = file.getName();
        String[] args = new String[]{filename, FileUtils.MEDIA_PUBLIC_FOLDER_NAME};
        Uri uri;
        if (filename.endsWith("jpg")) {
            if (FileUtils.isExtStorgAvailable()) {
                uri = Media.EXTERNAL_CONTENT_URI;
            } else {
                uri = Media.INTERNAL_CONTENT_URI;
            }
            this.contentResolver.delete(uri, "_display_name=? and bucket_display_name=?", args);
        } else if (filename.endsWith("mp4")) {
            if (FileUtils.isExtStorgAvailable()) {
                uri = Video.Media.EXTERNAL_CONTENT_URI;
            } else {
                uri = Video.Media.INTERNAL_CONTENT_URI;
            }
            this.contentResolver.delete(uri, "_display_name=? and bucket_display_name=?", args);
        }
    }

    public void deleteMedia(int id) {
        Uri uri;
        String[] args = new String[]{String.valueOf(id)};
        if (FileUtils.isExtStorgAvailable()) {
            uri = Media.EXTERNAL_CONTENT_URI;
        } else {
            uri = Media.INTERNAL_CONTENT_URI;
        }
        this.contentResolver.delete(uri, "_id=?", args);
    }

    public void deleteVideos(int[] ids) {
        Uri uri = FileUtils.isExtStorgAvailable() ? Video.Media.EXTERNAL_CONTENT_URI : Video.Media.INTERNAL_CONTENT_URI;
        String where = "";
        int size = ids.length;
        String[] args = new String[size];
        for (int i = 0; i < ids.length; i++) {
            args[i] = String.valueOf(ids[i]);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(where));
            stringBuilder.append("_id=?");
            where = stringBuilder.toString();
            if (i < size - 1) {
                stringBuilder = new StringBuilder(String.valueOf(where));
                stringBuilder.append(" OR ");
                where = stringBuilder.toString();
            }
        }
        this.contentResolver.delete(uri, where, args);
    }

    public void deleteImages(int[] ids) {
        Uri uri;
        if (FileUtils.isExtStorgAvailable()) {
            uri = Media.EXTERNAL_CONTENT_URI;
        } else {
            uri = Media.INTERNAL_CONTENT_URI;
        }
        String where = "";
        int size = ids.length;
        String[] args = new String[size];
        for (int i = 0; i < ids.length; i++) {
            args[i] = String.valueOf(ids[i]);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(where));
            stringBuilder.append("_id=?");
            where = stringBuilder.toString();
            if (i < size - 1) {
                stringBuilder = new StringBuilder(String.valueOf(where));
                stringBuilder.append(" OR ");
                where = stringBuilder.toString();
            }
        }
        this.contentResolver.delete(uri, where, args);
    }

    public void deleteMedia(String file) {
        deleteMedia(new File(file));
    }

    public void onMediaScannerConnected() {
        Log.d(TAG, "Media scanner [CONNECTED]");
        if (this.mediaToScan.isEmpty()) {
            Log.d(TAG, "Media scaner: No media in queue [DISCONNECTING]");
            this.connection.disconnect();
            return;
        }
        this.connection.scanFile((String) this.mediaToScan.get(0), null);
    }

    public void onScanCompleted(String path, Uri uri) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder("File ");
        stringBuilder.append(path);
        stringBuilder.append(" has been added to media gallery");
        Log.d(str, stringBuilder.toString());
        synchronized (this.mediaToScan) {
            this.mediaToScan.remove(path);
            if (this.mediaToScan.isEmpty()) {
                Log.d(TAG, "Media scaner: No media in queue [DISCONNECTING]");
                this.connection.disconnect();
            } else {
                this.connection.scanFile((String) this.mediaToScan.get(0), null);
            }
        }
    }

    public Cursor getImagesCursor() {
        return getMediaCursor(FileUtils.isExtStorgAvailable() ? Media.EXTERNAL_CONTENT_URI : Media.INTERNAL_CONTENT_URI);
    }

    public Cursor getVideosCursor() {
        return getMediaCursor(FileUtils.isExtStorgAvailable() ? Video.Media.EXTERNAL_CONTENT_URI : Video.Media.INTERNAL_CONTENT_URI);
    }

    public Cursor getAllMediaCursor() {
        Cursor cursor1 = getImagesCursor();
        Cursor cursor2 = getVideosCursor();
        return new MergeCursor(new Cursor[]{cursor1, cursor2});
    }

    public ArrayList<MediaVO> getMediaImageList() {
        ArrayList<MediaVO> resultList = new ArrayList();
        addMedia(resultList, FileUtils.isExtStorgAvailable() ? Media.EXTERNAL_CONTENT_URI : Media.INTERNAL_CONTENT_URI);
        return resultList;
    }

    public ArrayList<MediaVO> getMediaVideoList() {
        ArrayList<MediaVO> resultList = new ArrayList();
        addMedia(resultList, FileUtils.isExtStorgAvailable() ? Video.Media.EXTERNAL_CONTENT_URI : Video.Media.INTERNAL_CONTENT_URI);
        return resultList;
    }

    private void addMedia(ArrayList<MediaVO> resultList, Uri uri) {
        Cursor cursor = getMediaCursor(uri);
        if (cursor == null) {
            try {
                Log.w(TAG, "Unknown error");
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if (cursor.moveToFirst()) {
            int _ID = cursor.getColumnIndex("_id");
            int DATA = cursor.getColumnIndex("_data");
            int DATE_ADDED = cursor.getColumnIndex("date_added");
            do {
                int id = cursor.getInt(_ID);
                int dateAdded = cursor.getInt(DATE_ADDED);
                String path = cursor.getString(DATA);
                boolean isVideo = FileUtils.isVideo(path);
                MediaVO media = new MediaVO();
                media.setId(id);
                media.setDateAdded((long) dateAdded);
                media.setPath(path);
                media.setUri(Uri.withAppendedPath(uri, Integer.toString(id)));
                media.setVideo(isVideo);
                resultList.add(media);
            } while (cursor.moveToNext());
        } else {
            Log.w(TAG, "No media on the device");
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private Cursor getMediaCursor(Uri uri) {
        return this.contentResolver.query(uri, new String[]{"_id", "_data", "date_added"}, "bucket_display_name=?", new String[]{FileUtils.MEDIA_PUBLIC_FOLDER_NAME}, "date_added ASC");
    }

    public void onDestroy() {
        Log.d(TAG, "Media scaner: OnDestroyReceived [DISCONNECTING]");
        if (this.connection != null) {
            this.connection.disconnect();
        }
    }

    public int countOfMedia() {
        return countOfVideos() + countOfPhotos();
    }

    public int countOfVideos() {
        Cursor cursor = null;
        int count = 0;
        try {
            cursor = this.contentResolver.query(FileUtils.isExtStorgAvailable() ? Video.Media.EXTERNAL_CONTENT_URI : Video.Media.INTERNAL_CONTENT_URI, new String[]{"count(_id) as result"}, "bucket_display_name=?", new String[]{FileUtils.MEDIA_PUBLIC_FOLDER_NAME}, null);
            if (cursor == null) {
                Log.w(TAG, "Unknown error");
            } else if (cursor.moveToFirst()) {
                int resultIdx = cursor.getColumnIndex("result");
                if (resultIdx != -1) {
                    cursor.moveToFirst();
                    count = cursor.getInt(resultIdx);
                }
            } else {
                Log.w(TAG, "No media on the device");
            }
            if (cursor != null) {
                cursor.close();
            }
            return count;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public int countOfPhotos() {
        Uri uri;
        String[] projection = new String[]{"count(_id) as result"};
        String selection = "bucket_display_name=?";
        String[] selectionArgs = new String[]{FileUtils.MEDIA_PUBLIC_FOLDER_NAME};
        if (FileUtils.isExtStorgAvailable()) {
            uri = Media.EXTERNAL_CONTENT_URI;
        } else {
            uri = Media.INTERNAL_CONTENT_URI;
        }
        Cursor cursor = null;
        int count = 0;
        try {
            cursor = this.contentResolver.query(uri, projection, selection, selectionArgs, null);
            if (cursor == null) {
                Log.w(TAG, "Unknown error");
            } else if (cursor.moveToFirst()) {
                int resultIdx = cursor.getColumnIndex("result");
                if (resultIdx != -1) {
                    cursor.moveToFirst();
                    count = cursor.getInt(resultIdx);
                }
            } else {
                Log.w(TAG, "No media on the device");
            }
            if (cursor != null) {
                cursor.close();
            }
            return count;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
