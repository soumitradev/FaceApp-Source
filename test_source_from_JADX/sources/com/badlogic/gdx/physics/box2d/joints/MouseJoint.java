package com.badlogic.gdx.physics.box2d.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

public class MouseJoint extends Joint {
    private final Vector2 target = new Vector2();
    final float[] tmp = new float[2];

    private native float jniGetDampingRatio(long j);

    private native float jniGetFrequency(long j);

    private native float jniGetMaxForce(long j);

    private native void jniGetTarget(long j, float[] fArr);

    private native void jniSetDampingRatio(long j, float f);

    private native void jniSetFrequency(long j, float f);

    private native void jniSetMaxForce(long j, float f);

    private native void jniSetTarget(long j, float f, float f2);

    public MouseJoint(World world, long addr) {
        super(world, addr);
    }

    public void setTarget(Vector2 target) {
        jniSetTarget(this.addr, target.f16x, target.f17y);
    }

    public Vector2 getTarget() {
        jniGetTarget(this.addr, this.tmp);
        this.target.f16x = this.tmp[0];
        this.target.f17y = this.tmp[1];
        return this.target;
    }

    public void setMaxForce(float force) {
        jniSetMaxForce(this.addr, force);
    }

    public float getMaxForce() {
        return jniGetMaxForce(this.addr);
    }

    public void setFrequency(float hz) {
        jniSetFrequency(this.addr, hz);
    }

    public float getFrequency() {
        return jniGetFrequency(this.addr);
    }

    public void setDampingRatio(float ratio) {
        jniSetDampingRatio(this.addr, ratio);
    }

    public float getDampingRatio() {
        return jniGetDampingRatio(this.addr);
    }
}
