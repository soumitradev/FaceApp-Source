package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue.Triangle;
import com.badlogic.gdx.math.CumulativeDistribution;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public final class WeightMeshSpawnShapeValue extends MeshSpawnShapeValue {
    private CumulativeDistribution<Triangle> distribution = new CumulativeDistribution();

    public WeightMeshSpawnShapeValue(WeightMeshSpawnShapeValue value) {
        super(value);
        load(value);
    }

    public void init() {
        calculateWeights();
    }

    public void calculateWeights() {
        this.distribution.clear();
        VertexAttributes attributes = this.mesh.getVertexAttributes();
        int indicesCount = this.mesh.getNumIndices();
        int vertexCount = this.mesh.getNumVertices();
        int vertexSize = (short) (attributes.vertexSize / 4);
        int positionOffset = (short) (attributes.findByUsage(1).offset / 4);
        float[] vertices = new float[(vertexCount * vertexSize)];
        this.mesh.getVertices(vertices);
        float f = 2.0f;
        int i = 0;
        float x1;
        float y1;
        float z1;
        float x2;
        float y2;
        float z2;
        float area;
        int i2;
        int i3;
        int i4;
        if (indicesCount > 0) {
            short[] indices = new short[indicesCount];
            r0.mesh.getIndices(indices);
            while (i < indicesCount) {
                int p1Offset = (indices[i] * vertexSize) + positionOffset;
                int p2Offset = (indices[i + 1] * vertexSize) + positionOffset;
                int p3Offset = (indices[i + 2] * vertexSize) + positionOffset;
                x1 = vertices[p1Offset];
                y1 = vertices[p1Offset + 1];
                z1 = vertices[p1Offset + 2];
                x2 = vertices[p2Offset];
                y2 = vertices[p2Offset + 1];
                z2 = vertices[p2Offset + 2];
                float x3 = vertices[p3Offset];
                float y3 = vertices[p3Offset + 1];
                float z3 = vertices[p3Offset + 2];
                area = Math.abs(((((y2 - y3) * x1) + ((y3 - y1) * x2)) + ((y1 - y2) * x3)) / f);
                Triangle triangle = r13;
                CumulativeDistribution cumulativeDistribution = r0.distribution;
                VertexAttributes attributes2 = attributes;
                attributes = area;
                Triangle triangle2 = new Triangle(x1, y1, z1, x2, y2, z2, x3, y3, z3);
                cumulativeDistribution.add(triangle2, attributes);
                i += 3;
                attributes = attributes2;
                f = 2.0f;
            }
            i2 = indicesCount;
            i3 = vertexCount;
            i4 = positionOffset;
        } else {
            while (true) {
                int i5 = i;
                if (i5 >= vertexCount) {
                    break;
                }
                int p1Offset2 = i5 + positionOffset;
                i = p1Offset2 + vertexSize;
                int p3Offset2 = i + vertexSize;
                float x12 = vertices[p1Offset2];
                float y12 = vertices[p1Offset2 + 1];
                float z12 = vertices[p1Offset2 + 2];
                x1 = vertices[i];
                y1 = vertices[i + 1];
                z1 = vertices[i + 2];
                x2 = vertices[p3Offset2];
                y2 = vertices[p3Offset2 + 1];
                z2 = vertices[p3Offset2 + 2];
                area = Math.abs(((((y1 - y2) * x12) + ((y2 - y12) * x1)) + ((y12 - y1) * x2)) / 2.0f);
                i2 = indicesCount;
                Triangle triangle3 = r10;
                i3 = vertexCount;
                CumulativeDistribution cumulativeDistribution2 = r0.distribution;
                i4 = positionOffset;
                float area2 = area;
                Triangle triangle4 = new Triangle(x12, y12, z12, x1, y1, z1, x2, y2, z2);
                cumulativeDistribution2.add(triangle3, area2);
                i = i5 + vertexSize;
                indicesCount = i2;
                vertexCount = i3;
                positionOffset = i4;
            }
            i3 = vertexCount;
            i4 = positionOffset;
        }
        r0.distribution.generateNormalized();
    }

    public void spawnAux(Vector3 vector, float percent) {
        Triangle t = (Triangle) this.distribution.value();
        float a = MathUtils.random();
        float b = MathUtils.random();
        vector.set((t.x1 + ((t.x2 - t.x1) * a)) + ((t.x3 - t.x1) * b), (t.y1 + ((t.y2 - t.y1) * a)) + ((t.y3 - t.y1) * b), (t.z1 + ((t.z2 - t.z1) * a)) + ((t.z3 - t.z1) * b));
    }

    public SpawnShapeValue copy() {
        return new WeightMeshSpawnShapeValue(this);
    }
}
