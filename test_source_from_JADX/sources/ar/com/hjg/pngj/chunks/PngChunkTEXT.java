package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjException;
import org.catrobat.catroid.physics.content.bricks.CollisionReceiverBrick;

public class PngChunkTEXT extends PngChunkTextVar {
    public static final String ID = "tEXt";

    public PngChunkTEXT(ImageInfo info) {
        super("tEXt", info);
    }

    public ChunkRaw createRawChunk() {
        if (this.key != null) {
            if (this.key.trim().length() != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.key);
                stringBuilder.append(CollisionReceiverBrick.ANYTHING_ESCAPE_CHAR);
                stringBuilder.append(this.val);
                byte[] b = ChunkHelper.toBytes(stringBuilder.toString());
                ChunkRaw chunk = createEmptyChunk(b.length, false);
                chunk.data = b;
                return chunk;
            }
        }
        throw new PngjException("Text chunk key must be non empty");
    }

    public void parseFromRaw(ChunkRaw c) {
        int i = 0;
        while (i < c.data.length) {
            if (c.data[i] == (byte) 0) {
                break;
            }
            i++;
        }
        this.key = ChunkHelper.toString(c.data, 0, i);
        i++;
        this.val = i < c.data.length ? ChunkHelper.toString(c.data, i, c.data.length - i) : "";
    }
}
