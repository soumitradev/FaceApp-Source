package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Table.Cell;
import java.lang.reflect.Array;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@GwtCompatible
@Immutable
final class DenseImmutableTable<R, C, V> extends RegularImmutableTable<R, C, V> {
    private final int[] columnCounts = new int[this.columnKeyToIndex.size()];
    private final ImmutableMap<C, Integer> columnKeyToIndex;
    private final ImmutableMap<C, Map<R, V>> columnMap;
    private final int[] iterationOrderColumn;
    private final int[] iterationOrderRow;
    private final int[] rowCounts = new int[this.rowKeyToIndex.size()];
    private final ImmutableMap<R, Integer> rowKeyToIndex;
    private final ImmutableMap<R, Map<C, V>> rowMap;
    private final V[][] values;

    private final class Column extends DenseImmutableTable$ImmutableArrayMap<R, V> {
        private final int columnIndex;

        Column(int columnIndex) {
            super(DenseImmutableTable.this.columnCounts[columnIndex]);
            this.columnIndex = columnIndex;
        }

        ImmutableMap<R, Integer> keyToIndex() {
            return DenseImmutableTable.this.rowKeyToIndex;
        }

        V getValue(int keyIndex) {
            return DenseImmutableTable.this.values[keyIndex][this.columnIndex];
        }

        boolean isPartialView() {
            return true;
        }
    }

    private final class ColumnMap extends DenseImmutableTable$ImmutableArrayMap<C, Map<R, V>> {
        private ColumnMap() {
            super(DenseImmutableTable.this.columnCounts.length);
        }

        ImmutableMap<C, Integer> keyToIndex() {
            return DenseImmutableTable.this.columnKeyToIndex;
        }

        Map<R, V> getValue(int keyIndex) {
            return new Column(keyIndex);
        }

        boolean isPartialView() {
            return false;
        }
    }

    private final class Row extends DenseImmutableTable$ImmutableArrayMap<C, V> {
        private final int rowIndex;

        Row(int rowIndex) {
            super(DenseImmutableTable.this.rowCounts[rowIndex]);
            this.rowIndex = rowIndex;
        }

        ImmutableMap<C, Integer> keyToIndex() {
            return DenseImmutableTable.this.columnKeyToIndex;
        }

        V getValue(int keyIndex) {
            return DenseImmutableTable.this.values[this.rowIndex][keyIndex];
        }

        boolean isPartialView() {
            return true;
        }
    }

    private final class RowMap extends DenseImmutableTable$ImmutableArrayMap<R, Map<C, V>> {
        private RowMap() {
            super(DenseImmutableTable.this.rowCounts.length);
        }

        ImmutableMap<R, Integer> keyToIndex() {
            return DenseImmutableTable.this.rowKeyToIndex;
        }

        Map<C, V> getValue(int keyIndex) {
            return new Row(keyIndex);
        }

        boolean isPartialView() {
            return false;
        }
    }

    DenseImmutableTable(ImmutableList<Cell<R, C, V>> cellList, ImmutableSet<R> rowSpace, ImmutableSet<C> columnSpace) {
        this.values = (Object[][]) Array.newInstance(Object.class, new int[]{rowSpace.size(), columnSpace.size()});
        this.rowKeyToIndex = Maps.indexMap(rowSpace);
        this.columnKeyToIndex = Maps.indexMap(columnSpace);
        int[] iterationOrderRow = new int[cellList.size()];
        int[] iterationOrderColumn = new int[cellList.size()];
        for (int i = 0; i < cellList.size(); i++) {
            Cell<R, C, V> cell = (Cell) cellList.get(i);
            R rowKey = cell.getRowKey();
            C columnKey = cell.getColumnKey();
            int rowIndex = ((Integer) r0.rowKeyToIndex.get(rowKey)).intValue();
            int columnIndex = ((Integer) r0.columnKeyToIndex.get(columnKey)).intValue();
            Preconditions.checkArgument(r0.values[rowIndex][columnIndex] == null, "duplicate key: (%s, %s)", rowKey, columnKey);
            r0.values[rowIndex][columnIndex] = cell.getValue();
            int[] iArr = r0.rowCounts;
            iArr[rowIndex] = iArr[rowIndex] + 1;
            iArr = r0.columnCounts;
            iArr[columnIndex] = iArr[columnIndex] + 1;
            iterationOrderRow[i] = rowIndex;
            iterationOrderColumn[i] = columnIndex;
        }
        ImmutableList<Cell<R, C, V>> immutableList = cellList;
        r0.iterationOrderRow = iterationOrderRow;
        r0.iterationOrderColumn = iterationOrderColumn;
        r0.rowMap = new RowMap();
        r0.columnMap = new ColumnMap();
    }

    public ImmutableMap<C, Map<R, V>> columnMap() {
        return this.columnMap;
    }

    public ImmutableMap<R, Map<C, V>> rowMap() {
        return this.rowMap;
    }

    public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
        Integer rowIndex = (Integer) this.rowKeyToIndex.get(rowKey);
        Integer columnIndex = (Integer) this.columnKeyToIndex.get(columnKey);
        if (rowIndex != null) {
            if (columnIndex != null) {
                return this.values[rowIndex.intValue()][columnIndex.intValue()];
            }
        }
        return null;
    }

    public int size() {
        return this.iterationOrderRow.length;
    }

    Cell<R, C, V> getCell(int index) {
        int rowIndex = this.iterationOrderRow[index];
        int columnIndex = this.iterationOrderColumn[index];
        return cellOf(rowKeySet().asList().get(rowIndex), columnKeySet().asList().get(columnIndex), this.values[rowIndex][columnIndex]);
    }

    V getValue(int index) {
        return this.values[this.iterationOrderRow[index]][this.iterationOrderColumn[index]];
    }
}
