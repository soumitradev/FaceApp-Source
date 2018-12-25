package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.FilterType;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjExceptionInternal;
import java.util.Arrays;
import org.catrobat.catroid.common.BrickValues;

public class FiltersPerformance {
    public static final double[] FILTER_WEIGHTS_DEFAULT = new double[]{0.73d, 1.03d, 0.97d, 1.11d, 1.22d};
    private static final double LOG2NI = (-1.0d / Math.log(2.0d));
    private double[] absum = new double[5];
    private double[] cost = new double[5];
    private double[] entropy = new double[5];
    private double[] filter_weights = new double[]{-1.0d, -1.0d, -1.0d, -1.0d, -1.0d};
    private int[] histog = new int[256];
    private final ImageInfo iminfo;
    private boolean initdone = false;
    private int lastprefered = -1;
    private int lastrow = -1;
    private double memoryA = 0.7d;
    private double preferenceForNone = 1.0d;

    public FiltersPerformance(ImageInfo imgInfo) {
        this.iminfo = imgInfo;
    }

    private void init() {
        if (this.filter_weights[0] < BrickValues.SET_COLOR_TO) {
            System.arraycopy(FILTER_WEIGHTS_DEFAULT, 0, this.filter_weights, 0, 5);
            double wNone = this.filter_weights[0];
            if (this.iminfo.bitDepth == 16) {
                wNone = 1.2d;
            } else if (this.iminfo.alpha) {
                wNone = 0.8d;
            } else if (this.iminfo.indexed || this.iminfo.bitDepth < 8) {
                wNone = 0.4d;
            }
            this.filter_weights[0] = wNone / this.preferenceForNone;
        }
        Arrays.fill(this.cost, 1.0d);
        this.initdone = true;
    }

    public void updateFromFiltered(FilterType ftype, byte[] rowff, int rown) {
        updateFromRawOrFiltered(ftype, rowff, null, null, rown);
    }

    public void updateFromRaw(FilterType ftype, byte[] rowb, byte[] rowbprev, int rown) {
        updateFromRawOrFiltered(ftype, null, rowb, rowbprev, rown);
    }

    private void updateFromRawOrFiltered(FilterType ftype, byte[] rowff, byte[] rowb, byte[] rowbprev, int rown) {
        if (!this.initdone) {
            init();
        }
        if (rown != this.lastrow) {
            Arrays.fill(this.absum, Double.NaN);
            Arrays.fill(this.entropy, Double.NaN);
        }
        this.lastrow = rown;
        if (rowff != null) {
            computeHistogram(rowff);
        } else {
            computeHistogramForFilter(ftype, rowb, rowbprev);
        }
        if (ftype == FilterType.FILTER_NONE) {
            this.entropy[ftype.val] = computeEntropyFromHistogram();
        } else {
            this.absum[ftype.val] = computeAbsFromHistogram();
        }
    }

    public FilterType getPreferred() {
        int fi = 0;
        double vali = Double.MAX_VALUE;
        for (int i = 0; i < 5; i++) {
            double val;
            if (!Double.isNaN(this.absum[i])) {
                val = this.absum[i];
            } else if (Double.isNaN(this.entropy[i])) {
            } else {
                val = (Math.pow(2.0d, this.entropy[i]) - 1.0d) * 0.5d;
            }
            double val2 = (this.cost[i] * this.memoryA) + ((1.0d - this.memoryA) * (val * this.filter_weights[i]));
            this.cost[i] = val2;
            if (val2 < vali) {
                vali = val2;
                fi = i;
            }
        }
        this.lastprefered = fi;
        return FilterType.getByVal(this.lastprefered);
    }

    public final void computeHistogramForFilter(FilterType filterType, byte[] rowb, byte[] rowbprev) {
        Arrays.fill(this.histog, 0);
        int imax = this.iminfo.bytesPerRow;
        int i;
        int i2;
        int[] iArr;
        int i3;
        int j;
        switch (filterType) {
            case FILTER_NONE:
                i = 1;
                while (true) {
                    i2 = i;
                    if (i2 <= imax) {
                        iArr = this.histog;
                        i3 = rowb[i2] & 255;
                        iArr[i3] = iArr[i3] + 1;
                        i = i2 + 1;
                    } else {
                        return;
                    }
                }
            case FILTER_PAETH:
                for (i = 1; i <= imax; i++) {
                    int[] iArr2 = this.histog;
                    i3 = PngHelperInternal.filterRowPaeth(rowb[i], 0, rowbprev[i] & 255, 0);
                    iArr2[i3] = iArr2[i3] + 1;
                }
                j = 1;
                i2 = this.iminfo.bytesPixel + 1;
                while (i2 <= imax) {
                    iArr = this.histog;
                    i3 = PngHelperInternal.filterRowPaeth(rowb[i2], rowb[j] & 255, rowbprev[i2] & 255, rowbprev[j] & 255);
                    iArr[i3] = iArr[i3] + 1;
                    i2++;
                    j++;
                }
                return;
            case FILTER_SUB:
                for (j = 1; j <= this.iminfo.bytesPixel; j++) {
                    iArr = this.histog;
                    i2 = rowb[j] & 255;
                    iArr[i2] = iArr[i2] + 1;
                }
                i2 = this.iminfo.bytesPixel + 1;
                j = 1;
                while (i2 <= imax) {
                    iArr = this.histog;
                    i3 = (rowb[i2] - rowb[j]) & 255;
                    iArr[i3] = iArr[i3] + 1;
                    i2++;
                    j++;
                }
                return;
            case FILTER_UP:
                for (i2 = 1; i2 <= this.iminfo.bytesPerRow; i2++) {
                    iArr = this.histog;
                    i3 = (rowb[i2] - rowbprev[i2]) & 255;
                    iArr[i3] = iArr[i3] + 1;
                }
                return;
            case FILTER_AVERAGE:
                for (j = 1; j <= this.iminfo.bytesPixel; j++) {
                    iArr = this.histog;
                    i2 = ((rowb[j] & 255) - ((rowbprev[j] & 255) / 2)) & 255;
                    iArr[i2] = iArr[i2] + 1;
                }
                i2 = this.iminfo.bytesPixel + 1;
                j = 1;
                while (i2 <= imax) {
                    iArr = this.histog;
                    i3 = ((rowb[i2] & 255) - (((rowbprev[i2] & 255) + (rowb[j] & 255)) / 2)) & 255;
                    iArr[i3] = iArr[i3] + 1;
                    i2++;
                    j++;
                }
                return;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Bad filter:");
                stringBuilder.append(filterType);
                throw new PngjExceptionInternal(stringBuilder.toString());
        }
    }

    public void computeHistogram(byte[] rowff) {
        Arrays.fill(this.histog, 0);
        for (int i = 1; i < this.iminfo.bytesPerRow; i++) {
            int[] iArr = this.histog;
            int i2 = rowff[i] & 255;
            iArr[i2] = iArr[i2] + 1;
        }
    }

    public double computeAbsFromHistogram() {
        int s = 0;
        int i = 1;
        while (true) {
            int j = 128;
            if (i >= 128) {
                break;
            }
            s += this.histog[i] * i;
            i++;
        }
        i = 128;
        while (j > 0) {
            s += this.histog[i] * j;
            i++;
            j--;
        }
        return ((double) s) / ((double) this.iminfo.bytesPerRow);
    }

    public final double computeEntropyFromHistogram() {
        double s = 1.0d / ((double) this.iminfo.bytesPerRow);
        double ls = Math.log(s);
        double h = BrickValues.SET_COLOR_TO;
        for (int x : this.histog) {
            if (x > 0) {
                h += (Math.log((double) x) + ls) * ((double) x);
            }
        }
        h *= LOG2NI * s;
        if (h < BrickValues.SET_COLOR_TO) {
            return BrickValues.SET_COLOR_TO;
        }
        return h;
    }

    public void setPreferenceForNone(double preferenceForNone) {
        this.preferenceForNone = preferenceForNone;
    }

    public void tuneMemory(double m) {
        if (m == BrickValues.SET_COLOR_TO) {
            this.memoryA = BrickValues.SET_COLOR_TO;
        } else {
            this.memoryA = Math.pow(this.memoryA, 1.0d / m);
        }
    }

    public void setFilterWeights(double[] weights) {
        System.arraycopy(weights, 0, this.filter_weights, 0, 5);
    }
}
