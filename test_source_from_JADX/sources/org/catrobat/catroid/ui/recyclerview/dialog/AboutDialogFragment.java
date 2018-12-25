package org.catrobat.catroid.ui.recyclerview.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog$Builder;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.Utils;

public class AboutDialogFragment extends DialogFragment {
    public static final String TAG = AboutDialogFragment.class.getSimpleName();

    public Dialog onCreateDialog(Bundle bundle) {
        View view = View.inflate(getActivity(), R.layout.dialog_about, null);
        TextView developerUrlView = (TextView) view.findViewById(R.id.dialog_about_text_view_url);
        developerUrlView.setMovementMethod(LinkMovementMethod.getInstance());
        developerUrlView.setText(Html.fromHtml(getString(R.string.about_link_template, new Object[]{Constants.ABOUT_POCKETCODE_LICENSE_URL, getString(R.string.dialog_about_license_link_text)})));
        TextView aboutCatrobatView = (TextView) view.findViewById(R.id.dialog_about_text_catrobat_url);
        aboutCatrobatView.setMovementMethod(LinkMovementMethod.getInstance());
        aboutCatrobatView.setText(Html.fromHtml(getString(R.string.about_link_template, new Object[]{Constants.CATROBAT_ABOUT_URL, getString(R.string.dialog_about_catrobat_link_text)})));
        TextView aboutVersionNameTextView = (TextView) view.findViewById(R.id.dialog_about_text_view_version_name);
        String versionName = new StringBuilder();
        versionName.append(getString(R.string.android_version_prefix));
        versionName.append(Utils.getVersionName(getActivity()));
        aboutVersionNameTextView.setText(versionName.toString());
        return new AlertDialog$Builder(getActivity()).setTitle(R.string.dialog_about_title).setView(view).setPositiveButton(R.string.ok, null).create();
    }
}
