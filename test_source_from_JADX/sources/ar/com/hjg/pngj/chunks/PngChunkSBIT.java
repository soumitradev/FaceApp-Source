package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkSBIT extends PngChunkSingle {
    public static final String ID = "sBIT";
    private int alphasb;
    private int bluesb;
    private int graysb;
    private int greensb;
    private int redsb;

    public PngChunkSBIT(ImageInfo info) {
        super("sBIT", info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_PLTE_AND_IDAT;
    }

    private int getCLen() {
        int len = this.imgInfo.greyscale ? 1 : 3;
        if (this.imgInfo.alpha) {
            return len + 1;
        }
        return len;
    }

    public void parseFromRaw(ChunkRaw c) {
        if (c.len != getCLen()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bad chunk length ");
            stringBuilder.append(c);
            throw new PngjException(stringBuilder.toString());
        } else if (this.imgInfo.greyscale) {
            this.graysb = PngHelperInternal.readInt1fromByte(c.data, 0);
            if (this.imgInfo.alpha) {
                this.alphasb = PngHelperInternal.readInt1fromByte(c.data, 1);
            }
        } else {
            this.redsb = PngHelperInternal.readInt1fromByte(c.data, 0);
            this.greensb = PngHelperInternal.readInt1fromByte(c.data, 1);
            this.bluesb = PngHelperInternal.readInt1fromByte(c.data, 2);
            if (this.imgInfo.alpha) {
                this.alphasb = PngHelperInternal.readInt1fromByte(c.data, 3);
            }
        }
    }

    public ChunkRaw createRawChunk() {
        ChunkRaw c = createEmptyChunk(getCLen(), true);
        if (this.imgInfo.greyscale) {
            c.data[0] = (byte) this.graysb;
            if (this.imgInfo.alpha) {
                c.data[1] = (byte) this.alphasb;
            }
        } else {
            c.data[0] = (byte) this.redsb;
            c.data[1] = (byte) this.greensb;
            c.data[2] = (byte) this.bluesb;
            if (this.imgInfo.alpha) {
                c.data[3] = (byte) this.alphasb;
            }
        }
        return c;
    }

    public void setGraysb(int gray) {
        if (this.imgInfo.greyscale) {
            this.graysb = gray;
            return;
        }
        throw new PngjException("only greyscale images support this");
    }

    public int getGraysb() {
        if (this.imgInfo.greyscale) {
            return this.graysb;
        }
        throw new PngjException("only greyscale images support this");
    }

    public void setAlphasb(int a) {
        if (this.imgInfo.alpha) {
            this.alphasb = a;
            return;
        }
        throw new PngjException("only images with alpha support this");
    }

    public int getAlphasb() {
        if (this.imgInfo.alpha) {
            return this.alphasb;
        }
        throw new PngjException("only images with alpha support this");
    }

    public void setRGB(int r, int g, int b) {
        if (!this.imgInfo.greyscale) {
            if (!this.imgInfo.indexed) {
                this.redsb = r;
                this.greensb = g;
                this.bluesb = b;
                return;
            }
        }
        throw new PngjException("only rgb or rgba images support this");
    }

    public int[] getRGB() {
        if (!this.imgInfo.greyscale) {
            if (!this.imgInfo.indexed) {
                return new int[]{this.redsb, this.greensb, this.bluesb};
            }
        }
        throw new PngjException("only rgb or rgba images support this");
    }
}
