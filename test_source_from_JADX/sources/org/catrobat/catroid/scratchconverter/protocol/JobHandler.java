package org.catrobat.catroid.scratchconverter.protocol;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.images.WebImage;
import com.google.common.base.Preconditions;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.scratchconverter.Client.ConvertCallback;
import org.catrobat.catroid.scratchconverter.Client.DownloadCallback;
import org.catrobat.catroid.scratchconverter.ClientException;
import org.catrobat.catroid.scratchconverter.protocol.Job.DownloadState;
import org.catrobat.catroid.scratchconverter.protocol.Job.State;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobAlreadyRunningMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobFailedMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobFinishedMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobOutputMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobProgressMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobReadyMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobRunningMessage;

public class JobHandler implements DownloadCallback {
    private static final String TAG = JobHandler.class.getSimpleName();
    private ConvertCallback callback;
    private final Job job;

    public JobHandler(@NonNull Job job, @NonNull ConvertCallback callback) {
        Preconditions.checkArgument(job != null);
        this.job = job;
        this.callback = callback;
    }

    public boolean isInProgress() {
        return this.job.isInProgress();
    }

    public void onJobScheduled() {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Setting job as scheduled (jobID: ");
        stringBuilder.append(this.job.getJobID());
        stringBuilder.append(")");
        Log.d(str, stringBuilder.toString());
        this.job.setState(State.SCHEDULED);
        this.callback.onJobScheduled(this.job);
    }

    public void onDownloadStarted(String url) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Download started - Job ID is: ");
        stringBuilder.append(this.job.getJobID());
        Log.d(str, stringBuilder.toString());
        this.job.setDownloadState(DownloadState.DOWNLOADING);
        this.job.setState(State.FINISHED);
    }

    public void onDownloadProgress(short progress, String url) {
    }

    public void onDownloadFinished(String programName, String url) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Download finished - Resetting job with ID: ");
        stringBuilder.append(this.job.getJobID());
        Log.d(str, stringBuilder.toString());
        this.job.setDownloadState(DownloadState.DOWNLOADED);
        this.job.setState(State.FINISHED);
    }

    public void onUserCanceledDownload(String url) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User canceled download - Resetting job with ID: ");
        stringBuilder.append(this.job.getJobID());
        Log.d(str, stringBuilder.toString());
        this.job.setDownloadState(DownloadState.CANCELED);
        this.job.setState(State.FINISHED);
    }

    public void onUserCanceledConversion() {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User canceled conversion - Resetting job with ID: ");
        stringBuilder.append(this.job.getJobID());
        Log.d(str, stringBuilder.toString());
        this.job.setState(State.FINISHED);
    }

    public Job getJob() {
        return this.job;
    }

    public long getJobID() {
        return this.job.getJobID();
    }

    public void setCallback(@NonNull ConvertCallback callback) {
        this.callback = callback;
    }

    public void onJobMessage(JobMessage jobMessage) {
        Preconditions.checkArgument(this.job.getJobID() == jobMessage.getJobID());
        Preconditions.checkState(this.job.getState().isInProgress());
        switch (this.job.getState()) {
            case SCHEDULED:
                if (jobMessage instanceof JobReadyMessage) {
                    handleJobReadyMessage((JobReadyMessage) jobMessage);
                    return;
                } else if (jobMessage instanceof JobAlreadyRunningMessage) {
                    handleJobAlreadyRunningMessage((JobAlreadyRunningMessage) jobMessage);
                    return;
                } else if (jobMessage instanceof JobFinishedMessage) {
                    handleJobFinishedMessage((JobFinishedMessage) jobMessage);
                    return;
                } else if (jobMessage instanceof JobFailedMessage) {
                    handleJobFailedMessage((JobFailedMessage) jobMessage);
                    return;
                }
                break;
            case READY:
                if (jobMessage instanceof JobRunningMessage) {
                    handleJobRunningMessage((JobRunningMessage) jobMessage);
                    return;
                }
                break;
            case RUNNING:
                if (jobMessage instanceof JobProgressMessage) {
                    handleJobProgressMessage((JobProgressMessage) jobMessage);
                    return;
                } else if (jobMessage instanceof JobOutputMessage) {
                    handleJobOutputMessage((JobOutputMessage) jobMessage);
                    return;
                } else if (jobMessage instanceof JobFinishedMessage) {
                    handleJobFinishedMessage((JobFinishedMessage) jobMessage);
                    return;
                } else if (jobMessage instanceof JobFailedMessage) {
                    handleJobFailedMessage((JobFailedMessage) jobMessage);
                    return;
                }
                break;
            default:
                break;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to handle message of type in current state ");
        stringBuilder.append(this.job.getState());
        Log.w(str, stringBuilder.toString());
    }

    private void handleJobReadyMessage(@NonNull JobReadyMessage jobReadyMessage) {
        boolean z = false;
        Preconditions.checkArgument(getJob().getJobID() == jobReadyMessage.getJobID());
        if (this.job.getState() == State.SCHEDULED) {
            z = true;
        }
        Preconditions.checkState(z);
        this.job.setState(State.READY);
        this.callback.onConversionReady(this.job);
    }

    private void handleJobAlreadyRunningMessage(@NonNull JobAlreadyRunningMessage jobAlreadyRunningMessage) {
        boolean z = false;
        Preconditions.checkArgument(getJob().getJobID() == jobAlreadyRunningMessage.getJobID());
        if (this.job.getState() == State.SCHEDULED) {
            z = true;
        }
        Preconditions.checkState(z);
        this.job.setState(State.READY);
        handleJobRunningMessage(new JobRunningMessage(jobAlreadyRunningMessage.getJobID(), jobAlreadyRunningMessage.getJobTitle(), jobAlreadyRunningMessage.getJobImageURL()));
    }

    private void handleJobRunningMessage(@NonNull JobRunningMessage jobRunningMessage) {
        Preconditions.checkArgument(getJob().getJobID() == jobRunningMessage.getJobID());
        Preconditions.checkState(this.job.getState() == State.READY);
        this.job.setTitle(jobRunningMessage.getJobTitle());
        String jobImageURL = jobRunningMessage.getJobImageURL();
        if (jobImageURL != null) {
            int[] imageSize = new int[]{480, Constants.SCRATCH_IMAGE_DEFAULT_HEIGHT};
            this.job.setImage(new WebImage(Uri.parse(jobImageURL), imageSize[0], imageSize[1]));
        }
        this.job.setState(State.RUNNING);
        this.callback.onConversionStart(this.job);
    }

    private void handleJobProgressMessage(@NonNull JobProgressMessage jobProgressMessage) {
        boolean z = false;
        Preconditions.checkArgument(getJob().getJobID() == jobProgressMessage.getJobID());
        if (this.job.getState() == State.RUNNING) {
            z = true;
        }
        Preconditions.checkState(z);
        this.job.setProgress(jobProgressMessage.getProgress());
        this.callback.onJobProgress(this.job, jobProgressMessage.getProgress());
    }

    private void handleJobOutputMessage(@NonNull JobOutputMessage jobOutputMessage) {
        boolean z = true;
        Preconditions.checkArgument(getJob().getJobID() == jobOutputMessage.getJobID());
        if (this.job.getState() != State.RUNNING) {
            z = false;
        }
        Preconditions.checkState(z);
        String[] lines = jobOutputMessage.getLines();
        for (String line : lines) {
            Log.d(TAG, line);
        }
        this.callback.onJobOutput(this.job, lines);
    }

    private void handleJobFinishedMessage(@NonNull JobFinishedMessage jobFinishedMessage) {
        boolean z = false;
        Preconditions.checkArgument(getJob().getJobID() == jobFinishedMessage.getJobID());
        if (this.job.getState() != State.SCHEDULED) {
            if (this.job.getState() != State.RUNNING) {
                Preconditions.checkState(z);
                this.job.setState(State.FINISHED);
                this.job.setDownloadURL(jobFinishedMessage.getDownloadURL());
                this.callback.onConversionFinished(this.job, this, jobFinishedMessage.getDownloadURL(), jobFinishedMessage.getCachedDate());
            }
        }
        z = true;
        Preconditions.checkState(z);
        this.job.setState(State.FINISHED);
        this.job.setDownloadURL(jobFinishedMessage.getDownloadURL());
        this.callback.onConversionFinished(this.job, this, jobFinishedMessage.getDownloadURL(), jobFinishedMessage.getCachedDate());
    }

    private void handleJobFailedMessage(@NonNull JobFailedMessage jobFailedMessage) {
        ConvertCallback convertCallback;
        Job job;
        StringBuilder stringBuilder;
        boolean z = false;
        Preconditions.checkArgument(getJob().getJobID() == jobFailedMessage.getJobID());
        if (this.job.getState() != State.SCHEDULED) {
            if (this.job.getState() != State.RUNNING) {
                Preconditions.checkState(z);
                this.job.setState(State.FAILED);
                convertCallback = this.callback;
                job = this.job;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Job failed - Reason: ");
                stringBuilder.append(jobFailedMessage.getMessage());
                convertCallback.onConversionFailure(job, new ClientException(stringBuilder.toString()));
            }
        }
        z = true;
        Preconditions.checkState(z);
        this.job.setState(State.FAILED);
        convertCallback = this.callback;
        job = this.job;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Job failed - Reason: ");
        stringBuilder.append(jobFailedMessage.getMessage());
        convertCallback.onConversionFailure(job, new ClientException(stringBuilder.toString()));
    }
}
