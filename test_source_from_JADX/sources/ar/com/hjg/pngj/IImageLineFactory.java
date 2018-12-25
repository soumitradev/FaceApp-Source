package ar.com.hjg.pngj;

public interface IImageLineFactory<T extends IImageLine> {
    T createImageLine(ImageInfo imageInfo);
}
