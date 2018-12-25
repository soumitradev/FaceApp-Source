package com.badlogic.gdx.graphics.g3d.particles.renderers;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray.FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray.ObjectChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.batches.ModelInstanceParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;

public class ModelInstanceRenderer extends ParticleControllerRenderer<ModelInstanceControllerRenderData, ModelInstanceParticleBatch> {
    private boolean hasColor;
    private boolean hasRotation;
    private boolean hasScale;

    public ModelInstanceRenderer() {
        super(new ModelInstanceControllerRenderData());
    }

    public ModelInstanceRenderer(ModelInstanceParticleBatch batch) {
        this();
        setBatch(batch);
    }

    public void allocateChannels() {
        ((ModelInstanceControllerRenderData) this.renderData).positionChannel = (FloatChannel) this.controller.particles.addChannel(ParticleChannels.Position);
    }

    public void init() {
        ((ModelInstanceControllerRenderData) this.renderData).modelInstanceChannel = (ObjectChannel) this.controller.particles.getChannel(ParticleChannels.ModelInstance);
        ((ModelInstanceControllerRenderData) this.renderData).colorChannel = (FloatChannel) this.controller.particles.getChannel(ParticleChannels.Color);
        ((ModelInstanceControllerRenderData) this.renderData).scaleChannel = (FloatChannel) this.controller.particles.getChannel(ParticleChannels.Scale);
        ((ModelInstanceControllerRenderData) this.renderData).rotationChannel = (FloatChannel) this.controller.particles.getChannel(ParticleChannels.Rotation3D);
        boolean z = false;
        this.hasColor = ((ModelInstanceControllerRenderData) this.renderData).colorChannel != null;
        this.hasScale = ((ModelInstanceControllerRenderData) this.renderData).scaleChannel != null;
        if (((ModelInstanceControllerRenderData) this.renderData).rotationChannel != null) {
            z = true;
        }
        this.hasRotation = z;
    }

    public void update() {
        int i = 0;
        int positionOffset = 0;
        int c = this.controller.particles.size;
        while (i < c) {
            ModelInstance instance = ((ModelInstance[]) ((ModelInstanceControllerRenderData) r0.renderData).modelInstanceChannel.data)[i];
            float scale = r0.hasScale ? ((ModelInstanceControllerRenderData) r0.renderData).scaleChannel.data[i] : 1.0f;
            float qx = 0.0f;
            float qy = 0.0f;
            float qz = 0.0f;
            float qw = 1.0f;
            if (r0.hasRotation) {
                int rotationOffset = ((ModelInstanceControllerRenderData) r0.renderData).rotationChannel.strideSize * i;
                qx = ((ModelInstanceControllerRenderData) r0.renderData).rotationChannel.data[rotationOffset + 0];
                qy = ((ModelInstanceControllerRenderData) r0.renderData).rotationChannel.data[rotationOffset + 1];
                qz = ((ModelInstanceControllerRenderData) r0.renderData).rotationChannel.data[rotationOffset + 2];
                qw = ((ModelInstanceControllerRenderData) r0.renderData).rotationChannel.data[rotationOffset + 3];
            }
            instance.transform.set(((ModelInstanceControllerRenderData) r0.renderData).positionChannel.data[positionOffset + 0], ((ModelInstanceControllerRenderData) r0.renderData).positionChannel.data[positionOffset + 1], ((ModelInstanceControllerRenderData) r0.renderData).positionChannel.data[positionOffset + 2], qx, qy, qz, qw, scale, scale, scale);
            if (r0.hasColor) {
                int colorOffset = ((ModelInstanceControllerRenderData) r0.renderData).colorChannel.strideSize * i;
                ColorAttribute colorAttribute = (ColorAttribute) ((Material) instance.materials.get(0)).get(ColorAttribute.Diffuse);
                BlendingAttribute blendingAttribute = (BlendingAttribute) ((Material) instance.materials.get(0)).get(BlendingAttribute.Type);
                colorAttribute.color.f4r = ((ModelInstanceControllerRenderData) r0.renderData).colorChannel.data[colorOffset + 0];
                colorAttribute.color.f3g = ((ModelInstanceControllerRenderData) r0.renderData).colorChannel.data[colorOffset + 1];
                colorAttribute.color.f2b = ((ModelInstanceControllerRenderData) r0.renderData).colorChannel.data[colorOffset + 2];
                if (blendingAttribute != null) {
                    blendingAttribute.opacity = ((ModelInstanceControllerRenderData) r0.renderData).colorChannel.data[colorOffset + 3];
                }
            }
            i++;
            positionOffset += ((ModelInstanceControllerRenderData) r0.renderData).positionChannel.strideSize;
        }
        super.update();
    }

    public ParticleControllerComponent copy() {
        return new ModelInstanceRenderer((ModelInstanceParticleBatch) this.batch);
    }

    public boolean isCompatible(ParticleBatch<?> batch) {
        return batch instanceof ModelInstanceParticleBatch;
    }
}
