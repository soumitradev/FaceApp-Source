package com.badlogic.gdx.graphics.g3d.particles.renderers;

import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;

public abstract class ParticleControllerRenderer<D extends ParticleControllerRenderData, T extends ParticleBatch<D>> extends ParticleControllerComponent {
    protected T batch;
    protected D renderData;

    public abstract boolean isCompatible(ParticleBatch<?> particleBatch);

    protected ParticleControllerRenderer() {
    }

    protected ParticleControllerRenderer(D renderData) {
        this.renderData = renderData;
    }

    public void update() {
        this.batch.draw(this.renderData);
    }

    public boolean setBatch(ParticleBatch<?> batch) {
        if (!isCompatible(batch)) {
            return false;
        }
        this.batch = batch;
        return true;
    }

    public void set(ParticleController particleController) {
        super.set(particleController);
        if (this.renderData != null) {
            this.renderData.controller = this.controller;
        }
    }
}
