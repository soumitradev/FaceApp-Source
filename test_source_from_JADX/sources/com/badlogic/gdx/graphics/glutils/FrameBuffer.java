package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.Texture$TextureWrap;

public class FrameBuffer extends GLFrameBuffer<Texture> {
    public FrameBuffer(Pixmap$Format format, int width, int height, boolean hasDepth) {
        this(format, width, height, hasDepth, false);
    }

    public FrameBuffer(Pixmap$Format format, int width, int height, boolean hasDepth, boolean hasStencil) {
        super(format, width, height, hasDepth, hasStencil);
    }

    protected void setupTexture() {
        this.colorTexture = new Texture(this.width, this.height, this.format);
        ((Texture) this.colorTexture).setFilter(Texture$TextureFilter.Linear, Texture$TextureFilter.Linear);
        ((Texture) this.colorTexture).setWrap(Texture$TextureWrap.ClampToEdge, Texture$TextureWrap.ClampToEdge);
    }

    public static void unbind() {
        GLFrameBuffer.unbind();
    }
}
