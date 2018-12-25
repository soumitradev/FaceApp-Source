package org.catrobat.catroid.content.actions;

import android.support.annotation.NonNull;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import java.util.Iterator;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Script;

public class ScriptSequenceAction extends SequenceAction {
    protected final Script script;

    public ScriptSequenceAction(@NonNull Script script) {
        this.script = script;
    }

    public ScriptSequenceAction(Action action1, @NonNull Script script) {
        super(action1);
        this.script = script;
    }

    public ScriptSequenceAction(Action action1, Action action2, @NonNull Script script) {
        super(action1, action2);
        this.script = script;
    }

    public ScriptSequenceAction(Action action1, Action action2, Action action3, @NonNull Script script) {
        super(action1, action2, action3);
        this.script = script;
    }

    public ScriptSequenceAction(Action action1, Action action2, Action action3, Action action4, @NonNull Script script) {
        super(action1, action2, action3, action4);
        this.script = script;
    }

    public ScriptSequenceAction(Action action1, Action action2, Action action3, Action action4, Action action5, @NonNull Script script) {
        super(action1, action2, action3, action4, action5);
        this.script = script;
    }

    public Script getScript() {
        return this.script;
    }

    public ScriptSequenceAction clone() {
        ScriptSequenceAction copy = (ScriptSequenceAction) ActionFactory.eventSequence(this.script);
        Iterator it = getActions().iterator();
        while (it.hasNext()) {
            copy.addAction((Action) it.next());
        }
        return copy;
    }
}
