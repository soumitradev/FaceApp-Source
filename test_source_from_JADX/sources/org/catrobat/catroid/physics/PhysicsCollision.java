package org.catrobat.catroid.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.CollisionEventId;
import org.catrobat.catroid.physics.PhysicsBoundaryBox.BoundaryBoxIdentifier;

public class PhysicsCollision implements ContactListener {
    public static final String COLLISION_MESSAGE_CONNECTOR = "<\t-\t>";
    public static final String COLLISION_MESSAGE_ESCAPE_CHAR = "\t";
    public static final String COLLISION_WITH_ANYTHING_IDENTIFIER = "\tANYTHING\t";
    private Map<CollisionEventId, PhysicsCollisionBroadcast> physicsCollisionBroadcasts = new HashMap();
    private PhysicsWorld physicsWorld;

    public PhysicsCollision(PhysicsWorld physicsWorld) {
        this.physicsWorld = physicsWorld;
    }

    private static CollisionEventId generateKey(Sprite sprite1, Sprite sprite2) {
        return new CollisionEventId(sprite1, sprite2);
    }

    private void registerContact(Sprite sprite1, Sprite sprite2) {
        CollisionEventId identifier = generateKey(sprite1, sprite2);
        if (!this.physicsCollisionBroadcasts.containsKey(identifier)) {
            this.physicsCollisionBroadcasts.put(identifier, new PhysicsCollisionBroadcast(sprite1, sprite2));
        }
        ((PhysicsCollisionBroadcast) this.physicsCollisionBroadcasts.get(identifier)).increaseContactCounter();
    }

    private void unregisterContact(Sprite sprite1, Sprite sprite2) {
        CollisionEventId identifier = generateKey(sprite1, sprite2);
        if (this.physicsCollisionBroadcasts.containsKey(identifier)) {
            PhysicsCollisionBroadcast physicsCollisionBroadcast = (PhysicsCollisionBroadcast) this.physicsCollisionBroadcasts.get(identifier);
            physicsCollisionBroadcast.decreaseContactCounter();
            if (physicsCollisionBroadcast.getContactCounter() == 0) {
                physicsCollisionBroadcast.sendBroadcast();
                this.physicsCollisionBroadcasts.remove(identifier);
            }
        }
    }

    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        if ((a.getUserData() instanceof Sprite) && (b.getUserData() instanceof BoundaryBoxIdentifier)) {
            this.physicsWorld.bouncedOnEdge((Sprite) a.getUserData(), (BoundaryBoxIdentifier) b.getUserData());
        } else if ((a.getUserData() instanceof BoundaryBoxIdentifier) && (b.getUserData() instanceof Sprite)) {
            this.physicsWorld.bouncedOnEdge((Sprite) b.getUserData(), (BoundaryBoxIdentifier) a.getUserData());
        } else if ((a.getUserData() instanceof Sprite) && (b.getUserData() instanceof Sprite)) {
            registerContact((Sprite) a.getUserData(), (Sprite) b.getUserData());
        }
    }

    public void endContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        if ((a.getUserData() instanceof Sprite) && (b.getUserData() instanceof Sprite)) {
            unregisterContact((Sprite) a.getUserData(), (Sprite) b.getUserData());
        }
    }

    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
