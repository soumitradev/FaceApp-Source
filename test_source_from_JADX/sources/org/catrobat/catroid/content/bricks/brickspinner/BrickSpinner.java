package org.catrobat.catroid.content.bricks.brickspinner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;
import org.catrobat.catroid.common.Nameable;

public class BrickSpinner<T extends Nameable> implements android.widget.AdapterView.OnItemSelectedListener {
    private BrickSpinnerAdapter adapter;
    private OnItemSelectedListener<T> onItemSelectedListener;
    private Spinner spinner;

    private class BrickSpinnerAdapter extends ArrayAdapter<Nameable> {
        private List<Nameable> items;

        BrickSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Nameable> items) {
            super(context, resource, items);
            this.items = items;
        }

        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(17367049, parent, false);
            }
            final Nameable item = (Nameable) getItem(position);
            ((TextView) convertView).setText(item.getName());
            convertView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getActionIndex() == 0 && item.getClass().equals(NewOption.class)) {
                        BrickSpinner.this.onItemSelectedListener.onNewOptionSelected();
                    }
                    return false;
                }
            });
            return convertView;
        }

        @NonNull
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(17367048, parent, false);
            }
            ((TextView) convertView).setText(((Nameable) getItem(position)).getName());
            return convertView;
        }

        int getPosition(@Nullable String itemName) {
            for (int position = 0; position < getCount(); position++) {
                if (((Nameable) getItem(position)).getName().equals(itemName)) {
                    return position;
                }
            }
            return -1;
        }

        List<Nameable> getItems() {
            return this.items;
        }

        boolean containsNewOption() {
            for (Nameable item : this.items) {
                if (item instanceof NewOption) {
                    return true;
                }
            }
            return false;
        }
    }

    public interface OnItemSelectedListener<T> {
        void onItemSelected(@Nullable T t);

        void onNewOptionSelected();

        void onStringOptionSelected(String str);
    }

    public BrickSpinner(int spinnerId, @NonNull View parent, List<Nameable> items) {
        this.adapter = new BrickSpinnerAdapter(parent.getContext(), 17367048, items);
        this.spinner = (Spinner) parent.findViewById(spinnerId);
        this.spinner.setAdapter(this.adapter);
        this.spinner.setSelection(0);
        this.spinner.setOnItemSelectedListener(this);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener<T> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (this.onItemSelectedListener != null) {
            Nameable item = (Nameable) this.adapter.getItem(position);
            if (!item.getClass().equals(NewOption.class)) {
                if (item.getClass().equals(StringOption.class)) {
                    this.onItemSelectedListener.onStringOptionSelected(item.getName());
                } else {
                    this.onItemSelectedListener.onItemSelected(item);
                }
            }
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void add(@NonNull T item) {
        this.adapter.add(item);
    }

    public List<T> getItems() {
        return this.adapter.getItems();
    }

    public void setSelection(int position) {
        this.spinner.setSelection(position);
    }

    public void setSelection(@Nullable String itemName) {
        this.spinner.setOnItemSelectedListener(null);
        int position = consolidateSpinnerSelection(this.adapter.getPosition(itemName));
        this.spinner.setSelection(position);
        onSelectionSet((Nameable) this.adapter.getItem(position));
        this.spinner.setOnItemSelectedListener(this);
    }

    public void setSelection(@Nullable T item) {
        this.spinner.setOnItemSelectedListener(null);
        int position = consolidateSpinnerSelection(this.adapter.getPosition(item));
        this.spinner.setSelection(position);
        onSelectionSet((Nameable) this.adapter.getItem(position));
        this.spinner.setOnItemSelectedListener(this);
    }

    private int consolidateSpinnerSelection(int position) {
        if (position != -1) {
            return position;
        }
        if (!this.adapter.containsNewOption()) {
            return 0;
        }
        int i = 1;
        if (this.adapter.getCount() <= 1) {
            i = 0;
        }
        return i;
    }

    private void onSelectionSet(Nameable selectedItem) {
        if (this.onItemSelectedListener != null) {
            if (selectedItem.getClass().equals(NewOption.class)) {
                this.onItemSelectedListener.onItemSelected(null);
            } else if (selectedItem.getClass().equals(StringOption.class)) {
                this.onItemSelectedListener.onStringOptionSelected(selectedItem.getName());
            } else {
                this.onItemSelectedListener.onItemSelected(selectedItem);
            }
        }
    }
}
