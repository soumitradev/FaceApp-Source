package org.catrobat.catroid.cast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.widget.RelativeLayout;
import com.google.android.gms.cast.CastPresentation;
import com.google.android.gms.cast.CastRemoteDisplayLocalService;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.ToastUtil;

public class CastService extends CastRemoteDisplayLocalService {
    private Display display;
    private CastPresentation presentation;

    public class FirstScreenPresentation extends CastPresentation {
        public FirstScreenPresentation(Context serviceContext, Display display) {
            super(serviceContext, display);
        }

        @SuppressLint({"NewApi"})
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            RelativeLayout layout = new RelativeLayout(CastService.this.getApplication());
            setContentView(layout);
            CastManager.getInstance().setIsConnected(true);
            CastManager.getInstance().setRemoteLayout(layout);
            CastManager.getInstance().setRemoteLayoutToIdleScreen(CastService.this.getApplicationContext());
        }
    }

    public void onCreatePresentation(Display display) {
        createPresentation(display);
    }

    public void onDismissPresentation() {
        dismissPresentation();
    }

    @SuppressLint({"NewApi"})
    private void dismissPresentation() {
        if (this.presentation != null) {
            this.presentation.dismiss();
            this.presentation = null;
        }
    }

    @SuppressLint({"NewApi"})
    public void createPresentation(Display display) {
        if (display != null) {
            this.display = display;
        }
        dismissPresentation();
        this.presentation = new FirstScreenPresentation(this, this.display);
        try {
            this.presentation.show();
        } catch (Exception e) {
            ToastUtil.showError(getApplicationContext(), getString(R.string.cast_error_not_connected_msg));
            dismissPresentation();
        }
    }
}
