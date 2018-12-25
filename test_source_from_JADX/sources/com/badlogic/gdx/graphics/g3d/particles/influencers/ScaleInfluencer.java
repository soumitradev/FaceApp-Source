package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;

public class ScaleInfluencer extends SimpleInfluencer {
    public ScaleInfluencer() {
        this.valueChannelDescriptor = ParticleChannels.Scale;
    }

    public void activateParticles(int startIndex, int count) {
        int i;
        int a;
        int c;
        if (this.value.isRelative()) {
            i = this.valueChannel.strideSize * startIndex;
            a = this.interpolationChannel.strideSize * startIndex;
            c = (this.valueChannel.strideSize * count) + i;
            while (i < c) {
                float start = this.value.newLowValue() * this.controller.scale.f120x;
                float diff = this.value.newHighValue() * this.controller.scale.f120x;
                this.interpolationChannel.data[a + 0] = start;
                this.interpolationChannel.data[a + 1] = diff;
                this.valueChannel.data[i] = (this.value.getScale(0.0f) * diff) + start;
                i += this.valueChannel.strideSize;
                a += this.interpolationChannel.strideSize;
            }
            return;
        }
        i = this.valueChannel.strideSize * startIndex;
        a = this.interpolationChannel.strideSize * startIndex;
        c = (this.valueChannel.strideSize * count) + i;
        while (i < c) {
            start = this.value.newLowValue() * this.controller.scale.f120x;
            diff = (this.value.newHighValue() * this.controller.scale.f120x) - start;
            this.interpolationChannel.data[a + 0] = start;
            this.interpolationChannel.data[a + 1] = diff;
            this.valueChannel.data[i] = (this.value.getScale(0.0f) * diff) + start;
            i += this.valueChannel.strideSize;
            a += this.interpolationChannel.strideSize;
        }
    }

    public ScaleInfluencer(ScaleInfluencer scaleInfluencer) {
        super(scaleInfluencer);
    }

    public ParticleControllerComponent copy() {
        return new ScaleInfluencer(this);
    }
}
