package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.ToastUtil;

public class ScratchReconvertDialog extends DialogFragment {
    public static final String DIALOG_FRAGMENT_TAG = "scratch_reconvert_dialog";
    private static final String TAG = ScratchReconvertDialog.class.getSimpleName();
    protected Date cachedDate;
    protected ReconvertDialogCallback callback;
    protected Context context;
    protected RadioButton downloadExistingProgramRadioButton;
    protected RadioButton reconvertProgramRadioButton;

    /* renamed from: org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog$1 */
    class C19231 implements OnClickListener {
        C19231() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Log.d(ScratchReconvertDialog.TAG, "User canceled dialog by pressing Cancel-button");
            ToastUtil.showError(ScratchReconvertDialog.this.context, (int) R.string.notification_reconvert_download_program_cancel);
            if (ScratchReconvertDialog.this.callback != null) {
                ScratchReconvertDialog.this.callback.onUserCanceledConversion();
            }
        }
    }

    /* renamed from: org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog$2 */
    class C19242 implements OnClickListener {
        C19242() {
        }

        public void onClick(DialogInterface dialog, int which) {
            ScratchReconvertDialog.this.handleOkButton();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog$3 */
    class C19263 implements OnShowListener {

        /* renamed from: org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog$3$1 */
        class C19251 implements View.OnClickListener {
            C19251() {
            }

            public void onClick(View view) {
                ScratchReconvertDialog.this.handleOkButton();
            }
        }

        C19263() {
        }

        public void onShow(DialogInterface dialog) {
            ((AlertDialog) dialog).getButton(-1).setOnClickListener(new C19251());
        }
    }

    /* renamed from: org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog$4 */
    class C19274 implements OnKeyListener {
        C19274() {
        }

        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (event.getAction() == 0 && keyCode == 66) {
                boolean okButtonResult = ScratchReconvertDialog.this.handleOkButton();
                if (okButtonResult) {
                    ScratchReconvertDialog.this.dismiss();
                }
                return okButtonResult;
            } else if (keyCode != 4) {
                return false;
            } else {
                Log.d(ScratchReconvertDialog.TAG, "User canceled dialog by pressing Back-button");
                ToastUtil.showError(ScratchReconvertDialog.this.context, (int) R.string.notification_reconvert_download_program_cancel);
                if (ScratchReconvertDialog.this.callback != null) {
                    ScratchReconvertDialog.this.callback.onUserCanceledConversion();
                }
                ScratchReconvertDialog.this.dismiss();
                return true;
            }
        }
    }

    public interface ReconvertDialogCallback {
        void onDownloadExistingProgram();

        void onReconvertProgram();

        void onUserCanceledConversion();
    }

    public void setCachedDate(Date cachedDate) {
        this.cachedDate = cachedDate;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setReconvertDialogCallback(ReconvertDialogCallback callback) {
        this.callback = callback;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String quantityString;
        Dialog dialog;
        View dialogView = View.inflate(getActivity(), R.layout.dialog_scratch_reconvert, null);
        this.downloadExistingProgramRadioButton = (RadioButton) dialogView.findViewById(R.id.dialog_scratch_reconvert_radio_download);
        this.reconvertProgramRadioButton = (RadioButton) dialogView.findViewById(R.id.dialog_scratch_reconvert_radio_reconvert);
        long timeDifferenceInMS = new Date().getTime() - this.cachedDate.getTime();
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(timeDifferenceInMS);
        int hours = (int) TimeUnit.MILLISECONDS.toHours(timeDifferenceInMS);
        int days = (int) TimeUnit.MILLISECONDS.toDays(timeDifferenceInMS);
        if (days > 0) {
            quantityString = getResources().getQuantityString(R.plurals.days, days, new Object[]{Integer.valueOf(days)});
        } else if (hours > 0) {
            quantityString = getResources().getQuantityString(R.plurals.hours, hours, new Object[]{Integer.valueOf(hours)});
        } else {
            quantityString = getResources().getQuantityString(R.plurals.minutes, minutes, new Object[]{Integer.valueOf(minutes)});
            dialog = new AlertDialog$Builder(getActivity()).setView(dialogView).setTitle(getResources().getString(R.string.reconvert_text, new Object[]{quantityString})).setPositiveButton(R.string.ok, new C19242()).setNegativeButton(R.string.cancel, new C19231()).create();
            dialog.setOnShowListener(new C19263());
            dialog.setOnKeyListener(new C19274());
            return dialog;
        }
        dialog = new AlertDialog$Builder(getActivity()).setView(dialogView).setTitle(getResources().getString(R.string.reconvert_text, new Object[]{quantityString})).setPositiveButton(R.string.ok, new C19242()).setNegativeButton(R.string.cancel, new C19231()).create();
        dialog.setOnShowListener(new C19263());
        dialog.setOnKeyListener(new C19274());
        return dialog;
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(TAG, "User canceled dialog by clicking outside of the Dialog fragment");
        ToastUtil.showError(this.context, (int) R.string.notification_reconvert_download_program_cancel);
        if (this.callback != null) {
            this.callback.onUserCanceledConversion();
        }
    }

    private boolean handleOkButton() {
        if (this.downloadExistingProgramRadioButton.isChecked()) {
            this.callback.onDownloadExistingProgram();
        } else if (this.reconvertProgramRadioButton.isChecked()) {
            this.callback.onReconvertProgram();
        }
        dismiss();
        return true;
    }
}
