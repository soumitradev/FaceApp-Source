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
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoInitializer;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.stage.PreStageActivity;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public class TermsOfUseJSDialogFragment extends DialogFragment {
    public static final String DIALOG_ARGUMENT_TERMS_OF_USE_ACCEPT = "dialog_terms_of_use_accept";
    public static final String DIALOG_FRAGMENT_TAG = "dialog_terms_of_use";
    private static final String TAG = TermsOfUseJSDialogFragment.class.getSimpleName();
    private static PreStageActivity prestageStageActivity;
    CheckBox checkboxTermsOfUseAcceptedPermanently = null;

    /* renamed from: org.catrobat.catroid.ui.dialogs.TermsOfUseJSDialogFragment$1 */
    class C19371 implements OnClickListener {
        C19371() {
        }

        public void onClick(DialogInterface dialog, int id) {
            if (TermsOfUseJSDialogFragment.this.getActivity() instanceof PreStageActivity) {
                if (TermsOfUseJSDialogFragment.this.checkboxTermsOfUseAcceptedPermanently != null && TermsOfUseJSDialogFragment.this.checkboxTermsOfUseAcceptedPermanently.isChecked()) {
                    SettingsFragment.setTermsOfServiceJSAgreedPermanently(TermsOfUseJSDialogFragment.this.getActivity(), true);
                }
                JumpingSumoInitializer jsDiscoverer = ((PreStageActivity) TermsOfUseJSDialogFragment.this.getActivity()).getJumpingSumoInitialiser();
                if (jsDiscoverer != null && jsDiscoverer.checkRequirements()) {
                    jsDiscoverer.initialise();
                    jsDiscoverer.checkJumpingSumoAvailability(TermsOfUseJSDialogFragment.prestageStageActivity);
                }
            }
            dialog.dismiss();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.dialogs.TermsOfUseJSDialogFragment$2 */
    class C19382 implements OnKeyListener {
        C19382() {
        }

        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            Log.d(TermsOfUseJSDialogFragment.TAG, "prevent canceling the dialog with back button");
            return true;
        }
    }

    public static void setPrestageStageActivity(PreStageActivity preState) {
        prestageStageActivity = preState;
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
        termsOfUseDialogBuilder.setNeutralButton(R.string.ok, new C19371());
        termsOfUseDialogBuilder.setOnKeyListener(new C19382());
        if (isOnPreStageActivity) {
            this.checkboxTermsOfUseAcceptedPermanently = (CheckBox) view.findViewById(R.id.dialog_terms_of_use_check_box_agree_permanently);
            this.checkboxTermsOfUseAcceptedPermanently.setVisibility(0);
            this.checkboxTermsOfUseAcceptedPermanently.setText(R.string.dialog_terms_of_use_parrot_reminder_do_not_remind_again);
            termsOfUseDialogBuilder.setCancelable(false);
            termsOfUseTextView.setText(R.string.dialog_terms_of_use_jumpingsumo_reminder_text);
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
