package ar.com.hjg.pngj.pixels;

import android.support.v4.media.session.PlaybackStateCompat;
import ar.com.hjg.pngj.FilterType;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjOutputException;
import java.util.Arrays;

public class PixelsWriterDefault extends PixelsWriter {
    protected int adaptMaxSkip;
    protected int adaptNextRow = 0;
    protected double adaptSkipIncreaseFactor;
    protected int adaptSkipIncreaseSinceRow;
    protected FilterType curfilterType;
    protected FiltersPerformance filtersPerformance;
    protected byte[] rowb;
    protected byte[] rowbfilter;
    protected byte[] rowbprev;

    public PixelsWriterDefault(ImageInfo imgInfo) {
        super(imgInfo);
        this.filtersPerformance = new FiltersPerformance(imgInfo);
    }

    protected void initParams() {
        super.initParams();
        if (this.rowb == null || this.rowb.length < this.buflen) {
            this.rowb = new byte[this.buflen];
        }
        if (this.rowbfilter == null || this.rowbfilter.length < this.buflen) {
            this.rowbfilter = new byte[this.buflen];
        }
        if (this.rowbprev != null) {
            if (this.rowbprev.length >= this.buflen) {
                Arrays.fill(this.rowbprev, (byte) 0);
                if (this.imgInfo.cols < 3 && !FilterType.isValidStandard(this.filterType)) {
                    this.filterType = FilterType.FILTER_DEFAULT;
                }
                if (this.imgInfo.rows < 3 && !FilterType.isValidStandard(this.filterType)) {
                    this.filterType = FilterType.FILTER_DEFAULT;
                }
                if (this.imgInfo.getTotalPixels() <= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID && !FilterType.isValidStandard(this.filterType)) {
                    this.filterType = getDefaultFilter();
                }
                if (FilterType.isAdaptive(this.filterType)) {
                    this.adaptNextRow = 0;
                    if (this.filterType == FilterType.FILTER_ADAPTIVE_FAST) {
                        this.adaptMaxSkip = 200;
                        this.adaptSkipIncreaseSinceRow = 3;
                        this.adaptSkipIncreaseFactor = 0.25d;
                    } else if (this.filterType == FilterType.FILTER_ADAPTIVE_MEDIUM) {
                        this.adaptMaxSkip = 8;
                        this.adaptSkipIncreaseSinceRow = 32;
                        this.adaptSkipIncreaseFactor = 0.0125d;
                    } else if (this.filterType != FilterType.FILTER_ADAPTIVE_FULL) {
                        this.adaptMaxSkip = 0;
                        this.adaptSkipIncreaseSinceRow = 128;
                        this.adaptSkipIncreaseFactor = 0.008333333333333333d;
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("bad filter ");
                        stringBuilder.append(this.filterType);
                        throw new PngjOutputException(stringBuilder.toString());
                    }
                }
            }
        }
        this.rowbprev = new byte[this.buflen];
        this.filterType = FilterType.FILTER_DEFAULT;
        this.filterType = FilterType.FILTER_DEFAULT;
        this.filterType = getDefaultFilter();
        if (FilterType.isAdaptive(this.filterType)) {
            this.adaptNextRow = 0;
            if (this.filterType == FilterType.FILTER_ADAPTIVE_FAST) {
                this.adaptMaxSkip = 200;
                this.adaptSkipIncreaseSinceRow = 3;
                this.adaptSkipIncreaseFactor = 0.25d;
            } else if (this.filterType == FilterType.FILTER_ADAPTIVE_MEDIUM) {
                this.adaptMaxSkip = 8;
                this.adaptSkipIncreaseSinceRow = 32;
                this.adaptSkipIncreaseFactor = 0.0125d;
            } else if (this.filterType != FilterType.FILTER_ADAPTIVE_FULL) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("bad filter ");
                stringBuilder2.append(this.filterType);
                throw new PngjOutputException(stringBuilder2.toString());
            } else {
                this.adaptMaxSkip = 0;
                this.adaptSkipIncreaseSinceRow = 128;
                this.adaptSkipIncreaseFactor = 0.008333333333333333d;
            }
        }
    }

    protected void filterAndWrite(byte[] rowb) {
        if (rowb != this.rowb) {
            throw new RuntimeException("??");
        }
        decideCurFilterType();
        sendToCompressedStream(filterRowWithFilterType(this.curfilterType, rowb, this.rowbprev, this.rowbfilter));
        byte[] aux = this.rowb;
        this.rowb = this.rowbprev;
        this.rowbprev = aux;
    }

    protected void decideCurFilterType() {
        if (FilterType.isValidStandard(getFilterType())) {
            this.curfilterType = getFilterType();
        } else {
            int i = 0;
            if (getFilterType() == FilterType.FILTER_PRESERVE) {
                this.curfilterType = FilterType.getByVal(this.rowb[0]);
            } else if (getFilterType() == FilterType.FILTER_CYCLIC) {
                this.curfilterType = FilterType.getByVal(this.currentRow % 5);
            } else if (getFilterType() == FilterType.FILTER_DEFAULT) {
                setFilterType(getDefaultFilter());
                this.curfilterType = getFilterType();
            } else if (!FilterType.isAdaptive(getFilterType())) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("not implemented filter: ");
                stringBuilder.append(getFilterType());
                throw new PngjOutputException(stringBuilder.toString());
            } else if (this.currentRow == this.adaptNextRow) {
                for (FilterType ftype : FilterType.getAllStandard()) {
                    this.filtersPerformance.updateFromRaw(ftype, this.rowb, this.rowbprev, this.currentRow);
                }
                this.curfilterType = this.filtersPerformance.getPreferred();
                if (this.currentRow >= this.adaptSkipIncreaseSinceRow) {
                    i = (int) Math.round(((double) (this.currentRow - this.adaptSkipIncreaseSinceRow)) * this.adaptSkipIncreaseFactor);
                }
                int skip = i;
                if (skip > this.adaptMaxSkip) {
                    skip = this.adaptMaxSkip;
                }
                if (this.currentRow == 0) {
                    skip = 0;
                }
                this.adaptNextRow = (this.currentRow + 1) + skip;
            }
        }
        if (this.currentRow == 0 && this.curfilterType != FilterType.FILTER_NONE && this.curfilterType != FilterType.FILTER_SUB) {
            this.curfilterType = FilterType.FILTER_SUB;
        }
    }

    public byte[] getRowb() {
        if (!this.initdone) {
            init();
        }
        return this.rowb;
    }

    public void close() {
        super.close();
    }

    public void setPreferenceForNone(double preferenceForNone) {
        this.filtersPerformance.setPreferenceForNone(preferenceForNone);
    }

    public void tuneMemory(double m) {
        this.filtersPerformance.tuneMemory(m);
    }

    public void setFilterWeights(double[] weights) {
        this.filtersPerformance.setFilterWeights(weights);
    }
}
