package org.catrobat.catroid.ui.recyclerview.dialog.textwatcher;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

public class DialogInputWatcher implements TextWatcher {
    private boolean allowEmptyInput;
    private TextInputLayout inputLayout;
    private Button positiveButton;

    public DialogInputWatcher(TextInputLayout inputLayout, Button positiveButton, boolean allowEmptyInput) {
        this.inputLayout = inputLayout;
        this.positiveButton = positiveButton;
        this.allowEmptyInput = allowEmptyInput;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.inputLayout.setError(null);
        if (!this.allowEmptyInput) {
            this.positiveButton.setEnabled(s.length() > 0);
        }
    }

    public void afterTextChanged(Editable s) {
    }
}
