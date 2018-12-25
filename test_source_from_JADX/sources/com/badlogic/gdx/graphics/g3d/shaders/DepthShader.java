package com.badlogic.gdx.graphics.g3d.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class DepthShader extends DefaultShader {
    private static String defaultFragmentShader = null;
    private static String defaultVertexShader = null;
    private static final Attributes tmpAttributes = new Attributes();
    private final FloatAttribute alphaTestAttribute;
    public final int numBones;
    public final int weights;

    public static class Config extends DefaultShader$Config {
        public float defaultAlphaTest;
        public boolean depthBufferOnly;

        public Config() {
            this.depthBufferOnly = false;
            this.defaultAlphaTest = 0.5f;
            this.defaultCullFace = GL20.GL_FRONT;
        }

        public Config(String vertexShader, String fragmentShader) {
            super(vertexShader, fragmentShader);
            this.depthBufferOnly = false;
            this.defaultAlphaTest = 0.5f;
        }
    }

    public static final String getDefaultVertexShader() {
        if (defaultVertexShader == null) {
            defaultVertexShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/depth.vertex.glsl").readString();
        }
        return defaultVertexShader;
    }

    public static final String getDefaultFragmentShader() {
        if (defaultFragmentShader == null) {
            defaultFragmentShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/depth.fragment.glsl").readString();
        }
        return defaultFragmentShader;
    }

    public static String createPrefix(Renderable renderable, Config config) {
        String prefix = DefaultShader.createPrefix(renderable, config);
        if (config.depthBufferOnly) {
            return prefix;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append("#define PackedDepthFlag\n");
        return stringBuilder.toString();
    }

    public DepthShader(Renderable renderable) {
        this(renderable, new Config());
    }

    public DepthShader(Renderable renderable, Config config) {
        this(renderable, config, createPrefix(renderable, config));
    }

    public DepthShader(Renderable renderable, Config config, String prefix) {
        this(renderable, config, prefix, config.vertexShader != null ? config.vertexShader : getDefaultVertexShader(), config.fragmentShader != null ? config.fragmentShader : getDefaultFragmentShader());
    }

    public DepthShader(Renderable renderable, Config config, String prefix, String vertexShader, String fragmentShader) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append(vertexShader);
        String stringBuilder2 = stringBuilder.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(prefix);
        stringBuilder3.append(fragmentShader);
        this(renderable, config, new ShaderProgram(stringBuilder2, stringBuilder3.toString()));
    }

    public DepthShader(Renderable renderable, Config config, ShaderProgram shaderProgram) {
        super(renderable, config, shaderProgram);
        Attributes attributes = combineAttributes(renderable);
        this.numBones = renderable.bones == null ? 0 : config.numBones;
        int w = 0;
        int n = renderable.mesh.getVertexAttributes().size();
        for (int i = 0; i < n; i++) {
            VertexAttribute attr = renderable.mesh.getVertexAttributes().get(i);
            if (attr.usage == 64) {
                w |= 1 << attr.unit;
            }
        }
        this.weights = w;
        this.alphaTestAttribute = new FloatAttribute(FloatAttribute.AlphaTest, config.defaultAlphaTest);
    }

    public void begin(Camera camera, RenderContext context) {
        super.begin(camera, context);
    }

    public void end() {
        super.end();
    }

    public boolean canRender(Renderable renderable) {
        Attributes attributes = combineAttributes(renderable);
        boolean z = true;
        if (attributes.has(BlendingAttribute.Type)) {
            if ((this.attributesMask & BlendingAttribute.Type) != BlendingAttribute.Type) {
                return false;
            }
            if (attributes.has(TextureAttribute.Diffuse) != ((this.attributesMask & TextureAttribute.Diffuse) == TextureAttribute.Diffuse)) {
                return false;
            }
        }
        boolean skinned = (renderable.mesh.getVertexAttributes().getMask() & 64) == 64;
        if (skinned != (this.numBones > 0)) {
            return false;
        }
        if (!skinned) {
            return true;
        }
        int n = renderable.mesh.getVertexAttributes().size();
        int w = 0;
        for (int i = 0; i < n; i++) {
            VertexAttribute attr = renderable.mesh.getVertexAttributes().get(i);
            if (attr.usage == 64) {
                w |= 1 << attr.unit;
            }
        }
        if (w != this.weights) {
            z = false;
        }
        return z;
    }

    public void render(Renderable renderable, Attributes combinedAttributes) {
        if (combinedAttributes.has(BlendingAttribute.Type)) {
            Attribute blending = (BlendingAttribute) combinedAttributes.get(BlendingAttribute.Type);
            combinedAttributes.remove(BlendingAttribute.Type);
            boolean hasAlphaTest = combinedAttributes.has(FloatAttribute.AlphaTest);
            if (!hasAlphaTest) {
                combinedAttributes.set(this.alphaTestAttribute);
            }
            if (blending.opacity >= ((FloatAttribute) combinedAttributes.get(FloatAttribute.AlphaTest)).value) {
                super.render(renderable, combinedAttributes);
            }
            if (!hasAlphaTest) {
                combinedAttributes.remove(FloatAttribute.AlphaTest);
            }
            combinedAttributes.set(blending);
            return;
        }
        super.render(renderable, combinedAttributes);
    }

    private static final Attributes combineAttributes(Renderable renderable) {
        tmpAttributes.clear();
        if (renderable.environment != null) {
            tmpAttributes.set(renderable.environment);
        }
        if (renderable.material != null) {
            tmpAttributes.set(renderable.material);
        }
        return tmpAttributes;
    }
}
