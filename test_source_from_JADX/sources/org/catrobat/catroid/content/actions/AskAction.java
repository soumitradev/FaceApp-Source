package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.util.ArrayList;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;

public class AskAction extends Action {
    private boolean answerReceived = false;
    private UserVariable answerVariable;
    private boolean questionAsked = false;
    private Formula questionFormula;
    private Sprite sprite;

    private void askQuestion() {
        if (StageActivity.messageHandler != null) {
            String question = "";
            try {
                if (this.questionFormula != null) {
                    question = this.questionFormula.interpretString(this.sprite);
                }
            } catch (InterpretationException e) {
                Log.e(getClass().getSimpleName(), "formula interpretation in ask brick failed");
            }
            ArrayList<Object> params = new ArrayList();
            params.add(this);
            params.add(question);
            StageActivity.messageHandler.obtainMessage(0, params).sendToTarget();
            this.questionAsked = true;
        }
    }

    public void setAnswerText(String answer) {
        if (this.answerVariable != null) {
            this.answerVariable.setValue(answer);
            this.answerReceived = true;
        }
    }

    public void setAnswerVariable(UserVariable answerVariable) {
        if (answerVariable != null) {
            this.answerVariable = answerVariable;
        }
    }

    public void setQuestionFormula(Formula questionFormula) {
        this.questionFormula = questionFormula;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public boolean act(float delta) {
        if (!this.questionAsked) {
            askQuestion();
        }
        return this.answerReceived;
    }

    public void restart() {
        this.questionAsked = false;
        this.answerReceived = false;
        super.restart();
    }
}
