package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;

public class AddItemToUserListAction extends TemporalAction {
    private Formula formulaItemToAdd;
    private Sprite sprite;
    private UserList userList;

    protected void update(float percent) {
        if (this.userList != null) {
            this.userList.addListItem(this.formulaItemToAdd == null ? Double.valueOf(BrickValues.SET_COLOR_TO) : this.formulaItemToAdd.interpretObject(this.sprite));
        }
    }

    public void setUserList(UserList userVariable) {
        this.userList = userVariable;
    }

    public void setFormulaItemToAdd(Formula changeVariable) {
        this.formulaItemToAdd = changeVariable;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
