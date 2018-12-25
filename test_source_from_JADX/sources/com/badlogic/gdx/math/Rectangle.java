package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.NumberUtils;
import java.io.Serializable;

public class Rectangle implements Serializable, Shape2D {
    private static final long serialVersionUID = 5733252015138115702L;
    public static final Rectangle tmp = new Rectangle();
    public static final Rectangle tmp2 = new Rectangle();
    public float height;
    public float width;
    /* renamed from: x */
    public float f12x;
    /* renamed from: y */
    public float f13y;

    public Rectangle(float x, float y, float width, float height) {
        this.f12x = x;
        this.f13y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Rectangle rect) {
        this.f12x = rect.f12x;
        this.f13y = rect.f13y;
        this.width = rect.width;
        this.height = rect.height;
    }

    public Rectangle set(float x, float y, float width, float height) {
        this.f12x = x;
        this.f13y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public float getX() {
        return this.f12x;
    }

    public Rectangle setX(float x) {
        this.f12x = x;
        return this;
    }

    public float getY() {
        return this.f13y;
    }

    public Rectangle setY(float y) {
        this.f13y = y;
        return this;
    }

    public float getWidth() {
        return this.width;
    }

    public Rectangle setWidth(float width) {
        this.width = width;
        return this;
    }

    public float getHeight() {
        return this.height;
    }

    public Rectangle setHeight(float height) {
        this.height = height;
        return this;
    }

    public Vector2 getPosition(Vector2 position) {
        return position.set(this.f12x, this.f13y);
    }

    public Rectangle setPosition(Vector2 position) {
        this.f12x = position.f16x;
        this.f13y = position.f17y;
        return this;
    }

    public Rectangle setPosition(float x, float y) {
        this.f12x = x;
        this.f13y = y;
        return this;
    }

    public Rectangle setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Rectangle setSize(float sizeXY) {
        this.width = sizeXY;
        this.height = sizeXY;
        return this;
    }

    public Vector2 getSize(Vector2 size) {
        return size.set(this.width, this.height);
    }

    public boolean contains(float x, float y) {
        return this.f12x <= x && this.f12x + this.width >= x && this.f13y <= y && this.f13y + this.height >= y;
    }

    public boolean contains(Vector2 point) {
        return contains(point.f16x, point.f17y);
    }

    public boolean contains(Rectangle rectangle) {
        float xmin = rectangle.f12x;
        float xmax = rectangle.width + xmin;
        float ymin = rectangle.f13y;
        float ymax = rectangle.height + ymin;
        return xmin > this.f12x && xmin < this.f12x + this.width && xmax > this.f12x && xmax < this.f12x + this.width && ymin > this.f13y && ymin < this.f13y + this.height && ymax > this.f13y && ymax < this.f13y + this.height;
    }

    public boolean overlaps(Rectangle r) {
        return this.f12x < r.f12x + r.width && this.f12x + this.width > r.f12x && this.f13y < r.f13y + r.height && this.f13y + this.height > r.f13y;
    }

    public Rectangle set(Rectangle rect) {
        this.f12x = rect.f12x;
        this.f13y = rect.f13y;
        this.width = rect.width;
        this.height = rect.height;
        return this;
    }

    public Rectangle merge(Rectangle rect) {
        float minX = Math.min(this.f12x, rect.f12x);
        float maxX = Math.max(this.f12x + this.width, rect.f12x + rect.width);
        this.f12x = minX;
        this.width = maxX - minX;
        float minY = Math.min(this.f13y, rect.f13y);
        float maxY = Math.max(this.f13y + this.height, rect.f13y + rect.height);
        this.f13y = minY;
        this.height = maxY - minY;
        return this;
    }

    public Rectangle merge(float x, float y) {
        float minX = Math.min(this.f12x, x);
        float maxX = Math.max(this.f12x + this.width, x);
        this.f12x = minX;
        this.width = maxX - minX;
        float minY = Math.min(this.f13y, y);
        float maxY = Math.max(this.f13y + this.height, y);
        this.f13y = minY;
        this.height = maxY - minY;
        return this;
    }

    public Rectangle merge(Vector2 vec) {
        return merge(vec.f16x, vec.f17y);
    }

    public Rectangle merge(Vector2[] vecs) {
        float minX = this.f12x;
        float maxX = this.f12x + this.width;
        float minY = this.f13y;
        float maxY = this.f13y + this.height;
        for (Vector2 v : vecs) {
            minX = Math.min(minX, v.f16x);
            maxX = Math.max(maxX, v.f16x);
            minY = Math.min(minY, v.f17y);
            maxY = Math.max(maxY, v.f17y);
        }
        this.f12x = minX;
        this.width = maxX - minX;
        this.f13y = minY;
        this.height = maxY - minY;
        return this;
    }

    public float getAspectRatio() {
        return this.height == 0.0f ? Float.NaN : this.width / this.height;
    }

    public Vector2 getCenter(Vector2 vector) {
        vector.f16x = this.f12x + (this.width / 2.0f);
        vector.f17y = this.f13y + (this.height / 2.0f);
        return vector;
    }

    public Rectangle setCenter(float x, float y) {
        setPosition(x - (this.width / 2.0f), y - (this.height / 2.0f));
        return this;
    }

    public Rectangle setCenter(Vector2 position) {
        setPosition(position.f16x - (this.width / 2.0f), position.f17y - (this.height / 2.0f));
        return this;
    }

    public Rectangle fitOutside(Rectangle rect) {
        float ratio = getAspectRatio();
        if (ratio > rect.getAspectRatio()) {
            setSize(rect.height * ratio, rect.height);
        } else {
            setSize(rect.width, rect.width / ratio);
        }
        setPosition((rect.f12x + (rect.width / 2.0f)) - (this.width / 2.0f), (rect.f13y + (rect.height / 2.0f)) - (this.height / 2.0f));
        return this;
    }

    public Rectangle fitInside(Rectangle rect) {
        float ratio = getAspectRatio();
        if (ratio < rect.getAspectRatio()) {
            setSize(rect.height * ratio, rect.height);
        } else {
            setSize(rect.width, rect.width / ratio);
        }
        setPosition((rect.f12x + (rect.width / 2.0f)) - (this.width / 2.0f), (rect.f13y + (rect.height / 2.0f)) - (this.height / 2.0f));
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f12x);
        stringBuilder.append(",");
        stringBuilder.append(this.f13y);
        stringBuilder.append(",");
        stringBuilder.append(this.width);
        stringBuilder.append(",");
        stringBuilder.append(this.height);
        return stringBuilder.toString();
    }

    public float area() {
        return this.width * this.height;
    }

    public float perimeter() {
        return (this.width + this.height) * 2.0f;
    }

    public int hashCode() {
        return (((((((1 * 31) + NumberUtils.floatToRawIntBits(this.height)) * 31) + NumberUtils.floatToRawIntBits(this.width)) * 31) + NumberUtils.floatToRawIntBits(this.f12x)) * 31) + NumberUtils.floatToRawIntBits(this.f13y);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Rectangle other = (Rectangle) obj;
        if (NumberUtils.floatToRawIntBits(this.height) == NumberUtils.floatToRawIntBits(other.height) && NumberUtils.floatToRawIntBits(this.width) == NumberUtils.floatToRawIntBits(other.width) && NumberUtils.floatToRawIntBits(this.f12x) == NumberUtils.floatToRawIntBits(other.f12x) && NumberUtils.floatToRawIntBits(this.f13y) == NumberUtils.floatToRawIntBits(other.f13y)) {
            return true;
        }
        return false;
    }
}
