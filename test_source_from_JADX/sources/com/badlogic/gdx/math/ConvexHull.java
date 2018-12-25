package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ShortArray;

public class ConvexHull {
    private final FloatArray hull = new FloatArray();
    private final IntArray indices = new IntArray();
    private final ShortArray originalIndices = new ShortArray(false, 0);
    private final IntArray quicksortStack = new IntArray();
    private float[] sortedPoints;

    public FloatArray computePolygon(FloatArray points, boolean sorted) {
        return computePolygon(points.items, 0, points.size, sorted);
    }

    public FloatArray computePolygon(float[] polygon, boolean sorted) {
        return computePolygon(polygon, 0, polygon.length, sorted);
    }

    public FloatArray computePolygon(float[] points, int offset, int count, boolean sorted) {
        int i;
        int end = offset + count;
        if (!sorted) {
            if (this.sortedPoints == null || this.sortedPoints.length < count) {
                this.sortedPoints = new float[count];
            }
            System.arraycopy(points, offset, this.sortedPoints, 0, count);
            points = this.sortedPoints;
            offset = 0;
            sort(points, count);
        }
        FloatArray hull = this.hull;
        hull.clear();
        for (i = offset; i < end; i += 2) {
            float x = points[i];
            float y = points[i + 1];
            while (hull.size >= 4 && ccw(x, y) <= 0.0f) {
                hull.size -= 2;
            }
            hull.add(x);
            hull.add(y);
        }
        int t = hull.size + 2;
        for (i = end - 4; i >= offset; i -= 2) {
            y = points[i];
            float y2 = points[i + 1];
            while (hull.size >= t && ccw(y, y2) <= 0.0f) {
                hull.size -= 2;
            }
            hull.add(y);
            hull.add(y2);
        }
        return hull;
    }

    public IntArray computeIndices(FloatArray points, boolean sorted, boolean yDown) {
        return computeIndices(points.items, 0, points.size, sorted, yDown);
    }

    public IntArray computeIndices(float[] polygon, boolean sorted, boolean yDown) {
        return computeIndices(polygon, 0, polygon.length, sorted, yDown);
    }

    public IntArray computeIndices(float[] points, int offset, int count, boolean sorted, boolean yDown) {
        int end = offset + count;
        if (!sorted) {
            if (this.sortedPoints == null || this.sortedPoints.length < count) {
                this.sortedPoints = new float[count];
            }
            System.arraycopy(points, offset, this.sortedPoints, 0, count);
            points = this.sortedPoints;
            offset = 0;
            sortWithIndices(points, count, yDown);
        }
        IntArray indices = this.indices;
        indices.clear();
        FloatArray hull = this.hull;
        hull.clear();
        int i = offset;
        int index = i / 2;
        while (i < end) {
            float x = points[i];
            float y = points[i + 1];
            while (hull.size >= 4 && ccw(x, y) <= 0.0f) {
                hull.size -= 2;
                indices.size--;
            }
            hull.add(x);
            hull.add(y);
            indices.add(index);
            i += 2;
            index++;
        }
        i = end - 4;
        index = i / 2;
        int t = hull.size + 2;
        while (i >= offset) {
            y = points[i];
            float y2 = points[i + 1];
            while (hull.size >= t && ccw(y, y2) <= 0.0f) {
                hull.size -= 2;
                indices.size--;
            }
            hull.add(y);
            hull.add(y2);
            indices.add(index);
            i -= 2;
            index--;
        }
        if (!sorted) {
            short[] originalIndicesArray = this.originalIndices.items;
            int[] indicesArray = indices.items;
            t = indices.size;
            for (int i2 = 0; i2 < t; i2++) {
                indicesArray[i2] = originalIndicesArray[indicesArray[i2]];
            }
        }
        return indices;
    }

    private float ccw(float p3x, float p3y) {
        FloatArray hull = this.hull;
        int size = hull.size;
        float p1x = hull.get(size - 4);
        float p1y = hull.get(size - 3);
        return ((hull.get(size - 2) - p1x) * (p3y - p1y)) - ((hull.peek() - p1y) * (p3x - p1x));
    }

    private void sort(float[] values, int count) {
        int upper = count - 1;
        IntArray stack = this.quicksortStack;
        stack.add(0);
        stack.add(upper - 1);
        while (stack.size > 0) {
            upper = stack.pop();
            int lower = stack.pop();
            if (upper > lower) {
                int i = quicksortPartition(values, lower, upper);
                if (i - lower > upper - i) {
                    stack.add(lower);
                    stack.add(i - 2);
                }
                stack.add(i + 2);
                stack.add(upper);
                if (upper - i >= i - lower) {
                    stack.add(lower);
                    stack.add(i - 2);
                }
            }
        }
    }

    private int quicksortPartition(float[] values, int lower, int upper) {
        float x = values[lower];
        float y = values[lower + 1];
        int up = upper;
        int down = lower;
        while (down < up) {
            int down2 = down;
            while (down2 < up && values[down2] <= x) {
                down2 += 2;
            }
            while (true) {
                if (values[up] <= x) {
                    if (values[up] != x || values[up + 1] >= y) {
                        if (down2 < up) {
                            float temp = values[down2];
                            values[down2] = values[up];
                            values[up] = temp;
                            temp = values[down2 + 1];
                            values[down2 + 1] = values[up + 1];
                            values[up + 1] = temp;
                        }
                    }
                }
                up -= 2;
            }
            if (down2 < up) {
                float temp2 = values[down2];
                values[down2] = values[up];
                values[up] = temp2;
                temp2 = values[down2 + 1];
                values[down2 + 1] = values[up + 1];
                values[up + 1] = temp2;
            }
            down = down2;
        }
        values[lower] = values[up];
        values[up] = x;
        values[lower + 1] = values[up + 1];
        values[up + 1] = y;
        return up;
    }

    private void sortWithIndices(float[] values, int count, boolean yDown) {
        short pointCount = count / 2;
        this.originalIndices.clear();
        this.originalIndices.ensureCapacity(pointCount);
        short[] originalIndicesArray = this.originalIndices.items;
        for (short i = (short) 0; i < pointCount; i = (short) (i + 1)) {
            originalIndicesArray[i] = i;
        }
        int upper = count - 1;
        IntArray stack = this.quicksortStack;
        stack.add(0);
        stack.add(upper - 1);
        while (stack.size > 0) {
            int upper2 = stack.pop();
            int lower = stack.pop();
            if (upper2 > lower) {
                int i2 = quicksortPartitionWithIndices(values, lower, upper2, yDown, originalIndicesArray);
                if (i2 - lower > upper2 - i2) {
                    stack.add(lower);
                    stack.add(i2 - 2);
                }
                stack.add(i2 + 2);
                stack.add(upper2);
                if (upper2 - i2 >= i2 - lower) {
                    stack.add(lower);
                    stack.add(i2 - 2);
                }
            }
            upper = upper2;
        }
    }

    private int quicksortPartitionWithIndices(float[] values, int lower, int upper, boolean yDown, short[] originalIndices) {
        float x = values[lower];
        float y = values[lower + 1];
        int up = upper;
        int down = lower;
        while (down < up) {
            int down2 = down;
            while (down2 < up && values[down2] <= x) {
                down2 += 2;
            }
            if (yDown) {
                while (true) {
                    if (values[up] <= x && (values[up] != x || values[up + 1] >= y)) {
                        break;
                    }
                    up -= 2;
                }
            } else {
                while (true) {
                    if (values[up] <= x) {
                        if (values[up] != x || values[up + 1] <= y) {
                            break;
                        }
                    }
                    up -= 2;
                }
            }
            if (down2 < up) {
                float temp = values[down2];
                values[down2] = values[up];
                values[up] = temp;
                temp = values[down2 + 1];
                values[down2 + 1] = values[up + 1];
                values[up + 1] = temp;
                short tempIndex = originalIndices[down2 / 2];
                originalIndices[down2 / 2] = originalIndices[up / 2];
                originalIndices[up / 2] = tempIndex;
            }
            down = down2;
        }
        values[lower] = values[up];
        values[up] = x;
        values[lower + 1] = values[up + 1];
        values[up + 1] = y;
        short tempIndex2 = originalIndices[lower / 2];
        originalIndices[lower / 2] = originalIndices[up / 2];
        originalIndices[up / 2] = tempIndex2;
        return up;
    }
}
