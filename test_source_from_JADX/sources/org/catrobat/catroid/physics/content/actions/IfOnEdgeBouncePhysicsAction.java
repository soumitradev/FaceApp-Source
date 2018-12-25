package org.catrobat.catroid.physics.content.actions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.physics.PhysicsBoundaryBox.BoundaryBoxIdentifier;
import org.catrobat.catroid.physics.PhysicsObject;
import org.catrobat.catroid.physics.PhysicsWorld;

public class IfOnEdgeBouncePhysicsAction extends TemporalAction {
    private static final float COLLISION_OVERLAP_RANGE_FACTOR = 0.9f;
    public static final float THRESHOLD_VELOCITY_TO_ACTIVATE_BOUNCE = 10.0f;
    private PhysicsWorld physicsWorld;
    private Sprite sprite;

    private void performVerticalRepositioning(float bbLookOffsetX, boolean velocityHighEnoughToCollideAfterRepositioning, boolean correctGravityPresent) {
        this.sprite.look.setXInUserInterfaceDimensionUnit(this.sprite.look.getXInUserInterfaceDimensionUnit() + bbLookOffsetX);
        checkBounceActivation(correctGravityPresent, velocityHighEnoughToCollideAfterRepositioning, this.sprite, BoundaryBoxIdentifier.BBI_VERTICAL);
    }

    private void performHorizontalRepositioning(float bbLookOffsetY, boolean velocityHighEnoughToCollideAfterRepositioning, boolean correctGravityPresent) {
        this.sprite.look.setYInUserInterfaceDimensionUnit(this.sprite.look.getYInUserInterfaceDimensionUnit() + bbLookOffsetY);
        checkBounceActivation(correctGravityPresent, velocityHighEnoughToCollideAfterRepositioning, this.sprite, BoundaryBoxIdentifier.BBI_HORIZONTAL);
    }

    private void checkBounceActivation(boolean correctGravityPresent, boolean velocityHighEnoughToCollideAfterRepositioning, Sprite sprite, BoundaryBoxIdentifier boundaryBoxIdentifier) {
        if (velocityHighEnoughToCollideAfterRepositioning || correctGravityPresent) {
            this.physicsWorld.setBounceOnce(sprite, boundaryBoxIdentifier);
        }
    }

    protected void update(float percent) {
        Vector2 bbLowerEdge = new Vector2();
        Vector2 bbUpperEdge = new Vector2();
        PhysicsObject physicsObject = this.physicsWorld.getPhysicsObject(this.sprite);
        physicsObject.getBoundaryBox(bbLowerEdge, bbUpperEdge);
        float bbWidth = bbUpperEdge.f16x - bbLowerEdge.f16x;
        float bbHeight = bbUpperEdge.f17y - bbLowerEdge.f17y;
        float bbCenterX = bbLowerEdge.f16x + (bbWidth / 2.0f);
        float bbCenterY = bbLowerEdge.f17y + (bbHeight / 2.0f);
        int vsWidth = ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenWidth;
        int vsHeight = ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenHeight;
        float leftCollisionAreaInnerBorder = (((float) (-vsWidth)) / 2.0f) + (bbWidth / 2.0f);
        float leftCollisionAreaOuterBorder = ((-bbWidth) * COLLISION_OVERLAP_RANGE_FACTOR) + leftCollisionAreaInnerBorder;
        boolean z = false;
        boolean leftVelocityHighEnoughToCollideAfterRepositioning = physicsObject.getVelocity().f16x <= -10.0f;
        boolean leftGravityPresent = r0.physicsWorld.getGravity().f16x < 0.0f;
        float rightCollisionAreaInnerBorder = (((float) vsWidth) / 2.0f) - (bbWidth / 2.0f);
        float rightCollisionAreaOuterBorder = rightCollisionAreaInnerBorder + (bbWidth * COLLISION_OVERLAP_RANGE_FACTOR);
        boolean rightVelocityHighEnoughToCollideAfterRepositioning = physicsObject.getVelocity().f16x >= 10.0f;
        boolean rightGravityPresent = r0.physicsWorld.getGravity().f16x > 0.0f;
        if (leftCollisionAreaOuterBorder >= bbCenterX || bbCenterX >= leftCollisionAreaInnerBorder) {
            if (rightCollisionAreaOuterBorder <= bbCenterX || bbCenterX <= rightCollisionAreaInnerBorder) {
            } else {
                performVerticalRepositioning(-Math.abs((bbCenterX + (bbWidth / 2.0f)) - (((float) vsWidth) / 2.0f)), rightVelocityHighEnoughToCollideAfterRepositioning, rightGravityPresent);
            }
        } else {
            performVerticalRepositioning(Math.abs((bbCenterX - (bbWidth / 2.0f)) + (((float) vsWidth) / 1073741824)), leftVelocityHighEnoughToCollideAfterRepositioning, leftGravityPresent);
            float f = bbWidth;
        }
        float bottomCollisionAreaInnerBorder = (((float) (-vsHeight)) / 2.0f) + (bbHeight / 2.0f);
        bbWidth = ((-bbHeight) * COLLISION_OVERLAP_RANGE_FACTOR) + bottomCollisionAreaInnerBorder;
        rightGravityPresent = physicsObject.getVelocity().f17y <= -10.0f;
        boolean bottomGravityPresent = r0.physicsWorld.getGravity().f17y < 0.0f;
        float topCollisionAreaInnerBorder = (((float) vsHeight) / 2.0f) - (bbHeight / 2.0f);
        float topCollisionAreaOuterBorder = topCollisionAreaInnerBorder + (COLLISION_OVERLAP_RANGE_FACTOR * bbHeight);
        boolean topVelocityHighEnoughToCollideAfterRepositioning = physicsObject.getVelocity().f17y >= 10.0f;
        if (r0.physicsWorld.getGravity().f17y > 0.0f) {
            z = true;
        }
        boolean topGravityPresent = z;
        if (bbWidth >= bbCenterY || bbCenterY >= bottomCollisionAreaInnerBorder) {
            if (topCollisionAreaOuterBorder <= bbCenterY || bbCenterY <= topCollisionAreaInnerBorder) {
                return;
            }
            performHorizontalRepositioning(-Math.abs((bbCenterY + (bbHeight / 2.0f)) - (((float) vsHeight) / true)), topVelocityHighEnoughToCollideAfterRepositioning, topGravityPresent);
            return;
        }
        performHorizontalRepositioning(Math.abs((bbCenterY - (bbHeight / 2.0f)) + (((float) vsHeight) / 2.0f)), rightGravityPresent, bottomGravityPresent);
        boolean z2 = rightGravityPresent;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setPhysicsWorld(PhysicsWorld physicsWorld) {
        this.physicsWorld = physicsWorld;
    }
}
