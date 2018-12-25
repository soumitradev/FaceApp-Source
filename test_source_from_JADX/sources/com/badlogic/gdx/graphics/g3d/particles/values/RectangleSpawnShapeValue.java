package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public final class RectangleSpawnShapeValue extends PrimitiveSpawnShapeValue {
    public RectangleSpawnShapeValue(RectangleSpawnShapeValue value) {
        super(value);
        load(value);
    }

    public void spawnAux(Vector3 vector, float percent) {
        float width = this.spawnWidth + (this.spawnWidthDiff * this.spawnWidthValue.getScale(percent));
        float height = this.spawnHeight + (this.spawnHeightDiff * this.spawnHeightValue.getScale(percent));
        float depth = this.spawnDepth + (this.spawnDepthDiff * this.spawnDepthValue.getScale(percent));
        if (this.edges) {
            float tx;
            float f;
            float f2;
            int a = MathUtils.random(-1, 1);
            if (a == -1) {
                tx = MathUtils.random(1) == 0 ? (-width) / 2.0f : width / 2.0f;
                if (tx == 0.0f) {
                    f = MathUtils.random(1) == 0 ? (-height) / 2.0f : height / 2.0f;
                    f2 = MathUtils.random(1) == 0 ? (-depth) / 2.0f : depth / 2.0f;
                } else {
                    f = MathUtils.random(height) - (height / 2.0f);
                    f2 = MathUtils.random(depth) - (depth / 2.0f);
                }
            } else {
                float f3;
                if (a == 0) {
                    tx = MathUtils.random(1) == 0 ? (-depth) / 2.0f : depth / 2.0f;
                    if (tx == 0.0f) {
                        f2 = MathUtils.random(1) == 0 ? (-width) / 2.0f : width / 2.0f;
                        f = MathUtils.random(1) == 0 ? (-height) / 2.0f : height / 2.0f;
                    } else {
                        f2 = MathUtils.random(width) - (width / 2.0f);
                        f = MathUtils.random(height) - (height / 2.0f);
                    }
                    f3 = f2;
                    f2 = tx;
                } else {
                    tx = MathUtils.random(1) == 0 ? (-height) / 2.0f : height / 2.0f;
                    if (tx == 0.0f) {
                        f2 = MathUtils.random(1) == 0 ? (-depth) / 2.0f : depth / 2.0f;
                        f3 = MathUtils.random(1) == 0 ? (-width) / 2.0f : width / 2.0f;
                        f = tx;
                    } else {
                        f2 = MathUtils.random(depth) - (depth / 2.0f);
                        f = tx;
                        tx = MathUtils.random(width) - (width / 2.0f);
                    }
                }
                tx = f3;
            }
            vector.f120x = tx;
            vector.f121y = f;
            vector.f122z = f2;
            return;
        }
        vector.f120x = MathUtils.random(width) - (width / 2.0f);
        vector.f121y = MathUtils.random(height) - (height / 2.0f);
        vector.f122z = MathUtils.random(depth) - (depth / 2.0f);
    }

    public SpawnShapeValue copy() {
        return new RectangleSpawnShapeValue(this);
    }
}
