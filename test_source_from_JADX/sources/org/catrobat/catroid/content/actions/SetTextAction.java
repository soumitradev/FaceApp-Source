package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.TextActor;

public class SetTextAction extends TemporalAction {
    private TextActor actor;
    private Formula endX;
    private Formula endY;
    private Sprite sprite;
    private Formula text;

    protected void begin() {
        try {
            this.actor = new TextActor(this.text.interpretString(this.sprite), this.endX.interpretInteger(this.sprite).intValue(), this.endY.interpretInteger(this.sprite).intValue());
            StageActivity.stageListener.addActor(this.actor);
        } catch (InterpretationException exception) {
            Log.e(getClass().getSimpleName(), Log.getStackTraceString(exception));
        }
    }

    protected void update(float percent) {
        try {
            String str = this.text.interpretString(this.sprite);
            int posX = this.endX.interpretInteger(this.sprite).intValue();
            int posY = this.endY.interpretInteger(this.sprite).intValue();
            this.actor.setText(str);
            this.actor.setPosX(posX);
            this.actor.setPosY(posY);
        } catch (InterpretationException exception) {
            Log.e(getClass().getSimpleName(), Log.getStackTraceString(exception));
        }
    }

    public void setPosition(Formula x, Formula y) {
        this.endX = x;
        this.endY = y;
    }

    public void setText(Formula text) {
        this.text = text;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
