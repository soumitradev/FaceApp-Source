package org.catrobat.catroid.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/ui/ImportFromFileLauncher;", "Lorg/catrobat/catroid/ui/ImportLauncher;", "activity", "Landroid/support/v7/app/AppCompatActivity;", "type", "", "title", "(Landroid/support/v7/app/AppCompatActivity;Ljava/lang/String;Ljava/lang/String;)V", "startActivityForResult", "", "requestCode", "", "catroid_standaloneDebug"}, k = 1, mv = {1, 1, 10})
/* compiled from: ImportLaunchers.kt */
public final class ImportFromFileLauncher implements ImportLauncher {
    private final AppCompatActivity activity;
    private final String title;
    private final String type;

    public ImportFromFileLauncher(@NotNull AppCompatActivity activity, @NotNull String type, @NotNull String title) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(title, "title");
        this.activity = activity;
        this.type = type;
        this.title = title;
    }

    public void startActivityForResult(int requestCode) {
        this.activity.startActivityForResult(Intent.createChooser(new Intent("android.intent.action.GET_CONTENT").setType(this.type), this.title), requestCode);
    }
}
