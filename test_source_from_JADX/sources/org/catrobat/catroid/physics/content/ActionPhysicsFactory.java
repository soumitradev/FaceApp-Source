package org.catrobat.catroid.physics.content;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.physics.PhysicsLook;
import org.catrobat.catroid.physics.PhysicsObject;
import org.catrobat.catroid.physics.PhysicsObject.Type;
import org.catrobat.catroid.physics.PhysicsWorld;
import org.catrobat.catroid.physics.content.actions.GlideToPhysicsAction;
import org.catrobat.catroid.physics.content.actions.IfOnEdgeBouncePhysicsAction;
import org.catrobat.catroid.physics.content.actions.SetBounceFactorAction;
import org.catrobat.catroid.physics.content.actions.SetFrictionAction;
import org.catrobat.catroid.physics.content.actions.SetGravityAction;
import org.catrobat.catroid.physics.content.actions.SetMassAction;
import org.catrobat.catroid.physics.content.actions.SetPhysicsObjectTypeAction;
import org.catrobat.catroid.physics.content.actions.SetVelocityAction;
import org.catrobat.catroid.physics.content.actions.TurnLeftSpeedAction;
import org.catrobat.catroid.physics.content.actions.TurnRightSpeedAction;

public class ActionPhysicsFactory extends ActionFactory {
    private PhysicsObject getPhysicsObject(Sprite sprite) {
        return getPhysicsWorld().getPhysicsObject(sprite);
    }

    private PhysicsWorld getPhysicsWorld() {
        return ProjectManager.getInstance().getCurrentlyPlayingScene().getPhysicsWorld();
    }

    public Action createIfOnEdgeBounceAction(Sprite sprite) {
        IfOnEdgeBouncePhysicsAction action = (IfOnEdgeBouncePhysicsAction) Actions.action(IfOnEdgeBouncePhysicsAction.class);
        action.setSprite(sprite);
        action.setPhysicsWorld(getPhysicsWorld());
        return action;
    }

    public Action createGlideToAction(Sprite sprite, Formula x, Formula y, Formula duration) {
        GlideToPhysicsAction action = (GlideToPhysicsAction) Actions.action(GlideToPhysicsAction.class);
        action.setPosition(x, y);
        action.setDuration(duration);
        action.setSprite(sprite);
        action.setPhysicsLook((PhysicsLook) sprite.look);
        return action;
    }

    public Action createSetBounceFactorAction(Sprite sprite, Formula bounceFactor) {
        SetBounceFactorAction action = (SetBounceFactorAction) Actions.action(SetBounceFactorAction.class);
        action.setSprite(sprite);
        action.setPhysicsObject(getPhysicsObject(sprite));
        action.setBounceFactor(bounceFactor);
        return action;
    }

    public Action createSetFrictionAction(Sprite sprite, Formula friction) {
        SetFrictionAction action = (SetFrictionAction) Actions.action(SetFrictionAction.class);
        action.setSprite(sprite);
        action.setPhysicsObject(getPhysicsObject(sprite));
        action.setFriction(friction);
        return action;
    }

    public Action createSetGravityAction(Sprite sprite, Formula gravityX, Formula gravityY) {
        SetGravityAction action = (SetGravityAction) Actions.action(SetGravityAction.class);
        action.setSprite(sprite);
        action.setPhysicsWorld(getPhysicsWorld());
        action.setGravity(gravityX, gravityY);
        return action;
    }

    public Action createSetMassAction(Sprite sprite, Formula mass) {
        SetMassAction action = (SetMassAction) Actions.action(SetMassAction.class);
        action.setSprite(sprite);
        action.setPhysicsObject(getPhysicsObject(sprite));
        action.setMass(mass);
        return action;
    }

    public Action createSetPhysicsObjectTypeAction(Sprite sprite, Type type) {
        SetPhysicsObjectTypeAction action = (SetPhysicsObjectTypeAction) Actions.action(SetPhysicsObjectTypeAction.class);
        action.setPhysicsObject(getPhysicsObject(sprite));
        action.setType(type);
        return action;
    }

    public Action createSetVelocityAction(Sprite sprite, Formula velocityX, Formula velocityY) {
        SetVelocityAction action = (SetVelocityAction) Actions.action(SetVelocityAction.class);
        action.setSprite(sprite);
        action.setPhysicsObject(getPhysicsObject(sprite));
        action.setVelocity(velocityX, velocityY);
        return action;
    }

    public Action createTurnLeftSpeedAction(Sprite sprite, Formula speed) {
        TurnLeftSpeedAction action = (TurnLeftSpeedAction) Actions.action(TurnLeftSpeedAction.class);
        action.setSprite(sprite);
        action.setPhysicsObject(getPhysicsObject(sprite));
        action.setSpeed(speed);
        return action;
    }

    public Action createTurnRightSpeedAction(Sprite sprite, Formula speed) {
        TurnRightSpeedAction action = (TurnRightSpeedAction) Actions.action(TurnRightSpeedAction.class);
        action.setSprite(sprite);
        action.setPhysicsObject(getPhysicsObject(sprite));
        action.setSpeed(speed);
        return action;
    }
}
