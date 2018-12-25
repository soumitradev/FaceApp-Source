package org.catrobat.catroid.physics;

import android.util.Log;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxNativesLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.physics.PhysicsBoundaryBox.BoundaryBoxIdentifier;
import org.catrobat.catroid.physics.shapebuilder.PhysicsShapeBuilder;

public class PhysicsWorld {
    public static final float ACTIVE_AREA_HEIGHT_FACTOR = 2.0f;
    public static final float ACTIVE_AREA_WIDTH_FACTOR = 3.0f;
    public static final short CATEGORY_BOUNDARYBOX = (short) 2;
    public static final short CATEGORY_NO_COLLISION = (short) 0;
    public static final short CATEGORY_PHYSICSOBJECT = (short) 4;
    public static final Vector2 DEFAULT_GRAVITY = new Vector2(0.0f, -10.0f);
    public static final boolean IGNORE_SLEEPING_OBJECTS = false;
    public static final short MASK_BOUNDARYBOX = (short) 4;
    public static final short MASK_NO_COLLISION = (short) 0;
    public static final short MASK_PHYSICSOBJECT = (short) -3;
    public static final short MASK_TO_BOUNCE = (short) -1;
    public static final int POSITION_ITERATIONS = 3;
    public static final float RATIO = 10.0f;
    public static final int STABILIZING_STEPS = 6;
    private static final String TAG = PhysicsWorld.class.getSimpleName();
    public static final int VELOCITY_ITERATIONS = 3;
    public static Vector2 activeArea;
    private final ArrayList<Sprite> activeHorizontalBounces;
    private final ArrayList<Sprite> activeVerticalBounces;
    private PhysicsBoundaryBox boundaryBox;
    private final Map<Sprite, PhysicsObject> physicsObjects;
    private PhysicsShapeBuilder physicsShapeBuilder;
    private Box2DDebugRenderer renderer;
    private int stabilizingSteCounter;
    private final World world;

    static {
        GdxNativesLoader.load();
    }

    public PhysicsWorld() {
        this(ScreenValues.SCREEN_WIDTH, ScreenValues.SCREEN_HEIGHT);
    }

    public PhysicsWorld(int width, int height) {
        this.world = new World(DEFAULT_GRAVITY, false);
        this.physicsObjects = new HashMap();
        this.activeVerticalBounces = new ArrayList();
        this.activeHorizontalBounces = new ArrayList();
        this.stabilizingSteCounter = 0;
        this.physicsShapeBuilder = PhysicsShapeBuilder.getInstance();
        this.boundaryBox = new PhysicsBoundaryBox(this.world);
        this.boundaryBox.create(width, height);
        activeArea = new Vector2(((float) width) * 3.0f, ((float) height) * 2.0f);
        this.world.setContactListener(new PhysicsCollision(this));
    }

    public void setBounceOnce(Sprite sprite, BoundaryBoxIdentifier boundaryBoxIdentifier) {
        if (this.physicsObjects.containsKey(sprite)) {
            ((PhysicsObject) this.physicsObjects.get(sprite)).setIfOnEdgeBounce(true, sprite);
            switch (boundaryBoxIdentifier) {
                case BBI_HORIZONTAL:
                    this.activeHorizontalBounces.add(sprite);
                    return;
                case BBI_VERTICAL:
                    this.activeVerticalBounces.add(sprite);
                    return;
                default:
                    return;
            }
        }
    }

    public void step(float deltaTime) {
        if (this.stabilizingSteCounter < 6) {
            this.stabilizingSteCounter++;
            return;
        }
        try {
            this.world.step(deltaTime, 3, 3);
        } catch (Exception exception) {
            Log.e(TAG, Log.getStackTraceString(exception));
        }
    }

    public void render(Matrix4 perspectiveMatrix) {
        if (this.renderer == null) {
            this.renderer = new Box2DDebugRenderer(false, false, false, false, false, false);
        }
        this.renderer.render(this.world, perspectiveMatrix.scl(10.0f));
    }

    public void setGravity(float x, float y) {
        this.world.setGravity(new Vector2(x, y));
    }

    public Vector2 getGravity() {
        return this.world.getGravity();
    }

    public void changeLook(PhysicsObject physicsObject, Look look) {
        Shape[] shapes = null;
        if (!(look.getLookData() == null || look.getLookData().getFile() == null)) {
            shapes = this.physicsShapeBuilder.getScaledShapes(look.getLookData(), look.getSizeInUserInterfaceDimensionUnit() / 100.0f);
        }
        physicsObject.setShape(shapes);
    }

    public PhysicsObject getPhysicsObject(Sprite sprite) {
        if (sprite == null) {
            throw new NullPointerException();
        } else if (this.physicsObjects.containsKey(sprite)) {
            return (PhysicsObject) this.physicsObjects.get(sprite);
        } else {
            PhysicsObject physicsObject = createPhysicsObject(sprite);
            this.physicsObjects.put(sprite, physicsObject);
            return physicsObject;
        }
    }

    private PhysicsObject createPhysicsObject(Sprite sprite) {
        return new PhysicsObject(this.world.createBody(new BodyDef()), sprite);
    }

    public void bouncedOnEdge(Sprite sprite, BoundaryBoxIdentifier boundaryBoxIdentifier) {
        if (this.physicsObjects.containsKey(sprite)) {
            PhysicsObject physicsObject = (PhysicsObject) this.physicsObjects.get(sprite);
            switch (boundaryBoxIdentifier) {
                case BBI_HORIZONTAL:
                    if (this.activeHorizontalBounces.remove(sprite) && !this.activeVerticalBounces.contains(sprite)) {
                        physicsObject.setIfOnEdgeBounce(false, sprite);
                        PhysicsCollisionBroadcast.fireEvent(sprite, null);
                        return;
                    }
                    return;
                case BBI_VERTICAL:
                    if (this.activeVerticalBounces.remove(sprite) && !this.activeHorizontalBounces.contains(sprite)) {
                        physicsObject.setIfOnEdgeBounce(false, sprite);
                        PhysicsCollisionBroadcast.fireEvent(sprite, null);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
