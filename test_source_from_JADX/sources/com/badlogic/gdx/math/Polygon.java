package com.badlogic.gdx.math;

public class Polygon implements Shape2D {
    private Rectangle bounds;
    private boolean dirty;
    private float[] localVertices;
    private float originX;
    private float originY;
    private float rotation;
    private float scaleX;
    private float scaleY;
    private float[] worldVertices;
    /* renamed from: x */
    private float f10x;
    /* renamed from: y */
    private float f11y;

    public Polygon() {
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.dirty = true;
        this.localVertices = new float[0];
    }

    public Polygon(float[] vertices) {
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.dirty = true;
        if (vertices.length < 6) {
            throw new IllegalArgumentException("polygons must contain at least 3 points.");
        }
        this.localVertices = vertices;
    }

    public float[] getVertices() {
        return this.localVertices;
    }

    public float[] getTransformedVertices() {
        if (!this.dirty) {
            return r0.worldVertices;
        }
        float rotation;
        float cos;
        float sin;
        int n;
        int i;
        float x;
        float y;
        boolean scale = false;
        r0.dirty = false;
        float[] localVertices = r0.localVertices;
        if (r0.worldVertices == null || r0.worldVertices.length != localVertices.length) {
            r0.worldVertices = new float[localVertices.length];
        }
        float[] worldVertices = r0.worldVertices;
        float positionX = r0.f10x;
        float positionY = r0.f11y;
        float originX = r0.originX;
        float originY = r0.originY;
        float scaleX = r0.scaleX;
        float scaleY = r0.scaleY;
        if (scaleX == 1.0f) {
            if (scaleY == 1.0f) {
                rotation = r0.rotation;
                cos = MathUtils.cosDeg(rotation);
                sin = MathUtils.sinDeg(rotation);
                n = localVertices.length;
                for (i = 0; i < n; i += 2) {
                    x = localVertices[i] - originX;
                    y = localVertices[i + 1] - originY;
                    if (scale) {
                        x *= scaleX;
                        y *= scaleY;
                    }
                    if (rotation != 0.0f) {
                        float oldX = x;
                        x = (cos * x) - (sin * y);
                        y = (sin * oldX) + (cos * y);
                    }
                    worldVertices[i] = (positionX + x) + originX;
                    worldVertices[i + 1] = (positionY + y) + originY;
                }
                return worldVertices;
            }
        }
        scale = true;
        rotation = r0.rotation;
        cos = MathUtils.cosDeg(rotation);
        sin = MathUtils.sinDeg(rotation);
        n = localVertices.length;
        for (i = 0; i < n; i += 2) {
            x = localVertices[i] - originX;
            y = localVertices[i + 1] - originY;
            if (scale) {
                x *= scaleX;
                y *= scaleY;
            }
            if (rotation != 0.0f) {
                float oldX2 = x;
                x = (cos * x) - (sin * y);
                y = (sin * oldX2) + (cos * y);
            }
            worldVertices[i] = (positionX + x) + originX;
            worldVertices[i + 1] = (positionY + y) + originY;
        }
        return worldVertices;
    }

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
        this.dirty = true;
    }

    public void setPosition(float x, float y) {
        this.f10x = x;
        this.f11y = y;
        this.dirty = true;
    }

    public void setVertices(float[] vertices) {
        if (vertices.length < 6) {
            throw new IllegalArgumentException("polygons must contain at least 3 points.");
        }
        this.localVertices = vertices;
        this.dirty = true;
    }

    public void translate(float x, float y) {
        this.f10x += x;
        this.f11y += y;
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

    public void dirty() {
        this.dirty = true;
    }

    public float area() {
        float[] vertices = getTransformedVertices();
        return GeometryUtils.polygonArea(vertices, 0, vertices.length);
    }

    public Rectangle getBoundingRectangle() {
        float[] vertices = getTransformedVertices();
        float minX = vertices[0];
        float minY = vertices[1];
        float maxX = vertices[0];
        float maxY = vertices[1];
        int numFloats = vertices.length;
        int i = 2;
        while (i < numFloats) {
            minX = minX > vertices[i] ? vertices[i] : minX;
            minY = minY > vertices[i + 1] ? vertices[i + 1] : minY;
            maxX = maxX < vertices[i] ? vertices[i] : maxX;
            maxY = maxY < vertices[i + 1] ? vertices[i + 1] : maxY;
            i += 2;
        }
        if (this.bounds == null) {
            this.bounds = new Rectangle();
        }
        this.bounds.f12x = minX;
        this.bounds.f13y = minY;
        this.bounds.width = maxX - minX;
        this.bounds.height = maxY - minY;
        return this.bounds;
    }

    public boolean contains(float x, float y) {
        float[] vertices = getTransformedVertices();
        int numFloats = vertices.length;
        int intersects = 0;
        for (int i = 0; i < numFloats; i += 2) {
            float x1 = vertices[i];
            float y1 = vertices[i + 1];
            float x2 = vertices[(i + 2) % numFloats];
            float y2 = vertices[(i + 3) % numFloats];
            if (((y1 <= y && y < y2) || (y2 <= y && y < y1)) && x < (((x2 - x1) / (y2 - y1)) * (y - y1)) + x1) {
                intersects++;
            }
        }
        if ((intersects & 1) == 1) {
            return true;
        }
        return false;
    }

    public float getX() {
        return this.f10x;
    }

    public float getY() {
        return this.f11y;
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
}
