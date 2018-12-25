package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.support.v7.media.MediaRouter.RouteInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.generated70026.R;

public class SelectCastDialog extends DialogFragment {
    public static final String TAG = SelectCastDialog.class.getSimpleName();

    /* renamed from: org.catrobat.catroid.ui.dialogs.SelectCastDialog$1 */
    class C19281 implements OnClickListener {
        C19281() {
        }

        public void onClick(DialogInterface dialogInterface, int which) {
            synchronized (this) {
                CastManager.getInstance().getMediaRouter().unselect(2);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CastManager.getInstance().setCallback(1);
    }

    public void onDismiss(DialogInterface dialog) {
        CastManager.getInstance().setCallback();
        super.onDismiss(dialog);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayAdapter<RouteInfo> deviceAdapter = CastManager.getInstance().getDeviceAdapter();
        if (!CastManager.getInstance().currentlyConnecting()) {
            if (!CastManager.getInstance().isConnected()) {
                View view = View.inflate(getActivity(), R.layout.dialog_select_cast, null);
                ListView listView = (ListView) view.findViewById(R.id.cast_device_list_view);
                listView.setEmptyView(view.findViewById(R.id.empty_view_item));
                listView.setAdapter(deviceAdapter);
                listView.setDivider(null);
                final AlertDialog alertDialog = new AlertDialog$Builder(getContext()).setTitle(getString(R.string.cast_device_selector_dialog_title)).setView(view).create();
                listView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        synchronized (this) {
                            RouteInfo routeInfo = (RouteInfo) CastManager.getInstance().getRouteInfos().get(position);
                            CastManager.getInstance().setCallback();
                            CastManager.getInstance().startCastButtonAnimation();
                            CastManager.getInstance().selectRoute(routeInfo);
                            alertDialog.dismiss();
                        }
                    }
                });
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        synchronized (this) {
                            if (alertDialog != null && alertDialog.isShowing() && deviceAdapter.isEmpty()) {
                                TextView textview = (TextView) alertDialog.findViewById(R.id.cast_searching_for_cast_text_view);
                                String text = new StringBuilder();
                                text.append(SelectCastDialog.this.getString(R.string.cast_searching_for_cast_devices));
                                text.append(SelectCastDialog.this.getString(R.string.cast_trouble_finding_devices_tip));
                                textview.setText(text.toString());
                            }
                        }
                    }
                }, 3000);
                return alertDialog;
            }
        }
        AlertDialog$Builder builder = new AlertDialog$Builder(getContext());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.cast_ready_to_cast));
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(CastManager.getInstance().getSelectedDevice().getFriendlyName());
        builder.setMessage(stringBuilder.toString());
        builder.setPositiveButton(R.string.disconnect, new C19281());
        return builder.create();
    }
}
