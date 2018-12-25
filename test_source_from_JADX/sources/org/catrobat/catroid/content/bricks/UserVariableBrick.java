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
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.NewItemTextWatcher;

public abstract class UserVariableBrick extends FormulaBrick implements OnItemSelectedListener<UserVariable> {
    private transient BrickSpinner<UserVariable> spinner;
    protected UserVariable userVariable;

    /* renamed from: org.catrobat.catroid.content.bricks.UserVariableBrick$2 */
    class C18042 implements OnCancelListener {
        C18042() {
        }

        public void onCancel(DialogInterface dialog) {
            UserVariableBrick.this.spinner.setSelection(UserVariableBrick.this.userVariable);
        }
    }

    /* renamed from: org.catrobat.catroid.content.bricks.UserVariableBrick$3 */
    class C18053 implements OnClickListener {
        C18053() {
        }

        public void onClick(DialogInterface dialog, int which) {
            UserVariableBrick.this.spinner.setSelection(UserVariableBrick.this.userVariable);
        }
    }

    @IdRes
    protected abstract int getSpinnerId();

    public UserVariable getUserVariable() {
        return this.userVariable;
    }

    public void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        UserVariableBrick clone = (UserVariableBrick) super.clone();
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
        items.addAll(dataContainer.getSpriteUserVariables(sprite));
        items.addAll(dataContainer.getProjectUserVariables());
        this.spinner = new BrickSpinner(getSpinnerId(), this.view, items);
        this.spinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.userVariable);
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
                    Nameable userVariable = new UserVariable(textInput);
                    DataContainer dataContainer = currentScene.getDataContainer();
                    if (((RadioButton) ((Dialog) dialog).findViewById(R.id.global)).isChecked()) {
                        dataContainer.addUserVariable(userVariable);
                    } else {
                        dataContainer.addUserVariable(currentSprite, userVariable);
                    }
                    UserVariableBrick.this.spinner.add(userVariable);
                    UserVariableBrick.this.spinner.setSelection(userVariable);
                    UserVariableBrick.this.adapter.notifyDataSetChanged();
                }
            });
            builder.setTitle(R.string.formula_editor_variable_dialog_title).setView(R.layout.dialog_new_user_data).setNegativeButton(R.string.cancel, new C18053()).setOnCancelListener(new C18042()).create().show();
        }
    }

    public void onStringOptionSelected(String string) {
    }

    public void onItemSelected(@Nullable UserVariable item) {
        this.userVariable = item;
    }
}
