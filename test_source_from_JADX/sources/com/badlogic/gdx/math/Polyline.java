package com.badlogic.gdx.math;

public class Polyline implements Shape2D {
    private boolean calculateLength;
    private boolean calculateScaledLength;
    private boolean dirty;
    private float length;
    private float[] localVertices;
    private float originX;
    private float originY;
    private float rotation;
    private float scaleX;
    private float scaleY;
    private float scaledLength;
    private float[] worldVertices;
    /* renamed from: x */
    private float f115x;
    /* renamed from: y */
    private float f116y;

    public Polyline() {
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.calculateScaledLength = true;
        this.calculateLength = true;
        this.dirty = true;
        this.localVertices = new float[0];
    }

    public Polyline(float[] vertices) {
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.calculateScaledLength = true;
        this.calculateLength = true;
        this.dirty = true;
        if (vertices.length < 4) {
            throw new IllegalArgumentException("polylines must contain at least 2 points.");
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
        if (r0.worldVertices == null || r0.worldVertices.length < localVertices.length) {
            r0.worldVertices = new float[localVertices.length];
        }
        float[] worldVertices = r0.worldVertices;
        float positionX = r0.f115x;
        float positionY = r0.f116y;
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

    public float getLength() {
        if (!this.calculateLength) {
            return this.length;
        }
        this.calculateLength = false;
        this.length = 0.0f;
        int n = this.localVertices.length - 2;
        for (int i = 0; i < n; i += 2) {
            float x = this.localVertices[i + 2] - this.localVertices[i];
            float y = this.localVertices[i + 1] - this.localVertices[i + 3];
            this.length += (float) Math.sqrt((double) ((x * x) + (y * y)));
        }
        return this.length;
    }

    public float getScaledLength() {
        if (!this.calculateScaledLength) {
            return this.scaledLength;
        }
        this.calculateScaledLength = false;
        this.scaledLength = 0.0f;
        int n = this.localVertices.length - 2;
        for (int i = 0; i < n; i += 2) {
            float x = (this.localVertices[i + 2] * this.scaleX) - (this.localVertices[i] * this.scaleX);
            float y = (this.localVertices[i + 1] * this.scaleY) - (this.localVertices[i + 3] * this.scaleY);
            this.scaledLength += (float) Math.sqrt((double) ((x * x) + (y * y)));
        }
        return this.scaledLength;
    }

    public float getX() {
        return this.f115x;
    }

    public float getY() {
        return this.f116y;
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

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
        this.dirty = true;
    }

    public void setPosition(float x, float y) {
        this.f115x = x;
        this.f116y = y;
        this.dirty = true;
    }

    public void setVertices(float[] vertices) {
        if (vertices.length < 4) {
            throw new IllegalArgumentException("polylines must contain at least 2 points.");
        }
        this.localVertices = vertices;
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
        this.calculateScaledLength = true;
    }

    public void scale(float amount) {
        this.scaleX += amount;
        this.scaleY += amount;
        this.dirty = true;
        this.calculateScaledLength = true;
    }

    public void calculateLength() {
        this.calculateLength = true;
    }

    public void calculateScaledLength() {
        this.calculateScaledLength = true;
    }

    public void dirty() {
        this.dirty = true;
    }

    public void translate(float x, float y) {
        this.f115x += x;
        this.f116y += y;
        this.dirty = true;
    }
}
