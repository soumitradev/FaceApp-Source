package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.NumberUtils;
import java.io.Serializable;

public class Vector3 implements Serializable, Vector<Vector3> {
    /* renamed from: X */
    public static final Vector3 f117X = new Vector3(1.0f, 0.0f, 0.0f);
    /* renamed from: Y */
    public static final Vector3 f118Y = new Vector3(0.0f, 1.0f, 0.0f);
    /* renamed from: Z */
    public static final Vector3 f119Z = new Vector3(0.0f, 0.0f, 1.0f);
    public static final Vector3 Zero = new Vector3(0.0f, 0.0f, 0.0f);
    private static final long serialVersionUID = 3840054589595372522L;
    private static final Matrix4 tmpMat = new Matrix4();
    /* renamed from: x */
    public float f120x;
    /* renamed from: y */
    public float f121y;
    /* renamed from: z */
    public float f122z;

    public Vector3(float x, float y, float z) {
        set(x, y, z);
    }

    public Vector3(Vector3 vector) {
        set(vector);
    }

    public Vector3(float[] values) {
        set(values[0], values[1], values[2]);
    }

    public Vector3(Vector2 vector, float z) {
        set(vector.f16x, vector.f17y, z);
    }

    public Vector3 set(float x, float y, float z) {
        this.f120x = x;
        this.f121y = y;
        this.f122z = z;
        return this;
    }

    public Vector3 set(Vector3 vector) {
        return set(vector.f120x, vector.f121y, vector.f122z);
    }

    public Vector3 set(float[] values) {
        return set(values[0], values[1], values[2]);
    }

    public Vector3 set(Vector2 vector, float z) {
        return set(vector.f16x, vector.f17y, z);
    }

    public Vector3 cpy() {
        return new Vector3(this);
    }

    public Vector3 add(Vector3 vector) {
        return add(vector.f120x, vector.f121y, vector.f122z);
    }

    public Vector3 add(float x, float y, float z) {
        return set(this.f120x + x, this.f121y + y, this.f122z + z);
    }

    public Vector3 add(float values) {
        return set(this.f120x + values, this.f121y + values, this.f122z + values);
    }

    public Vector3 sub(Vector3 a_vec) {
        return sub(a_vec.f120x, a_vec.f121y, a_vec.f122z);
    }

    public Vector3 sub(float x, float y, float z) {
        return set(this.f120x - x, this.f121y - y, this.f122z - z);
    }

    public Vector3 sub(float value) {
        return set(this.f120x - value, this.f121y - value, this.f122z - value);
    }

    public Vector3 scl(float scalar) {
        return set(this.f120x * scalar, this.f121y * scalar, this.f122z * scalar);
    }

    public Vector3 scl(Vector3 other) {
        return set(this.f120x * other.f120x, this.f121y * other.f121y, this.f122z * other.f122z);
    }

    public Vector3 scl(float vx, float vy, float vz) {
        return set(this.f120x * vx, this.f121y * vy, this.f122z * vz);
    }

    public Vector3 mulAdd(Vector3 vec, float scalar) {
        this.f120x += vec.f120x * scalar;
        this.f121y += vec.f121y * scalar;
        this.f122z += vec.f122z * scalar;
        return this;
    }

    public Vector3 mulAdd(Vector3 vec, Vector3 mulVec) {
        this.f120x += vec.f120x * mulVec.f120x;
        this.f121y += vec.f121y * mulVec.f121y;
        this.f122z += vec.f122z * mulVec.f122z;
        return this;
    }

    public static float len(float x, float y, float z) {
        return (float) Math.sqrt((double) (((x * x) + (y * y)) + (z * z)));
    }

    public float len() {
        return (float) Math.sqrt((double) (((this.f120x * this.f120x) + (this.f121y * this.f121y)) + (this.f122z * this.f122z)));
    }

    public static float len2(float x, float y, float z) {
        return ((x * x) + (y * y)) + (z * z);
    }

    public float len2() {
        return ((this.f120x * this.f120x) + (this.f121y * this.f121y)) + (this.f122z * this.f122z);
    }

    public boolean idt(Vector3 vector) {
        return this.f120x == vector.f120x && this.f121y == vector.f121y && this.f122z == vector.f122z;
    }

    public static float dst(float x1, float y1, float z1, float x2, float y2, float z2) {
        float a = x2 - x1;
        float b = y2 - y1;
        float c = z2 - z1;
        return (float) Math.sqrt((double) (((a * a) + (b * b)) + (c * c)));
    }

    public float dst(Vector3 vector) {
        float a = vector.f120x - this.f120x;
        float b = vector.f121y - this.f121y;
        float c = vector.f122z - this.f122z;
        return (float) Math.sqrt((double) (((a * a) + (b * b)) + (c * c)));
    }

    public float dst(float x, float y, float z) {
        float a = x - this.f120x;
        float b = y - this.f121y;
        float c = z - this.f122z;
        return (float) Math.sqrt((double) (((a * a) + (b * b)) + (c * c)));
    }

    public static float dst2(float x1, float y1, float z1, float x2, float y2, float z2) {
        float a = x2 - x1;
        float b = y2 - y1;
        float c = z2 - z1;
        return ((a * a) + (b * b)) + (c * c);
    }

    public float dst2(Vector3 point) {
        float a = point.f120x - this.f120x;
        float b = point.f121y - this.f121y;
        float c = point.f122z - this.f122z;
        return ((a * a) + (b * b)) + (c * c);
    }

    public float dst2(float x, float y, float z) {
        float a = x - this.f120x;
        float b = y - this.f121y;
        float c = z - this.f122z;
        return ((a * a) + (b * b)) + (c * c);
    }

    public Vector3 nor() {
        float len2 = len2();
        if (len2 != 0.0f) {
            if (len2 != 1.0f) {
                return scl(1.0f / ((float) Math.sqrt((double) len2)));
            }
        }
        return this;
    }

    public static float dot(float x1, float y1, float z1, float x2, float y2, float z2) {
        return ((x1 * x2) + (y1 * y2)) + (z1 * z2);
    }

    public float dot(Vector3 vector) {
        return ((this.f120x * vector.f120x) + (this.f121y * vector.f121y)) + (this.f122z * vector.f122z);
    }

    public float dot(float x, float y, float z) {
        return ((this.f120x * x) + (this.f121y * y)) + (this.f122z * z);
    }

    public Vector3 crs(Vector3 vector) {
        return set((this.f121y * vector.f122z) - (this.f122z * vector.f121y), (this.f122z * vector.f120x) - (this.f120x * vector.f122z), (this.f120x * vector.f121y) - (this.f121y * vector.f120x));
    }

    public Vector3 crs(float x, float y, float z) {
        return set((this.f121y * z) - (this.f122z * y), (this.f122z * x) - (this.f120x * z), (this.f120x * y) - (this.f121y * x));
    }

    public Vector3 mul4x3(float[] matrix) {
        return set((((this.f120x * matrix[0]) + (this.f121y * matrix[3])) + (this.f122z * matrix[6])) + matrix[9], (((this.f120x * matrix[1]) + (this.f121y * matrix[4])) + (this.f122z * matrix[7])) + matrix[10], (((this.f120x * matrix[2]) + (this.f121y * matrix[5])) + (this.f122z * matrix[8])) + matrix[11]);
    }

    public Vector3 mul(Matrix4 matrix) {
        float[] l_mat = matrix.val;
        return set((((this.f120x * l_mat[0]) + (this.f121y * l_mat[4])) + (this.f122z * l_mat[8])) + l_mat[12], (((this.f120x * l_mat[1]) + (this.f121y * l_mat[5])) + (this.f122z * l_mat[9])) + l_mat[13], (((this.f120x * l_mat[2]) + (this.f121y * l_mat[6])) + (this.f122z * l_mat[10])) + l_mat[14]);
    }

    public Vector3 traMul(Matrix4 matrix) {
        float[] l_mat = matrix.val;
        return set((((this.f120x * l_mat[0]) + (this.f121y * l_mat[1])) + (this.f122z * l_mat[2])) + l_mat[3], (((this.f120x * l_mat[4]) + (this.f121y * l_mat[5])) + (this.f122z * l_mat[6])) + l_mat[7], (((this.f120x * l_mat[8]) + (this.f121y * l_mat[9])) + (this.f122z * l_mat[10])) + l_mat[11]);
    }

    public Vector3 mul(Matrix3 matrix) {
        float[] l_mat = matrix.val;
        return set(((this.f120x * l_mat[0]) + (this.f121y * l_mat[3])) + (this.f122z * l_mat[6]), ((this.f120x * l_mat[1]) + (this.f121y * l_mat[4])) + (this.f122z * l_mat[7]), ((this.f120x * l_mat[2]) + (this.f121y * l_mat[5])) + (this.f122z * l_mat[8]));
    }

    public Vector3 traMul(Matrix3 matrix) {
        float[] l_mat = matrix.val;
        return set(((this.f120x * l_mat[0]) + (this.f121y * l_mat[1])) + (this.f122z * l_mat[2]), ((this.f120x * l_mat[3]) + (this.f121y * l_mat[4])) + (this.f122z * l_mat[5]), ((this.f120x * l_mat[6]) + (this.f121y * l_mat[7])) + (this.f122z * l_mat[8]));
    }

    public Vector3 mul(Quaternion quat) {
        return quat.transform(this);
    }

    public Vector3 prj(Matrix4 matrix) {
        float[] l_mat = matrix.val;
        float l_w = 1.0f / ((((this.f120x * l_mat[3]) + (this.f121y * l_mat[7])) + (this.f122z * l_mat[11])) + l_mat[15]);
        return set(((((this.f120x * l_mat[0]) + (this.f121y * l_mat[4])) + (this.f122z * l_mat[8])) + l_mat[12]) * l_w, ((((this.f120x * l_mat[1]) + (this.f121y * l_mat[5])) + (this.f122z * l_mat[9])) + l_mat[13]) * l_w, ((((this.f120x * l_mat[2]) + (this.f121y * l_mat[6])) + (this.f122z * l_mat[10])) + l_mat[14]) * l_w);
    }

    public Vector3 rot(Matrix4 matrix) {
        float[] l_mat = matrix.val;
        return set(((this.f120x * l_mat[0]) + (this.f121y * l_mat[4])) + (this.f122z * l_mat[8]), ((this.f120x * l_mat[1]) + (this.f121y * l_mat[5])) + (this.f122z * l_mat[9]), ((this.f120x * l_mat[2]) + (this.f121y * l_mat[6])) + (this.f122z * l_mat[10]));
    }

    public Vector3 unrotate(Matrix4 matrix) {
        float[] l_mat = matrix.val;
        return set(((this.f120x * l_mat[0]) + (this.f121y * l_mat[1])) + (this.f122z * l_mat[2]), ((this.f120x * l_mat[4]) + (this.f121y * l_mat[5])) + (this.f122z * l_mat[6]), ((this.f120x * l_mat[8]) + (this.f121y * l_mat[9])) + (this.f122z * l_mat[10]));
    }

    public Vector3 untransform(Matrix4 matrix) {
        float[] l_mat = matrix.val;
        this.f120x -= l_mat[12];
        this.f121y -= l_mat[12];
        this.f122z -= l_mat[12];
        return set(((this.f120x * l_mat[0]) + (this.f121y * l_mat[1])) + (this.f122z * l_mat[2]), ((this.f120x * l_mat[4]) + (this.f121y * l_mat[5])) + (this.f122z * l_mat[6]), ((this.f120x * l_mat[8]) + (this.f121y * l_mat[9])) + (this.f122z * l_mat[10]));
    }

    public Vector3 rotate(float degrees, float axisX, float axisY, float axisZ) {
        return mul(tmpMat.setToRotation(axisX, axisY, axisZ, degrees));
    }

    public Vector3 rotateRad(float radians, float axisX, float axisY, float axisZ) {
        return mul(tmpMat.setToRotationRad(axisX, axisY, axisZ, radians));
    }

    public Vector3 rotate(Vector3 axis, float degrees) {
        tmpMat.setToRotation(axis, degrees);
        return mul(tmpMat);
    }

    public Vector3 rotateRad(Vector3 axis, float radians) {
        tmpMat.setToRotationRad(axis, radians);
        return mul(tmpMat);
    }

    public boolean isUnit() {
        return isUnit(1.0E-9f);
    }

    public boolean isUnit(float margin) {
        return Math.abs(len2() - 1.0f) < margin;
    }

    public boolean isZero() {
        return this.f120x == 0.0f && this.f121y == 0.0f && this.f122z == 0.0f;
    }

    public boolean isZero(float margin) {
        return len2() < margin;
    }

    public boolean isOnLine(Vector3 other, float epsilon) {
        return len2((this.f121y * other.f122z) - (this.f122z * other.f121y), (this.f122z * other.f120x) - (this.f120x * other.f122z), (this.f120x * other.f121y) - (this.f121y * other.f120x)) <= epsilon;
    }

    public boolean isOnLine(Vector3 other) {
        return len2((this.f121y * other.f122z) - (this.f122z * other.f121y), (this.f122z * other.f120x) - (this.f120x * other.f122z), (this.f120x * other.f121y) - (this.f121y * other.f120x)) <= 1.0E-6f;
    }

    public boolean isCollinear(Vector3 other, float epsilon) {
        return isOnLine(other, epsilon) && hasSameDirection(other);
    }

    public boolean isCollinear(Vector3 other) {
        return isOnLine(other) && hasSameDirection(other);
    }

    public boolean isCollinearOpposite(Vector3 other, float epsilon) {
        return isOnLine(other, epsilon) && hasOppositeDirection(other);
    }

    public boolean isCollinearOpposite(Vector3 other) {
        return isOnLine(other) && hasOppositeDirection(other);
    }

    public boolean isPerpendicular(Vector3 vector) {
        return MathUtils.isZero(dot(vector));
    }

    public boolean isPerpendicular(Vector3 vector, float epsilon) {
        return MathUtils.isZero(dot(vector), epsilon);
    }

    public boolean hasSameDirection(Vector3 vector) {
        return dot(vector) > 0.0f;
    }

    public boolean hasOppositeDirection(Vector3 vector) {
        return dot(vector) < 0.0f;
    }

    public Vector3 lerp(Vector3 target, float alpha) {
        this.f120x += (target.f120x - this.f120x) * alpha;
        this.f121y += (target.f121y - this.f121y) * alpha;
        this.f122z += (target.f122z - this.f122z) * alpha;
        return this;
    }

    public Vector3 interpolate(Vector3 target, float alpha, Interpolation interpolator) {
        return lerp(target, interpolator.apply(0.0f, 1.0f, alpha));
    }

    public Vector3 slerp(Vector3 target, float alpha) {
        float dot = dot(target);
        if (((double) dot) <= 0.9995d) {
            if (((double) dot) >= -0.9995d) {
                float theta = ((float) Math.acos((double) dot)) * alpha;
                float st = (float) Math.sin((double) theta);
                float tx = target.f120x - (this.f120x * dot);
                float ty = target.f121y - (this.f121y * dot);
                float tz = target.f122z - (this.f122z * dot);
                float l2 = ((tx * tx) + (ty * ty)) + (tz * tz);
                float dl = 1.0f;
                if (l2 >= 1.0E-4f) {
                    dl = 1.0f / ((float) Math.sqrt((double) l2));
                }
                dl *= st;
                return scl((float) Math.cos((double) theta)).add(tx * dl, ty * dl, tz * dl).nor();
            }
        }
        return lerp(target, alpha);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.f120x);
        stringBuilder.append(", ");
        stringBuilder.append(this.f121y);
        stringBuilder.append(", ");
        stringBuilder.append(this.f122z);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public Vector3 limit(float limit) {
        return limit2(limit * limit);
    }

    public Vector3 limit2(float limit2) {
        float len2 = len2();
        if (len2 > limit2) {
            scl((float) Math.sqrt((double) (limit2 / len2)));
        }
        return this;
    }

    public Vector3 setLength(float len) {
        return setLength2(len * len);
    }

    public Vector3 setLength2(float len2) {
        float oldLen2 = len2();
        if (oldLen2 != 0.0f) {
            if (oldLen2 != len2) {
                return scl((float) Math.sqrt((double) (len2 / oldLen2)));
            }
        }
        return this;
    }

    public Vector3 clamp(float min, float max) {
        float len2 = len2();
        if (len2 == 0.0f) {
            return this;
        }
        float max2 = max * max;
        if (len2 > max2) {
            return scl((float) Math.sqrt((double) (max2 / len2)));
        }
        float min2 = min * min;
        if (len2 < min2) {
            return scl((float) Math.sqrt((double) (min2 / len2)));
        }
        return this;
    }

    public int hashCode() {
        return (((((1 * 31) + NumberUtils.floatToIntBits(this.f120x)) * 31) + NumberUtils.floatToIntBits(this.f121y)) * 31) + NumberUtils.floatToIntBits(this.f122z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vector3 other = (Vector3) obj;
        if (NumberUtils.floatToIntBits(this.f120x) == NumberUtils.floatToIntBits(other.f120x) && NumberUtils.floatToIntBits(this.f121y) == NumberUtils.floatToIntBits(other.f121y) && NumberUtils.floatToIntBits(this.f122z) == NumberUtils.floatToIntBits(other.f122z)) {
            return true;
        }
        return false;
    }

    public boolean epsilonEquals(Vector3 other, float epsilon) {
        if (other != null && Math.abs(other.f120x - this.f120x) <= epsilon && Math.abs(other.f121y - this.f121y) <= epsilon && Math.abs(other.f122z - this.f122z) <= epsilon) {
            return true;
        }
        return false;
    }

    public boolean epsilonEquals(float x, float y, float z, float epsilon) {
        if (Math.abs(x - this.f120x) <= epsilon && Math.abs(y - this.f121y) <= epsilon && Math.abs(z - this.f122z) <= epsilon) {
            return true;
        }
        return false;
    }

    public Vector3 setZero() {
        this.f120x = 0.0f;
        this.f121y = 0.0f;
        this.f122z = 0.0f;
        return this;
    }
}
