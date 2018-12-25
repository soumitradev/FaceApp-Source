package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.generated70026.R;

public class DeleteItemOfUserListBrick extends UserListBrick {
    private static final long serialVersionUID = 1;

    public DeleteItemOfUserListBrick() {
        addAllowedBrickField(BrickField.LIST_DELETE_ITEM, R.id.brick_delete_item_of_userlist_edit_text);
    }

    public DeleteItemOfUserListBrick(Integer item) {
        this(new Formula(item));
    }

    public DeleteItemOfUserListBrick(Formula formula, UserList userList) {
        this(formula);
        this.userList = userList;
    }

    public DeleteItemOfUserListBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.LIST_DELETE_ITEM, formula);
    }

    public int getViewResource() {
        return R.layout.brick_delete_item_of_userlist;
    }

    protected int getSpinnerId() {
        return R.id.delete_item_of_userlist_spinner;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDeleteItemOfUserListAction(sprite, getFormulaWithBrickField(BrickField.LIST_DELETE_ITEM), this.userList));
        return null;
    }
}
