package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.ArrayList;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserList;

public class DeleteItemOfUserListAction extends TemporalAction {
    private Formula formulaIndexToDelete;
    private Sprite sprite;
    private UserList userList;

    protected void update(float percent) {
        if (this.userList != null && this.userList.getList().size() != 0) {
            int indexToDelete = 1;
            try {
                if (this.formulaIndexToDelete != null) {
                    indexToDelete = this.formulaIndexToDelete.interpretInteger(this.sprite).intValue();
                }
            } catch (InterpretationException e) {
            }
            indexToDelete--;
            if (indexToDelete < this.userList.getList().size()) {
                if (indexToDelete >= 0) {
                    ((ArrayList) this.userList.getList()).remove(indexToDelete);
                }
            }
        }
    }

    public void setUserList(UserList userVariable) {
        this.userList = userVariable;
    }

    public void setFormulaIndexToDelete(Formula formulaIndexToDelete) {
        this.formulaIndexToDelete = formulaIndexToDelete;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
