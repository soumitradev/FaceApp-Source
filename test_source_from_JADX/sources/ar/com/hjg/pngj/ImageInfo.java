package ar.com.hjg.pngj;

public class ImageInfo {
    public static final int MAX_COLS_ROW = 16777216;
    public final boolean alpha;
    public final int bitDepth;
    public final int bitspPixel;
    public final int bytesPerRow;
    public final int bytesPixel;
    public final int channels;
    public final int cols;
    public final boolean greyscale;
    public final boolean indexed;
    public final boolean packed;
    public final int rows;
    public final int samplesPerRow;
    public final int samplesPerRowPacked;
    private long totalPixels;
    private long totalRawBytes;

    public ImageInfo(int cols, int rows, int bitdepth, boolean alpha) {
        this(cols, rows, bitdepth, alpha, false, false);
    }

    public ImageInfo(int cols, int rows, int bitdepth, boolean alpha, boolean grayscale, boolean indexed) {
        this.totalPixels = -1;
        this.totalRawBytes = -1;
        this.cols = cols;
        this.rows = rows;
        this.alpha = alpha;
        this.indexed = indexed;
        this.greyscale = grayscale;
        if (this.greyscale && indexed) {
            throw new PngjException("palette and greyscale are mutually exclusive");
        }
        int i;
        int i2;
        StringBuilder stringBuilder;
        if (!grayscale) {
            if (!indexed) {
                i = alpha ? 4 : 3;
                this.channels = i;
                this.bitDepth = bitdepth;
                this.packed = bitdepth >= 8;
                this.bitspPixel = this.channels * this.bitDepth;
                this.bytesPixel = (this.bitspPixel + 7) / 8;
                this.bytesPerRow = ((this.bitspPixel * cols) + 7) / 8;
                this.samplesPerRow = this.channels * this.cols;
                this.samplesPerRowPacked = this.packed ? this.bytesPerRow : this.samplesPerRow;
                i2 = this.bitDepth;
                if (i2 != 4) {
                    if (i2 != 8) {
                        if (i2 != 16) {
                            switch (i2) {
                                case 1:
                                case 2:
                                    break;
                                default:
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("invalid bitdepth=");
                                    stringBuilder.append(this.bitDepth);
                                    throw new PngjException(stringBuilder.toString());
                            }
                        } else if (this.indexed) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("indexed can't have bitdepth=");
                            stringBuilder.append(this.bitDepth);
                            throw new PngjException(stringBuilder.toString());
                        }
                    }
                    if (cols >= 1) {
                        if (cols > 16777216) {
                            if (rows >= 1) {
                                if (rows > 16777216) {
                                    if (this.samplesPerRow >= 1) {
                                        throw new PngjException("invalid image parameters (overflow?)");
                                    }
                                    return;
                                }
                            }
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("invalid rows=");
                            stringBuilder.append(rows);
                            stringBuilder.append(" ???");
                            throw new PngjException(stringBuilder.toString());
                        }
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("invalid cols=");
                    stringBuilder.append(cols);
                    stringBuilder.append(" ???");
                    throw new PngjException(stringBuilder.toString());
                }
                if (!(this.indexed || this.greyscale)) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("only indexed or grayscale can have bitdepth=");
                    stringBuilder.append(this.bitDepth);
                    throw new PngjException(stringBuilder.toString());
                }
                if (cols >= 1) {
                    if (cols > 16777216) {
                        if (rows >= 1) {
                            if (rows > 16777216) {
                                if (this.samplesPerRow >= 1) {
                                    throw new PngjException("invalid image parameters (overflow?)");
                                }
                                return;
                            }
                        }
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("invalid rows=");
                        stringBuilder.append(rows);
                        stringBuilder.append(" ???");
                        throw new PngjException(stringBuilder.toString());
                    }
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("invalid cols=");
                stringBuilder.append(cols);
                stringBuilder.append(" ???");
                throw new PngjException(stringBuilder.toString());
            }
        }
        i = alpha ? 2 : 1;
        this.channels = i;
        this.bitDepth = bitdepth;
        if (bitdepth >= 8) {
        }
        this.packed = bitdepth >= 8;
        this.bitspPixel = this.channels * this.bitDepth;
        this.bytesPixel = (this.bitspPixel + 7) / 8;
        this.bytesPerRow = ((this.bitspPixel * cols) + 7) / 8;
        this.samplesPerRow = this.channels * this.cols;
        if (this.packed) {
        }
        this.samplesPerRowPacked = this.packed ? this.bytesPerRow : this.samplesPerRow;
        i2 = this.bitDepth;
        if (i2 != 4) {
            if (i2 != 8) {
                if (i2 != 16) {
                    switch (i2) {
                        case 1:
                        case 2:
                            break;
                        default:
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("invalid bitdepth=");
                            stringBuilder.append(this.bitDepth);
                            throw new PngjException(stringBuilder.toString());
                    }
                } else if (this.indexed) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("indexed can't have bitdepth=");
                    stringBuilder.append(this.bitDepth);
                    throw new PngjException(stringBuilder.toString());
                }
            }
            if (cols >= 1) {
                if (cols > 16777216) {
                    if (rows >= 1) {
                        if (rows > 16777216) {
                            if (this.samplesPerRow >= 1) {
                                throw new PngjException("invalid image parameters (overflow?)");
                            }
                            return;
                        }
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("invalid rows=");
                    stringBuilder.append(rows);
                    stringBuilder.append(" ???");
                    throw new PngjException(stringBuilder.toString());
                }
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("invalid cols=");
            stringBuilder.append(cols);
            stringBuilder.append(" ???");
            throw new PngjException(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("only indexed or grayscale can have bitdepth=");
        stringBuilder.append(this.bitDepth);
        throw new PngjException(stringBuilder.toString());
    }

    public long getTotalPixels() {
        if (this.totalPixels < 0) {
            this.totalPixels = ((long) this.cols) * ((long) this.rows);
        }
        return this.totalPixels;
    }

    public long getTotalRawBytes() {
        if (this.totalRawBytes < 0) {
            this.totalRawBytes = ((long) (this.bytesPerRow + 1)) * ((long) this.rows);
        }
        return this.totalRawBytes;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ImageInfo [cols=");
        stringBuilder.append(this.cols);
        stringBuilder.append(", rows=");
        stringBuilder.append(this.rows);
        stringBuilder.append(", bitDepth=");
        stringBuilder.append(this.bitDepth);
        stringBuilder.append(", channels=");
        stringBuilder.append(this.channels);
        stringBuilder.append(", alpha=");
        stringBuilder.append(this.alpha);
        stringBuilder.append(", greyscale=");
        stringBuilder.append(this.greyscale);
        stringBuilder.append(", indexed=");
        stringBuilder.append(this.indexed);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public String toStringBrief() {
        String stringBuilder;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(String.valueOf(this.cols));
        stringBuilder2.append("x");
        stringBuilder2.append(this.rows);
        if (this.bitDepth != 8) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("d");
            stringBuilder3.append(this.bitDepth);
            stringBuilder = stringBuilder3.toString();
        } else {
            stringBuilder = "";
        }
        stringBuilder2.append(stringBuilder);
        stringBuilder2.append(this.alpha ? "a" : "");
        stringBuilder2.append(this.indexed ? "p" : "");
        stringBuilder2.append(this.greyscale ? "g" : "");
        return stringBuilder2.toString();
    }

    public String toStringDetail() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ImageInfo [cols=");
        stringBuilder.append(this.cols);
        stringBuilder.append(", rows=");
        stringBuilder.append(this.rows);
        stringBuilder.append(", bitDepth=");
        stringBuilder.append(this.bitDepth);
        stringBuilder.append(", channels=");
        stringBuilder.append(this.channels);
        stringBuilder.append(", bitspPixel=");
        stringBuilder.append(this.bitspPixel);
        stringBuilder.append(", bytesPixel=");
        stringBuilder.append(this.bytesPixel);
        stringBuilder.append(", bytesPerRow=");
        stringBuilder.append(this.bytesPerRow);
        stringBuilder.append(", samplesPerRow=");
        stringBuilder.append(this.samplesPerRow);
        stringBuilder.append(", samplesPerRowP=");
        stringBuilder.append(this.samplesPerRowPacked);
        stringBuilder.append(", alpha=");
        stringBuilder.append(this.alpha);
        stringBuilder.append(", greyscale=");
        stringBuilder.append(this.greyscale);
        stringBuilder.append(", indexed=");
        stringBuilder.append(this.indexed);
        stringBuilder.append(", packed=");
        stringBuilder.append(this.packed);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public int hashCode() {
        int i = 1237;
        int i2 = ((((((((((1 * 31) + (this.alpha ? 1231 : 1237)) * 31) + this.bitDepth) * 31) + this.channels) * 31) + this.cols) * 31) + (this.greyscale ? 1231 : 1237)) * 31;
        if (this.indexed) {
            i = 1231;
        }
        return ((i2 + i) * 31) + this.rows;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ImageInfo other = (ImageInfo) obj;
        if (this.alpha == other.alpha && this.bitDepth == other.bitDepth && this.channels == other.channels && this.cols == other.cols && this.greyscale == other.greyscale && this.indexed == other.indexed && this.rows == other.rows) {
            return true;
        }
        return false;
    }
}
