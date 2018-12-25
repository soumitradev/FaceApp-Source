package ar.com.hjg.pngj;

public class ImageLineByte implements IImageLine, IImageLineArray {
    protected FilterType filterType;
    public final ImageInfo imgInfo;
    final byte[] scanline;
    final byte[] scanline2;
    final int size;

    /* renamed from: ar.com.hjg.pngj.ImageLineByte$1 */
    static class C07311 implements IImageLineFactory<ImageLineByte> {
        C07311() {
        }

        public ImageLineByte createImageLine(ImageInfo iminfo) {
            return new ImageLineByte(iminfo);
        }
    }

    public ImageLineByte(ImageInfo imgInfo) {
        this(imgInfo, null);
    }

    public ImageLineByte(ImageInfo imgInfo, byte[] sci) {
        this.imgInfo = imgInfo;
        this.filterType = FilterType.FILTER_UNKNOWN;
        this.size = imgInfo.samplesPerRow;
        byte[] bArr = (sci == null || sci.length < this.size) ? new byte[this.size] : sci;
        this.scanline = bArr;
        this.scanline2 = imgInfo.bitDepth == 16 ? new byte[this.size] : null;
    }

    public static IImageLineFactory<ImageLineByte> getFactory(ImageInfo iminfo) {
        return new C07311();
    }

    public FilterType getFilterUsed() {
        return this.filterType;
    }

    public byte[] getScanlineByte() {
        return this.scanline;
    }

    public byte[] getScanlineByte2() {
        return this.scanline2;
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
        byte[] bArr = raw;
        int i = len;
        int i2 = step;
        int c = 0;
        this.filterType = FilterType.getByVal(bArr[0]);
        int len1 = i - 1;
        int step1 = (i2 - 1) * this.imgInfo.channels;
        int s = 1;
        int c2;
        int i3;
        if (this.imgInfo.bitDepth == 8) {
            if (i2 == 1) {
                System.arraycopy(bArr, 1, r0.scanline, 0, len1);
                return;
            }
            c = 1;
            c2 = 0;
            i3 = r0.imgInfo.channels * offset;
            while (c <= len1) {
                r0.scanline[i3] = bArr[c];
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
            for (int r = 1; r < i; r++) {
                int mask = mask0;
                int shi = 8 - c2;
                while (true) {
                    r0.scanline[i] = (byte) ((bArr[r] & mask) >> shi);
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
                if (c < r0.imgInfo.samplesPerRow) {
                    s = c2 + 1;
                    r0.scanline[c] = bArr[c2];
                    i3 = s + 1;
                    r0.scanline2[c] = bArr[s];
                    c++;
                    s = i3;
                } else {
                    return;
                }
            }
        } else {
            c2 = 1;
            i3 = 0;
            if (offset != 0) {
                c = r0.imgInfo.channels * offset;
            }
            while (c2 <= len1) {
                i = c2 + 1;
                r0.scanline[c] = bArr[c2];
                mask0 = i + 1;
                r0.scanline2[c] = bArr[i];
                i3++;
                if (i3 == r0.imgInfo.channels) {
                    c += step1;
                    i3 = 0;
                }
                c++;
                c2 = mask0;
            }
        }
    }

    public void writeToPngRaw(byte[] raw) {
        int i = 0;
        raw[0] = (byte) this.filterType.val;
        int s = 1;
        int i2;
        if (this.imgInfo.bitDepth == 8) {
            System.arraycopy(this.scanline, 0, raw, 1, this.size);
            while (true) {
                i2 = i;
                if (i2 < this.size) {
                    raw[i2 + 1] = this.scanline[i2];
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        } else if (this.imgInfo.bitDepth == 16) {
            i2 = 0;
            while (true) {
                i = s;
                if (i2 < this.size) {
                    int s2 = i + 1;
                    raw[i] = this.scanline[i2];
                    s = s2 + 1;
                    raw[s2] = this.scanline2[i2];
                    i2++;
                } else {
                    return;
                }
            }
        } else {
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
    }

    public void endReadFromPngRaw() {
    }

    public int getSize() {
        return this.size;
    }

    public int getElem(int i) {
        return this.scanline2 == null ? this.scanline[i] & 255 : ((this.scanline[i] & 255) << 8) | (this.scanline2[i] & 255);
    }

    public byte[] getScanline() {
        return this.scanline;
    }

    public ImageInfo getImageInfo() {
        return this.imgInfo;
    }

    public FilterType getFilterType() {
        return this.filterType;
    }
}
