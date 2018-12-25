package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue.Triangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public final class UnweightedMeshSpawnShapeValue extends MeshSpawnShapeValue {
    private short[] indices;
    private int positionOffset;
    private int triangleCount;
    private int vertexCount;
    private int vertexSize;
    private float[] vertices;

    public UnweightedMeshSpawnShapeValue(UnweightedMeshSpawnShapeValue value) {
        super(value);
        load(value);
    }

    public void setMesh(Mesh mesh, Model model) {
        super.setMesh(mesh, model);
        this.vertexSize = mesh.getVertexSize() / 4;
        this.positionOffset = mesh.getVertexAttribute(1).offset / 4;
        int indicesCount = mesh.getNumIndices();
        if (indicesCount > 0) {
            this.indices = new short[indicesCount];
            mesh.getIndices(this.indices);
            this.triangleCount = this.indices.length / 3;
        } else {
            this.indices = null;
        }
        this.vertexCount = mesh.getNumVertices();
        this.vertices = new float[(this.vertexCount * this.vertexSize)];
        mesh.getVertices(this.vertices);
    }

    public void spawnAux(Vector3 vector, float percent) {
        if (this.indices == null) {
            int p1Offset = r0.positionOffset + (MathUtils.random(r0.vertexCount - 3) * r0.vertexSize);
            int p2Offset = r0.vertexSize + p1Offset;
            int p3Offset = r0.vertexSize + p2Offset;
            Triangle.pick(r0.vertices[p1Offset], r0.vertices[p1Offset + 1], r0.vertices[p1Offset + 2], r0.vertices[p2Offset], r0.vertices[p2Offset + 1], r0.vertices[p2Offset + 2], r0.vertices[p3Offset], r0.vertices[p3Offset + 1], r0.vertices[p3Offset + 2], vector);
            return;
        }
        int triangleIndex = MathUtils.random(r0.triangleCount - 1) * 3;
        p1Offset = (r0.indices[triangleIndex] * r0.vertexSize) + r0.positionOffset;
        p2Offset = (r0.indices[triangleIndex + 1] * r0.vertexSize) + r0.positionOffset;
        p3Offset = (r0.indices[triangleIndex + 2] * r0.vertexSize) + r0.positionOffset;
        Triangle.pick(r0.vertices[p1Offset], r0.vertices[p1Offset + 1], r0.vertices[p1Offset + 2], r0.vertices[p2Offset], r0.vertices[p2Offset + 1], r0.vertices[p2Offset + 2], r0.vertices[p3Offset], r0.vertices[p3Offset + 1], r0.vertices[p3Offset + 2], vector);
    }

    public SpawnShapeValue copy() {
        return new UnweightedMeshSpawnShapeValue(this);
    }
}
