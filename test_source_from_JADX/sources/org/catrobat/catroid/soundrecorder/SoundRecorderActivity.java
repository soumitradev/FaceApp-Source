package org.catrobat.catroid.soundrecorder;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import java.io.IOException;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.soundrecorder.RecordButton.RecordState;
import org.catrobat.catroid.ui.BaseActivity;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;

public class SoundRecorderActivity extends BaseActivity implements OnClickListener {
    private static final String TAG = SoundRecorderActivity.class.getSimpleName();
    private RecordButton recordButton;
    private SoundRecorder soundRecorder;
    private Chronometer timeRecorderChronometer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundrecorder);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.soundrecorder_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!Utils.isExternalStorageAvailable()) {
            ToastUtil.showError((Context) this, (int) R.string.error_no_writiable_external_storage_available);
            finish();
        }
        this.recordButton = (RecordButton) findViewById(R.id.soundrecorder_record_button);
        this.timeRecorderChronometer = (Chronometer) findViewById(R.id.soundrecorder_chronometer_time_recorded);
        this.recordButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() != R.id.soundrecorder_record_button) {
            return;
        }
        if (this.soundRecorder == null || !this.soundRecorder.isRecording()) {
            startRecording();
            this.timeRecorderChronometer.setBase(SystemClock.elapsedRealtime());
            this.timeRecorderChronometer.start();
            return;
        }
        stopRecording();
        this.timeRecorderChronometer.stop();
        finish();
    }

    public void onBackPressed() {
        stopRecording();
        super.onBackPressed();
    }

    private synchronized void startRecording() {
        if (this.soundRecorder == null || !this.soundRecorder.isRecording()) {
            try {
                if (this.soundRecorder != null) {
                    this.soundRecorder.stop();
                }
                String recordPath = new String[2];
                recordPath[0] = Constants.TMP_PATH;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(getString(R.string.soundrecorder_recorded_filename));
                stringBuilder.append(SoundRecorder.RECORDING_EXTENSION);
                recordPath[1] = stringBuilder.toString();
                this.soundRecorder = new SoundRecorder(PathBuilder.buildPath(recordPath));
                this.soundRecorder.start();
                setViewsToRecordingState();
            } catch (IOException e) {
                Log.e(TAG, "Error recording sound.", e);
                ToastUtil.showError((Context) this, (int) R.string.soundrecorder_error);
            } catch (IllegalStateException e2) {
                Log.e(TAG, "Error recording sound (Other recorder running?).", e2);
                ToastUtil.showError((Context) this, (int) R.string.soundrecorder_error);
            } catch (RuntimeException e3) {
                Log.e(TAG, "Device does not support audio or video format.", e3);
                ToastUtil.showError((Context) this, (int) R.string.soundrecorder_error);
            }
        }
    }

    private void setViewsToRecordingState() {
        this.recordButton.setState(RecordState.RECORD);
        this.recordButton.setImageResource(R.drawable.ic_microphone_active);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void stopRecording() {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.soundRecorder;	 Catch:{ all -> 0x003e }
        if (r0 == 0) goto L_0x003c;
    L_0x0005:
        r0 = r4.soundRecorder;	 Catch:{ all -> 0x003e }
        r0 = r0.isRecording();	 Catch:{ all -> 0x003e }
        if (r0 != 0) goto L_0x000e;
    L_0x000d:
        goto L_0x003c;
    L_0x000e:
        r4.setViewsToNotRecordingState();	 Catch:{ all -> 0x003e }
        r0 = r4.soundRecorder;	 Catch:{ IOException -> 0x0028 }
        r0.stop();	 Catch:{ IOException -> 0x0028 }
        r0 = r4.soundRecorder;	 Catch:{ IOException -> 0x0028 }
        r0 = r0.getPath();	 Catch:{ IOException -> 0x0028 }
        r1 = -1;
        r2 = new android.content.Intent;	 Catch:{ IOException -> 0x0028 }
        r3 = "android.intent.action.PICK";
        r2.<init>(r3, r0);	 Catch:{ IOException -> 0x0028 }
        r4.setResult(r1, r2);	 Catch:{ IOException -> 0x0028 }
        goto L_0x003a;
    L_0x0028:
        r0 = move-exception;
        r1 = TAG;	 Catch:{ all -> 0x003e }
        r2 = "Error recording sound.";
        android.util.Log.e(r1, r2, r0);	 Catch:{ all -> 0x003e }
        r1 = 2131821810; // 0x7f1104f2 float:1.9276374E38 double:1.0532599194E-314;
        org.catrobat.catroid.utils.ToastUtil.showError(r4, r1);	 Catch:{ all -> 0x003e }
        r1 = 0;
        r4.setResult(r1);	 Catch:{ all -> 0x003e }
    L_0x003a:
        monitor-exit(r4);
        return;
    L_0x003c:
        monitor-exit(r4);
        return;
    L_0x003e:
        r0 = move-exception;
        monitor-exit(r4);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.soundrecorder.SoundRecorderActivity.stopRecording():void");
    }

    private void setViewsToNotRecordingState() {
        this.recordButton.setState(RecordState.STOP);
        this.recordButton.setImageResource(R.drawable.ic_microphone);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }
}
