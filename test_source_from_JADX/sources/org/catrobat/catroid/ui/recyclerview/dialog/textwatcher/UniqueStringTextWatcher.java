package org.catrobat.catroid.ui.recyclerview.dialog.textwatcher;

import android.content.Context;
import android.support.annotation.Nullable;
import java.util.List;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.TextWatcher;

public class UniqueStringTextWatcher extends TextWatcher {
    private List<String> scope;

    public UniqueStringTextWatcher(List<String> scope) {
        this.scope = scope;
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
        } else if (this.scope.contains(input)) {
            error = context.getString(R.string.name_already_exists);
        }
        return error;
    }
}
