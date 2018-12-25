package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkBKGD extends PngChunkSingle {
    public static final String ID = "bKGD";
    private int blue;
    private int gray;
    private int green;
    private int paletteIndex;
    private int red;

    public PngChunkBKGD(ImageInfo info) {
        super("bKGD", info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.AFTER_PLTE_BEFORE_IDAT;
    }

    public ChunkRaw createRawChunk() {
        ChunkRaw c;
        if (this.imgInfo.greyscale) {
            c = createEmptyChunk(2, true);
            PngHelperInternal.writeInt2tobytes(this.gray, c.data, 0);
            return c;
        } else if (this.imgInfo.indexed) {
            c = createEmptyChunk(1, true);
            c.data[0] = (byte) this.paletteIndex;
            return c;
        } else {
            c = createEmptyChunk(6, true);
            PngHelperInternal.writeInt2tobytes(this.red, c.data, 0);
            PngHelperInternal.writeInt2tobytes(this.green, c.data, 0);
            PngHelperInternal.writeInt2tobytes(this.blue, c.data, 0);
            return c;
        }
    }

    public void parseFromRaw(ChunkRaw c) {
        if (this.imgInfo.greyscale) {
            this.gray = PngHelperInternal.readInt2fromBytes(c.data, 0);
        } else if (this.imgInfo.indexed) {
            this.paletteIndex = c.data[0] & 255;
        } else {
            this.red = PngHelperInternal.readInt2fromBytes(c.data, 0);
            this.green = PngHelperInternal.readInt2fromBytes(c.data, 2);
            this.blue = PngHelperInternal.readInt2fromBytes(c.data, 4);
        }
    }

    public void setGray(int gray) {
        if (this.imgInfo.greyscale) {
            this.gray = gray;
            return;
        }
        throw new PngjException("only gray images support this");
    }

    public int getGray() {
        if (this.imgInfo.greyscale) {
            return this.gray;
        }
        throw new PngjException("only gray images support this");
    }

    public void setPaletteIndex(int i) {
        if (this.imgInfo.indexed) {
            this.paletteIndex = i;
            return;
        }
        throw new PngjException("only indexed (pallete) images support this");
    }

    public int getPaletteIndex() {
        if (this.imgInfo.indexed) {
            return this.paletteIndex;
        }
        throw new PngjException("only indexed (pallete) images support this");
    }

    public void setRGB(int r, int g, int b) {
        if (!this.imgInfo.greyscale) {
            if (!this.imgInfo.indexed) {
                this.red = r;
                this.green = g;
                this.blue = b;
                return;
            }
        }
        throw new PngjException("only rgb or rgba images support this");
    }

    public int[] getRGB() {
        if (!this.imgInfo.greyscale) {
            if (!this.imgInfo.indexed) {
                return new int[]{this.red, this.green, this.blue};
            }
        }
        throw new PngjException("only rgb or rgba images support this");
    }
}
