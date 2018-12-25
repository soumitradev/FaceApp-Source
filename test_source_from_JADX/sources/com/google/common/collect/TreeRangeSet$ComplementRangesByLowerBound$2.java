package com.google.common.collect;

import com.google.common.collect.TreeRangeSet.ComplementRangesByLowerBound;
import java.util.Map.Entry;

class TreeRangeSet$ComplementRangesByLowerBound$2 extends AbstractIterator<Entry<Cut<C>, Range<C>>> {
    Cut<C> nextComplementRangeUpperBound = this.val$firstComplementRangeUpperBound;
    final /* synthetic */ ComplementRangesByLowerBound this$0;
    final /* synthetic */ Cut val$firstComplementRangeUpperBound;
    final /* synthetic */ PeekingIterator val$positiveItr;

    TreeRangeSet$ComplementRangesByLowerBound$2(ComplementRangesByLowerBound complementRangesByLowerBound, Cut cut, PeekingIterator peekingIterator) {
        this.this$0 = complementRangesByLowerBound;
        this.val$firstComplementRangeUpperBound = cut;
        this.val$positiveItr = peekingIterator;
    }

    protected Entry<Cut<C>, Range<C>> computeNext() {
        if (this.nextComplementRangeUpperBound == Cut.belowAll()) {
            return (Entry) endOfData();
        }
        Range<C> positiveRange;
        if (this.val$positiveItr.hasNext()) {
            positiveRange = (Range) this.val$positiveItr.next();
            Range<C> negativeRange = Range.create(positiveRange.upperBound, this.nextComplementRangeUpperBound);
            this.nextComplementRangeUpperBound = positiveRange.lowerBound;
            if (ComplementRangesByLowerBound.access$100(this.this$0).lowerBound.isLessThan(negativeRange.lowerBound)) {
                return Maps.immutableEntry(negativeRange.lowerBound, negativeRange);
            }
        } else if (ComplementRangesByLowerBound.access$100(this.this$0).lowerBound.isLessThan(Cut.belowAll())) {
            positiveRange = Range.create(Cut.belowAll(), this.nextComplementRangeUpperBound);
            this.nextComplementRangeUpperBound = Cut.belowAll();
            return Maps.immutableEntry(Cut.belowAll(), positiveRange);
        }
        return (Entry) endOfData();
    }
}
