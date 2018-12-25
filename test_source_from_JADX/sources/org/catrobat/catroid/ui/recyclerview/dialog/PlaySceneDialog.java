package org.catrobat.catroid.ui.recyclerview.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.generated70026.R;

public final class PlaySceneDialog extends AlertDialog {

    public static class Builder extends AlertDialog$Builder {
        public Builder(@NonNull Context context) {
            super(context);
            final ProjectManager projectManager = ProjectManager.getInstance();
            final Scene currentScene = projectManager.getCurrentlyEditedScene();
            final Scene defaultScene = projectManager.getCurrentProject().getDefaultScene();
            String[] dialogOptions = new String[2];
            dialogOptions[0] = String.format(context.getString(R.string.play_scene_dialog_default), new Object[]{defaultScene.getName()});
            dialogOptions[1] = String.format(context.getString(R.string.play_scene_dialog_current), new Object[]{currentScene.getName()});
            setTitle(R.string.play_scene_dialog_title);
            projectManager.setCurrentlyPlayingScene(defaultScene);
            projectManager.setStartScene(defaultScene);
            setSingleChoiceItems(dialogOptions, 0, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            projectManager.setCurrentlyPlayingScene(defaultScene);
                            projectManager.setStartScene(defaultScene);
                            return;
                        case 1:
                            projectManager.setCurrentlyPlayingScene(currentScene);
                            projectManager.setStartScene(currentScene);
                            return;
                        default:
                            return;
                    }
                }
            });
        }
    }

    private PlaySceneDialog(Context context) {
        super(context);
    }
}
