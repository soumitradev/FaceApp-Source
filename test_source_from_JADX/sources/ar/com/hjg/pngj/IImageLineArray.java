package ar.com.hjg.pngj;

public interface IImageLineArray {
    int getElem(int i);

    FilterType getFilterType();

    ImageInfo getImageInfo();

    int getSize();
}
