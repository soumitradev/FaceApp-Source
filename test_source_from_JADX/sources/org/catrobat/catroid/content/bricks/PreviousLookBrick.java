package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class PreviousLookBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;

    public int getViewResource() {
        return R.layout.brick_previous_look;
    }

    public View getPrototypeView(Context context) {
        View view = super.getPrototypeView(context);
        if (ProjectManager.getInstance().getCurrentSprite().getName().equals(context.getString(R.string.background))) {
            ((TextView) view.findViewById(R.id.brick_previous_look_text_view)).setText(R.string.brick_previous_background);
        }
        return view;
    }

    public View getView(Context context) {
        super.getView(context);
        if (ProjectManager.getInstance().getCurrentSprite().getName().equals(context.getString(R.string.background))) {
            ((TextView) this.view.findViewById(R.id.brick_previous_look_text_view)).setText(R.string.brick_previous_background);
        }
        return this.view;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPreviousLookAction(sprite));
        return null;
    }
}
