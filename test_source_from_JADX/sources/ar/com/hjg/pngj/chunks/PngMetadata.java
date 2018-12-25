package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.PngjException;
import java.util.ArrayList;
import java.util.List;

public class PngMetadata {
    private final ChunksList chunkList;
    private final boolean readonly;

    public PngMetadata(ChunksList chunks) {
        this.chunkList = chunks;
        if (chunks instanceof ChunksListForWrite) {
            this.readonly = false;
        } else {
            this.readonly = true;
        }
    }

    public void queueChunk(final PngChunk c, boolean lazyOverwrite) {
        ChunksListForWrite cl = getChunkListW();
        if (this.readonly) {
            throw new PngjException("cannot set chunk : readonly metadata");
        }
        if (lazyOverwrite) {
            ChunkHelper.trimList(cl.getQueuedChunks(), new ChunkPredicate() {
                public boolean match(PngChunk c2) {
                    return ChunkHelper.equivalent(c, c2);
                }
            });
        }
        cl.queue(c);
    }

    public void queueChunk(PngChunk c) {
        queueChunk(c, true);
    }

    private ChunksListForWrite getChunkListW() {
        return (ChunksListForWrite) this.chunkList;
    }

    public double[] getDpi() {
        PngChunk c = this.chunkList.getById1("pHYs", true);
        if (c == null) {
            return new double[]{-1.0d, -1.0d};
        }
        return ((PngChunkPHYS) c).getAsDpi2();
    }

    public void setDpi(double x) {
        setDpi(x, x);
    }

    public void setDpi(double x, double y) {
        PngChunkPHYS c = new PngChunkPHYS(this.chunkList.imageInfo);
        c.setAsDpi2(x, y);
        queueChunk(c);
    }

    public PngChunkTIME setTimeNow(int secsAgo) {
        PngChunkTIME c = new PngChunkTIME(this.chunkList.imageInfo);
        c.setNow(secsAgo);
        queueChunk(c);
        return c;
    }

    public PngChunkTIME setTimeNow() {
        return setTimeNow(0);
    }

    public PngChunkTIME setTimeYMDHMS(int yearx, int monx, int dayx, int hourx, int minx, int secx) {
        PngChunkTIME c = new PngChunkTIME(this.chunkList.imageInfo);
        c.setYMDHMS(yearx, monx, dayx, hourx, minx, secx);
        queueChunk(c, true);
        return c;
    }

    public PngChunkTIME getTime() {
        return (PngChunkTIME) this.chunkList.getById1("tIME");
    }

    public String getTimeAsString() {
        PngChunkTIME c = getTime();
        return c == null ? "" : c.getAsString();
    }

    public PngChunkTextVar setText(String k, String val, boolean useLatin1, boolean compress) {
        if (!compress || useLatin1) {
            PngChunkTextVar c;
            if (!useLatin1) {
                c = new PngChunkITXT(this.chunkList.imageInfo);
                ((PngChunkITXT) c).setLangtag(k);
            } else if (compress) {
                c = new PngChunkZTXT(this.chunkList.imageInfo);
            } else {
                c = new PngChunkTEXT(this.chunkList.imageInfo);
            }
            c.setKeyVal(k, val);
            queueChunk(c, true);
            return c;
        }
        throw new PngjException("cannot compress non latin text");
    }

    public PngChunkTextVar setText(String k, String val) {
        return setText(k, val, false, false);
    }

    public List<? extends PngChunkTextVar> getTxtsForKey(String k) {
        List c = new ArrayList();
        c.addAll(this.chunkList.getById("tEXt", k));
        c.addAll(this.chunkList.getById("zTXt", k));
        c.addAll(this.chunkList.getById("iTXt", k));
        return c;
    }

    public String getTxtForKey(String k) {
        List<? extends PngChunkTextVar> li = getTxtsForKey(k);
        if (li.isEmpty()) {
            return "";
        }
        StringBuilder t = new StringBuilder();
        for (PngChunkTextVar c : li) {
            t.append(c.getVal());
            t.append("\n");
        }
        return t.toString().trim();
    }

    public PngChunkPLTE getPLTE() {
        return (PngChunkPLTE) this.chunkList.getById1("PLTE");
    }

    public PngChunkPLTE createPLTEChunk() {
        PngChunkPLTE plte = new PngChunkPLTE(this.chunkList.imageInfo);
        queueChunk(plte);
        return plte;
    }

    public PngChunkTRNS getTRNS() {
        return (PngChunkTRNS) this.chunkList.getById1("tRNS");
    }

    public PngChunkTRNS createTRNSChunk() {
        PngChunkTRNS trns = new PngChunkTRNS(this.chunkList.imageInfo);
        queueChunk(trns);
        return trns;
    }
}
