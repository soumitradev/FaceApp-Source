package ar.com.hjg.pngj;

import java.util.ArrayList;
import java.util.List;

public abstract class ImageLineSetDefault<T extends IImageLine> implements IImageLineSet<T> {
    protected int currentRow = -1;
    protected T imageLine;
    protected List<T> imageLines;
    protected final ImageInfo imgInfo;
    private final int nlines;
    private final int offset;
    private final boolean singleCursor;
    private final int step;

    /* renamed from: ar.com.hjg.pngj.ImageLineSetDefault$1 */
    static class C07331 implements IImageLineSetFactory<ImageLineInt> {
        C07331() {
        }

        public IImageLineSet<ImageLineInt> create(ImageInfo iminfo, boolean singleCursor, int nlines, int noffset, int step) {
            return new ImageLineSetDefault<ImageLineInt>(iminfo, singleCursor, nlines, noffset, step) {
                protected ImageLineInt createImageLine() {
                    return new ImageLineInt(this.imgInfo);
                }
            };
        }
    }

    /* renamed from: ar.com.hjg.pngj.ImageLineSetDefault$2 */
    static class C07342 implements IImageLineSetFactory<ImageLineByte> {
        C07342() {
        }

        public IImageLineSet<ImageLineByte> create(ImageInfo iminfo, boolean singleCursor, int nlines, int noffset, int step) {
            return new ImageLineSetDefault<ImageLineByte>(iminfo, singleCursor, nlines, noffset, step) {
                protected ImageLineByte createImageLine() {
                    return new ImageLineByte(this.imgInfo);
                }
            };
        }
    }

    protected abstract T createImageLine();

    public ImageLineSetDefault(ImageInfo imgInfo, boolean singleCursor, int nlines, int noffset, int step) {
        this.imgInfo = imgInfo;
        this.singleCursor = singleCursor;
        if (singleCursor) {
            this.nlines = 1;
            this.offset = 0;
            this.step = 1;
        } else {
            this.nlines = imgInfo.rows;
            this.offset = 0;
            this.step = 1;
        }
        createImageLines();
    }

    private void createImageLines() {
        if (this.singleCursor) {
            this.imageLine = createImageLine();
            return;
        }
        this.imageLines = new ArrayList();
        for (int i = 0; i < this.nlines; i++) {
            this.imageLines.add(createImageLine());
        }
    }

    public T getImageLine(int n) {
        this.currentRow = n;
        if (this.singleCursor) {
            return this.imageLine;
        }
        return (IImageLine) this.imageLines.get(imageRowToMatrixRowStrict(n));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasImageLine(int r4) {
        /*
        r3 = this;
        r0 = r3.singleCursor;
        r1 = 0;
        r2 = 1;
        if (r0 == 0) goto L_0x000d;
    L_0x0006:
        r0 = r3.currentRow;
        if (r0 != r4) goto L_0x000c;
    L_0x000a:
        r1 = 1;
        goto L_0x0014;
    L_0x000c:
        goto L_0x0014;
    L_0x000d:
        r0 = r3.imageRowToMatrixRowStrict(r4);
        if (r0 < 0) goto L_0x000c;
    L_0x0013:
        goto L_0x000a;
    L_0x0014:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.hjg.pngj.ImageLineSetDefault.hasImageLine(int):boolean");
    }

    public int size() {
        return this.nlines;
    }

    public int imageRowToMatrixRowStrict(int imrow) {
        imrow -= this.offset;
        int mrow = (imrow < 0 || imrow % this.step != 0) ? -1 : imrow / this.step;
        if (mrow < this.nlines) {
            return mrow;
        }
        return -1;
    }

    public int matrixRowToImageRow(int mrow) {
        return (this.step * mrow) + this.offset;
    }

    public int imageRowToMatrixRow(int imrow) {
        int r = (imrow - this.offset) / this.step;
        if (r < 0) {
            return 0;
        }
        return r < this.nlines ? r : this.nlines - 1;
    }

    public static IImageLineSetFactory<ImageLineInt> getFactoryInt() {
        return new C07331();
    }

    public static IImageLineSetFactory<ImageLineByte> getFactoryByte() {
        return new C07342();
    }
}
