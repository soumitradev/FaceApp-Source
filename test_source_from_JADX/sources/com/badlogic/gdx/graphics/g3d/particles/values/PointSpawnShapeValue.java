package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.math.Vector3;

public final class PointSpawnShapeValue extends PrimitiveSpawnShapeValue {
    public PointSpawnShapeValue(PointSpawnShapeValue value) {
        super(value);
        load(value);
    }

    public void spawnAux(Vector3 vector, float percent) {
        vector.f120x = this.spawnWidth + (this.spawnWidthDiff * this.spawnWidthValue.getScale(percent));
        vector.f121y = this.spawnHeight + (this.spawnHeightDiff * this.spawnHeightValue.getScale(percent));
        vector.f122z = this.spawnDepth + (this.spawnDepthDiff * this.spawnDepthValue.getScale(percent));
    }

    public SpawnShapeValue copy() {
        return new PointSpawnShapeValue(this);
    }
}
