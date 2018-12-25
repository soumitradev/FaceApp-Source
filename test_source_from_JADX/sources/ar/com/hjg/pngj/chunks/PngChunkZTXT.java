package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjException;
import java.io.ByteArrayOutputStream;

public class PngChunkZTXT extends PngChunkTextVar {
    public static final String ID = "zTXt";

    public PngChunkZTXT(ImageInfo info) {
        super("zTXt", info);
    }

    public ChunkRaw createRawChunk() {
        if (this.key != null) {
            if (this.key.trim().length() != 0) {
                try {
                    ByteArrayOutputStream ba = new ByteArrayOutputStream();
                    ba.write(ChunkHelper.toBytes(this.key));
                    ba.write(0);
                    ba.write(0);
                    ba.write(ChunkHelper.compressBytes(ChunkHelper.toBytes(this.val), true));
                    byte[] b = ba.toByteArray();
                    ChunkRaw chunk = createEmptyChunk(b.length, false);
                    chunk.data = b;
                    return chunk;
                } catch (Throwable e) {
                    throw new PngjException(e);
                }
            }
        }
        throw new PngjException("Text chunk key must be non empty");
    }

    public void parseFromRaw(ChunkRaw c) {
        int nullsep = -1;
        for (int i = 0; i < c.data.length; i++) {
            if (c.data[i] == (byte) 0) {
                nullsep = i;
                break;
            }
        }
        if (nullsep >= 0) {
            if (nullsep <= c.data.length - 2) {
                this.key = ChunkHelper.toString(c.data, 0, nullsep);
                if (c.data[nullsep + 1] != 0) {
                    throw new PngjException("bad zTXt chunk: unknown compression method");
                }
                this.val = ChunkHelper.toString(ChunkHelper.compressBytes(c.data, nullsep + 2, (c.data.length - nullsep) - 2, false));
                return;
            }
        }
        throw new PngjException("bad zTXt chunk: no separator found");
    }
}
