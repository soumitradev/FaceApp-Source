package com.parrot.freeflight.vo;

import android.net.Uri;

public class MediaVO implements Comparable<MediaVO> {
    private long dateAdded;
    private int id;
    private boolean isSelected;
    private boolean isVideo;
    private String key;
    private String path;
    private Uri uri;

    public boolean isVideo() {
        return this.isVideo;
    }

    public void setVideo(boolean isVideo) {
        this.isVideo = isVideo;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return this.uri;
    }

    public long getDateAdded() {
        return this.dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getKey() {
        if (this.key == null) {
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.isVideo ? "video_" : ""));
            stringBuilder.append(this.id);
            this.key = stringBuilder.toString();
        }
        return this.key;
    }

    public int compareTo(MediaVO another) {
        long anotherDate = another.getDateAdded();
        int result = this.dateAdded < anotherDate ? -1 : 1;
        if (anotherDate == this.dateAdded) {
            return 0;
        }
        return result;
    }
}
