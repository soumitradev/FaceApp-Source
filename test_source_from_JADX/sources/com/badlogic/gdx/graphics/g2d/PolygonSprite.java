package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.NumberUtils;

public class PolygonSprite {
    private Rectangle bounds = new Rectangle();
    private final Color color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    private boolean dirty;
    private float height;
    private float originX;
    private float originY;
    PolygonRegion region;
    private float rotation;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float[] vertices;
    private float width;
    /* renamed from: x */
    private float f70x;
    /* renamed from: y */
    private float f71y;

    public PolygonSprite(PolygonRegion region) {
        setRegion(region);
        setColor(1.0f, 1.0f, 1.0f, 1.0f);
        setSize((float) region.region.regionWidth, (float) region.region.regionHeight);
        setOrigin(this.width / 2.0f, this.height / 2.0f);
    }

    public PolygonSprite(PolygonSprite sprite) {
        set(sprite);
    }

    public void set(PolygonSprite sprite) {
        if (sprite == null) {
            throw new IllegalArgumentException("sprite cannot be null.");
        }
        setRegion(sprite.region);
        this.f70x = sprite.f70x;
        this.f71y = sprite.f71y;
        this.width = sprite.width;
        this.height = sprite.height;
        this.originX = sprite.originX;
        this.originY = sprite.originY;
        this.rotation = sprite.rotation;
        this.scaleX = sprite.scaleX;
        this.scaleY = sprite.scaleY;
        this.color.set(sprite.color);
        this.dirty = sprite.dirty;
    }

    public void setBounds(float x, float y, float width, float height) {
        this.f70x = x;
        this.f71y = y;
        this.width = width;
        this.height = height;
        this.dirty = true;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        this.dirty = true;
    }

    public void setPosition(float x, float y) {
        translate(x - this.f70x, y - this.f71y);
    }

    public void setX(float x) {
        translateX(x - this.f70x);
    }

    public void setY(float y) {
        translateY(y - this.f71y);
    }

    public void translateX(float xAmount) {
        this.f70x += xAmount;
        if (!this.dirty) {
            float[] vertices = this.vertices;
            for (int i = 0; i < vertices.length; i += 5) {
                vertices[i] = vertices[i] + xAmount;
            }
        }
    }

    public void translateY(float yAmount) {
        this.f71y += yAmount;
        if (!this.dirty) {
            float[] vertices = this.vertices;
            for (int i = 1; i < vertices.length; i += 5) {
                vertices[i] = vertices[i] + yAmount;
            }
        }
    }

    public void translate(float xAmount, float yAmount) {
        this.f70x += xAmount;
        this.f71y += yAmount;
        if (!this.dirty) {
            float[] vertices = this.vertices;
            for (int i = 0; i < vertices.length; i += 5) {
                vertices[i] = vertices[i] + xAmount;
                int i2 = i + 1;
                vertices[i2] = vertices[i2] + yAmount;
            }
        }
    }

    public void setColor(Color tint) {
        this.color.set(tint);
        float color = tint.toFloatBits();
        float[] vertices = this.vertices;
        for (int i = 2; i < vertices.length; i += 5) {
            vertices[i] = color;
        }
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
        float color = NumberUtils.intToFloatColor(((int) (255.0f * r)) | (((((int) (a * 255.0f)) << 24) | (((int) (b * 255.0f)) << 16)) | (((int) (g * 255.0f)) << 8)));
        float[] vertices = this.vertices;
        for (int i = 2; i < vertices.length; i += 5) {
            vertices[i] = color;
        }
    }

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
        this.dirty = true;
    }

    public void setRotation(float degrees) {
        this.rotation = degrees;
        this.dirty = true;
    }

    public void rotate(float degrees) {
        this.rotation += degrees;
        this.dirty = true;
    }

    public void setScale(float scaleXY) {
        this.scaleX = scaleXY;
        this.scaleY = scaleXY;
        this.dirty = true;
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.dirty = true;
    }

    public void scale(float amount) {
        this.scaleX += amount;
        this.scaleY += amount;
        this.dirty = true;
    }

    public float[] getVertices() {
        if (!this.dirty) {
            return r0.vertices;
        }
        r0.dirty = false;
        float originX = r0.originX;
        float originY = r0.originY;
        float scaleX = r0.scaleX;
        float scaleY = r0.scaleY;
        PolygonRegion region = r0.region;
        float[] vertices = r0.vertices;
        float[] regionVertices = region.vertices;
        float worldOriginX = r0.f70x + originX;
        float worldOriginY = r0.f71y + originY;
        float sX = r0.width / ((float) region.region.getRegionWidth());
        float sY = r0.height / ((float) region.region.getRegionHeight());
        float cos = MathUtils.cosDeg(r0.rotation);
        float sin = MathUtils.sinDeg(r0.rotation);
        int i = 0;
        int v = 0;
        int n = regionVertices.length;
        while (i < n) {
            float fx = ((regionVertices[i] * sX) - originX) * scaleX;
            float fy = ((regionVertices[i + 1] * sY) - originY) * scaleY;
            vertices[v] = ((cos * fx) - (sin * fy)) + worldOriginX;
            vertices[v + 1] = ((sin * fx) + (cos * fy)) + worldOriginY;
            i += 2;
            v += 5;
        }
        return vertices;
    }

    public Rectangle getBoundingRectangle() {
        float[] vertices = getVertices();
        float minx = vertices[0];
        float miny = vertices[1];
        float maxx = vertices[0];
        float maxy = vertices[1];
        for (int i = 5; i < vertices.length; i += 5) {
            float x = vertices[i];
            float y = vertices[i + 1];
            minx = minx > x ? x : minx;
            maxx = maxx < x ? x : maxx;
            miny = miny > y ? y : miny;
            maxy = maxy < y ? y : maxy;
        }
        this.bounds.f12x = minx;
        this.bounds.f13y = miny;
        this.bounds.width = maxx - minx;
        this.bounds.height = maxy - miny;
        return this.bounds;
    }

    public void draw(PolygonSpriteBatch spriteBatch) {
        PolygonRegion region = this.region;
        spriteBatch.draw(region.region.texture, getVertices(), 0, this.vertices.length, region.triangles, 0, region.triangles.length);
    }

    public void draw(PolygonSpriteBatch spriteBatch, float alphaModulation) {
        Color color = getColor();
        float oldAlpha = color.f1a;
        color.f1a *= alphaModulation;
        setColor(color);
        draw(spriteBatch);
        color.f1a = oldAlpha;
        setColor(color);
    }

    public float getX() {
        return this.f70x;
    }

    public float getY() {
        return this.f71y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getOriginX() {
        return this.originX;
    }

    public float getOriginY() {
        return this.originY;
    }

    public float getRotation() {
        return this.rotation;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public Color getColor() {
        return this.color;
    }

    public Color getVertexColor() {
        int intBits = NumberUtils.floatToIntColor(this.vertices[2]);
        Color color = this.color;
        color.f4r = ((float) (intBits & 255)) / 255.0f;
        color.f3g = ((float) ((intBits >>> 8) & 255)) / 255.0f;
        color.f2b = ((float) ((intBits >>> 16) & 255)) / 255.0f;
        color.f1a = ((float) ((intBits >>> 24) & 255)) / 255.0f;
        return color;
    }

    public void setRegion(PolygonRegion region) {
        this.region = region;
        float[] regionVertices = region.vertices;
        float[] textureCoords = region.textureCoords;
        if (this.vertices == null || regionVertices.length != this.vertices.length) {
            this.vertices = new float[((regionVertices.length / 2) * 5)];
        }
        float[] vertices = this.vertices;
        int i = 0;
        int v = 2;
        int n = regionVertices.length;
        while (i < n) {
            vertices[v] = this.color.toFloatBits();
            vertices[v + 1] = textureCoords[i];
            vertices[v + 2] = textureCoords[i + 1];
            i += 2;
            v += 5;
        }
        this.dirty = true;
    }

    public PolygonRegion getRegion() {
        return this.region;
    }
}
