package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public abstract class PngChunkTextVar extends PngChunkMultiple {
    public static final String KEY_Author = "Author";
    public static final String KEY_Comment = "Comment";
    public static final String KEY_Copyright = "Copyright";
    public static final String KEY_Creation_Time = "Creation Time";
    public static final String KEY_Description = "Description";
    public static final String KEY_Disclaimer = "Disclaimer";
    public static final String KEY_Software = "Software";
    public static final String KEY_Source = "Source";
    public static final String KEY_Title = "Title";
    public static final String KEY_Warning = "Warning";
    protected String key;
    protected String val;

    public static class PngTxtInfo {
        public String author;
        public String comment;
        public String creation_time;
        public String description;
        public String disclaimer;
        public String software;
        public String source;
        public String title;
        public String warning;
    }

    protected PngChunkTextVar(String id, ImageInfo info) {
        super(id, info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NONE;
    }

    public String getKey() {
        return this.key;
    }

    public String getVal() {
        return this.val;
    }

    public void setKeyVal(String key, String val) {
        this.key = key;
        this.val = val;
    }
}
