package org.catrobat.catroid.content.bricks;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;

public class SetBackgroundBrick extends SetLookBrick {
    protected void onViewCreated(View view) {
        ((TextView) view.findViewById(R.id.brick_set_look_text_view)).setText(R.string.brick_set_background);
    }

    public void onNewOptionSelected() {
        AppCompatActivity activity = UiUtils.getActivityFromView(this.view);
        if (activity != null) {
            if (activity instanceof SpriteActivity) {
                ((SpriteActivity) activity).registerOnNewLookListener(this);
                ((SpriteActivity) activity).handleAddBackgroundButton();
            }
        }
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetLookAction(getSprite(), this.look, 1));
        return Collections.emptyList();
    }

    protected Sprite getSprite() {
        return ProjectManager.getInstance().getCurrentlyPlayingScene().getBackgroundSprite();
    }
}
