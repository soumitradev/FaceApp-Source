package org.catrobat.catroid.ui.recyclerview.dialog.textwatcher;

import android.content.Context;
import android.support.annotation.Nullable;
import java.util.List;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.TextWatcher;

public class NewItemTextWatcher<T extends Nameable> extends TextWatcher {
    private List<T> scope;

    public NewItemTextWatcher(List<T> scope) {
        this.scope = scope;
    }

    public void setScope(List<T> scope) {
        this.scope = scope;
    }

    private boolean isNameUnique(String name) {
        for (Nameable item : this.scope) {
            if (item.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public String validateInput(String input, Context context) {
        String error = null;
        if (input.isEmpty()) {
            return context.getString(R.string.name_empty);
        }
        input = input.trim();
        if (input.isEmpty()) {
            error = context.getString(R.string.name_consists_of_spaces_only);
        } else if (!isNameUnique(input)) {
            error = context.getString(R.string.name_already_exists);
        }
        return error;
    }
}
