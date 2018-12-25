package ar.com.hjg.pngj;

public interface IImageLine {
    void endReadFromPngRaw();

    void readFromPngRaw(byte[] bArr, int i, int i2, int i3);

    void writeToPngRaw(byte[] bArr);
}
