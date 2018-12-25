package com.badlogic.gdx.math.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import java.io.Serializable;
import java.util.List;

public class BoundingBox implements Serializable {
    private static final long serialVersionUID = -1286036817192127343L;
    private static final Vector3 tmpVector = new Vector3();
    private final Vector3 cnt = new Vector3();
    private final Vector3 dim = new Vector3();
    public final Vector3 max = new Vector3();
    public final Vector3 min = new Vector3();

    public Vector3 getCenter(Vector3 out) {
        return out.set(this.cnt);
    }

    public float getCenterX() {
        return this.cnt.f120x;
    }

    public float getCenterY() {
        return this.cnt.f121y;
    }

    public float getCenterZ() {
        return this.cnt.f122z;
    }

    public Vector3 getCorner000(Vector3 out) {
        return out.set(this.min.f120x, this.min.f121y, this.min.f122z);
    }

    public Vector3 getCorner001(Vector3 out) {
        return out.set(this.min.f120x, this.min.f121y, this.max.f122z);
    }

    public Vector3 getCorner010(Vector3 out) {
        return out.set(this.min.f120x, this.max.f121y, this.min.f122z);
    }

    public Vector3 getCorner011(Vector3 out) {
        return out.set(this.min.f120x, this.max.f121y, this.max.f122z);
    }

    public Vector3 getCorner100(Vector3 out) {
        return out.set(this.max.f120x, this.min.f121y, this.min.f122z);
    }

    public Vector3 getCorner101(Vector3 out) {
        return out.set(this.max.f120x, this.min.f121y, this.max.f122z);
    }

    public Vector3 getCorner110(Vector3 out) {
        return out.set(this.max.f120x, this.max.f121y, this.min.f122z);
    }

    public Vector3 getCorner111(Vector3 out) {
        return out.set(this.max.f120x, this.max.f121y, this.max.f122z);
    }

    public Vector3 getDimensions(Vector3 out) {
        return out.set(this.dim);
    }

    public float getWidth() {
        return this.dim.f120x;
    }

    public float getHeight() {
        return this.dim.f121y;
    }

    public float getDepth() {
        return this.dim.f122z;
    }

    public Vector3 getMin(Vector3 out) {
        return out.set(this.min);
    }

    public Vector3 getMax(Vector3 out) {
        return out.set(this.max);
    }

    public BoundingBox() {
        clr();
    }

    public BoundingBox(BoundingBox bounds) {
        set(bounds);
    }

    public BoundingBox(Vector3 minimum, Vector3 maximum) {
        set(minimum, maximum);
    }

    public BoundingBox set(BoundingBox bounds) {
        return set(bounds.min, bounds.max);
    }

    public BoundingBox set(Vector3 minimum, Vector3 maximum) {
        this.min.set(minimum.f120x < maximum.f120x ? minimum.f120x : maximum.f120x, minimum.f121y < maximum.f121y ? minimum.f121y : maximum.f121y, minimum.f122z < maximum.f122z ? minimum.f122z : maximum.f122z);
        this.max.set(minimum.f120x > maximum.f120x ? minimum.f120x : maximum.f120x, minimum.f121y > maximum.f121y ? minimum.f121y : maximum.f121y, minimum.f122z > maximum.f122z ? minimum.f122z : maximum.f122z);
        this.cnt.set(this.min).add(this.max).scl(0.5f);
        this.dim.set(this.max).sub(this.min);
        return this;
    }

    public BoundingBox set(Vector3[] points) {
        inf();
        for (Vector3 l_point : points) {
            ext(l_point);
        }
        return this;
    }

    public BoundingBox set(List<Vector3> points) {
        inf();
        for (Vector3 l_point : points) {
            ext(l_point);
        }
        return this;
    }

    public BoundingBox inf() {
        this.min.set(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        this.max.set(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        this.cnt.set(0.0f, 0.0f, 0.0f);
        this.dim.set(0.0f, 0.0f, 0.0f);
        return this;
    }

    public BoundingBox ext(Vector3 point) {
        return set(this.min.set(min(this.min.f120x, point.f120x), min(this.min.f121y, point.f121y), min(this.min.f122z, point.f122z)), this.max.set(Math.max(this.max.f120x, point.f120x), Math.max(this.max.f121y, point.f121y), Math.max(this.max.f122z, point.f122z)));
    }

    public BoundingBox clr() {
        return set(this.min.set(0.0f, 0.0f, 0.0f), this.max.set(0.0f, 0.0f, 0.0f));
    }

    public boolean isValid() {
        return this.min.f120x < this.max.f120x && this.min.f121y < this.max.f121y && this.min.f122z < this.max.f122z;
    }

    public BoundingBox ext(BoundingBox a_bounds) {
        return set(this.min.set(min(this.min.f120x, a_bounds.min.f120x), min(this.min.f121y, a_bounds.min.f121y), min(this.min.f122z, a_bounds.min.f122z)), this.max.set(max(this.max.f120x, a_bounds.max.f120x), max(this.max.f121y, a_bounds.max.f121y), max(this.max.f122z, a_bounds.max.f122z)));
    }

    public BoundingBox ext(BoundingBox bounds, Matrix4 transform) {
        ext(tmpVector.set(bounds.min.f120x, bounds.min.f121y, bounds.min.f122z).mul(transform));
        ext(tmpVector.set(bounds.min.f120x, bounds.min.f121y, bounds.max.f122z).mul(transform));
        ext(tmpVector.set(bounds.min.f120x, bounds.max.f121y, bounds.min.f122z).mul(transform));
        ext(tmpVector.set(bounds.min.f120x, bounds.max.f121y, bounds.max.f122z).mul(transform));
        ext(tmpVector.set(bounds.max.f120x, bounds.min.f121y, bounds.min.f122z).mul(transform));
        ext(tmpVector.set(bounds.max.f120x, bounds.min.f121y, bounds.max.f122z).mul(transform));
        ext(tmpVector.set(bounds.max.f120x, bounds.max.f121y, bounds.min.f122z).mul(transform));
        ext(tmpVector.set(bounds.max.f120x, bounds.max.f121y, bounds.max.f122z).mul(transform));
        return this;
    }

    public BoundingBox mul(Matrix4 transform) {
        float x0 = this.min.f120x;
        float y0 = this.min.f121y;
        float z0 = this.min.f122z;
        float x1 = this.max.f120x;
        float y1 = this.max.f121y;
        float z1 = this.max.f122z;
        inf();
        ext(tmpVector.set(x0, y0, z0).mul(transform));
        ext(tmpVector.set(x0, y0, z1).mul(transform));
        ext(tmpVector.set(x0, y1, z0).mul(transform));
        ext(tmpVector.set(x0, y1, z1).mul(transform));
        ext(tmpVector.set(x1, y0, z0).mul(transform));
        ext(tmpVector.set(x1, y0, z1).mul(transform));
        ext(tmpVector.set(x1, y1, z0).mul(transform));
        ext(tmpVector.set(x1, y1, z1).mul(transform));
        return this;
    }

    public boolean contains(BoundingBox b) {
        if (isValid()) {
            if (this.min.f120x > b.min.f120x || this.min.f121y > b.min.f121y || this.min.f122z > b.min.f122z || this.max.f120x < b.max.f120x || this.max.f121y < b.max.f121y || this.max.f122z < b.max.f122z) {
                return false;
            }
        }
        return true;
    }

    public boolean intersects(BoundingBox b) {
        boolean z = false;
        if (!isValid()) {
            return false;
        }
        float lx = Math.abs(this.cnt.f120x - b.cnt.f120x);
        float sumx = (this.dim.f120x / 2.0f) + (b.dim.f120x / 2.0f);
        float ly = Math.abs(this.cnt.f121y - b.cnt.f121y);
        float sumy = (this.dim.f121y / 2.0f) + (b.dim.f121y / 2.0f);
        float lz = Math.abs(this.cnt.f122z - b.cnt.f122z);
        float sumz = (this.dim.f122z / 2.0f) + (b.dim.f122z / 2.0f);
        if (lx <= sumx && ly <= sumy && lz <= sumz) {
            z = true;
        }
        return z;
    }

    public boolean contains(Vector3 v) {
        return this.min.f120x <= v.f120x && this.max.f120x >= v.f120x && this.min.f121y <= v.f121y && this.max.f121y >= v.f121y && this.min.f122z <= v.f122z && this.max.f122z >= v.f122z;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.min);
        stringBuilder.append("|");
        stringBuilder.append(this.max);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public BoundingBox ext(float x, float y, float z) {
        return set(this.min.set(min(this.min.f120x, x), min(this.min.f121y, y), min(this.min.f122z, z)), this.max.set(max(this.max.f120x, x), max(this.max.f121y, y), max(this.max.f122z, z)));
    }

    static final float min(float a, float b) {
        return a > b ? b : a;
    }

    static final float max(float a, float b) {
        return a > b ? a : b;
    }
}
