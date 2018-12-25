package org.catrobat.catroid.ui.recyclerview.adapter.multiselection;

import java.util.SortedSet;
import java.util.TreeSet;

public class MultiSelectionManager {
    private SortedSet<Integer> selectedPositions = new TreeSet();

    public void toggleSelection(int position) {
        if (isPositionSelected(position)) {
            removeSelection(position);
        } else {
            setSelection(position);
        }
    }

    public void setSelectionTo(boolean selection, int position) {
        if (selection) {
            setSelection(position);
        } else {
            removeSelection(position);
        }
    }

    public boolean isPositionSelected(int position) {
        return this.selectedPositions.contains(Integer.valueOf(position));
    }

    private void setSelection(int position) {
        this.selectedPositions.add(Integer.valueOf(position));
    }

    private void removeSelection(int position) {
        this.selectedPositions.remove(Integer.valueOf(position));
    }

    public SortedSet<Integer> getSelectedPositions() {
        return this.selectedPositions;
    }

    public void updateSelection(int fromPosition, int toPosition) {
        if (!this.selectedPositions.contains(Integer.valueOf(fromPosition)) || this.selectedPositions.contains(Integer.valueOf(toPosition))) {
            if (this.selectedPositions.contains(Integer.valueOf(toPosition)) && !this.selectedPositions.contains(Integer.valueOf(fromPosition))) {
                this.selectedPositions.remove(Integer.valueOf(toPosition));
                this.selectedPositions.add(Integer.valueOf(fromPosition));
            }
            return;
        }
        this.selectedPositions.remove(Integer.valueOf(fromPosition));
        this.selectedPositions.add(Integer.valueOf(toPosition));
    }

    public boolean isSelectionActive() {
        return this.selectedPositions.isEmpty() ^ 1;
    }

    public void clearSelection() {
        this.selectedPositions.clear();
    }
}
