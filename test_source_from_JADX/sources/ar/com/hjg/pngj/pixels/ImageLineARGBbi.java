package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.IImageLine;
import ar.com.hjg.pngj.IImageLineFactory;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineByte;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentSampleModel;

public class ImageLineARGBbi implements IImageLine {
    private boolean bgrOrder;
    private byte[] bytes;
    private boolean hasAlpha;
    private final BufferedImage image;
    public final ImageInfo imgInfo;
    private int rowLength;
    private int rowNumber = -1;

    /* renamed from: ar.com.hjg.pngj.pixels.ImageLineARGBbi$1 */
    static class C07411 implements IImageLineFactory<ImageLineByte> {
        C07411() {
        }

        public ImageLineByte createImageLine(ImageInfo iminfo) {
            return new ImageLineByte(iminfo);
        }
    }

    public ImageLineARGBbi(ImageInfo imgInfo, BufferedImage bi, byte[] bytesdata) {
        this.imgInfo = imgInfo;
        this.image = bi;
        this.bytes = bytesdata;
        this.hasAlpha = this.image.getColorModel().hasAlpha();
        if (this.hasAlpha) {
            this.rowLength = this.image.getWidth() * 4;
        } else {
            this.rowLength = this.image.getWidth() * 3;
        }
        boolean z = false;
        if (((ComponentSampleModel) this.image.getSampleModel()).getBandOffsets()[0] != 0) {
            z = true;
        }
        this.bgrOrder = z;
    }

    public static IImageLineFactory<ImageLineByte> getFactory(ImageInfo iminfo) {
        return new C07411();
    }

    public void readFromPngRaw(byte[] raw, int len, int offset, int step) {
        throw new RuntimeException("not implemented");
    }

    public void writeToPngRaw(byte[] raw) {
        if (this.imgInfo.bytesPerRow != this.rowLength) {
            throw new RuntimeException("??");
        }
        if (this.rowNumber >= 0) {
            if (this.rowNumber < this.imgInfo.rows) {
                int bytesIdx = this.rowLength * this.rowNumber;
                int i = 1;
                int bytesIdx2;
                byte a;
                int bytesIdx3;
                byte b;
                byte g;
                int i2;
                if (this.hasAlpha) {
                    if (this.bgrOrder) {
                        while (i <= this.rowLength) {
                            bytesIdx2 = a + 1;
                            a = this.bytes[a];
                            bytesIdx3 = bytesIdx2 + 1;
                            b = this.bytes[bytesIdx2];
                            int bytesIdx4 = bytesIdx3 + 1;
                            g = this.bytes[bytesIdx3];
                            byte bytesIdx5 = bytesIdx4 + 1;
                            byte r = this.bytes[bytesIdx4];
                            bytesIdx4 = i + 1;
                            raw[i] = r;
                            i = bytesIdx4 + 1;
                            raw[bytesIdx4] = g;
                            bytesIdx4 = i + 1;
                            raw[i] = b;
                            i = bytesIdx4 + 1;
                            raw[bytesIdx4] = a;
                            a = bytesIdx5;
                        }
                        return;
                    }
                    while (i <= this.rowLength) {
                        i2 = i + 1;
                        bytesIdx3 = bytesIdx + 1;
                        raw[i] = this.bytes[bytesIdx];
                        bytesIdx = i2 + 1;
                        bytesIdx2 = bytesIdx3 + 1;
                        raw[i2] = this.bytes[bytesIdx3];
                        i = bytesIdx + 1;
                        bytesIdx3 = bytesIdx2 + 1;
                        raw[bytesIdx] = this.bytes[bytesIdx2];
                        bytesIdx = i + 1;
                        bytesIdx2 = bytesIdx3 + 1;
                        raw[i] = this.bytes[bytesIdx3];
                        i = bytesIdx;
                        bytesIdx = bytesIdx2;
                    }
                    return;
                } else if (this.bgrOrder) {
                    while (i <= this.rowLength) {
                        bytesIdx2 = a + 1;
                        a = this.bytes[a];
                        bytesIdx3 = bytesIdx2 + 1;
                        b = this.bytes[bytesIdx2];
                        byte bytesIdx6 = bytesIdx3 + 1;
                        g = this.bytes[bytesIdx3];
                        bytesIdx3 = i + 1;
                        raw[i] = g;
                        i = bytesIdx3 + 1;
                        raw[bytesIdx3] = b;
                        bytesIdx3 = i + 1;
                        raw[i] = a;
                        i = bytesIdx3;
                        a = bytesIdx6;
                    }
                    return;
                } else {
                    while (i <= this.rowLength) {
                        i2 = i + 1;
                        bytesIdx3 = bytesIdx + 1;
                        raw[i] = this.bytes[bytesIdx];
                        bytesIdx = i2 + 1;
                        bytesIdx2 = bytesIdx3 + 1;
                        raw[i2] = this.bytes[bytesIdx3];
                        i = bytesIdx + 1;
                        bytesIdx3 = bytesIdx2 + 1;
                        raw[bytesIdx] = this.bytes[bytesIdx2];
                        bytesIdx = bytesIdx3;
                    }
                    return;
                }
            }
        }
        throw new RuntimeException("???");
    }

    public void endReadFromPngRaw() {
        throw new RuntimeException("not implemented");
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
