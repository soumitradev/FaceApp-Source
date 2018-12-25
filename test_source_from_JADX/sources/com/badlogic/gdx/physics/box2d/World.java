package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.physics.box2d.joints.GearJoint;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.physics.box2d.joints.MotorJoint;
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.LongMap;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import java.util.Iterator;

public final class World implements Disposable {
    protected final long addr;
    protected final LongMap<Body> bodies = new LongMap(100);
    private final Contact contact = new Contact(this, 0);
    private long[] contactAddrs = new long[200];
    protected ContactFilter contactFilter = null;
    protected ContactListener contactListener = null;
    private final Array<Contact> contacts = new Array();
    protected final LongMap<Fixture> fixtures = new LongMap(100);
    protected final Pool<Body> freeBodies = new Pool<Body>(100, 200) {
        protected Body newObject() {
            return new Body(World.this, 0);
        }
    };
    private final Array<Contact> freeContacts = new Array();
    protected final Pool<Fixture> freeFixtures = new Pool<Fixture>(100, 200) {
        protected Fixture newObject() {
            return new Fixture(null, 0);
        }
    };
    final Vector2 gravity = new Vector2();
    private final ContactImpulse impulse = new ContactImpulse(this, 0);
    protected final LongMap<Joint> joints = new LongMap(100);
    private final Manifold manifold = new Manifold(0);
    private QueryCallback queryCallback = null;
    private RayCastCallback rayCastCallback = null;
    private Vector2 rayNormal = new Vector2();
    private Vector2 rayPoint = new Vector2();
    final float[] tmpGravity = new float[2];

    public static native float getVelocityThreshold();

    private native void jniClearForces(long j);

    private native long jniCreateBody(long j, int i, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, float f9);

    private native long jniCreateDistanceJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7);

    private native long jniCreateFrictionJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, float f6);

    private native long jniCreateGearJoint(long j, long j2, long j3, boolean z, long j4, long j5, float f);

    private native long jniCreateMotorJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, float f6);

    private native long jniCreateMouseJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5);

    private native long jniCreatePrismaticJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z2, float f8, float f9, boolean z3, float f10, float f11);

    private native long jniCreatePulleyJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11);

    private native long jniCreateRevoluteJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, boolean z2, float f6, float f7, boolean z3, float f8, float f9);

    private native long jniCreateRopeJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5);

    private native long jniCreateWeldJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7);

    private native long jniCreateWheelJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, float f6, boolean z2, float f7, float f8, float f9, float f10);

    private native void jniDeactivateBody(long j, long j2);

    private native void jniDestroyBody(long j, long j2);

    private native void jniDestroyFixture(long j, long j2, long j3);

    private native void jniDestroyJoint(long j, long j2);

    private native void jniDispose(long j);

    private native boolean jniGetAutoClearForces(long j);

    private native int jniGetBodyCount(long j);

    private native int jniGetContactCount(long j);

    private native void jniGetContactList(long j, long[] jArr);

    private native void jniGetGravity(long j, float[] fArr);

    private native int jniGetJointcount(long j);

    private native int jniGetProxyCount(long j);

    private native boolean jniIsLocked(long j);

    private native void jniQueryAABB(long j, float f, float f2, float f3, float f4);

    private native void jniRayCast(long j, float f, float f2, float f3, float f4);

    private native void jniSetAutoClearForces(long j, boolean z);

    private native void jniSetContiousPhysics(long j, boolean z);

    private native void jniSetGravity(long j, float f, float f2);

    private native void jniSetWarmStarting(long j, boolean z);

    private native void jniStep(long j, float f, int i, int i2);

    private native long newWorld(float f, float f2, boolean z);

    private native void setUseDefaultContactFilter(boolean z);

    public static native void setVelocityThreshold(float f);

    static {
        new SharedLibraryLoader().load("gdx-box2d");
    }

    public World(Vector2 gravity, boolean doSleep) {
        this.addr = newWorld(gravity.f16x, gravity.f17y, doSleep);
        this.contacts.ensureCapacity(this.contactAddrs.length);
        this.freeContacts.ensureCapacity(this.contactAddrs.length);
        for (int i = 0; i < this.contactAddrs.length; i++) {
            this.freeContacts.add(new Contact(this, 0));
        }
    }

    public void setDestructionListener(DestructionListener listener) {
    }

    public void setContactFilter(ContactFilter filter) {
        this.contactFilter = filter;
        setUseDefaultContactFilter(filter == null);
    }

    public void setContactListener(ContactListener listener) {
        this.contactListener = listener;
    }

    public Body createBody(BodyDef def) {
        BodyDef bodyDef = def;
        long j = this.addr;
        int value = bodyDef.type.getValue();
        float f = bodyDef.position.f16x;
        float f2 = bodyDef.position.f17y;
        float f3 = bodyDef.angle;
        float f4 = bodyDef.linearVelocity.f16x;
        float f5 = bodyDef.linearVelocity.f17y;
        float f6 = bodyDef.angularVelocity;
        float f7 = bodyDef.linearDamping;
        float f8 = bodyDef.angularDamping;
        boolean z = bodyDef.allowSleep;
        boolean z2 = bodyDef.awake;
        boolean z3 = bodyDef.fixedRotation;
        boolean z4 = z2;
        boolean z5 = bodyDef.bullet;
        long bodyAddr = jniCreateBody(j, value, f, f2, f3, f4, f5, f6, f7, f8, z, z4, z3, z5, bodyDef.active, bodyDef.gravityScale);
        Body body = (Body) this.freeBodies.obtain();
        body.reset(bodyAddr);
        this.bodies.put(body.addr, body);
        return body;
    }

    public void destroyBody(Body body) {
        Array<JointEdge> jointList = body.getJointList();
        while (jointList.size > 0) {
            destroyJoint(((JointEdge) body.getJointList().get(0)).joint);
        }
        jniDestroyBody(this.addr, body.addr);
        body.setUserData(null);
        this.bodies.remove(body.addr);
        Array<Fixture> fixtureList = body.getFixtureList();
        while (fixtureList.size > 0) {
            ((Fixture) this.fixtures.remove(((Fixture) fixtureList.removeIndex(0)).addr)).setUserData(null);
        }
        this.freeBodies.free(body);
    }

    void destroyFixture(Body body, Fixture fixture) {
        jniDestroyFixture(this.addr, body.addr, fixture.addr);
    }

    void deactivateBody(Body body) {
        jniDeactivateBody(this.addr, body.addr);
    }

    public Joint createJoint(JointDef def) {
        long jointAddr = createProperJoint(def);
        Joint joint = null;
        if (def.type == JointType.DistanceJoint) {
            joint = new DistanceJoint(this, jointAddr);
        }
        if (def.type == JointType.FrictionJoint) {
            joint = new FrictionJoint(this, jointAddr);
        }
        Joint joint2 = joint;
        if (def.type == JointType.GearJoint) {
            joint2 = new GearJoint(this, jointAddr, ((GearJointDef) def).joint1, ((GearJointDef) def).joint2);
        }
        if (def.type == JointType.MotorJoint) {
            joint2 = new MotorJoint(this, jointAddr);
        }
        if (def.type == JointType.MouseJoint) {
            joint2 = new MouseJoint(this, jointAddr);
        }
        if (def.type == JointType.PrismaticJoint) {
            joint2 = new PrismaticJoint(this, jointAddr);
        }
        if (def.type == JointType.PulleyJoint) {
            joint2 = new PulleyJoint(this, jointAddr);
        }
        if (def.type == JointType.RevoluteJoint) {
            joint2 = new RevoluteJoint(this, jointAddr);
        }
        if (def.type == JointType.RopeJoint) {
            joint2 = new RopeJoint(this, jointAddr);
        }
        if (def.type == JointType.WeldJoint) {
            joint2 = new WeldJoint(this, jointAddr);
        }
        if (def.type == JointType.WheelJoint) {
            joint2 = new WheelJoint(this, jointAddr);
        }
        if (joint2 != null) {
            this.joints.put(joint2.addr, joint2);
        }
        JointEdge jointEdgeA = new JointEdge(def.bodyB, joint2);
        JointEdge jointEdgeB = new JointEdge(def.bodyA, joint2);
        joint2.jointEdgeA = jointEdgeA;
        joint2.jointEdgeB = jointEdgeB;
        def.bodyA.joints.add(jointEdgeA);
        def.bodyB.joints.add(jointEdgeB);
        return joint2;
    }

    private long createProperJoint(JointDef def) {
        World world = this;
        JointDef jointDef = def;
        if (jointDef.type == JointType.DistanceJoint) {
            DistanceJointDef d = (DistanceJointDef) jointDef;
            long j = world.addr;
            long j2 = d.bodyA.addr;
            long j3 = d.bodyB.addr;
            boolean z = d.collideConnected;
            float f = d.localAnchorA.f16x;
            float f2 = d.localAnchorA.f17y;
            float f3 = d.localAnchorB.f16x;
            float f4 = d.localAnchorB.f17y;
            float f5 = d.length;
            float f6 = d.frequencyHz;
            JointDef jointDef2 = def;
            return jniCreateDistanceJoint(j, j2, j3, z, f, f2, f3, f4, f5, f6, d.dampingRatio);
        }
        jointDef2 = jointDef;
        if (jointDef2.type == JointType.FrictionJoint) {
            FrictionJointDef d2 = (FrictionJointDef) jointDef2;
            JointDef jointDef3 = jointDef2;
            j = this.addr;
            j2 = d2.bodyA.addr;
            j3 = d2.bodyB.addr;
            z = d2.collideConnected;
            f = d2.localAnchorA.f16x;
            f2 = d2.localAnchorA.f17y;
            f3 = d2.localAnchorB.f16x;
            f4 = d2.localAnchorB.f17y;
            f5 = d2.maxForce;
            float f7 = d2.maxTorque;
            jointDef = jointDef3;
            return jniCreateFrictionJoint(j, j2, j3, z, f, f2, f3, f4, f5, f7);
        }
        jointDef = jointDef2;
        world = this;
        if (jointDef.type == JointType.GearJoint) {
            GearJointDef d3 = (GearJointDef) jointDef;
            return jniCreateGearJoint(world.addr, d3.bodyA.addr, d3.bodyB.addr, d3.collideConnected, d3.joint1.addr, d3.joint2.addr, d3.ratio);
        } else if (jointDef.type == JointType.MotorJoint) {
            MotorJointDef d4 = (MotorJointDef) jointDef;
            return jniCreateMotorJoint(world.addr, d4.bodyA.addr, d4.bodyB.addr, d4.collideConnected, d4.linearOffset.f16x, d4.linearOffset.f17y, d4.angularOffset, d4.maxForce, d4.maxTorque, d4.correctionFactor);
        } else if (jointDef.type == JointType.MouseJoint) {
            MouseJointDef d5 = (MouseJointDef) jointDef;
            return jniCreateMouseJoint(world.addr, d5.bodyA.addr, d5.bodyB.addr, d5.collideConnected, d5.target.f16x, d5.target.f17y, d5.maxForce, d5.frequencyHz, d5.dampingRatio);
        } else if (jointDef.type == JointType.PrismaticJoint) {
            PrismaticJointDef d6 = (PrismaticJointDef) jointDef;
            j = world.addr;
            j2 = d6.bodyA.addr;
            j3 = d6.bodyB.addr;
            z = d6.collideConnected;
            f = d6.localAnchorA.f16x;
            f2 = d6.localAnchorA.f17y;
            f3 = d6.localAnchorB.f16x;
            f4 = d6.localAnchorB.f17y;
            f5 = d6.localAxisA.f16x;
            f6 = d6.localAxisA.f17y;
            return jniCreatePrismaticJoint(j, j2, j3, z, f, f2, f3, f4, f5, f6, d6.referenceAngle, d6.enableLimit, d6.lowerTranslation, d6.upperTranslation, d6.enableMotor, d6.maxMotorForce, d6.motorSpeed);
        } else {
            jointDef2 = def;
            if (jointDef2.type == JointType.PulleyJoint) {
                PulleyJointDef d7 = (PulleyJointDef) jointDef2;
                j = this.addr;
                j2 = d7.bodyA.addr;
                j3 = d7.bodyB.addr;
                z = d7.collideConnected;
                f = d7.groundAnchorA.f16x;
                f2 = d7.groundAnchorA.f17y;
                f3 = d7.groundAnchorB.f16x;
                f4 = d7.groundAnchorB.f17y;
                f5 = d7.localAnchorA.f16x;
                float f8 = d7.localAnchorA.f17y;
                float f9 = d7.localAnchorB.f16x;
                f6 = d7.localAnchorB.f17y;
                return jniCreatePulleyJoint(j, j2, j3, z, f, f2, f3, f4, f5, f8, f9, f6, d7.lengthA, d7.lengthB, d7.ratio);
            }
            jointDef2 = def;
            if (jointDef2.type == JointType.RevoluteJoint) {
                RevoluteJointDef d8 = (RevoluteJointDef) jointDef2;
                j = this.addr;
                j2 = d8.bodyA.addr;
                j3 = d8.bodyB.addr;
                z = d8.collideConnected;
                f = d8.localAnchorA.f16x;
                f2 = d8.localAnchorA.f17y;
                f3 = d8.localAnchorB.f16x;
                f4 = d8.localAnchorB.f17y;
                f5 = d8.referenceAngle;
                boolean z2 = d8.enableLimit;
                float f10 = d8.lowerAngle;
                float f11 = d8.upperAngle;
                return jniCreateRevoluteJoint(j, j2, j3, z, f, f2, f3, f4, f5, z2, f10, f11, d8.enableMotor, d8.motorSpeed, d8.maxMotorTorque);
            }
            jointDef2 = def;
            if (jointDef2.type == JointType.RopeJoint) {
                RopeJointDef d9 = (RopeJointDef) jointDef2;
                return jniCreateRopeJoint(this.addr, d9.bodyA.addr, d9.bodyB.addr, d9.collideConnected, d9.localAnchorA.f16x, d9.localAnchorA.f17y, d9.localAnchorB.f16x, d9.localAnchorB.f17y, d9.maxLength);
            }
            World world2 = this;
            if (jointDef2.type == JointType.WeldJoint) {
                WeldJointDef d10 = (WeldJointDef) jointDef2;
                j = world2.addr;
                j2 = d10.bodyA.addr;
                j3 = d10.bodyB.addr;
                z = d10.collideConnected;
                f = d10.localAnchorA.f16x;
                f2 = d10.localAnchorA.f17y;
                f3 = d10.localAnchorB.f16x;
                f4 = d10.localAnchorB.f17y;
                f5 = d10.referenceAngle;
                f6 = d10.frequencyHz;
                return jniCreateWeldJoint(j, j2, j3, z, f, f2, f3, f4, f5, f6, d10.dampingRatio);
            }
            jointDef2 = def;
            if (jointDef2.type != JointType.WheelJoint) {
                return 0;
            }
            WheelJointDef d11 = (WheelJointDef) jointDef2;
            j = this.addr;
            j2 = d11.bodyA.addr;
            j3 = d11.bodyB.addr;
            z = d11.collideConnected;
            f = d11.localAnchorA.f16x;
            f2 = d11.localAnchorA.f17y;
            f3 = d11.localAnchorB.f16x;
            f4 = d11.localAnchorB.f17y;
            f5 = d11.localAxisA.f16x;
            f6 = d11.localAxisA.f17y;
            boolean z3 = d11.enableMotor;
            float f12 = d11.maxMotorTorque;
            return jniCreateWheelJoint(j, j2, j3, z, f, f2, f3, f4, f5, f6, z3, f12, d11.motorSpeed, d11.frequencyHz, d11.dampingRatio);
        }
    }

    public void destroyJoint(Joint joint) {
        joint.setUserData(null);
        this.joints.remove(joint.addr);
        joint.jointEdgeA.other.joints.removeValue(joint.jointEdgeB, true);
        joint.jointEdgeB.other.joints.removeValue(joint.jointEdgeA, true);
        jniDestroyJoint(this.addr, joint.addr);
    }

    public void step(float timeStep, int velocityIterations, int positionIterations) {
        jniStep(this.addr, timeStep, velocityIterations, positionIterations);
    }

    public void clearForces() {
        jniClearForces(this.addr);
    }

    public void setWarmStarting(boolean flag) {
        jniSetWarmStarting(this.addr, flag);
    }

    public void setContinuousPhysics(boolean flag) {
        jniSetContiousPhysics(this.addr, flag);
    }

    public int getProxyCount() {
        return jniGetProxyCount(this.addr);
    }

    public int getBodyCount() {
        return jniGetBodyCount(this.addr);
    }

    public int getFixtureCount() {
        return this.fixtures.size;
    }

    public int getJointCount() {
        return jniGetJointcount(this.addr);
    }

    public int getContactCount() {
        return jniGetContactCount(this.addr);
    }

    public void setGravity(Vector2 gravity) {
        jniSetGravity(this.addr, gravity.f16x, gravity.f17y);
    }

    public Vector2 getGravity() {
        jniGetGravity(this.addr, this.tmpGravity);
        this.gravity.f16x = this.tmpGravity[0];
        this.gravity.f17y = this.tmpGravity[1];
        return this.gravity;
    }

    public boolean isLocked() {
        return jniIsLocked(this.addr);
    }

    public void setAutoClearForces(boolean flag) {
        jniSetAutoClearForces(this.addr, flag);
    }

    public boolean getAutoClearForces() {
        return jniGetAutoClearForces(this.addr);
    }

    public void QueryAABB(QueryCallback callback, float lowerX, float lowerY, float upperX, float upperY) {
        this.queryCallback = callback;
        jniQueryAABB(this.addr, lowerX, lowerY, upperX, upperY);
    }

    public Array<Contact> getContactList() {
        int numContacts = getContactCount();
        if (numContacts > this.contactAddrs.length) {
            int newSize = numContacts * 2;
            this.contactAddrs = new long[newSize];
            this.contacts.ensureCapacity(newSize);
            this.freeContacts.ensureCapacity(newSize);
        }
        int i = 0;
        if (numContacts > this.freeContacts.size) {
            newSize = this.freeContacts.size;
            for (int i2 = 0; i2 < numContacts - newSize; i2++) {
                this.freeContacts.add(new Contact(this, 0));
            }
        }
        jniGetContactList(this.addr, this.contactAddrs);
        this.contacts.clear();
        while (true) {
            newSize = i;
            if (newSize >= numContacts) {
                return this.contacts;
            }
            Contact contact = (Contact) this.freeContacts.get(newSize);
            contact.addr = this.contactAddrs[newSize];
            this.contacts.add(contact);
            i = newSize + 1;
        }
    }

    public void getBodies(Array<Body> bodies) {
        bodies.clear();
        bodies.ensureCapacity(this.bodies.size);
        Iterator<Body> iter = this.bodies.values();
        while (iter.hasNext()) {
            bodies.add(iter.next());
        }
    }

    public void getFixtures(Array<Fixture> fixtures) {
        fixtures.clear();
        fixtures.ensureCapacity(this.fixtures.size);
        Iterator<Fixture> iter = this.fixtures.values();
        while (iter.hasNext()) {
            fixtures.add(iter.next());
        }
    }

    public void getJoints(Array<Joint> joints) {
        joints.clear();
        joints.ensureCapacity(this.joints.size);
        Iterator<Joint> iter = this.joints.values();
        while (iter.hasNext()) {
            joints.add(iter.next());
        }
    }

    public void dispose() {
        jniDispose(this.addr);
    }

    private boolean contactFilter(long fixtureA, long fixtureB) {
        if (this.contactFilter != null) {
            return this.contactFilter.shouldCollide((Fixture) this.fixtures.get(fixtureA), (Fixture) this.fixtures.get(fixtureB));
        }
        Filter filterA = ((Fixture) this.fixtures.get(fixtureA)).getFilterData();
        Filter filterB = ((Fixture) this.fixtures.get(fixtureB)).getFilterData();
        boolean z = false;
        if (filterA.groupIndex != filterB.groupIndex || filterA.groupIndex == (short) 0) {
            if (!((filterA.maskBits & filterB.categoryBits) == 0 || (filterA.categoryBits & filterB.maskBits) == 0)) {
                z = true;
            }
            return z;
        }
        if (filterA.groupIndex > (short) 0) {
            z = true;
        }
        return z;
    }

    private void beginContact(long contactAddr) {
        this.contact.addr = contactAddr;
        if (this.contactListener != null) {
            this.contactListener.beginContact(this.contact);
        }
    }

    private void endContact(long contactAddr) {
        this.contact.addr = contactAddr;
        if (this.contactListener != null) {
            this.contactListener.endContact(this.contact);
        }
    }

    private void preSolve(long contactAddr, long manifoldAddr) {
        this.contact.addr = contactAddr;
        this.manifold.addr = manifoldAddr;
        if (this.contactListener != null) {
            this.contactListener.preSolve(this.contact, this.manifold);
        }
    }

    private void postSolve(long contactAddr, long impulseAddr) {
        this.contact.addr = contactAddr;
        this.impulse.addr = impulseAddr;
        if (this.contactListener != null) {
            this.contactListener.postSolve(this.contact, this.impulse);
        }
    }

    private boolean reportFixture(long addr) {
        if (this.queryCallback != null) {
            return this.queryCallback.reportFixture((Fixture) this.fixtures.get(addr));
        }
        return false;
    }

    public void rayCast(RayCastCallback callback, Vector2 point1, Vector2 point2) {
        this.rayCastCallback = callback;
        jniRayCast(this.addr, point1.f16x, point1.f17y, point2.f16x, point2.f17y);
    }

    private float reportRayFixture(long addr, float pX, float pY, float nX, float nY, float fraction) {
        if (this.rayCastCallback == null) {
            return 0.0f;
        }
        this.rayPoint.f16x = pX;
        this.rayPoint.f17y = pY;
        this.rayNormal.f16x = nX;
        this.rayNormal.f17y = nY;
        return this.rayCastCallback.reportRayFixture((Fixture) this.fixtures.get(addr), this.rayPoint, this.rayNormal, fraction);
    }
}
