package com.badlogic.gdx.physics.box2d.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

public class MotorJoint extends Joint {
    private final Vector2 linearOffset = new Vector2();
    private final float[] tmp = new float[2];

    private native float jniGetAngularOffset(long j);

    private native float jniGetCorrectionFactor(long j);

    private native void jniGetLinearOffset(long j, float[] fArr);

    private native float jniGetMaxForce(long j);

    private native float jniGetMaxTorque(long j);

    private native void jniSetAngularOffset(long j, float f);

    private native void jniSetCorrectionFactor(long j, float f);

    private native void jniSetLinearOffset(long j, float f, float f2);

    private native void jniSetMaxForce(long j, float f);

    private native void jniSetMaxTorque(long j, float f);

    public MotorJoint(World world, long addr) {
        super(world, addr);
    }

    public Vector2 getLinearOffset() {
        jniGetLinearOffset(this.addr, this.tmp);
        this.linearOffset.set(this.tmp[0], this.tmp[1]);
        return this.linearOffset;
    }

    public void setLinearOffset(Vector2 linearOffset) {
        jniSetLinearOffset(this.addr, linearOffset.f16x, linearOffset.f17y);
    }

    public float getAngularOffset() {
        return jniGetAngularOffset(this.addr);
    }

    public void setAngularOffset(float angularOffset) {
        jniSetAngularOffset(this.addr, angularOffset);
    }

    public float getMaxForce() {
        return jniGetMaxForce(this.addr);
    }

    public void setMaxForce(float maxForce) {
        jniSetMaxForce(this.addr, maxForce);
    }

    public float getMaxTorque() {
        return jniGetMaxTorque(this.addr);
    }

    public void setMaxTorque(float maxTorque) {
        jniSetMaxTorque(this.addr, maxTorque);
    }

    public float getCorrectionFactor() {
        return jniGetCorrectionFactor(this.addr);
    }

    public void setCorrectionFactor(float correctionFactor) {
        jniSetCorrectionFactor(this.addr, correctionFactor);
    }
}
