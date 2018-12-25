package com.badlogic.gdx.math.collision;

import com.badlogic.gdx.math.Vector3;
import java.io.Serializable;

public class Segment implements Serializable {
    private static final long serialVersionUID = 2739667069736519602L;
    /* renamed from: a */
    public final Vector3 f89a = new Vector3();
    /* renamed from: b */
    public final Vector3 f90b = new Vector3();

    public Segment(Vector3 a, Vector3 b) {
        this.f89a.set(a);
        this.f90b.set(b);
    }

    public Segment(float aX, float aY, float aZ, float bX, float bY, float bZ) {
        this.f89a.set(aX, aY, aZ);
        this.f90b.set(bX, bY, bZ);
    }

    public float len() {
        return this.f89a.dst(this.f90b);
    }

    public float len2() {
        return this.f89a.dst2(this.f90b);
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (o == this) {
            return true;
        }
        if (o != null) {
            if (o.getClass() == getClass()) {
                Segment s = (Segment) o;
                if (!this.f89a.equals(s.f89a) || !this.f90b.equals(s.f90b)) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((1 * 71) + this.f89a.hashCode()) * 71) + this.f90b.hashCode();
    }
}
