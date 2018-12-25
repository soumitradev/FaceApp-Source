package org.catrobat.catroid.soundrecorder;

import android.media.MediaRecorder;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.io.IOException;

public class SoundRecorder {
    public static final String RECORDING_EXTENSION = ".m4a";
    public static final String TAG = SoundRecorder.class.getSimpleName();
    private boolean isRecording;
    private String path;
    private MediaRecorder recorder = new MediaRecorder();

    public SoundRecorder(String path) {
        this.path = path;
    }

    public void start() throws IOException, RuntimeException {
        File soundFile = new File(this.path);
        if (soundFile.exists()) {
            soundFile.delete();
        }
        File directory = soundFile.getParentFile();
        if (directory.exists() || directory.mkdirs()) {
            try {
                this.recorder.reset();
                this.recorder.setAudioSource(1);
                this.recorder.setOutputFormat(2);
                this.recorder.setAudioEncoder(3);
                this.recorder.setOutputFile(this.path);
                this.recorder.prepare();
                this.recorder.start();
                this.isRecording = true;
                return;
            } catch (IllegalStateException e) {
                throw e;
            } catch (RuntimeException e2) {
                throw e2;
            }
        }
        throw new IOException("Path to file could not be created.");
    }

    public void stop() throws IOException {
        try {
            this.recorder.stop();
        } catch (RuntimeException e) {
            Log.d(TAG, "Note that a RuntimeException is intentionally thrown to the application, if no valid audio/video data has been received when stop() is called. This happens if stop() is called immediately after start(). The failure lets the application take action accordingly to clean up the output file (delete the output file, for instance), since the output file is not properly constructed when this happens.");
        }
        this.recorder.reset();
        this.recorder.release();
        this.isRecording = false;
    }

    public Uri getPath() {
        return Uri.fromFile(new File(this.path));
    }

    public int getMaxAmplitude() {
        return this.recorder.getMaxAmplitude();
    }

    public boolean isRecording() {
        return this.isRecording;
    }
}
