package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.PngjInputException;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;
import java.io.ByteArrayInputStream;

public class PngChunkIHDR extends PngChunkSingle {
    public static final String ID = "IHDR";
    private int bitspc;
    private int colormodel;
    private int cols;
    private int compmeth;
    private int filmeth;
    private int interlaced;
    private int rows;

    public PngChunkIHDR(ImageInfo info) {
        super("IHDR", info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NA;
    }

    public ChunkRaw createRawChunk() {
        ChunkRaw c = new ChunkRaw(13, ChunkHelper.b_IHDR, true);
        PngHelperInternal.writeInt4tobytes(this.cols, c.data, 0);
        int offset = 0 + 4;
        PngHelperInternal.writeInt4tobytes(this.rows, c.data, offset);
        offset += 4;
        int offset2 = offset + 1;
        c.data[offset] = (byte) this.bitspc;
        int offset3 = offset2 + 1;
        c.data[offset2] = (byte) this.colormodel;
        offset2 = offset3 + 1;
        c.data[offset3] = (byte) this.compmeth;
        offset3 = offset2 + 1;
        c.data[offset2] = (byte) this.filmeth;
        offset2 = offset3 + 1;
        c.data[offset3] = (byte) this.interlaced;
        return c;
    }

    public void parseFromRaw(ChunkRaw c) {
        if (c.len != 13) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad IDHR len ");
            stringBuilder.append(c.len);
            throw new PngjException(stringBuilder.toString());
        }
        ByteArrayInputStream st = c.getAsByteStream();
        this.cols = PngHelperInternal.readInt4(st);
        this.rows = PngHelperInternal.readInt4(st);
        this.bitspc = PngHelperInternal.readByte(st);
        this.colormodel = PngHelperInternal.readByte(st);
        this.compmeth = PngHelperInternal.readByte(st);
        this.filmeth = PngHelperInternal.readByte(st);
        this.interlaced = PngHelperInternal.readByte(st);
    }

    public int getCols() {
        return this.cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getBitspc() {
        return this.bitspc;
    }

    public void setBitspc(int bitspc) {
        this.bitspc = bitspc;
    }

    public int getColormodel() {
        return this.colormodel;
    }

    public void setColormodel(int colormodel) {
        this.colormodel = colormodel;
    }

    public int getCompmeth() {
        return this.compmeth;
    }

    public void setCompmeth(int compmeth) {
        this.compmeth = compmeth;
    }

    public int getFilmeth() {
        return this.filmeth;
    }

    public void setFilmeth(int filmeth) {
        this.filmeth = filmeth;
    }

    public int getInterlaced() {
        return this.interlaced;
    }

    public void setInterlaced(int interlaced) {
        this.interlaced = interlaced;
    }

    public boolean isInterlaced() {
        return getInterlaced() == 1;
    }

    public ImageInfo createImageInfo() {
        boolean grayscale;
        check();
        boolean alpha = (getColormodel() & 4) != 0;
        boolean palette = (getColormodel() & 1) != 0;
        if (getColormodel() != 0) {
            if (getColormodel() != 4) {
                grayscale = false;
                return new ImageInfo(getCols(), getRows(), getBitspc(), alpha, grayscale, palette);
            }
        }
        grayscale = true;
        return new ImageInfo(getCols(), getRows(), getBitspc(), alpha, grayscale, palette);
    }

    public void check() {
        if (this.cols >= 1 && this.rows >= 1 && this.compmeth == 0) {
            if (this.filmeth == 0) {
                if (this.bitspc == 1 || this.bitspc == 2 || this.bitspc == 4 || this.bitspc == 8 || this.bitspc == 16) {
                    if (this.interlaced >= 0) {
                        if (this.interlaced <= 1) {
                            int i = this.colormodel;
                            if (i != 0) {
                                if (i != 6) {
                                    switch (i) {
                                        case 2:
                                        case 4:
                                            break;
                                        case 3:
                                            if (this.bitspc == 16) {
                                                throw new PngjInputException("bad IHDR: bitdepth invalid");
                                            }
                                            return;
                                        default:
                                            throw new PngjInputException("bad IHDR: invalid colormodel");
                                    }
                                }
                                if (this.bitspc != 8 && this.bitspc != 16) {
                                    throw new PngjInputException("bad IHDR: bitdepth invalid");
                                }
                                return;
                            }
                            return;
                        }
                    }
                    throw new PngjInputException("bad IHDR: interlace invalid");
                }
                throw new PngjInputException("bad IHDR: bitdepth invalid");
            }
        }
        throw new PngjInputException("bad IHDR: col/row/compmethod/filmethod invalid");
    }
}
