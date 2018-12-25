package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.NumberUtils;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ShortArray;
import java.util.Iterator;
import kotlin.jvm.internal.ShortCompanionObject;

public class MeshBuilder implements MeshPartBuilder {
    private static IntIntMap indicesMap = null;
    private static final Array<Matrix4> matrices4Array = new Array();
    private static final Pool<Matrix4> matrices4Pool = new C07712();
    private static final ShortArray tmpIndices = new ShortArray();
    private static final FloatArray tmpVertices = new FloatArray();
    private static final Vector3 vTmp = new Vector3();
    private static final Array<Vector3> vectorArray = new Array();
    private static final Pool<Vector3> vectorPool = new C07701();
    private VertexAttributes attributes;
    private int colOffset;
    private int colSize;
    private final Color color = new Color(Color.WHITE);
    private int cpOffset;
    private boolean hasColor = false;
    private boolean hasUVTransform = false;
    private ShortArray indices = new ShortArray();
    private int istart;
    private short lastIndex = (short) -1;
    private final Matrix4 matTmp1 = new Matrix4();
    private int norOffset;
    private final Matrix3 normalTransform = new Matrix3();
    private MeshPart part;
    private Array<MeshPart> parts = new Array();
    private int posOffset;
    private int posSize;
    private final Matrix4 positionTransform = new Matrix4();
    private int primitiveType;
    private int stride;
    private final Color tempC1 = new Color();
    private final Vector3 tempV1 = new Vector3();
    private final Vector3 tempV2 = new Vector3();
    private final Vector3 tempV3 = new Vector3();
    private final Vector3 tempV4 = new Vector3();
    private final Vector3 tempV5 = new Vector3();
    private final Vector3 tempV6 = new Vector3();
    private final Vector3 tempV7 = new Vector3();
    private final Vector3 tempV8 = new Vector3();
    private final Vector3 tmpNormal = new Vector3();
    private float uOffset = 0.0f;
    private float uScale = 1.0f;
    private int uvOffset;
    private float vOffset = 0.0f;
    private float vScale = 1.0f;
    private final VertexInfo vertTmp1 = new VertexInfo();
    private final VertexInfo vertTmp2 = new VertexInfo();
    private final VertexInfo vertTmp3 = new VertexInfo();
    private final VertexInfo vertTmp4 = new VertexInfo();
    private final VertexInfo vertTmp5 = new VertexInfo();
    private final VertexInfo vertTmp6 = new VertexInfo();
    private final VertexInfo vertTmp7 = new VertexInfo();
    private final VertexInfo vertTmp8 = new VertexInfo();
    private float[] vertex;
    private boolean vertexTransformationEnabled = false;
    private FloatArray vertices = new FloatArray();
    private short vindex;

    /* renamed from: com.badlogic.gdx.graphics.g3d.utils.MeshBuilder$1 */
    static class C07701 extends Pool<Vector3> {
        C07701() {
        }

        protected Vector3 newObject() {
            return new Vector3();
        }
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.utils.MeshBuilder$2 */
    static class C07712 extends Pool<Matrix4> {
        C07712() {
        }

        protected Matrix4 newObject() {
            return new Matrix4();
        }
    }

    public static VertexAttributes createAttributes(long usage) {
        Array<VertexAttribute> attrs = new Array();
        if ((usage & 1) == 1) {
            attrs.add(new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE));
        }
        if ((usage & 2) == 2) {
            attrs.add(new VertexAttribute(2, 4, ShaderProgram.COLOR_ATTRIBUTE));
        }
        if ((usage & 4) == 4) {
            attrs.add(new VertexAttribute(4, 4, ShaderProgram.COLOR_ATTRIBUTE));
        }
        if ((usage & 8) == 8) {
            attrs.add(new VertexAttribute(8, 3, ShaderProgram.NORMAL_ATTRIBUTE));
        }
        if ((usage & 16) == 16) {
            attrs.add(new VertexAttribute(16, 2, "a_texCoord0"));
        }
        VertexAttribute[] attributes = new VertexAttribute[attrs.size];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = (VertexAttribute) attrs.get(i);
        }
        return new VertexAttributes(attributes);
    }

    public void begin(long attributes) {
        begin(createAttributes(attributes), -1);
    }

    public void begin(VertexAttributes attributes) {
        begin(attributes, -1);
    }

    public void begin(long attributes, int primitiveType) {
        begin(createAttributes(attributes), primitiveType);
    }

    public void begin(VertexAttributes attributes, int primitiveType) {
        if (this.attributes != null) {
            throw new RuntimeException("Call end() first");
        }
        this.attributes = attributes;
        this.vertices.clear();
        this.indices.clear();
        this.parts.clear();
        int i = 0;
        this.vindex = (short) 0;
        this.istart = 0;
        this.part = null;
        this.stride = attributes.vertexSize / 4;
        if (this.vertex == null || this.vertex.length < this.stride) {
            this.vertex = new float[this.stride];
        }
        VertexAttribute a = attributes.findByUsage(1);
        if (a == null) {
            throw new GdxRuntimeException("Cannot build mesh without position attribute");
        }
        this.posOffset = a.offset / 4;
        this.posSize = a.numComponents;
        a = attributes.findByUsage(8);
        int i2 = -1;
        this.norOffset = a == null ? -1 : a.offset / 4;
        a = attributes.findByUsage(2);
        this.colOffset = a == null ? -1 : a.offset / 4;
        if (a != null) {
            i = a.numComponents;
        }
        this.colSize = i;
        VertexAttribute a2 = attributes.findByUsage(4);
        this.cpOffset = a2 == null ? -1 : a2.offset / 4;
        a2 = attributes.findByUsage(16);
        if (a2 != null) {
            i2 = a2.offset / 4;
        }
        this.uvOffset = i2;
        setColor(null);
        setVertexTransform(null);
        setUVRange(null);
        this.primitiveType = primitiveType;
    }

    private void endpart() {
        if (this.part != null) {
            this.part.indexOffset = this.istart;
            this.part.numVertices = this.indices.size - this.istart;
            this.istart = this.indices.size;
            this.part = null;
        }
    }

    public MeshPart part(String id, int primitiveType) {
        return part(id, primitiveType, new MeshPart());
    }

    public MeshPart part(String id, int primitiveType, MeshPart meshPart) {
        if (this.attributes == null) {
            throw new RuntimeException("Call begin() first");
        }
        endpart();
        this.part = meshPart;
        this.part.id = id;
        this.part.primitiveType = primitiveType;
        this.primitiveType = primitiveType;
        this.parts.add(this.part);
        setColor(null);
        setVertexTransform(null);
        setUVRange(null);
        return this.part;
    }

    public Mesh end(Mesh mesh) {
        endpart();
        if (this.attributes == null) {
            throw new GdxRuntimeException("Call begin() first");
        } else if (!this.attributes.equals(mesh.getVertexAttributes())) {
            throw new GdxRuntimeException("Mesh attributes don't match");
        } else if (mesh.getMaxVertices() * this.stride < this.vertices.size) {
            r1 = new StringBuilder();
            r1.append("Mesh can't hold enough vertices: ");
            r1.append(mesh.getMaxVertices());
            r1.append(" * ");
            r1.append(this.stride);
            r1.append(" < ");
            r1.append(this.vertices.size);
            throw new GdxRuntimeException(r1.toString());
        } else if (mesh.getMaxIndices() < this.indices.size) {
            r1 = new StringBuilder();
            r1.append("Mesh can't hold enough indices: ");
            r1.append(mesh.getMaxIndices());
            r1.append(" < ");
            r1.append(this.indices.size);
            throw new GdxRuntimeException(r1.toString());
        } else {
            mesh.setVertices(this.vertices.items, 0, this.vertices.size);
            mesh.setIndices(this.indices.items, 0, this.indices.size);
            Iterator i$ = this.parts.iterator();
            while (i$.hasNext()) {
                ((MeshPart) i$.next()).mesh = mesh;
            }
            this.parts.clear();
            this.attributes = null;
            this.vertices.clear();
            this.indices.clear();
            return mesh;
        }
    }

    public Mesh end() {
        return end(new Mesh(true, this.vertices.size / this.stride, this.indices.size, this.attributes));
    }

    public int getNumVertices() {
        return this.vertices.size / this.stride;
    }

    public int getNumIndices() {
        return this.indices.size;
    }

    public VertexAttributes getAttributes() {
        return this.attributes;
    }

    public MeshPart getMeshPart() {
        return this.part;
    }

    private Vector3 tmp(float x, float y, float z) {
        Vector3 result = ((Vector3) vectorPool.obtain()).set(x, y, z);
        vectorArray.add(result);
        return result;
    }

    private Vector3 tmp(Vector3 copyFrom) {
        return tmp(copyFrom.f120x, copyFrom.f121y, copyFrom.f122z);
    }

    private Matrix4 tmp() {
        Matrix4 result = ((Matrix4) matrices4Pool.obtain()).idt();
        matrices4Array.add(result);
        return result;
    }

    private Matrix4 tmp(Matrix4 copyFrom) {
        return tmp().set(copyFrom);
    }

    private void cleanup() {
        vectorPool.freeAll(vectorArray);
        vectorArray.clear();
        matrices4Pool.freeAll(matrices4Array);
        matrices4Array.clear();
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
        this.hasColor = this.color.equals(Color.WHITE) ^ 1;
    }

    public void setColor(Color color) {
        Color color2 = this.color;
        boolean z = color != null;
        this.hasColor = z;
        color2.set(!z ? Color.WHITE : color);
    }

    public void setUVRange(float u1, float v1, float u2, float v2) {
        boolean z;
        this.uOffset = u1;
        this.vOffset = v1;
        this.uScale = u2 - u1;
        this.vScale = v2 - v1;
        if (MathUtils.isZero(u1) && MathUtils.isZero(v1) && MathUtils.isEqual(u2, 1.0f)) {
            if (MathUtils.isEqual(v2, 1.0f)) {
                z = false;
                this.hasUVTransform = z;
            }
        }
        z = true;
        this.hasUVTransform = z;
    }

    public void setUVRange(TextureRegion region) {
        boolean z = region != null;
        this.hasUVTransform = z;
        if (z) {
            setUVRange(region.getU(), region.getV(), region.getU2(), region.getV2());
            return;
        }
        this.vOffset = 0.0f;
        this.uOffset = 0.0f;
        this.vScale = 1.0f;
        this.uScale = 1.0f;
    }

    public Matrix4 getVertexTransform(Matrix4 out) {
        return out.set(this.positionTransform);
    }

    public void setVertexTransform(Matrix4 transform) {
        boolean z = transform != null;
        this.vertexTransformationEnabled = z;
        if (z) {
            this.positionTransform.set(transform);
            this.normalTransform.set(transform).inv().transpose();
            return;
        }
        this.positionTransform.idt();
        this.normalTransform.idt();
    }

    public boolean isVertexTransformationEnabled() {
        return this.vertexTransformationEnabled;
    }

    public void setVertexTransformationEnabled(boolean enabled) {
        this.vertexTransformationEnabled = enabled;
    }

    public void ensureVertices(int numVertices) {
        this.vertices.ensureCapacity(this.stride * numVertices);
    }

    public void ensureIndices(int numIndices) {
        this.indices.ensureCapacity(numIndices);
    }

    public void ensureCapacity(int numVertices, int numIndices) {
        ensureVertices(numVertices);
        ensureIndices(numIndices);
    }

    public void ensureTriangleIndices(int numTriangles) {
        if (this.primitiveType == 1) {
            ensureIndices(numTriangles * 6);
            return;
        }
        if (this.primitiveType != 4) {
            if (this.primitiveType != 0) {
                throw new GdxRuntimeException("Incorrect primtive type");
            }
        }
        ensureIndices(numTriangles * 3);
    }

    public void ensureTriangles(int numVertices, int numTriangles) {
        ensureVertices(numVertices);
        ensureTriangleIndices(numTriangles);
    }

    public void ensureTriangles(int numTriangles) {
        ensureTriangles(numTriangles * 3, numTriangles);
    }

    public void ensureRectangleIndices(int numRectangles) {
        if (this.primitiveType == 0) {
            ensureIndices(numRectangles * 4);
        } else if (this.primitiveType == 1) {
            ensureIndices(numRectangles * 8);
        } else {
            ensureIndices(numRectangles * 6);
        }
    }

    public void ensureRectangles(int numVertices, int numRectangles) {
        ensureVertices(numVertices);
        ensureRectangleIndices(numRectangles);
    }

    public void ensureRectangles(int numRectangles) {
        ensureRectangles(numRectangles * 4, numRectangles);
    }

    public short lastIndex() {
        return this.lastIndex;
    }

    private static final void transformPosition(float[] values, int offset, int size, Matrix4 transform) {
        if (size > 2) {
            vTmp.set(values[offset], values[offset + 1], values[offset + 2]).mul(transform);
            values[offset] = vTmp.f120x;
            values[offset + 1] = vTmp.f121y;
            values[offset + 2] = vTmp.f122z;
        } else if (size > 1) {
            vTmp.set(values[offset], values[offset + 1], 0.0f).mul(transform);
            values[offset] = vTmp.f120x;
            values[offset + 1] = vTmp.f121y;
        } else {
            values[offset] = vTmp.set(values[offset], 0.0f, 0.0f).mul(transform).f120x;
        }
    }

    private static final void transformNormal(float[] values, int offset, int size, Matrix3 transform) {
        if (size > 2) {
            vTmp.set(values[offset], values[offset + 1], values[offset + 2]).mul(transform).nor();
            values[offset] = vTmp.f120x;
            values[offset + 1] = vTmp.f121y;
            values[offset + 2] = vTmp.f122z;
        } else if (size > 1) {
            vTmp.set(values[offset], values[offset + 1], 0.0f).mul(transform).nor();
            values[offset] = vTmp.f120x;
            values[offset + 1] = vTmp.f121y;
        } else {
            values[offset] = vTmp.set(values[offset], 0.0f, 0.0f).mul(transform).nor().f120x;
        }
    }

    private final void addVertex(float[] values, int offset) {
        int o = this.vertices.size;
        this.vertices.addAll(values, offset, this.stride);
        short s = this.vindex;
        this.vindex = (short) (s + 1);
        this.lastIndex = s;
        if (this.vertexTransformationEnabled) {
            transformPosition(this.vertices.items, this.posOffset + o, this.posSize, this.positionTransform);
            if (this.norOffset >= 0) {
                transformNormal(this.vertices.items, this.norOffset + o, 3, this.normalTransform);
            }
        }
        if (this.hasColor) {
            if (this.colOffset >= 0) {
                float[] fArr = this.vertices.items;
                int i = this.colOffset + o;
                fArr[i] = fArr[i] * this.color.f4r;
                fArr = this.vertices.items;
                i = (this.colOffset + o) + 1;
                fArr[i] = fArr[i] * this.color.f3g;
                fArr = this.vertices.items;
                i = (this.colOffset + o) + 2;
                fArr[i] = fArr[i] * this.color.f2b;
                if (this.colSize > 3) {
                    fArr = this.vertices.items;
                    i = (this.colOffset + o) + 3;
                    fArr[i] = fArr[i] * this.color.f1a;
                }
            } else if (this.cpOffset >= 0) {
                this.vertices.items[this.cpOffset + o] = this.tempC1.set(NumberUtils.floatToIntColor(this.vertices.items[this.cpOffset + o])).mul(this.color).toFloatBits();
            }
        }
        if (this.hasUVTransform && this.uvOffset >= 0) {
            this.vertices.items[this.uvOffset + o] = this.uOffset + (this.uScale * this.vertices.items[this.uvOffset + o]);
            this.vertices.items[(this.uvOffset + o) + 1] = this.vOffset + (this.vScale * this.vertices.items[(this.uvOffset + o) + 1]);
        }
    }

    public short vertex(Vector3 pos, Vector3 nor, Color col, Vector2 uv) {
        if (this.vindex >= ShortCompanionObject.MAX_VALUE) {
            throw new GdxRuntimeException("Too many vertices used");
        }
        this.vertex[this.posOffset] = pos.f120x;
        if (this.posSize > 1) {
            this.vertex[this.posOffset + 1] = pos.f121y;
        }
        if (this.posSize > 2) {
            this.vertex[this.posOffset + 2] = pos.f122z;
        }
        if (this.norOffset >= 0) {
            if (nor == null) {
                nor = this.tmpNormal.set(pos).nor();
            }
            this.vertex[this.norOffset] = nor.f120x;
            this.vertex[this.norOffset + 1] = nor.f121y;
            this.vertex[this.norOffset + 2] = nor.f122z;
        }
        if (this.colOffset >= 0) {
            if (col == null) {
                col = Color.WHITE;
            }
            this.vertex[this.colOffset] = col.f4r;
            this.vertex[this.colOffset + 1] = col.f3g;
            this.vertex[this.colOffset + 2] = col.f2b;
            if (this.colSize > 3) {
                this.vertex[this.colOffset + 3] = col.f1a;
            }
        } else if (this.cpOffset > 0) {
            if (col == null) {
                col = Color.WHITE;
            }
            this.vertex[this.cpOffset] = col.toFloatBits();
        }
        if (uv != null && this.uvOffset >= 0) {
            this.vertex[this.uvOffset] = uv.f16x;
            this.vertex[this.uvOffset + 1] = uv.f17y;
        }
        addVertex(this.vertex, 0);
        return this.lastIndex;
    }

    public short vertex(float... values) {
        int n = values.length - this.stride;
        int i = 0;
        while (i <= n) {
            addVertex(values, i);
            i += this.stride;
        }
        return this.lastIndex;
    }

    public short vertex(VertexInfo info) {
        Vector2 vector2 = null;
        Vector3 vector3 = info.hasPosition ? info.position : null;
        Vector3 vector32 = info.hasNormal ? info.normal : null;
        Color color = info.hasColor ? info.color : null;
        if (info.hasUV) {
            vector2 = info.uv;
        }
        return vertex(vector3, vector32, color, vector2);
    }

    public void index(short value) {
        this.indices.add(value);
    }

    public void index(short value1, short value2) {
        ensureIndices(2);
        this.indices.add(value1);
        this.indices.add(value2);
    }

    public void index(short value1, short value2, short value3) {
        ensureIndices(3);
        this.indices.add(value1);
        this.indices.add(value2);
        this.indices.add(value3);
    }

    public void index(short value1, short value2, short value3, short value4) {
        ensureIndices(4);
        this.indices.add(value1);
        this.indices.add(value2);
        this.indices.add(value3);
        this.indices.add(value4);
    }

    public void index(short value1, short value2, short value3, short value4, short value5, short value6) {
        ensureIndices(6);
        this.indices.add(value1);
        this.indices.add(value2);
        this.indices.add(value3);
        this.indices.add(value4);
        this.indices.add(value5);
        this.indices.add(value6);
    }

    public void index(short value1, short value2, short value3, short value4, short value5, short value6, short value7, short value8) {
        ensureIndices(8);
        this.indices.add(value1);
        this.indices.add(value2);
        this.indices.add(value3);
        this.indices.add(value4);
        this.indices.add(value5);
        this.indices.add(value6);
        this.indices.add(value7);
        this.indices.add(value8);
    }

    public void line(short index1, short index2) {
        if (this.primitiveType != 1) {
            throw new GdxRuntimeException("Incorrect primitive type");
        }
        index(index1, index2);
    }

    public void line(VertexInfo p1, VertexInfo p2) {
        ensureVertices(2);
        line(vertex(p1), vertex(p2));
    }

    public void line(Vector3 p1, Vector3 p2) {
        line(this.vertTmp1.set(p1, null, null, null), this.vertTmp2.set(p2, null, null, null));
    }

    public void line(float x1, float y1, float z1, float x2, float y2, float z2) {
        line(this.vertTmp1.set(null, null, null, null).setPos(x1, y1, z1), this.vertTmp2.set(null, null, null, null).setPos(x2, y2, z2));
    }

    public void line(Vector3 p1, Color c1, Vector3 p2, Color c2) {
        line(this.vertTmp1.set(p1, null, c1, null), this.vertTmp2.set(p2, null, c2, null));
    }

    public void triangle(short index1, short index2, short index3) {
        if (this.primitiveType != 4) {
            if (this.primitiveType != 0) {
                if (this.primitiveType == 1) {
                    index(index1, index2, index2, index3, index3, index1);
                    return;
                }
                throw new GdxRuntimeException("Incorrect primitive type");
            }
        }
        index(index1, index2, index3);
    }

    public void triangle(VertexInfo p1, VertexInfo p2, VertexInfo p3) {
        ensureVertices(3);
        triangle(vertex(p1), vertex(p2), vertex(p3));
    }

    public void triangle(Vector3 p1, Vector3 p2, Vector3 p3) {
        triangle(this.vertTmp1.set(p1, null, null, null), this.vertTmp2.set(p2, null, null, null), this.vertTmp3.set(p3, null, null, null));
    }

    public void triangle(Vector3 p1, Color c1, Vector3 p2, Color c2, Vector3 p3, Color c3) {
        triangle(this.vertTmp1.set(p1, null, c1, null), this.vertTmp2.set(p2, null, c2, null), this.vertTmp3.set(p3, null, c3, null));
    }

    public void rect(short corner00, short corner10, short corner11, short corner01) {
        if (this.primitiveType == 4) {
            index(corner00, corner10, corner11, corner11, corner01, corner00);
        } else if (this.primitiveType == 1) {
            index(corner00, corner10, corner10, corner11, corner11, corner01, corner01, corner00);
        } else if (this.primitiveType == 0) {
            index(corner00, corner10, corner11, corner01);
        } else {
            throw new GdxRuntimeException("Incorrect primitive type");
        }
    }

    public void rect(VertexInfo corner00, VertexInfo corner10, VertexInfo corner11, VertexInfo corner01) {
        ensureVertices(4);
        rect(vertex(corner00), vertex(corner10), vertex(corner11), vertex(corner01));
    }

    public void rect(Vector3 corner00, Vector3 corner10, Vector3 corner11, Vector3 corner01, Vector3 normal) {
        rect(this.vertTmp1.set(corner00, normal, null, null).setUV(0.0f, 1.0f), this.vertTmp2.set(corner10, normal, null, null).setUV(1.0f, 1.0f), this.vertTmp3.set(corner11, normal, null, null).setUV(1.0f, 0.0f), this.vertTmp4.set(corner01, normal, null, null).setUV(0.0f, 0.0f));
    }

    public void rect(float x00, float y00, float z00, float x10, float y10, float z10, float x11, float y11, float z11, float x01, float y01, float z01, float normalX, float normalY, float normalZ) {
        float f = normalX;
        float f2 = normalY;
        float f3 = normalZ;
        rect(this.vertTmp1.set(null, null, null, null).setPos(x00, y00, z00).setNor(f, f2, f3).setUV(0.0f, 1.0f), this.vertTmp2.set(null, null, null, null).setPos(x10, y10, z10).setNor(f, f2, f3).setUV(1.0f, 1.0f), this.vertTmp3.set(null, null, null, null).setPos(x11, y11, z11).setNor(f, f2, f3).setUV(1.0f, 0.0f), this.vertTmp4.set(null, null, null, null).setPos(x01, y01, z01).setNor(f, f2, f3).setUV(0.0f, 0.0f));
    }

    public void patch(VertexInfo corner00, VertexInfo corner10, VertexInfo corner11, VertexInfo corner01, int divisionsU, int divisionsV) {
        if (divisionsU >= 1) {
            if (divisionsV >= 1) {
                ensureRectangles((divisionsV + 1) * (divisionsU + 1), divisionsV * divisionsU);
                for (int u = 0; u <= divisionsU; u++) {
                    float alphaU = ((float) u) / ((float) divisionsU);
                    this.vertTmp5.set(corner00).lerp(corner10, alphaU);
                    this.vertTmp6.set(corner01).lerp(corner11, alphaU);
                    int v = 0;
                    while (v <= divisionsV) {
                        short idx = vertex(this.vertTmp7.set(this.vertTmp5).lerp(this.vertTmp6, ((float) v) / ((float) divisionsV)));
                        if (u > 0 && v > 0) {
                            rect((short) ((idx - divisionsV) - 2), (short) (idx - 1), idx, (short) ((idx - divisionsV) - 1));
                        }
                        v++;
                    }
                }
                return;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("divisionsU and divisionV must be > 0, u,v: ");
        stringBuilder.append(divisionsU);
        stringBuilder.append(", ");
        stringBuilder.append(divisionsV);
        throw new GdxRuntimeException(stringBuilder.toString());
    }

    public void patch(Vector3 corner00, Vector3 corner10, Vector3 corner11, Vector3 corner01, Vector3 normal, int divisionsU, int divisionsV) {
        Vector3 vector3 = normal;
        patch(this.vertTmp1.set(corner00, vector3, null, null).setUV(0.0f, 1.0f), this.vertTmp2.set(corner10, vector3, null, null).setUV(1.0f, 1.0f), this.vertTmp3.set(corner11, vector3, null, null).setUV(1.0f, 0.0f), this.vertTmp4.set(corner01, vector3, null, null).setUV(0.0f, 0.0f), divisionsU, divisionsV);
    }

    public void patch(float x00, float y00, float z00, float x10, float y10, float z10, float x11, float y11, float z11, float x01, float y01, float z01, float normalX, float normalY, float normalZ, int divisionsU, int divisionsV) {
        float f = normalX;
        float f2 = normalY;
        float f3 = normalZ;
        patch(this.vertTmp1.set(null).setPos(x00, y00, z00).setNor(f, f2, f3).setUV(0.0f, 1.0f), this.vertTmp2.set(null).setPos(x10, y10, z10).setNor(f, f2, f3).setUV(1.0f, 1.0f), this.vertTmp3.set(null).setPos(x11, y11, z11).setNor(f, f2, f3).setUV(1.0f, 0.0f), this.vertTmp4.set(null).setPos(x01, y01, z01).setNor(f, f2, f3).setUV(0.0f, 0.0f), divisionsU, divisionsV);
    }

    public void box(VertexInfo corner000, VertexInfo corner010, VertexInfo corner100, VertexInfo corner110, VertexInfo corner001, VertexInfo corner011, VertexInfo corner101, VertexInfo corner111) {
        ensureVertices(8);
        short i000 = vertex(corner000);
        short i100 = vertex(corner100);
        short i110 = vertex(corner110);
        short i010 = vertex(corner010);
        short i001 = vertex(corner001);
        short i101 = vertex(corner101);
        short i111 = vertex(corner111);
        short i011 = vertex(corner011);
        if (this.primitiveType == 1) {
            ensureIndices(24);
            rect(i000, i100, i110, i010);
            rect(i101, i001, i011, i111);
            short i1112 = i111;
            short i0112 = i011;
            short i1012 = i101;
            index(i000, i001, i010, i0112, i110, i1112, i100, i1012);
            short s = i1112;
            i111 = i0112;
            i011 = i1012;
            return;
        }
        i0112 = i011;
        i1112 = i111;
        i1012 = i101;
        short i0012 = i001;
        short i0102 = i010;
        if (r9.primitiveType == 0) {
            ensureRectangleIndices(2);
            rect(i000, i100, i110, i0102);
            i011 = i1012;
            rect(i011, i0012, i0112, i1112);
            return;
        }
        s = i1112;
        i111 = i0112;
        i011 = i1012;
        ensureRectangleIndices(6);
        rect(i000, i100, i110, i0102);
        rect(i011, i0012, i111, s);
        rect(i000, i0102, i111, i0012);
        rect(i011, s, i110, i100);
        rect(i011, i100, i000, i0012);
        rect(i110, s, i111, i0102);
    }

    public void box(Vector3 corner000, Vector3 corner010, Vector3 corner100, Vector3 corner110, Vector3 corner001, Vector3 corner011, Vector3 corner101, Vector3 corner111) {
        Vector3 vector3 = corner000;
        Vector3 vector32 = corner010;
        Vector3 vector33 = corner100;
        Vector3 vector34 = corner110;
        Vector3 vector35 = corner001;
        Vector3 vector36 = corner011;
        Vector3 vector37 = corner101;
        Vector3 vector38 = corner111;
        if (this.norOffset >= 0 || r9.uvOffset >= 0) {
            vector36 = vector38;
            ensureRectangles(6);
            vector38 = r9.tempV1.set(vector3).lerp(vector34, 0.5f).sub(r9.tempV2.set(vector35).lerp(vector36, 0.5f)).nor();
            rect(vector3, vector32, vector34, vector33, vector38);
            Vector3 vector39 = vector36;
            vector36 = corner011;
            vector39 = corner111;
            rect(vector36, vector35, corner101, vector39, vector38.scl(-1.0f));
            Vector3 vector310 = corner101;
            vector38 = r9.tempV1.set(vector3).lerp(vector310, 0.5f).sub(r9.tempV2.set(vector32).lerp(vector39, 0.5f)).nor();
            rect(vector35, vector3, vector33, vector310, vector38);
            Vector3 vector311 = vector36;
            rect(vector32, vector311, corner111, vector34, vector38.scl(-1.0f));
            Vector3 nor = r9.tempV1.set(vector3).lerp(vector36, 0.5f).sub(r9.tempV2.set(vector33).lerp(corner111, 0.5f)).nor();
            rect(vector35, vector311, vector32, vector3, nor);
            rect(vector33, vector34, corner111, corner101, nor.scl(-1.0f));
            return;
        }
        vector36 = vector38;
        box(r9.vertTmp1.set(vector3, null, null, null), r9.vertTmp2.set(vector32, null, null, null), r9.vertTmp3.set(vector33, null, null, null), r9.vertTmp4.set(vector34, null, null, null), r9.vertTmp5.set(vector35, null, null, null), r9.vertTmp6.set(vector36, null, null, null), r9.vertTmp7.set(vector37, null, null, null), r9.vertTmp8.set(vector38, null, null, null));
        vector36 = corner011;
    }

    public void box(Matrix4 transform) {
        box(tmp(-0.5f, -0.5f, -0.5f).mul(transform), tmp(-0.5f, 0.5f, -0.5f).mul(transform), tmp(0.5f, -0.5f, -0.5f).mul(transform), tmp(0.5f, 0.5f, -0.5f).mul(transform), tmp(-0.5f, -0.5f, 0.5f).mul(transform), tmp(-0.5f, 0.5f, 0.5f).mul(transform), tmp(0.5f, -0.5f, 0.5f).mul(transform), tmp(0.5f, 0.5f, 0.5f).mul(transform));
        cleanup();
    }

    public void box(float width, float height, float depth) {
        box(this.matTmp1.setToScaling(width, height, depth));
    }

    public void box(float x, float y, float z, float width, float height, float depth) {
        box(this.matTmp1.setToScaling(width, height, depth).trn(x, y, z));
    }

    public void circle(float radius, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ) {
        circle(radius, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, 0.0f, 360.0f);
    }

    public void circle(float radius, int divisions, Vector3 center, Vector3 normal) {
        circle(radius, divisions, center.f120x, center.f121y, center.f122z, normal.f120x, normal.f121y, normal.f122z);
    }

    public void circle(float radius, int divisions, Vector3 center, Vector3 normal, Vector3 tangent, Vector3 binormal) {
        Vector3 vector3 = center;
        Vector3 vector32 = normal;
        Vector3 vector33 = tangent;
        Vector3 vector34 = binormal;
        float f = vector3.f120x;
        float f2 = vector3.f121y;
        float f3 = vector3.f122z;
        float f4 = vector32.f120x;
        float f5 = vector32.f121y;
        float f6 = vector32.f122z;
        float f7 = vector33.f120x;
        float f8 = vector33.f121y;
        float f9 = vector33.f122z;
        float f10 = vector34.f120x;
        float f11 = vector34.f121y;
        float f12 = f11;
        float f13 = f10;
        circle(radius, divisions, f, f2, f3, f4, f5, f6, f7, f8, f9, f13, f12, vector34.f122z);
    }

    public void circle(float radius, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ) {
        circle(radius, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, tangentX, tangentY, tangentZ, binormalX, binormalY, binormalZ, 0.0f, 360.0f);
    }

    public void circle(float radius, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float angleFrom, float angleTo) {
        ellipse(radius * 2.0f, radius * 2.0f, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, angleFrom, angleTo);
    }

    public void circle(float radius, int divisions, Vector3 center, Vector3 normal, float angleFrom, float angleTo) {
        Vector3 vector3 = center;
        Vector3 vector32 = normal;
        circle(radius, divisions, vector3.f120x, vector3.f121y, vector3.f122z, vector32.f120x, vector32.f121y, vector32.f122z, angleFrom, angleTo);
    }

    public void circle(float radius, int divisions, Vector3 center, Vector3 normal, Vector3 tangent, Vector3 binormal, float angleFrom, float angleTo) {
        Vector3 vector3 = center;
        Vector3 vector32 = normal;
        Vector3 vector33 = tangent;
        Vector3 vector34 = binormal;
        float f = vector3.f120x;
        float f2 = vector3.f121y;
        float f3 = vector3.f122z;
        float f4 = vector32.f120x;
        float f5 = vector32.f121y;
        float f6 = vector32.f122z;
        float f7 = vector33.f120x;
        float f8 = vector33.f121y;
        float f9 = vector33.f122z;
        float f10 = vector34.f120x;
        float f11 = vector34.f121y;
        float f12 = f11;
        float f13 = f10;
        circle(radius, divisions, f, f2, f3, f4, f5, f6, f7, f8, f9, f13, f12, vector34.f122z, angleFrom, angleTo);
    }

    public void circle(float radius, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ, float angleFrom, float angleTo) {
        ellipse(radius * 2.0f, radius * 2.0f, 0.0f, 0.0f, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, tangentX, tangentY, tangentZ, binormalX, binormalY, binormalZ, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ) {
        ellipse(width, height, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, 0.0f, 360.0f);
    }

    public void ellipse(float width, float height, int divisions, Vector3 center, Vector3 normal) {
        ellipse(width, height, divisions, center.f120x, center.f121y, center.f122z, normal.f120x, normal.f121y, normal.f122z);
    }

    public void ellipse(float width, float height, int divisions, Vector3 center, Vector3 normal, Vector3 tangent, Vector3 binormal) {
        Vector3 vector3 = center;
        Vector3 vector32 = normal;
        Vector3 vector33 = tangent;
        Vector3 vector34 = binormal;
        float f = vector3.f120x;
        float f2 = vector3.f121y;
        float f3 = vector3.f122z;
        float f4 = vector32.f120x;
        float f5 = vector32.f121y;
        float f6 = vector32.f122z;
        float f7 = vector33.f120x;
        float f8 = vector33.f121y;
        float f9 = vector33.f122z;
        float f10 = vector34.f120x;
        float f11 = vector34.f121y;
        float f12 = f11;
        float f13 = f10;
        float f14 = f9;
        ellipse(width, height, divisions, f, f2, f3, f4, f5, f6, f7, f8, f14, f13, f12, vector34.f122z);
    }

    public void ellipse(float width, float height, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ) {
        ellipse(width, height, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, tangentX, tangentY, tangentZ, binormalX, binormalY, binormalZ, 0.0f, 360.0f);
    }

    public void ellipse(float width, float height, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float angleFrom, float angleTo) {
        ellipse(width, height, 0.0f, 0.0f, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, int divisions, Vector3 center, Vector3 normal, float angleFrom, float angleTo) {
        Vector3 vector3 = center;
        Vector3 vector32 = normal;
        ellipse(width, height, 0.0f, 0.0f, divisions, vector3.f120x, vector3.f121y, vector3.f122z, vector32.f120x, vector32.f121y, vector32.f122z, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, int divisions, Vector3 center, Vector3 normal, Vector3 tangent, Vector3 binormal, float angleFrom, float angleTo) {
        Vector3 vector3 = center;
        Vector3 vector32 = normal;
        Vector3 vector33 = tangent;
        Vector3 vector34 = binormal;
        float f = vector3.f120x;
        float f2 = vector3.f121y;
        float f3 = vector3.f122z;
        float f4 = vector32.f120x;
        float f5 = vector32.f121y;
        float f6 = vector32.f122z;
        float f7 = vector33.f120x;
        float f8 = vector33.f121y;
        float f9 = vector33.f122z;
        float f10 = vector34.f120x;
        float f11 = vector34.f121y;
        float f12 = f11;
        float f13 = f10;
        float f14 = f9;
        float f15 = f8;
        float f16 = f7;
        ellipse(width, height, 0.0f, 0.0f, divisions, f, f2, f3, f4, f5, f6, f16, f15, f14, f13, f12, vector34.f122z, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ, float angleFrom, float angleTo) {
        ellipse(width, height, 0.0f, 0.0f, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, tangentX, tangentY, tangentZ, binormalX, binormalY, binormalZ, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, Vector3 center, Vector3 normal) {
        Vector3 vector3 = center;
        Vector3 vector32 = normal;
        ellipse(width, height, innerWidth, innerHeight, divisions, vector3.f120x, vector3.f121y, vector3.f122z, vector32.f120x, vector32.f121y, vector32.f122z, 0.0f, 360.0f);
    }

    public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ) {
        ellipse(width, height, innerWidth, innerHeight, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, 0.0f, 360.0f);
    }

    public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float angleFrom, float angleTo) {
        float f = normalX;
        float f2 = normalY;
        float f3 = normalZ;
        this.tempV1.set(f, f2, f3).crs(0.0f, 0.0f, 1.0f);
        this.tempV2.set(f, f2, f3).crs(0.0f, 1.0f, 0.0f);
        if (this.tempV2.len2() > this.tempV1.len2()) {
            r15.tempV1.set(r15.tempV2);
        }
        r15.tempV2.set(r15.tempV1.nor()).crs(f, f2, f3).nor();
        float f4 = r15.tempV1.f120x;
        float f5 = r15.tempV1.f121y;
        float f6 = r15.tempV1.f122z;
        float f7 = r15.tempV2.f120x;
        float f8 = r15.tempV2.f121y;
        float f9 = f8;
        float f10 = f7;
        float f11 = f6;
        float f12 = f5;
        float f13 = f4;
        ellipse(width, height, innerWidth, innerHeight, divisions, centerX, centerY, centerZ, f, f2, f3, f13, f12, f11, f10, f9, r15.tempV2.f122z, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ, float angleFrom, float angleTo) {
        float step;
        float ao;
        Vector3 sxEx;
        Vector3 syEx;
        Vector3 sxIn;
        Vector3 syIn;
        VertexInfo currIn;
        Vector3 syIn2;
        VertexInfo currEx;
        Vector3 sxIn2;
        short center;
        float us;
        float vs;
        int i;
        float angle;
        short i2;
        short i3;
        short i4;
        short center2;
        int i5;
        short s;
        Vector3 vector3;
        Vector3 vector32;
        Vector3 vector33;
        short s2;
        float x;
        float y;
        float angle2;
        short i32;
        short i42;
        Vector3 sxEx2;
        Vector3 syEx2;
        MeshBuilder meshBuilder = this;
        int i6 = divisions;
        float f = centerX;
        short s3 = centerY;
        short s4 = centerZ;
        float f2 = normalX;
        float f3 = normalY;
        float f4 = normalZ;
        float f5 = tangentX;
        float f6 = tangentY;
        float f7 = tangentZ;
        float f8 = binormalX;
        f2 = binormalY;
        f3 = binormalZ;
        if (innerWidth > 0.0f) {
            if (innerHeight > 0.0f) {
                if (innerWidth == width && innerHeight == height) {
                    ensureVertices(i6 + 1);
                    ensureIndices(i6 + 1);
                    if (meshBuilder.primitiveType != 1) {
                        throw new GdxRuntimeException("Incorrect primitive type : expect GL_LINES because innerWidth == width && innerHeight == height");
                    }
                    step = ((angleTo - angleFrom) * 0.017453292f) / ((float) i6);
                    ao = angleFrom * 0.017453292f;
                    sxEx = meshBuilder.tempV1.set(f5, f6, f7).scl(width * 1056964608);
                    syEx = meshBuilder.tempV2.set(f8, f2, f3).scl(height * 1056964608);
                    sxIn = meshBuilder.tempV3.set(f5, f6, f7).scl(innerWidth * 0.5f);
                    syIn = meshBuilder.tempV4.set(f8, f2, f3).scl(innerHeight * 1056964608);
                    currIn = meshBuilder.vertTmp3.set(null, null, null, null);
                    currIn.hasNormal = true;
                    currIn.hasPosition = true;
                    currIn.hasUV = true;
                    currIn.uv.set(0.5f, 0.5f);
                    f4 = centerX;
                    currIn.position.set(f4, s3, s4);
                    syIn2 = syIn;
                    f6 = normalX;
                    f7 = normalY;
                    f8 = normalZ;
                    currIn.normal.set(f6, f7, f8);
                    currEx = meshBuilder.vertTmp4.set(null, null, null, null);
                    currEx.hasNormal = true;
                    currEx.hasPosition = true;
                    currEx.hasUV = true;
                    sxIn2 = sxIn;
                    currEx.uv.set(0.5f, 0.5f);
                    currEx.position.set(f4, s3, s4);
                    currEx.normal.set(f6, f7, f8);
                    center = vertex(currEx);
                    us = (innerWidth / width) * 0.5f;
                    vs = (innerHeight / height) * 0.5f;
                    i = 0;
                    angle = 0.0f;
                    i2 = (short) 0;
                    i3 = (short) 0;
                    i4 = (short) 0;
                    while (true) {
                        center2 = center;
                        i5 = i;
                        if (i5 <= divisions) {
                            s = i3;
                            vector3 = sxEx;
                            vector32 = syIn2;
                            vector33 = sxIn2;
                            s2 = center2;
                            i3 = s3;
                            i4 = s4;
                            return;
                        }
                        f8 = ao + (((float) i5) * step);
                        x = MathUtils.cos(f8);
                        y = MathUtils.sin(f8);
                        angle2 = f8;
                        i32 = i3;
                        i42 = i4;
                        sxEx2 = sxEx;
                        vector3 = sxEx2;
                        currEx.position.set(f4, s3, s4).add((sxEx2.f120x * x) + (syEx.f120x * y), (sxEx2.f121y * x) + (syEx.f121y * y), (sxEx2.f122z * x) + (syEx.f122z * y));
                        currEx.uv.set((x * 0.5f) + 0.5f, (y * 0.5f) + 0.5f);
                        s3 = vertex(currEx);
                        if (innerWidth > 0.0f) {
                            syEx2 = syEx;
                            vector32 = syIn2;
                            vector33 = sxIn2;
                            s = i32;
                            syEx = i42;
                            i3 = centerY;
                            i4 = centerZ;
                        } else if (innerHeight <= 0.0f) {
                            if (innerWidth == width) {
                            }
                            i3 = centerY;
                            i4 = centerZ;
                            vector33 = sxIn2;
                            vector32 = syIn2;
                            syEx2 = syEx;
                            currIn.position.set(f4, i3, i4).add((vector33.f120x * x) + (vector32.f120x * y), (vector33.f121y * x) + (vector32.f121y * y), (vector33.f122z * x) + (vector32.f122z * y));
                            currIn.uv.set((us * x) + 0.5f, (vs * y) + 1056964608);
                            s2 = s3;
                            s3 = vertex(currIn);
                            if (i5 != 0) {
                                syEx = i42;
                            } else {
                                rect(s3, s2, i42, i32);
                            }
                            syEx = s2;
                            s = s3;
                            i2 = s2;
                            s2 = center2;
                            i = i5 + 1;
                            center = s2;
                            syIn2 = vector32;
                            s3 = i3;
                            s4 = i4;
                            sxIn2 = vector33;
                            angle = angle2;
                            sxEx = vector3;
                            vector33 = normalZ;
                            i3 = s;
                            i4 = syEx;
                            syEx = syEx2;
                        } else {
                            syEx2 = syEx;
                            vector32 = syIn2;
                            vector33 = sxIn2;
                            s = i32;
                            syEx = i42;
                            i3 = centerY;
                            i4 = centerZ;
                        }
                        if (i5 != 0) {
                            s2 = center2;
                        } else {
                            s2 = center2;
                            triangle(s3, i2, s2);
                        }
                        i2 = s3;
                        i = i5 + 1;
                        center = s2;
                        syIn2 = vector32;
                        s3 = i3;
                        s4 = i4;
                        sxIn2 = vector33;
                        angle = angle2;
                        sxEx = vector3;
                        vector33 = normalZ;
                        i3 = s;
                        i4 = syEx;
                        syEx = syEx2;
                    }
                } else {
                    ensureRectangles((i6 + 1) * 2, i6 + 1);
                    step = ((angleTo - angleFrom) * 0.017453292f) / ((float) i6);
                    ao = angleFrom * 0.017453292f;
                    sxEx = meshBuilder.tempV1.set(f5, f6, f7).scl(width * 1056964608);
                    syEx = meshBuilder.tempV2.set(f8, f2, f3).scl(height * 1056964608);
                    sxIn = meshBuilder.tempV3.set(f5, f6, f7).scl(innerWidth * 0.5f);
                    syIn = meshBuilder.tempV4.set(f8, f2, f3).scl(innerHeight * 1056964608);
                    currIn = meshBuilder.vertTmp3.set(null, null, null, null);
                    currIn.hasNormal = true;
                    currIn.hasPosition = true;
                    currIn.hasUV = true;
                    currIn.uv.set(0.5f, 0.5f);
                    f4 = centerX;
                    currIn.position.set(f4, s3, s4);
                    syIn2 = syIn;
                    f6 = normalX;
                    f7 = normalY;
                    f8 = normalZ;
                    currIn.normal.set(f6, f7, f8);
                    currEx = meshBuilder.vertTmp4.set(null, null, null, null);
                    currEx.hasNormal = true;
                    currEx.hasPosition = true;
                    currEx.hasUV = true;
                    sxIn2 = sxIn;
                    currEx.uv.set(0.5f, 0.5f);
                    currEx.position.set(f4, s3, s4);
                    currEx.normal.set(f6, f7, f8);
                    center = vertex(currEx);
                    us = (innerWidth / width) * 0.5f;
                    vs = (innerHeight / height) * 0.5f;
                    i = 0;
                    angle = 0.0f;
                    i2 = (short) 0;
                    i3 = (short) 0;
                    i4 = (short) 0;
                    while (true) {
                        center2 = center;
                        i5 = i;
                        if (i5 <= divisions) {
                            f8 = ao + (((float) i5) * step);
                            x = MathUtils.cos(f8);
                            y = MathUtils.sin(f8);
                            angle2 = f8;
                            i32 = i3;
                            i42 = i4;
                            sxEx2 = sxEx;
                            vector3 = sxEx2;
                            currEx.position.set(f4, s3, s4).add((sxEx2.f120x * x) + (syEx.f120x * y), (sxEx2.f121y * x) + (syEx.f121y * y), (sxEx2.f122z * x) + (syEx.f122z * y));
                            currEx.uv.set((x * 0.5f) + 0.5f, (y * 0.5f) + 0.5f);
                            s3 = vertex(currEx);
                            if (innerWidth > 0.0f) {
                                syEx2 = syEx;
                                vector32 = syIn2;
                                vector33 = sxIn2;
                                s = i32;
                                syEx = i42;
                                i3 = centerY;
                                i4 = centerZ;
                            } else if (innerHeight <= 0.0f) {
                                syEx2 = syEx;
                                vector32 = syIn2;
                                vector33 = sxIn2;
                                s = i32;
                                syEx = i42;
                                i3 = centerY;
                                i4 = centerZ;
                            } else if (innerWidth == width || innerHeight != height) {
                                i3 = centerY;
                                i4 = centerZ;
                                vector33 = sxIn2;
                                vector32 = syIn2;
                                syEx2 = syEx;
                                currIn.position.set(f4, i3, i4).add((vector33.f120x * x) + (vector32.f120x * y), (vector33.f121y * x) + (vector32.f121y * y), (vector33.f122z * x) + (vector32.f122z * y));
                                currIn.uv.set((us * x) + 0.5f, (vs * y) + 1056964608);
                                s2 = s3;
                                s3 = vertex(currIn);
                                if (i5 != 0) {
                                    rect(s3, s2, i42, i32);
                                } else {
                                    syEx = i42;
                                }
                                syEx = s2;
                                s = s3;
                                i2 = s2;
                                s2 = center2;
                                i = i5 + 1;
                                center = s2;
                                syIn2 = vector32;
                                s3 = i3;
                                s4 = i4;
                                sxIn2 = vector33;
                                angle = angle2;
                                sxEx = vector3;
                                vector33 = normalZ;
                                i3 = s;
                                i4 = syEx;
                                syEx = syEx2;
                            } else {
                                if (i5 != 0) {
                                    line(s3, i2);
                                }
                                syEx2 = syEx;
                                i2 = s3;
                                vector32 = syIn2;
                                vector33 = sxIn2;
                                s2 = center2;
                                s = i32;
                                syEx = i42;
                                i3 = centerY;
                                i4 = centerZ;
                                i = i5 + 1;
                                center = s2;
                                syIn2 = vector32;
                                s3 = i3;
                                s4 = i4;
                                sxIn2 = vector33;
                                angle = angle2;
                                sxEx = vector3;
                                vector33 = normalZ;
                                i3 = s;
                                i4 = syEx;
                                syEx = syEx2;
                            }
                            if (i5 != 0) {
                                s2 = center2;
                                triangle(s3, i2, s2);
                            } else {
                                s2 = center2;
                            }
                            i2 = s3;
                            i = i5 + 1;
                            center = s2;
                            syIn2 = vector32;
                            s3 = i3;
                            s4 = i4;
                            sxIn2 = vector33;
                            angle = angle2;
                            sxEx = vector3;
                            vector33 = normalZ;
                            i3 = s;
                            i4 = syEx;
                            syEx = syEx2;
                        } else {
                            s = i3;
                            vector3 = sxEx;
                            vector32 = syIn2;
                            vector33 = sxIn2;
                            s2 = center2;
                            i3 = s3;
                            i4 = s4;
                            return;
                        }
                    }
                }
            }
        }
        ensureTriangles(i6 + 2, i6);
        step = ((angleTo - angleFrom) * 0.017453292f) / ((float) i6);
        ao = angleFrom * 0.017453292f;
        sxEx = meshBuilder.tempV1.set(f5, f6, f7).scl(width * 1056964608);
        syEx = meshBuilder.tempV2.set(f8, f2, f3).scl(height * 1056964608);
        sxIn = meshBuilder.tempV3.set(f5, f6, f7).scl(innerWidth * 0.5f);
        syIn = meshBuilder.tempV4.set(f8, f2, f3).scl(innerHeight * 1056964608);
        currIn = meshBuilder.vertTmp3.set(null, null, null, null);
        currIn.hasNormal = true;
        currIn.hasPosition = true;
        currIn.hasUV = true;
        currIn.uv.set(0.5f, 0.5f);
        f4 = centerX;
        currIn.position.set(f4, s3, s4);
        syIn2 = syIn;
        f6 = normalX;
        f7 = normalY;
        f8 = normalZ;
        currIn.normal.set(f6, f7, f8);
        currEx = meshBuilder.vertTmp4.set(null, null, null, null);
        currEx.hasNormal = true;
        currEx.hasPosition = true;
        currEx.hasUV = true;
        sxIn2 = sxIn;
        currEx.uv.set(0.5f, 0.5f);
        currEx.position.set(f4, s3, s4);
        currEx.normal.set(f6, f7, f8);
        center = vertex(currEx);
        us = (innerWidth / width) * 0.5f;
        vs = (innerHeight / height) * 0.5f;
        i = 0;
        angle = 0.0f;
        i2 = (short) 0;
        i3 = (short) 0;
        i4 = (short) 0;
        while (true) {
            center2 = center;
            i5 = i;
            if (i5 <= divisions) {
                f8 = ao + (((float) i5) * step);
                x = MathUtils.cos(f8);
                y = MathUtils.sin(f8);
                angle2 = f8;
                i32 = i3;
                i42 = i4;
                sxEx2 = sxEx;
                vector3 = sxEx2;
                currEx.position.set(f4, s3, s4).add((sxEx2.f120x * x) + (syEx.f120x * y), (sxEx2.f121y * x) + (syEx.f121y * y), (sxEx2.f122z * x) + (syEx.f122z * y));
                currEx.uv.set((x * 0.5f) + 0.5f, (y * 0.5f) + 0.5f);
                s3 = vertex(currEx);
                if (innerWidth > 0.0f) {
                    syEx2 = syEx;
                    vector32 = syIn2;
                    vector33 = sxIn2;
                    s = i32;
                    syEx = i42;
                    i3 = centerY;
                    i4 = centerZ;
                } else if (innerHeight <= 0.0f) {
                    syEx2 = syEx;
                    vector32 = syIn2;
                    vector33 = sxIn2;
                    s = i32;
                    syEx = i42;
                    i3 = centerY;
                    i4 = centerZ;
                } else {
                    if (innerWidth == width) {
                    }
                    i3 = centerY;
                    i4 = centerZ;
                    vector33 = sxIn2;
                    vector32 = syIn2;
                    syEx2 = syEx;
                    currIn.position.set(f4, i3, i4).add((vector33.f120x * x) + (vector32.f120x * y), (vector33.f121y * x) + (vector32.f121y * y), (vector33.f122z * x) + (vector32.f122z * y));
                    currIn.uv.set((us * x) + 0.5f, (vs * y) + 1056964608);
                    s2 = s3;
                    s3 = vertex(currIn);
                    if (i5 != 0) {
                        rect(s3, s2, i42, i32);
                    } else {
                        syEx = i42;
                    }
                    syEx = s2;
                    s = s3;
                    i2 = s2;
                    s2 = center2;
                    i = i5 + 1;
                    center = s2;
                    syIn2 = vector32;
                    s3 = i3;
                    s4 = i4;
                    sxIn2 = vector33;
                    angle = angle2;
                    sxEx = vector3;
                    vector33 = normalZ;
                    i3 = s;
                    i4 = syEx;
                    syEx = syEx2;
                }
                if (i5 != 0) {
                    s2 = center2;
                    triangle(s3, i2, s2);
                } else {
                    s2 = center2;
                }
                i2 = s3;
                i = i5 + 1;
                center = s2;
                syIn2 = vector32;
                s3 = i3;
                s4 = i4;
                sxIn2 = vector33;
                angle = angle2;
                sxEx = vector3;
                vector33 = normalZ;
                i3 = s;
                i4 = syEx;
                syEx = syEx2;
            } else {
                s = i3;
                vector3 = sxEx;
                vector32 = syIn2;
                vector33 = sxIn2;
                s2 = center2;
                i3 = s3;
                i4 = s4;
                return;
            }
        }
    }

    public void cylinder(float width, float height, float depth, int divisions) {
        cylinder(width, height, depth, divisions, 0.0f, 360.0f);
    }

    public void cylinder(float width, float height, float depth, int divisions, float angleFrom, float angleTo) {
        cylinder(width, height, depth, divisions, angleFrom, angleTo, true);
    }

    public void cylinder(float width, float height, float depth, int divisions, float angleFrom, float angleTo, boolean close) {
        int i = divisions;
        float hw = width * 0.5f;
        float hh = height * 0.5f;
        float hd = depth * 0.5f;
        float ao = angleFrom * 0.017453292f;
        float step = ((angleTo - angleFrom) * 0.017453292f) / ((float) i);
        float us = 1.0f / ((float) i);
        VertexInfo curr1 = this.vertTmp3.set(null, null, null, null);
        curr1.hasNormal = true;
        curr1.hasPosition = true;
        curr1.hasUV = true;
        VertexInfo curr2 = this.vertTmp4.set(null, null, null, null);
        curr2.hasNormal = true;
        curr2.hasPosition = true;
        curr2.hasUV = true;
        ensureRectangles((i + 1) * 2, i);
        int i2 = 0;
        float u = 0.0f;
        float angle = 0.0f;
        short i3 = (short) 0;
        short i4 = (short) 0;
        while (true) {
            int i5 = i2;
            if (i5 > i) {
                break;
            }
            float angle2 = ao + (((float) i5) * step);
            float u2 = 1.0f - (((float) i5) * us);
            curr1.position.set(MathUtils.cos(angle2) * hw, 0.0f, MathUtils.sin(angle2) * hd);
            curr1.normal.set(curr1.position).nor();
            curr1.position.f121y = -hh;
            curr1.uv.set(u2, 1.0f);
            curr2.position.set(curr1.position);
            curr2.normal.set(curr1.normal);
            curr2.position.f121y = hh;
            curr2.uv.set(u2, 0.0f);
            short i22 = vertex(curr1);
            short i1 = vertex(curr2);
            if (i5 != 0) {
                rect(i3, i1, i22, i4);
            }
            i4 = i22;
            i3 = i1;
            i2 = i5 + 1;
            angle = angle2;
            u = u2;
        }
        if (close) {
            float f = width;
            angle2 = depth;
            float hh2 = hh;
            ellipse(f, angle2, 0.0f, 0.0f, i, 0.0f, hh, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, angleFrom, angleTo);
            float hh3 = hh2;
            ellipse(f, angle2, 0.0f, 0.0f, divisions, 0.0f, -hh3, 0.0f, 0.0f, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 180.0f - angleTo, 180.0f - angleFrom);
            return;
        }
        short s = i3;
        VertexInfo vertexInfo = curr2;
        VertexInfo vertexInfo2 = curr1;
        float f2 = hh;
    }

    public void cone(float width, float height, float depth, int divisions) {
        cone(width, height, depth, divisions, 0.0f, 360.0f);
    }

    public void cone(float width, float height, float depth, int divisions, float angleFrom, float angleTo) {
        int i = divisions;
        ensureTriangles(i + 2, i);
        float hw = width * 0.5f;
        float hh = height * 0.5f;
        float hd = depth * 0.5f;
        float ao = angleFrom * 0.017453292f;
        float step = ((angleTo - angleFrom) * 0.017453292f) / ((float) i);
        float us = 1.0f / ((float) i);
        VertexInfo curr1 = this.vertTmp3.set(null, null, null, null);
        curr1.hasNormal = true;
        curr1.hasPosition = true;
        curr1.hasUV = true;
        VertexInfo curr2 = this.vertTmp4.set(null, null, null, null).setPos(0.0f, hh, 0.0f).setNor(0.0f, 1.0f, 0.0f).setUV(0.5f, 0.0f);
        short base = vertex(curr2);
        int i2 = 0;
        short i22 = (short) 0;
        float u = 0.0f;
        float angle = 0.0f;
        while (true) {
            int i3 = i2;
            if (i3 <= i) {
                float angle2 = ao + (((float) i3) * step);
                float u2 = 1.0f - (((float) i3) * us);
                curr1.position.set(MathUtils.cos(angle2) * hw, 0.0f, MathUtils.sin(angle2) * hd);
                curr1.normal.set(curr1.position).nor();
                curr1.position.f121y = -hh;
                curr1.uv.set(u2, 1.0f);
                short i1 = vertex(curr1);
                if (i3 != 0) {
                    triangle(base, i1, i22);
                }
                i22 = i1;
                i2 = i3 + 1;
                angle = angle2;
                u = u2;
            } else {
                ellipse(width, depth, 0.0f, 0.0f, i, 0.0f, -hh, 0.0f, 0.0f, -1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 180.0f - angleTo, 180.0f - angleFrom);
                return;
            }
        }
    }

    public void sphere(float width, float height, float depth, int divisionsU, int divisionsV) {
        sphere(width, height, depth, divisionsU, divisionsV, 0.0f, 360.0f, 0.0f, 180.0f);
    }

    public void sphere(Matrix4 transform, float width, float height, float depth, int divisionsU, int divisionsV) {
        sphere(transform, width, height, depth, divisionsU, divisionsV, 0.0f, 360.0f, 0.0f, 180.0f);
    }

    public void sphere(float width, float height, float depth, int divisionsU, int divisionsV, float angleUFrom, float angleUTo, float angleVFrom, float angleVTo) {
        sphere(this.matTmp1.idt(), width, height, depth, divisionsU, divisionsV, angleUFrom, angleUTo, angleVFrom, angleVTo);
    }

    public void sphere(Matrix4 transform, float width, float height, float depth, int divisionsU, int divisionsV, float angleUFrom, float angleUTo, float angleVFrom, float angleVTo) {
        float hh;
        float auo;
        float us;
        float hw;
        float hd;
        VertexInfo curr1;
        int i = divisionsU;
        int i2 = divisionsV;
        float hw2 = width * 0.5f;
        float hh2 = height * 0.5f;
        float hd2 = 0.5f * depth;
        float auo2 = angleUFrom * 0.017453292f;
        float stepU = ((angleUTo - angleUFrom) * 0.017453292f) / ((float) i);
        float avo = angleVFrom * 0.017453292f;
        float stepV = ((angleVTo - angleVFrom) * 0.017453292f) / ((float) i2);
        float us2 = 1.0f / ((float) i);
        float vs = 1.0f / ((float) i2);
        float u = 0.0f;
        VertexInfo curr12 = this.vertTmp3.set(null, null, null, null);
        curr12.hasNormal = true;
        curr12.hasPosition = true;
        curr12.hasUV = true;
        int s = i + 3;
        tmpIndices.clear();
        tmpIndices.ensureCapacity(i * 2);
        tmpIndices.size = s;
        int tempOffset = 0;
        ensureRectangles((i2 + 1) * (i + 1), i2 * i);
        int iv = 0;
        while (iv <= i2) {
            float angleV = (((float) iv) * stepV) + avo;
            float v = ((float) iv) * vs;
            float t = MathUtils.sin(angleV);
            float vs2 = vs;
            vs = MathUtils.cos(angleV) * hh2;
            float angleV2 = angleV;
            float u2 = u;
            int tempOffset2 = tempOffset;
            int iu = 0;
            while (true) {
                hh = hh2;
                int iu2 = iu;
                if (iu2 > i) {
                    break;
                }
                float v2;
                float h;
                float angleU = (((float) iu2) * stepU) + auo2;
                auo = auo2;
                auo2 = 1.0f - (((float) iu2) * us2);
                us = us2;
                hw = hw2;
                hd = hd2;
                curr12.position.set((MathUtils.cos(angleU) * hw2) * t, vs, (MathUtils.sin(angleU) * hd2) * t).mul(transform);
                curr12.normal.set(curr12.position).nor();
                curr12.uv.set(auo2, v);
                tmpIndices.set(tempOffset2, vertex(curr12));
                int o = tempOffset2 + s;
                if (iv <= 0 || iu2 <= 0) {
                    v2 = v;
                    h = vs;
                    curr1 = curr12;
                } else {
                    v2 = v;
                    h = vs;
                    curr1 = curr12;
                    rect(tmpIndices.get(tempOffset2), tmpIndices.get((o - 1) % s), tmpIndices.get((o - (i + 2)) % s), tmpIndices.get((o - (i + 1)) % s));
                }
                tempOffset2 = (tempOffset2 + 1) % tmpIndices.size;
                iu = iu2 + 1;
                u2 = auo2;
                float f = angleU;
                hh2 = hh;
                auo2 = auo;
                us2 = us;
                hw2 = hw;
                hd2 = hd;
                v = v2;
                vs = h;
                curr12 = curr1;
                i = divisionsU;
            }
            curr1 = curr12;
            hd = hd2;
            hw = hw2;
            us = us2;
            auo = auo2;
            hw2 = transform;
            iv++;
            tempOffset = tempOffset2;
            u = u2;
            vs = vs2;
            hh2 = hh;
            hw2 = hw;
            i = divisionsU;
            i2 = divisionsV;
        }
        curr1 = curr12;
        hd = hd2;
        hw = hw2;
        hh = hh2;
        us = us2;
        auo = auo2;
        hw2 = transform;
    }

    public void capsule(float radius, float height, int divisions) {
        MeshBuilder meshBuilder = this;
        if (height < radius * 2.0f) {
            throw new GdxRuntimeException("Height must be at least twice the radius");
        }
        float d = radius * 2.0f;
        float f = d;
        cylinder(d, height - d, f, divisions, 0.0f, 360.0f, false);
        float f2 = d;
        int i = divisions;
        int i2 = divisions;
        sphere(meshBuilder.matTmp1.setToTranslation(0.0f, (height - d) * 0.5f, 0.0f), d, f, f2, i, i2, 0.0f, 360.0f, 0.0f, 90.0f);
        sphere(meshBuilder.matTmp1.setToTranslation(0.0f, (height - d) * -0.5f, 0.0f), d, f, f2, i, i2, 0.0f, 360.0f, 90.0f, 180.0f);
    }

    public void arrow(float x1, float y1, float z1, float x2, float y2, float z2, float capLength, float stemThickness, int divisions) {
        float f = x1;
        float f2 = y1;
        float f3 = z1;
        int i = divisions;
        Vector3 begin = tmp(x1, y1, z1);
        Vector3 end = tmp(x2, y2, z2);
        float length = begin.dst(end);
        float coneHeight = length * capLength;
        float coneDiameter = ((float) (((double) coneHeight) * Math.sqrt(0.3333333432674408d))) * 2.0f;
        float stemLength = length - coneHeight;
        float stemDiameter = coneDiameter * stemThickness;
        Vector3 up = tmp(end).sub(begin).nor();
        Vector3 forward = tmp(up).crs(Vector3.f119Z);
        if (forward.isZero()) {
            forward.set(Vector3.f117X);
        }
        forward.crs(up).nor();
        Vector3 left = tmp(up).crs(forward).nor();
        Vector3 direction = tmp(end).sub(begin).nor();
        Matrix4 userTransform = getVertexTransform(tmp());
        Matrix4 transform = tmp();
        float[] val = transform.val;
        float coneDiameter2 = coneDiameter;
        val[0] = left.f120x;
        float coneHeight2 = coneHeight;
        val[4] = up.f120x;
        val[8] = forward.f120x;
        val[1] = left.f121y;
        val[5] = up.f121y;
        val[9] = forward.f121y;
        val[2] = left.f122z;
        val[6] = up.f122z;
        val[10] = forward.f122z;
        Matrix4 temp = tmp();
        transform.setTranslation(tmp(direction).scl(stemLength / 2.0f).add(f, f2, f3));
        setVertexTransform(temp.set(transform).mul(userTransform));
        cylinder(stemDiameter, stemLength, stemDiameter, i);
        transform.setTranslation(tmp(direction).scl(stemLength).add(f, f2, f3));
        setVertexTransform(temp.set(transform).mul(userTransform));
        coneHeight = coneDiameter2;
        cone(coneHeight, coneHeight2, coneHeight, i);
        setVertexTransform(userTransform);
        cleanup();
    }

    public void addMesh(Mesh mesh) {
        addMesh(mesh, 0, mesh.getNumIndices());
    }

    public void addMesh(MeshPart meshpart) {
        if (meshpart.primitiveType != this.primitiveType) {
            throw new GdxRuntimeException("Primitive type doesn't match");
        }
        addMesh(meshpart.mesh, meshpart.indexOffset, meshpart.numVertices);
    }

    public void addMesh(Mesh mesh, int indexOffset, int numIndices) {
        if (!this.attributes.equals(mesh.getVertexAttributes())) {
            throw new GdxRuntimeException("Vertex attributes do not match");
        } else if (numIndices > 0) {
            int numFloats = mesh.getNumVertices() * this.stride;
            tmpVertices.clear();
            tmpVertices.ensureCapacity(numFloats);
            tmpVertices.size = numFloats;
            mesh.getVertices(tmpVertices.items);
            tmpIndices.clear();
            tmpIndices.ensureCapacity(numIndices);
            tmpIndices.size = numIndices;
            mesh.getIndices(indexOffset, numIndices, tmpIndices.items, 0);
            addMesh(tmpVertices.items, tmpIndices.items, 0, numIndices);
        }
    }

    private void addMesh(float[] vertices, short[] indices, int indexOffset, int numIndices) {
        if (indicesMap == null) {
            indicesMap = new IntIntMap(numIndices);
        } else {
            indicesMap.clear();
            indicesMap.ensureCapacity(numIndices);
        }
        ensureIndices(numIndices);
        ensureVertices(vertices.length < numIndices ? vertices.length : numIndices);
        for (int i = 0; i < numIndices; i++) {
            int sidx = indices[i];
            int didx = indicesMap.get(sidx, -1);
            if (didx < 0) {
                addVertex(vertices, this.stride * sidx);
                IntIntMap intIntMap = indicesMap;
                short s = this.lastIndex;
                didx = s;
                intIntMap.put(sidx, s);
            }
            index((short) didx);
        }
    }
}
