package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.ui.ProjectListActivity;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.ui.recyclerview.RVButton;
import org.catrobat.catroid.ui.recyclerview.activity.ProjectUploadActivity;
import org.catrobat.catroid.ui.recyclerview.adapter.ButtonAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.ButtonAdapter.OnItemClickListener;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectLoaderTask;
import org.catrobat.catroid.ui.recyclerview.asynctask.ProjectLoaderTask.ProjectLoaderListener;
import org.catrobat.catroid.ui.recyclerview.dialog.NewProjectDialogFragment;
import org.catrobat.catroid.ui.recyclerview.viewholder.ButtonVH;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;

public class MainMenuFragment extends Fragment implements OnItemClickListener, ProjectLoaderListener {
    private static final int CONTINUE = 0;
    private static final int EXPLORE = 4;
    private static final int HELP = 3;
    private static final int NEW = 1;
    private static final int PROGRAMS = 2;
    public static final String TAG = MainMenuFragment.class.getSimpleName();
    private static final int UPLOAD = 5;
    private ButtonAdapter adapter;
    private View parent;
    private RecyclerView recyclerView;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.parent = inflater.inflate(R.layout.fragment_list_view, container, false);
        this.recyclerView = (RecyclerView) this.parent.findViewById(R.id.recycler_view);
        setShowProgressBar(true);
        return this.parent;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.adapter = new ButtonAdapter(getItems()) {
            @NonNull
            public ButtonVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_button, parent, false);
                view.setMinimumHeight(parent.getHeight() / this.items.size());
                return new ButtonVH(view);
            }
        };
        this.adapter.setOnItemClickListener(this);
        this.recyclerView.setAdapter(this.adapter);
        setShowProgressBar(false);
    }

    private List<RVButton> getItems() {
        List<RVButton> items = new ArrayList();
        items.add(new RVButton(0, ContextCompat.getDrawable(getActivity(), R.drawable.ic_main_menu_continue), getString(R.string.main_menu_continue)));
        items.add(new RVButton(1, ContextCompat.getDrawable(getActivity(), R.drawable.ic_main_menu_new), getString(R.string.main_menu_new)));
        items.add(new RVButton(2, ContextCompat.getDrawable(getActivity(), R.drawable.ic_main_menu_programs), getString(R.string.main_menu_programs)));
        items.add(new RVButton(3, ContextCompat.getDrawable(getActivity(), R.drawable.ic_main_menu_help), getString(R.string.main_menu_help)));
        items.add(new RVButton(4, ContextCompat.getDrawable(getActivity(), R.drawable.ic_main_menu_community), getString(R.string.main_menu_web)));
        items.add(new RVButton(5, ContextCompat.getDrawable(getActivity(), R.drawable.ic_main_menu_upload), getString(R.string.main_menu_upload)));
        return items;
    }

    public void onResume() {
        super.onResume();
        setShowProgressBar(false);
        ((RVButton) this.adapter.items.get(0)).subtitle = Utils.getCurrentProjectName(getActivity());
        this.adapter.notifyDataSetChanged();
    }

    public void setShowProgressBar(boolean show) {
        int i = 8;
        this.parent.findViewById(R.id.progress_bar).setVisibility(show ? 0 : 8);
        RecyclerView recyclerView = this.recyclerView;
        if (!show) {
            i = 0;
        }
        recyclerView.setVisibility(i);
    }

    public void onItemClick(int id) {
        switch (id) {
            case 0:
                ProjectLoaderTask loaderTask = new ProjectLoaderTask(getActivity(), this);
                setShowProgressBar(true);
                loaderTask.execute(new String[]{Utils.getCurrentProjectName(getActivity())});
                return;
            case 1:
                new NewProjectDialogFragment().show(getFragmentManager(), NewProjectDialogFragment.TAG);
                return;
            case 2:
                setShowProgressBar(true);
                startActivity(new Intent(getActivity(), ProjectListActivity.class));
                return;
            case 3:
                setShowProgressBar(true);
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Constants.CATROBAT_HELP_URL)));
                return;
            case 4:
                setShowProgressBar(true);
                startActivity(new Intent(getActivity(), WebViewActivity.class));
                return;
            case 5:
                setShowProgressBar(true);
                startActivity(new Intent(getActivity(), ProjectUploadActivity.class).putExtra("projectName", Utils.getCurrentProjectName(getActivity())));
                return;
            default:
                return;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
}
