package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjExceptionInternal;
import java.io.OutputStream;

public abstract class PngChunk {
    protected int chunkGroup = -1;
    public final boolean crit;
    public final String id;
    protected final ImageInfo imgInfo;
    private boolean priority = false;
    public final boolean pub;
    protected ChunkRaw raw;
    public final boolean safe;

    public enum ChunkOrderingConstraint {
        NONE,
        BEFORE_PLTE_AND_IDAT,
        AFTER_PLTE_BEFORE_IDAT,
        AFTER_PLTE_BEFORE_IDAT_PLTE_REQUIRED,
        BEFORE_IDAT,
        NA;

        public boolean mustGoBeforePLTE() {
            return this == BEFORE_PLTE_AND_IDAT;
        }

        public boolean mustGoBeforeIDAT() {
            if (!(this == BEFORE_IDAT || this == BEFORE_PLTE_AND_IDAT)) {
                if (this != AFTER_PLTE_BEFORE_IDAT) {
                    return false;
                }
            }
            return true;
        }

        public boolean mustGoAfterPLTE() {
            if (this != AFTER_PLTE_BEFORE_IDAT) {
                if (this != AFTER_PLTE_BEFORE_IDAT_PLTE_REQUIRED) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean isOk(int r6, boolean r7) {
            /*
            r5 = this;
            r0 = NONE;
            r1 = 1;
            if (r5 != r0) goto L_0x0006;
        L_0x0005:
            return r1;
        L_0x0006:
            r0 = BEFORE_IDAT;
            r2 = 4;
            r3 = 0;
            if (r5 != r0) goto L_0x0011;
        L_0x000c:
            if (r6 >= r2) goto L_0x000f;
        L_0x000e:
            goto L_0x0010;
        L_0x000f:
            r1 = 0;
        L_0x0010:
            return r1;
        L_0x0011:
            r0 = BEFORE_PLTE_AND_IDAT;
            r4 = 2;
            if (r5 != r0) goto L_0x001b;
        L_0x0016:
            if (r6 >= r4) goto L_0x0019;
        L_0x0018:
            goto L_0x001a;
        L_0x0019:
            r1 = 0;
        L_0x001a:
            return r1;
        L_0x001b:
            r0 = AFTER_PLTE_BEFORE_IDAT;
            if (r5 != r0) goto L_0x002c;
        L_0x001f:
            if (r7 == 0) goto L_0x0026;
        L_0x0021:
            if (r6 >= r2) goto L_0x0024;
        L_0x0023:
            goto L_0x002b;
        L_0x0024:
            r1 = 0;
            goto L_0x002b;
        L_0x0026:
            if (r6 >= r2) goto L_0x0024;
        L_0x0028:
            if (r6 <= r4) goto L_0x0024;
        L_0x002a:
            goto L_0x0023;
        L_0x002b:
            return r1;
        L_0x002c:
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint.isOk(int, boolean):boolean");
        }
    }

    protected abstract boolean allowsMultiple();

    protected abstract ChunkRaw createRawChunk();

    public abstract ChunkOrderingConstraint getOrderingConstraint();

    protected abstract void parseFromRaw(ChunkRaw chunkRaw);

    public PngChunk(String id, ImageInfo imgInfo) {
        this.id = id;
        this.imgInfo = imgInfo;
        this.crit = ChunkHelper.isCritical(id);
        this.pub = ChunkHelper.isPublic(id);
        this.safe = ChunkHelper.isSafeToCopy(id);
    }

    protected final ChunkRaw createEmptyChunk(int len, boolean alloc) {
        return new ChunkRaw(len, ChunkHelper.toBytes(this.id), alloc);
    }

    public final int getChunkGroup() {
        return this.chunkGroup;
    }

    final void setChunkGroup(int chunkGroup) {
        this.chunkGroup = chunkGroup;
    }

    public boolean hasPriority() {
        return this.priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    final void write(OutputStream os) {
        if (this.raw == null || this.raw.data == null) {
            this.raw = createRawChunk();
        }
        if (this.raw == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("null chunk ! creation failed for ");
            stringBuilder.append(this);
            throw new PngjExceptionInternal(stringBuilder.toString());
        }
        this.raw.writeChunk(os);
    }

    public ChunkRaw getRaw() {
        return this.raw;
    }

    void setRaw(ChunkRaw raw) {
        this.raw = raw;
    }

    public int getLen() {
        return this.raw != null ? this.raw.len : -1;
    }

    public long getOffset() {
        return this.raw != null ? this.raw.getOffset() : -1;
    }

    public void invalidateRawData() {
        this.raw = null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("chunk id= ");
        stringBuilder.append(this.id);
        stringBuilder.append(" (len=");
        stringBuilder.append(getLen());
        stringBuilder.append(" offset=");
        stringBuilder.append(getOffset());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
