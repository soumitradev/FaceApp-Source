package org.catrobat.catroid.ui.recyclerview.dialog;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog$Builder;
import org.catrobat.catroid.generated70026.R;

public class RatePocketCodeDialogFragment extends DialogFragment {
    public static final String TAG = RatePocketCodeDialogFragment.class.getSimpleName();

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.RatePocketCodeDialogFragment$1 */
    class C19741 implements OnClickListener {
        C19741() {
        }

        public void onClick(DialogInterface dialog, int id) {
            PreferenceManager.getDefaultSharedPreferences(RatePocketCodeDialogFragment.this.getActivity()).edit().putInt(UploadProgressDialogFragment.NUMBER_OF_UPLOADED_PROJECTS, 0).commit();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.recyclerview.dialog.RatePocketCodeDialogFragment$2 */
    class C19752 implements OnClickListener {
        C19752() {
        }

        public void onClick(DialogInterface dialog, int id) {
            try {
                RatePocketCodeDialogFragment ratePocketCodeDialogFragment = RatePocketCodeDialogFragment.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("market://details?id=");
                stringBuilder.append(RatePocketCodeDialogFragment.this.getActivity().getPackageName());
                ratePocketCodeDialogFragment.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())).addFlags(268435456));
            } catch (ActivityNotFoundException e) {
                RatePocketCodeDialogFragment ratePocketCodeDialogFragment2 = RatePocketCodeDialogFragment.this;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("https://play.google.com/store/apps/details?id=");
                stringBuilder2.append(RatePocketCodeDialogFragment.this.getActivity().getPackageName());
                ratePocketCodeDialogFragment2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder2.toString())));
            }
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        return new AlertDialog$Builder(getActivity()).setTitle(getString(R.string.rating_dialog_title)).setView(R.layout.dialog_rate_pocketcode).setPositiveButton(R.string.rating_dialog_rate_now, new C19752()).setNeutralButton(getString(R.string.rating_dialog_rate_later), new C19741()).setNegativeButton(getString(R.string.rating_dialog_rate_never), null).setCancelable(false).create();
    }
}
