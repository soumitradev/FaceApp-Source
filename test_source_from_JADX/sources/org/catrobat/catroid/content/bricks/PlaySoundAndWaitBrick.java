package org.catrobat.catroid.content.bricks;

import android.media.MediaMetadataRetriever;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class PlaySoundAndWaitBrick extends PlaySoundBrick {
    private static final long serialVersionUID = 1;

    protected void onViewCreated(View prototypeView) {
        ((TextView) this.view.findViewById(R.id.brick_play_sound_text_view)).setText(R.string.brick_play_sound_and_wait);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPlaySoundAction(sprite, this.sound));
        float duration = 0.0f;
        if (this.sound != null) {
            MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
            metadataRetriever.setDataSource(this.sound.getFile().getAbsolutePath());
            duration = ((float) Integer.parseInt(metadataRetriever.extractMetadata(9))) / 1000.0f;
        }
        sequence.addAction(sprite.getActionFactory().createWaitAction(sprite, new Formula(Float.valueOf(duration))));
        return null;
    }
}
