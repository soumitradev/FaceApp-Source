package android.support.v7.widget;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

class ListPopupWindow$3 implements OnItemSelectedListener {
    final /* synthetic */ ListPopupWindow this$0;

    ListPopupWindow$3(ListPopupWindow this$0) {
        this.this$0 = this$0;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (position != -1) {
            DropDownListView dropDownList = this.this$0.mDropDownList;
            if (dropDownList != null) {
                dropDownList.setListSelectionHidden(false);
            }
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
