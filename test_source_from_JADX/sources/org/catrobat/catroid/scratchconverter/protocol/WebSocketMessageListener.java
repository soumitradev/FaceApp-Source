package org.catrobat.catroid.scratchconverter.protocol;

import android.util.Log;
import com.google.common.base.Preconditions;
import com.koushikdutta.async.http.WebSocket.StringCallback;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.catrobat.catroid.scratchconverter.Client.ConvertCallback;
import org.catrobat.catroid.scratchconverter.Client.DownloadCallback;
import org.catrobat.catroid.scratchconverter.protocol.Job.State;
import org.catrobat.catroid.scratchconverter.protocol.JsonKeys.JsonDataKeys;
import org.catrobat.catroid.scratchconverter.protocol.message.Message.CategoryType;
import org.catrobat.catroid.scratchconverter.protocol.message.base.BaseMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class WebSocketMessageListener implements MessageListener, StringCallback {
    private static final String TAG = WebSocketMessageListener.class.getSimpleName();
    private BaseMessageHandler baseMessageHandler = null;
    private Map<Long, JobHandler> jobHandlers = Collections.synchronizedMap(new LinkedHashMap());

    public void setBaseMessageHandler(BaseMessageHandler baseMessageHandler) {
        this.baseMessageHandler = baseMessageHandler;
    }

    public synchronized void onStringAvailable(String s) {
        if (s != null) {
            try {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Receiving new message: ");
                stringBuilder.append(s);
                Log.d(str, stringBuilder.toString());
                JSONObject jsonMessage = new JSONObject(s);
                if (jsonMessage.length() != 0) {
                    CategoryType categoryType = CategoryType.valueOf(jsonMessage.getInt(JsonKeys.CATEGORY.toString()));
                    switch (categoryType) {
                        case BASE:
                            this.baseMessageHandler.onBaseMessage(BaseMessage.fromJson(jsonMessage));
                            break;
                        case JOB:
                            long jobID = jsonMessage.getJSONObject(JsonKeys.DATA.toString()).getLong(JsonDataKeys.JOB_ID.toString());
                            JobHandler jobHandler = (JobHandler) this.jobHandlers.get(Long.valueOf(jobID));
                            if (jobHandler == null) {
                                String str2 = TAG;
                                StringBuilder stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("No JobHandler registered for job with ID: ");
                                stringBuilder2.append(jobID);
                                Log.w(str2, stringBuilder2.toString());
                                break;
                            }
                            jobHandler.onJobMessage(JobMessage.fromJson(jsonMessage));
                            break;
                        default:
                            String str3 = TAG;
                            StringBuilder stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("Message of unsupported category-type ");
                            stringBuilder3.append(categoryType);
                            stringBuilder3.append(" received");
                            Log.w(str3, stringBuilder3.toString());
                            break;
                    }
                }
                return;
            } catch (JSONException ex) {
                Log.e(TAG, ex.getMessage());
            }
        } else {
            return;
        }
        return;
    }

    public boolean isJobInProgress(long jobID) {
        JobHandler jobHandler = (JobHandler) this.jobHandlers.get(Long.valueOf(jobID));
        if (jobHandler == null) {
            return false;
        }
        return jobHandler.isInProgress();
    }

    public void onUserCanceledConversion(long jobID) {
        JobHandler jobHandler = (JobHandler) this.jobHandlers.get(Long.valueOf(jobID));
        if (jobHandler != null) {
            jobHandler.onUserCanceledConversion();
        }
    }

    public int getNumberOfJobsInProgress() {
        int numberOfJobsInProgress = 0;
        for (JobHandler jobHandler : this.jobHandlers.values()) {
            if (jobHandler.isInProgress()) {
                numberOfJobsInProgress++;
            }
        }
        return numberOfJobsInProgress;
    }

    private JobHandler createOrUseExistingJobHandlerForJobIfPossible(Job job, boolean force, ConvertCallback convertCallback) {
        JobHandler jobHandler = (JobHandler) this.jobHandlers.get(Long.valueOf(job.getJobID()));
        String str;
        StringBuilder stringBuilder;
        if (jobHandler != null) {
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("JobHandler for jobID ");
            stringBuilder.append(job.getJobID());
            stringBuilder.append(" already exists!");
            Log.d(str, stringBuilder.toString());
            if (!force && jobHandler.isInProgress()) {
                return null;
            }
            jobHandler.setCallback(convertCallback);
        } else {
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Creating new JobHandler for jobID ");
            stringBuilder.append(job.getJobID());
            Log.d(str, stringBuilder.toString());
            jobHandler = new JobHandler(job, convertCallback);
            this.jobHandlers.put(Long.valueOf(jobHandler.getJobID()), jobHandler);
        }
        return jobHandler;
    }

    public boolean scheduleJob(Job job, boolean force, ConvertCallback convertCallback) {
        JobHandler jobHandler = createOrUseExistingJobHandlerForJobIfPossible(job, force, convertCallback);
        if (jobHandler == null) {
            return false;
        }
        jobHandler.onJobScheduled();
        return true;
    }

    public DownloadCallback restoreJobIfRunning(Job job, ConvertCallback convertCallback) {
        boolean z = true;
        JobHandler jobHandler = createOrUseExistingJobHandlerForJobIfPossible(job, true, convertCallback);
        if (jobHandler == null) {
            z = false;
        }
        Preconditions.checkState(z);
        if (job.getState() != State.FINISHED || job.getDownloadURL() == null) {
            return null;
        }
        return jobHandler;
    }
}
