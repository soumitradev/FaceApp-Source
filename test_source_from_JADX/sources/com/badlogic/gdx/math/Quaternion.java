package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.NumberUtils;
import java.io.Serializable;

public class Quaternion implements Serializable {
    private static final long serialVersionUID = -7661875440774897168L;
    private static Quaternion tmp1 = new Quaternion(0.0f, 0.0f, 0.0f, 0.0f);
    private static Quaternion tmp2 = new Quaternion(0.0f, 0.0f, 0.0f, 0.0f);
    /* renamed from: w */
    public float f85w;
    /* renamed from: x */
    public float f86x;
    /* renamed from: y */
    public float f87y;
    /* renamed from: z */
    public float f88z;

    public Quaternion(float x, float y, float z, float w) {
        set(x, y, z, w);
    }

    public Quaternion() {
        idt();
    }

    public Quaternion(Quaternion quaternion) {
        set(quaternion);
    }

    public Quaternion(Vector3 axis, float angle) {
        set(axis, angle);
    }

    public Quaternion set(float x, float y, float z, float w) {
        this.f86x = x;
        this.f87y = y;
        this.f88z = z;
        this.f85w = w;
        return this;
    }

    public Quaternion set(Quaternion quaternion) {
        return set(quaternion.f86x, quaternion.f87y, quaternion.f88z, quaternion.f85w);
    }

    public Quaternion set(Vector3 axis, float angle) {
        return setFromAxis(axis.f120x, axis.f121y, axis.f122z, angle);
    }

    public Quaternion cpy() {
        return new Quaternion(this);
    }

    public static final float len(float x, float y, float z, float w) {
        return (float) Math.sqrt((double) ((((x * x) + (y * y)) + (z * z)) + (w * w)));
    }

    public float len() {
        return (float) Math.sqrt((double) ((((this.f86x * this.f86x) + (this.f87y * this.f87y)) + (this.f88z * this.f88z)) + (this.f85w * this.f85w)));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.f86x);
        stringBuilder.append("|");
        stringBuilder.append(this.f87y);
        stringBuilder.append("|");
        stringBuilder.append(this.f88z);
        stringBuilder.append("|");
        stringBuilder.append(this.f85w);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public Quaternion setEulerAngles(float yaw, float pitch, float roll) {
        return setEulerAnglesRad(yaw * 0.017453292f, pitch * 0.017453292f, 0.017453292f * roll);
    }

    public Quaternion setEulerAnglesRad(float yaw, float pitch, float roll) {
        float hr = roll * 0.5f;
        float shr = (float) Math.sin((double) hr);
        float chr = (float) Math.cos((double) hr);
        float hp = pitch * 0.5f;
        float shp = (float) Math.sin((double) hp);
        float chp = (float) Math.cos((double) hp);
        float hy = 0.5f * yaw;
        float shy = (float) Math.sin((double) hy);
        float chy = (float) Math.cos((double) hy);
        float chy_shp = chy * shp;
        float shy_chp = shy * chp;
        float chy_chp = chy * chp;
        float shy_shp = shy * shp;
        this.f86x = (chy_shp * chr) + (shy_chp * shr);
        this.f87y = (shy_chp * chr) - (chy_shp * shr);
        this.f88z = (chy_chp * shr) - (shy_shp * chr);
        this.f85w = (chy_chp * chr) + (shy_shp * shr);
        return r0;
    }

    public int getGimbalPole() {
        float t = (this.f87y * this.f86x) + (this.f88z * this.f85w);
        if (t > 0.499f) {
            return 1;
        }
        return t < -0.499f ? -1 : 0;
    }

    public float getRollRad() {
        int pole = getGimbalPole();
        if (pole == 0) {
            return MathUtils.atan2(((this.f85w * this.f88z) + (this.f87y * this.f86x)) * 2.0f, 1.0f - (((this.f86x * this.f86x) + (this.f88z * this.f88z)) * 2.0f));
        }
        return MathUtils.atan2(this.f87y, this.f85w) * (((float) pole) * 2.0f);
    }

    public float getRoll() {
        return getRollRad() * 57.295776f;
    }

    public float getPitchRad() {
        int pole = getGimbalPole();
        return pole == 0 ? (float) Math.asin((double) MathUtils.clamp(((this.f85w * this.f86x) - (this.f88z * this.f87y)) * 2.0f, -1.0f, 1.0f)) : (((float) pole) * 3.1415927f) * 0.5f;
    }

    public float getPitch() {
        return getPitchRad() * 57.295776f;
    }

    public float getYawRad() {
        return getGimbalPole() == 0 ? MathUtils.atan2(((this.f87y * this.f85w) + (this.f86x * this.f88z)) * 2.0f, 1.0f - (((this.f87y * this.f87y) + (this.f86x * this.f86x)) * 2.0f)) : 0.0f;
    }

    public float getYaw() {
        return getYawRad() * 57.295776f;
    }

    public static final float len2(float x, float y, float z, float w) {
        return (((x * x) + (y * y)) + (z * z)) + (w * w);
    }

    public float len2() {
        return (((this.f86x * this.f86x) + (this.f87y * this.f87y)) + (this.f88z * this.f88z)) + (this.f85w * this.f85w);
    }

    public Quaternion nor() {
        float len = len2();
        if (!(len == 0.0f || MathUtils.isEqual(len, 1.0f))) {
            len = (float) Math.sqrt((double) len);
            this.f85w /= len;
            this.f86x /= len;
            this.f87y /= len;
            this.f88z /= len;
        }
        return this;
    }

    public Quaternion conjugate() {
        this.f86x = -this.f86x;
        this.f87y = -this.f87y;
        this.f88z = -this.f88z;
        return this;
    }

    public Vector3 transform(Vector3 v) {
        tmp2.set(this);
        tmp2.conjugate();
        tmp2.mulLeft(tmp1.set(v.f120x, v.f121y, v.f122z, 0.0f)).mulLeft(this);
        v.f120x = tmp2.f86x;
        v.f121y = tmp2.f87y;
        v.f122z = tmp2.f88z;
        return v;
    }

    public Quaternion mul(Quaternion other) {
        float newY = (((this.f85w * other.f87y) + (this.f87y * other.f85w)) + (this.f88z * other.f86x)) - (this.f86x * other.f88z);
        float newZ = (((this.f85w * other.f88z) + (this.f88z * other.f85w)) + (this.f86x * other.f87y)) - (this.f87y * other.f86x);
        float newW = (((this.f85w * other.f85w) - (this.f86x * other.f86x)) - (this.f87y * other.f87y)) - (this.f88z * other.f88z);
        this.f86x = (((this.f85w * other.f86x) + (this.f86x * other.f85w)) + (this.f87y * other.f88z)) - (this.f88z * other.f87y);
        this.f87y = newY;
        this.f88z = newZ;
        this.f85w = newW;
        return this;
    }

    public Quaternion mul(float x, float y, float z, float w) {
        float newY = (((this.f85w * y) + (this.f87y * w)) + (this.f88z * x)) - (this.f86x * z);
        float newZ = (((this.f85w * z) + (this.f88z * w)) + (this.f86x * y)) - (this.f87y * x);
        float newW = (((this.f85w * w) - (this.f86x * x)) - (this.f87y * y)) - (this.f88z * z);
        this.f86x = (((this.f85w * x) + (this.f86x * w)) + (this.f87y * z)) - (this.f88z * y);
        this.f87y = newY;
        this.f88z = newZ;
        this.f85w = newW;
        return this;
    }

    public Quaternion mulLeft(Quaternion other) {
        float newY = (((other.f85w * this.f87y) + (other.f87y * this.f85w)) + (other.f88z * this.f86x)) - (other.f86x * this.f88z);
        float newZ = (((other.f85w * this.f88z) + (other.f88z * this.f85w)) + (other.f86x * this.f87y)) - (other.f87y * this.f86x);
        float newW = (((other.f85w * this.f85w) - (other.f86x * this.f86x)) - (other.f87y * this.f87y)) - (other.f88z * this.f88z);
        this.f86x = (((other.f85w * this.f86x) + (other.f86x * this.f85w)) + (other.f87y * this.f88z)) - (other.f88z * this.f87y);
        this.f87y = newY;
        this.f88z = newZ;
        this.f85w = newW;
        return this;
    }

    public Quaternion mulLeft(float x, float y, float z, float w) {
        float newY = (((this.f87y * w) + (this.f85w * y)) + (this.f86x * z)) - (x * z);
        float newZ = (((this.f88z * w) + (this.f85w * z)) + (this.f87y * x)) - (y * x);
        float newW = (((this.f85w * w) - (this.f86x * x)) - (this.f87y * y)) - (z * z);
        this.f86x = (((this.f86x * w) + (this.f85w * x)) + (this.f88z * y)) - (z * y);
        this.f87y = newY;
        this.f88z = newZ;
        this.f85w = newW;
        return this;
    }

    public Quaternion add(Quaternion quaternion) {
        this.f86x += quaternion.f86x;
        this.f87y += quaternion.f87y;
        this.f88z += quaternion.f88z;
        this.f85w += quaternion.f85w;
        return this;
    }

    public Quaternion add(float qx, float qy, float qz, float qw) {
        this.f86x += qx;
        this.f87y += qy;
        this.f88z += qz;
        this.f85w += qw;
        return this;
    }

    public void toMatrix(float[] matrix) {
        float xx = this.f86x * this.f86x;
        float xy = this.f86x * this.f87y;
        float xz = this.f86x * this.f88z;
        float xw = this.f86x * this.f85w;
        float yy = this.f87y * this.f87y;
        float yz = this.f87y * this.f88z;
        float yw = this.f87y * this.f85w;
        float zz = this.f88z * this.f88z;
        float zw = this.f88z * this.f85w;
        matrix[0] = 1.0f - ((yy + zz) * 2.0f);
        matrix[4] = (xy - zw) * 2.0f;
        matrix[8] = (xz + yw) * 2.0f;
        matrix[12] = 0.0f;
        matrix[1] = (xy + zw) * 2.0f;
        matrix[5] = 1.0f - ((xx + zz) * 2.0f);
        matrix[9] = (yz - xw) * 2.0f;
        matrix[13] = 0.0f;
        matrix[2] = (xz - yw) * 2.0f;
        matrix[6] = (yz + xw) * 2.0f;
        matrix[10] = 1.0f - ((xx + yy) * 2.0f);
        matrix[14] = 0.0f;
        matrix[3] = 0.0f;
        matrix[7] = 0.0f;
        matrix[11] = 0.0f;
        matrix[15] = 1.0f;
    }

    public Quaternion idt() {
        return set(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public boolean isIdentity() {
        return MathUtils.isZero(this.f86x) && MathUtils.isZero(this.f87y) && MathUtils.isZero(this.f88z) && MathUtils.isEqual(this.f85w, 1.0f);
    }

    public boolean isIdentity(float tolerance) {
        return MathUtils.isZero(this.f86x, tolerance) && MathUtils.isZero(this.f87y, tolerance) && MathUtils.isZero(this.f88z, tolerance) && MathUtils.isEqual(this.f85w, 1.0f, tolerance);
    }

    public Quaternion setFromAxis(Vector3 axis, float degrees) {
        return setFromAxis(axis.f120x, axis.f121y, axis.f122z, degrees);
    }

    public Quaternion setFromAxisRad(Vector3 axis, float radians) {
        return setFromAxisRad(axis.f120x, axis.f121y, axis.f122z, radians);
    }

    public Quaternion setFromAxis(float x, float y, float z, float degrees) {
        return setFromAxisRad(x, y, z, 0.017453292f * degrees);
    }

    public Quaternion setFromAxisRad(float x, float y, float z, float radians) {
        float d = Vector3.len(x, y, z);
        if (d == 0.0f) {
            return idt();
        }
        float d2 = 1.0f / d;
        d = radians < 0.0f ? 6.2831855f - ((-radians) % 6.2831855f) : radians % 6.2831855f;
        float l_sin = (float) Math.sin((double) (d / 2.0f));
        return set((d2 * x) * l_sin, (d2 * y) * l_sin, (d2 * z) * l_sin, (float) Math.cos((double) (d / 2.0f))).nor();
    }

    public Quaternion setFromMatrix(boolean normalizeAxes, Matrix4 matrix) {
        return setFromAxes(normalizeAxes, matrix.val[0], matrix.val[4], matrix.val[8], matrix.val[1], matrix.val[5], matrix.val[9], matrix.val[2], matrix.val[6], matrix.val[10]);
    }

    public Quaternion setFromMatrix(Matrix4 matrix) {
        return setFromMatrix(false, matrix);
    }

    public Quaternion setFromMatrix(boolean normalizeAxes, Matrix3 matrix) {
        return setFromAxes(normalizeAxes, matrix.val[0], matrix.val[3], matrix.val[6], matrix.val[1], matrix.val[4], matrix.val[7], matrix.val[2], matrix.val[5], matrix.val[8]);
    }

    public Quaternion setFromMatrix(Matrix3 matrix) {
        return setFromMatrix(false, matrix);
    }

    public Quaternion setFromAxes(float xx, float xy, float xz, float yx, float yy, float yz, float zx, float zy, float zz) {
        return setFromAxes(false, xx, xy, xz, yx, yy, yz, zx, zy, zz);
    }

    public Quaternion setFromAxes(boolean normalizeAxes, float xx, float xy, float xz, float yx, float yy, float yz, float zx, float zy, float zz) {
        float lx;
        float ly;
        float xx2;
        float xy2;
        float xz2;
        float yy2;
        float yz2;
        float zx2;
        float zy2;
        Quaternion quaternion = this;
        if (normalizeAxes) {
            lx = 1.0f / Vector3.len(xx, xy, xz);
            ly = 1.0f / Vector3.len(yx, yy, yz);
            float lz = 1.0f / Vector3.len(zx, zy, zz);
            xx2 = xx * lx;
            xy2 = xy * lx;
            xz2 = xz * lx;
            yy2 = yy * ly;
            yz2 = (yz * ly) * ly;
            zx2 = zx * lz;
            zy2 = zy * lz;
            lx = zz * lz;
        } else {
            xx2 = xx;
            xy2 = xy;
            xz2 = xz;
            yy2 = yy;
            yz2 = yz;
            zx2 = zx;
            zy2 = zy;
            lx = zz;
        }
        ly = (xx2 + yy2) + lx;
        float s;
        float s2;
        if (ly >= 0.0f) {
            s = (float) Math.sqrt((double) (1.0f + ly));
            quaternion.f85w = s * 0.5f;
            s2 = 0.5f / s;
            quaternion.f86x = (zy2 - yz2) * s2;
            quaternion.f87y = (xz2 - zx2) * s2;
            quaternion.f88z = (yx - xy2) * s2;
        } else if (xx2 > yy2 && xx2 > lx) {
            s = (float) Math.sqrt(((((double) xx2) + 1.0d) - ((double) yy2)) - ((double) lx));
            quaternion.f86x = s * 0.5f;
            s2 = 0.5f / s;
            quaternion.f87y = (yx + xy2) * s2;
            quaternion.f88z = (xz2 + zx2) * s2;
            quaternion.f85w = (zy2 - yz2) * s2;
        } else if (yy2 > lx) {
            s = (float) Math.sqrt(((((double) yy2) + 1.0d) - ((double) xx2)) - ((double) lx));
            quaternion.f87y = s * 0.5f;
            s2 = 0.5f / s;
            quaternion.f86x = (yx + xy2) * s2;
            quaternion.f88z = (zy2 + yz2) * s2;
            quaternion.f85w = (xz2 - zx2) * s2;
        } else {
            s = (float) Math.sqrt(((((double) lx) + 1.0d) - ((double) xx2)) - ((double) yy2));
            quaternion.f88z = s * 0.5f;
            s2 = 0.5f / s;
            quaternion.f86x = (xz2 + zx2) * s2;
            quaternion.f87y = (zy2 + yz2) * s2;
            quaternion.f85w = (yx - xy2) * s2;
        }
        return quaternion;
    }

    public Quaternion setFromCross(Vector3 v1, Vector3 v2) {
        return setFromAxisRad((v1.f121y * v2.f122z) - (v1.f122z * v2.f121y), (v1.f122z * v2.f120x) - (v1.f120x * v2.f122z), (v1.f120x * v2.f121y) - (v1.f121y * v2.f120x), (float) Math.acos((double) MathUtils.clamp(v1.dot(v2), -1.0f, 1.0f)));
    }

    public Quaternion setFromCross(float x1, float y1, float z1, float x2, float y2, float z2) {
        return setFromAxisRad((y1 * z2) - (z1 * y2), (z1 * x2) - (x1 * z2), (x1 * y2) - (y1 * x2), (float) Math.acos((double) MathUtils.clamp(Vector3.dot(x1, y1, z1, x2, y2, z2), -1.0f, 1.0f)));
    }

    public Quaternion slerp(Quaternion end, float alpha) {
        float d = (((this.f86x * end.f86x) + (this.f87y * end.f87y)) + (this.f88z * end.f88z)) + (this.f85w * end.f85w);
        float absDot = d < 0.0f ? -d : d;
        float scale0 = 1.0f - alpha;
        float scale1 = alpha;
        if (((double) (1.0f - absDot)) > 0.1d) {
            float angle = (float) Math.acos((double) absDot);
            float invSinTheta = 1.0f / ((float) Math.sin((double) angle));
            scale0 = ((float) Math.sin((double) ((1.0f - alpha) * angle))) * invSinTheta;
            scale1 = ((float) Math.sin((double) (alpha * angle))) * invSinTheta;
        }
        if (d < 0.0f) {
            scale1 = -scale1;
        }
        this.f86x = (this.f86x * scale0) + (end.f86x * scale1);
        this.f87y = (this.f87y * scale0) + (end.f87y * scale1);
        this.f88z = (this.f88z * scale0) + (end.f88z * scale1);
        this.f85w = (this.f85w * scale0) + (end.f85w * scale1);
        return this;
    }

    public Quaternion slerp(Quaternion[] q) {
        float w = 1.0f / ((float) q.length);
        set(q[0]).exp(w);
        for (int i = 1; i < q.length; i++) {
            mul(tmp1.set(q[i]).exp(w));
        }
        nor();
        return this;
    }

    public Quaternion slerp(Quaternion[] q, float[] w) {
        set(q[0]).exp(w[0]);
        for (int i = 1; i < q.length; i++) {
            mul(tmp1.set(q[i]).exp(w[i]));
        }
        nor();
        return this;
    }

    public Quaternion exp(float alpha) {
        float coeff;
        float norm = len();
        float normExp = (float) Math.pow((double) norm, (double) alpha);
        float theta = (float) Math.acos((double) (this.f85w / norm));
        if (((double) Math.abs(theta)) < 0.001d) {
            coeff = (normExp * alpha) / norm;
        } else {
            coeff = (float) ((((double) normExp) * Math.sin((double) (alpha * theta))) / (((double) norm) * Math.sin((double) theta)));
        }
        this.f85w = (float) (((double) normExp) * Math.cos((double) (alpha * theta)));
        this.f86x *= coeff;
        this.f87y *= coeff;
        this.f88z *= coeff;
        nor();
        return this;
    }

    public int hashCode() {
        return (((((((1 * 31) + NumberUtils.floatToRawIntBits(this.f85w)) * 31) + NumberUtils.floatToRawIntBits(this.f86x)) * 31) + NumberUtils.floatToRawIntBits(this.f87y)) * 31) + NumberUtils.floatToRawIntBits(this.f88z);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Quaternion)) {
            return false;
        }
        Quaternion other = (Quaternion) obj;
        if (NumberUtils.floatToRawIntBits(this.f85w) != NumberUtils.floatToRawIntBits(other.f85w) || NumberUtils.floatToRawIntBits(this.f86x) != NumberUtils.floatToRawIntBits(other.f86x) || NumberUtils.floatToRawIntBits(this.f87y) != NumberUtils.floatToRawIntBits(other.f87y) || NumberUtils.floatToRawIntBits(this.f88z) != NumberUtils.floatToRawIntBits(other.f88z)) {
            z = false;
        }
        return z;
    }

    public static final float dot(float x1, float y1, float z1, float w1, float x2, float y2, float z2, float w2) {
        return (((x1 * x2) + (y1 * y2)) + (z1 * z2)) + (w1 * w2);
    }

    public float dot(Quaternion other) {
        return (((this.f86x * other.f86x) + (this.f87y * other.f87y)) + (this.f88z * other.f88z)) + (this.f85w * other.f85w);
    }

    public float dot(float x, float y, float z, float w) {
        return (((this.f86x * x) + (this.f87y * y)) + (this.f88z * z)) + (this.f85w * w);
    }

    public Quaternion mul(float scalar) {
        this.f86x *= scalar;
        this.f87y *= scalar;
        this.f88z *= scalar;
        this.f85w *= scalar;
        return this;
    }

    public float getAxisAngle(Vector3 axis) {
        return getAxisAngleRad(axis) * 57.295776f;
    }

    public float getAxisAngleRad(Vector3 axis) {
        if (this.f85w > 1.0f) {
            nor();
        }
        float angle = (float) (Math.acos((double) this.f85w) * 2.0d);
        double s = Math.sqrt((double) (1.0f - (this.f85w * this.f85w)));
        if (s < 9.999999974752427E-7d) {
            axis.f120x = this.f86x;
            axis.f121y = this.f87y;
            axis.f122z = this.f88z;
        } else {
            axis.f120x = (float) (((double) this.f86x) / s);
            axis.f121y = (float) (((double) this.f87y) / s);
            axis.f122z = (float) (((double) this.f88z) / s);
        }
        return angle;
    }

    public float getAngleRad() {
        return (float) (Math.acos((double) (this.f85w > 1.0f ? this.f85w / len() : this.f85w)) * 2.0d);
    }

    public float getAngle() {
        return getAngleRad() * 57.295776f;
    }

    public void getSwingTwist(float axisX, float axisY, float axisZ, Quaternion swing, Quaternion twist) {
        float d = Vector3.dot(this.f86x, this.f87y, this.f88z, axisX, axisY, axisZ);
        twist.set(axisX * d, axisY * d, axisZ * d, this.f85w).nor();
        swing.set(twist).conjugate().mulLeft(this);
    }

    public void getSwingTwist(Vector3 axis, Quaternion swing, Quaternion twist) {
        getSwingTwist(axis.f120x, axis.f121y, axis.f122z, swing, twist);
    }

    public float getAngleAroundRad(float axisX, float axisY, float axisZ) {
        float d = Vector3.dot(this.f86x, this.f87y, this.f88z, axisX, axisY, axisZ);
        float l2 = len2(axisX * d, axisY * d, axisZ * d, this.f85w);
        return MathUtils.isZero(l2) ? 0.0f : (float) (Math.acos((double) MathUtils.clamp((float) (((double) this.f85w) / Math.sqrt((double) l2)), -1.0f, 1.0f)) * 2.0d);
    }

    public float getAngleAroundRad(Vector3 axis) {
        return getAngleAroundRad(axis.f120x, axis.f121y, axis.f122z);
    }

    public float getAngleAround(float axisX, float axisY, float axisZ) {
        return getAngleAroundRad(axisX, axisY, axisZ) * 57.295776f;
    }

    public float getAngleAround(Vector3 axis) {
        return getAngleAround(axis.f120x, axis.f121y, axis.f122z);
    }
}
