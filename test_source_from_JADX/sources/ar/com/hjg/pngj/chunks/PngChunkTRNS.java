package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkTRNS extends PngChunkSingle {
    public static final String ID = "tRNS";
    private int blue;
    private int gray;
    private int green;
    private int[] paletteAlpha = new int[0];
    private int red;

    public PngChunkTRNS(ImageInfo info) {
        super("tRNS", info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.AFTER_PLTE_BEFORE_IDAT;
    }

    public ChunkRaw createRawChunk() {
        int n = 0;
        ChunkRaw c;
        if (this.imgInfo.greyscale) {
            c = createEmptyChunk(2, true);
            PngHelperInternal.writeInt2tobytes(this.gray, c.data, 0);
            return c;
        } else if (this.imgInfo.indexed) {
            c = createEmptyChunk(this.paletteAlpha.length, true);
            while (true) {
                int n2 = n;
                if (n2 >= c.len) {
                    return c;
                }
                c.data[n2] = (byte) this.paletteAlpha[n2];
                n = n2 + 1;
            }
        } else {
            c = createEmptyChunk(6, true);
            PngHelperInternal.writeInt2tobytes(this.red, c.data, 0);
            PngHelperInternal.writeInt2tobytes(this.green, c.data, 0);
            PngHelperInternal.writeInt2tobytes(this.blue, c.data, 0);
            return c;
        }
    }

    public void parseFromRaw(ChunkRaw c) {
        int n = 0;
        if (this.imgInfo.greyscale) {
            this.gray = PngHelperInternal.readInt2fromBytes(c.data, 0);
        } else if (this.imgInfo.indexed) {
            int nentries = c.data.length;
            this.paletteAlpha = new int[nentries];
            while (n < nentries) {
                this.paletteAlpha[n] = c.data[n] & 255;
                n++;
            }
        } else {
            this.red = PngHelperInternal.readInt2fromBytes(c.data, 0);
            this.green = PngHelperInternal.readInt2fromBytes(c.data, 2);
            this.blue = PngHelperInternal.readInt2fromBytes(c.data, 4);
        }
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

    public int getRGB888() {
        if (!this.imgInfo.greyscale) {
            if (!this.imgInfo.indexed) {
                return ((this.red << 16) | (this.green << 8)) | this.blue;
            }
        }
        throw new PngjException("only rgb or rgba images support this");
    }

    public void setGray(int g) {
        if (this.imgInfo.greyscale) {
            this.gray = g;
            return;
        }
        throw new PngjException("only grayscale images support this");
    }

    public int getGray() {
        if (this.imgInfo.greyscale) {
            return this.gray;
        }
        throw new PngjException("only grayscale images support this");
    }

    public void setPalletteAlpha(int[] palAlpha) {
        if (this.imgInfo.indexed) {
            this.paletteAlpha = palAlpha;
            return;
        }
        throw new PngjException("only indexed images support this");
    }

    public void setIndexEntryAsTransparent(int palAlphaIndex) {
        if (this.imgInfo.indexed) {
            this.paletteAlpha = new int[]{palAlphaIndex + 1};
            for (int i = 0; i < palAlphaIndex; i++) {
                this.paletteAlpha[i] = 255;
            }
            this.paletteAlpha[palAlphaIndex] = 0;
            return;
        }
        throw new PngjException("only indexed images support this");
    }

    public int[] getPalletteAlpha() {
        return this.paletteAlpha;
    }
}
