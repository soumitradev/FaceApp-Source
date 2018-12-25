package org.catrobat.catroid.physics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import java.util.Iterator;
import java.util.LinkedList;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;

public class PhysicsLook extends Look {
    public static final float SCALE_FACTOR_ACCURACY = 10000.0f;
    private final PhysicsObject physicsObject;
    private final PhysicsObjectStateHandler physicsObjectStateHandler = new PhysicsObjectStateHandler();

    private interface PhysicsObjectStateCondition {
        boolean isTrue();
    }

    private class PhysicsObjectStateHandler {
        private LinkedList<PhysicsObjectStateCondition> fixConditions = new LinkedList();
        private boolean fixed = false;
        private PhysicsObjectStateCondition glideToCondition;
        private boolean glideToIsActive = false;
        private boolean hangedUp = false;
        private LinkedList<PhysicsObjectStateCondition> hangupConditions = new LinkedList();
        private boolean nonColliding = false;
        private LinkedList<PhysicsObjectStateCondition> nonCollidingConditions = new LinkedList();
        private PhysicsObjectStateCondition positionCondition;
        private PhysicsObjectStateCondition transparencyCondition;
        private PhysicsObjectStateCondition visibleCondition;

        PhysicsObjectStateHandler() {
            this.positionCondition = new PhysicsObjectStateCondition(PhysicsLook.this) {
                public boolean isTrue() {
                    return isOutsideActiveArea();
                }

                private boolean isOutsideActiveArea() {
                    if (!isXOutsideActiveArea()) {
                        if (!isYOutsideActiveArea()) {
                            return false;
                        }
                    }
                    return true;
                }

                private boolean isXOutsideActiveArea() {
                    return Math.abs(PhysicsWorldConverter.convertBox2dToNormalCoordinate(PhysicsLook.this.physicsObject.getMassCenter().f16x)) - PhysicsLook.this.physicsObject.getCircumference() > PhysicsWorld.activeArea.f16x / 2.0f;
                }

                private boolean isYOutsideActiveArea() {
                    return Math.abs(PhysicsWorldConverter.convertBox2dToNormalCoordinate(PhysicsLook.this.physicsObject.getMassCenter().f17y)) - PhysicsLook.this.physicsObject.getCircumference() > PhysicsWorld.activeArea.f17y / 2.0f;
                }
            };
            this.visibleCondition = new PhysicsObjectStateCondition(PhysicsLook.this) {
                public boolean isTrue() {
                    return PhysicsLook.this.isLookVisible() ^ 1;
                }
            };
            this.transparencyCondition = new PhysicsObjectStateCondition(PhysicsLook.this) {
                public boolean isTrue() {
                    return ((double) PhysicsLook.this.alpha) == BrickValues.SET_COLOR_TO;
                }
            };
            this.glideToCondition = new PhysicsObjectStateCondition(PhysicsLook.this) {
                public boolean isTrue() {
                    return PhysicsObjectStateHandler.this.glideToIsActive;
                }
            };
            this.hangupConditions.add(this.transparencyCondition);
            this.hangupConditions.add(this.positionCondition);
            this.hangupConditions.add(this.visibleCondition);
            this.hangupConditions.add(this.glideToCondition);
            this.nonCollidingConditions.add(this.transparencyCondition);
            this.nonCollidingConditions.add(this.positionCondition);
            this.nonCollidingConditions.add(this.visibleCondition);
            this.fixConditions.add(this.glideToCondition);
        }

        private boolean checkHangup(boolean record) {
            boolean shouldBeHangedUp = false;
            Iterator it = this.hangupConditions.iterator();
            while (it.hasNext()) {
                if (((PhysicsObjectStateCondition) it.next()).isTrue()) {
                    shouldBeHangedUp = true;
                    break;
                }
            }
            boolean activateHangup = false;
            boolean deactivateHangup = this.hangedUp && !shouldBeHangedUp;
            if (!this.hangedUp && shouldBeHangedUp) {
                activateHangup = true;
            }
            if (deactivateHangup) {
                PhysicsLook.this.physicsObject.deactivateHangup(record);
            } else if (activateHangup) {
                PhysicsLook.this.physicsObject.activateHangup();
            }
            this.hangedUp = shouldBeHangedUp;
            return this.hangedUp;
        }

        private boolean checkNonColliding(boolean record) {
            boolean shouldBeNonColliding = false;
            Iterator it = this.nonCollidingConditions.iterator();
            while (it.hasNext()) {
                if (((PhysicsObjectStateCondition) it.next()).isTrue()) {
                    shouldBeNonColliding = true;
                    break;
                }
            }
            boolean activateNonColliding = true;
            boolean deactivateNonColliding = this.nonColliding && !shouldBeNonColliding;
            if (this.nonColliding || !shouldBeNonColliding) {
                activateNonColliding = false;
            }
            if (deactivateNonColliding) {
                PhysicsLook.this.physicsObject.deactivateNonColliding(record, false);
            } else if (activateNonColliding) {
                PhysicsLook.this.physicsObject.activateNonColliding(false);
            }
            this.nonColliding = shouldBeNonColliding;
            return this.nonColliding;
        }

        private boolean checkFixed(boolean record) {
            boolean shouldBeFixed = false;
            Iterator it = this.fixConditions.iterator();
            while (it.hasNext()) {
                if (((PhysicsObjectStateCondition) it.next()).isTrue()) {
                    shouldBeFixed = true;
                    break;
                }
            }
            boolean activateFix = false;
            boolean deactivateFix = this.fixed && !shouldBeFixed;
            if (!this.fixed && shouldBeFixed) {
                activateFix = true;
            }
            if (deactivateFix) {
                PhysicsLook.this.physicsObject.deactivateFixed(record);
            } else if (activateFix) {
                PhysicsLook.this.physicsObject.activateFixed();
            }
            this.fixed = shouldBeFixed;
            return this.fixed;
        }

        public void update(boolean record) {
            checkHangup(record);
            checkNonColliding(record);
            checkFixed(record);
        }

        public void activateGlideTo() {
            if (!this.glideToIsActive) {
                this.glideToIsActive = true;
                PhysicsLook.this.updatePhysicsObjectState(true);
            }
        }

        public void deactivateGlideTo() {
            this.glideToIsActive = false;
            PhysicsLook.this.updatePhysicsObjectState(true);
        }

        public boolean isHangedUp() {
            return this.hangedUp;
        }

        public void setNonColliding(boolean nonColliding) {
            if (this.nonColliding != nonColliding) {
                this.nonColliding = nonColliding;
                update(true);
            }
        }
    }

    public PhysicsLook(Sprite sprite, PhysicsWorld physicsWorld) {
        super(sprite);
        this.physicsObject = physicsWorld.getPhysicsObject(sprite);
    }

    public void copyTo(Look destination) {
        super.copyTo(destination);
        if (destination instanceof PhysicsLook) {
            this.physicsObject.copyTo(((PhysicsLook) destination).physicsObject);
        }
    }

    public void setTransparencyInUserInterfaceDimensionUnit(float percent) {
        super.setTransparencyInUserInterfaceDimensionUnit(percent);
        updatePhysicsObjectState(true);
    }

    public void setLookData(LookData lookData) {
        super.setLookData(lookData);
        ProjectManager.getInstance().getCurrentlyPlayingScene().getPhysicsWorld().changeLook(this.physicsObject, this);
        updatePhysicsObjectState(true);
    }

    public void setXInUserInterfaceDimensionUnit(float x) {
        setX(x - (getWidth() / 2.0f));
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (this.physicsObject != null) {
            this.physicsObject.setX((getWidth() / 2.0f) + x);
            this.physicsObject.setY((getHeight() / 2.0f) + y);
        }
    }

    public void setX(float x) {
        super.setX(x);
        if (this.physicsObject != null) {
            this.physicsObject.setX((getWidth() / 2.0f) + x);
        }
    }

    public void setY(float y) {
        super.setY(y);
        if (this.physicsObject != null) {
            this.physicsObject.setY((getHeight() / 2.0f) + y);
        }
    }

    public float getAngularVelocityInUserInterfaceDimensionUnit() {
        return this.physicsObject.getRotationSpeed();
    }

    public float getXVelocityInUserInterfaceDimensionUnit() {
        return this.physicsObject.getVelocity().f16x;
    }

    public float getYVelocityInUserInterfaceDimensionUnit() {
        return this.physicsObject.getVelocity().f17y;
    }

    public float getX() {
        float x = this.physicsObject.getX() - (getWidth() / 2.0f);
        super.setX(x);
        return x;
    }

    public float getY() {
        float y = this.physicsObject.getY() - (getHeight() / 2.0f);
        super.setY(y);
        return y;
    }

    public float getRotation() {
        super.setRotation(this.physicsObject.getDirection() % 360.0f);
        float rotation = super.getRotation();
        float realRotation = this.physicsObject.getDirection() % 360.0f;
        if (realRotation < 0.0f) {
            realRotation += 360.0f;
        }
        switch (super.getRotationMode()) {
            case 0:
                boolean orientedRight;
                boolean orientedLeft;
                super.setRotation(0.0f);
                if (realRotation <= 180.0f) {
                    if (realRotation != 0.0f) {
                        orientedRight = false;
                        orientedLeft = realRotation > 180.0f && realRotation != 0.0f;
                        if (((isFlipped() && orientedRight) || (!isFlipped() && orientedLeft)) && this.lookData != null) {
                            this.lookData.getTextureRegion().flip(true, false);
                            break;
                        }
                    }
                }
                orientedRight = true;
                if (realRotation > 180.0f) {
                    break;
                }
                this.lookData.getTextureRegion().flip(true, false);
                break;
            case 1:
                super.setRotation(rotation);
                break;
            case 2:
                super.setRotation(0.0f);
                break;
            default:
                break;
        }
        return super.getRotation();
    }

    public void setRotation(float degrees) {
        super.setRotation(degrees);
        if (this.physicsObject != null) {
            this.physicsObject.setDirection(super.getRotation() % 360.0f);
        }
    }

    public void setScale(float scaleX, float scaleY) {
        Vector2 oldScales = new Vector2(getScaleX(), getScaleY());
        if (scaleX < 0.0f || scaleY < 0.0f) {
            scaleX = 0.0f;
            scaleY = 0.0f;
        }
        int scaleXComp = Math.round(scaleX * 1176256512);
        int scaleYComp = Math.round(scaleY * 1176256512);
        if (scaleXComp != Math.round(oldScales.f16x * 10000.0f) || scaleYComp != Math.round(oldScales.f17y * 10000.0f)) {
            super.setScale(scaleX, scaleY);
            if (this.physicsObject != null) {
                ProjectManager.getInstance().getCurrentlyPlayingScene().getPhysicsWorld().changeLook(this.physicsObject, this);
                updatePhysicsObjectState(true);
            }
        }
    }

    public void updatePhysicsObjectState(boolean record) {
        this.physicsObjectStateHandler.update(record);
    }

    public void setLookVisible(boolean visible) {
        super.setLookVisible(visible);
        this.physicsObjectStateHandler.update(true);
    }

    public boolean isHangedUp() {
        return this.physicsObjectStateHandler.isHangedUp();
    }

    public void setNonColliding(boolean nonColliding) {
        this.physicsObjectStateHandler.setNonColliding(nonColliding);
    }

    public void startGlide() {
        this.physicsObjectStateHandler.activateGlideTo();
    }

    public void stopGlide() {
        this.physicsObjectStateHandler.deactivateGlideTo();
    }

    public void draw(Batch batch, float parentAlpha) {
        this.physicsObjectStateHandler.checkHangup(true);
        super.draw(batch, parentAlpha);
    }
}
