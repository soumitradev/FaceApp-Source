package org.catrobat.catroid.ui.recyclerview.dialog.textwatcher;

import android.content.Context;
import android.support.annotation.Nullable;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.TextWatcher;

public class NonEmptyStringTextWatcher extends TextWatcher {
    @Nullable
    public String validateInput(String input, Context context) {
        String error = null;
        if (input.isEmpty()) {
            return context.getString(R.string.name_empty);
        }
        if (input.trim().isEmpty()) {
            error = context.getString(R.string.name_consists_of_spaces_only);
        }
        return error;
    }
}
