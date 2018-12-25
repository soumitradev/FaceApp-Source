package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cubemap.CubemapSide;
import com.badlogic.gdx.graphics.CubemapData;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Blending;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.TextureData.Factory;
import com.badlogic.gdx.graphics.TextureData.TextureDataType;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class FacedCubemapData implements CubemapData {
    protected final TextureData[] data;

    public FacedCubemapData() {
        TextureData textureData = (TextureData) null;
        this(textureData, textureData, textureData, textureData, textureData, textureData);
    }

    public FacedCubemapData(FileHandle positiveX, FileHandle negativeX, FileHandle positiveY, FileHandle negativeY, FileHandle positiveZ, FileHandle negativeZ) {
        this(Factory.loadFromFile(positiveX, false), Factory.loadFromFile(negativeX, false), Factory.loadFromFile(positiveY, false), Factory.loadFromFile(negativeY, false), Factory.loadFromFile(positiveZ, false), Factory.loadFromFile(negativeZ, false));
    }

    public FacedCubemapData(FileHandle positiveX, FileHandle negativeX, FileHandle positiveY, FileHandle negativeY, FileHandle positiveZ, FileHandle negativeZ, boolean useMipMaps) {
        this(Factory.loadFromFile(positiveX, useMipMaps), Factory.loadFromFile(negativeX, useMipMaps), Factory.loadFromFile(positiveY, useMipMaps), Factory.loadFromFile(negativeY, useMipMaps), Factory.loadFromFile(positiveZ, useMipMaps), Factory.loadFromFile(negativeZ, useMipMaps));
    }

    public FacedCubemapData(Pixmap positiveX, Pixmap negativeX, Pixmap positiveY, Pixmap negativeY, Pixmap positiveZ, Pixmap negativeZ) {
        this(positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ, false);
    }

    public FacedCubemapData(Pixmap positiveX, Pixmap negativeX, Pixmap positiveY, Pixmap negativeY, Pixmap positiveZ, Pixmap negativeZ, boolean useMipMaps) {
        Pixmap pixmap = positiveX;
        Pixmap pixmap2 = negativeX;
        Pixmap pixmap3 = positiveY;
        Pixmap pixmap4 = negativeY;
        Pixmap pixmap5 = positiveZ;
        Pixmap pixmap6 = negativeZ;
        boolean z = useMipMaps;
        this(pixmap == null ? null : new PixmapTextureData(pixmap, null, z, false), pixmap2 == null ? null : new PixmapTextureData(pixmap2, null, z, false), pixmap3 == null ? null : new PixmapTextureData(pixmap3, null, z, false), pixmap4 == null ? null : new PixmapTextureData(pixmap4, null, z, false), pixmap5 == null ? null : new PixmapTextureData(pixmap5, null, z, false), pixmap6 == null ? null : new PixmapTextureData(pixmap6, null, z, false));
    }

    public FacedCubemapData(int width, int height, int depth, Pixmap$Format format) {
        this(new PixmapTextureData(new Pixmap(depth, height, format), null, false, true), (TextureData) new PixmapTextureData(new Pixmap(depth, height, format), null, false, true), (TextureData) new PixmapTextureData(new Pixmap(width, depth, format), null, false, true), (TextureData) new PixmapTextureData(new Pixmap(width, depth, format), null, false, true), (TextureData) new PixmapTextureData(new Pixmap(width, height, format), null, false, true), (TextureData) new PixmapTextureData(new Pixmap(width, height, format), null, false, true));
    }

    public FacedCubemapData(TextureData positiveX, TextureData negativeX, TextureData positiveY, TextureData negativeY, TextureData positiveZ, TextureData negativeZ) {
        this.data = new TextureData[6];
        this.data[0] = positiveX;
        this.data[1] = negativeX;
        this.data[2] = positiveY;
        this.data[3] = negativeY;
        this.data[4] = positiveZ;
        this.data[5] = negativeZ;
    }

    public boolean isManaged() {
        for (TextureData data : this.data) {
            if (!data.isManaged()) {
                return false;
            }
        }
        return true;
    }

    public void load(CubemapSide side, FileHandle file) {
        this.data[side.index] = Factory.loadFromFile(file, false);
    }

    public void load(CubemapSide side, Pixmap pixmap) {
        TextureData[] textureDataArr = this.data;
        int i = side.index;
        Pixmap$Format pixmap$Format = null;
        if (pixmap != null) {
            pixmap$Format = new PixmapTextureData(pixmap, null, false, false);
        }
        textureDataArr[i] = pixmap$Format;
    }

    public boolean isComplete() {
        for (TextureData textureData : this.data) {
            if (textureData == null) {
                return false;
            }
        }
        return true;
    }

    public TextureData getTextureData(CubemapSide side) {
        return this.data[side.index];
    }

    public int getWidth() {
        int width;
        int tmp;
        int width2 = 0;
        if (this.data[CubemapSide.PositiveZ.index] != null) {
            width = this.data[CubemapSide.PositiveZ.index].getWidth();
            tmp = width;
            if (width > 0) {
                width2 = tmp;
            }
        }
        if (this.data[CubemapSide.NegativeZ.index] != null) {
            width = this.data[CubemapSide.NegativeZ.index].getWidth();
            tmp = width;
            if (width > width2) {
                width2 = tmp;
            }
        }
        if (this.data[CubemapSide.PositiveY.index] != null) {
            width = this.data[CubemapSide.PositiveY.index].getWidth();
            tmp = width;
            if (width > width2) {
                width2 = tmp;
            }
        }
        if (this.data[CubemapSide.NegativeY.index] == null) {
            return width2;
        }
        width = this.data[CubemapSide.NegativeY.index].getWidth();
        tmp = width;
        if (width > width2) {
            return tmp;
        }
        return width2;
    }

    public int getHeight() {
        int height;
        int tmp;
        int height2 = 0;
        if (this.data[CubemapSide.PositiveZ.index] != null) {
            height = this.data[CubemapSide.PositiveZ.index].getHeight();
            tmp = height;
            if (height > 0) {
                height2 = tmp;
            }
        }
        if (this.data[CubemapSide.NegativeZ.index] != null) {
            height = this.data[CubemapSide.NegativeZ.index].getHeight();
            tmp = height;
            if (height > height2) {
                height2 = tmp;
            }
        }
        if (this.data[CubemapSide.PositiveX.index] != null) {
            height = this.data[CubemapSide.PositiveX.index].getHeight();
            tmp = height;
            if (height > height2) {
                height2 = tmp;
            }
        }
        if (this.data[CubemapSide.NegativeX.index] == null) {
            return height2;
        }
        height = this.data[CubemapSide.NegativeX.index].getHeight();
        tmp = height;
        if (height > height2) {
            return tmp;
        }
        return height2;
    }

    public boolean isPrepared() {
        return false;
    }

    public void prepare() {
        if (isComplete()) {
            for (int i = 0; i < this.data.length; i++) {
                if (!this.data[i].isPrepared()) {
                    this.data[i].prepare();
                }
            }
            return;
        }
        throw new GdxRuntimeException("You need to complete your cubemap data before using it");
    }

    public void consumeCubemapData() {
        FacedCubemapData facedCubemapData = this;
        for (int i = 0; i < facedCubemapData.data.length; i++) {
            if (facedCubemapData.data[i].getType() == TextureDataType.Custom) {
                facedCubemapData.data[i].consumeCustomData(GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i);
            } else {
                Pixmap pixmap = facedCubemapData.data[i].consumePixmap();
                boolean disposePixmap = facedCubemapData.data[i].disposePixmap();
                if (facedCubemapData.data[i].getFormat() != pixmap.getFormat()) {
                    Pixmap tmp = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), facedCubemapData.data[i].getFormat());
                    Pixmap$Blending blend = Pixmap.getBlending();
                    Pixmap.setBlending(Pixmap$Blending.None);
                    tmp.drawPixmap(pixmap, 0, 0, 0, 0, pixmap.getWidth(), pixmap.getHeight());
                    Pixmap.setBlending(blend);
                    if (facedCubemapData.data[i].disposePixmap()) {
                        pixmap.dispose();
                    }
                    pixmap = tmp;
                }
                Gdx.gl.glPixelStorei(GL20.GL_UNPACK_ALIGNMENT, 1);
                Gdx.gl.glTexImage2D(i + GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
            }
        }
    }
}
