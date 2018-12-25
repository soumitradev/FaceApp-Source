package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.IndexArray;
import com.badlogic.gdx.graphics.glutils.IndexBufferObject;
import com.badlogic.gdx.graphics.glutils.IndexBufferObjectSubData;
import com.badlogic.gdx.graphics.glutils.IndexData;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.VertexArray;
import com.badlogic.gdx.graphics.glutils.VertexBufferObject;
import com.badlogic.gdx.graphics.glutils.VertexBufferObjectSubData;
import com.badlogic.gdx.graphics.glutils.VertexBufferObjectWithVAO;
import com.badlogic.gdx.graphics.glutils.VertexData;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;
import java.util.Map;
import name.antonsmirnov.firmata.FormatHelper;

public class Mesh implements Disposable {
    static final Map<Application, Array<Mesh>> meshes = new HashMap();
    boolean autoBind = true;
    final IndexData indices;
    final boolean isVertexArray;
    private final Vector3 tmpV = new Vector3();
    final VertexData vertices;

    public enum VertexDataType {
        VertexArray,
        VertexBufferObject,
        VertexBufferObjectSubData,
        VertexBufferObjectWithVAO
    }

    protected Mesh(VertexData vertices, IndexData indices, boolean isVertexArray) {
        this.vertices = vertices;
        this.indices = indices;
        this.isVertexArray = isVertexArray;
        addManagedMesh(Gdx.app, this);
    }

    public Mesh(boolean isStatic, int maxVertices, int maxIndices, VertexAttribute... attributes) {
        this.vertices = makeVertexBuffer(isStatic, maxVertices, new VertexAttributes(attributes));
        this.indices = new IndexBufferObject(isStatic, maxIndices);
        this.isVertexArray = false;
        addManagedMesh(Gdx.app, this);
    }

    public Mesh(boolean isStatic, int maxVertices, int maxIndices, VertexAttributes attributes) {
        this.vertices = makeVertexBuffer(isStatic, maxVertices, attributes);
        this.indices = new IndexBufferObject(isStatic, maxIndices);
        this.isVertexArray = false;
        addManagedMesh(Gdx.app, this);
    }

    public Mesh(boolean staticVertices, boolean staticIndices, int maxVertices, int maxIndices, VertexAttributes attributes) {
        this.vertices = makeVertexBuffer(staticVertices, maxVertices, attributes);
        this.indices = new IndexBufferObject(staticIndices, maxIndices);
        this.isVertexArray = false;
        addManagedMesh(Gdx.app, this);
    }

    private VertexData makeVertexBuffer(boolean isStatic, int maxVertices, VertexAttributes vertexAttributes) {
        if (Gdx.gl30 != null) {
            return new VertexBufferObjectWithVAO(isStatic, maxVertices, vertexAttributes);
        }
        return new VertexBufferObject(isStatic, maxVertices, vertexAttributes);
    }

    public Mesh(VertexDataType type, boolean isStatic, int maxVertices, int maxIndices, VertexAttribute... attributes) {
        switch (type) {
            case VertexBufferObject:
                this.vertices = new VertexBufferObject(isStatic, maxVertices, attributes);
                this.indices = new IndexBufferObject(isStatic, maxIndices);
                this.isVertexArray = false;
                break;
            case VertexBufferObjectSubData:
                this.vertices = new VertexBufferObjectSubData(isStatic, maxVertices, attributes);
                this.indices = new IndexBufferObjectSubData(isStatic, maxIndices);
                this.isVertexArray = false;
                break;
            case VertexBufferObjectWithVAO:
                this.vertices = new VertexBufferObjectWithVAO(isStatic, maxVertices, attributes);
                this.indices = new IndexBufferObjectSubData(isStatic, maxIndices);
                this.isVertexArray = false;
                break;
            default:
                this.vertices = new VertexArray(maxVertices, attributes);
                this.indices = new IndexArray(maxIndices);
                this.isVertexArray = true;
                break;
        }
        addManagedMesh(Gdx.app, this);
    }

    public Mesh setVertices(float[] vertices) {
        this.vertices.setVertices(vertices, 0, vertices.length);
        return this;
    }

    public Mesh setVertices(float[] vertices, int offset, int count) {
        this.vertices.setVertices(vertices, offset, count);
        return this;
    }

    public Mesh updateVertices(int targetOffset, float[] source) {
        return updateVertices(targetOffset, source, 0, source.length);
    }

    public Mesh updateVertices(int targetOffset, float[] source, int sourceOffset, int count) {
        this.vertices.updateVertices(targetOffset, source, sourceOffset, count);
        return this;
    }

    public float[] getVertices(float[] vertices) {
        return getVertices(0, -1, vertices);
    }

    public float[] getVertices(int srcOffset, float[] vertices) {
        return getVertices(srcOffset, -1, vertices);
    }

    public float[] getVertices(int srcOffset, int count, float[] vertices) {
        return getVertices(srcOffset, count, vertices, 0);
    }

    public float[] getVertices(int srcOffset, int count, float[] vertices, int destOffset) {
        int max = (getNumVertices() * getVertexSize()) / 4;
        if (count == -1) {
            count = max - srcOffset;
            if (count > vertices.length - destOffset) {
                count = vertices.length - destOffset;
            }
        }
        if (srcOffset >= 0 && count > 0 && srcOffset + count <= max && destOffset >= 0) {
            if (destOffset < vertices.length) {
                if (vertices.length - destOffset < count) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("not enough room in vertices array, has ");
                    stringBuilder.append(vertices.length);
                    stringBuilder.append(" floats, needs ");
                    stringBuilder.append(count);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                int pos = getVerticesBuffer().position();
                getVerticesBuffer().position(srcOffset);
                getVerticesBuffer().get(vertices, destOffset, count);
                getVerticesBuffer().position(pos);
                return vertices;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public Mesh setIndices(short[] indices) {
        this.indices.setIndices(indices, 0, indices.length);
        return this;
    }

    public Mesh setIndices(short[] indices, int offset, int count) {
        this.indices.setIndices(indices, offset, count);
        return this;
    }

    public void getIndices(short[] indices) {
        getIndices(indices, 0);
    }

    public void getIndices(short[] indices, int destOffset) {
        getIndices(0, indices, destOffset);
    }

    public void getIndices(int srcOffset, short[] indices, int destOffset) {
        getIndices(srcOffset, -1, indices, destOffset);
    }

    public void getIndices(int srcOffset, int count, short[] indices, int destOffset) {
        StringBuilder stringBuilder;
        int max = getNumIndices();
        if (count < 0) {
            count = max - srcOffset;
        }
        if (srcOffset >= 0 && srcOffset < max) {
            if (srcOffset + count <= max) {
                if (indices.length - destOffset < count) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("not enough room in indices array, has ");
                    stringBuilder.append(indices.length);
                    stringBuilder.append(" shorts, needs ");
                    stringBuilder.append(count);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                int pos = getIndicesBuffer().position();
                getIndicesBuffer().position(srcOffset);
                getIndicesBuffer().get(indices, destOffset, count);
                getIndicesBuffer().position(pos);
                return;
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid range specified, offset: ");
        stringBuilder.append(srcOffset);
        stringBuilder.append(", count: ");
        stringBuilder.append(count);
        stringBuilder.append(", max: ");
        stringBuilder.append(max);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int getNumIndices() {
        return this.indices.getNumIndices();
    }

    public int getNumVertices() {
        return this.vertices.getNumVertices();
    }

    public int getMaxVertices() {
        return this.vertices.getNumMaxVertices();
    }

    public int getMaxIndices() {
        return this.indices.getNumMaxIndices();
    }

    public int getVertexSize() {
        return this.vertices.getAttributes().vertexSize;
    }

    public void setAutoBind(boolean autoBind) {
        this.autoBind = autoBind;
    }

    public void bind(ShaderProgram shader) {
        bind(shader, null);
    }

    public void bind(ShaderProgram shader, int[] locations) {
        this.vertices.bind(shader, locations);
        if (this.indices.getNumIndices() > 0) {
            this.indices.bind();
        }
    }

    public void unbind(ShaderProgram shader) {
        unbind(shader, null);
    }

    public void unbind(ShaderProgram shader, int[] locations) {
        this.vertices.unbind(shader, locations);
        if (this.indices.getNumIndices() > 0) {
            this.indices.unbind();
        }
    }

    public void render(ShaderProgram shader, int primitiveType) {
        render(shader, primitiveType, 0, this.indices.getNumMaxIndices() > 0 ? getNumIndices() : getNumVertices(), this.autoBind);
    }

    public void render(ShaderProgram shader, int primitiveType, int offset, int count) {
        render(shader, primitiveType, offset, count, this.autoBind);
    }

    public void render(ShaderProgram shader, int primitiveType, int offset, int count, boolean autoBind) {
        if (count != 0) {
            if (autoBind) {
                bind(shader);
            }
            if (this.isVertexArray) {
                if (this.indices.getNumIndices() > 0) {
                    Buffer buffer = this.indices.getBuffer();
                    int oldPosition = buffer.position();
                    int oldLimit = buffer.limit();
                    buffer.position(offset);
                    buffer.limit(offset + count);
                    Gdx.gl20.glDrawElements(primitiveType, count, (int) GL20.GL_UNSIGNED_SHORT, buffer);
                    buffer.position(oldPosition);
                    buffer.limit(oldLimit);
                } else {
                    Gdx.gl20.glDrawArrays(primitiveType, offset, count);
                }
            } else if (this.indices.getNumIndices() > 0) {
                Gdx.gl20.glDrawElements(primitiveType, count, (int) GL20.GL_UNSIGNED_SHORT, offset * 2);
            } else {
                Gdx.gl20.glDrawArrays(primitiveType, offset, count);
            }
            if (autoBind) {
                unbind(shader);
            }
        }
    }

    public void dispose() {
        if (meshes.get(Gdx.app) != null) {
            ((Array) meshes.get(Gdx.app)).removeValue(this, true);
        }
        this.vertices.dispose();
        this.indices.dispose();
    }

    public VertexAttribute getVertexAttribute(int usage) {
        VertexAttributes attributes = this.vertices.getAttributes();
        int len = attributes.size();
        for (int i = 0; i < len; i++) {
            if (attributes.get(i).usage == usage) {
                return attributes.get(i);
            }
        }
        return null;
    }

    public VertexAttributes getVertexAttributes() {
        return this.vertices.getAttributes();
    }

    public FloatBuffer getVerticesBuffer() {
        return this.vertices.getBuffer();
    }

    public BoundingBox calculateBoundingBox() {
        BoundingBox bbox = new BoundingBox();
        calculateBoundingBox(bbox);
        return bbox;
    }

    public void calculateBoundingBox(BoundingBox bbox) {
        int numVertices = getNumVertices();
        if (numVertices == 0) {
            throw new GdxRuntimeException("No vertices defined");
        }
        FloatBuffer verts = this.vertices.getBuffer();
        bbox.inf();
        VertexAttribute posAttrib = getVertexAttribute(1);
        int vertexSize = this.vertices.getAttributes().vertexSize / 4;
        int idx = posAttrib.offset / 4;
        int i = 0;
        int i2;
        switch (posAttrib.numComponents) {
            case 1:
                while (true) {
                    i2 = i;
                    if (i2 < numVertices) {
                        bbox.ext(verts.get(idx), 0.0f, 0.0f);
                        idx += vertexSize;
                        i = i2 + 1;
                    } else {
                        return;
                    }
                }
            case 2:
                while (true) {
                    i2 = i;
                    if (i2 < numVertices) {
                        bbox.ext(verts.get(idx), verts.get(idx + 1), 0.0f);
                        idx += vertexSize;
                        i = i2 + 1;
                    } else {
                        return;
                    }
                }
            case 3:
                while (true) {
                    i2 = i;
                    if (i2 < numVertices) {
                        bbox.ext(verts.get(idx), verts.get(idx + 1), verts.get(idx + 2));
                        idx += vertexSize;
                        i = i2 + 1;
                    } else {
                        return;
                    }
                }
            default:
                return;
        }
    }

    public BoundingBox calculateBoundingBox(BoundingBox out, int offset, int count) {
        return extendBoundingBox(out.inf(), offset, count);
    }

    public BoundingBox calculateBoundingBox(BoundingBox out, int offset, int count, Matrix4 transform) {
        return extendBoundingBox(out.inf(), offset, count, transform);
    }

    public BoundingBox extendBoundingBox(BoundingBox out, int offset, int count) {
        return extendBoundingBox(out, offset, count, null);
    }

    public BoundingBox extendBoundingBox(BoundingBox out, int offset, int count, Matrix4 transform) {
        int numIndices;
        Mesh mesh = this;
        BoundingBox boundingBox = out;
        int i = offset;
        int i2 = count;
        Matrix4 matrix4 = transform;
        int numIndices2 = getNumIndices();
        if (i < 0 || i2 < 1) {
            numIndices = numIndices2;
        } else if (i + i2 > numIndices2) {
            numIndices = numIndices2;
        } else {
            FloatBuffer verts = mesh.vertices.getBuffer();
            ShortBuffer index = mesh.indices.getBuffer();
            VertexAttribute posAttrib = getVertexAttribute(1);
            int posoff = posAttrib.offset / 4;
            int vertexSize = mesh.vertices.getAttributes().vertexSize / 4;
            int end = i + i2;
            VertexAttribute vertexAttribute;
            switch (posAttrib.numComponents) {
                case 1:
                    vertexAttribute = posAttrib;
                    for (numIndices2 = i; numIndices2 < end; numIndices2++) {
                        mesh.tmpV.set(verts.get((index.get(numIndices2) * vertexSize) + posoff), 0.0f, 0.0f);
                        if (matrix4 != null) {
                            mesh.tmpV.mul(matrix4);
                        }
                        boundingBox.ext(mesh.tmpV);
                    }
                    break;
                case 2:
                    vertexAttribute = posAttrib;
                    for (numIndices2 = i; numIndices2 < end; numIndices2++) {
                        posAttrib = (index.get(numIndices2) * vertexSize) + posoff;
                        mesh.tmpV.set(verts.get(posAttrib), verts.get(posAttrib + 1), 0.0f);
                        if (matrix4 != null) {
                            mesh.tmpV.mul(matrix4);
                        }
                        boundingBox.ext(mesh.tmpV);
                    }
                    break;
                case 3:
                    int i3 = i;
                    while (i3 < end) {
                        int idx = (index.get(i3) * vertexSize) + posoff;
                        vertexAttribute = posAttrib;
                        numIndices = numIndices2;
                        mesh.tmpV.set(verts.get(idx), verts.get(idx + 1), verts.get(idx + 2));
                        if (matrix4 != null) {
                            mesh.tmpV.mul(matrix4);
                        }
                        boundingBox.ext(mesh.tmpV);
                        i3++;
                        posAttrib = vertexAttribute;
                        numIndices2 = numIndices;
                    }
                    break;
            }
            return boundingBox;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not enough indices ( offset=");
        stringBuilder.append(i);
        stringBuilder.append(", count=");
        stringBuilder.append(i2);
        stringBuilder.append(", max=");
        stringBuilder.append(numIndices);
        stringBuilder.append(" )");
        throw new GdxRuntimeException(stringBuilder.toString());
    }

    public float calculateRadiusSquared(float centerX, float centerY, float centerZ, int offset, int count, Matrix4 transform) {
        Mesh mesh = this;
        float f = centerX;
        float f2 = centerY;
        float f3 = centerZ;
        int i = count;
        Matrix4 matrix4 = transform;
        int numIndices = getNumIndices();
        if (offset < 0 || i < 1) {
        } else if (offset + i > numIndices) {
            r16 = numIndices;
        } else {
            FloatBuffer verts = mesh.vertices.getBuffer();
            ShortBuffer index = mesh.indices.getBuffer();
            VertexAttribute posAttrib = getVertexAttribute(1);
            int posoff = posAttrib.offset / 4;
            int vertexSize = mesh.vertices.getAttributes().vertexSize / 4;
            int end = offset + i;
            float result = 0.0f;
            VertexAttribute vertexAttribute;
            int posoff2;
            switch (posAttrib.numComponents) {
                case 1:
                    vertexAttribute = posAttrib;
                    posoff2 = posoff;
                    for (i = offset; i < end; i++) {
                        mesh.tmpV.set(verts.get((index.get(i) * vertexSize) + posoff2), 0.0f, 0.0f);
                        if (matrix4 != null) {
                            mesh.tmpV.mul(matrix4);
                        }
                        float r = mesh.tmpV.sub(f, f2, f3).len2();
                        if (r > result) {
                            result = r;
                        }
                    }
                    break;
                case 2:
                    vertexAttribute = posAttrib;
                    posoff2 = posoff;
                    for (i = offset; i < end; i++) {
                        numIndices = (index.get(i) * vertexSize) + posoff2;
                        mesh.tmpV.set(verts.get(numIndices), verts.get(numIndices + 1), 0.0f);
                        if (matrix4 != null) {
                            mesh.tmpV.mul(matrix4);
                        }
                        posAttrib = mesh.tmpV.sub(f, f2, f3).len2();
                        if (posAttrib > result) {
                            result = posAttrib;
                        }
                    }
                    break;
                case 3:
                    int i2 = offset;
                    while (i2 < end) {
                        int idx = (index.get(i2) * vertexSize) + posoff;
                        r16 = numIndices;
                        vertexAttribute = posAttrib;
                        posoff2 = posoff;
                        mesh.tmpV.set(verts.get(idx), verts.get(idx + 1), verts.get(idx + 2));
                        if (matrix4 != null) {
                            mesh.tmpV.mul(matrix4);
                        }
                        float r2 = mesh.tmpV.sub(f, f2, f3).len2();
                        if (r2 > result) {
                            result = r2;
                        }
                        i2++;
                        numIndices = r16;
                        posAttrib = vertexAttribute;
                        posoff = posoff2;
                        i = count;
                    }
                    break;
            }
            return result;
        }
        throw new GdxRuntimeException("Not enough indices");
    }

    public float calculateRadius(float centerX, float centerY, float centerZ, int offset, int count, Matrix4 transform) {
        return (float) Math.sqrt((double) calculateRadiusSquared(centerX, centerY, centerZ, offset, count, transform));
    }

    public float calculateRadius(Vector3 center, int offset, int count, Matrix4 transform) {
        return calculateRadius(center.f120x, center.f121y, center.f122z, offset, count, transform);
    }

    public float calculateRadius(float centerX, float centerY, float centerZ, int offset, int count) {
        return calculateRadius(centerX, centerY, centerZ, offset, count, null);
    }

    public float calculateRadius(Vector3 center, int offset, int count) {
        return calculateRadius(center.f120x, center.f121y, center.f122z, offset, count, null);
    }

    public float calculateRadius(float centerX, float centerY, float centerZ) {
        return calculateRadius(centerX, centerY, centerZ, 0, getNumIndices(), null);
    }

    public float calculateRadius(Vector3 center) {
        return calculateRadius(center.f120x, center.f121y, center.f122z, 0, getNumIndices(), null);
    }

    public ShortBuffer getIndicesBuffer() {
        return this.indices.getBuffer();
    }

    private static void addManagedMesh(Application app, Mesh mesh) {
        Array<Mesh> managedResources = (Array) meshes.get(app);
        if (managedResources == null) {
            managedResources = new Array();
        }
        managedResources.add(mesh);
        meshes.put(app, managedResources);
    }

    public static void invalidateAllMeshes(Application app) {
        Array<Mesh> meshesArray = (Array) meshes.get(app);
        if (meshesArray != null) {
            for (int i = 0; i < meshesArray.size; i++) {
                ((Mesh) meshesArray.get(i)).vertices.invalidate();
                ((Mesh) meshesArray.get(i)).indices.invalidate();
            }
        }
    }

    public static void clearAllMeshes(Application app) {
        meshes.remove(app);
    }

    public static String getManagedStatus() {
        StringBuilder builder = new StringBuilder();
        builder.append("Managed meshes/app: { ");
        for (Application app : meshes.keySet()) {
            builder.append(((Array) meshes.get(app)).size);
            builder.append(FormatHelper.SPACE);
        }
        builder.append("}");
        return builder.toString();
    }

    public void scale(float scaleX, float scaleY, float scaleZ) {
        VertexAttribute posAttr = getVertexAttribute(1);
        int offset = posAttr.offset / 4;
        int numComponents = posAttr.numComponents;
        int numVertices = getNumVertices();
        int vertexSize = getVertexSize() / 4;
        float[] vertices = new float[(numVertices * vertexSize)];
        getVertices(vertices);
        int idx = offset;
        int i = 0;
        int i2;
        switch (numComponents) {
            case 1:
                while (i < numVertices) {
                    vertices[idx] = vertices[idx] * scaleX;
                    idx += vertexSize;
                    i++;
                }
                break;
            case 2:
                while (i < numVertices) {
                    vertices[idx] = vertices[idx] * scaleX;
                    i2 = idx + 1;
                    vertices[i2] = vertices[i2] * scaleY;
                    idx += vertexSize;
                    i++;
                }
                break;
            case 3:
                while (i < numVertices) {
                    vertices[idx] = vertices[idx] * scaleX;
                    i2 = idx + 1;
                    vertices[i2] = vertices[i2] * scaleY;
                    i2 = idx + 2;
                    vertices[i2] = vertices[i2] * scaleZ;
                    idx += vertexSize;
                    i++;
                }
                break;
            default:
                break;
        }
        setVertices(vertices);
    }

    public void transform(Matrix4 matrix) {
        transform(matrix, 0, getNumVertices());
    }

    public void transform(Matrix4 matrix, int start, int count) {
        VertexAttribute posAttr = getVertexAttribute(1);
        int posOffset = posAttr.offset / 4;
        int stride = getVertexSize() / 4;
        int numComponents = posAttr.numComponents;
        int numVertices = getNumVertices();
        float[] vertices = new float[(count * stride)];
        getVertices(start * stride, count * stride, vertices);
        transform(matrix, vertices, stride, posOffset, numComponents, 0, count);
        updateVertices(start * stride, vertices);
    }

    public static void transform(Matrix4 matrix, float[] vertices, int vertexSize, int offset, int dimensions, int start, int count) {
        if (offset >= 0 && dimensions >= 1) {
            if (offset + dimensions <= vertexSize) {
                if (start >= 0 && count >= 1) {
                    if ((start + count) * vertexSize <= vertices.length) {
                        Vector3 tmp = new Vector3();
                        int idx = (start * vertexSize) + offset;
                        int i = 0;
                        switch (dimensions) {
                            case 1:
                                while (i < count) {
                                    tmp.set(vertices[idx], 0.0f, 0.0f).mul(matrix);
                                    vertices[idx] = tmp.f120x;
                                    idx += vertexSize;
                                    i++;
                                }
                                return;
                            case 2:
                                while (i < count) {
                                    tmp.set(vertices[idx], vertices[idx + 1], 0.0f).mul(matrix);
                                    vertices[idx] = tmp.f120x;
                                    vertices[idx + 1] = tmp.f121y;
                                    idx += vertexSize;
                                    i++;
                                }
                                return;
                            case 3:
                                while (true) {
                                    int i2 = i;
                                    if (i2 < count) {
                                        tmp.set(vertices[idx], vertices[idx + 1], vertices[idx + 2]).mul(matrix);
                                        vertices[idx] = tmp.f120x;
                                        vertices[idx + 1] = tmp.f121y;
                                        vertices[idx + 2] = tmp.f122z;
                                        idx += vertexSize;
                                        i = i2 + 1;
                                    } else {
                                        return;
                                    }
                                }
                            default:
                                return;
                        }
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("start = ");
                stringBuilder.append(start);
                stringBuilder.append(", count = ");
                stringBuilder.append(count);
                stringBuilder.append(", vertexSize = ");
                stringBuilder.append(vertexSize);
                stringBuilder.append(", length = ");
                stringBuilder.append(vertices.length);
                throw new IndexOutOfBoundsException(stringBuilder.toString());
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public void transformUV(Matrix3 matrix) {
        transformUV(matrix, 0, getNumVertices());
    }

    protected void transformUV(Matrix3 matrix, int start, int count) {
        int offset = getVertexAttribute(16).offset / 4;
        int vertexSize = getVertexSize() / 4;
        float[] vertices = new float[(getNumVertices() * vertexSize)];
        getVertices(0, vertices.length, vertices);
        transformUV(matrix, vertices, vertexSize, offset, start, count);
        setVertices(vertices, 0, vertices.length);
    }

    public static void transformUV(Matrix3 matrix, float[] vertices, int vertexSize, int offset, int start, int count) {
        if (start >= 0 && count >= 1) {
            if ((start + count) * vertexSize <= vertices.length) {
                Vector2 tmp = new Vector2();
                int idx = (start * vertexSize) + offset;
                for (int i = 0; i < count; i++) {
                    tmp.set(vertices[idx], vertices[idx + 1]).mul(matrix);
                    vertices[idx] = tmp.f16x;
                    vertices[idx + 1] = tmp.f17y;
                    idx += vertexSize;
                }
                return;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("start = ");
        stringBuilder.append(start);
        stringBuilder.append(", count = ");
        stringBuilder.append(count);
        stringBuilder.append(", vertexSize = ");
        stringBuilder.append(vertexSize);
        stringBuilder.append(", length = ");
        stringBuilder.append(vertices.length);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public Mesh copy(boolean isStatic, boolean removeDuplicates, int[] usage) {
        int as;
        int size;
        int i;
        short[] checks;
        short newVertexSize;
        int numVertices;
        short[] checks2;
        int idx;
        short[] checks3;
        short i2;
        int numVertices2;
        float[] tmp;
        short newIndex;
        int vertexSize;
        boolean found;
        int k;
        Mesh result;
        boolean z = isStatic;
        int[] iArr = usage;
        int vertexSize2 = getVertexSize() / 4;
        int numVertices3 = getNumVertices();
        float[] vertices = new float[(numVertices3 * vertexSize2)];
        getVertices(0, vertices.length, vertices);
        VertexAttribute[] attrs = null;
        short s = (short) 0;
        if (iArr != null) {
            as = 0;
            size = 0;
            for (i = 0; i < iArr.length; i++) {
                if (getVertexAttribute(iArr[i]) != null) {
                    size += getVertexAttribute(iArr[i]).numComponents;
                    as++;
                }
            }
            if (size > 0) {
                attrs = new VertexAttribute[as];
                checks = new short[size];
                i = -1;
                int ai = -1;
                newVertexSize = (short) 0;
                int i3 = 0;
                while (i3 < iArr.length) {
                    VertexAttribute a = getVertexAttribute(iArr[i3]);
                    if (a == null) {
                        numVertices = numVertices3;
                        checks2 = checks;
                    } else {
                        idx = i;
                        i = 0;
                        while (i < a.numComponents) {
                            idx++;
                            checks[idx] = (short) (a.offset + i);
                            i++;
                            iArr = usage;
                        }
                        ai++;
                        numVertices = numVertices3;
                        checks2 = checks;
                        attrs[ai] = new VertexAttribute(a.usage, a.numComponents, a.alias);
                        newVertexSize += a.numComponents;
                        i = idx;
                    }
                    i3++;
                    numVertices3 = numVertices;
                    checks = checks2;
                    iArr = usage;
                }
                numVertices = numVertices3;
                checks2 = checks;
                s = newVertexSize;
                if (checks2 != null) {
                    checks3 = new short[vertexSize2];
                    for (i2 = (short) 0; i2 < vertexSize2; i2 = (short) (i2 + 1)) {
                        checks3[i2] = i2;
                    }
                    s = vertexSize2;
                } else {
                    checks3 = checks2;
                }
                numVertices3 = getNumIndices();
                checks = null;
                if (numVertices3 <= 0) {
                    checks = new short[numVertices3];
                    getIndices(checks);
                    if (!removeDuplicates) {
                        if (s != vertexSize2) {
                            short s2 = vertexSize2;
                            numVertices2 = numVertices;
                        }
                    }
                    tmp = new float[vertices.length];
                    size = 0;
                    i = 0;
                    while (i < numVertices3) {
                        as = checks[i] * vertexSize2;
                        newIndex = (short) -1;
                        if (removeDuplicates) {
                            vertexSize = vertexSize2;
                        } else {
                            newVertexSize = (short) -1;
                            newIndex = (short) 0;
                            while (newIndex < size && newVertexSize < (short) 0) {
                                idx = newIndex * s;
                                found = true;
                                k = 0;
                                while (true) {
                                    vertexSize = vertexSize2;
                                    vertexSize2 = k;
                                    if (vertexSize2 < checks3.length && found) {
                                        if (tmp[idx + vertexSize2] != vertices[as + checks3[vertexSize2]]) {
                                            found = false;
                                        }
                                        k = vertexSize2 + 1;
                                        vertexSize2 = vertexSize;
                                    } else if (!found) {
                                        newVertexSize = newIndex;
                                    }
                                }
                                if (!found) {
                                    newVertexSize = newIndex;
                                }
                                newIndex = (short) (newIndex + 1);
                                vertexSize2 = vertexSize;
                            }
                            vertexSize = vertexSize2;
                            newIndex = newVertexSize;
                        }
                        if (newIndex <= (short) 0) {
                            checks[i] = newIndex;
                        } else {
                            numVertices2 = size * s;
                            for (vertexSize2 = 0; vertexSize2 < checks3.length; vertexSize2++) {
                                tmp[numVertices2 + vertexSize2] = vertices[as + checks3[vertexSize2]];
                            }
                            checks[i] = (short) size;
                            size++;
                        }
                        i++;
                        vertexSize2 = vertexSize;
                    }
                    vertices = tmp;
                    numVertices2 = size;
                } else {
                    numVertices2 = numVertices;
                }
                if (attrs != null) {
                    result = new Mesh(z, numVertices2, checks != null ? 0 : checks.length, getVertexAttributes());
                } else {
                    result = new Mesh(z, numVertices2, checks != null ? 0 : checks.length, attrs);
                }
                result.setVertices(vertices, 0, numVertices2 * s);
                result.setIndices(checks);
                return result;
            }
        }
        numVertices = numVertices3;
        checks2 = null;
        if (checks2 != null) {
            checks3 = checks2;
        } else {
            checks3 = new short[vertexSize2];
            for (i2 = (short) 0; i2 < vertexSize2; i2 = (short) (i2 + 1)) {
                checks3[i2] = i2;
            }
            s = vertexSize2;
        }
        numVertices3 = getNumIndices();
        checks = null;
        if (numVertices3 <= 0) {
            numVertices2 = numVertices;
        } else {
            checks = new short[numVertices3];
            getIndices(checks);
            if (removeDuplicates) {
                if (s != vertexSize2) {
                    short s22 = vertexSize2;
                    numVertices2 = numVertices;
                }
            }
            tmp = new float[vertices.length];
            size = 0;
            i = 0;
            while (i < numVertices3) {
                as = checks[i] * vertexSize2;
                newIndex = (short) -1;
                if (removeDuplicates) {
                    vertexSize = vertexSize2;
                } else {
                    newVertexSize = (short) -1;
                    newIndex = (short) 0;
                    while (newIndex < size) {
                        idx = newIndex * s;
                        found = true;
                        k = 0;
                        while (true) {
                            vertexSize = vertexSize2;
                            vertexSize2 = k;
                            if (vertexSize2 < checks3.length) {
                                break;
                            }
                            break;
                            k = vertexSize2 + 1;
                            vertexSize2 = vertexSize;
                        }
                        if (!found) {
                            newVertexSize = newIndex;
                        }
                        newIndex = (short) (newIndex + 1);
                        vertexSize2 = vertexSize;
                    }
                    vertexSize = vertexSize2;
                    newIndex = newVertexSize;
                }
                if (newIndex <= (short) 0) {
                    numVertices2 = size * s;
                    for (vertexSize2 = 0; vertexSize2 < checks3.length; vertexSize2++) {
                        tmp[numVertices2 + vertexSize2] = vertices[as + checks3[vertexSize2]];
                    }
                    checks[i] = (short) size;
                    size++;
                } else {
                    checks[i] = newIndex;
                }
                i++;
                vertexSize2 = vertexSize;
            }
            vertices = tmp;
            numVertices2 = size;
        }
        if (attrs != null) {
            if (checks != null) {
            }
            result = new Mesh(z, numVertices2, checks != null ? 0 : checks.length, attrs);
        } else {
            if (checks != null) {
            }
            result = new Mesh(z, numVertices2, checks != null ? 0 : checks.length, getVertexAttributes());
        }
        result.setVertices(vertices, 0, numVertices2 * s);
        result.setIndices(checks);
        return result;
    }

    public Mesh copy(boolean isStatic) {
        return copy(isStatic, false, null);
    }
}
