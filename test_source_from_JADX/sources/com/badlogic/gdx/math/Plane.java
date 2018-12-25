package com.badlogic.gdx.math;

import java.io.Serializable;

public class Plane implements Serializable {
    private static final long serialVersionUID = -1240652082930747866L;
    /* renamed from: d */
    public float f84d = 0.0f;
    public final Vector3 normal = new Vector3();

    public enum PlaneSide {
        OnPlane,
        Back,
        Front
    }

    public Plane(Vector3 normal, float d) {
        this.normal.set(normal).nor();
        this.f84d = d;
    }

    public Plane(Vector3 normal, Vector3 point) {
        this.normal.set(normal).nor();
        this.f84d = -this.normal.dot(point);
    }

    public Plane(Vector3 point1, Vector3 point2, Vector3 point3) {
        set(point1, point2, point3);
    }

    public void set(Vector3 point1, Vector3 point2, Vector3 point3) {
        this.normal.set(point1).sub(point2).crs(point2.f120x - point3.f120x, point2.f121y - point3.f121y, point2.f122z - point3.f122z).nor();
        this.f84d = -point1.dot(this.normal);
    }

    public void set(float nx, float ny, float nz, float d) {
        this.normal.set(nx, ny, nz);
        this.f84d = d;
    }

    public float distance(Vector3 point) {
        return this.normal.dot(point) + this.f84d;
    }

    public PlaneSide testPoint(Vector3 point) {
        float dist = this.normal.dot(point) + this.f84d;
        if (dist == 0.0f) {
            return PlaneSide.OnPlane;
        }
        if (dist < 0.0f) {
            return PlaneSide.Back;
        }
        return PlaneSide.Front;
    }

    public PlaneSide testPoint(float x, float y, float z) {
        float dist = this.normal.dot(x, y, z) + this.f84d;
        if (dist == 0.0f) {
            return PlaneSide.OnPlane;
        }
        if (dist < 0.0f) {
            return PlaneSide.Back;
        }
        return PlaneSide.Front;
    }

    public boolean isFrontFacing(Vector3 direction) {
        return this.normal.dot(direction) <= 0.0f;
    }

    public Vector3 getNormal() {
        return this.normal;
    }

    public float getD() {
        return this.f84d;
    }

    public void set(Vector3 point, Vector3 normal) {
        this.normal.set(normal);
        this.f84d = -point.dot(normal);
    }

    public void set(float pointX, float pointY, float pointZ, float norX, float norY, float norZ) {
        this.normal.set(norX, norY, norZ);
        this.f84d = -(((pointX * norX) + (pointY * norY)) + (pointZ * norZ));
    }

    public void set(Plane plane) {
        this.normal.set(plane.normal);
        this.f84d = plane.f84d;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.normal.toString());
        stringBuilder.append(", ");
        stringBuilder.append(this.f84d);
        return stringBuilder.toString();
    }
}
