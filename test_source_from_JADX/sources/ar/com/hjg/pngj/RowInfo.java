package ar.com.hjg.pngj;

class RowInfo {
    byte[] buf;
    int buflen;
    int bytesRow;
    int colsSubImg;
    int dX;
    int dY;
    public final Deinterlacer deinterlacer;
    public final ImageInfo imgInfo;
    public final boolean imode;
    int oX;
    int oY;
    int pass;
    int rowNreal;
    int rowNseq;
    int rowNsubImg;
    int rowsSubImg;

    public RowInfo(ImageInfo imgInfo, Deinterlacer deinterlacer) {
        this.imgInfo = imgInfo;
        this.deinterlacer = deinterlacer;
        this.imode = deinterlacer != null;
    }

    void update(int rowseq) {
        this.rowNseq = rowseq;
        if (this.imode) {
            this.pass = this.deinterlacer.getPass();
            this.dX = this.deinterlacer.dX;
            this.dY = this.deinterlacer.dY;
            this.oX = this.deinterlacer.oX;
            this.oY = this.deinterlacer.oY;
            this.rowNreal = this.deinterlacer.getCurrRowReal();
            this.rowNsubImg = this.deinterlacer.getCurrRowSubimg();
            this.rowsSubImg = this.deinterlacer.getRows();
            this.colsSubImg = this.deinterlacer.getCols();
            this.bytesRow = ((this.imgInfo.bitspPixel * this.colsSubImg) + 7) / 8;
            return;
        }
        this.pass = 1;
        this.dY = 1;
        this.dX = 1;
        this.oY = 0;
        this.oX = 0;
        this.rowNsubImg = rowseq;
        this.rowNreal = rowseq;
        this.rowsSubImg = this.imgInfo.rows;
        this.colsSubImg = this.imgInfo.cols;
        this.bytesRow = this.imgInfo.bytesPerRow;
    }

    void updateBuf(byte[] buf, int buflen) {
        this.buf = buf;
        this.buflen = buflen;
    }
}
