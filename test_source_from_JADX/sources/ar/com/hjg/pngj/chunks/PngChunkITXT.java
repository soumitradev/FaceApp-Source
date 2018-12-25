package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjException;
import java.io.ByteArrayOutputStream;

public class PngChunkITXT extends PngChunkTextVar {
    public static final String ID = "iTXt";
    private boolean compressed = false;
    private String langTag = "";
    private String translatedTag = "";

    public PngChunkITXT(ImageInfo info) {
        super("iTXt", info);
    }

    public ChunkRaw createRawChunk() {
        if (this.key != null) {
            if (this.key.trim().length() != 0) {
                try {
                    ByteArrayOutputStream ba = new ByteArrayOutputStream();
                    ba.write(ChunkHelper.toBytes(this.key));
                    ba.write(0);
                    ba.write(this.compressed);
                    ba.write(0);
                    ba.write(ChunkHelper.toBytes(this.langTag));
                    ba.write(0);
                    ba.write(ChunkHelper.toBytesUTF8(this.translatedTag));
                    ba.write(0);
                    byte[] textbytes = ChunkHelper.toBytesUTF8(this.val);
                    if (this.compressed) {
                        textbytes = ChunkHelper.compressBytes(textbytes, true);
                    }
                    ba.write(textbytes);
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
        int[] nullsIdx = new int[3];
        int nullsFound = 0;
        int i = 0;
        while (i < c.data.length) {
            if (c.data[i] == (byte) 0) {
                nullsIdx[nullsFound] = i;
                nullsFound++;
                if (nullsFound == 1) {
                    i += 2;
                }
                if (nullsFound == 3) {
                    break;
                }
            }
            i++;
        }
        if (nullsFound != 3) {
            throw new PngjException("Bad formed PngChunkITXT chunk");
        }
        this.key = ChunkHelper.toString(c.data, 0, nullsIdx[0]);
        i = nullsIdx[0] + 1;
        this.compressed = c.data[i] != (byte) 0;
        i++;
        if (!this.compressed || c.data[i] == (byte) 0) {
            this.langTag = ChunkHelper.toString(c.data, i, nullsIdx[1] - i);
            this.translatedTag = ChunkHelper.toStringUTF8(c.data, nullsIdx[1] + 1, (nullsIdx[2] - nullsIdx[1]) - 1);
            int i2 = nullsIdx[2] + 1;
            if (this.compressed) {
                this.val = ChunkHelper.toStringUTF8(ChunkHelper.compressBytes(c.data, i2, c.data.length - i2, false));
                return;
            } else {
                this.val = ChunkHelper.toStringUTF8(c.data, i2, c.data.length - i2);
                return;
            }
        }
        throw new PngjException("Bad formed PngChunkITXT chunk - bad compression method ");
    }

    public boolean isCompressed() {
        return this.compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public String getLangtag() {
        return this.langTag;
    }

    public void setLangtag(String langtag) {
        this.langTag = langtag;
    }

    public String getTranslatedTag() {
        return this.translatedTag;
    }

    public void setTranslatedTag(String translatedTag) {
        this.translatedTag = translatedTag;
    }
}
