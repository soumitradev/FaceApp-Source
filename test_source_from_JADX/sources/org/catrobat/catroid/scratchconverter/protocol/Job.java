package org.catrobat.catroid.scratchconverter.protocol;

import android.net.Uri;
import android.util.SparseArray;
import com.google.android.gms.common.images.WebImage;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.scratchconverter.protocol.JsonKeys.JsonJobDataKeys;
import org.json.JSONException;
import org.json.JSONObject;

public class Job {
    private short downloadProgress;
    private DownloadState downloadState;
    private String downloadURL;
    private WebImage image;
    private long jobID;
    private short progress;
    private State state = State.UNSCHEDULED;
    private String title;

    public enum DownloadState {
        NOT_READY(0),
        READY(1),
        DOWNLOADING(2),
        DOWNLOADED(3),
        CANCELED(4);
        
        private static SparseArray<DownloadState> downloadStates;
        private int downloadState;

        static {
            downloadStates = new SparseArray();
            DownloadState[] values = values();
            int length = values.length;
            int i;
            while (i < length) {
                DownloadState legEnum = values[i];
                downloadStates.put(legEnum.downloadState, legEnum);
                i++;
            }
        }

        private DownloadState(int downloadState) {
            this.downloadState = downloadState;
        }

        public static DownloadState valueOf(int downloadState) {
            return (DownloadState) downloadStates.get(downloadState);
        }

        public int getDownloadStateID() {
            return this.downloadState;
        }
    }

    public enum State {
        UNSCHEDULED(-2),
        SCHEDULED(-1),
        READY(0),
        RUNNING(1),
        FINISHED(2),
        FAILED(3);
        
        private static SparseArray<State> states;
        private int state;

        static {
            states = new SparseArray();
            State[] values = values();
            int length = values.length;
            int i;
            while (i < length) {
                State legEnum = values[i];
                states.put(legEnum.state, legEnum);
                i++;
            }
        }

        private State(int state) {
            this.state = state;
        }

        public static State valueOf(int state) {
            return (State) states.get(state);
        }

        public boolean isInProgress() {
            if (!(this == SCHEDULED || this == READY)) {
                if (this != RUNNING) {
                    return false;
                }
            }
            return true;
        }

        public int getStateID() {
            return this.state;
        }
    }

    public Job(long jobID, String title, WebImage image) {
        this.jobID = jobID;
        this.title = title;
        this.image = image;
        this.progress = (short) 0;
        this.downloadState = DownloadState.NOT_READY;
        this.downloadURL = null;
    }

    public static Job fromJson(JSONObject data) throws JSONException {
        String imageURL;
        State state = State.valueOf(data.getInt(JsonJobDataKeys.STATE.toString()));
        long jobID = data.getLong(JsonJobDataKeys.JOB_ID.toString());
        String title = data.getString(JsonJobDataKeys.TITLE.toString());
        if (data.isNull(JsonJobDataKeys.IMAGE_URL.toString())) {
            imageURL = null;
        } else {
            imageURL = data.getString(JsonJobDataKeys.IMAGE_URL.toString());
        }
        WebImage image = null;
        if (imageURL != null) {
            int[] imageSize = new int[]{480, Constants.SCRATCH_IMAGE_DEFAULT_HEIGHT};
            image = new WebImage(Uri.parse(imageURL), imageSize[0], imageSize[1]);
        }
        short progress = (short) data.getInt(JsonJobDataKeys.PROGRESS.toString());
        String downloadURL = data.getString(JsonJobDataKeys.DOWNLOAD_URL.toString());
        Job job = new Job(jobID, title, image);
        job.state = state;
        job.progress = progress;
        job.downloadURL = downloadURL;
        return job;
    }

    public State getState() {
        return this.state;
    }

    public boolean isInProgress() {
        return this.state.isInProgress();
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getJobID() {
        return this.jobID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getProgress() {
        return this.progress;
    }

    public void setProgress(short progress) {
        this.progress = progress;
    }

    public short getDownloadProgress() {
        return this.downloadProgress;
    }

    public void setDownloadProgress(short downloadProgress) {
        this.downloadProgress = downloadProgress;
    }

    public WebImage getImage() {
        return this.image;
    }

    public void setImage(WebImage image) {
        this.image = image;
    }

    public DownloadState getDownloadState() {
        return this.downloadState;
    }

    public void setDownloadState(DownloadState downloadState) {
        this.downloadState = downloadState;
    }

    public String getDownloadURL() {
        return this.downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }
}
