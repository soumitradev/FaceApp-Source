package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.utils.ToastUtil;

public class StageDialog extends Dialog implements OnClickListener {
    private static final String TAG = StageDialog.class.getSimpleName();
    private StageActivity stageActivity;
    private StageListener stageListener;

    private class FinishThreadAndDisposeTexturesTask extends AsyncTask<Void, Void, Void> {
        private FinishThreadAndDisposeTexturesTask() {
        }

        protected Void doInBackground(Void... params) {
            StageDialog.this.stageActivity.manageLoadAndFinish();
            return null;
        }
    }

    public StageDialog(StageActivity stageActivity, StageListener stageListener, int theme) {
        super(stageActivity, theme);
        this.stageActivity = stageActivity;
        this.stageListener = stageListener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_stage);
        getWindow().getAttributes();
        getWindow().getAttributes();
        getWindow().setLayout(-1, -2);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        ((Button) findViewById(R.id.stage_dialog_button_back)).setOnClickListener(this);
        ((Button) findViewById(R.id.stage_dialog_button_continue)).setOnClickListener(this);
        ((Button) findViewById(R.id.stage_dialog_button_restart)).setOnClickListener(this);
        ((Button) findViewById(R.id.stage_dialog_button_toggle_axes)).setOnClickListener(this);
        ((Button) findViewById(R.id.stage_dialog_button_screenshot)).setOnClickListener(this);
        if (this.stageActivity.getResizePossible()) {
            ((ImageButton) findViewById(R.id.stage_dialog_button_maximize)).setOnClickListener(this);
        } else {
            ((ImageButton) findViewById(R.id.stage_dialog_button_maximize)).setVisibility(8);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stage_dialog_button_back:
                onBackPressed();
                return;
            case R.id.stage_dialog_button_continue:
                onContinuePressed();
                return;
            case R.id.stage_dialog_button_maximize:
                this.stageListener.toggleScreenMode();
                return;
            case R.id.stage_dialog_button_restart:
                onRestartPressed();
                return;
            case R.id.stage_dialog_button_screenshot:
                makeScreenshot();
                return;
            case R.id.stage_dialog_button_toggle_axes:
                toggleAxes();
                return;
            default:
                Log.w(TAG, "Unimplemented button clicked! This shouldn't happen!");
                return;
        }
    }

    public void onBackPressed() {
        clearBroadcastMaps();
        dismiss();
        this.stageActivity.exit();
        new FinishThreadAndDisposeTexturesTask().execute(new Void[]{null, null, null});
    }

    public void onContinuePressed() {
        if (!ProjectManager.getInstance().getCurrentProject().isCastProject() || CastManager.getInstance().isConnected()) {
            dismiss();
            this.stageActivity.resume();
            return;
        }
        ToastUtil.showError(getContext(), this.stageActivity.getResources().getString(R.string.cast_error_not_connected_msg));
    }

    public void onRestartPressed() {
        if (!ProjectManager.getInstance().getCurrentProject().isCastProject() || CastManager.getInstance().isConnected()) {
            clearBroadcastMaps();
            dismiss();
            restartProject();
            return;
        }
        ToastUtil.showError(getContext(), this.stageActivity.getResources().getString(R.string.cast_error_not_connected_msg));
    }

    private void makeScreenshot() {
        if (!ProjectManager.getInstance().getCurrentProject().isCastProject() || CastManager.getInstance().isConnected()) {
            if (this.stageListener.makeManualScreenshot()) {
                ToastUtil.showSuccess(this.stageActivity, (int) R.string.notification_screenshot_ok);
            } else {
                ToastUtil.showError(this.stageActivity, (int) R.string.error_screenshot_failed);
            }
            return;
        }
        ToastUtil.showError(getContext(), this.stageActivity.getResources().getString(R.string.cast_error_not_connected_msg));
    }

    private void restartProject() {
        this.stageListener.reloadProject(this);
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                Log.e(TAG, "Thread activated too early!", e);
            }
        }
        this.stageActivity.resume();
    }

    private void toggleAxes() {
        Button axesToggleButton = (Button) findViewById(R.id.stage_dialog_button_toggle_axes);
        if (this.stageListener.axesOn) {
            this.stageListener.axesOn = false;
            axesToggleButton.setText(R.string.stage_dialog_axes_on);
            return;
        }
        this.stageListener.axesOn = true;
        axesToggleButton.setText(R.string.stage_dialog_axes_off);
    }

    private void clearBroadcastMaps() {
        for (Scene scene : ProjectManager.getInstance().getCurrentProject().getSceneList()) {
            for (Sprite sprite : scene.getSpriteList()) {
                sprite.getIdToEventThreadMap().clear();
            }
        }
    }
}
