package com.badlogic.gdx.math.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import java.io.Serializable;

public class Ray implements Serializable {
    private static final long serialVersionUID = -620692054835390878L;
    static Vector3 tmp = new Vector3();
    public final Vector3 direction = new Vector3();
    public final Vector3 origin = new Vector3();

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin.set(origin);
        this.direction.set(direction).nor();
    }

    public Ray cpy() {
        return new Ray(this.origin, this.direction);
    }

    public Vector3 getEndPoint(Vector3 out, float distance) {
        return out.set(this.direction).scl(distance).add(this.origin);
    }

    public Ray mul(Matrix4 matrix) {
        tmp.set(this.origin).add(this.direction);
        tmp.mul(matrix);
        this.origin.mul(matrix);
        this.direction.set(tmp.sub(this.origin));
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ray [");
        stringBuilder.append(this.origin);
        stringBuilder.append(":");
        stringBuilder.append(this.direction);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public Ray set(Vector3 origin, Vector3 direction) {
        this.origin.set(origin);
        this.direction.set(direction);
        return this;
    }

    public Ray set(float x, float y, float z, float dx, float dy, float dz) {
        this.origin.set(x, y, z);
        this.direction.set(dx, dy, dz);
        return this;
    }

    public Ray set(Ray ray) {
        this.origin.set(ray.origin);
        this.direction.set(ray.direction);
        return this;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (o == this) {
            return true;
        }
        if (o != null) {
            if (o.getClass() == getClass()) {
                Ray r = (Ray) o;
                if (!this.direction.equals(r.direction) || !this.origin.equals(r.origin)) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((1 * 73) + this.direction.hashCode()) * 73) + this.origin.hashCode();
    }
}
