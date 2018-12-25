package org.catrobat.catroid.content.actions;

import android.content.Intent;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.util.ArrayList;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageActivity.IntentListener;

public class AskSpeechAction extends Action implements IntentListener {
    private static final String TAG = "AskSpeechAction";
    private boolean answerReceived = false;
    private UserVariable answerVariable;
    private boolean questionAsked = false;
    private Formula questionFormula;
    private Sprite sprite;

    private void askQuestion() {
        if (StageActivity.messageHandler != null) {
            ArrayList<Object> params = new ArrayList();
            params.add(this);
            StageActivity.messageHandler.obtainMessage(1, params).sendToTarget();
            this.questionAsked = true;
        }
    }

    private Intent createRecognitionIntent(String question) {
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        if (!(question == null || question.length() == 0)) {
            intent.putExtra("android.speech.extra.PROMPT", question);
        }
        return intent;
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

    public Intent getTargetIntent() {
        String question = "";
        try {
            if (this.questionFormula != null) {
                question = this.questionFormula.interpretString(this.sprite);
            }
        } catch (InterpretationException e) {
            Log.e(getClass().getSimpleName(), "formula interpretation in ask brick failed");
        }
        return createRecognitionIntent(question);
    }

    public void onIntentResult(int resultCode, Intent data) {
        switch (resultCode) {
            case -1:
                ArrayList<String> matches = data.getStringArrayListExtra("android.speech.extra.RESULTS");
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Results Speechrecognition: ");
                stringBuilder.append(matches.toString());
                Log.d(str, stringBuilder.toString());
                if (matches == null || matches.size() <= 0) {
                    setAnswerText("");
                    return;
                } else {
                    setAnswerText((String) matches.get(0));
                    return;
                }
            case 0:
            case 1:
                setAnswerText("");
                return;
            default:
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("unhandeld speech recognizer resultCode ");
                stringBuilder2.append(resultCode);
                Log.e(str2, stringBuilder2.toString());
                return;
        }
    }
}
