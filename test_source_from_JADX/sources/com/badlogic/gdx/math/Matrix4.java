package com.badlogic.gdx.math;

import java.io.Serializable;

public class Matrix4 implements Serializable {
    public static final int M00 = 0;
    public static final int M01 = 4;
    public static final int M02 = 8;
    public static final int M03 = 12;
    public static final int M10 = 1;
    public static final int M11 = 5;
    public static final int M12 = 9;
    public static final int M13 = 13;
    public static final int M20 = 2;
    public static final int M21 = 6;
    public static final int M22 = 10;
    public static final int M23 = 14;
    public static final int M30 = 3;
    public static final int M31 = 7;
    public static final int M32 = 11;
    public static final int M33 = 15;
    static final Vector3 l_vex = new Vector3();
    static final Vector3 l_vey = new Vector3();
    static final Vector3 l_vez = new Vector3();
    static Quaternion quat = new Quaternion();
    static Quaternion quat2 = new Quaternion();
    static final Vector3 right = new Vector3();
    private static final long serialVersionUID = -2717655254359579617L;
    private static final float[] tmp = new float[16];
    static final Vector3 tmpForward = new Vector3();
    static final Matrix4 tmpMat = new Matrix4();
    static final Vector3 tmpUp = new Vector3();
    static final Vector3 tmpVec = new Vector3();
    public final float[] val;

    public static native float det(float[] fArr);

    public static native boolean inv(float[] fArr);

    public static native void mul(float[] fArr, float[] fArr2);

    public static native void mulVec(float[] fArr, float[] fArr2);

    public static native void mulVec(float[] fArr, float[] fArr2, int i, int i2, int i3);

    public static native void prj(float[] fArr, float[] fArr2);

    public static native void prj(float[] fArr, float[] fArr2, int i, int i2, int i3);

    public static native void rot(float[] fArr, float[] fArr2);

    public static native void rot(float[] fArr, float[] fArr2, int i, int i2, int i3);

    public Matrix4() {
        this.val = new float[16];
        this.val[0] = 1.0f;
        this.val[5] = 1.0f;
        this.val[10] = 1.0f;
        this.val[15] = 1.0f;
    }

    public Matrix4(Matrix4 matrix) {
        this.val = new float[16];
        set(matrix);
    }

    public Matrix4(float[] values) {
        this.val = new float[16];
        set(values);
    }

    public Matrix4(Quaternion quaternion) {
        this.val = new float[16];
        set(quaternion);
    }

    public Matrix4(Vector3 position, Quaternion rotation, Vector3 scale) {
        this.val = new float[16];
        set(position, rotation, scale);
    }

    public Matrix4 set(Matrix4 matrix) {
        return set(matrix.val);
    }

    public Matrix4 set(float[] values) {
        System.arraycopy(values, 0, this.val, 0, this.val.length);
        return this;
    }

    public Matrix4 set(Quaternion quaternion) {
        return set(quaternion.f86x, quaternion.f87y, quaternion.f88z, quaternion.f85w);
    }

    public Matrix4 set(float quaternionX, float quaternionY, float quaternionZ, float quaternionW) {
        return set(0.0f, 0.0f, 0.0f, quaternionX, quaternionY, quaternionZ, quaternionW);
    }

    public Matrix4 set(Vector3 position, Quaternion orientation) {
        return set(position.f120x, position.f121y, position.f122z, orientation.f86x, orientation.f87y, orientation.f88z, orientation.f85w);
    }

    public Matrix4 set(float translationX, float translationY, float translationZ, float quaternionX, float quaternionY, float quaternionZ, float quaternionW) {
        float xs = quaternionX * 2.0f;
        float ys = quaternionY * 2.0f;
        float zs = 2.0f * quaternionZ;
        float wx = quaternionW * xs;
        float wy = quaternionW * ys;
        float wz = quaternionW * zs;
        float xx = quaternionX * xs;
        float xy = quaternionX * ys;
        float xz = quaternionX * zs;
        float yy = quaternionY * ys;
        float yz = quaternionY * zs;
        float zz = quaternionZ * zs;
        this.val[0] = 1.0f - (yy + zz);
        this.val[4] = xy - wz;
        this.val[8] = xz + wy;
        this.val[12] = translationX;
        this.val[1] = xy + wz;
        this.val[5] = 1.0f - (xx + zz);
        this.val[9] = yz - wx;
        this.val[13] = translationY;
        this.val[2] = xz - wy;
        this.val[6] = yz + wx;
        this.val[10] = 1.0f - (xx + yy);
        this.val[14] = translationZ;
        this.val[3] = 0.0f;
        this.val[7] = 0.0f;
        this.val[11] = 0.0f;
        this.val[15] = 1.0f;
        return r0;
    }

    public Matrix4 set(Vector3 position, Quaternion orientation, Vector3 scale) {
        return set(position.f120x, position.f121y, position.f122z, orientation.f86x, orientation.f87y, orientation.f88z, orientation.f85w, scale.f120x, scale.f121y, scale.f122z);
    }

    public Matrix4 set(float translationX, float translationY, float translationZ, float quaternionX, float quaternionY, float quaternionZ, float quaternionW, float scaleX, float scaleY, float scaleZ) {
        float xs = quaternionX * 2.0f;
        float ys = quaternionY * 2.0f;
        float zs = 2.0f * quaternionZ;
        float wx = quaternionW * xs;
        float wy = quaternionW * ys;
        float wz = quaternionW * zs;
        float xx = quaternionX * xs;
        float xy = quaternionX * ys;
        float xz = quaternionX * zs;
        float yy = quaternionY * ys;
        float yz = quaternionY * zs;
        float zz = quaternionZ * zs;
        this.val[0] = (1.0f - (yy + zz)) * scaleX;
        this.val[4] = (xy - wz) * scaleY;
        this.val[8] = (xz + wy) * scaleZ;
        this.val[12] = translationX;
        this.val[1] = (xy + wz) * scaleX;
        this.val[5] = (1.0f - (xx + zz)) * scaleY;
        this.val[9] = (yz - wx) * scaleZ;
        this.val[13] = translationY;
        this.val[2] = (xz - wy) * scaleX;
        this.val[6] = (yz + wx) * scaleY;
        this.val[10] = (1.0f - (xx + yy)) * scaleZ;
        this.val[14] = translationZ;
        this.val[3] = 0.0f;
        this.val[7] = 0.0f;
        this.val[11] = 0.0f;
        this.val[15] = 1.0f;
        return r0;
    }

    public Matrix4 set(Vector3 xAxis, Vector3 yAxis, Vector3 zAxis, Vector3 pos) {
        this.val[0] = xAxis.f120x;
        this.val[4] = xAxis.f121y;
        this.val[8] = xAxis.f122z;
        this.val[1] = yAxis.f120x;
        this.val[5] = yAxis.f121y;
        this.val[9] = yAxis.f122z;
        this.val[2] = zAxis.f120x;
        this.val[6] = zAxis.f121y;
        this.val[10] = zAxis.f122z;
        this.val[12] = pos.f120x;
        this.val[13] = pos.f121y;
        this.val[14] = pos.f122z;
        this.val[3] = 0.0f;
        this.val[7] = 0.0f;
        this.val[11] = 0.0f;
        this.val[15] = 1.0f;
        return this;
    }

    public Matrix4 cpy() {
        return new Matrix4(this);
    }

    public Matrix4 trn(Vector3 vector) {
        float[] fArr = this.val;
        fArr[12] = fArr[12] + vector.f120x;
        fArr = this.val;
        fArr[13] = fArr[13] + vector.f121y;
        fArr = this.val;
        fArr[14] = fArr[14] + vector.f122z;
        return this;
    }

    public Matrix4 trn(float x, float y, float z) {
        float[] fArr = this.val;
        fArr[12] = fArr[12] + x;
        fArr = this.val;
        fArr[13] = fArr[13] + y;
        fArr = this.val;
        fArr[14] = fArr[14] + z;
        return this;
    }

    public float[] getValues() {
        return this.val;
    }

    public Matrix4 mul(Matrix4 matrix) {
        mul(this.val, matrix.val);
        return this;
    }

    public Matrix4 mulLeft(Matrix4 matrix) {
        tmpMat.set(matrix);
        mul(tmpMat.val, this.val);
        return set(tmpMat);
    }

    public Matrix4 tra() {
        tmp[0] = this.val[0];
        tmp[4] = this.val[1];
        tmp[8] = this.val[2];
        tmp[12] = this.val[3];
        tmp[1] = this.val[4];
        tmp[5] = this.val[5];
        tmp[9] = this.val[6];
        tmp[13] = this.val[7];
        tmp[2] = this.val[8];
        tmp[6] = this.val[9];
        tmp[10] = this.val[10];
        tmp[14] = this.val[11];
        tmp[3] = this.val[12];
        tmp[7] = this.val[13];
        tmp[11] = this.val[14];
        tmp[15] = this.val[15];
        return set(tmp);
    }

    public Matrix4 idt() {
        this.val[0] = 1.0f;
        this.val[4] = 0.0f;
        this.val[8] = 0.0f;
        this.val[12] = 0.0f;
        this.val[1] = 0.0f;
        this.val[5] = 1.0f;
        this.val[9] = 0.0f;
        this.val[13] = 0.0f;
        this.val[2] = 0.0f;
        this.val[6] = 0.0f;
        this.val[10] = 1.0f;
        this.val[14] = 0.0f;
        this.val[3] = 0.0f;
        this.val[7] = 0.0f;
        this.val[11] = 0.0f;
        this.val[15] = 1.0f;
        return this;
    }

    public Matrix4 inv() {
        float l_det = (((((((((((((((((((((((((this.val[3] * this.val[6]) * this.val[9]) * this.val[12]) - (((this.val[2] * this.val[7]) * this.val[9]) * this.val[12])) - (((this.val[3] * this.val[5]) * this.val[10]) * this.val[12])) + (((this.val[1] * this.val[7]) * this.val[10]) * this.val[12])) + (((this.val[2] * this.val[5]) * this.val[11]) * this.val[12])) - (((this.val[1] * this.val[6]) * this.val[11]) * this.val[12])) - (((this.val[3] * this.val[6]) * this.val[8]) * this.val[13])) + (((this.val[2] * this.val[7]) * this.val[8]) * this.val[13])) + (((this.val[3] * this.val[4]) * this.val[10]) * this.val[13])) - (((this.val[0] * this.val[7]) * this.val[10]) * this.val[13])) - (((this.val[2] * this.val[4]) * this.val[11]) * this.val[13])) + (((this.val[0] * this.val[6]) * this.val[11]) * this.val[13])) + (((this.val[3] * this.val[5]) * this.val[8]) * this.val[14])) - (((this.val[1] * this.val[7]) * this.val[8]) * this.val[14])) - (((this.val[3] * this.val[4]) * this.val[9]) * this.val[14])) + (((this.val[0] * this.val[7]) * this.val[9]) * this.val[14])) + (((this.val[1] * this.val[4]) * this.val[11]) * this.val[14])) - (((this.val[0] * this.val[5]) * this.val[11]) * this.val[14])) - (((this.val[2] * this.val[5]) * this.val[8]) * this.val[15])) + (((this.val[1] * this.val[6]) * this.val[8]) * this.val[15])) + (((this.val[2] * this.val[4]) * this.val[9]) * this.val[15])) - (((this.val[0] * this.val[6]) * this.val[9]) * this.val[15])) - (((this.val[1] * this.val[4]) * this.val[10]) * this.val[15])) + (((this.val[0] * this.val[5]) * this.val[10]) * this.val[15]);
        if (l_det == 0.0f) {
            throw new RuntimeException("non-invertible matrix");
        }
        float inv_det = 1.0f / l_det;
        tmp[0] = ((((((r0.val[9] * r0.val[14]) * r0.val[7]) - ((r0.val[13] * r0.val[10]) * r0.val[7])) + ((r0.val[13] * r0.val[6]) * r0.val[11])) - ((r0.val[5] * r0.val[14]) * r0.val[11])) - ((r0.val[9] * r0.val[6]) * r0.val[15])) + ((r0.val[5] * r0.val[10]) * r0.val[15]);
        tmp[4] = ((((((r0.val[12] * r0.val[10]) * r0.val[7]) - ((r0.val[8] * r0.val[14]) * r0.val[7])) - ((r0.val[12] * r0.val[6]) * r0.val[11])) + ((r0.val[4] * r0.val[14]) * r0.val[11])) + ((r0.val[8] * r0.val[6]) * r0.val[15])) - ((r0.val[4] * r0.val[10]) * r0.val[15]);
        tmp[8] = ((((((r0.val[8] * r0.val[13]) * r0.val[7]) - ((r0.val[12] * r0.val[9]) * r0.val[7])) + ((r0.val[12] * r0.val[5]) * r0.val[11])) - ((r0.val[4] * r0.val[13]) * r0.val[11])) - ((r0.val[8] * r0.val[5]) * r0.val[15])) + ((r0.val[4] * r0.val[9]) * r0.val[15]);
        tmp[12] = ((((((r0.val[12] * r0.val[9]) * r0.val[6]) - ((r0.val[8] * r0.val[13]) * r0.val[6])) - ((r0.val[12] * r0.val[5]) * r0.val[10])) + ((r0.val[4] * r0.val[13]) * r0.val[10])) + ((r0.val[8] * r0.val[5]) * r0.val[14])) - ((r0.val[4] * r0.val[9]) * r0.val[14]);
        tmp[1] = ((((((r0.val[13] * r0.val[10]) * r0.val[3]) - ((r0.val[9] * r0.val[14]) * r0.val[3])) - ((r0.val[13] * r0.val[2]) * r0.val[11])) + ((r0.val[1] * r0.val[14]) * r0.val[11])) + ((r0.val[9] * r0.val[2]) * r0.val[15])) - ((r0.val[1] * r0.val[10]) * r0.val[15]);
        tmp[5] = ((((((r0.val[8] * r0.val[14]) * r0.val[3]) - ((r0.val[12] * r0.val[10]) * r0.val[3])) + ((r0.val[12] * r0.val[2]) * r0.val[11])) - ((r0.val[0] * r0.val[14]) * r0.val[11])) - ((r0.val[8] * r0.val[2]) * r0.val[15])) + ((r0.val[0] * r0.val[10]) * r0.val[15]);
        tmp[9] = ((((((r0.val[12] * r0.val[9]) * r0.val[3]) - ((r0.val[8] * r0.val[13]) * r0.val[3])) - ((r0.val[12] * r0.val[1]) * r0.val[11])) + ((r0.val[0] * r0.val[13]) * r0.val[11])) + ((r0.val[8] * r0.val[1]) * r0.val[15])) - ((r0.val[0] * r0.val[9]) * r0.val[15]);
        tmp[13] = ((((((r0.val[8] * r0.val[13]) * r0.val[2]) - ((r0.val[12] * r0.val[9]) * r0.val[2])) + ((r0.val[12] * r0.val[1]) * r0.val[10])) - ((r0.val[0] * r0.val[13]) * r0.val[10])) - ((r0.val[8] * r0.val[1]) * r0.val[14])) + ((r0.val[0] * r0.val[9]) * r0.val[14]);
        tmp[2] = ((((((r0.val[5] * r0.val[14]) * r0.val[3]) - ((r0.val[13] * r0.val[6]) * r0.val[3])) + ((r0.val[13] * r0.val[2]) * r0.val[7])) - ((r0.val[1] * r0.val[14]) * r0.val[7])) - ((r0.val[5] * r0.val[2]) * r0.val[15])) + ((r0.val[1] * r0.val[6]) * r0.val[15]);
        tmp[6] = ((((((r0.val[12] * r0.val[6]) * r0.val[3]) - ((r0.val[4] * r0.val[14]) * r0.val[3])) - ((r0.val[12] * r0.val[2]) * r0.val[7])) + ((r0.val[0] * r0.val[14]) * r0.val[7])) + ((r0.val[4] * r0.val[2]) * r0.val[15])) - ((r0.val[0] * r0.val[6]) * r0.val[15]);
        tmp[10] = ((((((r0.val[4] * r0.val[13]) * r0.val[3]) - ((r0.val[12] * r0.val[5]) * r0.val[3])) + ((r0.val[12] * r0.val[1]) * r0.val[7])) - ((r0.val[0] * r0.val[13]) * r0.val[7])) - ((r0.val[4] * r0.val[1]) * r0.val[15])) + ((r0.val[0] * r0.val[5]) * r0.val[15]);
        tmp[14] = ((((((r0.val[12] * r0.val[5]) * r0.val[2]) - ((r0.val[4] * r0.val[13]) * r0.val[2])) - ((r0.val[12] * r0.val[1]) * r0.val[6])) + ((r0.val[0] * r0.val[13]) * r0.val[6])) + ((r0.val[4] * r0.val[1]) * r0.val[14])) - ((r0.val[0] * r0.val[5]) * r0.val[14]);
        tmp[3] = ((((((r0.val[9] * r0.val[6]) * r0.val[3]) - ((r0.val[5] * r0.val[10]) * r0.val[3])) - ((r0.val[9] * r0.val[2]) * r0.val[7])) + ((r0.val[1] * r0.val[10]) * r0.val[7])) + ((r0.val[5] * r0.val[2]) * r0.val[11])) - ((r0.val[1] * r0.val[6]) * r0.val[11]);
        tmp[7] = ((((((r0.val[4] * r0.val[10]) * r0.val[3]) - ((r0.val[8] * r0.val[6]) * r0.val[3])) + ((r0.val[8] * r0.val[2]) * r0.val[7])) - ((r0.val[0] * r0.val[10]) * r0.val[7])) - ((r0.val[4] * r0.val[2]) * r0.val[11])) + ((r0.val[0] * r0.val[6]) * r0.val[11]);
        tmp[11] = ((((((r0.val[8] * r0.val[5]) * r0.val[3]) - ((r0.val[4] * r0.val[9]) * r0.val[3])) - ((r0.val[8] * r0.val[1]) * r0.val[7])) + ((r0.val[0] * r0.val[9]) * r0.val[7])) + ((r0.val[4] * r0.val[1]) * r0.val[11])) - ((r0.val[0] * r0.val[5]) * r0.val[11]);
        tmp[15] = ((((((r0.val[4] * r0.val[9]) * r0.val[2]) - ((r0.val[8] * r0.val[5]) * r0.val[2])) + ((r0.val[8] * r0.val[1]) * r0.val[6])) - ((r0.val[0] * r0.val[9]) * r0.val[6])) - ((r0.val[4] * r0.val[1]) * r0.val[10])) + ((r0.val[0] * r0.val[5]) * r0.val[10]);
        r0.val[0] = tmp[0] * inv_det;
        r0.val[4] = tmp[4] * inv_det;
        r0.val[8] = tmp[8] * inv_det;
        r0.val[12] = tmp[12] * inv_det;
        r0.val[1] = tmp[1] * inv_det;
        r0.val[5] = tmp[5] * inv_det;
        r0.val[9] = tmp[9] * inv_det;
        r0.val[13] = tmp[13] * inv_det;
        r0.val[2] = tmp[2] * inv_det;
        r0.val[6] = tmp[6] * inv_det;
        r0.val[10] = tmp[10] * inv_det;
        r0.val[14] = tmp[14] * inv_det;
        r0.val[3] = tmp[3] * inv_det;
        r0.val[7] = tmp[7] * inv_det;
        r0.val[11] = tmp[11] * inv_det;
        r0.val[15] = tmp[15] * inv_det;
        return r0;
    }

    public float det() {
        return (((((((((((((((((((((((((this.val[3] * this.val[6]) * this.val[9]) * this.val[12]) - (((this.val[2] * this.val[7]) * this.val[9]) * this.val[12])) - (((this.val[3] * this.val[5]) * this.val[10]) * this.val[12])) + (((this.val[1] * this.val[7]) * this.val[10]) * this.val[12])) + (((this.val[2] * this.val[5]) * this.val[11]) * this.val[12])) - (((this.val[1] * this.val[6]) * this.val[11]) * this.val[12])) - (((this.val[3] * this.val[6]) * this.val[8]) * this.val[13])) + (((this.val[2] * this.val[7]) * this.val[8]) * this.val[13])) + (((this.val[3] * this.val[4]) * this.val[10]) * this.val[13])) - (((this.val[0] * this.val[7]) * this.val[10]) * this.val[13])) - (((this.val[2] * this.val[4]) * this.val[11]) * this.val[13])) + (((this.val[0] * this.val[6]) * this.val[11]) * this.val[13])) + (((this.val[3] * this.val[5]) * this.val[8]) * this.val[14])) - (((this.val[1] * this.val[7]) * this.val[8]) * this.val[14])) - (((this.val[3] * this.val[4]) * this.val[9]) * this.val[14])) + (((this.val[0] * this.val[7]) * this.val[9]) * this.val[14])) + (((this.val[1] * this.val[4]) * this.val[11]) * this.val[14])) - (((this.val[0] * this.val[5]) * this.val[11]) * this.val[14])) - (((this.val[2] * this.val[5]) * this.val[8]) * this.val[15])) + (((this.val[1] * this.val[6]) * this.val[8]) * this.val[15])) + (((this.val[2] * this.val[4]) * this.val[9]) * this.val[15])) - (((this.val[0] * this.val[6]) * this.val[9]) * this.val[15])) - (((this.val[1] * this.val[4]) * this.val[10]) * this.val[15])) + (((this.val[0] * this.val[5]) * this.val[10]) * this.val[15]);
    }

    public float det3x3() {
        return ((((((this.val[0] * this.val[5]) * this.val[10]) + ((this.val[4] * this.val[9]) * this.val[2])) + ((this.val[8] * this.val[1]) * this.val[6])) - ((this.val[0] * this.val[9]) * this.val[6])) - ((this.val[4] * this.val[1]) * this.val[10])) - ((this.val[8] * this.val[5]) * this.val[2]);
    }

    public Matrix4 setToProjection(float near, float far, float fovy, float aspectRatio) {
        idt();
        float l_fd = (float) (1.0d / Math.tan((((double) fovy) * 0.017453292519943295d) / 2.0d));
        float l_a1 = (far + near) / (near - far);
        float l_a2 = ((2.0f * far) * near) / (near - far);
        this.val[0] = l_fd / aspectRatio;
        this.val[1] = 0.0f;
        this.val[2] = 0.0f;
        this.val[3] = 0.0f;
        this.val[4] = 0.0f;
        this.val[5] = l_fd;
        this.val[6] = 0.0f;
        this.val[7] = 0.0f;
        this.val[8] = 0.0f;
        this.val[9] = 0.0f;
        this.val[10] = l_a1;
        this.val[11] = -1.0f;
        this.val[12] = 0.0f;
        this.val[13] = 0.0f;
        this.val[14] = l_a2;
        this.val[15] = 0.0f;
        return this;
    }

    public Matrix4 setToProjection(float left, float right, float bottom, float top, float near, float far) {
        float y = (near * 2.0f) / (top - bottom);
        float a = (right + left) / (right - left);
        float b = (top + bottom) / (top - bottom);
        float l_a1 = (far + near) / (near - far);
        float l_a2 = ((2.0f * far) * near) / (near - far);
        this.val[0] = (near * 2.0f) / (right - left);
        this.val[1] = 0.0f;
        this.val[2] = 0.0f;
        this.val[3] = 0.0f;
        this.val[4] = 0.0f;
        this.val[5] = y;
        this.val[6] = 0.0f;
        this.val[7] = 0.0f;
        this.val[8] = a;
        this.val[9] = b;
        this.val[10] = l_a1;
        this.val[11] = -1.0f;
        this.val[12] = 0.0f;
        this.val[13] = 0.0f;
        this.val[14] = l_a2;
        this.val[15] = 0.0f;
        return r0;
    }

    public Matrix4 setToOrtho2D(float x, float y, float width, float height) {
        setToOrtho(x, x + width, y, y + height, 0.0f, 1.0f);
        return this;
    }

    public Matrix4 setToOrtho2D(float x, float y, float width, float height, float near, float far) {
        setToOrtho(x, x + width, y, y + height, near, far);
        return this;
    }

    public Matrix4 setToOrtho(float left, float right, float bottom, float top, float near, float far) {
        idt();
        float y_orth = 2.0f / (top - bottom);
        float z_orth = -2.0f / (far - near);
        float tx = (-(right + left)) / (right - left);
        float ty = (-(top + bottom)) / (top - bottom);
        float tz = (-(far + near)) / (far - near);
        this.val[0] = 2.0f / (right - left);
        this.val[1] = 0.0f;
        this.val[2] = 0.0f;
        this.val[3] = 0.0f;
        this.val[4] = 0.0f;
        this.val[5] = y_orth;
        this.val[6] = 0.0f;
        this.val[7] = 0.0f;
        this.val[8] = 0.0f;
        this.val[9] = 0.0f;
        this.val[10] = z_orth;
        this.val[11] = 0.0f;
        this.val[12] = tx;
        this.val[13] = ty;
        this.val[14] = tz;
        this.val[15] = 1.0f;
        return this;
    }

    public Matrix4 setTranslation(Vector3 vector) {
        this.val[12] = vector.f120x;
        this.val[13] = vector.f121y;
        this.val[14] = vector.f122z;
        return this;
    }

    public Matrix4 setTranslation(float x, float y, float z) {
        this.val[12] = x;
        this.val[13] = y;
        this.val[14] = z;
        return this;
    }

    public Matrix4 setToTranslation(Vector3 vector) {
        idt();
        this.val[12] = vector.f120x;
        this.val[13] = vector.f121y;
        this.val[14] = vector.f122z;
        return this;
    }

    public Matrix4 setToTranslation(float x, float y, float z) {
        idt();
        this.val[12] = x;
        this.val[13] = y;
        this.val[14] = z;
        return this;
    }

    public Matrix4 setToTranslationAndScaling(Vector3 translation, Vector3 scaling) {
        idt();
        this.val[12] = translation.f120x;
        this.val[13] = translation.f121y;
        this.val[14] = translation.f122z;
        this.val[0] = scaling.f120x;
        this.val[5] = scaling.f121y;
        this.val[10] = scaling.f122z;
        return this;
    }

    public Matrix4 setToTranslationAndScaling(float translationX, float translationY, float translationZ, float scalingX, float scalingY, float scalingZ) {
        idt();
        this.val[12] = translationX;
        this.val[13] = translationY;
        this.val[14] = translationZ;
        this.val[0] = scalingX;
        this.val[5] = scalingY;
        this.val[10] = scalingZ;
        return this;
    }

    public Matrix4 setToRotation(Vector3 axis, float degrees) {
        if (degrees != 0.0f) {
            return set(quat.set(axis, degrees));
        }
        idt();
        return this;
    }

    public Matrix4 setToRotationRad(Vector3 axis, float radians) {
        if (radians != 0.0f) {
            return set(quat.setFromAxisRad(axis, radians));
        }
        idt();
        return this;
    }

    public Matrix4 setToRotation(float axisX, float axisY, float axisZ, float degrees) {
        if (degrees != 0.0f) {
            return set(quat.setFromAxis(axisX, axisY, axisZ, degrees));
        }
        idt();
        return this;
    }

    public Matrix4 setToRotationRad(float axisX, float axisY, float axisZ, float radians) {
        if (radians != 0.0f) {
            return set(quat.setFromAxisRad(axisX, axisY, axisZ, radians));
        }
        idt();
        return this;
    }

    public Matrix4 setToRotation(Vector3 v1, Vector3 v2) {
        return set(quat.setFromCross(v1, v2));
    }

    public Matrix4 setToRotation(float x1, float y1, float z1, float x2, float y2, float z2) {
        return set(quat.setFromCross(x1, y1, z1, x2, y2, z2));
    }

    public Matrix4 setFromEulerAngles(float yaw, float pitch, float roll) {
        quat.setEulerAngles(yaw, pitch, roll);
        return set(quat);
    }

    public Matrix4 setToScaling(Vector3 vector) {
        idt();
        this.val[0] = vector.f120x;
        this.val[5] = vector.f121y;
        this.val[10] = vector.f122z;
        return this;
    }

    public Matrix4 setToScaling(float x, float y, float z) {
        idt();
        this.val[0] = x;
        this.val[5] = y;
        this.val[10] = z;
        return this;
    }

    public Matrix4 setToLookAt(Vector3 direction, Vector3 up) {
        l_vez.set(direction).nor();
        l_vex.set(direction).nor();
        l_vex.crs(up).nor();
        l_vey.set(l_vex).crs(l_vez).nor();
        idt();
        this.val[0] = l_vex.f120x;
        this.val[4] = l_vex.f121y;
        this.val[8] = l_vex.f122z;
        this.val[1] = l_vey.f120x;
        this.val[5] = l_vey.f121y;
        this.val[9] = l_vey.f122z;
        this.val[2] = -l_vez.f120x;
        this.val[6] = -l_vez.f121y;
        this.val[10] = -l_vez.f122z;
        return this;
    }

    public Matrix4 setToLookAt(Vector3 position, Vector3 target, Vector3 up) {
        tmpVec.set(target).sub(position);
        setToLookAt(tmpVec, up);
        mul(tmpMat.setToTranslation(-position.f120x, -position.f121y, -position.f122z));
        return this;
    }

    public Matrix4 setToWorld(Vector3 position, Vector3 forward, Vector3 up) {
        tmpForward.set(forward).nor();
        right.set(tmpForward).crs(up).nor();
        tmpUp.set(right).crs(tmpForward).nor();
        set(right, tmpUp, tmpForward.scl(-1.0f), position);
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.val[0]);
        stringBuilder.append("|");
        stringBuilder.append(this.val[4]);
        stringBuilder.append("|");
        stringBuilder.append(this.val[8]);
        stringBuilder.append("|");
        stringBuilder.append(this.val[12]);
        stringBuilder.append("]\n");
        stringBuilder.append("[");
        stringBuilder.append(this.val[1]);
        stringBuilder.append("|");
        stringBuilder.append(this.val[5]);
        stringBuilder.append("|");
        stringBuilder.append(this.val[9]);
        stringBuilder.append("|");
        stringBuilder.append(this.val[13]);
        stringBuilder.append("]\n");
        stringBuilder.append("[");
        stringBuilder.append(this.val[2]);
        stringBuilder.append("|");
        stringBuilder.append(this.val[6]);
        stringBuilder.append("|");
        stringBuilder.append(this.val[10]);
        stringBuilder.append("|");
        stringBuilder.append(this.val[14]);
        stringBuilder.append("]\n");
        stringBuilder.append("[");
        stringBuilder.append(this.val[3]);
        stringBuilder.append("|");
        stringBuilder.append(this.val[7]);
        stringBuilder.append("|");
        stringBuilder.append(this.val[11]);
        stringBuilder.append("|");
        stringBuilder.append(this.val[15]);
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }

    public Matrix4 lerp(Matrix4 matrix, float alpha) {
        for (int i = 0; i < 16; i++) {
            this.val[i] = (this.val[i] * (1.0f - alpha)) + (matrix.val[i] * alpha);
        }
        return this;
    }

    public Matrix4 avg(Matrix4 other, float w) {
        getScale(tmpVec);
        other.getScale(tmpForward);
        getRotation(quat);
        other.getRotation(quat2);
        getTranslation(tmpUp);
        other.getTranslation(right);
        setToScaling(tmpVec.scl(w).add(tmpForward.scl(1.0f - w)));
        rotate(quat.slerp(quat2, 1.0f - w));
        setTranslation(tmpUp.scl(w).add(right.scl(1.0f - w)));
        return this;
    }

    public Matrix4 avg(Matrix4[] t) {
        float w = 1.0f / ((float) t.length);
        tmpVec.set(t[0].getScale(tmpUp).scl(w));
        quat.set(t[0].getRotation(quat2).exp(w));
        tmpForward.set(t[0].getTranslation(tmpUp).scl(w));
        for (int i = 1; i < t.length; i++) {
            tmpVec.add(t[i].getScale(tmpUp).scl(w));
            quat.mul(t[i].getRotation(quat2).exp(w));
            tmpForward.add(t[i].getTranslation(tmpUp).scl(w));
        }
        quat.nor();
        setToScaling(tmpVec);
        rotate(quat);
        setTranslation(tmpForward);
        return this;
    }

    public Matrix4 avg(Matrix4[] t, float[] w) {
        tmpVec.set(t[0].getScale(tmpUp).scl(w[0]));
        quat.set(t[0].getRotation(quat2).exp(w[0]));
        tmpForward.set(t[0].getTranslation(tmpUp).scl(w[0]));
        for (int i = 1; i < t.length; i++) {
            tmpVec.add(t[i].getScale(tmpUp).scl(w[i]));
            quat.mul(t[i].getRotation(quat2).exp(w[i]));
            tmpForward.add(t[i].getTranslation(tmpUp).scl(w[i]));
        }
        quat.nor();
        setToScaling(tmpVec);
        rotate(quat);
        setTranslation(tmpForward);
        return this;
    }

    public Matrix4 set(Matrix3 mat) {
        this.val[0] = mat.val[0];
        this.val[1] = mat.val[1];
        this.val[2] = mat.val[2];
        this.val[3] = 0.0f;
        this.val[4] = mat.val[3];
        this.val[5] = mat.val[4];
        this.val[6] = mat.val[5];
        this.val[7] = 0.0f;
        this.val[8] = 0.0f;
        this.val[9] = 0.0f;
        this.val[10] = 1.0f;
        this.val[11] = 0.0f;
        this.val[12] = mat.val[6];
        this.val[13] = mat.val[7];
        this.val[14] = 0.0f;
        this.val[15] = mat.val[8];
        return this;
    }

    public Matrix4 set(Affine2 affine) {
        this.val[0] = affine.m00;
        this.val[1] = affine.m10;
        this.val[2] = 0.0f;
        this.val[3] = 0.0f;
        this.val[4] = affine.m01;
        this.val[5] = affine.m11;
        this.val[6] = 0.0f;
        this.val[7] = 0.0f;
        this.val[8] = 0.0f;
        this.val[9] = 0.0f;
        this.val[10] = 1.0f;
        this.val[11] = 0.0f;
        this.val[12] = affine.m02;
        this.val[13] = affine.m12;
        this.val[14] = 0.0f;
        this.val[15] = 1.0f;
        return this;
    }

    public Matrix4 setAsAffine(Affine2 affine) {
        this.val[0] = affine.m00;
        this.val[1] = affine.m10;
        this.val[4] = affine.m01;
        this.val[5] = affine.m11;
        this.val[12] = affine.m02;
        this.val[13] = affine.m12;
        return this;
    }

    public Matrix4 setAsAffine(Matrix4 mat) {
        this.val[0] = mat.val[0];
        this.val[1] = mat.val[1];
        this.val[4] = mat.val[4];
        this.val[5] = mat.val[5];
        this.val[12] = mat.val[12];
        this.val[13] = mat.val[13];
        return this;
    }

    public Matrix4 scl(Vector3 scale) {
        float[] fArr = this.val;
        fArr[0] = fArr[0] * scale.f120x;
        fArr = this.val;
        fArr[5] = fArr[5] * scale.f121y;
        fArr = this.val;
        fArr[10] = fArr[10] * scale.f122z;
        return this;
    }

    public Matrix4 scl(float x, float y, float z) {
        float[] fArr = this.val;
        fArr[0] = fArr[0] * x;
        fArr = this.val;
        fArr[5] = fArr[5] * y;
        fArr = this.val;
        fArr[10] = fArr[10] * z;
        return this;
    }

    public Matrix4 scl(float scale) {
        float[] fArr = this.val;
        fArr[0] = fArr[0] * scale;
        fArr = this.val;
        fArr[5] = fArr[5] * scale;
        fArr = this.val;
        fArr[10] = fArr[10] * scale;
        return this;
    }

    public Vector3 getTranslation(Vector3 position) {
        position.f120x = this.val[12];
        position.f121y = this.val[13];
        position.f122z = this.val[14];
        return position;
    }

    public Quaternion getRotation(Quaternion rotation, boolean normalizeAxes) {
        return rotation.setFromMatrix(normalizeAxes, this);
    }

    public Quaternion getRotation(Quaternion rotation) {
        return rotation.setFromMatrix(this);
    }

    public float getScaleXSquared() {
        return ((this.val[0] * this.val[0]) + (this.val[4] * this.val[4])) + (this.val[8] * this.val[8]);
    }

    public float getScaleYSquared() {
        return ((this.val[1] * this.val[1]) + (this.val[5] * this.val[5])) + (this.val[9] * this.val[9]);
    }

    public float getScaleZSquared() {
        return ((this.val[2] * this.val[2]) + (this.val[6] * this.val[6])) + (this.val[10] * this.val[10]);
    }

    public float getScaleX() {
        return (MathUtils.isZero(this.val[4]) && MathUtils.isZero(this.val[8])) ? Math.abs(this.val[0]) : (float) Math.sqrt((double) getScaleXSquared());
    }

    public float getScaleY() {
        return (MathUtils.isZero(this.val[1]) && MathUtils.isZero(this.val[9])) ? Math.abs(this.val[5]) : (float) Math.sqrt((double) getScaleYSquared());
    }

    public float getScaleZ() {
        return (MathUtils.isZero(this.val[2]) && MathUtils.isZero(this.val[6])) ? Math.abs(this.val[10]) : (float) Math.sqrt((double) getScaleZSquared());
    }

    public Vector3 getScale(Vector3 scale) {
        return scale.set(getScaleX(), getScaleY(), getScaleZ());
    }

    public Matrix4 toNormalMatrix() {
        this.val[12] = 0.0f;
        this.val[13] = 0.0f;
        this.val[14] = 0.0f;
        return inv().tra();
    }

    public Matrix4 translate(Vector3 translation) {
        return translate(translation.f120x, translation.f121y, translation.f122z);
    }

    public Matrix4 translate(float x, float y, float z) {
        tmp[0] = 1.0f;
        tmp[4] = 0.0f;
        tmp[8] = 0.0f;
        tmp[12] = x;
        tmp[1] = 0.0f;
        tmp[5] = 1.0f;
        tmp[9] = 0.0f;
        tmp[13] = y;
        tmp[2] = 0.0f;
        tmp[6] = 0.0f;
        tmp[10] = 1.0f;
        tmp[14] = z;
        tmp[3] = 0.0f;
        tmp[7] = 0.0f;
        tmp[11] = 0.0f;
        tmp[15] = 1.0f;
        mul(this.val, tmp);
        return this;
    }

    public Matrix4 rotate(Vector3 axis, float degrees) {
        if (degrees == 0.0f) {
            return this;
        }
        quat.set(axis, degrees);
        return rotate(quat);
    }

    public Matrix4 rotateRad(Vector3 axis, float radians) {
        if (radians == 0.0f) {
            return this;
        }
        quat.setFromAxisRad(axis, radians);
        return rotate(quat);
    }

    public Matrix4 rotate(float axisX, float axisY, float axisZ, float degrees) {
        if (degrees == 0.0f) {
            return this;
        }
        quat.setFromAxis(axisX, axisY, axisZ, degrees);
        return rotate(quat);
    }

    public Matrix4 rotateRad(float axisX, float axisY, float axisZ, float radians) {
        if (radians == 0.0f) {
            return this;
        }
        quat.setFromAxisRad(axisX, axisY, axisZ, radians);
        return rotate(quat);
    }

    public Matrix4 rotate(Quaternion rotation) {
        rotation.toMatrix(tmp);
        mul(this.val, tmp);
        return this;
    }

    public Matrix4 rotate(Vector3 v1, Vector3 v2) {
        return rotate(quat.setFromCross(v1, v2));
    }

    public Matrix4 scale(float scaleX, float scaleY, float scaleZ) {
        tmp[0] = scaleX;
        tmp[4] = 0.0f;
        tmp[8] = 0.0f;
        tmp[12] = 0.0f;
        tmp[1] = 0.0f;
        tmp[5] = scaleY;
        tmp[9] = 0.0f;
        tmp[13] = 0.0f;
        tmp[2] = 0.0f;
        tmp[6] = 0.0f;
        tmp[10] = scaleZ;
        tmp[14] = 0.0f;
        tmp[3] = 0.0f;
        tmp[7] = 0.0f;
        tmp[11] = 0.0f;
        tmp[15] = 1.0f;
        mul(this.val, tmp);
        return this;
    }

    public void extract4x3Matrix(float[] dst) {
        dst[0] = this.val[0];
        dst[1] = this.val[1];
        dst[2] = this.val[2];
        dst[3] = this.val[4];
        dst[4] = this.val[5];
        dst[5] = this.val[6];
        dst[6] = this.val[8];
        dst[7] = this.val[9];
        dst[8] = this.val[10];
        dst[9] = this.val[12];
        dst[10] = this.val[13];
        dst[11] = this.val[14];
    }
}
