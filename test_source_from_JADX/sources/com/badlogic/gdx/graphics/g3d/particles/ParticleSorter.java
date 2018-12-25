package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderData;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public abstract class ParticleSorter {
    static final Vector3 TMP_V1 = new Vector3();
    protected Camera camera;

    public static class Distance extends ParticleSorter {
        private int currentSize = 0;
        private float[] distances;
        private int[] particleIndices;
        private int[] particleOffsets;

        public void ensureCapacity(int capacity) {
            if (this.currentSize < capacity) {
                this.distances = new float[capacity];
                this.particleIndices = new int[capacity];
                this.particleOffsets = new int[capacity];
                this.currentSize = capacity;
            }
        }

        public <T extends ParticleControllerRenderData> int[] sort(Array<T> renderData) {
            float[] val = this.camera.view.val;
            float cx = val[2];
            float cy = val[6];
            float cz = val[10];
            int count = 0;
            int i = 0;
            Iterator i$ = renderData.iterator();
            while (i$.hasNext()) {
                ParticleControllerRenderData data = (ParticleControllerRenderData) i$.next();
                int k = 0;
                int c = data.controller.particles.size + i;
                while (i < c) {
                    this.distances[i] = ((data.positionChannel.data[k + 0] * cx) + (data.positionChannel.data[k + 1] * cy)) + (data.positionChannel.data[k + 2] * cz);
                    this.particleIndices[i] = i;
                    i++;
                    k += data.positionChannel.strideSize;
                }
                count += data.controller.particles.size;
            }
            qsort(0, count - 1);
            for (i = 0; i < count; i++) {
                this.particleOffsets[this.particleIndices[i]] = i;
            }
            return this.particleOffsets;
        }

        public void qsort(int si, int ei) {
            if (si < ei) {
                int j;
                int tmpIndex;
                if (ei - si <= 8) {
                    for (int i = si; i <= ei; i++) {
                        j = i;
                        while (j > si && this.distances[j - 1] > this.distances[j]) {
                            float tmp = this.distances[j];
                            this.distances[j] = this.distances[j - 1];
                            this.distances[j - 1] = tmp;
                            tmpIndex = this.particleIndices[j];
                            this.particleIndices[j] = this.particleIndices[j - 1];
                            this.particleIndices[j - 1] = tmpIndex;
                            j--;
                        }
                    }
                    return;
                }
                float pivot = this.distances[si];
                j = si + 1;
                int particlesPivotIndex = this.particleIndices[si];
                for (tmpIndex = si + 1; tmpIndex <= ei; tmpIndex++) {
                    if (pivot > this.distances[tmpIndex]) {
                        if (tmpIndex > j) {
                            float tmp2 = this.distances[tmpIndex];
                            this.distances[tmpIndex] = this.distances[j];
                            this.distances[j] = tmp2;
                            int tmpIndex2 = this.particleIndices[tmpIndex];
                            this.particleIndices[tmpIndex] = this.particleIndices[j];
                            this.particleIndices[j] = tmpIndex2;
                        }
                        j++;
                    }
                }
                this.distances[si] = this.distances[j - 1];
                this.distances[j - 1] = pivot;
                this.particleIndices[si] = this.particleIndices[j - 1];
                this.particleIndices[j - 1] = particlesPivotIndex;
                qsort(si, j - 2);
                qsort(j, ei);
            }
        }
    }

    public static class None extends ParticleSorter {
        int currentCapacity = 0;
        int[] indices;

        public void ensureCapacity(int capacity) {
            if (this.currentCapacity < capacity) {
                this.indices = new int[capacity];
                for (int i = 0; i < capacity; i++) {
                    this.indices[i] = i;
                }
                this.currentCapacity = capacity;
            }
        }

        public <T extends ParticleControllerRenderData> int[] sort(Array<T> array) {
            return this.indices;
        }
    }

    public abstract <T extends ParticleControllerRenderData> int[] sort(Array<T> array);

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void ensureCapacity(int capacity) {
    }
}
