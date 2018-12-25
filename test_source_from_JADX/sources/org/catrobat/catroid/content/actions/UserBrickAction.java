package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.DelegateAction;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.bricks.UserBrick;

public class UserBrickAction extends DelegateAction {
    private UserBrick userBrick;

    public boolean delegate(float delta) {
        ProjectManager.getInstance().setCurrentUserBrick(this.userBrick);
        return this.action.act(delta);
    }

    public void setUserBrick(UserBrick userBrick) {
        this.userBrick = userBrick;
    }
}
