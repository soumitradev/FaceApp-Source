package org.catrobat.catroid.ui.recyclerview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.text.Editable;
import org.catrobat.catroid.generated70026.R;

public final class TextInputDialog extends AlertDialog {

    public interface OnClickListener {
        void onPositiveButtonClick(DialogInterface dialogInterface, String str);
    }

    public static abstract class TextWatcher implements android.text.TextWatcher {
        private AlertDialog alertDialog;
        private TextInputLayout inputLayout;

        @Nullable
        public abstract String validateInput(String str, Context context);

        private void setInputLayout(@NonNull TextInputLayout inputLayout) {
            this.inputLayout = inputLayout;
        }

        private void setAlertDialog(@NonNull AlertDialog alertDialog) {
            this.alertDialog = alertDialog;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            String error = validateInput(s.toString(), this.alertDialog.getContext());
            this.inputLayout.setError(error);
            this.alertDialog.getButton(-1).setEnabled(error == null);
        }
    }

    public static class Builder extends AlertDialog$Builder {
        @Nullable
        private String hint;
        @Nullable
        private String text;
        @Nullable
        private TextWatcher textWatcher;

        public Builder(@NonNull Context context) {
            super(context);
            setView(R.layout.dialog_text_input);
        }

        public Builder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTextWatcher(TextWatcher textWatcher) {
            this.textWatcher = textWatcher;
            return this;
        }

        public Builder setPositiveButton(String text, final OnClickListener listener) {
            setPositiveButton(text, new android.content.DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    listener.onPositiveButtonClick(dialog, ((TextInputLayout) ((Dialog) dialog).findViewById(R.id.input)).getEditText().getText().toString());
                }
            });
            return this;
        }

        public AlertDialog create() {
            final AlertDialog alertDialog = super.create();
            alertDialog.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface dialog) {
                    TextInputLayout textInputLayout = (TextInputLayout) alertDialog.findViewById(R.id.input);
                    textInputLayout.setHint(Builder.this.hint);
                    textInputLayout.getEditText().setText(Builder.this.text);
                    textInputLayout.getEditText().selectAll();
                    if (Builder.this.textWatcher != null) {
                        textInputLayout.getEditText().addTextChangedListener(Builder.this.textWatcher);
                        Builder.this.textWatcher.setInputLayout(textInputLayout);
                        Builder.this.textWatcher.setAlertDialog(alertDialog);
                        alertDialog.getButton(-1).setEnabled(Builder.this.textWatcher.validateInput(textInputLayout.getEditText().getText().toString(), Builder.this.getContext()) == null);
                    }
                }
            });
            return alertDialog;
        }
    }

    private TextInputDialog(@NonNull Context context) {
        super(context);
    }
}
