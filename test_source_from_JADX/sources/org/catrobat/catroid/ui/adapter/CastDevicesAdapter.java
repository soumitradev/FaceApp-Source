package org.catrobat.catroid.ui.adapter;

import android.content.Context;
import android.support.v7.media.MediaRouter.RouteInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.generated70026.R;

public class CastDevicesAdapter extends ArrayAdapter<RouteInfo> {
    public CastDevicesAdapter(Context context, int resource, List<RouteInfo> items) {
        super(context, resource, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_cast_device_list_item, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.cast_device_name)).setText(((RouteInfo) CastManager.getInstance().getRouteInfos().get(position)).getName());
        return convertView;
    }
}
