package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray.FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.Arrays;

public class DynamicsInfluencer extends Influencer {
    private FloatChannel accellerationChannel;
    private FloatChannel angularVelocityChannel;
    boolean has2dAngularVelocity;
    boolean has3dAngularVelocity;
    boolean hasAcceleration;
    private FloatChannel positionChannel;
    private FloatChannel previousPositionChannel;
    private FloatChannel rotationChannel;
    public Array<DynamicsModifier> velocities;

    public DynamicsInfluencer() {
        this.velocities = new Array(true, 3, DynamicsModifier.class);
    }

    public DynamicsInfluencer(DynamicsModifier... velocities) {
        this.velocities = new Array(true, velocities.length, DynamicsModifier.class);
        for (DynamicsModifier value : velocities) {
            this.velocities.add((DynamicsModifier) value.copy());
        }
    }

    public DynamicsInfluencer(DynamicsInfluencer velocityInfluencer) {
        this((DynamicsModifier[]) velocityInfluencer.velocities.toArray(DynamicsModifier.class));
    }

    public void allocateChannels() {
        boolean z = false;
        for (int k = 0; k < this.velocities.size; k++) {
            ((DynamicsModifier[]) this.velocities.items)[k].allocateChannels();
        }
        this.accellerationChannel = (FloatChannel) this.controller.particles.getChannel(ParticleChannels.Acceleration);
        this.hasAcceleration = this.accellerationChannel != null;
        if (this.hasAcceleration) {
            this.positionChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Position);
            this.previousPositionChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.PreviousPosition);
        }
        this.angularVelocityChannel = (FloatChannel) this.controller.particles.getChannel(ParticleChannels.AngularVelocity2D);
        this.has2dAngularVelocity = this.angularVelocityChannel != null;
        if (this.has2dAngularVelocity) {
            this.rotationChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Rotation2D);
            this.has3dAngularVelocity = false;
            return;
        }
        this.angularVelocityChannel = (FloatChannel) this.controller.particles.getChannel(ParticleChannels.AngularVelocity3D);
        if (this.angularVelocityChannel != null) {
            z = true;
        }
        this.has3dAngularVelocity = z;
        if (this.has3dAngularVelocity) {
            this.rotationChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Rotation3D);
        }
    }

    public void set(ParticleController particleController) {
        super.set(particleController);
        for (int k = 0; k < this.velocities.size; k++) {
            ((DynamicsModifier[]) this.velocities.items)[k].set(particleController);
        }
    }

    public void init() {
        for (int k = 0; k < this.velocities.size; k++) {
            ((DynamicsModifier[]) this.velocities.items)[k].init();
        }
    }

    public void activateParticles(int startIndex, int count) {
        int i;
        if (this.hasAcceleration) {
            i = this.positionChannel.strideSize * startIndex;
            int c = (this.positionChannel.strideSize * count) + i;
            while (i < c) {
                this.previousPositionChannel.data[i + 0] = this.positionChannel.data[i + 0];
                this.previousPositionChannel.data[i + 1] = this.positionChannel.data[i + 1];
                this.previousPositionChannel.data[i + 2] = this.positionChannel.data[i + 2];
                i += this.positionChannel.strideSize;
            }
        }
        int c2;
        if (this.has2dAngularVelocity) {
            i = this.rotationChannel.strideSize * startIndex;
            c2 = (this.rotationChannel.strideSize * count) + i;
            while (i < c2) {
                this.rotationChannel.data[i + 0] = 1.0f;
                this.rotationChannel.data[i + 1] = 0.0f;
                i += this.rotationChannel.strideSize;
            }
        } else if (this.has3dAngularVelocity) {
            i = this.rotationChannel.strideSize * startIndex;
            c2 = (this.rotationChannel.strideSize * count) + i;
            while (i < c2) {
                this.rotationChannel.data[i + 0] = 0.0f;
                this.rotationChannel.data[i + 1] = 0.0f;
                this.rotationChannel.data[i + 2] = 0.0f;
                this.rotationChannel.data[i + 3] = 1.0f;
                i += this.rotationChannel.strideSize;
            }
        }
        for (i = 0; i < this.velocities.size; i++) {
            ((DynamicsModifier[]) this.velocities.items)[i].activateParticles(startIndex, count);
        }
    }

    public void update() {
        int k;
        int i;
        int angularOffset = 0;
        if (this.hasAcceleration) {
            Arrays.fill(this.accellerationChannel.data, 0, this.controller.particles.size * this.accellerationChannel.strideSize, 0.0f);
        }
        if (this.has2dAngularVelocity || this.has3dAngularVelocity) {
            Arrays.fill(this.angularVelocityChannel.data, 0, this.controller.particles.size * this.angularVelocityChannel.strideSize, 0.0f);
        }
        for (k = 0; k < this.velocities.size; k++) {
            ((DynamicsModifier[]) this.velocities.items)[k].update();
        }
        if (this.hasAcceleration) {
            i = 0;
            k = 0;
            while (i < this.controller.particles.size) {
                float x = this.positionChannel.data[k + 0];
                float y = this.positionChannel.data[k + 1];
                float z = this.positionChannel.data[k + 2];
                this.positionChannel.data[k + 0] = ((x * 2.0f) - this.previousPositionChannel.data[k + 0]) + (this.accellerationChannel.data[k + 0] * this.controller.deltaTimeSqr);
                this.positionChannel.data[k + 1] = ((y * 2.0f) - this.previousPositionChannel.data[k + 1]) + (this.accellerationChannel.data[k + 1] * this.controller.deltaTimeSqr);
                this.positionChannel.data[k + 2] = ((2.0f * z) - this.previousPositionChannel.data[k + 2]) + (this.accellerationChannel.data[k + 2] * this.controller.deltaTimeSqr);
                this.previousPositionChannel.data[k + 0] = x;
                this.previousPositionChannel.data[k + 1] = y;
                this.previousPositionChannel.data[k + 2] = z;
                i++;
                k += this.positionChannel.strideSize;
            }
        }
        float currentSine;
        float newSine;
        if (this.has2dAngularVelocity) {
            k = 0;
            while (k < this.controller.particles.size) {
                float rotation = this.angularVelocityChannel.data[k] * this.controller.deltaTime;
                if (rotation != 0.0f) {
                    x = MathUtils.cosDeg(rotation);
                    y = MathUtils.sinDeg(rotation);
                    z = this.rotationChannel.data[angularOffset + 0];
                    currentSine = this.rotationChannel.data[angularOffset + 1];
                    newSine = (currentSine * x) + (z * y);
                    this.rotationChannel.data[angularOffset + 0] = (z * x) - (currentSine * y);
                    this.rotationChannel.data[angularOffset + 1] = newSine;
                }
                k++;
                angularOffset += this.rotationChannel.strideSize;
            }
        } else if (this.has3dAngularVelocity) {
            k = 0;
            i = 0;
            while (k < this.controller.particles.size) {
                x = this.angularVelocityChannel.data[angularOffset + 0];
                y = this.angularVelocityChannel.data[angularOffset + 1];
                z = this.angularVelocityChannel.data[angularOffset + 2];
                currentSine = this.rotationChannel.data[i + 0];
                float qy = this.rotationChannel.data[i + 1];
                newSine = this.rotationChannel.data[i + 2];
                float qw = this.rotationChannel.data[i + 3];
                TMP_Q.set(x, y, z, 0.0f).mul(currentSine, qy, newSine, qw).mul(this.controller.deltaTime * 0.5f).add(currentSine, qy, newSine, qw).nor();
                this.rotationChannel.data[i + 0] = TMP_Q.f86x;
                this.rotationChannel.data[i + 1] = TMP_Q.f87y;
                this.rotationChannel.data[i + 2] = TMP_Q.f88z;
                this.rotationChannel.data[i + 3] = TMP_Q.f85w;
                k++;
                i += this.rotationChannel.strideSize;
                angularOffset += this.angularVelocityChannel.strideSize;
            }
        }
    }

    public DynamicsInfluencer copy() {
        return new DynamicsInfluencer(this);
    }

    public void write(Json json) {
        json.writeValue("velocities", this.velocities, Array.class, DynamicsModifier.class);
    }

    public void read(Json json, JsonValue jsonData) {
        this.velocities.addAll((Array) json.readValue("velocities", Array.class, DynamicsModifier.class, jsonData));
    }
}
