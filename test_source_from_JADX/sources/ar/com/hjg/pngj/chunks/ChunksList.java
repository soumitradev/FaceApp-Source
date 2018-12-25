package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjException;
import java.util.ArrayList;
import java.util.List;

public class ChunksList {
    public static final int CHUNK_GROUP_0_IDHR = 0;
    public static final int CHUNK_GROUP_1_AFTERIDHR = 1;
    public static final int CHUNK_GROUP_2_PLTE = 2;
    public static final int CHUNK_GROUP_3_AFTERPLTE = 3;
    public static final int CHUNK_GROUP_4_IDAT = 4;
    public static final int CHUNK_GROUP_5_AFTERIDAT = 5;
    public static final int CHUNK_GROUP_6_END = 6;
    List<PngChunk> chunks = new ArrayList();
    final ImageInfo imageInfo;
    boolean withPlte = false;

    public ChunksList(ImageInfo imfinfo) {
        this.imageInfo = imfinfo;
    }

    public List<PngChunk> getChunks() {
        return this.chunks;
    }

    protected static List<PngChunk> getXById(List<PngChunk> list, final String id, final String innerid) {
        if (innerid == null) {
            return ChunkHelper.filterList(list, new ChunkPredicate() {
                public boolean match(PngChunk c) {
                    return c.id.equals(id);
                }
            });
        }
        return ChunkHelper.filterList(list, new ChunkPredicate() {
            public boolean match(PngChunk c) {
                if (!c.id.equals(id)) {
                    return false;
                }
                if ((c instanceof PngChunkTextVar) && !((PngChunkTextVar) c).getKey().equals(innerid)) {
                    return false;
                }
                if (!(c instanceof PngChunkSPLT) || ((PngChunkSPLT) c).getPalName().equals(innerid)) {
                    return true;
                }
                return false;
            }
        });
    }

    public void appendReadChunk(PngChunk chunk, int chunkGroup) {
        chunk.setChunkGroup(chunkGroup);
        this.chunks.add(chunk);
        if (chunk.id.equals("PLTE")) {
            this.withPlte = true;
        }
    }

    public List<? extends PngChunk> getById(String id) {
        return getById(id, null);
    }

    public List<? extends PngChunk> getById(String id, String innerid) {
        return getXById(this.chunks, id, innerid);
    }

    public PngChunk getById1(String id) {
        return getById1(id, false);
    }

    public PngChunk getById1(String id, boolean failIfMultiple) {
        return getById1(id, null, failIfMultiple);
    }

    public PngChunk getById1(String id, String innerid, boolean failIfMultiple) {
        List<? extends PngChunk> list = getById(id, innerid);
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

    public List<PngChunk> getEquivalent(final PngChunk c2) {
        return ChunkHelper.filterList(this.chunks, new ChunkPredicate() {
            public boolean match(PngChunk c) {
                return ChunkHelper.equivalent(c, c2);
            }
        });
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ChunkList: read: ");
        stringBuilder.append(this.chunks.size());
        return stringBuilder.toString();
    }

    public String toStringFull() {
        StringBuilder sb = new StringBuilder(toString());
        sb.append("\n Read:\n");
        for (PngChunk chunk : this.chunks) {
            sb.append(chunk);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" G=");
            stringBuilder.append(chunk.getChunkGroup());
            stringBuilder.append("\n");
            sb.append(stringBuilder.toString());
        }
        return sb.toString();
    }
}
