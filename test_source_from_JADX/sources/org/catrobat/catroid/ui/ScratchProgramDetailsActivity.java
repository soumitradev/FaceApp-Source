package org.catrobat.catroid.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.common.base.Preconditions;
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.common.ScratchVisibilityState;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.scratchconverter.Client.DownloadCallback;
import org.catrobat.catroid.scratchconverter.ConversionManager;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.transfers.FetchScratchProgramDetailsTask;
import org.catrobat.catroid.transfers.FetchScratchProgramDetailsTask.ScratchProgramListTaskDelegate;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener;
import org.catrobat.catroid.ui.recyclerview.adapter.ScratchProgramAdapter;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;
import org.catrobat.catroid.ui.scratchconverter.JobViewListener;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ScratchDataFetcher;
import org.catrobat.catroid.web.ServerCalls;

public class ScratchProgramDetailsActivity extends BaseActivity implements ScratchProgramListTaskDelegate, JobViewListener, DownloadCallback, OnItemClickListener<ScratchProgramData> {
    public static final String TAG = ScratchProgramDetailsActivity.class.getSimpleName();
    private static ConversionManager conversionManager;
    private static ScratchDataFetcher dataFetcher = ServerCalls.getInstance();
    private ScratchProgramAdapter adapter;
    private Button convertButton;
    private FetchScratchProgramDetailsTask fetchRemixesTask = new FetchScratchProgramDetailsTask();
    private ScratchProgramData programData;
    private ProgressDialog progressDialog;

    /* renamed from: org.catrobat.catroid.ui.ScratchProgramDetailsActivity$1 */
    class C19051 implements OnClickListener {
        C19051() {
        }

        public void onClick(View v) {
            if (ScratchProgramDetailsActivity.conversionManager.getNumberOfJobsInProgress() >= 3) {
                ToastUtil.showError(ScratchProgramDetailsActivity.this.getApplicationContext(), ScratchProgramDetailsActivity.this.getResources().getQuantityString(R.plurals.error_cannot_convert_more_than_x_programs, 3, new Object[]{Integer.valueOf(3)}));
                return;
            }
            ScratchProgramDetailsActivity.this.convertProgram(ScratchProgramDetailsActivity.this.programData);
        }
    }

    public static void setConversionManager(ConversionManager manager) {
        conversionManager = manager;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_project_details);
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
        this.programData = (ScratchProgramData) getIntent().getParcelableExtra(Constants.INTENT_SCRATCH_PROGRAM_DATA);
        Preconditions.checkState(this.programData != null);
        this.convertButton = (Button) findViewById(R.id.convert_button);
        ((TextView) findViewById(R.id.project_title_view)).setText(this.programData.getTitle());
        ((TextView) findViewById(R.id.instructions_view)).setText("-");
        if (conversionManager.isJobInProgress(this.programData.getId())) {
            onJobInProgress();
        } else if (conversionManager.isJobDownloading(this.programData.getId())) {
            onJobDownloading();
        } else {
            onJobNotInProgress();
        }
        conversionManager.addJobViewListener(this.programData.getId(), this);
        conversionManager.addGlobalDownloadCallback(this);
        this.convertButton.setOnClickListener(new C19051());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_remixes);
        this.adapter = new ScratchProgramAdapter(new ArrayList());
        this.adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(this.adapter);
        if (!(this.programData.getImage() == null || this.programData.getImage().getUrl() == null)) {
            Picasso.with(this).load(Utils.changeSizeOfScratchImageURL(this.programData.getImage().getUrl().toString(), getResources().getDimensionPixelSize(R.dimen.scratch_project_image_height))).into((ImageView) findViewById(R.id.project_image_view));
        }
        this.fetchRemixesTask.setContext(this).setDelegate(this).setFetcher(dataFetcher);
        this.fetchRemixesTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Long[]{Long.valueOf(this.programData.getId())});
    }

    private void convertProgram(ScratchProgramData item) {
        if (conversionManager.getNumberOfJobsInProgress() > 3) {
            ToastUtil.showError((Context) this, getResources().getQuantityString(R.plurals.error_cannot_convert_more_than_x_programs, 3, new Object[]{Integer.valueOf(3)}));
        } else if (Utils.isDeprecatedScratchProgram(item)) {
            DateFormat dateFormat = DateFormat.getDateInstance();
            ToastUtil.showError((Context) this, getString(R.string.error_cannot_convert_deprecated_scratch_program_x_x, new Object[]{item.getTitle(), dateFormat.format(Utils.getScratchSecondReleasePublishedDate())}));
        } else if (conversionManager.isJobInProgress(item.getId())) {
            onJobInProgress();
        } else if (conversionManager.isJobDownloading(item.getId())) {
            onJobDownloading();
        } else {
            conversionManager.convertProgram(item.getId(), item.getTitle(), item.getImage(), false);
            ToastUtil.showSuccess((Context) this, getResources().getQuantityString(R.plurals.scratch_conversion_scheduled_x, 1, new Object[]{Integer.valueOf(1)}));
        }
    }

    protected void onStart() {
        super.onStart();
        conversionManager.setCurrentActivity(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        conversionManager.removeJobViewListener(this.programData.getId(), this);
        conversionManager.removeGlobalDownloadCallback(this);
        this.fetchRemixesTask.cancel(true);
        this.progressDialog.dismiss();
    }

    public void onItemClick(ScratchProgramData item) {
        Intent intent = new Intent(this, ScratchProgramDetailsActivity.class);
        intent.putExtra(Constants.INTENT_SCRATCH_PROGRAM_DATA, item);
        startActivityForResult(intent, 1);
    }

    public void onItemLongClick(ScratchProgramData item, CheckableVH h) {
    }

    private void onJobNotInProgress() {
        this.convertButton.setEnabled(true);
        this.convertButton.setText(R.string.convert);
    }

    private void onJobInProgress() {
        this.convertButton.setEnabled(false);
        this.convertButton.setText(R.string.converting);
    }

    private void onJobDownloading() {
        this.convertButton.setEnabled(false);
        this.convertButton.setText(R.string.status_downloading);
    }

    public void onPreExecute() {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setMessage(getString(R.string.loading));
        this.progressDialog.show();
    }

    public void onPostExecute(ScratchProgramData programData) {
        this.progressDialog.dismiss();
        if (programData == null) {
            ToastUtil.showError((Context) this, (int) R.string.error_scratch_project_data_not_available);
            return;
        }
        this.programData = programData;
        onProgramDataUpdated();
    }

    private void onProgramDataUpdated() {
        ((TextView) findViewById(R.id.project_title_view)).setText(this.programData.getTitle());
        ((TextView) findViewById(R.id.owner_view)).setText(getString(R.string.by_x, new Object[]{this.programData.getOwner()}));
        TextView creditsView = (TextView) findViewById(R.id.credits_view);
        if (this.programData.getNotesAndCredits() == null || this.programData.getNotesAndCredits().length() <= 0) {
            findViewById(R.id.credits_title_view).setVisibility(8);
            creditsView.setVisibility(8);
        } else {
            String notesAndCredits = this.programData.getNotesAndCredits().replace("\n\n", "\n");
            findViewById(R.id.credits_title_view).setVisibility(0);
            creditsView.setText(notesAndCredits);
            creditsView.setVisibility(0);
        }
        TextView instructionsView = (TextView) findViewById(R.id.instructions_view);
        if (this.programData.getInstructions() != null) {
            String instructions = this.programData.getInstructions().replace("\n\n", "\n");
            instructionsView.setText(instructions.length() > 0 ? instructions : "-");
        } else {
            instructionsView.setText("-");
        }
        ((TextView) findViewById(R.id.scratch_project_favorites_text)).setText(Utils.humanFriendlyFormattedShortNumber(this.programData.getFavorites()));
        ((TextView) findViewById(R.id.scratch_project_loves_text)).setText(Utils.humanFriendlyFormattedShortNumber(this.programData.getLoves()));
        ((TextView) findViewById(R.id.scratch_project_views_text)).setText(Utils.humanFriendlyFormattedShortNumber(this.programData.getViews()));
        TextView dateSharedView = (TextView) findViewById(R.id.date_shared_view);
        TextView dateModifiedView = (TextView) findViewById(R.id.date_modified_view);
        RelativeLayout dateViews = (RelativeLayout) findViewById(R.id.dates_view);
        dateViews.setVisibility(8);
        if (this.programData.getSharedDate() != null) {
            dateSharedView.setText(getString(R.string.shared_at_x, new Object[]{Utils.formatDate(this.programData.getSharedDate(), Locale.getDefault())}));
            dateSharedView.setVisibility(0);
            dateViews.setVisibility(0);
        } else {
            dateSharedView.setVisibility(8);
        }
        if (this.programData.getModifiedDate() != null) {
            dateModifiedView.setText(getString(R.string.modified_at_x, new Object[]{Utils.formatDate(this.programData.getModifiedDate(), Locale.getDefault())}));
            dateModifiedView.setVisibility(0);
            dateViews.setVisibility(0);
        } else {
            dateModifiedView.setVisibility(8);
        }
        findViewById(R.id.project_details_layout).setVisibility(0);
        ScratchVisibilityState visibilityState = this.programData.getVisibilityState();
        if (visibilityState == null || visibilityState == ScratchVisibilityState.PUBLIC) {
            findViewById(R.id.privacy_warning).setVisibility(8);
            this.convertButton.setVisibility(0);
        } else {
            findViewById(R.id.privacy_warning).setVisibility(0);
            this.convertButton.setVisibility(8);
        }
        if (this.programData.getRemixes().size() > 0) {
            findViewById(R.id.remixes_title_view).setVisibility(0);
            findViewById(R.id.recycler_view_remixes).setVisibility(0);
            this.adapter.setItems(this.programData.getRemixes());
            return;
        }
        findViewById(R.id.remixes_title_view).setVisibility(8);
    }

    public void onJobScheduled(Job job) {
        if (job.getJobID() == this.programData.getId()) {
            onJobInProgress();
        }
    }

    public void onJobReady(Job job) {
    }

    public void onJobStarted(Job job) {
    }

    public void onJobProgress(Job job, short progress) {
    }

    public void onJobOutput(Job job, @NonNull String[] lines) {
    }

    public void onJobFinished(Job job) {
    }

    public void onJobFailed(Job job) {
        if (job.getJobID() == this.programData.getId()) {
            onJobNotInProgress();
        }
    }

    public void onUserCanceledJob(Job job) {
        if (job.getJobID() == this.programData.getId()) {
            onJobNotInProgress();
        }
    }

    public void onDownloadStarted(String url) {
        if (Utils.extractScratchJobIDFromURL(url) == this.programData.getId()) {
            onJobDownloading();
        }
    }

    public void onDownloadProgress(short progress, String url) {
    }

    public void onDownloadFinished(String catrobatProgramName, String url) {
        if (Utils.extractScratchJobIDFromURL(url) == this.programData.getId()) {
            onJobNotInProgress();
        }
    }

    public void onUserCanceledDownload(String url) {
        if (Utils.extractScratchJobIDFromURL(url) == this.programData.getId()) {
            onJobNotInProgress();
        }
    }
}
