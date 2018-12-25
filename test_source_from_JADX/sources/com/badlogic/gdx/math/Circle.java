package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.NumberUtils;
import java.io.Serializable;

public class Circle implements Serializable, Shape2D {
    public float radius;
    /* renamed from: x */
    public float f111x;
    /* renamed from: y */
    public float f112y;

    public Circle(float x, float y, float radius) {
        this.f111x = x;
        this.f112y = y;
        this.radius = radius;
    }

    public Circle(Vector2 position, float radius) {
        this.f111x = position.f16x;
        this.f112y = position.f17y;
        this.radius = radius;
    }

    public Circle(Circle circle) {
        this.f111x = circle.f111x;
        this.f112y = circle.f112y;
        this.radius = circle.radius;
    }

    public Circle(Vector2 center, Vector2 edge) {
        this.f111x = center.f16x;
        this.f112y = center.f17y;
        this.radius = Vector2.len(center.f16x - edge.f16x, center.f17y - edge.f17y);
    }

    public void set(float x, float y, float radius) {
        this.f111x = x;
        this.f112y = y;
        this.radius = radius;
    }

    public void set(Vector2 position, float radius) {
        this.f111x = position.f16x;
        this.f112y = position.f17y;
        this.radius = radius;
    }

    public void set(Circle circle) {
        this.f111x = circle.f111x;
        this.f112y = circle.f112y;
        this.radius = circle.radius;
    }

    public void set(Vector2 center, Vector2 edge) {
        this.f111x = center.f16x;
        this.f112y = center.f17y;
        this.radius = Vector2.len(center.f16x - edge.f16x, center.f17y - edge.f17y);
    }

    public void setPosition(Vector2 position) {
        this.f111x = position.f16x;
        this.f112y = position.f17y;
    }

    public void setPosition(float x, float y) {
        this.f111x = x;
        this.f112y = y;
    }

    public void setX(float x) {
        this.f111x = x;
    }

    public void setY(float y) {
        this.f112y = y;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public boolean contains(float x, float y) {
        float x2 = this.f111x - x;
        x = this.f112y - y;
        return (x2 * x2) + (x * x) <= this.radius * this.radius;
    }

    public boolean contains(Vector2 point) {
        float dx = this.f111x - point.f16x;
        float dy = this.f112y - point.f17y;
        return (dx * dx) + (dy * dy) <= this.radius * this.radius;
    }

    public boolean contains(Circle c) {
        float dx = this.f111x - c.f111x;
        float dy = this.f112y - c.f112y;
        return ((dx * dx) + (dy * dy)) + (c.radius * c.radius) <= this.radius * this.radius;
    }

    public boolean overlaps(Circle c) {
        float dx = this.f111x - c.f111x;
        float dy = this.f112y - c.f112y;
        float radiusSum = this.radius + c.radius;
        return (dx * dx) + (dy * dy) < radiusSum * radiusSum;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f111x);
        stringBuilder.append(",");
        stringBuilder.append(this.f112y);
        stringBuilder.append(",");
        stringBuilder.append(this.radius);
        return stringBuilder.toString();
    }

    public float circumference() {
        return this.radius * 6.2831855f;
    }

    public float area() {
        return (this.radius * this.radius) * 3.1415927f;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (o == this) {
            return true;
        }
        if (o != null) {
            if (o.getClass() == getClass()) {
                Circle c = (Circle) o;
                if (this.f111x != c.f111x || this.f112y != c.f112y || this.radius != c.radius) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((((1 * 41) + NumberUtils.floatToRawIntBits(this.radius)) * 41) + NumberUtils.floatToRawIntBits(this.f111x)) * 41) + NumberUtils.floatToRawIntBits(this.f112y);
    }
}
