package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.drone.ardrone.DroneInitializer;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.stage.PreStageActivity;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public class TermsOfUseDialogFragment extends DialogFragment {
    public static final String DIALOG_ARGUMENT_TERMS_OF_USE_ACCEPT = "dialog_terms_of_use_accept";
    public static final String TAG = TermsOfUseDialogFragment.class.getSimpleName();
    CheckBox acceptedPermanentlyCheckbox = null;

    /* renamed from: org.catrobat.catroid.ui.dialogs.TermsOfUseDialogFragment$1 */
    class C19351 implements OnClickListener {
        C19351() {
        }

        public void onClick(DialogInterface dialog, int id) {
            if (TermsOfUseDialogFragment.this.getActivity() instanceof PreStageActivity) {
                if (TermsOfUseDialogFragment.this.acceptedPermanentlyCheckbox != null && TermsOfUseDialogFragment.this.acceptedPermanentlyCheckbox.isChecked()) {
                    SettingsFragment.setTermsOfServiceAgreedPermanently(TermsOfUseDialogFragment.this.getActivity(), true);
                    SettingsFragment.setTermsOfServiceJSAgreedPermanently(TermsOfUseDialogFragment.this.getActivity(), true);
                }
                DroneInitializer droneInitializer = ((PreStageActivity) TermsOfUseDialogFragment.this.getActivity()).getDroneInitialiser();
                if (droneInitializer != null && droneInitializer.checkRequirements()) {
                    droneInitializer.checkDroneConnectivity();
                }
            }
            dialog.dismiss();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.dialogs.TermsOfUseDialogFragment$2 */
    class C19362 implements OnKeyListener {
        C19362() {
        }

        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            Log.d(TermsOfUseDialogFragment.TAG, "prevent canceling the dialog with back button");
            return true;
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        String termsOfUseUrlStringText;
        Bundle fragmentDialogArguments = getArguments();
        boolean isOnPreStageActivity = false;
        if (fragmentDialogArguments != null) {
            isOnPreStageActivity = fragmentDialogArguments.getBoolean("dialog_terms_of_use_accept", false);
        }
        View view = View.inflate(getActivity(), R.layout.dialog_terms_of_use, null);
        TextView termsOfUseTextView = (TextView) view.findViewById(R.id.dialog_terms_of_use_text_view_info);
        TextView termsOfUseUrlTextView = (TextView) view.findViewById(R.id.dialog_terms_of_use_text_view_url);
        termsOfUseUrlTextView.setMovementMethod(LinkMovementMethod.getInstance());
        AlertDialog$Builder termsOfUseDialogBuilder = new AlertDialog$Builder(getActivity()).setView(view).setTitle(R.string.dialog_terms_of_use_title);
        termsOfUseDialogBuilder.setPositiveButton(R.string.ok, new C19351());
        termsOfUseDialogBuilder.setOnKeyListener(new C19362());
        if (isOnPreStageActivity) {
            this.acceptedPermanentlyCheckbox = (CheckBox) view.findViewById(R.id.dialog_terms_of_use_check_box_agree_permanently);
            this.acceptedPermanentlyCheckbox.setVisibility(0);
            this.acceptedPermanentlyCheckbox.setText(R.string.dialog_terms_of_use_parrot_reminder_do_not_remind_again);
            termsOfUseDialogBuilder.setCancelable(false);
            termsOfUseTextView.setText(R.string.dialog_terms_of_use_parrot_reminder_text);
            termsOfUseUrlStringText = getString(R.string.dialog_terms_of_use_link_text_parrot_reminder);
        } else {
            termsOfUseTextView.setText(R.string.dialog_terms_of_use_info);
            termsOfUseUrlStringText = getString(R.string.dialog_terms_of_use_link_text);
        }
        termsOfUseUrlTextView.setText(Html.fromHtml(getString(R.string.terms_of_use_link_template, new Object[]{Constants.CATROBAT_TERMS_OF_USE_URL, termsOfUseUrlStringText})));
        AlertDialog termsOfUseDialog = termsOfUseDialogBuilder.create();
        if (isOnPreStageActivity) {
            termsOfUseDialog.setCancelable(false);
            termsOfUseDialog.setCanceledOnTouchOutside(false);
        } else {
            termsOfUseDialog.setCanceledOnTouchOutside(true);
        }
        return termsOfUseDialog;
    }
}
