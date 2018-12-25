package android.support.v7.widget;

import android.view.View;
import java.util.ArrayList;

class StaggeredGridLayoutManager$Span {
    static final int INVALID_LINE = Integer.MIN_VALUE;
    int mCachedEnd = Integer.MIN_VALUE;
    int mCachedStart = Integer.MIN_VALUE;
    int mDeletedSize = 0;
    final int mIndex;
    ArrayList<View> mViews = new ArrayList();
    final /* synthetic */ StaggeredGridLayoutManager this$0;

    StaggeredGridLayoutManager$Span(StaggeredGridLayoutManager this$0, int index) {
        this.this$0 = this$0;
        this.mIndex = index;
    }

    int getStartLine(int def) {
        if (this.mCachedStart != Integer.MIN_VALUE) {
            return this.mCachedStart;
        }
        if (this.mViews.size() == 0) {
            return def;
        }
        calculateCachedStart();
        return this.mCachedStart;
    }

    void calculateCachedStart() {
        View startView = (View) this.mViews.get(0);
        StaggeredGridLayoutManager$LayoutParams lp = getLayoutParams(startView);
        this.mCachedStart = this.this$0.mPrimaryOrientation.getDecoratedStart(startView);
        if (lp.mFullSpan) {
            FullSpanItem fsi = this.this$0.mLazySpanLookup.getFullSpanItem(lp.getViewLayoutPosition());
            if (fsi != null && fsi.mGapDir == -1) {
                this.mCachedStart -= fsi.getGapForSpan(this.mIndex);
            }
        }
    }

    int getStartLine() {
        if (this.mCachedStart != Integer.MIN_VALUE) {
            return this.mCachedStart;
        }
        calculateCachedStart();
        return this.mCachedStart;
    }

    int getEndLine(int def) {
        if (this.mCachedEnd != Integer.MIN_VALUE) {
            return this.mCachedEnd;
        }
        if (this.mViews.size() == 0) {
            return def;
        }
        calculateCachedEnd();
        return this.mCachedEnd;
    }

    void calculateCachedEnd() {
        View endView = (View) this.mViews.get(this.mViews.size() - 1);
        StaggeredGridLayoutManager$LayoutParams lp = getLayoutParams(endView);
        this.mCachedEnd = this.this$0.mPrimaryOrientation.getDecoratedEnd(endView);
        if (lp.mFullSpan) {
            FullSpanItem fsi = this.this$0.mLazySpanLookup.getFullSpanItem(lp.getViewLayoutPosition());
            if (fsi != null && fsi.mGapDir == 1) {
                this.mCachedEnd += fsi.getGapForSpan(this.mIndex);
            }
        }
    }

    int getEndLine() {
        if (this.mCachedEnd != Integer.MIN_VALUE) {
            return this.mCachedEnd;
        }
        calculateCachedEnd();
        return this.mCachedEnd;
    }

    void prependToSpan(View view) {
        StaggeredGridLayoutManager$LayoutParams lp = getLayoutParams(view);
        lp.mSpan = this;
        this.mViews.add(0, view);
        this.mCachedStart = Integer.MIN_VALUE;
        if (this.mViews.size() == 1) {
            this.mCachedEnd = Integer.MIN_VALUE;
        }
        if (lp.isItemRemoved() || lp.isItemChanged()) {
            this.mDeletedSize += this.this$0.mPrimaryOrientation.getDecoratedMeasurement(view);
        }
    }

    void appendToSpan(View view) {
        StaggeredGridLayoutManager$LayoutParams lp = getLayoutParams(view);
        lp.mSpan = this;
        this.mViews.add(view);
        this.mCachedEnd = Integer.MIN_VALUE;
        if (this.mViews.size() == 1) {
            this.mCachedStart = Integer.MIN_VALUE;
        }
        if (lp.isItemRemoved() || lp.isItemChanged()) {
            this.mDeletedSize += this.this$0.mPrimaryOrientation.getDecoratedMeasurement(view);
        }
    }

    void cacheReferenceLineAndClear(boolean reverseLayout, int offset) {
        int reference;
        if (reverseLayout) {
            reference = getEndLine(Integer.MIN_VALUE);
        } else {
            reference = getStartLine(Integer.MIN_VALUE);
        }
        clear();
        if (reference != Integer.MIN_VALUE) {
            if ((!reverseLayout || reference >= this.this$0.mPrimaryOrientation.getEndAfterPadding()) && (reverseLayout || reference <= this.this$0.mPrimaryOrientation.getStartAfterPadding())) {
                if (offset != Integer.MIN_VALUE) {
                    reference += offset;
                }
                this.mCachedEnd = reference;
                this.mCachedStart = reference;
            }
        }
    }

    void clear() {
        this.mViews.clear();
        invalidateCache();
        this.mDeletedSize = 0;
    }

    void invalidateCache() {
        this.mCachedStart = Integer.MIN_VALUE;
        this.mCachedEnd = Integer.MIN_VALUE;
    }

    void setLine(int line) {
        this.mCachedStart = line;
        this.mCachedEnd = line;
    }

    void popEnd() {
        int size = this.mViews.size();
        View end = (View) this.mViews.remove(size - 1);
        StaggeredGridLayoutManager$LayoutParams lp = getLayoutParams(end);
        lp.mSpan = null;
        if (lp.isItemRemoved() || lp.isItemChanged()) {
            this.mDeletedSize -= this.this$0.mPrimaryOrientation.getDecoratedMeasurement(end);
        }
        if (size == 1) {
            this.mCachedStart = Integer.MIN_VALUE;
        }
        this.mCachedEnd = Integer.MIN_VALUE;
    }

    void popStart() {
        View start = (View) this.mViews.remove(0);
        StaggeredGridLayoutManager$LayoutParams lp = getLayoutParams(start);
        lp.mSpan = null;
        if (this.mViews.size() == 0) {
            this.mCachedEnd = Integer.MIN_VALUE;
        }
        if (lp.isItemRemoved() || lp.isItemChanged()) {
            this.mDeletedSize -= this.this$0.mPrimaryOrientation.getDecoratedMeasurement(start);
        }
        this.mCachedStart = Integer.MIN_VALUE;
    }

    public int getDeletedSize() {
        return this.mDeletedSize;
    }

    StaggeredGridLayoutManager$LayoutParams getLayoutParams(View view) {
        return (StaggeredGridLayoutManager$LayoutParams) view.getLayoutParams();
    }

    void onOffset(int dt) {
        if (this.mCachedStart != Integer.MIN_VALUE) {
            this.mCachedStart += dt;
        }
        if (this.mCachedEnd != Integer.MIN_VALUE) {
            this.mCachedEnd += dt;
        }
    }

    public int findFirstVisibleItemPosition() {
        if (this.this$0.mReverseLayout) {
            return findOneVisibleChild(this.mViews.size() - 1, -1, false);
        }
        return findOneVisibleChild(0, this.mViews.size(), false);
    }

    public int findFirstPartiallyVisibleItemPosition() {
        if (this.this$0.mReverseLayout) {
            return findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
        }
        return findOnePartiallyVisibleChild(0, this.mViews.size(), true);
    }

    public int findFirstCompletelyVisibleItemPosition() {
        if (this.this$0.mReverseLayout) {
            return findOneVisibleChild(this.mViews.size() - 1, -1, true);
        }
        return findOneVisibleChild(0, this.mViews.size(), true);
    }

    public int findLastVisibleItemPosition() {
        if (this.this$0.mReverseLayout) {
            return findOneVisibleChild(0, this.mViews.size(), false);
        }
        return findOneVisibleChild(this.mViews.size() - 1, -1, false);
    }

    public int findLastPartiallyVisibleItemPosition() {
        if (this.this$0.mReverseLayout) {
            return findOnePartiallyVisibleChild(0, this.mViews.size(), true);
        }
        return findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
    }

    public int findLastCompletelyVisibleItemPosition() {
        if (this.this$0.mReverseLayout) {
            return findOneVisibleChild(0, this.mViews.size(), true);
        }
        return findOneVisibleChild(this.mViews.size() - 1, -1, true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    int findOnePartiallyOrCompletelyVisibleChild(int r16, int r17, boolean r18, boolean r19, boolean r20) {
        /*
        r15 = this;
        r0 = r15;
        r1 = r17;
        r2 = r0.this$0;
        r2 = r2.mPrimaryOrientation;
        r2 = r2.getStartAfterPadding();
        r3 = r0.this$0;
        r3 = r3.mPrimaryOrientation;
        r3 = r3.getEndAfterPadding();
        r4 = -1;
        r5 = 1;
        r6 = r16;
        if (r1 <= r6) goto L_0x001b;
    L_0x0019:
        r7 = 1;
        goto L_0x001c;
    L_0x001b:
        r7 = -1;
    L_0x001c:
        r8 = r6;
    L_0x001d:
        if (r8 == r1) goto L_0x0076;
    L_0x001f:
        r9 = r0.mViews;
        r9 = r9.get(r8);
        r9 = (android.view.View) r9;
        r10 = r0.this$0;
        r10 = r10.mPrimaryOrientation;
        r10 = r10.getDecoratedStart(r9);
        r11 = r0.this$0;
        r11 = r11.mPrimaryOrientation;
        r11 = r11.getDecoratedEnd(r9);
        r12 = 0;
        if (r20 == 0) goto L_0x0040;
    L_0x003a:
        if (r10 > r3) goto L_0x003e;
    L_0x003c:
        r13 = 1;
        goto L_0x0043;
    L_0x003e:
        r13 = 0;
        goto L_0x0043;
    L_0x0040:
        if (r10 >= r3) goto L_0x003e;
    L_0x0042:
        goto L_0x003c;
    L_0x0043:
        if (r20 == 0) goto L_0x004a;
    L_0x0045:
        if (r11 < r2) goto L_0x0049;
    L_0x0047:
        r12 = 1;
        goto L_0x004d;
    L_0x0049:
        goto L_0x004d;
    L_0x004a:
        if (r11 <= r2) goto L_0x0049;
    L_0x004c:
        goto L_0x0047;
    L_0x004d:
        if (r13 == 0) goto L_0x0074;
    L_0x004f:
        if (r12 == 0) goto L_0x0074;
    L_0x0051:
        if (r18 == 0) goto L_0x0060;
    L_0x0053:
        if (r19 == 0) goto L_0x0060;
    L_0x0055:
        if (r10 < r2) goto L_0x0074;
    L_0x0057:
        if (r11 > r3) goto L_0x0074;
    L_0x0059:
        r4 = r0.this$0;
        r4 = r4.getPosition(r9);
        return r4;
    L_0x0060:
        if (r19 == 0) goto L_0x0069;
    L_0x0062:
        r4 = r0.this$0;
        r4 = r4.getPosition(r9);
        return r4;
    L_0x0069:
        if (r10 < r2) goto L_0x006d;
    L_0x006b:
        if (r11 <= r3) goto L_0x0074;
    L_0x006d:
        r4 = r0.this$0;
        r4 = r4.getPosition(r9);
        return r4;
    L_0x0074:
        r8 = r8 + r7;
        goto L_0x001d;
    L_0x0076:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager$Span.findOnePartiallyOrCompletelyVisibleChild(int, int, boolean, boolean, boolean):int");
    }

    int findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible) {
        return findOnePartiallyOrCompletelyVisibleChild(fromIndex, toIndex, completelyVisible, true, false);
    }

    int findOnePartiallyVisibleChild(int fromIndex, int toIndex, boolean acceptEndPointInclusion) {
        return findOnePartiallyOrCompletelyVisibleChild(fromIndex, toIndex, false, false, acceptEndPointInclusion);
    }

    public View getFocusableViewAfter(int referenceChildPosition, int layoutDir) {
        View candidate = null;
        int i;
        if (layoutDir != -1) {
            i = this.mViews.size() - 1;
            while (i >= 0) {
                View view = (View) this.mViews.get(i);
                if (!this.this$0.mReverseLayout || this.this$0.getPosition(view) < referenceChildPosition) {
                    if (this.this$0.mReverseLayout || this.this$0.getPosition(view) > referenceChildPosition) {
                        if (!view.hasFocusable()) {
                            break;
                        }
                        candidate = view;
                        i--;
                    } else {
                        break;
                    }
                }
                break;
            }
        }
        i = this.mViews.size();
        int i2 = 0;
        while (i2 < i) {
            View view2 = (View) this.mViews.get(i2);
            if (!this.this$0.mReverseLayout || this.this$0.getPosition(view2) > referenceChildPosition) {
                if (this.this$0.mReverseLayout || this.this$0.getPosition(view2) < referenceChildPosition) {
                    if (!view2.hasFocusable()) {
                        break;
                    }
                    candidate = view2;
                    i2++;
                } else {
                    break;
                }
            }
            break;
        }
        return candidate;
    }
}
