package org.catrobat.catroid.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsBoundaryBox {
    public static final int FRAME_SIZE = 5;
    private final World world;

    public enum BoundaryBoxIdentifier {
        BBI_HORIZONTAL,
        BBI_VERTICAL
    }

    public PhysicsBoundaryBox(World world) {
        this.world = world;
    }

    public void create(int width, int height) {
        float boxWidth = PhysicsWorldConverter.convertNormalToBox2dCoordinate((float) width);
        float boxHeight = PhysicsWorldConverter.convertNormalToBox2dCoordinate((float) height);
        float boxElementSize = PhysicsWorldConverter.convertNormalToBox2dCoordinate(5.0f);
        float halfBoxElementSize = boxElementSize / 2.0f;
        createSide(new Vector2(0.0f, (boxHeight / 2.0f) + halfBoxElementSize), boxWidth, boxElementSize, BoundaryBoxIdentifier.BBI_HORIZONTAL);
        createSide(new Vector2(0.0f, (-(boxHeight / 2.0f)) - halfBoxElementSize), boxWidth, boxElementSize, BoundaryBoxIdentifier.BBI_HORIZONTAL);
        createSide(new Vector2((-(boxWidth / 2.0f)) - halfBoxElementSize, 0.0f), boxElementSize, boxHeight, BoundaryBoxIdentifier.BBI_VERTICAL);
        createSide(new Vector2((boxWidth / 2.0f) + halfBoxElementSize, 0.0f), boxElementSize, boxHeight, BoundaryBoxIdentifier.BBI_VERTICAL);
    }

    private void createSide(Vector2 center, float width, float height, BoundaryBoxIdentifier identifier) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.allowSleep = false;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2.0f, height / 2.0f, center, 0.0f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.maskBits = (short) 4;
        fixtureDef.filter.categoryBits = (short) 2;
        Body body = this.world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(identifier);
    }
}
