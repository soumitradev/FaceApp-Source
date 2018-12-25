package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.stage.ShowBubbleActor;
import org.catrobat.catroid.stage.StageActivity;

public class ThinkSayBubbleAction extends TemporalAction {
    private static final String TAG = ThinkSayBubbleAction.class.getSimpleName();
    private Sprite sprite;
    private Formula text;
    private int type;

    protected void update(float delta) {
        try {
            ShowBubbleActor showBubbleActor = createBubbleActor();
            if (StageActivity.stageListener.getBubbleActorForSprite(this.sprite) != null) {
                StageActivity.stageListener.removeBubbleActorForSprite(this.sprite);
            }
            if (showBubbleActor != null) {
                StageActivity.stageListener.setBubbleActorForSprite(this.sprite, showBubbleActor);
            }
        } catch (InterpretationException e) {
            Log.d(TAG, "Failed to create Bubble Actor", e);
        }
    }

    public ShowBubbleActor createBubbleActor() throws InterpretationException {
        String textToDisplay = this.text == null ? "" : this.text.interpretString(this.sprite);
        if (textToDisplay.isEmpty()) {
            return null;
        }
        return new ShowBubbleActor(textToDisplay, this.sprite, this.type);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setText(Formula text) {
        this.text = text;
    }

    public void setType(int type) {
        this.type = type;
    }
}
