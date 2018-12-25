package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.NumberUtils;
import java.io.Serializable;

public class Ellipse implements Serializable, Shape2D {
    private static final long serialVersionUID = 7381533206532032099L;
    public float height;
    public float width;
    /* renamed from: x */
    public float f113x;
    /* renamed from: y */
    public float f114y;

    public Ellipse(Ellipse ellipse) {
        this.f113x = ellipse.f113x;
        this.f114y = ellipse.f114y;
        this.width = ellipse.width;
        this.height = ellipse.height;
    }

    public Ellipse(float x, float y, float width, float height) {
        this.f113x = x;
        this.f114y = y;
        this.width = width;
        this.height = height;
    }

    public Ellipse(Vector2 position, float width, float height) {
        this.f113x = position.f16x;
        this.f114y = position.f17y;
        this.width = width;
        this.height = height;
    }

    public Ellipse(Vector2 position, Vector2 size) {
        this.f113x = position.f16x;
        this.f114y = position.f17y;
        this.width = size.f16x;
        this.height = size.f17y;
    }

    public Ellipse(Circle circle) {
        this.f113x = circle.f111x;
        this.f114y = circle.f112y;
        this.width = circle.radius;
        this.height = circle.radius;
    }

    public boolean contains(float x, float y) {
        x -= this.f113x;
        y -= this.f114y;
        return ((x * x) / (((this.width * 0.5f) * this.width) * 0.5f)) + ((y * y) / (((this.height * 0.5f) * this.height) * 0.5f)) <= 1.0f;
    }

    public boolean contains(Vector2 point) {
        return contains(point.f16x, point.f17y);
    }

    public void set(float x, float y, float width, float height) {
        this.f113x = x;
        this.f114y = y;
        this.width = width;
        this.height = height;
    }

    public void set(Ellipse ellipse) {
        this.f113x = ellipse.f113x;
        this.f114y = ellipse.f114y;
        this.width = ellipse.width;
        this.height = ellipse.height;
    }

    public void set(Circle circle) {
        this.f113x = circle.f111x;
        this.f114y = circle.f112y;
        this.width = circle.radius;
        this.height = circle.radius;
    }

    public void set(Vector2 position, Vector2 size) {
        this.f113x = position.f16x;
        this.f114y = position.f17y;
        this.width = size.f16x;
        this.height = size.f17y;
    }

    public Ellipse setPosition(Vector2 position) {
        this.f113x = position.f16x;
        this.f114y = position.f17y;
        return this;
    }

    public Ellipse setPosition(float x, float y) {
        this.f113x = x;
        this.f114y = y;
        return this;
    }

    public Ellipse setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public float area() {
        return ((this.width * this.height) * 3.1415927f) / 4.0f;
    }

    public float circumference() {
        float a = this.width / 2.0f;
        float b = this.height / 2.0f;
        if (a * 3.0f <= b) {
            if (b * 3.0f <= a) {
                return (float) (Math.sqrt((double) (((a * a) + (b * b)) / 2.0f)) * 6.2831854820251465d);
            }
        }
        return (float) ((((double) ((a + b) * 3.0f)) - Math.sqrt((double) (((a * 3.0f) + b) * ((3.0f * b) + a)))) * 3.1415927410125732d);
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (o == this) {
            return true;
        }
        if (o != null) {
            if (o.getClass() == getClass()) {
                Ellipse e = (Ellipse) o;
                if (this.f113x != e.f113x || this.f114y != e.f114y || this.width != e.width || this.height != e.height) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((((((1 * 53) + NumberUtils.floatToRawIntBits(this.height)) * 53) + NumberUtils.floatToRawIntBits(this.width)) * 53) + NumberUtils.floatToRawIntBits(this.f113x)) * 53) + NumberUtils.floatToRawIntBits(this.f114y);
    }
}
