package ar.com.hjg.pngj;

import android.support.v4.internal.view.SupportMenu;
import ar.com.hjg.pngj.chunks.PngChunkPLTE;
import ar.com.hjg.pngj.chunks.PngChunkTRNS;
import com.pdrogfer.mididroid.event.meta.MetaEvent;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;
import org.catrobat.catroid.common.BrickValues;

public class ImageLineHelper {
    static int[][] DEPTH_UNPACK;
    static int[] DEPTH_UNPACK_1;
    static int[] DEPTH_UNPACK_2;
    static int[] DEPTH_UNPACK_4;

    private static void initDepthScale() {
        DEPTH_UNPACK_1 = new int[2];
        for (int i = 0; i < 2; i++) {
            DEPTH_UNPACK_1[i] = i * 255;
        }
        DEPTH_UNPACK_2 = new int[4];
        for (int i2 = 0; i2 < 4; i2++) {
            DEPTH_UNPACK_2[i2] = (i2 * 255) / 3;
        }
        DEPTH_UNPACK_4 = new int[16];
        for (int i3 = 0; i3 < 16; i3++) {
            DEPTH_UNPACK_4[i3] = (i3 * 255) / 15;
        }
        DEPTH_UNPACK = new int[][]{null, DEPTH_UNPACK_1, DEPTH_UNPACK_2, null, DEPTH_UNPACK_4};
    }

    public static void scaleUp(IImageLineArray line) {
        if (!line.getImageInfo().indexed) {
            if (line.getImageInfo().bitDepth < 8) {
                if (DEPTH_UNPACK_1 == null || DEPTH_UNPACK == null) {
                    initDepthScale();
                }
                int[] scaleArray = DEPTH_UNPACK[line.getImageInfo().bitDepth];
                int i = 0;
                if (line instanceof ImageLineInt) {
                    ImageLineInt iline = (ImageLineInt) line;
                    while (i < iline.getSize()) {
                        iline.scanline[i] = scaleArray[iline.scanline[i]];
                        i++;
                    }
                } else if (line instanceof ImageLineByte) {
                    ImageLineByte iline2 = (ImageLineByte) line;
                    while (i < iline2.getSize()) {
                        iline2.scanline[i] = (byte) scaleArray[iline2.scanline[i]];
                        i++;
                    }
                } else {
                    throw new PngjException("not implemented");
                }
            }
        }
    }

    public static void scaleDown(IImageLineArray line) {
        if (!line.getImageInfo().indexed) {
            if (line.getImageInfo().bitDepth < 8) {
                if (line instanceof ImageLineInt) {
                    int scalefactor = 8 - line.getImageInfo().bitDepth;
                    int i = 0;
                    if (line instanceof ImageLineInt) {
                        ImageLineInt iline = (ImageLineInt) line;
                        while (i < line.getSize()) {
                            iline.scanline[i] = iline.scanline[i] >> scalefactor;
                            i++;
                        }
                    } else if (line instanceof ImageLineByte) {
                        ImageLineByte iline2 = (ImageLineByte) line;
                        while (i < line.getSize()) {
                            iline2.scanline[i] = (byte) ((iline2.scanline[i] & 255) >> scalefactor);
                            i++;
                        }
                    }
                    return;
                }
                throw new PngjException("not implemented");
            }
        }
    }

    public static byte scaleUp(int bitdepth, byte v) {
        return bitdepth < 8 ? (byte) DEPTH_UNPACK[bitdepth][v] : v;
    }

    public static byte scaleDown(int bitdepth, byte v) {
        return bitdepth < 8 ? (byte) (v >> (8 - bitdepth)) : v;
    }

    public static int[] palette2rgb(ImageLineInt line, PngChunkPLTE pal, PngChunkTRNS trns, int[] buf) {
        return palette2rgb(line, pal, trns, buf, false);
    }

    static int[] lineToARGB32(ImageLineByte line, PngChunkPLTE pal, PngChunkTRNS trns, int[] buf) {
        boolean alphachannel = line.imgInfo.alpha;
        int cols = line.getImageInfo().cols;
        if (buf == null || buf.length < cols) {
            buf = new int[cols];
        }
        int nindexesWithAlpha;
        int index;
        if (line.getImageInfo().indexed) {
            nindexesWithAlpha = trns != null ? trns.getPalletteAlpha().length : 0;
            for (int c = 0; c < cols; c++) {
                index = line.scanline[c] & 255;
                buf[c] = ((index < nindexesWithAlpha ? trns.getPalletteAlpha()[index] : 255) << 24) | pal.getEntry(index);
            }
        } else {
            index = -1;
            int c2;
            int c22;
            int alpha;
            if (line.imgInfo.greyscale) {
                if (trns != null) {
                    index = trns.getGray();
                }
                nindexesWithAlpha = index;
                c2 = 0;
                index = 0;
                while (c2 < cols) {
                    c22 = index + 1;
                    index = line.scanline[index] & 255;
                    if (alphachannel) {
                        alpha = line.scanline[c22] & 255;
                        c22++;
                    } else {
                        alpha = index != nindexesWithAlpha ? 255 : 0;
                    }
                    buf[c2] = (((alpha << 24) | index) | (index << 8)) | (index << 16);
                    c2++;
                    index = c22;
                }
            } else {
                if (trns != null) {
                    index = trns.getRGB888();
                }
                nindexesWithAlpha = index;
                c2 = 0;
                index = 0;
                while (c2 < cols) {
                    c22 = index + 1;
                    int c23 = c22 + 1;
                    c22 = c23 + 1;
                    index = (((line.scanline[index] & 255) << 16) | ((line.scanline[c22] & 255) << 8)) | (line.scanline[c23] & 255);
                    if (alphachannel) {
                        alpha = line.scanline[c22] & 255;
                        c22++;
                    } else {
                        alpha = index != nindexesWithAlpha ? 255 : 0;
                    }
                    buf[c2] = (alpha << 24) | index;
                    c2++;
                    index = c22;
                }
            }
        }
        return buf;
    }

    static byte[] lineToRGBA8888(ImageLineByte line, PngChunkPLTE pal, PngChunkTRNS trns, byte[] buf) {
        boolean alphachannel = line.imgInfo.alpha;
        int cols = line.imgInfo.cols;
        int bytes = cols * 4;
        if (buf == null || buf.length < bytes) {
            buf = new byte[bytes];
        }
        int b = 0;
        int nindexesWithAlpha;
        int i;
        int index;
        int rgb;
        int b2;
        if (line.imgInfo.indexed) {
            nindexesWithAlpha = trns != null ? trns.getPalletteAlpha().length : 0;
            for (int c = 0; c < cols; c++) {
                i = 255;
                index = line.scanline[c] & 255;
                rgb = pal.getEntry(index);
                b2 = b + 1;
                buf[b] = (byte) ((rgb >> 16) & 255);
                b = b2 + 1;
                buf[b2] = (byte) ((rgb >> 8) & 255);
                b2 = b + 1;
                buf[b] = (byte) (rgb & 255);
                b = b2 + 1;
                if (index < nindexesWithAlpha) {
                    i = trns.getPalletteAlpha()[index];
                }
                buf[b2] = (byte) i;
            }
        } else if (line.imgInfo.greyscale) {
            nindexesWithAlpha = trns != null ? trns.getGray() : -1;
            byte val = (byte) 0;
            index = 0;
            while (index < bytes) {
                byte c2;
                byte c3 = val + 1;
                val = line.scanline[val];
                rgb = index + 1;
                buf[index] = val;
                index = rgb + 1;
                buf[rgb] = val;
                rgb = index + 1;
                buf[index] = val;
                index = rgb + 1;
                if (alphachannel) {
                    c2 = c3 + 1;
                    b2 = line.scanline[c3];
                } else if ((val & 255) == nindexesWithAlpha) {
                    c2 = c3;
                    b2 = 0;
                } else {
                    c2 = c3;
                    b2 = -1;
                }
                buf[rgb] = b2;
                val = c2;
            }
        } else if (alphachannel) {
            System.arraycopy(line.scanline, 0, buf, 0, bytes);
        } else {
            index = 0;
            nindexesWithAlpha = 0;
            while (nindexesWithAlpha < bytes) {
                i = nindexesWithAlpha + 1;
                b2 = index + 1;
                buf[nindexesWithAlpha] = line.scanline[index];
                nindexesWithAlpha = i + 1;
                rgb = b2 + 1;
                buf[i] = line.scanline[b2];
                index = nindexesWithAlpha + 1;
                b2 = rgb + 1;
                buf[nindexesWithAlpha] = line.scanline[rgb];
                nindexesWithAlpha = index + 1;
                buf[index] = (byte) -1;
                if (trns != null && buf[nindexesWithAlpha - 3] == ((byte) trns.getRGB()[0]) && buf[nindexesWithAlpha - 2] == ((byte) trns.getRGB()[1]) && buf[nindexesWithAlpha - 1] == ((byte) trns.getRGB()[2])) {
                    buf[nindexesWithAlpha - 1] = (byte) 0;
                }
                index = b2;
            }
        }
        return buf;
    }

    static byte[] lineToRGB888(ImageLineByte line, PngChunkPLTE pal, byte[] buf) {
        boolean alphachannel = line.imgInfo.alpha;
        int cols = line.imgInfo.cols;
        int bytes = cols * 3;
        if (buf == null || buf.length < bytes) {
            buf = new byte[bytes];
        }
        int[] rgb = new int[3];
        int b = 0;
        int c;
        int b2;
        int b3;
        if (line.imgInfo.indexed) {
            c = 0;
            b2 = 0;
            while (c < cols) {
                pal.getEntryRgb(line.scanline[c] & 255, rgb);
                b3 = b2 + 1;
                buf[b2] = (byte) rgb[0];
                b2 = b3 + 1;
                buf[b3] = (byte) rgb[1];
                b3 = b2 + 1;
                buf[b2] = (byte) rgb[2];
                c++;
                b2 = b3;
            }
        } else if (line.imgInfo.greyscale) {
            byte val = (byte) 0;
            while (b < bytes) {
                byte c2 = val + 1;
                val = line.scanline[val];
                int b4 = b + 1;
                buf[b] = val;
                b = b4 + 1;
                buf[b4] = val;
                b4 = b + 1;
                buf[b] = val;
                if (alphachannel) {
                    val = c2 + 1;
                    b = b4;
                } else {
                    b = b4;
                    val = c2;
                }
            }
        } else if (alphachannel) {
            b2 = 0;
            while (b < bytes) {
                c = b + 1;
                int c3 = b2 + 1;
                buf[b] = line.scanline[b2];
                b2 = c + 1;
                b3 = c3 + 1;
                buf[c] = line.scanline[c3];
                b = b2 + 1;
                c3 = b3 + 1;
                buf[b2] = line.scanline[b3];
                b2 = c3 + 1;
            }
        } else {
            System.arraycopy(line.scanline, 0, buf, 0, bytes);
        }
        return buf;
    }

    public static int[] palette2rgba(ImageLineInt line, PngChunkPLTE pal, PngChunkTRNS trns, int[] buf) {
        return palette2rgb(line, pal, trns, buf, true);
    }

    public static int[] palette2rgb(ImageLineInt line, PngChunkPLTE pal, int[] buf) {
        return palette2rgb(line, pal, null, buf, false);
    }

    private static int[] palette2rgb(IImageLine line, PngChunkPLTE pal, PngChunkTRNS trns, int[] buf, boolean alphaForced) {
        IImageLine iImageLine = line;
        int[] buf2 = buf;
        boolean isbyte = true;
        int c = 0;
        boolean isalpha = trns != null;
        int channels = isalpha ? 4 : 3;
        ImageLineByte lineb = null;
        ImageLineInt linei = iImageLine instanceof ImageLineInt ? iImageLine : null;
        if (iImageLine instanceof ImageLineByte) {
            lineb = iImageLine;
        }
        lineb = lineb;
        if (lineb == null) {
            isbyte = false;
        }
        int cols = (linei != null ? linei.imgInfo : lineb.imgInfo).cols;
        int nsamples = cols * channels;
        if (buf2 == null || buf2.length < nsamples) {
            buf2 = new int[nsamples];
        }
        int nindexesWithAlpha = trns != null ? trns.getPalletteAlpha().length : 0;
        while (c < cols) {
            int index = isbyte ? lineb.scanline[c] & 255 : linei.scanline[c];
            pal.getEntryRgb(index, buf2, c * channels);
            if (isalpha) {
                buf2[(c * channels) + 3] = index < nindexesWithAlpha ? trns.getPalletteAlpha()[index] : 255;
            }
            c++;
        }
        PngChunkPLTE pngChunkPLTE = pal;
        return buf2;
    }

    public static String infoFirstLastPixels(ImageLineInt line) {
        if (line.imgInfo.channels == 1) {
            return String.format("first=(%d) last=(%d)", new Object[]{Integer.valueOf(line.scanline[0]), Integer.valueOf(line.scanline[line.scanline.length - 1])});
        }
        return String.format("first=(%d %d %d) last=(%d %d %d)", new Object[]{Integer.valueOf(line.scanline[0]), Integer.valueOf(line.scanline[1]), Integer.valueOf(line.scanline[2]), Integer.valueOf(line.scanline[line.scanline.length - line.imgInfo.channels]), Integer.valueOf(line.scanline[(line.scanline.length - line.imgInfo.channels) + 1]), Integer.valueOf(line.scanline[(line.scanline.length - line.imgInfo.channels) + 2])});
    }

    public static int getPixelRGB8(IImageLine line, int column) {
        int offset;
        if (line instanceof ImageLineInt) {
            offset = ((ImageLineInt) line).imgInfo.channels * column;
            int[] scanline = ((ImageLineInt) line).getScanline();
            return ((scanline[offset] << 16) | (scanline[offset + 1] << 8)) | scanline[offset + 2];
        } else if (line instanceof ImageLineByte) {
            offset = ((ImageLineByte) line).imgInfo.channels * column;
            byte[] scanline2 = ((ImageLineByte) line).getScanline();
            return (((scanline2[offset] & 255) << 16) | ((scanline2[offset + 1] & 255) << 8)) | (scanline2[offset + 2] & 255);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not supported ");
            stringBuilder.append(line.getClass());
            throw new PngjException(stringBuilder.toString());
        }
    }

    public static int getPixelARGB8(IImageLine line, int column) {
        int offset;
        if (line instanceof ImageLineInt) {
            offset = ((ImageLineInt) line).imgInfo.channels * column;
            int[] scanline = ((ImageLineInt) line).getScanline();
            return (((scanline[offset + 3] << 24) | (scanline[offset] << 16)) | (scanline[offset + 1] << 8)) | scanline[offset + 2];
        } else if (line instanceof ImageLineByte) {
            offset = ((ImageLineByte) line).imgInfo.channels * column;
            byte[] scanline2 = ((ImageLineByte) line).getScanline();
            return ((((scanline2[offset + 3] & 255) << 24) | ((scanline2[offset] & 255) << 16)) | ((scanline2[offset + 1] & 255) << 8)) | (scanline2[offset + 2] & 255);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not supported ");
            stringBuilder.append(line.getClass());
            throw new PngjException(stringBuilder.toString());
        }
    }

    public static void setPixelsRGB8(ImageLineInt line, int[] rgb) {
        int i = 0;
        int j = 0;
        while (i < line.imgInfo.cols) {
            int j2 = j + 1;
            line.scanline[j] = (rgb[i] >> 16) & 255;
            int j3 = j2 + 1;
            line.scanline[j2] = (rgb[i] >> 8) & 255;
            j2 = j3 + 1;
            line.scanline[j3] = rgb[i] & 255;
            i++;
            j = j2;
        }
    }

    public static void setPixelRGB8(ImageLineInt line, int col, int r, int g, int b) {
        col *= line.imgInfo.channels;
        int col2 = col + 1;
        line.scanline[col] = r;
        int col3 = col2 + 1;
        line.scanline[col2] = g;
        line.scanline[col3] = b;
    }

    public static void setPixelRGB8(ImageLineInt line, int col, int rgb) {
        setPixelRGB8(line, col, (rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255);
    }

    public static void setPixelsRGBA8(ImageLineInt line, int[] rgb) {
        int i = 0;
        int j = 0;
        while (i < line.imgInfo.cols) {
            int j2 = j + 1;
            line.scanline[j] = (rgb[i] >> 16) & 255;
            int j3 = j2 + 1;
            line.scanline[j2] = (rgb[i] >> 8) & 255;
            j2 = j3 + 1;
            line.scanline[j3] = rgb[i] & 255;
            j3 = j2 + 1;
            line.scanline[j2] = (rgb[i] >> 24) & 255;
            i++;
            j = j3;
        }
    }

    public static void setPixelRGBA8(ImageLineInt line, int col, int r, int g, int b, int a) {
        col *= line.imgInfo.channels;
        int col2 = col + 1;
        line.scanline[col] = r;
        int col3 = col2 + 1;
        line.scanline[col2] = g;
        col2 = col3 + 1;
        line.scanline[col3] = b;
        line.scanline[col2] = a;
    }

    public static void setPixelRGBA8(ImageLineInt line, int col, int rgb) {
        setPixelRGBA8(line, col, (rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255, (rgb >> 24) & 255);
    }

    public static void setValD(ImageLineInt line, int i, double d) {
        line.scanline[i] = double2int(line, d);
    }

    public static int interpol(int a, int b, int c, int d, double dx, double dy) {
        return (int) ((((1.0d - dy) * ((((double) a) * (1.0d - dx)) + (((double) b) * dx))) + (((((double) c) * (1.0d - dx)) + (((double) d) * dx)) * dy)) + 0.5d);
    }

    public static double int2double(ImageLineInt line, int p) {
        double d;
        double d2;
        if (line.imgInfo.bitDepth == 16) {
            d = (double) p;
            d2 = 65535.0d;
        } else {
            d = (double) p;
            d2 = 255.0d;
        }
        return d / d2;
    }

    public static double int2doubleClamped(ImageLineInt line, int p) {
        double d;
        double d2;
        if (line.imgInfo.bitDepth == 16) {
            d = (double) p;
            d2 = 65535.0d;
        } else {
            d = (double) p;
            d2 = 255.0d;
        }
        d /= d2;
        if (d <= BrickValues.SET_COLOR_TO) {
            return BrickValues.SET_COLOR_TO;
        }
        return d >= 1.0d ? 1.0d : d;
    }

    public static int double2int(ImageLineInt line, double d) {
        double d2 = BrickValues.SET_COLOR_TO;
        if (d > BrickValues.SET_COLOR_TO) {
            d2 = d >= 1.0d ? 1.0d : d;
        }
        return (int) (((line.imgInfo.bitDepth == 16 ? 65535.0d : 255.0d) * d2) + 0.5d);
    }

    public static int double2intClamped(ImageLineInt line, double d) {
        double d2 = BrickValues.SET_COLOR_TO;
        if (d > BrickValues.SET_COLOR_TO) {
            d2 = d >= 1.0d ? 1.0d : d;
        }
        return (int) (((line.imgInfo.bitDepth == 16 ? 65535.0d : 255.0d) * d2) + 0.5d);
    }

    public static int clampTo_0_255(int i) {
        if (i > 255) {
            return 255;
        }
        return i < 0 ? 0 : i;
    }

    public static int clampTo_0_65535(int i) {
        if (i > SupportMenu.USER_MASK) {
            return SupportMenu.USER_MASK;
        }
        return i < 0 ? 0 : i;
    }

    public static int clampTo_128_127(int x) {
        if (x > MetaEvent.SEQUENCER_SPECIFIC) {
            return MetaEvent.SEQUENCER_SPECIFIC;
        }
        return x < -128 ? -128 : x;
    }

    static int getMaskForPackedFormats(int bitDepth) {
        if (bitDepth == 4) {
            return SysexMessageWriter.COMMAND_START;
        }
        if (bitDepth == 2) {
            return ReportAnalogPinMessageWriter.COMMAND;
        }
        return 128;
    }

    static int getMaskForPackedFormatsLs(int bitDepth) {
        if (bitDepth == 4) {
            return 15;
        }
        if (bitDepth == 2) {
            return 3;
        }
        return 1;
    }
}
