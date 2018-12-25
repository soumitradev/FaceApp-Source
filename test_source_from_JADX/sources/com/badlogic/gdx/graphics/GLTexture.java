package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.TextureData.TextureDataType;
import com.badlogic.gdx.graphics.glutils.MipMapGenerator;
import com.badlogic.gdx.utils.Disposable;

public abstract class GLTexture implements Disposable {
    protected int glHandle;
    public final int glTarget;
    protected Texture$TextureFilter magFilter;
    protected Texture$TextureFilter minFilter;
    protected Texture$TextureWrap uWrap;
    protected Texture$TextureWrap vWrap;

    public abstract int getDepth();

    public abstract int getHeight();

    public abstract int getWidth();

    public abstract boolean isManaged();

    protected abstract void reload();

    public GLTexture(int glTarget) {
        this(glTarget, Gdx.gl.glGenTexture());
    }

    public GLTexture(int glTarget, int glHandle) {
        this.minFilter = Texture$TextureFilter.Nearest;
        this.magFilter = Texture$TextureFilter.Nearest;
        this.uWrap = Texture$TextureWrap.ClampToEdge;
        this.vWrap = Texture$TextureWrap.ClampToEdge;
        this.glTarget = glTarget;
        this.glHandle = glHandle;
    }

    public void bind() {
        Gdx.gl.glBindTexture(this.glTarget, this.glHandle);
    }

    public void bind(int unit) {
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0 + unit);
        Gdx.gl.glBindTexture(this.glTarget, this.glHandle);
    }

    public Texture$TextureFilter getMinFilter() {
        return this.minFilter;
    }

    public Texture$TextureFilter getMagFilter() {
        return this.magFilter;
    }

    public Texture$TextureWrap getUWrap() {
        return this.uWrap;
    }

    public Texture$TextureWrap getVWrap() {
        return this.vWrap;
    }

    public int getTextureObjectHandle() {
        return this.glHandle;
    }

    public void unsafeSetWrap(Texture$TextureWrap u, Texture$TextureWrap v) {
        unsafeSetWrap(u, v, false);
    }

    public void unsafeSetWrap(Texture$TextureWrap u, Texture$TextureWrap v, boolean force) {
        if (u != null && (force || this.uWrap != u)) {
            Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_WRAP_S, (float) u.getGLEnum());
            this.uWrap = u;
        }
        if (v == null) {
            return;
        }
        if (force || this.vWrap != v) {
            Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_WRAP_T, (float) v.getGLEnum());
            this.vWrap = v;
        }
    }

    public void setWrap(Texture$TextureWrap u, Texture$TextureWrap v) {
        this.uWrap = u;
        this.vWrap = v;
        bind();
        Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_WRAP_S, (float) u.getGLEnum());
        Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_WRAP_T, (float) v.getGLEnum());
    }

    public void unsafeSetFilter(Texture$TextureFilter minFilter, Texture$TextureFilter magFilter) {
        unsafeSetFilter(minFilter, magFilter, false);
    }

    public void unsafeSetFilter(Texture$TextureFilter minFilter, Texture$TextureFilter magFilter, boolean force) {
        if (minFilter != null && (force || this.minFilter != minFilter)) {
            Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_MIN_FILTER, (float) minFilter.getGLEnum());
            this.minFilter = minFilter;
        }
        if (magFilter == null) {
            return;
        }
        if (force || this.magFilter != magFilter) {
            Gdx.gl.glTexParameterf(this.glTarget, 10240, (float) magFilter.getGLEnum());
            this.magFilter = magFilter;
        }
    }

    public void setFilter(Texture$TextureFilter minFilter, Texture$TextureFilter magFilter) {
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        bind();
        Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_MIN_FILTER, (float) minFilter.getGLEnum());
        Gdx.gl.glTexParameterf(this.glTarget, 10240, (float) magFilter.getGLEnum());
    }

    protected void delete() {
        if (this.glHandle != 0) {
            Gdx.gl.glDeleteTexture(this.glHandle);
            this.glHandle = 0;
        }
    }

    public void dispose() {
        delete();
    }

    protected static void uploadImageData(int target, TextureData data) {
        uploadImageData(target, data, 0);
    }

    public static void uploadImageData(int target, TextureData data, int miplevel) {
        int i = target;
        TextureData textureData = data;
        if (textureData != null) {
            if (!data.isPrepared()) {
                data.prepare();
            }
            if (data.getType() == TextureDataType.Custom) {
                textureData.consumeCustomData(i);
                return;
            }
            Pixmap pixmap = data.consumePixmap();
            boolean disposePixmap = data.disposePixmap();
            if (data.getFormat() != pixmap.getFormat()) {
                Pixmap tmp = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), data.getFormat());
                Pixmap$Blending blend = Pixmap.getBlending();
                Pixmap.setBlending(Pixmap$Blending.None);
                tmp.drawPixmap(pixmap, 0, 0, 0, 0, pixmap.getWidth(), pixmap.getHeight());
                Pixmap.setBlending(blend);
                if (data.disposePixmap()) {
                    pixmap.dispose();
                }
                pixmap = tmp;
                disposePixmap = true;
            }
            Pixmap pixmap2 = pixmap;
            boolean disposePixmap2 = disposePixmap;
            Gdx.gl.glPixelStorei(GL20.GL_UNPACK_ALIGNMENT, 1);
            if (data.useMipMaps()) {
                MipMapGenerator.generateMipMap(i, pixmap2, pixmap2.getWidth(), pixmap2.getHeight());
            } else {
                Gdx.gl.glTexImage2D(i, miplevel, pixmap2.getGLInternalFormat(), pixmap2.getWidth(), pixmap2.getHeight(), 0, pixmap2.getGLFormat(), pixmap2.getGLType(), pixmap2.getPixels());
            }
            if (disposePixmap2) {
                pixmap2.dispose();
            }
        }
    }
}
