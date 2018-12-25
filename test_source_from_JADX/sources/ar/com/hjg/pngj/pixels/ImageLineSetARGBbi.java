package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.IImageLineSet;
import ar.com.hjg.pngj.ImageInfo;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageLineSetARGBbi implements IImageLineSet<ImageLineARGBbi> {
    BufferedImage image;
    private ImageInfo imginfo;
    private ImageLineARGBbi line;

    public ImageLineSetARGBbi(BufferedImage bi, ImageInfo imginfo) {
        this.image = bi;
        this.imginfo = imginfo;
        this.line = new ImageLineARGBbi(imginfo, bi, ((DataBufferByte) this.image.getRaster().getDataBuffer()).getData());
    }

    public ImageLineARGBbi getImageLine(int n) {
        this.line.setRowNumber(n);
        return this.line;
    }

    public boolean hasImageLine(int n) {
        return n >= 0 && n < this.imginfo.rows;
    }

    public int size() {
        return 1;
    }
}
