package com.badlogic.gdx.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Pixmap implements Disposable {
    private static Pixmap$Blending blending = Pixmap$Blending.SourceOver;
    int color = 0;
    private boolean disposed;
    final Gdx2DPixmap pixmap;

    public static void setBlending(Pixmap$Blending blending) {
        blending = blending;
        Gdx2DPixmap.setBlend(blending == Pixmap$Blending.None ? 0 : 1);
    }

    public static void setFilter(Pixmap$Filter filter) {
        Gdx2DPixmap.setScale(filter == Pixmap$Filter.NearestNeighbour ? 0 : 1);
    }

    public Pixmap(int width, int height, Pixmap$Format format) {
        this.pixmap = new Gdx2DPixmap(width, height, Pixmap$Format.toGdx2DPixmapFormat(format));
        setColor(0.0f, 0.0f, 0.0f, 0.0f);
        fill();
    }

    public Pixmap(byte[] encodedData, int offset, int len) {
        try {
            this.pixmap = new Gdx2DPixmap(encodedData, offset, len, 0);
        } catch (IOException e) {
            throw new GdxRuntimeException("Couldn't load pixmap from image data", e);
        }
    }

    public Pixmap(FileHandle file) {
        try {
            byte[] bytes = file.readBytes();
            this.pixmap = new Gdx2DPixmap(bytes, 0, bytes.length, 0);
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't load file: ");
            stringBuilder.append(file);
            throw new GdxRuntimeException(stringBuilder.toString(), e);
        }
    }

    public Pixmap(Gdx2DPixmap pixmap) {
        this.pixmap = pixmap;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setColor(float r, float g, float b, float a) {
        this.color = Color.rgba8888(r, g, b, a);
    }

    public void setColor(Color color) {
        this.color = Color.rgba8888(color.f4r, color.f3g, color.f2b, color.f1a);
    }

    public void fill() {
        this.pixmap.clear(this.color);
    }

    public void drawLine(int x, int y, int x2, int y2) {
        this.pixmap.drawLine(x, y, x2, y2, this.color);
    }

    public void drawRectangle(int x, int y, int width, int height) {
        this.pixmap.drawRect(x, y, width, height, this.color);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y) {
        drawPixmap(pixmap, x, y, 0, 0, pixmap.getWidth(), pixmap.getHeight());
    }

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcx, int srcy, int srcWidth, int srcHeight) {
        this.pixmap.drawPixmap(pixmap.pixmap, srcx, srcy, x, y, srcWidth, srcHeight);
    }

    public void drawPixmap(Pixmap pixmap, int srcx, int srcy, int srcWidth, int srcHeight, int dstx, int dsty, int dstWidth, int dstHeight) {
        this.pixmap.drawPixmap(pixmap.pixmap, srcx, srcy, srcWidth, srcHeight, dstx, dsty, dstWidth, dstHeight);
    }

    public void fillRectangle(int x, int y, int width, int height) {
        this.pixmap.fillRect(x, y, width, height, this.color);
    }

    public void drawCircle(int x, int y, int radius) {
        this.pixmap.drawCircle(x, y, radius, this.color);
    }

    public void fillCircle(int x, int y, int radius) {
        this.pixmap.fillCircle(x, y, radius, this.color);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.pixmap.fillTriangle(x1, y1, x2, y2, x3, y3, this.color);
    }

    public int getPixel(int x, int y) {
        return this.pixmap.getPixel(x, y);
    }

    public int getWidth() {
        return this.pixmap.getWidth();
    }

    public int getHeight() {
        return this.pixmap.getHeight();
    }

    public void dispose() {
        if (this.disposed) {
            throw new GdxRuntimeException("Pixmap already disposed!");
        }
        this.pixmap.dispose();
        this.disposed = true;
    }

    public void drawPixel(int x, int y) {
        this.pixmap.setPixel(x, y, this.color);
    }

    public void drawPixel(int x, int y, int color) {
        this.pixmap.setPixel(x, y, color);
    }

    public int getGLFormat() {
        return this.pixmap.getGLFormat();
    }

    public int getGLInternalFormat() {
        return this.pixmap.getGLInternalFormat();
    }

    public int getGLType() {
        return this.pixmap.getGLType();
    }

    public ByteBuffer getPixels() {
        if (!this.disposed) {
            return this.pixmap.getPixels();
        }
        throw new GdxRuntimeException("Pixmap already disposed");
    }

    public Pixmap$Format getFormat() {
        return Pixmap$Format.fromGdx2DPixmapFormat(this.pixmap.getFormat());
    }

    public static Pixmap$Blending getBlending() {
        return blending;
    }
}
