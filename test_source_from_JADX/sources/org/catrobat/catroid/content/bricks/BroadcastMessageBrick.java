package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.content.bricks.brickspinner.StringOption;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.NonEmptyStringTextWatcher;

public abstract class BroadcastMessageBrick extends BrickBaseType implements OnItemSelectedListener<StringOption> {
    private transient BrickSpinner<StringOption> spinner;

    /* renamed from: org.catrobat.catroid.content.bricks.BroadcastMessageBrick$2 */
    class C17762 implements OnCancelListener {
        C17762() {
        }

        public void onCancel(DialogInterface dialog) {
            BroadcastMessageBrick.this.spinner.setSelection(BroadcastMessageBrick.this.getBroadcastMessage());
        }
    }

    /* renamed from: org.catrobat.catroid.content.bricks.BroadcastMessageBrick$3 */
    class C17773 implements OnClickListener {
        C17773() {
        }

        public void onClick(DialogInterface dialog, int which) {
            BroadcastMessageBrick.this.spinner.setSelection(BroadcastMessageBrick.this.getBroadcastMessage());
        }
    }

    /* renamed from: org.catrobat.catroid.content.bricks.BroadcastMessageBrick$1 */
    class C20921 implements TextInputDialog.OnClickListener {
        C20921() {
        }

        public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
            BroadcastMessageBrick.this.addItem(textInput);
        }
    }

    public abstract String getBroadcastMessage();

    public abstract void setBroadcastMessage(String str);

    public BrickBaseType clone() throws CloneNotSupportedException {
        BroadcastMessageBrick clone = (BroadcastMessageBrick) super.clone();
        clone.spinner = null;
        return clone;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        List<String> messages = ProjectManager.getInstance().getCurrentProject().getBroadcastMessageContainer().getBroadcastMessages();
        if (messages.isEmpty()) {
            messages.add(context.getString(R.string.brick_broadcast_default_value));
        }
        List<Nameable> items = new ArrayList();
        items.add(new NewOption(context.getString(R.string.new_option)));
        for (String message : messages) {
            items.add(new StringOption(message));
        }
        this.spinner = new BrickSpinner(R.id.brick_broadcast_spinner, this.view, items);
        this.spinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(getBroadcastMessage());
        return this.view;
    }

    public void onNewOptionSelected() {
        AppCompatActivity activity = UiUtils.getActivityFromView(this.view);
        if (activity != null) {
            Builder builder = new Builder(activity);
            builder.setHint(activity.getString(R.string.dialog_new_broadcast_message_name)).setTextWatcher(new NonEmptyStringTextWatcher()).setPositiveButton(activity.getString(R.string.ok), new C20921());
            builder.setTitle(R.string.dialog_new_broadcast_message_title).setNegativeButton(R.string.cancel, new C17773()).setOnCancelListener(new C17762()).create().show();
        }
    }

    public void addItem(String item) {
        if (ProjectManager.getInstance().getCurrentProject().getBroadcastMessageContainer().addBroadcastMessage(item)) {
            this.spinner.add(new StringOption(item));
        }
        this.spinner.setSelection(item);
        this.adapter.notifyDataSetChanged();
    }

    public void onStringOptionSelected(String string) {
        setBroadcastMessage(string);
    }

    public void onItemSelected(@Nullable StringOption item) {
    }
}
