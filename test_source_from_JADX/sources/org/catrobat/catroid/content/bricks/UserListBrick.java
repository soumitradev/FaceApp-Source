package org.catrobat.catroid.content.bricks;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.NewItemTextWatcher;

public abstract class UserListBrick extends FormulaBrick implements OnItemSelectedListener<UserList> {
    private transient BrickSpinner<UserList> spinner;
    protected UserList userList;

    /* renamed from: org.catrobat.catroid.content.bricks.UserListBrick$2 */
    class C18022 implements OnCancelListener {
        C18022() {
        }

        public void onCancel(DialogInterface dialog) {
            UserListBrick.this.spinner.setSelection(UserListBrick.this.userList);
        }
    }

    /* renamed from: org.catrobat.catroid.content.bricks.UserListBrick$3 */
    class C18033 implements OnClickListener {
        C18033() {
        }

        public void onClick(DialogInterface dialog, int which) {
            UserListBrick.this.spinner.setSelection(UserListBrick.this.userList);
        }
    }

    @IdRes
    protected abstract int getSpinnerId();

    public UserList getUserList() {
        return this.userList;
    }

    public void setUserList(UserList userList) {
        this.userList = userList;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        UserListBrick clone = (UserListBrick) super.clone();
        clone.spinner = null;
        return clone;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        Sprite sprite = ProjectManager.getInstance().getCurrentSprite();
        DataContainer dataContainer = ProjectManager.getInstance().getCurrentlyEditedScene().getDataContainer();
        List<Nameable> items = new ArrayList();
        items.add(new NewOption(context.getString(R.string.new_option)));
        items.addAll(dataContainer.getSpriteUserLists(sprite));
        items.addAll(dataContainer.getProjectUserLists());
        this.spinner = new BrickSpinner(getSpinnerId(), this.view, items);
        this.spinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.userList);
        return this.view;
    }

    public void onNewOptionSelected() {
        AppCompatActivity activity = UiUtils.getActivityFromView(this.view);
        if (activity != null) {
            final Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
            final Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
            Builder builder = new Builder(activity);
            builder.setHint(activity.getString(R.string.data_label)).setTextWatcher(new NewItemTextWatcher(this.spinner.getItems())).setPositiveButton(activity.getString(R.string.ok), new TextInputDialog.OnClickListener() {
                public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
                    Nameable userList = new UserList(textInput);
                    DataContainer dataContainer = currentScene.getDataContainer();
                    if (((RadioButton) ((Dialog) dialog).findViewById(R.id.global)).isChecked()) {
                        dataContainer.addUserList(userList);
                    } else {
                        dataContainer.addUserList(currentSprite, userList);
                    }
                    UserListBrick.this.spinner.add(userList);
                    UserListBrick.this.spinner.setSelection(userList);
                    UserListBrick.this.adapter.notifyDataSetChanged();
                }
            });
            builder.setTitle(R.string.formula_editor_list_dialog_title).setView(R.layout.dialog_new_user_data).setNegativeButton(R.string.cancel, new C18033()).setOnCancelListener(new C18022()).create().show();
        }
    }

    public void onStringOptionSelected(String string) {
    }

    public void onItemSelected(@Nullable UserList item) {
        this.userList = item;
    }
}
