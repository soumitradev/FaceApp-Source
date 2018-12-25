package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;

public abstract class PngChunkSingle extends PngChunk {
    protected PngChunkSingle(String id, ImageInfo imgInfo) {
        super(id, imgInfo);
    }

    public final boolean allowsMultiple() {
        return false;
    }

    public int hashCode() {
        return (1 * 31) + (this.id == null ? 0 : this.id.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PngChunkSingle other = (PngChunkSingle) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
