package org.catrobat.catroid.content.bricks;

import android.view.View;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class SetBackgroundAndWaitBrick extends SetBackgroundBrick {
    protected void onViewCreated(View view) {
        ((TextView) view.findViewById(R.id.brick_set_look_text_view)).setText(R.string.brick_set_background_and_wait);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetLookAction(getSprite(), this.look, 0));
        return Collections.emptyList();
    }
}
