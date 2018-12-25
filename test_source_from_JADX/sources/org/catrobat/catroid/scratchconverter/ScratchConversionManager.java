package org.catrobat.catroid.scratchconverter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.android.gms.common.images.WebImage;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.scratchconverter.Client.DownloadCallback;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.scratchconverter.protocol.Job.DownloadState;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog;
import org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog.ReconvertDialogCallback;
import org.catrobat.catroid.ui.scratchconverter.BaseInfoViewListener;
import org.catrobat.catroid.ui.scratchconverter.JobViewListener;
import org.catrobat.catroid.utils.DownloadUtil;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.json.JSONException;
import org.json.JSONObject;

public class ScratchConversionManager implements ConversionManager {
    private static final String TAG = ScratchConversionManager.class.getSimpleName();
    private Set<BaseInfoViewListener> baseInfoViewListeners;
    private final Client client;
    private AppCompatActivity currentActivity;
    private Map<String, DownloadCallback> downloadCallbacks = new HashMap();
    private Set<DownloadCallback> globalDownloadCallbacks = Collections.synchronizedSet(new HashSet());
    private Set<JobViewListener> globalJobViewListeners;
    private Map<Long, Set<JobViewListener>> jobViewListeners;
    private boolean shutdown;
    private final boolean verbose;

    /* renamed from: org.catrobat.catroid.scratchconverter.ScratchConversionManager$2 */
    class C18652 implements Runnable {
        C18652() {
        }

        public void run() {
            ToastUtil.showError(ScratchConversionManager.this.currentActivity, (int) R.string.connection_failed);
            ScratchConversionManager.this.closeAllActivities();
        }
    }

    /* renamed from: org.catrobat.catroid.scratchconverter.ScratchConversionManager$3 */
    class C18663 implements Runnable {
        C18663() {
        }

        public void run() {
            ToastUtil.showError(ScratchConversionManager.this.currentActivity, (int) R.string.authentication_failed);
            ScratchConversionManager.this.closeAllActivities();
        }
    }

    @SuppressLint({"UseSparseArrays"})
    public ScratchConversionManager(AppCompatActivity rootActivity, Client client, boolean verbose) {
        this.currentActivity = rootActivity;
        this.client = client;
        this.verbose = verbose;
        client.setConvertCallback(this);
        this.jobViewListeners = Collections.synchronizedMap(new HashMap());
        this.globalJobViewListeners = Collections.synchronizedSet(new HashSet());
        this.baseInfoViewListeners = Collections.synchronizedSet(new HashSet());
        this.shutdown = false;
        DownloadUtil.getInstance().setDownloadCallback(this);
    }

    public void setCurrentActivity(AppCompatActivity activity) {
        this.currentActivity = activity;
    }

    public void addGlobalDownloadCallback(DownloadCallback callback) {
        this.globalDownloadCallbacks.add(callback);
    }

    public boolean removeGlobalDownloadCallback(DownloadCallback callback) {
        return this.globalDownloadCallbacks.remove(callback);
    }

    public boolean isJobInProgress(long jobID) {
        return this.client.isJobInProgress(jobID);
    }

    public boolean isJobDownloading(long jobID) {
        return readDownloadStateFromDisk(jobID) == DownloadState.DOWNLOADING;
    }

    public int getNumberOfJobsInProgress() {
        return this.client.getNumberOfJobsInProgress();
    }

    public void connectAndAuthenticate() {
        this.client.connectAndAuthenticate(this);
    }

    public void shutdown() {
        this.shutdown = true;
        DownloadUtil.getInstance().setDownloadCallback(null);
        if (!this.client.isClosed()) {
            this.client.close();
        }
    }

    public void convertProgram(long jobID, String title, WebImage image, boolean force) {
        updateDownloadStateOnDisk(jobID, DownloadState.NOT_READY);
        this.client.convertProgram(jobID, title, image, this.verbose, force);
    }

    private void closeAllActivities() {
        if (!this.shutdown) {
            Intent intent = new Intent(this.currentActivity.getApplicationContext(), MainMenuActivity.class);
            intent.addFlags(67108864);
            this.currentActivity.startActivity(intent);
        }
    }

    public void onSuccess(long clientID) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this.currentActivity.getApplicationContext()).edit();
        editor.putLong(SharedPreferenceKeys.SCRATCH_CONVERTER_CLIENT_ID_PREFERENCE_KEY, clientID);
        editor.commit();
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Connection established (clientID: ");
        stringBuilder.append(clientID);
        stringBuilder.append(")");
        Log.i(str, stringBuilder.toString());
        Preconditions.checkState(this.client.isAuthenticated());
        this.client.retrieveInfo();
    }

    public void onConnectionClosed(ClientException ex) {
        Log.d(TAG, "Connection closed!");
        final String exceptionMessage = ex.getMessage();
        this.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                if (exceptionMessage != null) {
                    Log.e(ScratchConversionManager.TAG, exceptionMessage);
                }
                if (!ScratchConversionManager.this.shutdown) {
                    ToastUtil.showError(ScratchConversionManager.this.currentActivity, (int) R.string.connection_lost_or_closed_by_server);
                }
                ScratchConversionManager.this.closeAllActivities();
            }
        });
    }

    public void onConnectionFailure(ClientException ex) {
        Log.e(TAG, ex.getMessage());
        this.currentActivity.runOnUiThread(new C18652());
    }

    public void onAuthenticationFailure(ClientException ex) {
        Log.e(TAG, ex.getMessage());
        this.currentActivity.runOnUiThread(new C18663());
    }

    public void addBaseInfoViewListener(BaseInfoViewListener baseInfoViewListener) {
        this.baseInfoViewListeners.add(baseInfoViewListener);
    }

    public boolean removeBaseInfoViewListener(BaseInfoViewListener baseInfoViewListener) {
        return this.baseInfoViewListeners.remove(baseInfoViewListener);
    }

    public void addGlobalJobViewListener(JobViewListener jobViewListener) {
        this.globalJobViewListeners.add(jobViewListener);
    }

    public boolean removeGlobalJobViewListener(JobViewListener jobViewListener) {
        return this.globalJobViewListeners.remove(jobViewListener);
    }

    public void addJobViewListener(long jobID, JobViewListener jobViewListener) {
        Set<JobViewListener> listeners = (Set) this.jobViewListeners.get(Long.valueOf(jobID));
        if (listeners == null) {
            listeners = new HashSet();
        }
        listeners.add(jobViewListener);
        this.jobViewListeners.put(Long.valueOf(jobID), listeners);
    }

    public boolean removeJobViewListener(long jobID, JobViewListener jobViewListener) {
        Set<JobViewListener> listeners = (Set) this.jobViewListeners.get(Long.valueOf(jobID));
        return listeners != null && listeners.remove(jobViewListener);
    }

    @NonNull
    private JobViewListener[] getJobViewListeners(long jobID) {
        Set<JobViewListener> mergedListenersList = new HashSet();
        Set<JobViewListener> listenersList = (Set) this.jobViewListeners.get(Long.valueOf(jobID));
        if (listenersList != null) {
            mergedListenersList.addAll(listenersList);
        }
        mergedListenersList.addAll(this.globalJobViewListeners);
        return (JobViewListener[]) mergedListenersList.toArray(new JobViewListener[mergedListenersList.size()]);
    }

    public void onInfo(final float supportedCatrobatLanguageVersion, final Job[] jobs) {
        for (Job job : jobs) {
            job.setDownloadState(readDownloadStateFromDisk(job.getJobID()));
        }
        this.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                String access$000 = ScratchConversionManager.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Supported Catrobat Language version: ");
                stringBuilder.append(supportedCatrobatLanguageVersion);
                Log.i(access$000, stringBuilder.toString());
                for (BaseInfoViewListener viewListener : ScratchConversionManager.this.baseInfoViewListeners) {
                    viewListener.onJobsInfo(jobs);
                }
                if (0.998f < supportedCatrobatLanguageVersion) {
                    AlertDialog$Builder builder = new AlertDialog$Builder(ScratchConversionManager.this.currentActivity);
                    builder.setTitle(R.string.warning);
                    builder.setMessage(R.string.error_scratch_converter_outdated_app_version);
                    builder.setNeutralButton(R.string.close, null);
                    builder.create().show();
                }
            }
        });
    }

    public void onJobScheduled(final Job job) {
        this.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                for (JobViewListener viewListener : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                    viewListener.onJobScheduled(job);
                }
            }
        });
    }

    public void onConversionReady(final Job job) {
        Log.i(TAG, "Conversion ready!");
        this.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                for (JobViewListener viewListener : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                    viewListener.onJobReady(job);
                }
            }
        });
    }

    public void onConversionStart(final Job job) {
        Log.i(TAG, "Conversion started!");
        this.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                ToastUtil.showSuccess(ScratchConversionManager.this.currentActivity, ScratchConversionManager.this.currentActivity.getString(R.string.scratch_conversion_started));
                for (JobViewListener viewListener : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                    viewListener.onJobStarted(job);
                }
            }
        });
    }

    public void onConversionFinished(Job job, DownloadCallback downloadCallback, String downloadURL, Date cachedUTCDate) {
        Log.i(TAG, "Conversion finished!");
        updateDownloadStateOnDisk(job.getJobID(), DownloadState.READY);
        conversionFinished(job, downloadCallback, downloadURL, cachedUTCDate);
    }

    public void onConversionAlreadyFinished(Job job, DownloadCallback downloadCallback, String downloadURL) {
        if (readDownloadStateFromDisk(job.getJobID()) == DownloadState.NOT_READY) {
            updateDownloadStateOnDisk(job.getJobID(), DownloadState.READY);
        }
        conversionFinished(job, downloadCallback, downloadURL, null);
    }

    private void conversionFinished(Job job, DownloadCallback downloadCallback, String downloadURL, Date cachedUTCDate) {
        String baseUrl = Constants.SCRATCH_CONVERTER_BASE_URL;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.SCRATCH_CONVERTER_BASE_URL.substring(0, Constants.SCRATCH_CONVERTER_BASE_URL.length() - 1));
        String str = downloadURL;
        stringBuilder.append(str);
        String fullDownloadURL = stringBuilder.toString();
        DownloadState localDownloadState = readDownloadStateFromDisk(job.getJobID());
        if (localDownloadState == DownloadState.READY || localDownloadState == DownloadState.DOWNLOADING) {
            final DownloadState finalLocalDownloadState = localDownloadState;
            final Job job2 = job;
            final String str2 = str;
            final DownloadCallback downloadCallback2 = downloadCallback;
            final String str3 = fullDownloadURL;
            final Date date = cachedUTCDate;
            r8.currentActivity.runOnUiThread(new Runnable() {

                /* renamed from: org.catrobat.catroid.scratchconverter.ScratchConversionManager$8$1 */
                class C21111 implements ReconvertDialogCallback {
                    C21111() {
                    }

                    public void onDownloadExistingProgram() {
                        ScratchConversionManager.this.downloadProgram(str3);
                    }

                    public void onReconvertProgram() {
                        ScratchConversionManager.this.convertProgram(job2.getJobID(), job2.getTitle(), job2.getImage(), true);
                    }

                    public void onUserCanceledConversion() {
                        ScratchConversionManager.this.client.onUserCanceledConversion(job2.getJobID());
                        for (JobViewListener viewListener : ScratchConversionManager.this.getJobViewListeners(job2.getJobID())) {
                            viewListener.onUserCanceledJob(job2);
                        }
                    }
                }

                public void run() {
                    for (JobViewListener viewListener : ScratchConversionManager.this.getJobViewListeners(job2.getJobID())) {
                        viewListener.onJobFinished(job2);
                    }
                    ScratchConversionManager.this.downloadCallbacks.put(str2, downloadCallback2);
                    if (finalLocalDownloadState == DownloadState.DOWNLOADING) {
                        Log.i(ScratchConversionManager.TAG, "Download of converted project is already RUNNNING!!");
                        ScratchConversionManager.this.onDownloadStarted(str3);
                        return;
                    }
                    Log.i(ScratchConversionManager.TAG, "Downloading missed converted project...");
                    if (date != null) {
                        ScratchReconvertDialog reconvertDialog = new ScratchReconvertDialog();
                        reconvertDialog.setContext(ScratchConversionManager.this.currentActivity);
                        reconvertDialog.setCachedDate(date);
                        reconvertDialog.setReconvertDialogCallback(new C21111());
                        reconvertDialog.show(ScratchConversionManager.this.currentActivity.getSupportFragmentManager(), ScratchReconvertDialog.DIALOG_FRAGMENT_TAG);
                        return;
                    }
                    ScratchConversionManager.this.downloadProgram(str3);
                }
            });
        }
    }

    private void downloadProgram(String fullDownloadURL) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Start download: ");
        stringBuilder.append(fullDownloadURL);
        Log.d(str, stringBuilder.toString());
        DownloadUtil.getInstance().prepareDownloadAndStartIfPossible(this.currentActivity, fullDownloadURL);
    }

    public void onConversionFailure(@Nullable final Job job, ClientException ex) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Conversion failed: ");
        stringBuilder.append(ex.getMessage());
        Log.e(str, stringBuilder.toString());
        this.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                if (job != null) {
                    for (JobViewListener viewListener : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                        viewListener.onJobFailed(job);
                    }
                    ToastUtil.showError(ScratchConversionManager.this.currentActivity, ScratchConversionManager.this.currentActivity.getResources().getString(R.string.error_specific_scratch_program_conversion_failed_x, new Object[]{job.getTitle()}));
                    return;
                }
                ToastUtil.showError(ScratchConversionManager.this.currentActivity, (int) R.string.error_scratch_program_conversion_failed);
                ScratchConversionManager.this.closeAllActivities();
            }
        });
    }

    public void onError(final String errorMessage) {
        this.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                for (BaseInfoViewListener viewListener : ScratchConversionManager.this.baseInfoViewListeners) {
                    viewListener.onError(errorMessage);
                }
            }
        });
    }

    public void onJobOutput(final Job job, final String[] lines) {
        this.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                for (JobViewListener viewListener : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                    viewListener.onJobOutput(job, lines);
                }
            }
        });
    }

    public void onJobProgress(final Job job, final short progress) {
        this.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                for (JobViewListener viewListener : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                    viewListener.onJobProgress(job, progress);
                }
            }
        });
    }

    private void updateDownloadStateOnDisk(long jobID, DownloadState downloadState) {
        Log.d(TAG, "Update download-state of program on disk");
        try {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.currentActivity.getApplicationContext());
            Editor editor = sharedPref.edit();
            String data = sharedPref.getString(SharedPreferenceKeys.SCRATCH_CONVERTER_DOWNLOAD_STATE_PREFERENCE_KEY, null);
            HashMap<String, String> downloadStates = new HashMap();
            if (data != null) {
                JSONObject jsonObject = new JSONObject(data);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = (String) keysItr.next();
                    downloadStates.put(key, jsonObject.getString(key));
                }
            }
            downloadStates.put(Long.toString(jobID), Integer.toString(downloadState.getDownloadStateID()));
            Log.d(TAG, downloadStates.toString());
            editor.putString(SharedPreferenceKeys.SCRATCH_CONVERTER_DOWNLOAD_STATE_PREFERENCE_KEY, new JSONObject(downloadStates).toString());
            editor.commit();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private DownloadState readDownloadStateFromDisk(long jobID) {
        Log.d(TAG, "Read download-state of program from disk");
        try {
            String data = PreferenceManager.getDefaultSharedPreferences(this.currentActivity.getApplicationContext()).getString(SharedPreferenceKeys.SCRATCH_CONVERTER_DOWNLOAD_STATE_PREFERENCE_KEY, null);
            HashMap<String, String> downloadStates = new HashMap();
            if (data != null) {
                JSONObject jsonObject = new JSONObject(data);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = (String) keysItr.next();
                    downloadStates.put(key, jsonObject.getString(key));
                }
            }
            String result = (String) downloadStates.get(Long.toString(jobID));
            if (result == null) {
                return DownloadState.NOT_READY;
            }
            return DownloadState.valueOf(Integer.parseInt(result));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return DownloadState.NOT_READY;
        }
    }

    public void onDownloadStarted(final String url) {
        updateDownloadStateOnDisk(Utils.extractScratchJobIDFromURL(url), DownloadState.DOWNLOADING);
        this.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                DownloadCallback callback = (DownloadCallback) ScratchConversionManager.this.downloadCallbacks.get(url);
                if (callback != null) {
                    callback.onDownloadStarted(url);
                }
                for (DownloadCallback cb : ScratchConversionManager.this.globalDownloadCallbacks) {
                    cb.onDownloadStarted(url);
                }
            }
        });
    }

    public void onDownloadProgress(final short progress, final String url) {
        this.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                DownloadCallback callback = (DownloadCallback) ScratchConversionManager.this.downloadCallbacks.get(url);
                if (callback != null) {
                    callback.onDownloadProgress(progress, url);
                }
                for (DownloadCallback cb : ScratchConversionManager.this.globalDownloadCallbacks) {
                    cb.onDownloadProgress(progress, url);
                }
            }
        });
    }

    public void onDownloadFinished(final String catrobatProgramName, final String url) {
        updateDownloadStateOnDisk(Utils.extractScratchJobIDFromURL(url), DownloadState.DOWNLOADED);
        this.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                DownloadCallback callback = (DownloadCallback) ScratchConversionManager.this.downloadCallbacks.get(url);
                if (callback != null) {
                    callback.onDownloadFinished(catrobatProgramName, url);
                }
                for (DownloadCallback cb : ScratchConversionManager.this.globalDownloadCallbacks) {
                    cb.onDownloadFinished(catrobatProgramName, url);
                }
            }
        });
    }

    public void onUserCanceledDownload(String url) {
        updateDownloadStateOnDisk(Utils.extractScratchJobIDFromURL(url), DownloadState.CANCELED);
        DownloadCallback callback = (DownloadCallback) this.downloadCallbacks.get(url);
        if (callback != null) {
            callback.onUserCanceledDownload(url);
        }
        for (DownloadCallback cb : this.globalDownloadCallbacks) {
            cb.onUserCanceledDownload(url);
        }
    }
}
