package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class PngChunkSPLT extends PngChunkMultiple {
    public static final String ID = "sPLT";
    private String palName;
    private int[] palette;
    private int sampledepth;

    public PngChunkSPLT(ImageInfo info) {
        super("sPLT", info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_IDAT;
    }

    public ChunkRaw createRawChunk() {
        try {
            OutputStream ba = new ByteArrayOutputStream();
            ba.write(ChunkHelper.toBytes(this.palName));
            ba.write(0);
            ba.write((byte) this.sampledepth);
            int nentries = getNentries();
            for (int n = 0; n < nentries; n++) {
                for (int i = 0; i < 4; i++) {
                    if (this.sampledepth == 8) {
                        PngHelperInternal.writeByte(ba, (byte) this.palette[(n * 5) + i]);
                    } else {
                        PngHelperInternal.writeInt2(ba, this.palette[(n * 5) + i]);
                    }
                }
                PngHelperInternal.writeInt2(ba, this.palette[(n * 5) + 4]);
            }
            byte[] b = ba.toByteArray();
            ChunkRaw chunk = createEmptyChunk(b.length, false);
            chunk.data = b;
            return chunk;
        } catch (Throwable e) {
            throw new PngjException(e);
        }
    }

    public void parseFromRaw(ChunkRaw c) {
        int i;
        int t = -1;
        int i2 = 0;
        for (i = 0; i < c.data.length; i++) {
            if (c.data[i] == (byte) 0) {
                t = i;
                break;
            }
        }
        if (t > 0) {
            if (t <= c.data.length - 2) {
                this.palName = ChunkHelper.toString(c.data, 0, t);
                this.sampledepth = PngHelperInternal.readInt1fromByte(c.data, t + 1);
                t += 2;
                i = (c.data.length - t) / (this.sampledepth == 8 ? 6 : 10);
                this.palette = new int[(i * 5)];
                int ne = 0;
                while (i2 < i) {
                    int t2;
                    int t3;
                    int g;
                    int t4;
                    int t5;
                    if (this.sampledepth == 8) {
                        t2 = t + 1;
                        t = PngHelperInternal.readInt1fromByte(c.data, t);
                        t3 = t2 + 1;
                        g = PngHelperInternal.readInt1fromByte(c.data, t2);
                        t4 = t3 + 1;
                        t2 = PngHelperInternal.readInt1fromByte(c.data, t3);
                        t5 = t4 + 1;
                        t3 = PngHelperInternal.readInt1fromByte(c.data, t4);
                    } else {
                        g = PngHelperInternal.readInt2fromBytes(c.data, t);
                        t += 2;
                        t2 = PngHelperInternal.readInt2fromBytes(c.data, t);
                        t += 2;
                        t3 = PngHelperInternal.readInt2fromBytes(c.data, t);
                        t += 2;
                        t4 = PngHelperInternal.readInt2fromBytes(c.data, t);
                        t5 = t + 2;
                        t = g;
                        g = t2;
                        t2 = t3;
                        t3 = t4;
                    }
                    t4 = PngHelperInternal.readInt2fromBytes(c.data, t5);
                    t5 += 2;
                    int ne2 = ne + 1;
                    this.palette[ne] = t;
                    int ne3 = ne2 + 1;
                    this.palette[ne2] = g;
                    ne2 = ne3 + 1;
                    this.palette[ne3] = t2;
                    ne3 = ne2 + 1;
                    this.palette[ne2] = t3;
                    ne2 = ne3 + 1;
                    this.palette[ne3] = t4;
                    i2++;
                    t = t5;
                    ne = ne2;
                }
                return;
            }
        }
        throw new PngjException("bad sPLT chunk: no separator found");
    }

    public int getNentries() {
        return this.palette.length / 5;
    }

    public String getPalName() {
        return this.palName;
    }

    public void setPalName(String palName) {
        this.palName = palName;
    }

    public int getSampledepth() {
        return this.sampledepth;
    }

    public void setSampledepth(int sampledepth) {
        this.sampledepth = sampledepth;
    }

    public int[] getPalette() {
        return this.palette;
    }

    public void setPalette(int[] palette) {
        this.palette = palette;
    }
}
