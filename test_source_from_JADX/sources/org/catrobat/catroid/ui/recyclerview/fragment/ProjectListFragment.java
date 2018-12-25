package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.PluralsRes;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.ProjectData;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.exceptions.ProjectException;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.ui.fragment.ProjectDetailsFragment;
import org.catrobat.catroid.ui.recyclerview.activity.ProjectUploadActivity;
import org.catrobat.catroid.ui.recyclerview.adapter.ProjectAdapter;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectCopyTask;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectCopyTask.ProjectCopyListener;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectCreatorTask;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectCreatorTask.ProjectCreatorListener;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectLoaderTask;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectLoaderTask.ProjectLoaderListener;
import org.catrobat.catroid.ui.recyclerview.controller.ProjectController;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.ToastUtil;

public class ProjectListFragment extends RecyclerViewFragment<ProjectData> implements ProjectCreatorListener, ProjectLoaderListener, ProjectCopyListener {
    public static final String TAG = ProjectListFragment.class.getSimpleName();
    private ProjectController projectController = new ProjectController();

    /* renamed from: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$1 */
    class C20011 implements Comparator<ProjectData> {
        C20011() {
        }

        public int compare(ProjectData project1, ProjectData project2) {
            return Long.valueOf(project2.lastUsed).compareTo(Long.valueOf(project1.lastUsed));
        }
    }

    public void onResume() {
        ProjectManager.getInstance().setCurrentProject(null);
        this.adapter.setItems(getItemList());
        BottomBar.showBottomBar(getActivity());
        super.onResume();
    }

    protected void initializeAdapter() {
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_PROJECTS_PREFERENCE_KEY;
        this.adapter = new ProjectAdapter(getItemList());
        onAdapterReady();
    }

    private List<ProjectData> getItemList() {
        List<ProjectData> items = new ArrayList();
        for (String projectName : FileMetaDataExtractor.getProjectNames(FlavoredConstants.DEFAULT_ROOT_DIRECTORY)) {
            items.add(new ProjectData(projectName, new File(PathBuilder.buildPath(PathBuilder.buildProjectPath((String) r1.next()), Constants.CODE_XML_FILE_NAME)).lastModified()));
        }
        Collections.sort(items, new C20011());
        return items;
    }

    protected void prepareActionMode(int type) {
        if (type == 2) {
            this.adapter.allowMultiSelection = false;
        }
        super.prepareActionMode(type);
    }

    protected void packItems(List<ProjectData> list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TAG);
        stringBuilder.append(": Projects cannot be backpacked");
        throw new IllegalStateException(stringBuilder.toString());
    }

    protected boolean isBackpackEmpty() {
        return true;
    }

    protected void switchToBackpack() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TAG);
        stringBuilder.append(": Projects cannot be backpacked");
        throw new IllegalStateException(stringBuilder.toString());
    }

    protected void copyItems(List<ProjectData> selectedItems) {
        finishActionMode();
        setShowProgressBar(true);
        ProjectCopyTask copyTask = new ProjectCopyTask(getActivity(), this);
        String name = this.uniqueNameProvider.getUniqueNameInNameables(((ProjectData) selectedItems.get(0)).getName(), this.adapter.getItems());
        copyTask.execute(new String[]{((ProjectData) selectedItems.get(0)).getName(), name});
    }

    @PluralsRes
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_projects;
    }

    protected void deleteItems(List<ProjectData> selectedItems) {
        setShowProgressBar(true);
        for (ProjectData item : selectedItems) {
            try {
                this.projectController.delete(item);
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            this.adapter.remove(item);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_projects, selectedItems.size(), new Object[]{Integer.valueOf(selectedItems.size())}));
        finishActionMode();
        this.adapter.setItems(getItemList());
        if (this.adapter.getItems().isEmpty()) {
            setShowProgressBar(true);
            new ProjectCreatorTask(getActivity(), this).execute(new Void[0]);
        }
    }

    protected int getRenameDialogTitle() {
        return R.string.rename_project;
    }

    protected int getRenameDialogHint() {
        return R.string.project_name_label;
    }

    public void renameItem(ProjectData item, String name) {
        setShowProgressBar(true);
        ProjectManager projectManager = ProjectManager.getInstance();
        if (!name.equals(item.projectName)) {
            try {
                projectManager.loadProject(item.projectName, getActivity());
                projectManager.renameProject(name, getActivity());
                projectManager.loadProject(name, getActivity());
            } catch (ProjectException e) {
                Log.e(TAG, Log.getStackTraceString(e));
                ToastUtil.showError(getActivity(), (int) R.string.error_rename_incompatible_project);
            }
        }
        finishActionMode();
        this.adapter.setItems(getItemList());
    }

    public void onCreateFinished(boolean success) {
        if (success) {
            this.adapter.setItems(getItemList());
            setShowProgressBar(false);
            return;
        }
        ToastUtil.showError(getActivity(), (int) R.string.wtf_error);
        getActivity().finish();
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

    public void onCopyFinished(boolean success) {
        if (success) {
            this.adapter.setItems(getItemList());
        } else {
            ToastUtil.showError(getActivity(), (int) R.string.error_copy_project);
        }
        setShowProgressBar(false);
    }

    @PluralsRes
    protected int getActionModeTitleId(int actionModeType) {
        switch (actionModeType) {
            case 1:
                return R.plurals.am_pack_projects_title;
            case 2:
                return R.plurals.am_copy_projects_title;
            case 3:
                return R.plurals.am_delete_projects_title;
            case 4:
                return R.plurals.am_rename_projects_title;
            default:
                throw new IllegalStateException("ActionModeType not set correctly");
        }
    }

    public void onItemClick(ProjectData item) {
        if (this.actionModeType == 0) {
            ProjectLoaderTask loaderTask = new ProjectLoaderTask(getActivity(), this);
            setShowProgressBar(true);
            loaderTask.execute(new String[]{item.projectName});
        }
    }

    public void onItemLongClick(final ProjectData item, CheckableVH holder) {
        new AlertDialog$Builder(getActivity()).setTitle(item.projectName).setItems(new CharSequence[]{getString(R.string.copy), getString(R.string.delete), getString(R.string.rename), getString(R.string.show_details), getString(R.string.upload_button)}, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        ProjectListFragment.this.copyItems(new ArrayList(Collections.singletonList(item)));
                        return;
                    case 1:
                        ProjectListFragment.this.showDeleteAlert(new ArrayList(Collections.singletonList(item)));
                        return;
                    case 2:
                        ProjectListFragment.this.showRenameDialog(new ArrayList(Collections.singletonList(item)));
                        return;
                    case 3:
                        ProjectDetailsFragment fragment = new ProjectDetailsFragment();
                        Bundle args = new Bundle();
                        args.putSerializable(ProjectDetailsFragment.SELECTED_PROJECT_KEY, item);
                        fragment.setArguments(args);
                        ProjectListFragment.this.getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, ProjectDetailsFragment.TAG).addToBackStack(ProjectDetailsFragment.TAG).commit();
                        return;
                    case 4:
                        ProjectListFragment.this.startActivity(new Intent(ProjectListFragment.this.getActivity(), ProjectUploadActivity.class).putExtra("projectName", item.getName()));
                        return;
                    default:
                        dialog.dismiss();
                        return;
                }
            }
        }).show();
    }
}
