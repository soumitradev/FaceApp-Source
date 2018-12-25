package org.catrobat.catroid.content.actions;

import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.util.HashMap;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.io.SoundManager;
import org.catrobat.catroid.stage.PreStageActivity;
import org.catrobat.catroid.utils.Utils;

public class SpeakAction extends TemporalAction {
    private boolean determineLength = false;
    private String hashText;
    private Object interpretedText;
    private float lengthOfText;
    private OnUtteranceCompletedListener listener;
    private File speechFile;
    private Sprite sprite;
    private Formula text;

    /* renamed from: org.catrobat.catroid.content.actions.SpeakAction$1 */
    class C17741 implements OnUtteranceCompletedListener {
        C17741() {
        }

        public void onUtteranceCompleted(String utteranceId) {
            if (SpeakAction.this.determineLength) {
                SpeakAction.this.lengthOfText = SoundManager.getInstance().getDurationOfSoundFile(SpeakAction.this.speechFile.getAbsolutePath());
            } else {
                SoundManager.getInstance().playSoundFile(SpeakAction.this.speechFile.getAbsolutePath());
            }
        }
    }

    protected void begin() {
        try {
            this.interpretedText = this.text == null ? "" : this.text.interpretString(this.sprite);
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            this.interpretedText = "";
        }
        boolean isFirstLevelStringTree = false;
        if (this.text != null && this.text.getRoot().getElementType() == ElementType.STRING) {
            isFirstLevelStringTree = true;
        }
        if (!isFirstLevelStringTree) {
            try {
                if ((this.interpretedText instanceof String) && Double.valueOf((String) this.interpretedText).isNaN()) {
                    this.interpretedText = "";
                }
            } catch (NumberFormatException numberFormatException) {
                Log.d(getClass().getSimpleName(), "Couldn't parse String", numberFormatException);
            }
        }
        this.hashText = Utils.md5Checksum(String.valueOf(this.interpretedText));
        String fileName = this.hashText;
        File pathToSpeechFile = new File(Constants.TEXT_TO_SPEECH_TMP_PATH);
        pathToSpeechFile.mkdirs();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fileName);
        stringBuilder.append(Constants.DEFAULT_SOUND_EXTENSION);
        this.speechFile = new File(pathToSpeechFile, stringBuilder.toString());
        this.listener = new C17741();
        super.begin();
    }

    protected void update(float delta) {
        HashMap<String, String> speakParameter = new HashMap();
        speakParameter.put("utteranceId", this.hashText);
        PreStageActivity.textToSpeech(String.valueOf(this.interpretedText), this.speechFile, this.listener, speakParameter);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setText(Formula text) {
        this.text = text;
    }

    public float getLengthOfText() {
        return this.lengthOfText;
    }

    public void setDetermineLength(boolean getDurationOfText) {
        this.determineLength = getDurationOfText;
    }
}
