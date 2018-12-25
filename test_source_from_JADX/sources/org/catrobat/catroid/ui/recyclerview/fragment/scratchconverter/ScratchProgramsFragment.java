package org.catrobat.catroid.ui.recyclerview.fragment.scratchconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.scratchconverter.protocol.Job.DownloadState;
import org.catrobat.catroid.scratchconverter.protocol.Job.State;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.ui.ScratchConverterActivity.OnJobListListener;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener;
import org.catrobat.catroid.ui.recyclerview.adapter.ScratchJobAdapter;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectLoaderTask;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectLoaderTask.ProjectLoaderListener;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;
import org.catrobat.catroid.utils.ToastUtil;

public class ScratchProgramsFragment extends Fragment implements OnJobListListener, OnItemClickListener<Job>, ProjectLoaderListener {
    public static final String TAG = ScratchProgramsFragment.class.getSimpleName();
    private ScratchJobAdapter finishedJobsAdapter;
    private LinearLayout finishedJobsLayout;
    private RecyclerView finishedJobsRecyclerView;
    private View parent;
    private ScratchJobAdapter runningJobsAdapter;
    private LinearLayout runningJobsLayout;
    private RecyclerView runningJobsRecyclerView;

    public void initializeAdapters(ScratchJobAdapter runningJobsAdapter, ScratchJobAdapter finishedJobsAdapter) {
        this.runningJobsAdapter = runningJobsAdapter;
        this.finishedJobsAdapter = finishedJobsAdapter;
        runningJobsAdapter.setOnItemClickListener(this);
        finishedJobsAdapter.setOnItemClickListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.parent = inflater.inflate(R.layout.fragment_scratch_programs, container, false);
        this.runningJobsLayout = (LinearLayout) this.parent.findViewById(R.id.programs_in_progress);
        this.finishedJobsLayout = (LinearLayout) this.parent.findViewById(R.id.programs_finished);
        this.runningJobsRecyclerView = (RecyclerView) this.parent.findViewById(R.id.recycler_view_in_progress);
        this.finishedJobsRecyclerView = (RecyclerView) this.parent.findViewById(R.id.recycler_view_finished);
        return this.parent;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.runningJobsRecyclerView.setAdapter(this.runningJobsAdapter);
        this.finishedJobsRecyclerView.setAdapter(this.finishedJobsAdapter);
    }

    public void onResume() {
        super.onResume();
        setShowProgressBar(false);
    }

    public void onJobListChanged() {
        this.runningJobsAdapter.notifyDataSetChanged();
        this.finishedJobsAdapter.notifyDataSetChanged();
    }

    public void onItemClick(Job item) {
        if (item.getState() == State.FAILED) {
            ToastUtil.showError(getActivity(), (int) R.string.error_cannot_open_failed_scratch_program);
        } else if (item.getDownloadState() == DownloadState.DOWNLOADING) {
            new AlertDialog$Builder(getActivity()).setTitle(R.string.warning).setMessage(R.string.error_cannot_open_currently_downloading_scratch_program).setNeutralButton(R.string.close, null).create().show();
        } else {
            if (item.getDownloadState() != DownloadState.NOT_READY) {
                if (item.getDownloadState() != DownloadState.CANCELED) {
                    if (XstreamSerializer.getInstance().projectExists(item.getTitle())) {
                        setShowProgressBar(true);
                        new ProjectLoaderTask(getActivity(), this).execute(new String[]{item.getTitle()});
                        return;
                    }
                    new AlertDialog$Builder(getActivity()).setTitle(R.string.warning).setMessage(R.string.error_cannot_open_not_existing_scratch_program).setNeutralButton(R.string.close, null).create().show();
                    return;
                }
            }
            new AlertDialog$Builder(getActivity()).setTitle(R.string.warning).setMessage(R.string.error_cannot_open_not_yet_downloaded_scratch_program).setNeutralButton(R.string.close, null).create().show();
        }
    }

    public void setShowProgressBar(boolean show) {
        int i = 8;
        this.parent.findViewById(R.id.progress_bar).setVisibility(show ? 0 : 8);
        this.runningJobsLayout.setVisibility(show ? 8 : 0);
        LinearLayout linearLayout = this.finishedJobsLayout;
        if (!show) {
            i = 0;
        }
        linearLayout.setVisibility(i);
    }

    public void onLoadFinished(boolean success, String message) {
        if (success) {
            Intent intent = new Intent(getActivity(), ProjectActivity.class);
            intent.putExtra("fragmentPosition", 0);
            startActivity(intent);
            return;
        }
        setShowProgressBar(false);
        ToastUtil.showError(getActivity(), message);
    }

    public void onItemLongClick(Job item, CheckableVH holder) {
    }
}
