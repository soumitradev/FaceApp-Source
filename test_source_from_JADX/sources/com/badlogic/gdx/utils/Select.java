package com.badlogic.gdx.utils;

import java.util.Comparator;

public class Select {
    private static Select instance;
    private QuickSelect quickSelect;

    public static Select instance() {
        if (instance == null) {
            instance = new Select();
        }
        return instance;
    }

    public <T> T select(T[] items, Comparator<T> comp, int kthLowest, int size) {
        return items[selectIndex(items, comp, kthLowest, size)];
    }

    public <T> int selectIndex(T[] items, Comparator<T> comp, int kthLowest, int size) {
        if (size < 1) {
            throw new GdxRuntimeException("cannot select from empty array (size < 1)");
        } else if (kthLowest > size) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Kth rank is larger than size. k: ");
            stringBuilder.append(kthLowest);
            stringBuilder.append(", size: ");
            stringBuilder.append(size);
            throw new GdxRuntimeException(stringBuilder.toString());
        } else {
            int idx;
            if (kthLowest == 1) {
                idx = fastMin(items, comp, size);
            } else if (kthLowest == size) {
                idx = fastMax(items, comp, size);
            } else {
                if (this.quickSelect == null) {
                    this.quickSelect = new QuickSelect();
                }
                return this.quickSelect.select(items, comp, kthLowest, size);
            }
            return idx;
        }
    }

    private <T> int fastMin(T[] items, Comparator<T> comp, int size) {
        int lowestIdx = 0;
        for (int i = 1; i < size; i++) {
            if (comp.compare(items[i], items[lowestIdx]) < 0) {
                lowestIdx = i;
            }
        }
        return lowestIdx;
    }

    private <T> int fastMax(T[] items, Comparator<T> comp, int size) {
        int highestIdx = 0;
        for (int i = 1; i < size; i++) {
            if (comp.compare(items[i], items[highestIdx]) > 0) {
                highestIdx = i;
            }
        }
        return highestIdx;
    }
}
