package ar.com.hjg.pngj;

public class ImageLineInt implements IImageLine, IImageLineArray {
    protected FilterType filterType;
    public final ImageInfo imgInfo;
    protected final int[] scanline;
    protected final int size;

    /* renamed from: ar.com.hjg.pngj.ImageLineInt$1 */
    static class C07321 implements IImageLineFactory<ImageLineInt> {
        C07321() {
        }

        public ImageLineInt createImageLine(ImageInfo iminfo) {
            return new ImageLineInt(iminfo);
        }
    }

    public ImageLineInt(ImageInfo imgInfo) {
        this(imgInfo, null);
    }

    public ImageLineInt(ImageInfo imgInfo, int[] sci) {
        this.filterType = FilterType.FILTER_UNKNOWN;
        this.imgInfo = imgInfo;
        this.filterType = FilterType.FILTER_UNKNOWN;
        this.size = imgInfo.samplesPerRow;
        int[] iArr = (sci == null || sci.length < this.size) ? new int[this.size] : sci;
        this.scanline = iArr;
    }

    public FilterType getFilterType() {
        return this.filterType;
    }

    protected void setFilterType(FilterType ft) {
        this.filterType = ft;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" cols=");
        stringBuilder.append(this.imgInfo.cols);
        stringBuilder.append(" bpc=");
        stringBuilder.append(this.imgInfo.bitDepth);
        stringBuilder.append(" size=");
        stringBuilder.append(this.scanline.length);
        return stringBuilder.toString();
    }

    public void readFromPngRaw(byte[] raw, int len, int offset, int step) {
        int i = len;
        int i2 = step;
        int c = 0;
        setFilterType(FilterType.getByVal(raw[0]));
        int len1 = i - 1;
        int step1 = (i2 - 1) * this.imgInfo.channels;
        int s = 1;
        int c2;
        if (this.imgInfo.bitDepth == 8) {
            if (i2 == 1) {
                while (c < r0.size) {
                    r0.scanline[c] = raw[c + 1] & 255;
                    c++;
                }
                return;
            }
            c = 1;
            c2 = 0;
            int i3 = r0.imgInfo.channels * offset;
            while (c <= len1) {
                r0.scanline[i3] = raw[c] & 255;
                c2++;
                if (c2 == r0.imgInfo.channels) {
                    c2 = 0;
                    i3 += step1;
                }
                c++;
                i3++;
            }
        } else if (r0.imgInfo.bitDepth != 16) {
            c2 = r0.imgInfo.bitDepth;
            mask0 = ImageLineHelper.getMaskForPackedFormats(c2);
            i = r0.imgInfo.channels * offset;
            for (r = 1; r < i; r++) {
                int mask = mask0;
                int shi = 8 - c2;
                while (true) {
                    r0.scanline[i] = (raw[r] & mask) >> shi;
                    mask >>= c2;
                    shi -= c2;
                    i++;
                    c++;
                    if (c == r0.imgInfo.channels) {
                        c = 0;
                        i += step1;
                    }
                    if (mask == 0) {
                        break;
                    } else if (i >= r0.size) {
                        break;
                    }
                }
            }
        } else if (i2 == 1) {
            c = 0;
            while (true) {
                c2 = s;
                if (c < r0.size) {
                    mask0 = c2 + 1;
                    i = mask0 + 1;
                    r0.scanline[c] = ((raw[c2] & 255) << 8) | (raw[mask0] & 255);
                    c++;
                    s = i;
                } else {
                    return;
                }
            }
        } else {
            c2 = 1;
            mask0 = 0;
            if (offset != 0) {
                c = r0.imgInfo.channels * offset;
            }
            while (c2 <= len1) {
                r = c2 + 1;
                r0.scanline[c] = ((raw[c2] & 255) << 8) | (raw[r] & 255);
                mask0++;
                if (mask0 == r0.imgInfo.channels) {
                    c += step1;
                    mask0 = 0;
                }
                c2 = r + 1;
                c++;
            }
        }
    }

    public void writeToPngRaw(byte[] raw) {
        int i = 0;
        raw[0] = (byte) this.filterType.val;
        int i2;
        if (this.imgInfo.bitDepth == 8) {
            while (true) {
                i2 = i;
                if (i2 < this.size) {
                    raw[i2 + 1] = (byte) this.scanline[i2];
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
        int s = 1;
        if (this.imgInfo.bitDepth == 16) {
            i2 = 0;
            while (true) {
                i = s;
                if (i2 < this.size) {
                    s = i + 1;
                    raw[i] = (byte) (this.scanline[i2] >> 8);
                    i = s + 1;
                    raw[s] = (byte) (this.scanline[i2] & 255);
                    i2++;
                    s = i;
                } else {
                    return;
                }
            }
        }
        i2 = this.imgInfo.bitDepth;
        int i3 = 0;
        int v = 0;
        int shi = 8 - i2;
        i = 1;
        while (i3 < this.size) {
            v |= this.scanline[i3] << shi;
            shi -= i2;
            if (shi < 0 || i3 == this.size - 1) {
                int r = i + 1;
                raw[i] = (byte) v;
                v = 0;
                shi = 8 - i2;
                i = r;
            }
            i3++;
        }
    }

    public void endReadFromPngRaw() {
    }

    public int getSize() {
        return this.size;
    }

    public int getElem(int i) {
        return this.scanline[i];
    }

    public int[] getScanline() {
        return this.scanline;
    }

    public ImageInfo getImageInfo() {
        return this.imgInfo;
    }

    public static IImageLineFactory<ImageLineInt> getFactory(ImageInfo iminfo) {
        return new C07321();
    }
}
