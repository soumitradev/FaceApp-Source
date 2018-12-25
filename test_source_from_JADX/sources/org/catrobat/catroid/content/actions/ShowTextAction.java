package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.UserBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.ShowTextActor;
import org.catrobat.catroid.stage.StageActivity;

public class ShowTextAction extends TemporalAction {
    public static final String TAG = ShowTextAction.class.getSimpleName();
    private ShowTextActor actor;
    private Sprite sprite;
    private UserBrick userBrick;
    private UserVariable variableToShow;
    private Formula xPosition;
    private Formula yPosition;

    protected void begin() {
        try {
            int xPosition = this.xPosition.interpretInteger(this.sprite).intValue();
            int yPosition = this.yPosition.interpretInteger(this.sprite).intValue();
            if (StageActivity.stageListener != null) {
                Array<Actor> stageActors = StageActivity.stageListener.getStage().getActors();
                ShowTextActor dummyActor = new ShowTextActor(new UserVariable("dummyActor"), 0, 0, this.sprite, this.userBrick);
                Iterator it = stageActors.iterator();
                while (it.hasNext()) {
                    Actor actor = (Actor) it.next();
                    if (actor.getClass().equals(dummyActor.getClass())) {
                        ShowTextActor showTextActor = (ShowTextActor) actor;
                        if (showTextActor.getVariableNameToCompare().equals(this.variableToShow.getName()) && showTextActor.getSprite().equals(this.sprite) && (this.userBrick == null || showTextActor.getUserBrick().equals(this.userBrick))) {
                            actor.remove();
                        }
                    }
                }
                this.actor = new ShowTextActor(this.variableToShow, xPosition, yPosition, this.sprite, this.userBrick);
                StageActivity.stageListener.addActor(this.actor);
            }
            this.variableToShow.setVisible(true);
        } catch (InterpretationException e) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("InterpretationException: ");
            stringBuilder.append(e);
            Log.d(str, stringBuilder.toString());
        }
    }

    protected void update(float percent) {
        try {
            int xPosition = this.xPosition.interpretInteger(this.sprite).intValue();
            int yPosition = this.yPosition.interpretInteger(this.sprite).intValue();
            if (this.actor != null) {
                this.actor.setPositionX(xPosition);
                this.actor.setPositionY(yPosition);
            }
        } catch (InterpretationException e) {
            Log.d(TAG, "InterpretationException");
        }
    }

    public void setPosition(Formula xPosition, Formula yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setVariableToShow(UserVariable userVariable) {
        this.variableToShow = userVariable;
    }

    public void setUserBrick(UserBrick userBrick) {
        this.userBrick = userBrick;
    }
}
