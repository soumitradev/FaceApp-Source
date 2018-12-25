package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.ArrayList;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserList;

public class ReplaceItemInUserListAction extends TemporalAction {
    private Formula formulaIndexToReplace;
    private Formula formulaItemToInsert;
    private Sprite sprite;
    private UserList userList;

    protected void update(float percent) {
        if (this.userList != null) {
            Object value = this.formulaItemToInsert == null ? Double.valueOf(BrickValues.SET_COLOR_TO) : this.formulaItemToInsert.interpretObject(this.sprite);
            int indexToReplace = 1;
            try {
                if (this.formulaIndexToReplace != null) {
                    indexToReplace = this.formulaIndexToReplace.interpretInteger(this.sprite).intValue();
                }
            } catch (InterpretationException e) {
            }
            indexToReplace--;
            if (indexToReplace < this.userList.getList().size()) {
                if (indexToReplace >= 0) {
                    ((ArrayList) this.userList.getList()).set(indexToReplace, value);
                }
            }
        }
    }

    public void setUserList(UserList userVariable) {
        this.userList = userVariable;
    }

    public void setFormulaIndexToReplace(Formula formulaIndexToReplace) {
        this.formulaIndexToReplace = formulaIndexToReplace;
    }

    public void setFormulaItemToInsert(Formula formulaItemToInsert) {
        this.formulaItemToInsert = formulaItemToInsert;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
