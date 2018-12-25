package org.catrobat.catroid.ui.recyclerview.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog$Builder;
import android.view.View;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.utils.Utils;

public class PrivacyPolicyDialogFragment extends DialogFragment {
    private static final String BUNDLE_FORCE_ACCEPT = "forceAccept";
    public static final String TAG = PrivacyPolicyDialogFragment.class.getSimpleName();
    private boolean forceAccept = false;
    private boolean showDeleteAccountDialog = false;

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.PrivacyPolicyDialogFragment$1 */
    class C19711 implements OnClickListener {
        C19711() {
        }

        public void onClick(DialogInterface dialog, int id) {
            if (PrivacyPolicyDialogFragment.this.showDeleteAccountDialog) {
                PrivacyPolicyDialogFragment.this.showDeleteAccountDialog();
            }
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.PrivacyPolicyDialogFragment$2 */
    class C19722 implements OnClickListener {
        C19722() {
        }

        public void onClick(DialogInterface dialog, int which) {
            PrivacyPolicyDialogFragment.this.getActivity().startActivity(new Intent(PrivacyPolicyDialogFragment.this.getActivity(), WebViewActivity.class).putExtra("url", Constants.CATROBAT_DELETE_ACCOUNT_URL));
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.PrivacyPolicyDialogFragment$3 */
    class C19733 implements OnClickListener {
        C19733() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Utils.logoutUser(PrivacyPolicyDialogFragment.this.getActivity());
        }
    }

    public PrivacyPolicyDialogFragment newInstance(boolean forceAccept) {
        PrivacyPolicyDialogFragment fragment = new PrivacyPolicyDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(BUNDLE_FORCE_ACCEPT, forceAccept);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        if (bundle != null) {
            this.forceAccept = bundle.getBoolean(BUNDLE_FORCE_ACCEPT, false);
        }
        AlertDialog$Builder builder = new AlertDialog$Builder(getActivity()).setTitle(R.string.dialog_privacy_policy_title).setView(View.inflate(getActivity(), R.layout.dialog_privacy_policy, null));
        if (this.forceAccept) {
            builder.setNegativeButton(R.string.disagree, new C19711());
        } else {
            builder.setPositiveButton(R.string.ok, null);
        }
        return builder.create();
    }

    private void showDeleteAccountDialog() {
        new AlertDialog$Builder(getActivity()).setTitle(R.string.dialog_privacy_policy_title).setMessage(R.string.dialog_privacy_policy_declined_text).setPositiveButton(R.string.back, new C19733()).setNegativeButton(R.string.delete_account, new C19722()).create().show();
    }
}
