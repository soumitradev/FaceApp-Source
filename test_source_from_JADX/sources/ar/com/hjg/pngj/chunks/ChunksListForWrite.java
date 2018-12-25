package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.PngjOutputException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ChunksListForWrite extends ChunksList {
    private HashMap<String, Integer> alreadyWrittenKeys = new HashMap();
    private final List<PngChunk> queuedChunks = new ArrayList();

    public ChunksListForWrite(ImageInfo imfinfo) {
        super(imfinfo);
    }

    public List<? extends PngChunk> getQueuedById(String id) {
        return getQueuedById(id, null);
    }

    public List<? extends PngChunk> getQueuedById(String id, String innerid) {
        return ChunksList.getXById(this.queuedChunks, id, innerid);
    }

    public PngChunk getQueuedById1(String id, String innerid, boolean failIfMultiple) {
        List<? extends PngChunk> list = getQueuedById(id, innerid);
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() <= 1 || (!failIfMultiple && ((PngChunk) list.get(0)).allowsMultiple())) {
            return (PngChunk) list.get(list.size() - 1);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected multiple chunks id=");
        stringBuilder.append(id);
        throw new PngjException(stringBuilder.toString());
    }

    public PngChunk getQueuedById1(String id, boolean failIfMultiple) {
        return getQueuedById1(id, null, failIfMultiple);
    }

    public PngChunk getQueuedById1(String id) {
        return getQueuedById1(id, false);
    }

    public List<PngChunk> getQueuedEquivalent(final PngChunk c2) {
        return ChunkHelper.filterList(this.queuedChunks, new ChunkPredicate() {
            public boolean match(PngChunk c) {
                return ChunkHelper.equivalent(c, c2);
            }
        });
    }

    public boolean removeChunk(PngChunk c) {
        if (c == null) {
            return false;
        }
        return this.queuedChunks.remove(c);
    }

    public boolean queue(PngChunk c) {
        this.queuedChunks.add(c);
        return true;
    }

    private static boolean shouldWrite(PngChunk c, int currentGroup) {
        if (currentGroup == 2) {
            return c.id.equals("PLTE");
        }
        if (currentGroup % 2 == 0) {
            throw new PngjOutputException("bad chunk group?");
        }
        int maxChunkGroup;
        int minChunkGroup;
        int preferred;
        if (c.getOrderingConstraint().mustGoBeforePLTE()) {
            maxChunkGroup = 1;
            minChunkGroup = 1;
        } else if (c.getOrderingConstraint().mustGoBeforeIDAT()) {
            maxChunkGroup = 3;
            minChunkGroup = c.getOrderingConstraint().mustGoAfterPLTE() ? 3 : 1;
        } else {
            maxChunkGroup = 5;
            minChunkGroup = 1;
            preferred = maxChunkGroup;
            if (c.hasPriority()) {
                preferred = minChunkGroup;
            }
            if (ChunkHelper.isUnknown(c) && c.getChunkGroup() > 0) {
                preferred = c.getChunkGroup();
            }
            if (currentGroup == preferred) {
                return true;
            }
            if (currentGroup > preferred || currentGroup > maxChunkGroup) {
                return false;
            }
            return true;
        }
        preferred = maxChunkGroup;
        if (c.hasPriority()) {
            preferred = minChunkGroup;
        }
        preferred = c.getChunkGroup();
        if (currentGroup == preferred) {
            return true;
        }
        if (currentGroup > preferred) {
        }
        return false;
    }

    public int writeChunks(OutputStream os, int currentGroup) {
        int cont = 0;
        Iterator<PngChunk> it = this.queuedChunks.iterator();
        while (it.hasNext()) {
            PngChunk c = (PngChunk) it.next();
            if (shouldWrite(c, currentGroup)) {
                StringBuilder stringBuilder;
                if (ChunkHelper.isCritical(c.id) && !c.id.equals("PLTE")) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("bad chunk queued: ");
                    stringBuilder.append(c);
                    throw new PngjOutputException(stringBuilder.toString());
                } else if (!this.alreadyWrittenKeys.containsKey(c.id) || c.allowsMultiple()) {
                    c.write(os);
                    this.chunks.add(c);
                    HashMap hashMap = this.alreadyWrittenKeys;
                    String str = c.id;
                    int i = 1;
                    if (this.alreadyWrittenKeys.containsKey(c.id)) {
                        i = 1 + ((Integer) this.alreadyWrittenKeys.get(c.id)).intValue();
                    }
                    hashMap.put(str, Integer.valueOf(i));
                    c.setChunkGroup(currentGroup);
                    it.remove();
                    cont++;
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("duplicated chunk does not allow multiple: ");
                    stringBuilder.append(c);
                    throw new PngjOutputException(stringBuilder.toString());
                }
            }
        }
        return cont;
    }

    public List<PngChunk> getQueuedChunks() {
        return this.queuedChunks;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ChunkList: written: ");
        stringBuilder.append(getChunks().size());
        stringBuilder.append(" queue: ");
        stringBuilder.append(this.queuedChunks.size());
        return stringBuilder.toString();
    }

    public String toStringFull() {
        StringBuilder sb = new StringBuilder(toString());
        sb.append("\n Written:\n");
        for (PngChunk chunk : getChunks()) {
            sb.append(chunk);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" G=");
            stringBuilder.append(chunk.getChunkGroup());
            stringBuilder.append("\n");
            sb.append(stringBuilder.toString());
        }
        if (!this.queuedChunks.isEmpty()) {
            sb.append(" Queued:\n");
            for (PngChunk chunk2 : this.queuedChunks) {
                sb.append(chunk2);
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
