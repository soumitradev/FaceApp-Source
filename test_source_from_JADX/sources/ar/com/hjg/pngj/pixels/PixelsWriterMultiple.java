package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.FilterType;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import java.util.LinkedList;

public class PixelsWriterMultiple extends PixelsWriter {
    protected static final int HINT_MEMORY_DEFAULT_KB = 100;
    protected int bandNum;
    protected CompressorStream[] filterBank = new CompressorStream[6];
    protected byte[] filteredRowTmp;
    protected byte[][] filteredRows = new byte[5][];
    protected FiltersPerformance filtersPerf;
    protected int firstRowInThisBand;
    protected int hintMemoryKb;
    private int hintRowsPerBand;
    protected int lastRowInThisBand;
    protected int rowInBand;
    protected LinkedList<byte[]> rows;
    protected int rowsPerBand;
    protected int rowsPerBandCurrent;
    private boolean tryAdaptive;
    private boolean useLz4;

    public PixelsWriterMultiple(ImageInfo imgInfo) {
        super(imgInfo);
        int i = 0;
        this.rowsPerBand = 0;
        this.rowsPerBandCurrent = 0;
        this.rowInBand = -1;
        this.bandNum = -1;
        this.tryAdaptive = true;
        this.hintMemoryKb = 100;
        this.hintRowsPerBand = 1000;
        this.useLz4 = true;
        this.filtersPerf = new FiltersPerformance(imgInfo);
        this.rows = new LinkedList();
        while (i < 2) {
            this.rows.add(new byte[this.buflen]);
            i++;
        }
        this.filteredRowTmp = new byte[this.buflen];
    }

    protected void filterAndWrite(byte[] rowb) {
        if (!this.initdone) {
            init();
        }
        if (rowb != this.rows.get(0)) {
            throw new RuntimeException("?");
        }
        int i$;
        int len$;
        setBandFromNewRown();
        byte[] rowbprev = (byte[]) this.rows.get(1);
        for (FilterType ftype : FilterType.getAllStandardNoneLast()) {
            if (this.currentRow != 0 || ftype == FilterType.FILTER_NONE || ftype == FilterType.FILTER_SUB) {
                byte[] filtered = filterRowWithFilterType(ftype, rowb, rowbprev, this.filteredRows[ftype.val]);
                this.filterBank[ftype.val].write(filtered);
                if (this.currentRow == 0 && ftype == FilterType.FILTER_SUB) {
                    this.filterBank[FilterType.FILTER_PAETH.val].write(filtered);
                    this.filterBank[FilterType.FILTER_AVERAGE.val].write(filtered);
                    this.filterBank[FilterType.FILTER_UP.val].write(filtered);
                }
                if (this.tryAdaptive) {
                    this.filtersPerf.updateFromFiltered(ftype, filtered, this.currentRow);
                }
            }
        }
        this.filteredRows[0] = rowb;
        if (this.tryAdaptive) {
            this.filterBank[5].write(this.filteredRows[this.filtersPerf.getPreferred().val]);
        }
        if (this.currentRow == this.lastRowInThisBand) {
            byte[] filtersAdapt = this.filterBank[getBestCompressor()].getFirstBytes();
            len$ = this.firstRowInThisBand;
            i$ = 0;
            int j = this.lastRowInThisBand - this.firstRowInThisBand;
            while (len$ <= this.lastRowInThisBand) {
                byte[] filtered2;
                int fti = filtersAdapt[i$];
                if (len$ != this.lastRowInThisBand) {
                    filtered2 = filterRowWithFilterType(FilterType.getByVal(fti), (byte[]) this.rows.get(j), (byte[]) this.rows.get(j + 1), this.filteredRowTmp);
                } else {
                    filtered2 = this.filteredRows[fti];
                }
                sendToCompressedStream(filtered2);
                len$++;
                j--;
                i$++;
            }
        }
        if (this.rows.size() > this.rowsPerBandCurrent) {
            this.rows.addFirst(this.rows.removeLast());
        } else {
            this.rows.addFirst(new byte[this.buflen]);
        }
    }

    public byte[] getRowb() {
        return (byte[]) this.rows.get(0);
    }

    private void setBandFromNewRown() {
        boolean newBand;
        boolean z = false;
        if (this.currentRow != 0) {
            if (this.currentRow <= this.lastRowInThisBand) {
                newBand = false;
                if (this.currentRow == 0) {
                    this.bandNum = -1;
                }
                if (newBand) {
                    this.rowInBand++;
                } else {
                    this.bandNum++;
                    this.rowInBand = 0;
                }
                if (newBand) {
                    this.firstRowInThisBand = this.currentRow;
                    this.lastRowInThisBand = (this.firstRowInThisBand + this.rowsPerBand) - 1;
                    if ((this.firstRowInThisBand + (this.rowsPerBand * 2)) - 1 >= this.imgInfo.rows) {
                        this.lastRowInThisBand = this.imgInfo.rows - 1;
                    }
                    this.rowsPerBandCurrent = (this.lastRowInThisBand + 1) - this.firstRowInThisBand;
                    if (this.rowsPerBandCurrent > 3) {
                        if (this.rowsPerBandCurrent < 10 || this.imgInfo.bytesPerRow >= 64) {
                            z = true;
                        }
                    }
                    this.tryAdaptive = z;
                    rebuildFiltersBank();
                }
            }
        }
        newBand = true;
        if (this.currentRow == 0) {
            this.bandNum = -1;
        }
        if (newBand) {
            this.rowInBand++;
        } else {
            this.bandNum++;
            this.rowInBand = 0;
        }
        if (newBand) {
            this.firstRowInThisBand = this.currentRow;
            this.lastRowInThisBand = (this.firstRowInThisBand + this.rowsPerBand) - 1;
            if ((this.firstRowInThisBand + (this.rowsPerBand * 2)) - 1 >= this.imgInfo.rows) {
                this.lastRowInThisBand = this.imgInfo.rows - 1;
            }
            this.rowsPerBandCurrent = (this.lastRowInThisBand + 1) - this.firstRowInThisBand;
            if (this.rowsPerBandCurrent > 3) {
                if (this.rowsPerBandCurrent < 10) {
                }
                z = true;
            }
            this.tryAdaptive = z;
            rebuildFiltersBank();
        }
    }

    private void rebuildFiltersBank() {
        long bytesPerBandCurrent = ((long) this.rowsPerBandCurrent) * ((long) this.buflen);
        for (int i = 0; i <= 5; i++) {
            CompressorStream cp;
            CompressorStream cp2 = this.filterBank[i];
            if (cp2 != null) {
                if (cp2.totalbytes == bytesPerBandCurrent) {
                    cp2.reset();
                    cp2.setStoreFirstByte(true, this.rowsPerBandCurrent);
                }
            }
            if (cp2 != null) {
                cp2.close();
            }
            if (this.useLz4) {
                cp = new CompressorStreamLz4(null, this.buflen, bytesPerBandCurrent);
            } else {
                cp = new CompressorStreamDeflater(null, this.buflen, bytesPerBandCurrent, 4, 0);
            }
            cp2 = cp;
            this.filterBank[i] = cp2;
            cp2.setStoreFirstByte(true, this.rowsPerBandCurrent);
        }
    }

    private int computeInitialRowsPerBand() {
        int r = (int) (((((double) this.hintMemoryKb) * 1024.0d) / ((double) (this.imgInfo.bytesPerRow + 1))) - 5.0d);
        if (r < 1) {
            r = 1;
        }
        if (this.hintRowsPerBand > 0 && r > this.hintRowsPerBand) {
            r = this.hintRowsPerBand;
        }
        if (r > this.imgInfo.rows) {
            r = this.imgInfo.rows;
        }
        if (r > 2 && r > this.imgInfo.rows / 8) {
            int k = (this.imgInfo.rows + (r - 1)) / r;
            r = (this.imgInfo.rows + (k / 2)) / k;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("rows :");
        stringBuilder.append(r);
        stringBuilder.append("/");
        stringBuilder.append(this.imgInfo.rows);
        PngHelperInternal.debug(stringBuilder.toString());
        return r;
    }

    private int getBestCompressor() {
        double bestcr = Double.MAX_VALUE;
        int bestb = -1;
        int i = this.tryAdaptive ? 5 : 4;
        while (i >= 0) {
            double cr = this.filterBank[i].getCompressionRatio();
            if (cr <= bestcr) {
                bestb = i;
                bestcr = cr;
            }
            i--;
        }
        return bestb;
    }

    protected void initParams() {
        if (this.imgInfo.cols < 3 && !FilterType.isValidStandard(this.filterType)) {
            this.filterType = FilterType.FILTER_DEFAULT;
        }
        if (this.imgInfo.rows < 3 && !FilterType.isValidStandard(this.filterType)) {
            this.filterType = FilterType.FILTER_DEFAULT;
        }
        int i = 1;
        while (i <= 4) {
            if (this.filteredRows[i] == null || this.filteredRows[i].length < this.buflen) {
                this.filteredRows[i] = new byte[this.buflen];
            }
            i++;
        }
        if (this.rowsPerBand == 0) {
            this.rowsPerBand = computeInitialRowsPerBand();
        }
    }

    public void close() {
        super.close();
        this.rows.clear();
        for (CompressorStream f : this.filterBank) {
            f.close();
        }
    }

    public void setHintMemoryKb(int hintMemoryKb) {
        int i = 10000;
        if (hintMemoryKb <= 0) {
            i = 100;
        } else if (hintMemoryKb <= 10000) {
            i = hintMemoryKb;
        }
        this.hintMemoryKb = i;
    }

    public void setHintRowsPerBand(int hintRowsPerBand) {
        this.hintRowsPerBand = hintRowsPerBand;
    }

    public void setUseLz4(boolean lz4) {
        this.useLz4 = lz4;
    }

    public FiltersPerformance getFiltersPerf() {
        return this.filtersPerf;
    }
}
