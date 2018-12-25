package org.catrobat.catroid.physics;

import com.badlogic.gdx.math.Vector2;

public final class PhysicsWorldConverter {
    private PhysicsWorldConverter() {
    }

    public static float convertBox2dToNormalAngle(float box2dAngle) {
        return (float) Math.toDegrees((double) box2dAngle);
    }

    public static float convertNormalToBox2dAngle(float catroidAngle) {
        return (float) Math.toRadians((double) catroidAngle);
    }

    public static float convertBox2dToNormalCoordinate(float box2dCoordinate) {
        return 10.0f * box2dCoordinate;
    }

    public static float convertNormalToBox2dCoordinate(float catroidCoordinate) {
        return catroidCoordinate / 10.0f;
    }

    public static Vector2 convertBox2dToNormalVector(Vector2 box2DVector) {
        return new Vector2(convertBox2dToNormalCoordinate(box2DVector.f16x), convertBox2dToNormalCoordinate(box2DVector.f17y));
    }

    public static Vector2 convertCatroidToBox2dVector(Vector2 catroidVector) {
        return new Vector2(convertNormalToBox2dCoordinate(catroidVector.f16x), convertNormalToBox2dCoordinate(catroidVector.f17y));
    }
}
