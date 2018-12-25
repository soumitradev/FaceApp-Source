package org.catrobat.catroid.formulaeditor;

import android.util.SparseArray;
import android.util.SparseIntArray;

public class ExternInternRepresentationMapping {
    public static final int MAPPING_NOT_FOUND = Integer.MIN_VALUE;
    private SparseIntArray externInternMapping = new SparseIntArray();
    private int externStringLength = 0;
    private SparseArray<ExternToken> internExternMapping = new SparseArray();

    public void putMapping(int externStringStartIndex, int externStringEndIndex, int internListIndex) {
        this.externInternMapping.put(externStringStartIndex, internListIndex);
        this.externInternMapping.put(externStringEndIndex - 1, internListIndex);
        this.internExternMapping.put(internListIndex, new ExternToken(externStringStartIndex, externStringEndIndex));
        if (externStringEndIndex >= this.externStringLength) {
            this.externStringLength = externStringEndIndex;
        }
    }

    public int getExternTokenStartIndex(int internIndex) {
        ExternToken externToken = (ExternToken) this.internExternMapping.get(internIndex);
        if (externToken == null) {
            return Integer.MIN_VALUE;
        }
        return externToken.getStartIndex();
    }

    public int getExternTokenEndIndex(int internIndex) {
        ExternToken externToken = (ExternToken) this.internExternMapping.get(internIndex);
        if (externToken == null) {
            return Integer.MIN_VALUE;
        }
        return externToken.getEndIndex();
    }

    public int getInternTokenByExternIndex(int externIndex) {
        if (externIndex < 0) {
            return Integer.MIN_VALUE;
        }
        int searchDownInternToken = searchDown(this.externInternMapping, externIndex - 1);
        int currentInternToken = this.externInternMapping.get(externIndex, Integer.MIN_VALUE);
        int searchUpInternToken = searchUp(this.externInternMapping, externIndex + 1);
        if (currentInternToken != Integer.MIN_VALUE) {
            return currentInternToken;
        }
        if (searchDownInternToken == Integer.MIN_VALUE || searchUpInternToken == Integer.MIN_VALUE || searchDownInternToken != searchUpInternToken) {
            return Integer.MIN_VALUE;
        }
        return searchDownInternToken;
    }

    public int getExternTokenStartOffset(int externIndex, int internTokenOffsetTo) {
        int searchIndex = externIndex;
        while (searchIndex >= 0) {
            if (this.externInternMapping.get(searchIndex, Integer.MIN_VALUE) == Integer.MIN_VALUE || this.externInternMapping.get(searchIndex, Integer.MIN_VALUE) != internTokenOffsetTo) {
                searchIndex--;
            } else {
                int rightEdgeSelectionToken = getExternTokenStartOffset(searchIndex - 1, internTokenOffsetTo);
                if (rightEdgeSelectionToken == -1) {
                    return externIndex - searchIndex;
                }
                return ((externIndex - searchIndex) + rightEdgeSelectionToken) + 1;
            }
        }
        return -1;
    }

    private int searchDown(SparseIntArray mapping, int index) {
        for (int searchIndex = index; searchIndex >= 0; searchIndex--) {
            if (mapping.get(searchIndex, Integer.MIN_VALUE) != Integer.MIN_VALUE) {
                return mapping.get(searchIndex);
            }
        }
        return Integer.MIN_VALUE;
    }

    private int searchUp(SparseIntArray mapping, int index) {
        for (int searchIndex = index; searchIndex < this.externStringLength; searchIndex++) {
            if (mapping.get(searchIndex, Integer.MIN_VALUE) != Integer.MIN_VALUE) {
                return mapping.get(searchIndex);
            }
        }
        return Integer.MIN_VALUE;
    }
}
