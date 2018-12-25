package org.catrobat.catroid.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/ui/ImportFromPocketPaintLauncher;", "Lorg/catrobat/catroid/ui/ImportLauncher;", "activity", "Landroid/support/v7/app/AppCompatActivity;", "(Landroid/support/v7/app/AppCompatActivity;)V", "startActivityForResult", "", "requestCode", "", "catroid_standaloneDebug"}, k = 1, mv = {1, 1, 10})
/* compiled from: ImportLaunchers.kt */
public final class ImportFromPocketPaintLauncher implements ImportLauncher {
    private final AppCompatActivity activity;

    public ImportFromPocketPaintLauncher(@NotNull AppCompatActivity activity) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        this.activity = activity;
    }

    public void startActivityForResult(int requestCode) {
        Intent intent = new Intent("android.intent.action.MAIN").setComponent(new ComponentName(this.activity, Constants.POCKET_PAINT_INTENT_ACTIVITY_NAME));
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_PICTURE_PATH_POCKET_PAINT, "");
        bundle.putString(Constants.EXTRA_PICTURE_NAME_POCKET_PAINT, this.activity.getString(R.string.default_look_name));
        intent.putExtras(bundle);
        intent.addCategory("android.intent.category.LAUNCHER");
        this.activity.startActivityForResult(intent, requestCode);
    }
}
