package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.math.Vector2;

public class Manifold {
    long addr;
    final Vector2 localNormal = new Vector2();
    final Vector2 localPoint = new Vector2();
    final ManifoldPoint[] points = new ManifoldPoint[]{new ManifoldPoint(), new ManifoldPoint()};
    final float[] tmpFloat = new float[4];
    final int[] tmpInt = new int[2];

    public class ManifoldPoint {
        public int contactID = null;
        public final Vector2 localPoint = new Vector2();
        public float normalImpulse;
        public float tangentImpulse;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("id: ");
            stringBuilder.append(this.contactID);
            stringBuilder.append(", ");
            stringBuilder.append(this.localPoint);
            stringBuilder.append(", ");
            stringBuilder.append(this.normalImpulse);
            stringBuilder.append(", ");
            stringBuilder.append(this.tangentImpulse);
            return stringBuilder.toString();
        }
    }

    public enum ManifoldType {
        Circle,
        FaceA,
        FaceB
    }

    private native void jniGetLocalNormal(long j, float[] fArr);

    private native void jniGetLocalPoint(long j, float[] fArr);

    private native int jniGetPoint(long j, float[] fArr, int i);

    private native int jniGetPointCount(long j);

    private native int jniGetType(long j);

    protected Manifold(long addr) {
        this.addr = addr;
    }

    public ManifoldType getType() {
        int type = jniGetType(this.addr);
        if (type == 0) {
            return ManifoldType.Circle;
        }
        if (type == 1) {
            return ManifoldType.FaceA;
        }
        if (type == 2) {
            return ManifoldType.FaceB;
        }
        return ManifoldType.Circle;
    }

    public int getPointCount() {
        return jniGetPointCount(this.addr);
    }

    public Vector2 getLocalNormal() {
        jniGetLocalNormal(this.addr, this.tmpFloat);
        this.localNormal.set(this.tmpFloat[0], this.tmpFloat[1]);
        return this.localNormal;
    }

    public Vector2 getLocalPoint() {
        jniGetLocalPoint(this.addr, this.tmpFloat);
        this.localPoint.set(this.tmpFloat[0], this.tmpFloat[1]);
        return this.localPoint;
    }

    public ManifoldPoint[] getPoints() {
        int count = jniGetPointCount(this.addr);
        for (int i = 0; i < count; i++) {
            int contactID = jniGetPoint(this.addr, this.tmpFloat, i);
            ManifoldPoint point = this.points[i];
            point.contactID = contactID;
            point.localPoint.set(this.tmpFloat[0], this.tmpFloat[1]);
            point.normalImpulse = this.tmpFloat[2];
            point.tangentImpulse = this.tmpFloat[3];
        }
        return this.points;
    }
}
