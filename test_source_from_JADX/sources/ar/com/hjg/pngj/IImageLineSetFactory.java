package ar.com.hjg.pngj;

public interface IImageLineSetFactory<T extends IImageLine> {
    IImageLineSet<T> create(ImageInfo imageInfo, boolean z, int i, int i2, int i3);
}
