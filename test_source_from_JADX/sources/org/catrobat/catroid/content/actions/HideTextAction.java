package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.UserBrick;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.ShowTextActor;
import org.catrobat.catroid.stage.StageActivity;

public class HideTextAction extends TemporalAction {
    private Sprite sprite;
    private UserBrick userBrick;
    private UserVariable variableToHide;

    protected void begin() {
        if (StageActivity.stageListener != null) {
            Array<Actor> stageActors = StageActivity.stageListener.getStage().getActors();
            ShowTextActor dummyActor = new ShowTextActor(new UserVariable("dummyActor"), 0, 0, this.sprite, this.userBrick);
            Iterator it = stageActors.iterator();
            while (it.hasNext()) {
                Actor actor = (Actor) it.next();
                if (actor.getClass().equals(dummyActor.getClass())) {
                    ShowTextActor showTextActor = (ShowTextActor) actor;
                    if (showTextActor.getVariableNameToCompare().equals(this.variableToHide.getName()) && showTextActor.getSprite().equals(this.sprite) && (this.userBrick == null || showTextActor.getUserBrick().equals(this.userBrick))) {
                        actor.remove();
                    }
                }
            }
        }
        this.variableToHide.setVisible(false);
    }

    protected void update(float percent) {
    }

    public void setVariableToHide(UserVariable userVariable) {
        this.variableToHide = userVariable;
    }

    public void setUserBrick(UserBrick userBrick) {
        this.userBrick = userBrick;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
