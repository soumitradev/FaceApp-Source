package org.catrobat.catroid.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.Array;
import java.util.Arrays;
import java.util.Iterator;
import org.catrobat.catroid.content.Sprite;

public class PhysicsObject {
    public static final float DEFAULT_BOUNCE_FACTOR = 0.8f;
    public static final float DEFAULT_DENSITY = 1.0f;
    public static final float DEFAULT_FRICTION = 0.2f;
    public static final float DEFAULT_MASS = 1.0f;
    public static final float MAX_FRICTION = 1.0f;
    public static final float MIN_BOUNCE_FACTOR = 0.0f;
    public static final float MIN_DENSITY = 0.0f;
    public static final float MIN_FRICTION = 0.0f;
    public static final float MIN_MASS = 1.0E-6f;
    private final Body body;
    private Vector2 bodyAabbLowerLeft;
    private Vector2 bodyAabbUpperRight;
    private short categoryMaskRecord = (short) 4;
    private float circumference;
    private short collisionMaskRecord = (short) 0;
    private Vector2 fixtureAabbLowerLeft;
    private Vector2 fixtureAabbUpperRight;
    private final FixtureDef fixtureDef = new FixtureDef();
    private float gravityScale = 0.0f;
    private boolean ifOnEdgeBounce = false;
    private float mass;
    private float rotationSpeed = 0.0f;
    private Type savedType = Type.NONE;
    private Shape[] shapes;
    private Vector2 tmpVertice;
    private Type type;
    private Vector2 velocity = new Vector2();

    public enum Type {
        DYNAMIC,
        FIXED,
        NONE
    }

    public PhysicsObject(Body b, Sprite sprite) {
        this.body = b;
        this.body.setUserData(sprite);
        this.mass = 1.0f;
        this.fixtureDef.density = 1.0f;
        this.fixtureDef.friction = 0.2f;
        this.fixtureDef.restitution = 0.8f;
        setType(Type.NONE);
        this.tmpVertice = new Vector2();
    }

    public void copyTo(PhysicsObject destination) {
        destination.setType(getType());
        destination.setPosition(getPosition());
        destination.setDirection(getDirection());
        destination.setMass(getMass());
        destination.setRotationSpeed(getRotationSpeed());
        destination.setBounceFactor(getBounceFactor());
        destination.setFriction(getFriction());
        destination.setVelocity(getVelocity());
    }

    public void setShape(Shape[] shapes) {
        if (!Arrays.equals(this.shapes, shapes)) {
            if (shapes != null) {
                this.shapes = (Shape[]) Arrays.copyOf(shapes, shapes.length);
            } else {
                this.shapes = null;
            }
            while (this.body.getFixtureList().size > 0) {
                this.body.destroyFixture((Fixture) this.body.getFixtureList().first());
            }
            if (shapes != null) {
                for (Shape tempShape : shapes) {
                    this.fixtureDef.shape = tempShape;
                    this.body.createFixture(this.fixtureDef);
                }
            }
            setMass(this.mass);
            calculateCircumference();
        }
    }

    private void calculateCircumference() {
        if (this.body.getFixtureList().size == 0) {
            this.circumference = 0.0f;
        } else {
            this.circumference = PhysicsWorldConverter.convertNormalToBox2dCoordinate(getBoundaryBoxDimensions().len() / 2.0f);
        }
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        if (this.type != type) {
            this.type = type;
            switch (type) {
                case DYNAMIC:
                    this.body.setType(BodyType.DynamicBody);
                    this.body.setGravityScale(1.0f);
                    this.body.setBullet(true);
                    setMass(this.mass);
                    this.collisionMaskRecord = (short) -3;
                    break;
                case FIXED:
                    this.body.setType(BodyType.KinematicBody);
                    this.collisionMaskRecord = (short) -3;
                    break;
                case NONE:
                    this.body.setType(BodyType.KinematicBody);
                    this.collisionMaskRecord = (short) 0;
                    break;
                default:
                    break;
            }
            calculateCircumference();
            setCollisionBits(this.categoryMaskRecord, this.collisionMaskRecord);
        }
    }

    public float getDirection() {
        return PhysicsWorldConverter.convertBox2dToNormalAngle(this.body.getAngle());
    }

    public void setDirection(float degrees) {
        this.body.setTransform(this.body.getPosition(), PhysicsWorldConverter.convertNormalToBox2dAngle(degrees));
    }

    public float getX() {
        return PhysicsWorldConverter.convertBox2dToNormalCoordinate(this.body.getPosition().f16x);
    }

    public float getY() {
        return PhysicsWorldConverter.convertBox2dToNormalCoordinate(this.body.getPosition().f17y);
    }

    public Vector2 getMassCenter() {
        return this.body.getWorldCenter();
    }

    public float getCircumference() {
        return PhysicsWorldConverter.convertBox2dToNormalCoordinate(this.circumference);
    }

    public Vector2 getPosition() {
        return PhysicsWorldConverter.convertBox2dToNormalVector(this.body.getPosition());
    }

    public void setX(float x) {
        this.body.setTransform(PhysicsWorldConverter.convertNormalToBox2dCoordinate(x), this.body.getPosition().f17y, this.body.getAngle());
    }

    public void setY(float y) {
        this.body.setTransform(this.body.getPosition().f16x, PhysicsWorldConverter.convertNormalToBox2dCoordinate(y), this.body.getAngle());
    }

    public void setPosition(float x, float y) {
        this.body.setTransform(PhysicsWorldConverter.convertNormalToBox2dCoordinate(x), PhysicsWorldConverter.convertNormalToBox2dCoordinate(y), this.body.getAngle());
    }

    public void setPosition(Vector2 position) {
        setPosition(position.f16x, position.f17y);
    }

    public float getRotationSpeed() {
        return (float) Math.toDegrees((double) this.body.getAngularVelocity());
    }

    public void setRotationSpeed(float degreesPerSecond) {
        this.body.setAngularVelocity((float) Math.toRadians((double) degreesPerSecond));
    }

    public Vector2 getVelocity() {
        return PhysicsWorldConverter.convertBox2dToNormalVector(this.body.getLinearVelocity());
    }

    public void setVelocity(float x, float y) {
        this.body.setLinearVelocity(PhysicsWorldConverter.convertNormalToBox2dCoordinate(x), PhysicsWorldConverter.convertNormalToBox2dCoordinate(y));
    }

    public void setVelocity(Vector2 velocity) {
        setVelocity(velocity.f16x, velocity.f17y);
    }

    public float getMass() {
        return this.mass;
    }

    public float getBounceFactor() {
        return this.fixtureDef.restitution;
    }

    public void setMass(float mass) {
        this.mass = mass;
        if (mass < 0.0f) {
            this.mass = 1.0E-6f;
        }
        if (mass < 1.0E-6f) {
            mass = 1.0E-6f;
        }
        if (!isStaticObject()) {
            setDensity(mass / (this.body.getMass() / this.fixtureDef.density));
        }
    }

    private boolean isStaticObject() {
        return this.body.getMass() == 0.0f;
    }

    private void setDensity(float density) {
        if (density < 0.0f) {
            density = 0.0f;
        }
        this.fixtureDef.density = density;
        Iterator it = this.body.getFixtureList().iterator();
        while (it.hasNext()) {
            ((Fixture) it.next()).setDensity(density);
        }
        this.body.resetMassData();
    }

    public float getFriction() {
        return this.fixtureDef.friction;
    }

    public void setFriction(float friction) {
        if (friction < 0.0f) {
            friction = 0.0f;
        }
        if (friction > 1.0f) {
            friction = 1.0f;
        }
        this.fixtureDef.friction = friction;
        Iterator it = this.body.getFixtureList().iterator();
        while (it.hasNext()) {
            ((Fixture) it.next()).setFriction(friction);
        }
    }

    public void setBounceFactor(float bounceFactor) {
        if (bounceFactor < 0.0f) {
            bounceFactor = 0.0f;
        }
        this.fixtureDef.restitution = bounceFactor;
        Iterator it = this.body.getFixtureList().iterator();
        while (it.hasNext()) {
            ((Fixture) it.next()).setRestitution(bounceFactor);
        }
    }

    public void setGravityScale(float scale) {
        this.body.setGravityScale(scale);
    }

    public float getGravityScale() {
        return this.body.getGravityScale();
    }

    public void setIfOnEdgeBounce(boolean bounce, Sprite sprite) {
        if (this.ifOnEdgeBounce != bounce) {
            short maskBits;
            this.ifOnEdgeBounce = bounce;
            if (bounce) {
                maskBits = (short) -1;
                this.body.setUserData(sprite);
            } else {
                maskBits = (short) -3;
            }
            setCollisionBits(this.categoryMaskRecord, maskBits);
        }
    }

    protected void setCollisionBits(short categoryBits, short maskBits) {
        setCollisionBits(categoryBits, maskBits, true);
    }

    protected void setCollisionBits(short categoryBits, short maskBits, boolean updateState) {
        this.fixtureDef.filter.categoryBits = categoryBits;
        this.fixtureDef.filter.maskBits = maskBits;
        Iterator it = this.body.getFixtureList().iterator();
        while (it.hasNext()) {
            Fixture fixture = (Fixture) it.next();
            Filter filter = fixture.getFilterData();
            filter.categoryBits = categoryBits;
            filter.maskBits = maskBits;
            fixture.setFilterData(filter);
        }
        if (updateState) {
            updateNonCollidingState();
        }
    }

    private void updateNonCollidingState() {
        if (this.body.getUserData() != null && (this.body.getUserData() instanceof Sprite)) {
            Object look = ((Sprite) this.body.getUserData()).look;
            if (look != null && (look instanceof PhysicsLook)) {
                ((PhysicsLook) look).setNonColliding(isNonColliding());
            }
        }
    }

    public void getBoundaryBox(Vector2 lowerLeft, Vector2 upperRight) {
        calculateAabb();
        lowerLeft.f16x = PhysicsWorldConverter.convertBox2dToNormalVector(this.bodyAabbLowerLeft).f16x;
        lowerLeft.f17y = PhysicsWorldConverter.convertBox2dToNormalVector(this.bodyAabbLowerLeft).f17y;
        upperRight.f16x = PhysicsWorldConverter.convertBox2dToNormalVector(this.bodyAabbUpperRight).f16x;
        upperRight.f17y = PhysicsWorldConverter.convertBox2dToNormalVector(this.bodyAabbUpperRight).f17y;
    }

    public Vector2 getBoundaryBoxDimensions() {
        calculateAabb();
        return new Vector2(PhysicsWorldConverter.convertBox2dToNormalCoordinate(Math.abs(this.bodyAabbUpperRight.f16x - this.bodyAabbLowerLeft.f16x)) + 1.0f, PhysicsWorldConverter.convertBox2dToNormalCoordinate(Math.abs(this.bodyAabbUpperRight.f17y - this.bodyAabbLowerLeft.f17y)) + 1.0f);
    }

    public void activateHangup() {
        this.velocity = new Vector2(getVelocity());
        this.rotationSpeed = getRotationSpeed();
        this.gravityScale = getGravityScale();
        setGravityScale(0.0f);
        setVelocity(0.0f, 0.0f);
        setRotationSpeed(0.0f);
    }

    public void deactivateHangup(boolean record) {
        if (record) {
            setGravityScale(this.gravityScale);
            setVelocity(this.velocity.f16x, this.velocity.f17y);
            setRotationSpeed(this.rotationSpeed);
            return;
        }
        setGravityScale(1.0f);
    }

    public void activateNonColliding(boolean updateState) {
        setCollisionBits(this.categoryMaskRecord, (short) 0, updateState);
    }

    public void deactivateNonColliding(boolean record, boolean updateState) {
        if (record) {
            setCollisionBits(this.categoryMaskRecord, this.collisionMaskRecord, updateState);
        }
    }

    public void activateFixed() {
        this.savedType = getType();
        setType(Type.FIXED);
    }

    public void deactivateFixed(boolean record) {
        if (record) {
            setType(this.savedType);
        }
    }

    public boolean isNonColliding() {
        return this.collisionMaskRecord == (short) 0;
    }

    private void calculateAabb() {
        this.bodyAabbLowerLeft = new Vector2(2.14748365E9f, 2.14748365E9f);
        this.bodyAabbUpperRight = new Vector2(-2.14748365E9f, -2.14748365E9f);
        Transform transform = this.body.getTransform();
        int len = this.body.getFixtureList().size;
        Array<Fixture> fixtures = this.body.getFixtureList();
        if (fixtures.size == 0) {
            this.bodyAabbLowerLeft.f16x = 0.0f;
            this.bodyAabbLowerLeft.f17y = 0.0f;
            this.bodyAabbUpperRight.f16x = 0.0f;
            this.bodyAabbUpperRight.f17y = 0.0f;
        }
        for (int i = 0; i < len; i++) {
            calculateAabb((Fixture) fixtures.get(i), transform);
        }
    }

    private void calculateAabb(Fixture fixture, Transform transform) {
        this.fixtureAabbLowerLeft = new Vector2(2.14748365E9f, 2.14748365E9f);
        this.fixtureAabbUpperRight = new Vector2(-2.14748365E9f, -2.14748365E9f);
        if (fixture.getType() == com.badlogic.gdx.physics.box2d.Shape.Type.Circle) {
            CircleShape shape = (CircleShape) fixture.getShape();
            float radius = shape.getRadius();
            this.tmpVertice.set(shape.getPosition());
            this.tmpVertice.rotate(transform.getRotation()).add(transform.getPosition());
            this.fixtureAabbLowerLeft.set(this.tmpVertice.f16x - radius, this.tmpVertice.f17y - radius);
            this.fixtureAabbUpperRight.set(this.tmpVertice.f16x + radius, this.tmpVertice.f17y + radius);
        } else if (fixture.getType() == com.badlogic.gdx.physics.box2d.Shape.Type.Polygon) {
            PolygonShape shape2 = (PolygonShape) fixture.getShape();
            int vertexCount = shape2.getVertexCount();
            shape2.getVertex(0, this.tmpVertice);
            this.fixtureAabbLowerLeft.set(transform.mul(this.tmpVertice));
            this.fixtureAabbUpperRight.set(this.fixtureAabbLowerLeft);
            for (int i = 1; i < vertexCount; i++) {
                shape2.getVertex(i, this.tmpVertice);
                transform.mul(this.tmpVertice);
                this.fixtureAabbLowerLeft.f16x = Math.min(this.fixtureAabbLowerLeft.f16x, this.tmpVertice.f16x);
                this.fixtureAabbLowerLeft.f17y = Math.min(this.fixtureAabbLowerLeft.f17y, this.tmpVertice.f17y);
                this.fixtureAabbUpperRight.f16x = Math.max(this.fixtureAabbUpperRight.f16x, this.tmpVertice.f16x);
                this.fixtureAabbUpperRight.f17y = Math.max(this.fixtureAabbUpperRight.f17y, this.tmpVertice.f17y);
            }
        }
        this.bodyAabbLowerLeft.f16x = Math.min(this.fixtureAabbLowerLeft.f16x, this.bodyAabbLowerLeft.f16x);
        this.bodyAabbLowerLeft.f17y = Math.min(this.fixtureAabbLowerLeft.f17y, this.bodyAabbLowerLeft.f17y);
        this.bodyAabbUpperRight.f16x = Math.max(this.fixtureAabbUpperRight.f16x, this.bodyAabbUpperRight.f16x);
        this.bodyAabbUpperRight.f17y = Math.max(this.fixtureAabbUpperRight.f17y, this.bodyAabbUpperRight.f17y);
    }
}
