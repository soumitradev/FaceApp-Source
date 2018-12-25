package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class AddItemToUserListBrick extends UserListBrick {
    private static final long serialVersionUID = 1;

    public AddItemToUserListBrick() {
        addAllowedBrickField(BrickField.LIST_ADD_ITEM, R.id.brick_add_item_to_userlist_edit_text);
    }

    public AddItemToUserListBrick(double value) {
        this(new Formula(Double.valueOf(value)));
    }

    public AddItemToUserListBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.LIST_ADD_ITEM, formula);
    }

    public int getViewResource() {
        return R.layout.brick_add_item_to_userlist;
    }

    protected int getSpinnerId() {
        return R.id.add_item_to_userlist_spinner;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAddItemToUserListAction(sprite, getFormulaWithBrickField(BrickField.LIST_ADD_ITEM), this.userList));
        return null;
    }
}
