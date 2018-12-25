package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Inputs;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Iterator;

public class ParticleShader extends BaseShader {
    static final Vector3 TMP_VECTOR3 = new Vector3();
    private static String defaultFragmentShader = null;
    private static String defaultVertexShader = null;
    protected static long implementedFlags = (BlendingAttribute.Type | TextureAttribute.Diffuse);
    private static final long optionalAttributes = (IntAttribute.CullFace | DepthTestAttribute.Type);
    protected final Config config;
    Material currentMaterial;
    private long materialMask;
    private Renderable renderable;
    private long vertexMask;

    public enum AlignMode {
        Screen,
        ViewPoint
    }

    public static class Config {
        public AlignMode align = AlignMode.Screen;
        public int defaultCullFace = -1;
        public int defaultDepthFunc = -1;
        public String fragmentShader = null;
        public boolean ignoreUnimplemented = true;
        public ParticleType type = ParticleType.Billboard;
        public String vertexShader = null;

        public Config(AlignMode align, ParticleType type) {
            this.align = align;
            this.type = type;
        }

        public Config(AlignMode align) {
            this.align = align;
        }

        public Config(ParticleType type) {
            this.type = type;
        }

        public Config(String vertexShader, String fragmentShader) {
            this.vertexShader = vertexShader;
            this.fragmentShader = fragmentShader;
        }
    }

    public static class Inputs {
        public static final Uniform cameraInvDirection = new Uniform("u_cameraInvDirection");
        public static final Uniform cameraRight = new Uniform("u_cameraRight");
        public static final Uniform regionSize = new Uniform("u_regionSize");
        public static final Uniform screenWidth = new Uniform("u_screenWidth");
    }

    public enum ParticleType {
        Billboard,
        Point
    }

    public static class Setters {
        public static final Setter cameraInvDirection = new C07643();
        public static final Setter cameraPosition = new C07654();
        public static final Setter cameraRight = new C07621();
        public static final Setter cameraUp = new C07632();
        public static final Setter screenWidth = new C07665();
        public static final Setter worldViewTrans = new C07676();

        /* renamed from: com.badlogic.gdx.graphics.g3d.particles.ParticleShader$Setters$1 */
        static class C07621 implements Setter {
            C07621() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return true;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, ParticleShader.TMP_VECTOR3.set(shader.camera.direction).crs(shader.camera.up).nor());
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.particles.ParticleShader$Setters$2 */
        static class C07632 implements Setter {
            C07632() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return true;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, ParticleShader.TMP_VECTOR3.set(shader.camera.up).nor());
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.particles.ParticleShader$Setters$3 */
        static class C07643 implements Setter {
            C07643() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return true;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, ParticleShader.TMP_VECTOR3.set(-shader.camera.direction.f120x, -shader.camera.direction.f121y, -shader.camera.direction.f122z).nor());
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.particles.ParticleShader$Setters$4 */
        static class C07654 implements Setter {
            C07654() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return true;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, shader.camera.position);
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.particles.ParticleShader$Setters$5 */
        static class C07665 implements Setter {
            C07665() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return true;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, (float) Gdx.graphics.getWidth());
            }
        }

        /* renamed from: com.badlogic.gdx.graphics.g3d.particles.ParticleShader$Setters$6 */
        static class C07676 implements Setter {
            final Matrix4 temp = new Matrix4();

            C07676() {
            }

            public boolean isGlobal(BaseShader shader, int inputID) {
                return false;
            }

            public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
                shader.set(inputID, this.temp.set(shader.camera.view).mul(renderable.worldTransform));
            }
        }
    }

    public static String getDefaultVertexShader() {
        if (defaultVertexShader == null) {
            defaultVertexShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/particles/particles.vertex.glsl").readString();
        }
        return defaultVertexShader;
    }

    public static String getDefaultFragmentShader() {
        if (defaultFragmentShader == null) {
            defaultFragmentShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/particles/particles.fragment.glsl").readString();
        }
        return defaultFragmentShader;
    }

    public ParticleShader(Renderable renderable) {
        this(renderable, new Config());
    }

    public ParticleShader(Renderable renderable, Config config) {
        this(renderable, config, createPrefix(renderable, config));
    }

    public ParticleShader(Renderable renderable, Config config, String prefix) {
        this(renderable, config, prefix, config.vertexShader != null ? config.vertexShader : getDefaultVertexShader(), config.fragmentShader != null ? config.fragmentShader : getDefaultFragmentShader());
    }

    public ParticleShader(Renderable renderable, Config config, String prefix, String vertexShader, String fragmentShader) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append(vertexShader);
        String stringBuilder2 = stringBuilder.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(prefix);
        stringBuilder3.append(fragmentShader);
        this(renderable, config, new ShaderProgram(stringBuilder2, stringBuilder3.toString()));
    }

    public ParticleShader(Renderable renderable, Config config, ShaderProgram shaderProgram) {
        this.config = config;
        this.program = shaderProgram;
        this.renderable = renderable;
        this.materialMask = renderable.material.getMask() | optionalAttributes;
        this.vertexMask = renderable.mesh.getVertexAttributes().getMask();
        if (config.ignoreUnimplemented || (implementedFlags & this.materialMask) == this.materialMask) {
            register(DefaultShader$Inputs.viewTrans, DefaultShader$Setters.viewTrans);
            register(DefaultShader$Inputs.projViewTrans, DefaultShader$Setters.projViewTrans);
            register(DefaultShader$Inputs.projTrans, DefaultShader$Setters.projTrans);
            register(Inputs.screenWidth, Setters.screenWidth);
            register(DefaultShader$Inputs.cameraUp, Setters.cameraUp);
            register(Inputs.cameraRight, Setters.cameraRight);
            register(Inputs.cameraInvDirection, Setters.cameraInvDirection);
            register(DefaultShader$Inputs.cameraPosition, Setters.cameraPosition);
            register(DefaultShader$Inputs.diffuseTexture, DefaultShader$Setters.diffuseTexture);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Some attributes not implemented yet (");
        stringBuilder.append(this.materialMask);
        stringBuilder.append(")");
        throw new GdxRuntimeException(stringBuilder.toString());
    }

    public void init() {
        ShaderProgram program = this.program;
        this.program = null;
        init(program, this.renderable);
        this.renderable = null;
    }

    public static String createPrefix(Renderable renderable, Config config) {
        StringBuilder stringBuilder;
        String prefix = "";
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append("#version 120\n");
            prefix = stringBuilder.toString();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append("#version 100\n");
            prefix = stringBuilder.toString();
        }
        if (config.type != ParticleType.Billboard) {
            return prefix;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append("#define billboard\n");
        prefix = stringBuilder.toString();
        if (config.align == AlignMode.Screen) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append("#define screenFacing\n");
            return stringBuilder.toString();
        } else if (config.align != AlignMode.ViewPoint) {
            return prefix;
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append("#define viewPointFacing\n");
            return stringBuilder.toString();
        }
    }

    public boolean canRender(Renderable renderable) {
        return this.materialMask == (renderable.material.getMask() | optionalAttributes) && this.vertexMask == renderable.mesh.getVertexAttributes().getMask();
    }

    public int compareTo(Shader other) {
        if (other == null) {
            return -1;
        }
        return other == this ? 0 : 0;
    }

    public boolean equals(Object obj) {
        return obj instanceof ParticleShader ? equals((ParticleShader) obj) : false;
    }

    public boolean equals(ParticleShader obj) {
        return obj == this;
    }

    public void begin(Camera camera, RenderContext context) {
        super.begin(camera, context);
    }

    public void render(Renderable renderable) {
        if (!renderable.material.has(BlendingAttribute.Type)) {
            this.context.setBlending(false, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        bindMaterial(renderable);
        super.render(renderable);
    }

    public void end() {
        this.currentMaterial = null;
        super.end();
    }

    protected void bindMaterial(Renderable renderable) {
        if (this.currentMaterial != renderable.material) {
            int cullFace = this.config.defaultCullFace == -1 ? GL20.GL_BACK : this.config.defaultCullFace;
            int depthFunc = this.config.defaultDepthFunc == -1 ? 515 : this.config.defaultDepthFunc;
            float depthRangeNear = 0.0f;
            float depthRangeFar = 1.0f;
            boolean depthMask = true;
            this.currentMaterial = renderable.material;
            Iterator i$ = this.currentMaterial.iterator();
            while (i$.hasNext()) {
                Attribute attr = (Attribute) i$.next();
                long t = attr.type;
                if (BlendingAttribute.is(t)) {
                    this.context.setBlending(true, ((BlendingAttribute) attr).sourceFunction, ((BlendingAttribute) attr).destFunction);
                } else if ((t & DepthTestAttribute.Type) == DepthTestAttribute.Type) {
                    DepthTestAttribute dta = (DepthTestAttribute) attr;
                    depthFunc = dta.depthFunc;
                    depthRangeNear = dta.depthRangeNear;
                    depthRangeFar = dta.depthRangeFar;
                    depthMask = dta.depthMask;
                } else if (!this.config.ignoreUnimplemented) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown material attribute: ");
                    stringBuilder.append(attr.toString());
                    throw new GdxRuntimeException(stringBuilder.toString());
                }
            }
            this.context.setCullFace(cullFace);
            this.context.setDepthTest(depthFunc, depthRangeNear, depthRangeFar);
            this.context.setDepthMask(depthMask);
        }
    }

    public void dispose() {
        this.program.dispose();
        super.dispose();
    }

    public int getDefaultCullFace() {
        return this.config.defaultCullFace == -1 ? GL20.GL_BACK : this.config.defaultCullFace;
    }

    public void setDefaultCullFace(int cullFace) {
        this.config.defaultCullFace = cullFace;
    }

    public int getDefaultDepthFunc() {
        return this.config.defaultDepthFunc == -1 ? 515 : this.config.defaultDepthFunc;
    }

    public void setDefaultDepthFunc(int depthFunc) {
        this.config.defaultDepthFunc = depthFunc;
    }
}
