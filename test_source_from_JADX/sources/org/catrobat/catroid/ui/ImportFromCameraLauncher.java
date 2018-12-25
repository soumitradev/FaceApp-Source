package org.catrobat.catroid.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import com.facebook.share.internal.ShareConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.generated70026.R;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/ui/ImportFromCameraLauncher;", "Lorg/catrobat/catroid/ui/ImportLauncher;", "activity", "Landroid/support/v7/app/AppCompatActivity;", "uri", "Landroid/net/Uri;", "(Landroid/support/v7/app/AppCompatActivity;Landroid/net/Uri;)V", "startActivityForResult", "", "requestCode", "", "catroid_standaloneDebug"}, k = 1, mv = {1, 1, 10})
/* compiled from: ImportLaunchers.kt */
public final class ImportFromCameraLauncher implements ImportLauncher {
    private final AppCompatActivity activity;
    private final Uri uri;

    public ImportFromCameraLauncher(@NotNull AppCompatActivity activity, @NotNull Uri uri) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        Intrinsics.checkParameterIsNotNull(uri, ShareConstants.MEDIA_URI);
        this.activity = activity;
        this.uri = uri;
    }

    public void startActivityForResult(int requestCode) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", this.uri);
        this.activity.startActivityForResult(Intent.createChooser(intent, this.activity.getString(R.string.select_look_from_camera)), requestCode);
    }
}
