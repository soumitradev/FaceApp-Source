package com.badlogic.gdx.graphics.g3d.particles.batches;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray.FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader.AlignMode;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData.SaveData;
import com.badlogic.gdx.graphics.g3d.particles.renderers.BillboardControllerRenderData;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import java.util.Iterator;

public class BillboardParticleBatch extends BufferedParticleBatch<BillboardControllerRenderData> {
    private static final VertexAttributes CPU_ATTRIBUTES = new VertexAttributes(new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(16, 2, "a_texCoord0"), new VertexAttribute(2, 4, ShaderProgram.COLOR_ATTRIBUTE));
    private static final int CPU_COLOR_OFFSET = ((short) (CPU_ATTRIBUTES.findByUsage(2).offset / 4));
    private static final int CPU_POSITION_OFFSET = ((short) (CPU_ATTRIBUTES.findByUsage(1).offset / 4));
    private static final int CPU_UV_OFFSET = ((short) (CPU_ATTRIBUTES.findByUsage(16).offset / 4));
    private static final int CPU_VERTEX_SIZE = (CPU_ATTRIBUTES.vertexSize / 4);
    private static final VertexAttributes GPU_ATTRIBUTES = new VertexAttributes(new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(16, 2, "a_texCoord0"), new VertexAttribute(2, 4, ShaderProgram.COLOR_ATTRIBUTE), new VertexAttribute(512, 4, "a_sizeAndRotation"));
    private static final int GPU_COLOR_OFFSET = ((short) (GPU_ATTRIBUTES.findByUsage(2).offset / 4));
    private static final int GPU_POSITION_OFFSET = ((short) (GPU_ATTRIBUTES.findByUsage(1).offset / 4));
    private static final int GPU_SIZE_ROTATION_OFFSET = ((short) (GPU_ATTRIBUTES.findByUsage(512).offset / 4));
    private static final int GPU_UV_OFFSET = ((short) (GPU_ATTRIBUTES.findByUsage(16).offset / 4));
    private static final int GPU_VERTEX_SIZE = (GPU_ATTRIBUTES.vertexSize / 4);
    private static final int MAX_PARTICLES_PER_MESH = 8191;
    private static final int MAX_VERTICES_PER_MESH = 32764;
    protected static final Matrix3 TMP_M3 = new Matrix3();
    protected static final Vector3 TMP_V1 = new Vector3();
    protected static final Vector3 TMP_V2 = new Vector3();
    protected static final Vector3 TMP_V3 = new Vector3();
    protected static final Vector3 TMP_V4 = new Vector3();
    protected static final Vector3 TMP_V5 = new Vector3();
    protected static final Vector3 TMP_V6 = new Vector3();
    protected static final int directionUsage = 1024;
    protected static final int sizeAndRotationUsage = 512;
    protected BlendingAttribute blendingAttribute;
    private VertexAttributes currentAttributes;
    private int currentVertexSize;
    protected DepthTestAttribute depthTestAttribute;
    private short[] indices;
    protected AlignMode mode;
    private RenderablePool renderablePool;
    private Array<Renderable> renderables;
    Shader shader;
    protected Texture texture;
    protected boolean useGPU;
    private float[] vertices;

    public static class Config {
        AlignMode mode;
        boolean useGPU;

        public Config(boolean useGPU, AlignMode mode) {
            this.useGPU = useGPU;
            this.mode = mode;
        }
    }

    private class RenderablePool extends Pool<Renderable> {
        public Renderable newObject() {
            return BillboardParticleBatch.this.allocRenderable();
        }
    }

    public BillboardParticleBatch(AlignMode mode, boolean useGPU, int capacity, BlendingAttribute blendingAttribute, DepthTestAttribute depthTestAttribute) {
        super(BillboardControllerRenderData.class);
        this.currentVertexSize = 0;
        this.useGPU = false;
        this.mode = AlignMode.Screen;
        this.renderables = new Array();
        this.renderablePool = new RenderablePool();
        this.blendingAttribute = blendingAttribute;
        this.depthTestAttribute = depthTestAttribute;
        if (this.blendingAttribute == null) {
            this.blendingAttribute = new BlendingAttribute(1, GL20.GL_ONE_MINUS_SRC_ALPHA, 1.0f);
        }
        if (this.depthTestAttribute == null) {
            this.depthTestAttribute = new DepthTestAttribute(515, false);
        }
        allocIndices();
        initRenderData();
        ensureCapacity(capacity);
        setUseGpu(useGPU);
        setAlignMode(mode);
    }

    public BillboardParticleBatch(AlignMode mode, boolean useGPU, int capacity) {
        this(mode, useGPU, capacity, null, null);
    }

    public BillboardParticleBatch() {
        this(AlignMode.Screen, false, 100);
    }

    public BillboardParticleBatch(int capacity) {
        this(AlignMode.Screen, false, capacity);
    }

    public void allocParticlesData(int capacity) {
        this.vertices = new float[((this.currentVertexSize * 4) * capacity)];
        allocRenderables(capacity);
    }

    protected Renderable allocRenderable() {
        Renderable renderable = new Renderable();
        renderable.primitiveType = 4;
        renderable.meshPartOffset = 0;
        renderable.material = new Material(this.blendingAttribute, this.depthTestAttribute, TextureAttribute.createDiffuse(this.texture));
        renderable.mesh = new Mesh(false, (int) MAX_VERTICES_PER_MESH, 49146, this.currentAttributes);
        renderable.mesh.setIndices(this.indices);
        renderable.shader = this.shader;
        return renderable;
    }

    private void allocIndices() {
        this.indices = new short[49146];
        int i = 0;
        int vertex = 0;
        while (i < 49146) {
            this.indices[i] = (short) vertex;
            this.indices[i + 1] = (short) (vertex + 1);
            this.indices[i + 2] = (short) (vertex + 2);
            this.indices[i + 3] = (short) (vertex + 2);
            this.indices[i + 4] = (short) (vertex + 3);
            this.indices[i + 5] = (short) vertex;
            i += 6;
            vertex += 4;
        }
    }

    private void allocRenderables(int capacity) {
        int meshCount = MathUtils.ceil((float) (capacity / MAX_PARTICLES_PER_MESH));
        int free = this.renderablePool.getFree();
        if (free < meshCount) {
            int left = meshCount - free;
            for (int i = 0; i < left; i++) {
                this.renderablePool.free(this.renderablePool.newObject());
            }
        }
    }

    private Shader getShader(Renderable renderable) {
        Shader shader = this.useGPU ? new ParticleShader(renderable, new com.badlogic.gdx.graphics.g3d.particles.ParticleShader.Config(this.mode)) : new DefaultShader(renderable);
        shader.init();
        return shader;
    }

    private void allocShader() {
        Renderable newRenderable = allocRenderable();
        Shader shader = getShader(newRenderable);
        newRenderable.shader = shader;
        this.shader = shader;
        this.renderablePool.free(newRenderable);
    }

    private void clearRenderablesPool() {
        this.renderablePool.freeAll(this.renderables);
        int free = this.renderablePool.getFree();
        for (int i = 0; i < free; i++) {
            ((Renderable) this.renderablePool.obtain()).mesh.dispose();
        }
        this.renderables.clear();
    }

    public void setVertexData() {
        if (this.useGPU) {
            this.currentAttributes = GPU_ATTRIBUTES;
            this.currentVertexSize = GPU_VERTEX_SIZE;
            return;
        }
        this.currentAttributes = CPU_ATTRIBUTES;
        this.currentVertexSize = CPU_VERTEX_SIZE;
    }

    private void initRenderData() {
        setVertexData();
        clearRenderablesPool();
        allocShader();
        resetCapacity();
    }

    public void setAlignMode(AlignMode mode) {
        if (mode != this.mode) {
            this.mode = mode;
            if (this.useGPU) {
                initRenderData();
                allocRenderables(this.bufferedParticlesCount);
            }
        }
    }

    public AlignMode getAlignMode() {
        return this.mode;
    }

    public void setUseGpu(boolean useGPU) {
        if (this.useGPU != useGPU) {
            this.useGPU = useGPU;
            initRenderData();
            allocRenderables(this.bufferedParticlesCount);
        }
    }

    public boolean isUseGPU() {
        return this.useGPU;
    }

    public void setTexture(Texture texture) {
        this.renderablePool.freeAll(this.renderables);
        this.renderables.clear();
        int free = this.renderablePool.getFree();
        for (int i = 0; i < free; i++) {
            ((TextureAttribute) ((Renderable) this.renderablePool.obtain()).material.get(TextureAttribute.Diffuse)).textureDescription.texture = texture;
        }
        this.texture = texture;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void begin() {
        super.begin();
        this.renderablePool.freeAll(this.renderables);
        this.renderables.clear();
    }

    private static void putVertex(float[] vertices, int offset, float x, float y, float z, float u, float v, float scaleX, float scaleY, float cosRotation, float sinRotation, float r, float g, float b, float a) {
        vertices[GPU_POSITION_OFFSET + offset] = x;
        vertices[(GPU_POSITION_OFFSET + offset) + 1] = y;
        vertices[(GPU_POSITION_OFFSET + offset) + 2] = z;
        vertices[GPU_UV_OFFSET + offset] = u;
        vertices[(GPU_UV_OFFSET + offset) + 1] = v;
        vertices[GPU_SIZE_ROTATION_OFFSET + offset] = scaleX;
        vertices[(GPU_SIZE_ROTATION_OFFSET + offset) + 1] = scaleY;
        vertices[(GPU_SIZE_ROTATION_OFFSET + offset) + 2] = cosRotation;
        vertices[(GPU_SIZE_ROTATION_OFFSET + offset) + 3] = sinRotation;
        vertices[GPU_COLOR_OFFSET + offset] = r;
        vertices[(GPU_COLOR_OFFSET + offset) + 1] = g;
        vertices[(GPU_COLOR_OFFSET + offset) + 2] = b;
        vertices[(GPU_COLOR_OFFSET + offset) + 3] = a;
    }

    private static void putVertex(float[] vertices, int offset, Vector3 p, float u, float v, float r, float g, float b, float a) {
        vertices[CPU_POSITION_OFFSET + offset] = p.f120x;
        vertices[(CPU_POSITION_OFFSET + offset) + 1] = p.f121y;
        vertices[(CPU_POSITION_OFFSET + offset) + 2] = p.f122z;
        vertices[CPU_UV_OFFSET + offset] = u;
        vertices[(CPU_UV_OFFSET + offset) + 1] = v;
        vertices[CPU_COLOR_OFFSET + offset] = r;
        vertices[(CPU_COLOR_OFFSET + offset) + 1] = g;
        vertices[(CPU_COLOR_OFFSET + offset) + 2] = b;
        vertices[(CPU_COLOR_OFFSET + offset) + 3] = a;
    }

    private void fillVerticesGPU(int[] particlesOffset) {
        int tp = 0;
        Iterator i$ = this.renderData.iterator();
        while (i$.hasNext()) {
            BillboardControllerRenderData data = (BillboardControllerRenderData) i$.next();
            FloatChannel scaleChannel = data.scaleChannel;
            FloatChannel regionChannel = data.regionChannel;
            FloatChannel positionChannel = data.positionChannel;
            FloatChannel colorChannel = data.colorChannel;
            FloatChannel rotationChannel = data.rotationChannel;
            int p = 0;
            int c = data.controller.particles.size;
            while (p < c) {
                int baseOffset = (particlesOffset[tp] * r0.currentVertexSize) * 4;
                float scale = scaleChannel.data[scaleChannel.strideSize * p];
                int regionOffset = p * regionChannel.strideSize;
                int positionOffset = p * positionChannel.strideSize;
                int colorOffset = p * colorChannel.strideSize;
                int rotationOffset = p * rotationChannel.strideSize;
                float px = positionChannel.data[positionOffset + 0];
                float py = positionChannel.data[positionOffset + 1];
                float pz = positionChannel.data[positionOffset + 2];
                float u = regionChannel.data[regionOffset + 0];
                float v = regionChannel.data[regionOffset + 1];
                float u2 = regionChannel.data[regionOffset + 2];
                float v2 = regionChannel.data[regionOffset + 3];
                float sx = regionChannel.data[regionOffset + 4] * scale;
                float sy = regionChannel.data[regionOffset + 5] * scale;
                float r = colorChannel.data[colorOffset + 0];
                float g = colorChannel.data[colorOffset + 1];
                float b = colorChannel.data[colorOffset + 2];
                float a = colorChannel.data[colorOffset + 3];
                float cosRotation = rotationChannel.data[rotationOffset + 0];
                float sinRotation = rotationChannel.data[rotationOffset + 1];
                Iterator i$2 = i$;
                BillboardControllerRenderData data2 = data;
                FloatChannel scaleChannel2 = scaleChannel;
                float sy2 = sy;
                float sx2 = sx;
                putVertex(r0.vertices, baseOffset, px, py, pz, u, v2, -sx, -sy, cosRotation, sinRotation, r, g, b, a);
                i$ = r0.currentVertexSize + baseOffset;
                float f = px;
                float f2 = py;
                float f3 = pz;
                float f4 = u2;
                float f5 = sx2;
                float f6 = cosRotation;
                float f7 = sinRotation;
                float f8 = r;
                float f9 = g;
                float f10 = b;
                float f11 = a;
                putVertex(r0.vertices, i$, f, f2, f3, f4, v2, f5, -sy2, f6, f7, f8, f9, f10, f11);
                i$ += r0.currentVertexSize;
                float f12 = v;
                float f13 = sy2;
                putVertex(r0.vertices, i$, f, f2, f3, f4, f12, f5, f13, f6, f7, f8, f9, f10, f11);
                putVertex(r0.vertices, i$ + r0.currentVertexSize, f, f2, f3, u, f12, -sx2, f13, f6, f7, f8, f9, f10, f11);
                int i = r0.currentVertexSize;
                p++;
                tp++;
                i$ = i$2;
                data = data2;
                scaleChannel = scaleChannel2;
            }
        }
    }

    private void fillVerticesToViewPointCPU(int[] particlesOffset) {
        int tp = 0;
        Iterator i$ = this.renderData.iterator();
        while (i$.hasNext()) {
            Iterator i$2;
            int tp2;
            BillboardControllerRenderData data = (BillboardControllerRenderData) i$.next();
            FloatChannel scaleChannel = data.scaleChannel;
            FloatChannel regionChannel = data.regionChannel;
            FloatChannel positionChannel = data.positionChannel;
            FloatChannel colorChannel = data.colorChannel;
            FloatChannel rotationChannel = data.rotationChannel;
            int p = 0;
            int c = data.controller.particles.size;
            while (p < c) {
                int baseOffset = (particlesOffset[tp] * r0.currentVertexSize) * 4;
                float scale = scaleChannel.data[scaleChannel.strideSize * p];
                int regionOffset = p * regionChannel.strideSize;
                int positionOffset = p * positionChannel.strideSize;
                int colorOffset = p * colorChannel.strideSize;
                int rotationOffset = p * rotationChannel.strideSize;
                float px = positionChannel.data[positionOffset + 0];
                float py = positionChannel.data[positionOffset + 1];
                float pz = positionChannel.data[positionOffset + 2];
                i$2 = i$;
                float u = regionChannel.data[regionOffset + 0];
                BillboardControllerRenderData data2 = data;
                float v = regionChannel.data[regionOffset + 1];
                FloatChannel scaleChannel2 = scaleChannel;
                float u2 = regionChannel.data[regionOffset + 2];
                FloatChannel positionChannel2 = positionChannel;
                float v2 = regionChannel.data[regionOffset + 3];
                int c2 = c;
                float sx = regionChannel.data[regionOffset + 4] * scale;
                float sy = regionChannel.data[regionOffset + 5] * scale;
                FloatChannel regionChannel2 = regionChannel;
                float r = colorChannel.data[colorOffset + 0];
                tp2 = tp;
                float g = colorChannel.data[colorOffset + 1];
                int p2 = p;
                float b = colorChannel.data[colorOffset + 2];
                float v3 = v;
                v = colorChannel.data[colorOffset + 3];
                FloatChannel colorChannel2 = colorChannel;
                float cosRotation = rotationChannel.data[rotationOffset + 0];
                float u22 = u2;
                u2 = rotationChannel.data[rotationOffset + 1];
                FloatChannel rotationChannel2 = rotationChannel;
                float a = v;
                Vector3 look = TMP_V3.set(r0.camera.position).sub(px, py, pz).nor();
                float b2 = b;
                Vector3 right = TMP_V1.set(r0.camera.up).crs(look).nor();
                Vector3 up = TMP_V2.set(look).crs(right);
                right.scl(sx);
                up.scl(sy);
                float f;
                float f2;
                float f3;
                float f4;
                float f5;
                float f6;
                if (cosRotation != 1.0f) {
                    TMP_M3.setToRotation(look, cosRotation, u2);
                    u2 = pz;
                    cosRotation = py;
                    right = px;
                    putVertex(r0.vertices, baseOffset, TMP_V6.set((-TMP_V1.f120x) - TMP_V2.f120x, (-TMP_V1.f121y) - TMP_V2.f121y, (-TMP_V1.f122z) - TMP_V2.f122z).mul(TMP_M3).add(px, py, pz), u, v2, r, g, b2, a);
                    look = r0.currentVertexSize + baseOffset;
                    f = u22;
                    f2 = r;
                    f3 = g;
                    f4 = b2;
                    f5 = a;
                    putVertex(r0.vertices, look, TMP_V6.set(TMP_V1.f120x - TMP_V2.f120x, TMP_V1.f121y - TMP_V2.f121y, TMP_V1.f122z - TMP_V2.f122z).mul(TMP_M3).add(right, cosRotation, u2), f, v2, f2, f3, f4, f5);
                    look += r0.currentVertexSize;
                    f6 = v3;
                    putVertex(r0.vertices, look, TMP_V6.set(TMP_V1.f120x + TMP_V2.f120x, TMP_V1.f121y + TMP_V2.f121y, TMP_V1.f122z + TMP_V2.f122z).mul(TMP_M3).add(right, cosRotation, u2), f, f6, f2, f3, f4, f5);
                    look += r0.currentVertexSize;
                    putVertex(r0.vertices, look, TMP_V6.set((-TMP_V1.f120x) + TMP_V2.f120x, (-TMP_V1.f121y) + TMP_V2.f121y, (-TMP_V1.f122z) + TMP_V2.f122z).mul(TMP_M3).add(right, cosRotation, u2), u, f6, f2, f3, f4, f5);
                } else {
                    float f7 = u2;
                    float f8 = cosRotation;
                    Vector3 vector3 = right;
                    Vector3 vector32 = up;
                    float f9 = sx;
                    u2 = pz;
                    cosRotation = py;
                    float px2 = px;
                    putVertex(r0.vertices, baseOffset, TMP_V6.set(((-TMP_V1.f120x) - TMP_V2.f120x) + px2, ((-TMP_V1.f121y) - TMP_V2.f121y) + cosRotation, ((-TMP_V1.f122z) - TMP_V2.f122z) + u2), u, v2, r, g, b2, a);
                    int baseOffset2 = r0.currentVertexSize + baseOffset;
                    f = u22;
                    f2 = r;
                    f3 = g;
                    f4 = b2;
                    f5 = a;
                    putVertex(r0.vertices, baseOffset2, TMP_V6.set((TMP_V1.f120x - TMP_V2.f120x) + px2, (TMP_V1.f121y - TMP_V2.f121y) + cosRotation, (TMP_V1.f122z - TMP_V2.f122z) + u2), f, v2, f2, f3, f4, f5);
                    baseOffset2 += r0.currentVertexSize;
                    f6 = v3;
                    putVertex(r0.vertices, baseOffset2, TMP_V6.set((TMP_V1.f120x + TMP_V2.f120x) + px2, (TMP_V1.f121y + TMP_V2.f121y) + cosRotation, (TMP_V1.f122z + TMP_V2.f122z) + u2), f, f6, f2, f3, f4, f5);
                    baseOffset2 += r0.currentVertexSize;
                    putVertex(r0.vertices, baseOffset2, TMP_V6.set(((-TMP_V1.f120x) + TMP_V2.f120x) + px2, ((-TMP_V1.f121y) + TMP_V2.f121y) + cosRotation, ((-TMP_V1.f122z) + TMP_V2.f122z) + u2), u, f6, f2, f3, f4, f5);
                }
                p = p2 + 1;
                tp = tp2 + 1;
                i$ = i$2;
                data = data2;
                scaleChannel = scaleChannel2;
                positionChannel = positionChannel2;
                c = c2;
                regionChannel = regionChannel2;
                colorChannel = colorChannel2;
                rotationChannel = rotationChannel2;
            }
            tp2 = tp;
            i$2 = i$;
        }
    }

    private void fillVerticesToScreenCPU(int[] particlesOffset) {
        Vector3 right;
        Vector3 up;
        Vector3 look = TMP_V3.set(this.camera.direction).scl(-1.0f);
        Vector3 right2 = TMP_V4.set(this.camera.up).crs(look).nor();
        Vector3 up2 = this.camera.up;
        int tp = 0;
        Iterator i$ = this.renderData.iterator();
        while (i$.hasNext()) {
            Iterator i$2;
            int tp2;
            BillboardControllerRenderData data = (BillboardControllerRenderData) i$.next();
            FloatChannel scaleChannel = data.scaleChannel;
            FloatChannel regionChannel = data.regionChannel;
            FloatChannel positionChannel = data.positionChannel;
            FloatChannel colorChannel = data.colorChannel;
            FloatChannel rotationChannel = data.rotationChannel;
            int p = 0;
            int c = data.controller.particles.size;
            while (p < c) {
                Vector3 look2;
                i$2 = i$;
                int baseOffset = (particlesOffset[tp] * r0.currentVertexSize) * 4;
                BillboardControllerRenderData data2 = data;
                float scale = scaleChannel.data[scaleChannel.strideSize * p];
                int regionOffset = regionChannel.strideSize * p;
                FloatChannel scaleChannel2 = scaleChannel;
                int positionOffset = positionChannel.strideSize * p;
                int c2 = c;
                c = colorChannel.strideSize * p;
                int rotationOffset = rotationChannel.strideSize * p;
                tp2 = tp;
                float px = positionChannel.data[positionOffset + 0];
                int p2 = p;
                float py = positionChannel.data[positionOffset + 1];
                int baseOffset2 = baseOffset;
                float pz = positionChannel.data[positionOffset + 2];
                float u = regionChannel.data[regionOffset + 0];
                FloatChannel positionChannel2 = positionChannel;
                float v = regionChannel.data[regionOffset + 1];
                float u2 = regionChannel.data[regionOffset + 2];
                float v2 = regionChannel.data[regionOffset + 3];
                float sx = regionChannel.data[regionOffset + 4] * scale;
                float u3 = u;
                u = regionChannel.data[regionOffset + 5] * scale;
                scale = colorChannel.data[c + 0];
                FloatChannel regionChannel2 = regionChannel;
                float g = colorChannel.data[c + 1];
                float b = colorChannel.data[c + 2];
                float a = colorChannel.data[c + 3];
                FloatChannel colorChannel2 = colorChannel;
                float cosRotation = rotationChannel.data[rotationOffset + 0];
                float sinRotation = rotationChannel.data[rotationOffset + 1];
                FloatChannel rotationChannel2 = rotationChannel;
                TMP_V1.set(right2).scl(sx);
                TMP_V2.set(up2).scl(u);
                float f;
                float f2;
                float f3;
                float f4;
                float f5;
                float f6;
                if (cosRotation != 1.0f) {
                    TMP_M3.setToRotation(look, cosRotation, sinRotation);
                    look2 = look;
                    right = right2;
                    up = up2;
                    putVertex(r0.vertices, baseOffset2, TMP_V6.set((-TMP_V1.f120x) - TMP_V2.f120x, (-TMP_V1.f121y) - TMP_V2.f121y, (-TMP_V1.f122z) - TMP_V2.f122z).mul(TMP_M3).add(px, py, pz), u3, v2, scale, g, b, a);
                    look = baseOffset2 + r0.currentVertexSize;
                    f = u2;
                    f2 = scale;
                    f3 = g;
                    f4 = b;
                    f5 = a;
                    putVertex(r0.vertices, look, TMP_V6.set(TMP_V1.f120x - TMP_V2.f120x, TMP_V1.f121y - TMP_V2.f121y, TMP_V1.f122z - TMP_V2.f122z).mul(TMP_M3).add(px, py, pz), f, v2, f2, f3, f4, f5);
                    look += r0.currentVertexSize;
                    f6 = v;
                    putVertex(r0.vertices, look, TMP_V6.set(TMP_V1.f120x + TMP_V2.f120x, TMP_V1.f121y + TMP_V2.f121y, TMP_V1.f122z + TMP_V2.f122z).mul(TMP_M3).add(px, py, pz), f, f6, f2, f3, f4, f5);
                    look += r0.currentVertexSize;
                    putVertex(r0.vertices, look, TMP_V6.set((-TMP_V1.f120x) + TMP_V2.f120x, (-TMP_V1.f121y) + TMP_V2.f121y, (-TMP_V1.f122z) + TMP_V2.f122z).mul(TMP_M3).add(px, py, pz), u3, f6, f2, f3, f4, f5);
                } else {
                    look2 = look;
                    right = right2;
                    up = up2;
                    float f7 = u;
                    float f8 = sx;
                    float f9 = cosRotation;
                    putVertex(r0.vertices, baseOffset2, TMP_V6.set(((-TMP_V1.f120x) - TMP_V2.f120x) + px, ((-TMP_V1.f121y) - TMP_V2.f121y) + py, ((-TMP_V1.f122z) - TMP_V2.f122z) + pz), u3, v2, scale, g, b, a);
                    look = baseOffset2 + r0.currentVertexSize;
                    f = u2;
                    f2 = scale;
                    f3 = g;
                    f4 = b;
                    f5 = a;
                    putVertex(r0.vertices, look, TMP_V6.set((TMP_V1.f120x - TMP_V2.f120x) + px, (TMP_V1.f121y - TMP_V2.f121y) + py, (TMP_V1.f122z - TMP_V2.f122z) + pz), f, v2, f2, f3, f4, f5);
                    look += r0.currentVertexSize;
                    f6 = v;
                    putVertex(r0.vertices, look, TMP_V6.set((TMP_V1.f120x + TMP_V2.f120x) + px, (TMP_V1.f121y + TMP_V2.f121y) + py, (TMP_V1.f122z + TMP_V2.f122z) + pz), f, f6, f2, f3, f4, f5);
                    look += r0.currentVertexSize;
                    putVertex(r0.vertices, look, TMP_V6.set(((-TMP_V1.f120x) + TMP_V2.f120x) + px, ((-TMP_V1.f121y) + TMP_V2.f121y) + py, ((-TMP_V1.f122z) + TMP_V2.f122z) + pz), u3, f6, f2, f3, f4, f5);
                }
                p = p2 + 1;
                tp = tp2 + 1;
                i$ = i$2;
                data = data2;
                scaleChannel = scaleChannel2;
                c = c2;
                positionChannel = positionChannel2;
                regionChannel = regionChannel2;
                colorChannel = colorChannel2;
                rotationChannel = rotationChannel2;
                look = look2;
                right2 = right;
                up2 = up;
            }
            right = right2;
            up = up2;
            tp2 = tp;
            i$2 = i$;
        }
        right = right2;
        up = up2;
    }

    protected void flush(int[] offsets) {
        if (this.useGPU) {
            fillVerticesGPU(offsets);
        } else if (this.mode == AlignMode.Screen) {
            fillVerticesToScreenCPU(offsets);
        } else if (this.mode == AlignMode.ViewPoint) {
            fillVerticesToViewPointCPU(offsets);
        }
        int vCount = this.bufferedParticlesCount * 4;
        int v = 0;
        while (v < vCount) {
            int addedVertexCount = Math.min(vCount - v, MAX_VERTICES_PER_MESH);
            Renderable renderable = (Renderable) this.renderablePool.obtain();
            renderable.meshPartSize = (addedVertexCount / 4) * 6;
            renderable.mesh.setVertices(this.vertices, this.currentVertexSize * v, this.currentVertexSize * addedVertexCount);
            this.renderables.add(renderable);
            v += addedVertexCount;
        }
    }

    public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
        Iterator i$ = this.renderables.iterator();
        while (i$.hasNext()) {
            renderables.add(((Renderable) pool.obtain()).set((Renderable) i$.next()));
        }
    }

    public void save(AssetManager manager, ResourceData resources) {
        SaveData data = resources.createSaveData("billboardBatch");
        data.save("cfg", new Config(this.useGPU, this.mode));
        data.saveAsset(manager.getAssetFileName(this.texture), Texture.class);
    }

    public void load(AssetManager manager, ResourceData resources) {
        SaveData data = resources.getSaveData("billboardBatch");
        if (data != null) {
            setTexture((Texture) manager.get(data.loadAsset()));
            Config cfg = (Config) data.load("cfg");
            setUseGpu(cfg.useGPU);
            setAlignMode(cfg.mode);
        }
    }
}
