package ar.com.hjg.pngj.chunks;

import android.support.v4.view.MotionEventCompat;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkPLTE extends PngChunkSingle {
    public static final String ID = "PLTE";
    private int[] entries;
    private int nentries = 0;

    public PngChunkPLTE(ImageInfo info) {
        super("PLTE", info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NA;
    }

    public ChunkRaw createRawChunk() {
        int[] rgb = new int[3];
        ChunkRaw c = createEmptyChunk(this.nentries * 3, true);
        int n = 0;
        int i = 0;
        while (n < this.nentries) {
            getEntryRgb(n, rgb);
            int i2 = i + 1;
            c.data[i] = (byte) rgb[0];
            int i3 = i2 + 1;
            c.data[i2] = (byte) rgb[1];
            i2 = i3 + 1;
            c.data[i3] = (byte) rgb[2];
            n++;
            i = i2;
        }
        return c;
    }

    public void parseFromRaw(ChunkRaw chunk) {
        setNentries(chunk.len / 3);
        int n = 0;
        int i = 0;
        while (n < this.nentries) {
            int i2 = i + 1;
            int i3 = i2 + 1;
            int i4 = i3 + 1;
            setEntry(n, chunk.data[i] & 255, chunk.data[i2] & 255, chunk.data[i3] & 255);
            n++;
            i = i4;
        }
    }

    public void setNentries(int n) {
        this.nentries = n;
        if (this.nentries >= 1) {
            if (this.nentries <= 256) {
                if (this.entries == null || this.entries.length != this.nentries) {
                    this.entries = new int[this.nentries];
                    return;
                }
                return;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid pallette - nentries=");
        stringBuilder.append(this.nentries);
        throw new PngjException(stringBuilder.toString());
    }

    public int getNentries() {
        return this.nentries;
    }

    public void setEntry(int n, int r, int g, int b) {
        this.entries[n] = ((r << 16) | (g << 8)) | b;
    }

    public int getEntry(int n) {
        return this.entries[n];
    }

    public void getEntryRgb(int n, int[] rgb) {
        getEntryRgb(n, rgb, 0);
    }

    public void getEntryRgb(int n, int[] rgb, int offset) {
        int v = this.entries[n];
        rgb[offset + 0] = (16711680 & v) >> 16;
        rgb[offset + 1] = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & v) >> 8;
        rgb[offset + 2] = v & 255;
    }

    public int minBitDepth() {
        if (this.nentries <= 2) {
            return 1;
        }
        if (this.nentries <= 4) {
            return 2;
        }
        if (this.nentries <= 16) {
            return 4;
        }
        return 8;
    }
}
