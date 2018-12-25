package ar.com.hjg.pngj;

public interface IImageLineSet<T extends IImageLine> {
    T getImageLine(int i);

    boolean hasImageLine(int i);

    int size();
}
