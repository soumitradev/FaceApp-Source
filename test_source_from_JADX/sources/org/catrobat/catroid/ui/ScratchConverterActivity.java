package org.catrobat.catroid.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.scratchconverter.Client;
import org.catrobat.catroid.scratchconverter.Client.DownloadCallback;
import org.catrobat.catroid.scratchconverter.ConversionManager;
import org.catrobat.catroid.scratchconverter.ScratchConversionManager;
import org.catrobat.catroid.scratchconverter.WebSocketClient;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.scratchconverter.protocol.Job.DownloadState;
import org.catrobat.catroid.scratchconverter.protocol.Job.State;
import org.catrobat.catroid.scratchconverter.protocol.WebSocketMessageListener;
import org.catrobat.catroid.ui.recyclerview.adapter.ScratchJobAdapter;
import org.catrobat.catroid.ui.recyclerview.fragment.scratchconverter.ScratchProgramsFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.scratchconverter.ScratchSearchResultsFragment;
import org.catrobat.catroid.ui.scratchconverter.BaseInfoViewListener;
import org.catrobat.catroid.ui.scratchconverter.JobViewListener;
import org.catrobat.catroid.utils.Utils;

public class ScratchConverterActivity extends BaseActivity implements BaseInfoViewListener, JobViewListener, DownloadCallback {
    public static final int FRAGMENT_PROJECTS = 1;
    public static final int FRAGMENT_SEARCH = 0;
    public static final String TAG = ScratchConverterActivity.class.getSimpleName();
    private static Client client;
    private View bottomBar;
    private ConversionManager conversionManager;
    private List<Job> finishedJobs = new ArrayList();
    private OnJobListListener jobListListener;
    private List<Job> runningJobs = new ArrayList();
    private ScratchProgramsFragment scratchProjectsFragment;
    private ScratchSearchResultsFragment searchResultsFragment;

    /* renamed from: org.catrobat.catroid.ui.ScratchConverterActivity$1 */
    class C19041 implements OnClickListener {
        C19041() {
        }

        public void onClick(View v) {
            if (ScratchConverterActivity.this.searchResultsFragment.isVisible()) {
                ScratchConverterActivity.this.switchToFragment(1);
            }
            if (ScratchConverterActivity.this.scratchProjectsFragment.isVisible()) {
                ScratchConverterActivity.this.switchToFragment(0);
            }
        }
    }

    public interface OnJobListListener {
        void onJobListChanged();
    }

    public static void setClient(Client converterClient) {
        client = converterClient;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_converter);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String scratchConverter = getString(R.string.main_menu_scratch_converter);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(scratchConverter);
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(getString(R.string.beta));
        SpannableString scratchConverterBeta = new SpannableString(stringBuilder.toString());
        scratchConverterBeta.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.beta_label_color)), scratchConverter.length(), scratchConverterBeta.length(), 33);
        getSupportActionBar().setTitle(scratchConverterBeta);
        client = new WebSocketClient(PreferenceManager.getDefaultSharedPreferences(this).getLong(SharedPreferenceKeys.SCRATCH_CONVERTER_CLIENT_ID_PREFERENCE_KEY, -1), new WebSocketMessageListener());
        this.conversionManager = new ScratchConversionManager(this, client, false);
        this.conversionManager.addBaseInfoViewListener(this);
        this.conversionManager.addGlobalJobViewListener(this);
        this.conversionManager.addGlobalDownloadCallback(this);
        this.searchResultsFragment = new ScratchSearchResultsFragment();
        this.searchResultsFragment.setConversionManager(this.conversionManager);
        this.scratchProjectsFragment = new ScratchProgramsFragment();
        this.scratchProjectsFragment.initializeAdapters(new ScratchJobAdapter(this.runningJobs), new ScratchJobAdapter(this.finishedJobs));
        this.jobListListener = this.scratchProjectsFragment;
        this.bottomBar = findViewById(R.id.bottom_bar);
        this.bottomBar.setVisibility(8);
        this.bottomBar.setOnClickListener(new C19041());
        this.searchResultsFragment.setConversionManager(this.conversionManager);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, this.searchResultsFragment).commit();
    }

    private void switchToFragment(int fragmentPosition) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (fragmentPosition) {
            case 0:
                if (getSupportFragmentManager().getBackStackEntryCount() <= 0) {
                    fragmentTransaction.replace(R.id.fragment_container, this.searchResultsFragment);
                    break;
                } else {
                    getSupportFragmentManager().popBackStack();
                    break;
                }
            case 1:
                fragmentTransaction.replace(R.id.fragment_container, this.scratchProjectsFragment).addToBackStack(ScratchProgramsFragment.TAG);
                break;
            default:
                return;
        }
        fragmentTransaction.commit();
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    protected void onStart() {
        super.onStart();
        this.conversionManager.setCurrentActivity(this);
        if (!client.isAuthenticated()) {
            this.conversionManager.connectAndAuthenticate();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        this.conversionManager.shutdown();
    }

    public void onJobsInfo(Job[] jobs) {
        this.runningJobs.clear();
        this.finishedJobs.clear();
        for (Job job : jobs) {
            if (job.isInProgress()) {
                this.runningJobs.add(job);
            } else if (job.getState() != State.UNSCHEDULED) {
                this.finishedJobs.add(job);
            }
        }
        updateBottomBar();
        this.jobListListener.onJobListChanged();
    }

    private void updateBottomBar() {
        TextView detailsView = (TextView) this.bottomBar.findViewById(R.id.details_view);
        ((TextView) this.bottomBar.findViewById(R.id.title_view)).setText(getResources().getQuantityString(R.plurals.status_in_progress_x_jobs, this.runningJobs.size(), new Object[]{Integer.valueOf(this.runningJobs.size())}));
        detailsView.setText(getResources().getQuantityString(R.plurals.status_completed_x_jobs, this.finishedJobs.size(), new Object[]{Integer.valueOf(this.finishedJobs.size())}));
        if (this.runningJobs.size() <= 0) {
            if (this.finishedJobs.size() <= 0) {
                this.bottomBar.setVisibility(8);
                return;
            }
        }
        this.bottomBar.setVisibility(0);
    }

    public void onError(String errorMessage) {
    }

    public void onJobScheduled(Job job) {
        if (!listContainsJob(this.runningJobs, job)) {
            this.runningJobs.add(0, job);
        }
        updateBottomBar();
        this.jobListListener.onJobListChanged();
    }

    public void onJobReady(Job job) {
        this.jobListListener.onJobListChanged();
    }

    public void onJobStarted(Job job) {
        if (!listContainsJob(this.runningJobs, job)) {
            this.runningJobs.add(0, job);
        }
        updateBottomBar();
        this.jobListListener.onJobListChanged();
    }

    public void onJobProgress(Job job, short progress) {
    }

    public void onJobOutput(Job job, @NonNull String[] lines) {
    }

    public void onJobFinished(Job job) {
        removeFromList(this.runningJobs, job);
        if (!listContainsJob(this.finishedJobs, job)) {
            this.finishedJobs.add(0, job);
        }
        updateBottomBar();
        this.jobListListener.onJobListChanged();
    }

    public void onJobFailed(Job job) {
        removeFromList(this.runningJobs, job);
        if (!listContainsJob(this.finishedJobs, job)) {
            this.finishedJobs.add(0, job);
        }
        updateBottomBar();
        this.jobListListener.onJobListChanged();
    }

    public void onUserCanceledJob(Job job) {
        removeFromList(this.runningJobs, job);
        if (!listContainsJob(this.finishedJobs, job)) {
            this.finishedJobs.add(0, job);
        }
        updateBottomBar();
        this.jobListListener.onJobListChanged();
    }

    public void onDownloadStarted(String url) {
        long jobId = Utils.extractScratchJobIDFromURL(url);
        for (Job job : this.finishedJobs) {
            if (job.getJobID() == jobId) {
                job.setDownloadState(DownloadState.DOWNLOADING);
            }
        }
        this.jobListListener.onJobListChanged();
    }

    public void onDownloadProgress(short progress, String url) {
    }

    public void onDownloadFinished(String catrobatProgramName, String url) {
        long jobId = Utils.extractScratchJobIDFromURL(url);
        for (Job job : this.finishedJobs) {
            if (job.getJobID() == jobId) {
                job.setDownloadState(DownloadState.DOWNLOADED);
            }
        }
        this.jobListListener.onJobListChanged();
    }

    public void onUserCanceledDownload(String url) {
        long jobId = Utils.extractScratchJobIDFromURL(url);
        for (Job job : this.finishedJobs) {
            if (job.getJobID() == jobId) {
                job.setDownloadState(DownloadState.CANCELED);
            }
        }
        this.jobListListener.onJobListChanged();
    }

    private boolean listContainsJob(List<Job> list, Job job) {
        for (Job jobInList : list) {
            if (jobInList.getJobID() == job.getJobID()) {
                return true;
            }
        }
        return false;
    }

    private boolean removeFromList(List<Job> list, Job job) {
        for (Job jobInList : list) {
            if (jobInList.getJobID() == job.getJobID()) {
                list.remove(jobInList);
                return true;
            }
        }
        return false;
    }
}
