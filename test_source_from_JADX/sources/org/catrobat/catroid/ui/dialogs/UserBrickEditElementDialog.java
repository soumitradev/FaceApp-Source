package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.fragment.UserBrickElementEditorFragment;
import org.catrobat.catroid.utils.ToastUtil;

public class UserBrickEditElementDialog extends DialogFragment {
    public static final String DIALOG_FRAGMENT_TAG = "dialog_new_text_catroid";
    private static boolean editMode;
    private static int stringResourceOfHintText;
    private static int stringResourceOfTitle;
    private static ArrayList<String> takenVariables;
    private static CharSequence text;
    private List<DialogListener> listenerList = new ArrayList();
    private UserBrickElementEditorFragment userBrickElementEditorFragment;

    /* renamed from: org.catrobat.catroid.ui.dialogs.UserBrickEditElementDialog$2 */
    class C19402 implements OnClickListener {
        C19402() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    public interface DialogListener {
        void onFinishDialog(CharSequence charSequence, boolean z);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            dismiss();
        }
    }

    public static void setTitle(int stringResource) {
        stringResourceOfTitle = stringResource;
    }

    public static void setText(CharSequence sequence) {
        text = sequence;
    }

    public static void setHintText(int stringResource) {
        stringResourceOfHintText = stringResource;
    }

    public static void setTakenVariables(ArrayList<String> variables) {
        takenVariables = variables;
    }

    public static void setEditMode(boolean mode) {
        editMode = mode;
    }

    public void setUserBrickElementEditorFragment(UserBrickElementEditorFragment userBrickElementEditorFragment) {
        this.userBrickElementEditorFragment = userBrickElementEditorFragment;
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (stringResourceOfTitle == R.string.add_variable) {
            ProjectManager.getInstance().getCurrentUserBrick().getDefinitionBrick().removeDataAt(ProjectManager.getInstance().getCurrentUserBrick().getDefinitionBrick().getUserScriptDefinitionBrickElements().size() - 1, getActivity().getApplicationContext());
            this.userBrickElementEditorFragment.decreaseIndexOfCurrentlyEditedElement();
        }
        finishDialog(null);
    }

    public Dialog onCreateDialog(Bundle bundle) {
        final View dialogView = View.inflate(getActivity(), R.layout.dialog_brick_editor_edit_element, null);
        EditText textField = (EditText) dialogView.findViewById(R.id.dialog_brick_editor_edit_element_edit_text);
        textField.setText(text);
        textField.setSelection(text.length());
        final Dialog dialogNewVariable = new AlertDialog$Builder(getActivity()).setView(dialogView).setTitle(stringResourceOfTitle).setNegativeButton(R.string.cancel, new C19402()).setPositiveButton(R.string.ok, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                UserBrickEditElementDialog.this.handleOkButton(dialogView);
            }
        }).create();
        dialogNewVariable.setCanceledOnTouchOutside(true);
        dialogNewVariable.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialog) {
                UserBrickEditElementDialog.this.handleOnShow(dialogNewVariable);
            }
        });
        return dialogNewVariable;
    }

    public void addDialogListener(DialogListener newVariableDialogListener) {
        this.listenerList.add(newVariableDialogListener);
    }

    private void finishDialog(CharSequence text) {
        for (DialogListener newVariableDialogListener : this.listenerList) {
            newVariableDialogListener.onFinishDialog(text, editMode);
        }
    }

    private void handleOkButton(View dialogView) {
        finishDialog(((EditText) dialogView.findViewById(R.id.dialog_brick_editor_edit_element_edit_text)).getText());
    }

    private void handleOnShow(Dialog dialogNewVariable) {
        final Button positiveButton = ((AlertDialog) dialogNewVariable).getButton(-1);
        EditText dialogEditText = (EditText) dialogNewVariable.findViewById(R.id.dialog_brick_editor_edit_element_edit_text);
        dialogEditText.selectAll();
        dialogEditText.setHint(stringResourceOfHintText);
        ((InputMethodManager) getActivity().getSystemService("input_method")).showSoftInput(dialogEditText, 1);
        dialogEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable editable) {
                positiveButton.setEnabled(true);
                if (editable.length() == 0) {
                    positiveButton.setEnabled(false);
                }
                Iterator it = UserBrickEditElementDialog.takenVariables.iterator();
                while (it.hasNext()) {
                    if (editable.toString().equals((String) it.next())) {
                        positiveButton.setEnabled(false);
                        ToastUtil.showError(UserBrickEditElementDialog.this.getActivity(), (int) R.string.formula_editor_existing_variable);
                        return;
                    }
                }
            }
        });
    }
}
