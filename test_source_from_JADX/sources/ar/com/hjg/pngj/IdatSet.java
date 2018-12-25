package ar.com.hjg.pngj;

import java.util.Arrays;
import java.util.zip.Checksum;
import java.util.zip.Inflater;

public class IdatSet extends DeflatedChunksSet {
    protected final Deinterlacer deinterlacer;
    protected int[] filterUseStat;
    protected final ImageInfo imgInfo;
    protected byte[] rowUnfiltered;
    protected byte[] rowUnfilteredPrev;
    final RowInfo rowinfo;

    public IdatSet(String id, ImageInfo iminfo, Deinterlacer deinterlacer) {
        this(id, iminfo, deinterlacer, null, null);
    }

    public IdatSet(String id, ImageInfo iminfo, Deinterlacer deinterlacer, Inflater inf, byte[] buffer) {
        super(id, (deinterlacer != null ? deinterlacer.getBytesToRead() : iminfo.bytesPerRow) + 1, iminfo.bytesPerRow + 1, inf, buffer);
        this.filterUseStat = new int[5];
        this.imgInfo = iminfo;
        this.deinterlacer = deinterlacer;
        this.rowinfo = new RowInfo(iminfo, deinterlacer);
    }

    public void unfilterRow() {
        unfilterRow(this.rowinfo.bytesRow);
    }

    protected void unfilterRow(int nbytes) {
        if (this.rowUnfiltered == null || this.rowUnfiltered.length < this.row.length) {
            this.rowUnfiltered = new byte[this.row.length];
            this.rowUnfilteredPrev = new byte[this.row.length];
        }
        if (this.rowinfo.rowNsubImg == 0) {
            Arrays.fill(this.rowUnfiltered, (byte) 0);
        }
        byte[] tmp = this.rowUnfiltered;
        this.rowUnfiltered = this.rowUnfilteredPrev;
        this.rowUnfilteredPrev = tmp;
        int ftn = this.row[0];
        FilterType ft = FilterType.getByVal(ftn);
        if (ft == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Filter type ");
            stringBuilder.append(ftn);
            stringBuilder.append(" invalid");
            throw new PngjInputException(stringBuilder.toString());
        }
        int[] iArr = this.filterUseStat;
        iArr[ftn] = iArr[ftn] + 1;
        this.rowUnfiltered[0] = this.row[0];
        switch (ft) {
            case FILTER_NONE:
                unfilterRowNone(nbytes);
                return;
            case FILTER_SUB:
                unfilterRowSub(nbytes);
                return;
            case FILTER_UP:
                unfilterRowUp(nbytes);
                return;
            case FILTER_AVERAGE:
                unfilterRowAverage(nbytes);
                return;
            case FILTER_PAETH:
                unfilterRowPaeth(nbytes);
                return;
            default:
                stringBuilder = new StringBuilder();
                stringBuilder.append("Filter type ");
                stringBuilder.append(ftn);
                stringBuilder.append(" not implemented");
                throw new PngjInputException(stringBuilder.toString());
        }
    }

    private void unfilterRowAverage(int nbytes) {
        int i = 1;
        int j = 1 - this.imgInfo.bytesPixel;
        while (i <= nbytes) {
            this.rowUnfiltered[i] = (byte) (this.row[i] + (((this.rowUnfilteredPrev[i] & 255) + (j > 0 ? this.rowUnfiltered[j] & 255 : 0)) / 2));
            i++;
            j++;
        }
    }

    private void unfilterRowNone(int nbytes) {
        for (int i = 1; i <= nbytes; i++) {
            this.rowUnfiltered[i] = this.row[i];
        }
    }

    private void unfilterRowPaeth(int nbytes) {
        int i = 1;
        int j = 1 - this.imgInfo.bytesPixel;
        while (i <= nbytes) {
            int y = 0;
            int x = j > 0 ? this.rowUnfiltered[j] & 255 : 0;
            if (j > 0) {
                y = this.rowUnfilteredPrev[j] & 255;
            }
            this.rowUnfiltered[i] = (byte) (this.row[i] + PngHelperInternal.filterPaethPredictor(x, this.rowUnfilteredPrev[i] & 255, y));
            i++;
            j++;
        }
    }

    private void unfilterRowSub(int nbytes) {
        for (int i = 1; i <= this.imgInfo.bytesPixel; i++) {
            this.rowUnfiltered[i] = this.row[i];
        }
        int j = 1;
        int i2 = this.imgInfo.bytesPixel + 1;
        while (i2 <= nbytes) {
            this.rowUnfiltered[i2] = (byte) (this.row[i2] + this.rowUnfiltered[j]);
            i2++;
            j++;
        }
    }

    private void unfilterRowUp(int nbytes) {
        for (int i = 1; i <= nbytes; i++) {
            this.rowUnfiltered[i] = (byte) (this.row[i] + this.rowUnfilteredPrev[i]);
        }
    }

    protected void preProcessRow() {
        super.preProcessRow();
        this.rowinfo.update(getRown());
        unfilterRow();
        this.rowinfo.updateBuf(this.rowUnfiltered, this.rowinfo.bytesRow + 1);
    }

    protected int processRowCallback() {
        return advanceToNextRow();
    }

    protected void processDoneCallback() {
    }

    public int advanceToNextRow() {
        int bytesNextRow;
        int i = 0;
        if (this.deinterlacer == null) {
            if (getRown() < this.imgInfo.rows - 1) {
                i = this.imgInfo.bytesPerRow + 1;
            }
            bytesNextRow = i;
        } else {
            if (this.deinterlacer.nextRow()) {
                i = this.deinterlacer.getBytesToRead() + 1;
            }
            bytesNextRow = i;
        }
        if (!isCallbackMode()) {
            prepareForNextRow(bytesNextRow);
        }
        return bytesNextRow;
    }

    public boolean isRowReady() {
        return isWaitingForMoreInput() ^ 1;
    }

    public byte[] getUnfilteredRow() {
        return this.rowUnfiltered;
    }

    public Deinterlacer getDeinterlacer() {
        return this.deinterlacer;
    }

    void updateCrcs(Checksum... idatCrcs) {
        for (Checksum idatCrca : idatCrcs) {
            if (idatCrca != null) {
                idatCrca.update(getUnfilteredRow(), 1, getRowFilled() - 1);
            }
        }
    }

    public void close() {
        super.close();
        this.rowUnfiltered = null;
        this.rowUnfilteredPrev = null;
    }

    public int[] getFilterUseStat() {
        return this.filterUseStat;
    }
}
