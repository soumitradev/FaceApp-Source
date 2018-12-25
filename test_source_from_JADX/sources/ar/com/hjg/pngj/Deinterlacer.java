package ar.com.hjg.pngj;

public class Deinterlacer {
    private int cols;
    private int currRowReal = -1;
    private int currRowSeq;
    private int currRowSubimg = -1;
    int dX;
    int dXsamples;
    int dY;
    private boolean ended = false;
    final ImageInfo imi;
    int oX;
    int oXsamples;
    int oY;
    private int pass;
    private int rows;
    int totalRows = 0;

    public Deinterlacer(ImageInfo iminfo) {
        this.imi = iminfo;
        this.pass = 0;
        this.currRowSeq = 0;
        setPass(1);
        setRow(0);
    }

    private void setRow(int n) {
        this.currRowSubimg = n;
        this.currRowReal = (this.dY * n) + this.oY;
        if (this.currRowReal >= 0) {
            if (this.currRowReal < this.imi.rows) {
                return;
            }
        }
        throw new PngjExceptionInternal("bad row - this should not happen");
    }

    boolean nextRow() {
        this.currRowSeq++;
        if (this.rows != 0) {
            if (this.currRowSubimg < this.rows - 1) {
                setRow(this.currRowSubimg + 1);
                return true;
            }
        }
        if (this.pass == 7) {
            this.ended = true;
            return false;
        }
        setPass(this.pass + 1);
        if (this.rows == 0) {
            this.currRowSeq--;
            return nextRow();
        }
        setRow(0);
        return true;
    }

    boolean isEnded() {
        return this.ended;
    }

    void setPass(int p) {
        if (this.pass != p) {
            this.pass = p;
            byte[] pp = paramsForPass(p);
            this.dX = pp[0];
            this.dY = pp[1];
            this.oX = pp[2];
            this.oY = pp[3];
            this.rows = this.imi.rows > this.oY ? (((this.imi.rows + this.dY) - 1) - this.oY) / this.dY : 0;
            this.cols = this.imi.cols > this.oX ? (((this.imi.cols + this.dX) - 1) - this.oX) / this.dX : 0;
            if (this.cols == 0) {
                this.rows = 0;
            }
            this.dXsamples = this.dX * this.imi.channels;
            this.oXsamples = this.oX * this.imi.channels;
        }
    }

    static byte[] paramsForPass(int p) {
        switch (p) {
            case 1:
                return new byte[]{(byte) 8, (byte) 8, (byte) 0, (byte) 0};
            case 2:
                return new byte[]{(byte) 8, (byte) 8, (byte) 4, (byte) 0};
            case 3:
                return new byte[]{(byte) 4, (byte) 8, (byte) 0, (byte) 4};
            case 4:
                return new byte[]{(byte) 4, (byte) 4, (byte) 2, (byte) 0};
            case 5:
                return new byte[]{(byte) 2, (byte) 4, (byte) 0, (byte) 2};
            case 6:
                return new byte[]{(byte) 2, (byte) 2, (byte) 1, (byte) 0};
            case 7:
                return new byte[]{(byte) 1, (byte) 2, (byte) 0, (byte) 1};
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("bad interlace pass");
                stringBuilder.append(p);
                throw new PngjExceptionInternal(stringBuilder.toString());
        }
    }

    int getCurrRowSubimg() {
        return this.currRowSubimg;
    }

    int getCurrRowReal() {
        return this.currRowReal;
    }

    int getPass() {
        return this.pass;
    }

    int getRows() {
        return this.rows;
    }

    int getCols() {
        return this.cols;
    }

    public int getPixelsToRead() {
        return getCols();
    }

    public int getBytesToRead() {
        return ((this.imi.bitspPixel * getPixelsToRead()) + 7) / 8;
    }

    public int getdY() {
        return this.dY;
    }

    public int getdX() {
        return this.dX;
    }

    public int getoY() {
        return this.oY;
    }

    public int getoX() {
        return this.oX;
    }

    public int getTotalRows() {
        if (this.totalRows == 0) {
            for (int p = 1; p <= 7; p++) {
                byte[] pp = paramsForPass(p);
                int i = 0;
                int rows = this.imi.rows > pp[3] ? (((this.imi.rows + pp[1]) - 1) - pp[3]) / pp[1] : 0;
                if (this.imi.cols > pp[2]) {
                    i = (((this.imi.cols + pp[0]) - 1) - pp[2]) / pp[0];
                }
                int cols = i;
                if (rows > 0 && cols > 0) {
                    this.totalRows += rows;
                }
            }
        }
        return this.totalRows;
    }

    public long getTotalRawBytes() {
        Deinterlacer deinterlacer = this;
        long bytes = 0;
        for (int p = 1; p <= 7; p++) {
            byte[] pp = paramsForPass(p);
            int i = 0;
            int rows = deinterlacer.imi.rows > pp[3] ? (((deinterlacer.imi.rows + pp[1]) - 1) - pp[3]) / pp[1] : 0;
            if (deinterlacer.imi.cols > pp[2]) {
                i = (((deinterlacer.imi.cols + pp[0]) - 1) - pp[2]) / pp[0];
            }
            int cols = i;
            int bytesr = ((deinterlacer.imi.bitspPixel * cols) + 7) / 8;
            if (rows > 0 && cols > 0) {
                bytes += ((long) rows) * (((long) bytesr) + 1);
            }
        }
        return bytes;
    }

    public int getCurrRowSeq() {
        return this.currRowSeq;
    }
}
