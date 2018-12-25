package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.generated70026.R;

public class InsertItemIntoUserListBrick extends UserListBrick {
    private static final long serialVersionUID = 1;

    public InsertItemIntoUserListBrick() {
        addAllowedBrickField(BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, R.id.brick_insert_item_into_userlist_value_edit_text);
        addAllowedBrickField(BrickField.INSERT_ITEM_INTO_USERLIST_INDEX, R.id.brick_insert_item_into_userlist_at_index_edit_text);
    }

    public InsertItemIntoUserListBrick(Formula userListFormulaValueToInsert, Formula userListFormulaIndexToInsert, UserList userList) {
        this(userListFormulaValueToInsert, userListFormulaIndexToInsert);
        this.userList = userList;
    }

    public InsertItemIntoUserListBrick(double value, Integer indexToInsert) {
        this(new Formula(Double.valueOf(value)), new Formula(indexToInsert));
    }

    public InsertItemIntoUserListBrick(Formula userListFormulaValueToInsert, Formula userListFormulaIndexToInsert) {
        this();
        setFormulaWithBrickField(BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, userListFormulaValueToInsert);
        setFormulaWithBrickField(BrickField.INSERT_ITEM_INTO_USERLIST_INDEX, userListFormulaIndexToInsert);
    }

    public int getViewResource() {
        return R.layout.brick_insert_item_into_userlist;
    }

    protected int getSpinnerId() {
        return R.id.insert_item_into_userlist_spinner;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createInsertItemIntoUserListAction(sprite, getFormulaWithBrickField(BrickField.INSERT_ITEM_INTO_USERLIST_INDEX), getFormulaWithBrickField(BrickField.INSERT_ITEM_INTO_USERLIST_VALUE), this.userList));
        return null;
    }
}
