package org.catrobat.catroid.ui.recyclerview.fragment.scratchconverter;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.common.ScratchSearchResult;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.scratchconverter.ConversionManager;
import org.catrobat.catroid.transfers.SearchScratchProgramsTask;
import org.catrobat.catroid.transfers.SearchScratchProgramsTask.SearchScratchProgramsTaskDelegate;
import org.catrobat.catroid.ui.ScratchProgramDetailsActivity;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.SelectionListener;
import org.catrobat.catroid.ui.recyclerview.adapter.ScratchProgramAdapter;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ScratchDataFetcher;
import org.catrobat.catroid.web.ServerCalls;

public class ScratchSearchResultsFragment extends Fragment implements Callback, SelectionListener, OnItemClickListener<ScratchProgramData> {
    private static final int CONVERT = 1;
    private static final int NONE = 0;
    public static final String TAG = ScratchSearchResultsFragment.class.getSimpleName();
    private ActionMode actionMode;
    private int actionModeType = 0;
    private ScratchProgramAdapter adapter;
    private ConversionManager conversionManager;
    private ScratchDataFetcher dataFetcher = ServerCalls.getInstance();
    private OnQueryListener onQueryListener = new OnQueryListener();
    private RecyclerView recyclerView;
    private SearchScratchProgramsTask searchTask;
    private SearchTaskDelegate searchTaskDelegate = new SearchTaskDelegate();
    private SearchView searchView;

    class OnQueryListener implements OnQueryTextListener {
        OnQueryListener() {
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        public boolean onQueryTextChange(String newText) {
            if (newText.length() > 1) {
                ScratchSearchResultsFragment.this.searchTaskDelegate.startSearch(newText);
            }
            return false;
        }
    }

    class SearchTaskDelegate implements SearchScratchProgramsTaskDelegate {
        SearchTaskDelegate() {
        }

        void startSearch(String query) {
            clearPendingSearch();
            ScratchSearchResultsFragment.this.searchTask = new SearchScratchProgramsTask().setDelegate(this).setFetcher(ScratchSearchResultsFragment.this.dataFetcher);
            if (query != null) {
                ScratchSearchResultsFragment.this.searchTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new String[]{query});
                return;
            }
            ScratchSearchResultsFragment.this.searchTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new String[0]);
        }

        void clearPendingSearch() {
            if (ScratchSearchResultsFragment.this.searchTask != null) {
                ScratchSearchResultsFragment.this.searchTask.cancel(true);
            }
        }

        public void onPreExecute() {
        }

        public void onPostExecute(ScratchSearchResult result) {
            if (result == null) {
                ToastUtil.showError(ScratchSearchResultsFragment.this.getActivity(), (int) R.string.search_failed);
            } else if (result.getProgramDataList() != null) {
                ScratchSearchResultsFragment.this.adapter.setItems(result.getProgramDataList());
            }
        }
    }

    public void setConversionManager(ConversionManager conversionManager) {
        this.conversionManager = conversionManager;
    }

    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        switch (this.actionModeType) {
            case 0:
                return false;
            case 1:
                mode.setTitle(getString(R.string.am_convert));
                break;
            default:
                break;
        }
        mode.getMenuInflater().inflate(R.menu.context_menu, menu);
        this.adapter.showCheckBoxes = true;
        this.adapter.notifyDataSetChanged();
        return true;
    }

    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() != R.id.confirm) {
            return false;
        }
        handleContextualAction();
        return true;
    }

    public void onDestroyActionMode(ActionMode mode) {
        resetActionModeParameters();
        this.adapter.clearSelection();
    }

    private void handleContextualAction() {
        if (this.adapter.getSelectedItems().isEmpty()) {
            this.actionMode.finish();
            return;
        }
        switch (this.actionModeType) {
            case 0:
                throw new IllegalStateException("ActionModeType not set Correctly");
            case 1:
                convertItems(this.adapter.getSelectedItems());
                break;
            default:
                break;
        }
    }

    private void resetActionModeParameters() {
        this.actionModeType = 0;
        this.actionMode = null;
        this.adapter.showCheckBoxes = false;
        this.adapter.allowMultiSelection = true;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scratch_results, container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.searchView = (SearchView) view.findViewById(R.id.search_view_scratch);
        setHasOptionsMenu(true);
        return view;
    }

    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        this.adapter = new ScratchProgramAdapter(new ArrayList());
        this.adapter.showDetails = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(SharedPreferenceKeys.SHOW_DETAILS_SCRATCH_PROJECTS_PREFERENCE_KEY, false);
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.setSelectionListener(this);
        this.adapter.setOnItemClickListener(this);
        this.searchView.setOnQueryTextListener(this.onQueryListener);
    }

    public void onResume() {
        super.onResume();
        this.searchTaskDelegate.startSearch(null);
    }

    public void onStop() {
        super.onStop();
        this.searchTaskDelegate.clearPendingSearch();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_scratch_projects, menu);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        this.adapter.showDetails = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(SharedPreferenceKeys.SHOW_DETAILS_SCRATCH_PROJECTS_PREFERENCE_KEY, false);
        menu.findItem(R.id.show_details).setTitle(this.adapter.showDetails ? R.string.hide_details : R.string.show_details);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.convert) {
            startActionMode(1);
        } else if (itemId != R.id.show_details) {
            return super.onOptionsItemSelected(item);
        } else {
            this.adapter.showDetails ^= true;
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(SharedPreferenceKeys.SHOW_DETAILS_SCRATCH_PROJECTS_PREFERENCE_KEY, this.adapter.showDetails).commit();
            this.adapter.notifyDataSetChanged();
        }
        return true;
    }

    private void startActionMode(int type) {
        if (this.adapter.getItems().isEmpty()) {
            ToastUtil.showError(getActivity(), (int) R.string.am_empty_list);
            resetActionModeParameters();
            return;
        }
        this.actionModeType = type;
        this.actionMode = getActivity().startActionMode(this);
    }

    private void finishActionMode() {
        this.adapter.clearSelection();
        if (this.actionModeType != 0) {
            this.actionMode.finish();
        }
    }

    private void convertItems(List<ScratchProgramData> selectedItems) {
        if (this.conversionManager.getNumberOfJobsInProgress() > 3) {
            ToastUtil.showError(getActivity(), getResources().getQuantityString(R.plurals.error_cannot_convert_more_than_x_programs, 3, new Object[]{Integer.valueOf(3)}));
            return;
        }
        int counter = 0;
        for (ScratchProgramData item : selectedItems) {
            if (Utils.isDeprecatedScratchProgram(item)) {
                DateFormat dateFormat = DateFormat.getDateInstance();
                ToastUtil.showError(getActivity(), getString(R.string.error_cannot_convert_deprecated_scratch_program_x_x, new Object[]{item.getTitle(), dateFormat.format(Utils.getScratchSecondReleasePublishedDate())}));
            } else if (!this.conversionManager.isJobInProgress(item.getId())) {
                this.conversionManager.convertProgram(item.getId(), item.getTitle(), item.getImage(), false);
                counter++;
            }
        }
        if (counter > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.scratch_conversion_scheduled_x, counter, new Object[]{Integer.valueOf(counter)}));
        }
        finishActionMode();
    }

    public void onSelectionChanged(int selectedItemCnt) {
        if (this.actionModeType != 1) {
            throw new IllegalStateException("ActionModeType not set correctly");
        }
        this.actionMode.setTitle(getResources().getQuantityString(R.plurals.am_convert_projects_title, selectedItemCnt, new Object[]{Integer.valueOf(selectedItemCnt)}));
    }

    public void onItemClick(ScratchProgramData item) {
        if (this.actionModeType == 0) {
            ScratchProgramDetailsActivity.setConversionManager(this.conversionManager);
            Intent intent = new Intent(getActivity(), ScratchProgramDetailsActivity.class);
            intent.putExtra(Constants.INTENT_SCRATCH_PROGRAM_DATA, item);
            startActivity(intent);
        }
    }

    public void onItemLongClick(final ScratchProgramData item, CheckableVH holder) {
        new AlertDialog$Builder(getActivity()).setTitle(item.getTitle()).setItems(new CharSequence[]{getString(R.string.convert)}, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    ScratchSearchResultsFragment.this.convertItems(new ArrayList(Collections.singletonList(item)));
                }
            }
        }).show();
    }
}
