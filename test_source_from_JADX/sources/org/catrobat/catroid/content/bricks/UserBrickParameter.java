package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;

public class UserBrickParameter extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private UserScriptDefinitionBrickElement element;
    private transient UserBrick parent;
    private transient TextView prototypeView;

    public int getViewResource() {
        return this.parent.getViewResource();
    }

    public View getView(Context context) {
        super.getView(context);
        return this.parent.getView(context);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetVariableAction(sprite, getFormulaWithBrickField(BrickField.VARIABLE), ProjectManager.getInstance().getCurrentlyEditedScene().getDataContainer().getUserVariable(sprite, this.parent, this.element.getText())));
        return null;
    }

    public TextView getPrototypeView() {
        return this.prototypeView;
    }

    public void setPrototypeView(TextView prototypeView) {
        this.prototypeView = prototypeView;
    }

    public UserScriptDefinitionBrickElement getElement() {
        return this.element;
    }

    public void setElement(UserScriptDefinitionBrickElement element) {
        this.element = element;
    }

    public UserBrick getParent() {
        return this.parent;
    }

    public void setParent(UserBrick parent) {
        this.parent = parent;
    }
}
