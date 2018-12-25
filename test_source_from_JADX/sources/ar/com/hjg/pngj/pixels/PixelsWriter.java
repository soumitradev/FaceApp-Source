package ar.com.hjg.pngj.pixels;

import android.support.v4.media.session.PlaybackStateCompat;
import ar.com.hjg.pngj.FilterType;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjOutputException;
import java.io.OutputStream;

public abstract class PixelsWriter {
    protected final int buflen;
    protected final int bytesPixel;
    protected final int bytesRow;
    private CompressorStream compressorStream;
    protected int currentRow;
    protected int deflaterCompLevel = 6;
    protected int deflaterStrategy = 0;
    protected FilterType filterType;
    private int[] filtersUsed = new int[5];
    protected final ImageInfo imgInfo;
    protected boolean initdone = false;
    private OutputStream os;

    protected abstract void filterAndWrite(byte[] bArr);

    public abstract byte[] getRowb();

    public PixelsWriter(ImageInfo imgInfo) {
        this.imgInfo = imgInfo;
        this.bytesRow = imgInfo.bytesPerRow;
        this.buflen = this.bytesRow + 1;
        this.bytesPixel = imgInfo.bytesPixel;
        this.currentRow = -1;
        this.filterType = FilterType.FILTER_DEFAULT;
    }

    public final void processRow(byte[] rowb) {
        if (!this.initdone) {
            init();
        }
        this.currentRow++;
        filterAndWrite(rowb);
    }

    protected void sendToCompressedStream(byte[] rowf) {
        this.compressorStream.write(rowf, 0, rowf.length);
        int[] iArr = this.filtersUsed;
        byte b = rowf[0];
        iArr[b] = iArr[b] + 1;
    }

    protected final byte[] filterRowWithFilterType(FilterType _filterType, byte[] _rowb, byte[] _rowbprev, byte[] _rowf) {
        if (_filterType == FilterType.FILTER_NONE) {
            _rowf = _rowb;
        }
        _rowf[0] = (byte) _filterType.val;
        int i;
        int i2;
        switch (_filterType) {
            case FILTER_NONE:
                break;
            case FILTER_PAETH:
                for (i = 1; i <= this.bytesPixel; i++) {
                    _rowf[i] = (byte) PngHelperInternal.filterRowPaeth(_rowb[i], 0, _rowbprev[i] & 255, 0);
                }
                i = 1;
                i2 = this.bytesPixel + 1;
                while (i2 <= this.bytesRow) {
                    _rowf[i2] = (byte) PngHelperInternal.filterRowPaeth(_rowb[i2], _rowb[i] & 255, _rowbprev[i2] & 255, _rowbprev[i] & 255);
                    i2++;
                    i++;
                }
                break;
            case FILTER_SUB:
                for (i = 1; i <= this.bytesPixel; i++) {
                    _rowf[i] = _rowb[i];
                }
                i = 1;
                i2 = this.bytesPixel + 1;
                while (i2 <= this.bytesRow) {
                    _rowf[i2] = (byte) (_rowb[i2] - _rowb[i]);
                    i2++;
                    i++;
                }
                break;
            case FILTER_AVERAGE:
                for (i = 1; i <= this.bytesPixel; i++) {
                    _rowf[i] = (byte) (_rowb[i] - ((_rowbprev[i] & 255) / 2));
                }
                i = 1;
                i2 = this.bytesPixel + 1;
                while (i2 <= this.bytesRow) {
                    _rowf[i2] = (byte) (_rowb[i2] - (((_rowbprev[i2] & 255) + (_rowb[i] & 255)) / 2));
                    i2++;
                    i++;
                }
                break;
            case FILTER_UP:
                i = 1;
                while (i <= this.bytesRow) {
                    _rowf[i] = (byte) (_rowb[i] - _rowbprev[i]);
                    i++;
                }
                i2 = i;
                break;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Filter type not recognized: ");
                stringBuilder.append(_filterType);
                throw new PngjOutputException(stringBuilder.toString());
        }
        return _rowf;
    }

    protected final void init() {
        if (!this.initdone) {
            initParams();
            this.initdone = true;
        }
    }

    protected void initParams() {
        if (this.compressorStream == null) {
            this.compressorStream = new CompressorStreamDeflater(this.os, this.buflen, this.imgInfo.getTotalRawBytes(), this.deflaterCompLevel, this.deflaterStrategy);
        }
    }

    public void close() {
        if (this.compressorStream != null) {
            this.compressorStream.close();
        }
    }

    public void setDeflaterStrategy(Integer deflaterStrategy) {
        this.deflaterStrategy = deflaterStrategy.intValue();
    }

    public void setDeflaterCompLevel(Integer deflaterCompLevel) {
        this.deflaterCompLevel = deflaterCompLevel.intValue();
    }

    public Integer getDeflaterCompLevel() {
        return Integer.valueOf(this.deflaterCompLevel);
    }

    public final void setOs(OutputStream datStream) {
        this.os = datStream;
    }

    public OutputStream getOs() {
        return this.os;
    }

    public final FilterType getFilterType() {
        return this.filterType;
    }

    public final void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public double getCompression() {
        return this.compressorStream.isDone() ? this.compressorStream.getCompressionRatio() : 1.0d;
    }

    public void setCompressorStream(CompressorStream compressorStream) {
        this.compressorStream = compressorStream;
    }

    public long getTotalBytesToWrite() {
        return this.imgInfo.getTotalRawBytes();
    }

    protected FilterType getDefaultFilter() {
        if (!this.imgInfo.indexed) {
            if (this.imgInfo.bitDepth >= 8) {
                if (this.imgInfo.getTotalPixels() < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
                    return FilterType.FILTER_NONE;
                }
                if (this.imgInfo.rows == 1) {
                    return FilterType.FILTER_SUB;
                }
                if (this.imgInfo.cols == 1) {
                    return FilterType.FILTER_UP;
                }
                return FilterType.FILTER_PAETH;
            }
        }
        return FilterType.FILTER_NONE;
    }

    public final String getFiltersUsed() {
        return String.format("%d,%d,%d,%d,%d", new Object[]{Integer.valueOf((int) (((((double) this.filtersUsed[0]) * 100.0d) / ((double) this.imgInfo.rows)) + 0.5d)), Integer.valueOf((int) (((((double) this.filtersUsed[1]) * 100.0d) / ((double) this.imgInfo.rows)) + 0.5d)), Integer.valueOf((int) (((((double) this.filtersUsed[2]) * 100.0d) / ((double) this.imgInfo.rows)) + 0.5d)), Integer.valueOf((int) (((((double) this.filtersUsed[3]) * 100.0d) / ((double) this.imgInfo.rows)) + 0.5d)), Integer.valueOf((int) (((((double) this.filtersUsed[4]) * 100.0d) / ((double) this.imgInfo.rows)) + 0.5d))});
    }
}
