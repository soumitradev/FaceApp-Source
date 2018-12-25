package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkSRGB extends PngChunkSingle {
    public static final String ID = "sRGB";
    public static final int RENDER_INTENT_Absolute_colorimetric = 3;
    public static final int RENDER_INTENT_Perceptual = 0;
    public static final int RENDER_INTENT_Relative_colorimetric = 1;
    public static final int RENDER_INTENT_Saturation = 2;
    private int intent;

    public PngChunkSRGB(ImageInfo info) {
        super("sRGB", info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_PLTE_AND_IDAT;
    }

    public void parseFromRaw(ChunkRaw c) {
        if (c.len != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bad chunk length ");
            stringBuilder.append(c);
            throw new PngjException(stringBuilder.toString());
        }
        this.intent = PngHelperInternal.readInt1fromByte(c.data, 0);
    }

    public ChunkRaw createRawChunk() {
        ChunkRaw c = createEmptyChunk(1, true);
        c.data[0] = (byte) this.intent;
        return c;
    }

    public int getIntent() {
        return this.intent;
    }

    public void setIntent(int intent) {
        this.intent = intent;
    }
}
