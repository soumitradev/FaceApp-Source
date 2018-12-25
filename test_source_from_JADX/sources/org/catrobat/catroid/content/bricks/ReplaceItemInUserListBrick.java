package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.generated70026.R;

public class ReplaceItemInUserListBrick extends UserListBrick {
    private static final long serialVersionUID = 1;

    public ReplaceItemInUserListBrick() {
        addAllowedBrickField(BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, R.id.brick_replace_item_in_userlist_value_edit_text);
        addAllowedBrickField(BrickField.REPLACE_ITEM_IN_USERLIST_INDEX, R.id.brick_replace_item_in_userlist_at_index_edit_text);
    }

    public ReplaceItemInUserListBrick(double value, Integer indexToReplace) {
        this(new Formula(Double.valueOf(value)), new Formula(indexToReplace));
    }

    public ReplaceItemInUserListBrick(Formula valueFormula, Formula indexFormula, UserList userList) {
        this(valueFormula, indexFormula);
        this.userList = userList;
    }

    public ReplaceItemInUserListBrick(Formula valueFormula, Formula indexFormula) {
        this();
        setFormulaWithBrickField(BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, valueFormula);
        setFormulaWithBrickField(BrickField.REPLACE_ITEM_IN_USERLIST_INDEX, indexFormula);
    }

    public int getViewResource() {
        return R.layout.brick_replace_item_in_userlist;
    }

    protected int getSpinnerId() {
        return R.id.replace_item_in_userlist_spinner;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createReplaceItemInUserListAction(sprite, getFormulaWithBrickField(BrickField.REPLACE_ITEM_IN_USERLIST_INDEX), getFormulaWithBrickField(BrickField.REPLACE_ITEM_IN_USERLIST_VALUE), this.userList));
        return null;
    }
}
