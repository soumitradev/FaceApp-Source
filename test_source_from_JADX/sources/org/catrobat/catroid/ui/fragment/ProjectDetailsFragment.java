package org.catrobat.catroid.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.catrobat.catroid.common.ProjectData;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.exceptions.LoadingProjectException;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.Builder;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.ToastUtil;

public class ProjectDetailsFragment extends Fragment {
    public static final String SELECTED_PROJECT_KEY = "selectedProject";
    public static final String TAG = ProjectDetailsFragment.class.getSimpleName();
    private TextView description;
    private ProjectData projectData;

    /* renamed from: org.catrobat.catroid.ui.fragment.ProjectDetailsFragment$1 */
    class C19511 implements OnClickListener {
        C19511() {
        }

        public void onClick(View v) {
            ProjectDetailsFragment.this.handleDescriptionPressed();
        }
    }

    /* renamed from: org.catrobat.catroid.ui.fragment.ProjectDetailsFragment$2 */
    class C21202 implements TextInputDialog.OnClickListener {
        C21202() {
        }

        public void onPositiveButtonClick(DialogInterface dialog, String textInput) {
            ProjectDetailsFragment.this.setDescription(textInput);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_show_details, container, false);
        setHasOptionsMenu(true);
        try {
            this.projectData = (ProjectData) getArguments().getSerializable(SELECTED_PROJECT_KEY);
            this.projectData.project = XstreamSerializer.getInstance().loadProject(this.projectData.projectName, getActivity());
        } catch (IOException e) {
            ToastUtil.showError(getActivity(), (int) R.string.error_load_project);
            Log.e(TAG, Log.getStackTraceString(e));
            getActivity().onBackPressed();
        } catch (LoadingProjectException e2) {
            ToastUtil.showError(getActivity(), (int) R.string.error_load_project);
            Log.e(TAG, Log.getStackTraceString(e2));
            getActivity().onBackPressed();
        }
        String sceneName = XstreamSerializer.extractDefaultSceneNameFromXml(this.projectData.projectName);
        ProjectAndSceneScreenshotLoader screenshotLoader = new ProjectAndSceneScreenshotLoader(getActivity());
        XmlHeader header = this.projectData.project.getXmlHeader();
        screenshotLoader.loadAndShowScreenshot(this.projectData.projectName, sceneName, false, (ImageView) view.findViewById(R.id.image));
        String size = FileMetaDataExtractor.getSizeAsString(new File(PathBuilder.buildProjectPath(this.projectData.projectName)), getActivity());
        int modeText = header.islandscapeMode() ? R.string.landscape : R.string.portrait;
        String screen = new StringBuilder();
        screen.append(header.getVirtualScreenWidth());
        screen.append("x");
        screen.append(header.getVirtualScreenHeight());
        screen = screen.toString();
        ((TextView) view.findViewById(R.id.name)).setText(this.projectData.projectName);
        ((TextView) view.findViewById(R.id.author_value)).setText(getUserHandle());
        ((TextView) view.findViewById(R.id.size_value)).setText(size);
        ((TextView) view.findViewById(R.id.last_access_value)).setText(getLastAccess());
        ((TextView) view.findViewById(R.id.screen_size_value)).setText(screen);
        ((TextView) view.findViewById(R.id.mode_value)).setText(modeText);
        ((TextView) view.findViewById(R.id.remix_of_value)).setText(getRemixOf());
        this.description = (TextView) view.findViewById(R.id.description_value);
        this.description.setText(header.getDescription());
        this.description.setOnClickListener(new C19511());
        BottomBar.hideBottomBar(getActivity());
        return view;
    }

    private void handleDescriptionPressed() {
        Builder builder = new Builder(getContext());
        builder.setHint(getString(R.string.description)).setText(this.projectData.project.getDescription()).setPositiveButton(getString(R.string.ok), new C21202());
        builder.setTitle(R.string.set_description).setNegativeButton(R.string.cancel, null).create().show();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.delete).setVisible(false);
        menu.findItem(R.id.copy).setVisible(false);
        menu.findItem(R.id.rename).setVisible(false);
        menu.findItem(R.id.show_details).setVisible(false);
    }

    private String getLastAccess() {
        Date lastModified = new Date(this.projectData.lastUsed);
        if (DateUtils.isToday(lastModified.getTime())) {
            return getString(R.string.details_date_today).concat(": ").concat(DateFormat.getTimeFormat(getActivity()).format(lastModified));
        }
        return DateFormat.getDateFormat(getActivity()).format(lastModified);
    }

    private String getUserHandle() {
        String userHandle = this.projectData.project.getXmlHeader().getUserHandle();
        if (userHandle != null) {
            if (!userHandle.equals("")) {
                return userHandle;
            }
        }
        return getString(R.string.unknown);
    }

    private String getRemixOf() {
        String remixOf = this.projectData.project.getXmlHeader().getRemixParentsUrlString();
        if (remixOf != null) {
            if (!remixOf.equals("")) {
                return remixOf;
            }
        }
        return getString(R.string.nxt_no_sensor);
    }

    public void setDescription(String description) {
        this.projectData.project.setDescription(description);
        if (XstreamSerializer.getInstance().saveProject(this.projectData.project)) {
            this.description.setText(description);
        } else {
            ToastUtil.showError(getActivity(), (int) R.string.error_set_description);
        }
    }
}
