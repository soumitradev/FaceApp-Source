package org.catrobat.catroid.physics.shapebuilder;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.physics.box2d.Shape;

public interface PhysicsShapeBuilderStrategy {
    Shape[] build(Pixmap pixmap, float f);
}
