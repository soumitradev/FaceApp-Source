package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.IntBuffer;

public final class DefaultTextureBinder implements TextureBinder {
    public static final int MAX_GLES_UNITS = 32;
    public static final int ROUNDROBIN = 0;
    public static final int WEIGHTED = 1;
    private int bindCount;
    private final int count;
    private int currentTexture;
    private final int method;
    private final int offset;
    private int reuseCount;
    private final int reuseWeight;
    private boolean reused;
    private final TextureDescriptor tempDesc;
    private final GLTexture[] textures;
    private final int[] weights;

    public DefaultTextureBinder(int method) {
        this(method, 0);
    }

    public DefaultTextureBinder(int method, int offset) {
        this(method, offset, -1);
    }

    public DefaultTextureBinder(int method, int offset, int count) {
        this(method, offset, count, 10);
    }

    public DefaultTextureBinder(int method, int offset, int count, int reuseWeight) {
        this.reuseCount = 0;
        this.bindCount = 0;
        this.tempDesc = new TextureDescriptor();
        this.currentTexture = 0;
        int max = Math.min(getMaxTextureUnits(), 32);
        if (count < 0) {
            count = max - offset;
        }
        if (offset >= 0 && count >= 0 && offset + count <= max) {
            if (reuseWeight >= 1) {
                this.method = method;
                this.offset = offset;
                this.count = count;
                this.textures = new GLTexture[count];
                this.reuseWeight = reuseWeight;
                this.weights = method == 1 ? new int[count] : null;
                return;
            }
        }
        throw new GdxRuntimeException("Illegal arguments");
    }

    private static int getMaxTextureUnits() {
        IntBuffer buffer = BufferUtils.newIntBuffer(16);
        Gdx.gl.glGetIntegerv(GL20.GL_MAX_TEXTURE_IMAGE_UNITS, buffer);
        return buffer.get(0);
    }

    public void begin() {
        for (int i = 0; i < this.count; i++) {
            this.textures[i] = null;
            if (this.weights != null) {
                this.weights[i] = 0;
            }
        }
    }

    public void end() {
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
    }

    public final int bind(TextureDescriptor textureDesc) {
        return bindTexture(textureDesc, false);
    }

    public final int bind(GLTexture texture) {
        this.tempDesc.set(texture, null, null, null, null);
        return bindTexture(this.tempDesc, false);
    }

    private final int bindTexture(TextureDescriptor textureDesc, boolean rebind) {
        int i;
        GLTexture texture = textureDesc.texture;
        this.reused = false;
        int bindTextureRoundRobin;
        int i2;
        switch (this.method) {
            case 0:
                i = this.offset;
                bindTextureRoundRobin = bindTextureRoundRobin(texture);
                i2 = bindTextureRoundRobin;
                i += bindTextureRoundRobin;
                break;
            case 1:
                i = this.offset;
                bindTextureRoundRobin = bindTextureWeighted(texture);
                i2 = bindTextureRoundRobin;
                i += bindTextureRoundRobin;
                break;
            default:
                return -1;
        }
        if (this.reused) {
            this.reuseCount++;
            if (rebind) {
                texture.bind(i);
            } else {
                Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0 + i);
            }
        } else {
            this.bindCount++;
        }
        texture.unsafeSetWrap(textureDesc.uWrap, textureDesc.vWrap);
        texture.unsafeSetFilter(textureDesc.minFilter, textureDesc.magFilter);
        return i;
    }

    private final int bindTextureRoundRobin(GLTexture texture) {
        for (int i = 0; i < this.count; i++) {
            int idx = (this.currentTexture + i) % this.count;
            if (this.textures[idx] == texture) {
                this.reused = true;
                return idx;
            }
        }
        this.currentTexture = (this.currentTexture + 1) % this.count;
        this.textures[this.currentTexture] = texture;
        texture.bind(this.offset + this.currentTexture);
        return this.currentTexture;
    }

    private final int bindTextureWeighted(GLTexture texture) {
        int result = -1;
        int i = 0;
        int weight = this.weights[0];
        int windex = 0;
        while (i < this.count) {
            int[] iArr;
            if (this.textures[i] == texture) {
                result = i;
                iArr = this.weights;
                iArr[i] = iArr[i] + this.reuseWeight;
            } else {
                if (this.weights[i] >= 0) {
                    iArr = this.weights;
                    int i2 = iArr[i] - 1;
                    iArr[i] = i2;
                    if (i2 >= weight) {
                    }
                }
                weight = this.weights[i];
                windex = i;
            }
            i++;
        }
        if (result < 0) {
            this.textures[windex] = texture;
            this.weights[windex] = 100;
            result = windex;
            texture.bind(this.offset + windex);
            return result;
        }
        this.reused = true;
        return result;
    }

    public final int getBindCount() {
        return this.bindCount;
    }

    public final int getReuseCount() {
        return this.reuseCount;
    }

    public final void resetCounts() {
        this.reuseCount = 0;
        this.bindCount = 0;
    }
}
