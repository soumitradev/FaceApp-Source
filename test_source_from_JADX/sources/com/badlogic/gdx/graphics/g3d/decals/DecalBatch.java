package com.badlogic.gdx.graphics.g3d.decals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SortedIntList;
import com.badlogic.gdx.utils.SortedIntList.Node;
import java.util.Iterator;

public class DecalBatch implements Disposable {
    private static final int DEFAULT_SIZE = 1000;
    private final SortedIntList<Array<Decal>> groupList;
    private final Pool<Array<Decal>> groupPool;
    private GroupStrategy groupStrategy;
    private Mesh mesh;
    private final Array<Array<Decal>> usedGroups;
    private float[] vertices;

    public DecalBatch(GroupStrategy groupStrategy) {
        this(1000, groupStrategy);
    }

    public DecalBatch(int size, GroupStrategy groupStrategy) {
        this.groupList = new SortedIntList();
        this.groupPool = new Pool<Array<Decal>>(16) {
            protected Array<Decal> newObject() {
                return new Array(false, 100);
            }
        };
        this.usedGroups = new Array(16);
        initialize(size);
        setGroupStrategy(groupStrategy);
    }

    public void setGroupStrategy(GroupStrategy groupStrategy) {
        this.groupStrategy = groupStrategy;
    }

    public void initialize(int size) {
        this.vertices = new float[(size * 24)];
        VertexDataType vertexDataType = VertexDataType.VertexArray;
        if (Gdx.gl30 != null) {
            vertexDataType = VertexDataType.VertexBufferObjectWithVAO;
        }
        int i = size * 4;
        int i2 = size * 6;
        r6 = new VertexAttribute[3];
        int i3 = 0;
        r6[0] = new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE);
        r6[1] = new VertexAttribute(4, 4, ShaderProgram.COLOR_ATTRIBUTE);
        r6[2] = new VertexAttribute(16, 2, "a_texCoord0");
        this.mesh = new Mesh(vertexDataType, false, i, i2, r6);
        short[] indices = new short[(size * 6)];
        int v = 0;
        while (true) {
            int i4 = i3;
            if (i4 < indices.length) {
                indices[i4] = (short) v;
                indices[i4 + 1] = (short) (v + 2);
                indices[i4 + 2] = (short) (v + 1);
                indices[i4 + 3] = (short) (v + 1);
                indices[i4 + 4] = (short) (v + 2);
                indices[i4 + 5] = (short) (v + 3);
                i3 = i4 + 6;
                v += 4;
            } else {
                this.mesh.setIndices(indices);
                return;
            }
        }
    }

    public int getSize() {
        return this.vertices.length / 24;
    }

    public void add(Decal decal) {
        int groupIndex = this.groupStrategy.decideGroup(decal);
        Array<Decal> targetGroup = (Array) this.groupList.get(groupIndex);
        if (targetGroup == null) {
            targetGroup = (Array) this.groupPool.obtain();
            targetGroup.clear();
            this.usedGroups.add(targetGroup);
            this.groupList.insert(groupIndex, targetGroup);
        }
        targetGroup.add(decal);
    }

    public void flush() {
        render();
        clear();
    }

    protected void render() {
        this.groupStrategy.beforeGroups();
        Iterator i$ = this.groupList.iterator();
        while (i$.hasNext()) {
            Node<Array<Decal>> group = (Node) i$.next();
            this.groupStrategy.beforeGroup(group.index, (Array) group.value);
            render(this.groupStrategy.getGroupShader(group.index), (Array) group.value);
            this.groupStrategy.afterGroup(group.index);
        }
        this.groupStrategy.afterGroups();
    }

    private void render(ShaderProgram shader, Array<Decal> decals) {
        DecalMaterial lastMaterial = null;
        int idx = 0;
        Iterator i$ = decals.iterator();
        while (i$.hasNext()) {
            Decal decal = (Decal) i$.next();
            if (lastMaterial == null || !lastMaterial.equals(decal.getMaterial())) {
                if (idx > 0) {
                    flush(shader, idx);
                    idx = 0;
                }
                decal.material.set();
                lastMaterial = decal.material;
            }
            decal.update();
            System.arraycopy(decal.vertices, 0, this.vertices, idx, decal.vertices.length);
            idx += decal.vertices.length;
            if (idx == this.vertices.length) {
                flush(shader, idx);
                idx = 0;
            }
        }
        if (idx > 0) {
            flush(shader, idx);
        }
    }

    protected void flush(ShaderProgram shader, int verticesPosition) {
        this.mesh.setVertices(this.vertices, 0, verticesPosition);
        this.mesh.render(shader, 4, 0, verticesPosition / 4);
    }

    protected void clear() {
        this.groupList.clear();
        this.groupPool.freeAll(this.usedGroups);
        this.usedGroups.clear();
    }

    public void dispose() {
        clear();
        this.vertices = null;
        this.mesh.dispose();
    }
}
