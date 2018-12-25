package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Table.Cell;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.concurrent.Immutable;

@GwtCompatible
@Immutable
final class SparseImmutableTable<R, C, V> extends RegularImmutableTable<R, C, V> {
    private final ImmutableMap<C, Map<R, V>> columnMap;
    private final int[] iterationOrderColumn;
    private final int[] iterationOrderRow;
    private final ImmutableMap<R, Map<C, V>> rowMap;

    SparseImmutableTable(ImmutableList<Cell<R, C, V>> cellList, ImmutableSet<R> rowSpace, ImmutableSet<C> columnSpace) {
        Map<R, Integer> rowIndex;
        SparseImmutableTable sparseImmutableTable = this;
        Map<R, Integer> rowIndex2 = Maps.indexMap(rowSpace);
        Map<R, Map<C, V>> rows = Maps.newLinkedHashMap();
        Iterator i$ = rowSpace.iterator();
        while (i$.hasNext()) {
            rows.put(i$.next(), new LinkedHashMap());
        }
        Map<C, Map<R, V>> columns = Maps.newLinkedHashMap();
        Iterator i$2 = columnSpace.iterator();
        while (i$2.hasNext()) {
            columns.put(i$2.next(), new LinkedHashMap());
        }
        int[] iterationOrderRow = new int[cellList.size()];
        int[] iterationOrderColumn = new int[cellList.size()];
        int i = 0;
        while (i < cellList.size()) {
            Cell<R, C, V> cell = (Cell) cellList.get(i);
            R rowKey = cell.getRowKey();
            C columnKey = cell.getColumnKey();
            V value = cell.getValue();
            iterationOrderRow[i] = ((Integer) rowIndex2.get(rowKey)).intValue();
            Map<C, V> thisRow = (Map) rows.get(rowKey);
            iterationOrderColumn[i] = thisRow.size();
            V oldValue = thisRow.put(columnKey, value);
            if (oldValue != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Duplicate value for row=");
                stringBuilder.append(rowKey);
                stringBuilder.append(", column=");
                stringBuilder.append(columnKey);
                stringBuilder.append(": ");
                stringBuilder.append(value);
                stringBuilder.append(", ");
                stringBuilder.append(oldValue);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            rowIndex = rowIndex2;
            ((Map) columns.get(columnKey)).put(rowKey, value);
            i++;
            rowIndex2 = rowIndex;
        }
        ImmutableList<Cell<R, C, V>> immutableList = cellList;
        rowIndex = rowIndex2;
        sparseImmutableTable.iterationOrderRow = iterationOrderRow;
        sparseImmutableTable.iterationOrderColumn = iterationOrderColumn;
        ImmutableMap$Builder<R, Map<C, V>> rowBuilder = new ImmutableMap$Builder(rows.size());
        for (Entry<R, Map<C, V>> row : rows.entrySet()) {
            rowBuilder.put(row.getKey(), ImmutableMap.copyOf((Map) row.getValue()));
        }
        sparseImmutableTable.rowMap = rowBuilder.build();
        ImmutableMap$Builder<C, Map<R, V>> columnBuilder = new ImmutableMap$Builder(columns.size());
        for (Entry<C, Map<R, V>> col : columns.entrySet()) {
            columnBuilder.put(col.getKey(), ImmutableMap.copyOf((Map) col.getValue()));
        }
        sparseImmutableTable.columnMap = columnBuilder.build();
    }

    public ImmutableMap<C, Map<R, V>> columnMap() {
        return this.columnMap;
    }

    public ImmutableMap<R, Map<C, V>> rowMap() {
        return this.rowMap;
    }

    public int size() {
        return this.iterationOrderRow.length;
    }

    Cell<R, C, V> getCell(int index) {
        Entry<R, Map<C, V>> rowEntry = (Entry) this.rowMap.entrySet().asList().get(this.iterationOrderRow[index]);
        ImmutableMap<C, V> row = (ImmutableMap) rowEntry.getValue();
        Entry<C, V> colEntry = (Entry) row.entrySet().asList().get(this.iterationOrderColumn[index]);
        return cellOf(rowEntry.getKey(), colEntry.getKey(), colEntry.getValue());
    }

    V getValue(int index) {
        ImmutableMap<C, V> row = (ImmutableMap) this.rowMap.values().asList().get(this.iterationOrderRow[index]);
        return row.values().asList().get(this.iterationOrderColumn[index]);
    }
}
