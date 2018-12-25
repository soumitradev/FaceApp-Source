package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.catrobat.catroid.common.ProjectData;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedVH;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.PathBuilder;

public class ProjectAdapter extends ExtendedRVAdapter<ProjectData> {
    public ProjectAdapter(List<ProjectData> items) {
        super(items);
    }

    public void onBindViewHolder(ExtendedVH holder, int position) {
        ExtendedVH extendedVH = holder;
        Context context = extendedVH.itemView.getContext();
        ProjectAndSceneScreenshotLoader loader = new ProjectAndSceneScreenshotLoader(context);
        ProjectData item = (ProjectData) this.items.get(position);
        String sceneName = XstreamSerializer.extractDefaultSceneNameFromXml(item.projectName);
        extendedVH.title.setText(item.projectName);
        loader.loadAndShowScreenshot(item.projectName, sceneName, false, extendedVH.image);
        if (this.showDetails) {
            String lastAccess = DateFormat.getDateInstance(2).format(new Date(item.lastUsed));
            extendedVH.details.setText(String.format(Locale.getDefault(), context.getString(R.string.project_details), new Object[]{lastAccess, FileMetaDataExtractor.getSizeAsString(new File(PathBuilder.buildProjectPath(item.projectName)), context)}));
            extendedVH.details.setVisibility(0);
            return;
        }
        extendedVH.details.setVisibility(8);
    }
}
