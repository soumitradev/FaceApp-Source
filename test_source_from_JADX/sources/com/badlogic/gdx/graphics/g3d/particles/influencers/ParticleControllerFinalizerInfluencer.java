package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray.FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray.ObjectChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ParticleControllerFinalizerInfluencer extends Influencer {
    ObjectChannel<ParticleController> controllerChannel;
    boolean hasRotation;
    boolean hasScale;
    FloatChannel positionChannel;
    FloatChannel rotationChannel;
    FloatChannel scaleChannel;

    public void init() {
        this.controllerChannel = (ObjectChannel) this.controller.particles.getChannel(ParticleChannels.ParticleController);
        if (this.controllerChannel == null) {
            throw new GdxRuntimeException("ParticleController channel not found, specify an influencer which will allocate it please.");
        }
        this.scaleChannel = (FloatChannel) this.controller.particles.getChannel(ParticleChannels.Scale);
        this.rotationChannel = (FloatChannel) this.controller.particles.getChannel(ParticleChannels.Rotation3D);
        boolean z = false;
        this.hasScale = this.scaleChannel != null;
        if (this.rotationChannel != null) {
            z = true;
        }
        this.hasRotation = z;
    }

    public void allocateChannels() {
        this.positionChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Position);
    }

    public void update() {
        int i = 0;
        int positionOffset = 0;
        int c = this.controller.particles.size;
        while (i < c) {
            ParticleController particleController = ((ParticleController[]) r0.controllerChannel.data)[i];
            float scale = r0.hasScale ? r0.scaleChannel.data[i] : 1.0f;
            float qx = 0.0f;
            float qy = 0.0f;
            float qz = 0.0f;
            float qw = 1.0f;
            if (r0.hasRotation) {
                int rotationOffset = r0.rotationChannel.strideSize * i;
                qx = r0.rotationChannel.data[rotationOffset + 0];
                qy = r0.rotationChannel.data[rotationOffset + 1];
                qz = r0.rotationChannel.data[rotationOffset + 2];
                qw = r0.rotationChannel.data[rotationOffset + 3];
            }
            particleController.setTransform(r0.positionChannel.data[positionOffset + 0], r0.positionChannel.data[positionOffset + 1], r0.positionChannel.data[positionOffset + 2], qx, qy, qz, qw, scale);
            particleController.update();
            i++;
            positionOffset += r0.positionChannel.strideSize;
        }
    }

    public ParticleControllerFinalizerInfluencer copy() {
        return new ParticleControllerFinalizerInfluencer();
    }
}
