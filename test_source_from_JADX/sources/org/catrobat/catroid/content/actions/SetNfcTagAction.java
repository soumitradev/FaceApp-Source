package org.catrobat.catroid.content.actions;

import android.nfc.NdefMessage;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.nfc.NfcHandler;
import org.catrobat.catroid.stage.StageActivity;

public class SetNfcTagAction extends Action {
    private static final String TAG = SetNfcTagAction.class.getSimpleName();
    private boolean firstExecution = true;
    private NdefMessage message;
    private Formula nfcNdefMessage;
    private int nfcTagNdefSpinnerSelection;
    private Sprite sprite;

    public void setNfcTagNdefSpinnerSelection(int spinnerSelection) {
        this.nfcTagNdefSpinnerSelection = spinnerSelection;
    }

    public void setNfcNdefMessage(Formula nfcNdefMessage) {
        this.nfcNdefMessage = nfcNdefMessage;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void restart() {
        super.restart();
        this.firstExecution = true;
    }

    public boolean act(float delta) {
        boolean z = true;
        if (this.nfcNdefMessage == null) {
            return true;
        }
        if (this.firstExecution) {
            try {
                this.message = NfcHandler.createMessage(this.nfcNdefMessage.interpretString(this.sprite), this.nfcTagNdefSpinnerSelection);
                synchronized (StageActivity.class) {
                    StageActivity.setNfcTagMessage(this.message);
                }
                this.firstExecution = false;
            } catch (Exception e) {
                Log.d(TAG, "No new message was added to the Stage", e);
                return true;
            }
        }
        if (StageActivity.getNfcTagMessage() == this.message) {
            z = false;
        }
        return z;
    }
}
