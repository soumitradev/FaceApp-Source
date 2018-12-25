package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Gdx2DPixmap implements Disposable {
    public static final int GDX2D_BLEND_NONE = 0;
    public static final int GDX2D_BLEND_SRC_OVER = 1;
    public static final int GDX2D_FORMAT_ALPHA = 1;
    public static final int GDX2D_FORMAT_LUMINANCE_ALPHA = 2;
    public static final int GDX2D_FORMAT_RGB565 = 5;
    public static final int GDX2D_FORMAT_RGB888 = 3;
    public static final int GDX2D_FORMAT_RGBA4444 = 6;
    public static final int GDX2D_FORMAT_RGBA8888 = 4;
    public static final int GDX2D_SCALE_LINEAR = 1;
    public static final int GDX2D_SCALE_NEAREST = 0;
    long basePtr;
    int format;
    int height;
    long[] nativeData;
    ByteBuffer pixelPtr;
    int width;

    private static native void clear(long j, int i);

    private static native void drawCircle(long j, int i, int i2, int i3, int i4);

    private static native void drawLine(long j, int i, int i2, int i3, int i4, int i5);

    private static native void drawPixmap(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    private static native void drawRect(long j, int i, int i2, int i3, int i4, int i5);

    private static native void fillCircle(long j, int i, int i2, int i3, int i4);

    private static native void fillRect(long j, int i, int i2, int i3, int i4, int i5);

    private static native void fillTriangle(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7);

    private static native void free(long j);

    public static native String getFailureReason();

    private static native int getPixel(long j, int i, int i2);

    private static native ByteBuffer load(long[] jArr, byte[] bArr, int i, int i2);

    private static native ByteBuffer newPixmap(long[] jArr, int i, int i2, int i3);

    public static native void setBlend(int i);

    private static native void setPixel(long j, int i, int i2, int i3);

    public static native void setScale(int i);

    static {
        setBlend(1);
        setScale(1);
    }

    public Gdx2DPixmap(byte[] encodedData, int offset, int len, int requestedFormat) throws IOException {
        this.nativeData = new long[4];
        this.pixelPtr = load(this.nativeData, encodedData, offset, len);
        if (this.pixelPtr == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error loading pixmap: ");
            stringBuilder.append(getFailureReason());
            throw new IOException(stringBuilder.toString());
        }
        this.basePtr = this.nativeData[0];
        this.width = (int) this.nativeData[1];
        this.height = (int) this.nativeData[2];
        this.format = (int) this.nativeData[3];
        if (requestedFormat != 0 && requestedFormat != this.format) {
            convert(requestedFormat);
        }
    }

    public Gdx2DPixmap(InputStream in, int requestedFormat) throws IOException {
        this.nativeData = new long[4];
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        while (true) {
            int read = in.read(buffer);
            int readBytes = read;
            if (read == -1) {
                break;
            }
            bytes.write(buffer, 0, readBytes);
        }
        buffer = bytes.toByteArray();
        this.pixelPtr = load(this.nativeData, buffer, 0, buffer.length);
        if (this.pixelPtr == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error loading pixmap: ");
            stringBuilder.append(getFailureReason());
            throw new IOException(stringBuilder.toString());
        }
        this.basePtr = this.nativeData[0];
        this.width = (int) this.nativeData[1];
        this.height = (int) this.nativeData[2];
        this.format = (int) this.nativeData[3];
        if (requestedFormat != 0 && requestedFormat != this.format) {
            convert(requestedFormat);
        }
    }

    public Gdx2DPixmap(int width, int height, int format) throws GdxRuntimeException {
        this.nativeData = new long[4];
        this.pixelPtr = newPixmap(this.nativeData, width, height, format);
        if (this.pixelPtr == null) {
            throw new GdxRuntimeException("Error loading pixmap.");
        }
        this.basePtr = this.nativeData[0];
        this.width = (int) this.nativeData[1];
        this.height = (int) this.nativeData[2];
        this.format = (int) this.nativeData[3];
    }

    public Gdx2DPixmap(ByteBuffer pixelPtr, long[] nativeData) {
        this.nativeData = new long[4];
        this.pixelPtr = pixelPtr;
        this.basePtr = nativeData[0];
        this.width = (int) nativeData[1];
        this.height = (int) nativeData[2];
        this.format = (int) nativeData[3];
    }

    private void convert(int requestedFormat) {
        Gdx2DPixmap pixmap = new Gdx2DPixmap(this.width, this.height, requestedFormat);
        pixmap.drawPixmap(this, 0, 0, 0, 0, this.width, this.height);
        dispose();
        this.basePtr = pixmap.basePtr;
        this.format = pixmap.format;
        this.height = pixmap.height;
        this.nativeData = pixmap.nativeData;
        this.pixelPtr = pixmap.pixelPtr;
        this.width = pixmap.width;
    }

    public void dispose() {
        free(this.basePtr);
    }

    public void clear(int color) {
        clear(this.basePtr, color);
    }

    public void setPixel(int x, int y, int color) {
        setPixel(this.basePtr, x, y, color);
    }

    public int getPixel(int x, int y) {
        return getPixel(this.basePtr, x, y);
    }

    public void drawLine(int x, int y, int x2, int y2, int color) {
        drawLine(this.basePtr, x, y, x2, y2, color);
    }

    public void drawRect(int x, int y, int width, int height, int color) {
        drawRect(this.basePtr, x, y, width, height, color);
    }

    public void drawCircle(int x, int y, int radius, int color) {
        drawCircle(this.basePtr, x, y, radius, color);
    }

    public void fillRect(int x, int y, int width, int height, int color) {
        fillRect(this.basePtr, x, y, width, height, color);
    }

    public void fillCircle(int x, int y, int radius, int color) {
        fillCircle(this.basePtr, x, y, radius, color);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int color) {
        fillTriangle(this.basePtr, x1, y1, x2, y2, x3, y3, color);
    }

    public void drawPixmap(Gdx2DPixmap src, int srcX, int srcY, int dstX, int dstY, int width, int height) {
        drawPixmap(src.basePtr, this.basePtr, srcX, srcY, width, height, dstX, dstY, width, height);
    }

    public void drawPixmap(Gdx2DPixmap src, int srcX, int srcY, int srcWidth, int srcHeight, int dstX, int dstY, int dstWidth, int dstHeight) {
        drawPixmap(src.basePtr, this.basePtr, srcX, srcY, srcWidth, srcHeight, dstX, dstY, dstWidth, dstHeight);
    }

    public static Gdx2DPixmap newPixmap(InputStream in, int requestedFormat) {
        try {
            return new Gdx2DPixmap(in, requestedFormat);
        } catch (IOException e) {
            return null;
        }
    }

    public static Gdx2DPixmap newPixmap(int width, int height, int format) {
        try {
            return new Gdx2DPixmap(width, height, format);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public ByteBuffer getPixels() {
        return this.pixelPtr;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getFormat() {
        return this.format;
    }

    public int getGLInternalFormat() {
        switch (this.format) {
            case 1:
                return GL20.GL_ALPHA;
            case 2:
                return GL20.GL_LUMINANCE_ALPHA;
            case 3:
            case 5:
                return GL20.GL_RGB;
            case 4:
            case 6:
                return GL20.GL_RGBA;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown format: ");
                stringBuilder.append(this.format);
                throw new GdxRuntimeException(stringBuilder.toString());
        }
    }

    public int getGLFormat() {
        return getGLInternalFormat();
    }

    public int getGLType() {
        switch (this.format) {
            case 1:
            case 2:
            case 3:
            case 4:
                return GL20.GL_UNSIGNED_BYTE;
            case 5:
                return GL20.GL_UNSIGNED_SHORT_5_6_5;
            case 6:
                return GL20.GL_UNSIGNED_SHORT_4_4_4_4;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown format: ");
                stringBuilder.append(this.format);
                throw new GdxRuntimeException(stringBuilder.toString());
        }
    }

    public String getFormatString() {
        switch (this.format) {
            case 1:
                return "alpha";
            case 2:
                return "luminance alpha";
            case 3:
                return "rgb888";
            case 4:
                return "rgba8888";
            case 5:
                return "rgb565";
            case 6:
                return "rgba4444";
            default:
                return "unknown";
        }
    }
}
