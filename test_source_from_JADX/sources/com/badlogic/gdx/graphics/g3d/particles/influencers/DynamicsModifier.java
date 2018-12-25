package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray.FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class DynamicsModifier extends Influencer {
    protected static final Quaternion TMP_Q = new Quaternion();
    protected static final Vector3 TMP_V1 = new Vector3();
    protected static final Vector3 TMP_V2 = new Vector3();
    protected static final Vector3 TMP_V3 = new Vector3();
    public boolean isGlobal = false;
    protected FloatChannel lifeChannel;

    public static class FaceDirection extends DynamicsModifier {
        FloatChannel accellerationChannel;
        FloatChannel rotationChannel;

        public FaceDirection(FaceDirection rotation) {
            super(rotation);
        }

        public void allocateChannels() {
            this.rotationChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Rotation3D);
            this.accellerationChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Acceleration);
        }

        public void update() {
            int i = 0;
            int accelOffset = 0;
            int c = (this.controller.particles.size * this.rotationChannel.strideSize) + 0;
            while (i < c) {
                Vector3 axisZ = TMP_V1.set(r0.accellerationChannel.data[accelOffset + 0], r0.accellerationChannel.data[accelOffset + 1], r0.accellerationChannel.data[accelOffset + 2]).nor();
                Vector3 axisY = TMP_V2.set(TMP_V1).crs(Vector3.f118Y).nor().crs(TMP_V1).nor();
                Vector3 axisX = TMP_V3.set(axisY).crs(axisZ).nor();
                int c2 = c;
                TMP_Q.setFromAxes(false, axisX.f120x, axisY.f120x, axisZ.f120x, axisX.f121y, axisY.f121y, axisZ.f121y, axisX.f122z, axisY.f122z, axisZ.f122z);
                r0.rotationChannel.data[i + 0] = TMP_Q.f86x;
                r0.rotationChannel.data[i + 1] = TMP_Q.f87y;
                r0.rotationChannel.data[i + 2] = TMP_Q.f88z;
                r0.rotationChannel.data[i + 3] = TMP_Q.f85w;
                i += r0.rotationChannel.strideSize;
                accelOffset += r0.accellerationChannel.strideSize;
                c = c2;
            }
        }

        public ParticleControllerComponent copy() {
            return new FaceDirection(this);
        }
    }

    public static abstract class Strength extends DynamicsModifier {
        protected FloatChannel strengthChannel;
        public ScaledNumericValue strengthValue = new ScaledNumericValue();

        public Strength(Strength rotation) {
            super(rotation);
            this.strengthValue.load(rotation.strengthValue);
        }

        public void allocateChannels() {
            super.allocateChannels();
            ParticleChannels.Interpolation.id = this.controller.particleChannels.newId();
            this.strengthChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Interpolation);
        }

        public void activateParticles(int startIndex, int count) {
            int i = this.strengthChannel.strideSize * startIndex;
            int c = (this.strengthChannel.strideSize * count) + i;
            while (i < c) {
                float start = this.strengthValue.newLowValue();
                float diff = this.strengthValue.newHighValue();
                if (!this.strengthValue.isRelative()) {
                    diff -= start;
                }
                this.strengthChannel.data[i + 0] = start;
                this.strengthChannel.data[i + 1] = diff;
                i += this.strengthChannel.strideSize;
            }
        }

        public void write(Json json) {
            super.write(json);
            json.writeValue("strengthValue", this.strengthValue);
        }

        public void read(Json json, JsonValue jsonData) {
            super.read(json, jsonData);
            this.strengthValue = (ScaledNumericValue) json.readValue("strengthValue", ScaledNumericValue.class, jsonData);
        }
    }

    public static abstract class Angular extends Strength {
        protected FloatChannel angularChannel;
        public ScaledNumericValue phiValue = new ScaledNumericValue();
        public ScaledNumericValue thetaValue = new ScaledNumericValue();

        public Angular(Angular value) {
            super(value);
            this.thetaValue.load(value.thetaValue);
            this.phiValue.load(value.phiValue);
        }

        public void allocateChannels() {
            super.allocateChannels();
            ParticleChannels.Interpolation4.id = this.controller.particleChannels.newId();
            this.angularChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Interpolation4);
        }

        public void activateParticles(int startIndex, int count) {
            super.activateParticles(startIndex, count);
            int i = this.angularChannel.strideSize * startIndex;
            int c = (this.angularChannel.strideSize * count) + i;
            while (i < c) {
                float start = this.thetaValue.newLowValue();
                float diff = this.thetaValue.newHighValue();
                if (!this.thetaValue.isRelative()) {
                    diff -= start;
                }
                this.angularChannel.data[i + 0] = start;
                this.angularChannel.data[i + 1] = diff;
                start = this.phiValue.newLowValue();
                diff = this.phiValue.newHighValue();
                if (!this.phiValue.isRelative()) {
                    diff -= start;
                }
                this.angularChannel.data[i + 2] = start;
                this.angularChannel.data[i + 3] = diff;
                i += this.angularChannel.strideSize;
            }
        }

        public void write(Json json) {
            super.write(json);
            json.writeValue("thetaValue", this.thetaValue);
            json.writeValue("phiValue", this.phiValue);
        }

        public void read(Json json, JsonValue jsonData) {
            super.read(json, jsonData);
            this.thetaValue = (ScaledNumericValue) json.readValue("thetaValue", ScaledNumericValue.class, jsonData);
            this.phiValue = (ScaledNumericValue) json.readValue("phiValue", ScaledNumericValue.class, jsonData);
        }
    }

    public static class BrownianAcceleration extends Strength {
        FloatChannel accelerationChannel;

        public BrownianAcceleration(BrownianAcceleration rotation) {
            super(rotation);
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.accelerationChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Acceleration);
        }

        public void update() {
            int lifeOffset = 2;
            int strengthOffset = 0;
            int forceOffset = 0;
            int i = 0;
            int c = this.controller.particles.size;
            while (i < c) {
                TMP_V3.set(MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f)).nor().scl(this.strengthChannel.data[strengthOffset + 0] + (this.strengthChannel.data[strengthOffset + 1] * this.strengthValue.getScale(this.lifeChannel.data[lifeOffset])));
                float[] fArr = this.accelerationChannel.data;
                int i2 = forceOffset + 0;
                fArr[i2] = fArr[i2] + TMP_V3.f120x;
                fArr = this.accelerationChannel.data;
                i2 = forceOffset + 1;
                fArr[i2] = fArr[i2] + TMP_V3.f121y;
                fArr = this.accelerationChannel.data;
                i2 = forceOffset + 2;
                fArr[i2] = fArr[i2] + TMP_V3.f122z;
                i++;
                strengthOffset += this.strengthChannel.strideSize;
                forceOffset += this.accelerationChannel.strideSize;
                lifeOffset += this.lifeChannel.strideSize;
            }
        }

        public BrownianAcceleration copy() {
            return new BrownianAcceleration(this);
        }
    }

    public static class CentripetalAcceleration extends Strength {
        FloatChannel accelerationChannel;
        FloatChannel positionChannel;

        public CentripetalAcceleration(CentripetalAcceleration rotation) {
            super(rotation);
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.accelerationChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Acceleration);
            this.positionChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Position);
        }

        public void update() {
            float cx = 0.0f;
            float cy = 0.0f;
            float cz = 0.0f;
            if (!this.isGlobal) {
                float[] val = this.controller.transform.val;
                cx = val[12];
                cy = val[13];
                cz = val[14];
            }
            int lifeOffset = 2;
            int strengthOffset = 0;
            int positionOffset = 0;
            int forceOffset = 0;
            int i = 0;
            int c = this.controller.particles.size;
            while (i < c) {
                TMP_V3.set(this.positionChannel.data[positionOffset + 0] - cx, this.positionChannel.data[positionOffset + 1] - cy, this.positionChannel.data[positionOffset + 2] - cz).nor().scl(this.strengthChannel.data[strengthOffset + 0] + (this.strengthChannel.data[strengthOffset + 1] * this.strengthValue.getScale(this.lifeChannel.data[lifeOffset])));
                float[] fArr = this.accelerationChannel.data;
                int i2 = forceOffset + 0;
                fArr[i2] = fArr[i2] + TMP_V3.f120x;
                fArr = this.accelerationChannel.data;
                i2 = forceOffset + 1;
                fArr[i2] = fArr[i2] + TMP_V3.f121y;
                fArr = this.accelerationChannel.data;
                i2 = forceOffset + 2;
                fArr[i2] = fArr[i2] + TMP_V3.f122z;
                i++;
                positionOffset += this.positionChannel.strideSize;
                strengthOffset += this.strengthChannel.strideSize;
                forceOffset += this.accelerationChannel.strideSize;
                lifeOffset += this.lifeChannel.strideSize;
            }
        }

        public CentripetalAcceleration copy() {
            return new CentripetalAcceleration(this);
        }
    }

    public static class Rotational2D extends Strength {
        FloatChannel rotationalVelocity2dChannel;

        public Rotational2D(Rotational2D rotation) {
            super(rotation);
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.rotationalVelocity2dChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.AngularVelocity2D);
        }

        public void update() {
            int i = 0;
            int l = 2;
            int s = 0;
            int c = (this.controller.particles.size * this.rotationalVelocity2dChannel.strideSize) + 0;
            while (i < c) {
                float[] fArr = this.rotationalVelocity2dChannel.data;
                fArr[i] = fArr[i] + (this.strengthChannel.data[s + 0] + (this.strengthChannel.data[s + 1] * this.strengthValue.getScale(this.lifeChannel.data[l])));
                s += this.strengthChannel.strideSize;
                i += this.rotationalVelocity2dChannel.strideSize;
                l += this.lifeChannel.strideSize;
            }
        }

        public Rotational2D copy() {
            return new Rotational2D(this);
        }
    }

    public static class PolarAcceleration extends Angular {
        FloatChannel directionalVelocityChannel;

        public PolarAcceleration(PolarAcceleration rotation) {
            super(rotation);
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.directionalVelocityChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Acceleration);
        }

        public void update() {
            int i = 0;
            int l = 2;
            int s = 0;
            int a = 0;
            int c = (this.controller.particles.size * this.directionalVelocityChannel.strideSize) + 0;
            while (i < c) {
                float lifePercent = r0.lifeChannel.data[l];
                float strength = r0.strengthChannel.data[s + 0] + (r0.strengthChannel.data[s + 1] * r0.strengthValue.getScale(lifePercent));
                float phi = r0.angularChannel.data[a + 2] + (r0.angularChannel.data[a + 3] * r0.phiValue.getScale(lifePercent));
                float theta = r0.angularChannel.data[a + 0] + (r0.angularChannel.data[a + 1] * r0.thetaValue.getScale(lifePercent));
                float cosTheta = MathUtils.cosDeg(theta);
                float sinTheta = MathUtils.sinDeg(theta);
                float cosPhi = MathUtils.cosDeg(phi);
                float sinPhi = MathUtils.sinDeg(phi);
                int c2 = c;
                TMP_V3.set(cosTheta * sinPhi, cosPhi, sinTheta * sinPhi).nor().scl(strength);
                float[] fArr = r0.directionalVelocityChannel.data;
                int i2 = i + 0;
                fArr[i2] = fArr[i2] + TMP_V3.f120x;
                fArr = r0.directionalVelocityChannel.data;
                int i3 = i + 1;
                fArr[i3] = fArr[i3] + TMP_V3.f121y;
                fArr = r0.directionalVelocityChannel.data;
                i3 = i + 2;
                fArr[i3] = fArr[i3] + TMP_V3.f122z;
                s += r0.strengthChannel.strideSize;
                i += r0.directionalVelocityChannel.strideSize;
                a += r0.angularChannel.strideSize;
                l += r0.lifeChannel.strideSize;
                c = c2;
            }
        }

        public PolarAcceleration copy() {
            return new PolarAcceleration(this);
        }
    }

    public static class Rotational3D extends Angular {
        FloatChannel rotationChannel;
        FloatChannel rotationalForceChannel;

        public Rotational3D(Rotational3D rotation) {
            super(rotation);
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.rotationChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Rotation3D);
            this.rotationalForceChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.AngularVelocity3D);
        }

        public void update() {
            int i = 0;
            int l = 2;
            int s = 0;
            int a = 0;
            int c = this.controller.particles.size * this.rotationalForceChannel.strideSize;
            while (i < c) {
                float lifePercent = r0.lifeChannel.data[l];
                float strength = r0.strengthChannel.data[s + 0] + (r0.strengthChannel.data[s + 1] * r0.strengthValue.getScale(lifePercent));
                float phi = r0.angularChannel.data[a + 2] + (r0.angularChannel.data[a + 3] * r0.phiValue.getScale(lifePercent));
                float theta = r0.angularChannel.data[a + 0] + (r0.angularChannel.data[a + 1] * r0.thetaValue.getScale(lifePercent));
                float cosTheta = MathUtils.cosDeg(theta);
                float sinTheta = MathUtils.sinDeg(theta);
                float cosPhi = MathUtils.cosDeg(phi);
                float sinPhi = MathUtils.sinDeg(phi);
                int c2 = c;
                TMP_V3.set(cosTheta * sinPhi, cosPhi, sinTheta * sinPhi);
                TMP_V3.scl(0.017453292f * strength);
                float[] fArr = r0.rotationalForceChannel.data;
                int i2 = i + 0;
                fArr[i2] = fArr[i2] + TMP_V3.f120x;
                fArr = r0.rotationalForceChannel.data;
                int i3 = i + 1;
                fArr[i3] = fArr[i3] + TMP_V3.f121y;
                fArr = r0.rotationalForceChannel.data;
                i3 = i + 2;
                fArr[i3] = fArr[i3] + TMP_V3.f122z;
                s += r0.strengthChannel.strideSize;
                i += r0.rotationalForceChannel.strideSize;
                a += r0.angularChannel.strideSize;
                l += r0.lifeChannel.strideSize;
                c = c2;
            }
        }

        public Rotational3D copy() {
            return new Rotational3D(this);
        }
    }

    public static class TangentialAcceleration extends Angular {
        FloatChannel directionalVelocityChannel;
        FloatChannel positionChannel;

        public TangentialAcceleration(TangentialAcceleration rotation) {
            super(rotation);
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.directionalVelocityChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Acceleration);
            this.positionChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Position);
        }

        public void update() {
            int i = 0;
            int l = 2;
            int s = 0;
            int a = 0;
            int positionOffset = 0;
            int c = (this.controller.particles.size * this.directionalVelocityChannel.strideSize) + 0;
            while (i < c) {
                float lifePercent = r0.lifeChannel.data[l];
                float strength = r0.strengthChannel.data[s + 0] + (r0.strengthChannel.data[s + 1] * r0.strengthValue.getScale(lifePercent));
                float phi = r0.angularChannel.data[a + 2] + (r0.angularChannel.data[a + 3] * r0.phiValue.getScale(lifePercent));
                float theta = r0.angularChannel.data[a + 0] + (r0.angularChannel.data[a + 1] * r0.thetaValue.getScale(lifePercent));
                float cosTheta = MathUtils.cosDeg(theta);
                float sinTheta = MathUtils.sinDeg(theta);
                float cosPhi = MathUtils.cosDeg(phi);
                float sinPhi = MathUtils.sinDeg(phi);
                int c2 = c;
                TMP_V3.set(cosTheta * sinPhi, cosPhi, sinTheta * sinPhi).crs(r0.positionChannel.data[positionOffset + 0], r0.positionChannel.data[positionOffset + 1], r0.positionChannel.data[positionOffset + 2]).nor().scl(strength);
                float[] fArr = r0.directionalVelocityChannel.data;
                int i2 = i + 0;
                fArr[i2] = fArr[i2] + TMP_V3.f120x;
                fArr = r0.directionalVelocityChannel.data;
                i2 = i + 1;
                fArr[i2] = fArr[i2] + TMP_V3.f121y;
                fArr = r0.directionalVelocityChannel.data;
                i2 = i + 2;
                fArr[i2] = fArr[i2] + TMP_V3.f122z;
                s += r0.strengthChannel.strideSize;
                i += r0.directionalVelocityChannel.strideSize;
                a += r0.angularChannel.strideSize;
                l += r0.lifeChannel.strideSize;
                positionOffset += r0.positionChannel.strideSize;
                c = c2;
            }
        }

        public TangentialAcceleration copy() {
            return new TangentialAcceleration(this);
        }
    }

    public DynamicsModifier(DynamicsModifier modifier) {
        this.isGlobal = modifier.isGlobal;
    }

    public void allocateChannels() {
        this.lifeChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Life);
    }

    public void write(Json json) {
        super.write(json);
        json.writeValue("isGlobal", Boolean.valueOf(this.isGlobal));
    }

    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        this.isGlobal = ((Boolean) json.readValue("isGlobal", Boolean.TYPE, jsonData)).booleanValue();
    }
}
