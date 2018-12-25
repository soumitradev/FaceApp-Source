package com.badlogic.gdx.graphics.g3d.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DirectionalLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.AmbientCubemap;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters.ACubemap;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters.Bones;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Iterator;

public class DefaultShader extends BaseShader {
    @Deprecated
    public static int defaultCullFace = GL20.GL_BACK;
    @Deprecated
    public static int defaultDepthFunc = 515;
    private static String defaultFragmentShader = null;
    private static String defaultVertexShader = null;
    protected static long implementedFlags = ((((BlendingAttribute.Type | TextureAttribute.Diffuse) | ColorAttribute.Diffuse) | ColorAttribute.Specular) | FloatAttribute.Shininess);
    private static final long optionalAttributes = (IntAttribute.CullFace | DepthTestAttribute.Type);
    private static final Attributes tmpAttributes = new Attributes();
    protected final AmbientCubemap ambientCubemap;
    protected final long attributesMask;
    private Camera camera;
    protected final DefaultShader$Config config;
    protected int dirLightsColorOffset;
    protected int dirLightsDirectionOffset;
    protected int dirLightsLoc;
    protected int dirLightsSize;
    protected final DirectionalLight[] directionalLights;
    protected final boolean environmentCubemap;
    protected final boolean lighting;
    private boolean lightsSet;
    private Matrix3 normalMatrix;
    protected final PointLight[] pointLights;
    protected int pointLightsColorOffset;
    protected int pointLightsIntensityOffset;
    protected int pointLightsLoc;
    protected int pointLightsPositionOffset;
    protected int pointLightsSize;
    private Renderable renderable;
    protected final boolean shadowMap;
    private float time;
    private final Vector3 tmpV1;
    public final int u_alphaTest;
    protected final int u_ambientCubemap;
    public final int u_ambientTexture;
    public final int u_ambientUVTransform;
    public final int u_bones;
    public final int u_cameraDirection;
    public final int u_cameraPosition;
    public final int u_cameraUp;
    public final int u_diffuseColor;
    public final int u_diffuseTexture;
    public final int u_diffuseUVTransform;
    protected final int u_dirLights0color;
    protected final int u_dirLights0direction;
    protected final int u_dirLights1color;
    public final int u_emissiveColor;
    public final int u_emissiveTexture;
    public final int u_emissiveUVTransform;
    protected final int u_environmentCubemap;
    protected final int u_fogColor;
    public final int u_normalMatrix;
    public final int u_normalTexture;
    public final int u_normalUVTransform;
    public final int u_opacity;
    protected final int u_pointLights0color;
    protected final int u_pointLights0intensity;
    protected final int u_pointLights0position;
    protected final int u_pointLights1color;
    public final int u_projTrans;
    public final int u_projViewTrans;
    public final int u_projViewWorldTrans;
    public final int u_reflectionColor;
    public final int u_reflectionTexture;
    public final int u_reflectionUVTransform;
    protected final int u_shadowMapProjViewTrans;
    protected final int u_shadowPCFOffset;
    protected final int u_shadowTexture;
    public final int u_shininess;
    public final int u_specularColor;
    public final int u_specularTexture;
    public final int u_specularUVTransform;
    public final int u_time;
    public final int u_viewTrans;
    public final int u_viewWorldTrans;
    public final int u_worldTrans;
    private long vertexMask;

    public static String getDefaultVertexShader() {
        if (defaultVertexShader == null) {
            defaultVertexShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/default.vertex.glsl").readString();
        }
        return defaultVertexShader;
    }

    public static String getDefaultFragmentShader() {
        if (defaultFragmentShader == null) {
            defaultFragmentShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/default.fragment.glsl").readString();
        }
        return defaultFragmentShader;
    }

    public DefaultShader(Renderable renderable) {
        this(renderable, new DefaultShader$Config());
    }

    public DefaultShader(Renderable renderable, DefaultShader$Config config) {
        this(renderable, config, createPrefix(renderable, config));
    }

    public DefaultShader(Renderable renderable, DefaultShader$Config config, String prefix) {
        this(renderable, config, prefix, config.vertexShader != null ? config.vertexShader : getDefaultVertexShader(), config.fragmentShader != null ? config.fragmentShader : getDefaultFragmentShader());
    }

    public DefaultShader(Renderable renderable, DefaultShader$Config config, String prefix, String vertexShader, String fragmentShader) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        stringBuilder.append(vertexShader);
        String stringBuilder2 = stringBuilder.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(prefix);
        stringBuilder3.append(fragmentShader);
        this(renderable, config, new ShaderProgram(stringBuilder2, stringBuilder3.toString()));
    }

    public DefaultShader(Renderable renderable, DefaultShader$Config config, ShaderProgram shaderProgram) {
        boolean z;
        int i;
        int i2;
        this.u_dirLights0color = register(new Uniform("u_dirLights[0].color"));
        this.u_dirLights0direction = register(new Uniform("u_dirLights[0].direction"));
        this.u_dirLights1color = register(new Uniform("u_dirLights[1].color"));
        this.u_pointLights0color = register(new Uniform("u_pointLights[0].color"));
        this.u_pointLights0position = register(new Uniform("u_pointLights[0].position"));
        this.u_pointLights0intensity = register(new Uniform("u_pointLights[0].intensity"));
        this.u_pointLights1color = register(new Uniform("u_pointLights[1].color"));
        this.u_fogColor = register(new Uniform("u_fogColor"));
        this.u_shadowMapProjViewTrans = register(new Uniform("u_shadowMapProjViewTrans"));
        this.u_shadowTexture = register(new Uniform("u_shadowTexture"));
        this.u_shadowPCFOffset = register(new Uniform("u_shadowPCFOffset"));
        this.ambientCubemap = new AmbientCubemap();
        this.normalMatrix = new Matrix3();
        this.tmpV1 = new Vector3();
        Attributes attributes = combineAttributes(renderable);
        this.config = config;
        this.program = shaderProgram;
        boolean z2 = true;
        int i3 = 0;
        this.lighting = renderable.environment != null;
        if (!attributes.has(CubemapAttribute.EnvironmentMap)) {
            if (!this.lighting || !attributes.has(CubemapAttribute.EnvironmentMap)) {
                z = false;
                this.environmentCubemap = z;
                if (this.lighting || renderable.environment.shadowMap == null) {
                    z2 = false;
                }
                this.shadowMap = z2;
                this.renderable = renderable;
                this.attributesMask = attributes.getMask() | optionalAttributes;
                this.vertexMask = renderable.mesh.getVertexAttributes().getMask();
                i = (this.lighting || config.numDirectionalLights <= 0) ? 0 : config.numDirectionalLights;
                this.directionalLights = new DirectionalLight[i];
                for (i = 0; i < this.directionalLights.length; i++) {
                    this.directionalLights[i] = new DirectionalLight();
                }
                i = (this.lighting || config.numPointLights <= 0) ? 0 : config.numPointLights;
                this.pointLights = new PointLight[i];
                while (true) {
                    i = i3;
                    if (i < this.pointLights.length) {
                        break;
                    }
                    this.pointLights[i] = new PointLight();
                    i3 = i + 1;
                }
                if (!config.ignoreUnimplemented || (implementedFlags & this.attributesMask) == this.attributesMask) {
                    this.u_projTrans = register(DefaultShader$Inputs.projTrans, DefaultShader$Setters.projTrans);
                    this.u_viewTrans = register(DefaultShader$Inputs.viewTrans, DefaultShader$Setters.viewTrans);
                    this.u_projViewTrans = register(DefaultShader$Inputs.projViewTrans, DefaultShader$Setters.projViewTrans);
                    this.u_cameraPosition = register(DefaultShader$Inputs.cameraPosition, DefaultShader$Setters.cameraPosition);
                    this.u_cameraDirection = register(DefaultShader$Inputs.cameraDirection, DefaultShader$Setters.cameraDirection);
                    this.u_cameraUp = register(DefaultShader$Inputs.cameraUp, DefaultShader$Setters.cameraUp);
                    this.u_time = register(new Uniform("u_time"));
                    this.u_worldTrans = register(DefaultShader$Inputs.worldTrans, DefaultShader$Setters.worldTrans);
                    this.u_viewWorldTrans = register(DefaultShader$Inputs.viewWorldTrans, DefaultShader$Setters.viewWorldTrans);
                    this.u_projViewWorldTrans = register(DefaultShader$Inputs.projViewWorldTrans, DefaultShader$Setters.projViewWorldTrans);
                    this.u_normalMatrix = register(DefaultShader$Inputs.normalMatrix, DefaultShader$Setters.normalMatrix);
                    i2 = -1;
                    i = (renderable.bones != null || config.numBones <= 0) ? -1 : register(DefaultShader$Inputs.bones, new Bones(config.numBones));
                    this.u_bones = i;
                    this.u_shininess = register(DefaultShader$Inputs.shininess, DefaultShader$Setters.shininess);
                    this.u_opacity = register(DefaultShader$Inputs.opacity);
                    this.u_diffuseColor = register(DefaultShader$Inputs.diffuseColor, DefaultShader$Setters.diffuseColor);
                    this.u_diffuseTexture = register(DefaultShader$Inputs.diffuseTexture, DefaultShader$Setters.diffuseTexture);
                    this.u_diffuseUVTransform = register(DefaultShader$Inputs.diffuseUVTransform, DefaultShader$Setters.diffuseUVTransform);
                    this.u_specularColor = register(DefaultShader$Inputs.specularColor, DefaultShader$Setters.specularColor);
                    this.u_specularTexture = register(DefaultShader$Inputs.specularTexture, DefaultShader$Setters.specularTexture);
                    this.u_specularUVTransform = register(DefaultShader$Inputs.specularUVTransform, DefaultShader$Setters.specularUVTransform);
                    this.u_emissiveColor = register(DefaultShader$Inputs.emissiveColor, DefaultShader$Setters.emissiveColor);
                    this.u_emissiveTexture = register(DefaultShader$Inputs.emissiveTexture, DefaultShader$Setters.emissiveTexture);
                    this.u_emissiveUVTransform = register(DefaultShader$Inputs.emissiveUVTransform, DefaultShader$Setters.emissiveUVTransform);
                    this.u_reflectionColor = register(DefaultShader$Inputs.reflectionColor, DefaultShader$Setters.reflectionColor);
                    this.u_reflectionTexture = register(DefaultShader$Inputs.reflectionTexture, DefaultShader$Setters.reflectionTexture);
                    this.u_reflectionUVTransform = register(DefaultShader$Inputs.reflectionUVTransform, DefaultShader$Setters.reflectionUVTransform);
                    this.u_normalTexture = register(DefaultShader$Inputs.normalTexture, DefaultShader$Setters.normalTexture);
                    this.u_normalUVTransform = register(DefaultShader$Inputs.normalUVTransform, DefaultShader$Setters.normalUVTransform);
                    this.u_ambientTexture = register(DefaultShader$Inputs.ambientTexture, DefaultShader$Setters.ambientTexture);
                    this.u_ambientUVTransform = register(DefaultShader$Inputs.ambientUVTransform, DefaultShader$Setters.ambientUVTransform);
                    this.u_alphaTest = register(DefaultShader$Inputs.alphaTest);
                    this.u_ambientCubemap = this.lighting ? register(DefaultShader$Inputs.ambientCube, new ACubemap(config.numDirectionalLights, config.numPointLights)) : -1;
                    if (this.environmentCubemap) {
                        i2 = register(DefaultShader$Inputs.environmentCubemap, DefaultShader$Setters.environmentCubemap);
                    }
                    this.u_environmentCubemap = i2;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Some attributes not implemented yet (");
                stringBuilder.append(this.attributesMask);
                stringBuilder.append(")");
                throw new GdxRuntimeException(stringBuilder.toString());
            }
        }
        z = true;
        this.environmentCubemap = z;
        if (this.lighting) {
        }
        z2 = false;
        this.shadowMap = z2;
        this.renderable = renderable;
        this.attributesMask = attributes.getMask() | optionalAttributes;
        this.vertexMask = renderable.mesh.getVertexAttributes().getMask();
        if (this.lighting) {
        }
        this.directionalLights = new DirectionalLight[i];
        for (i = 0; i < this.directionalLights.length; i++) {
            this.directionalLights[i] = new DirectionalLight();
        }
        if (this.lighting) {
        }
        this.pointLights = new PointLight[i];
        while (true) {
            i = i3;
            if (i < this.pointLights.length) {
                break;
            }
            this.pointLights[i] = new PointLight();
            i3 = i + 1;
        }
        if (config.ignoreUnimplemented) {
        }
        this.u_projTrans = register(DefaultShader$Inputs.projTrans, DefaultShader$Setters.projTrans);
        this.u_viewTrans = register(DefaultShader$Inputs.viewTrans, DefaultShader$Setters.viewTrans);
        this.u_projViewTrans = register(DefaultShader$Inputs.projViewTrans, DefaultShader$Setters.projViewTrans);
        this.u_cameraPosition = register(DefaultShader$Inputs.cameraPosition, DefaultShader$Setters.cameraPosition);
        this.u_cameraDirection = register(DefaultShader$Inputs.cameraDirection, DefaultShader$Setters.cameraDirection);
        this.u_cameraUp = register(DefaultShader$Inputs.cameraUp, DefaultShader$Setters.cameraUp);
        this.u_time = register(new Uniform("u_time"));
        this.u_worldTrans = register(DefaultShader$Inputs.worldTrans, DefaultShader$Setters.worldTrans);
        this.u_viewWorldTrans = register(DefaultShader$Inputs.viewWorldTrans, DefaultShader$Setters.viewWorldTrans);
        this.u_projViewWorldTrans = register(DefaultShader$Inputs.projViewWorldTrans, DefaultShader$Setters.projViewWorldTrans);
        this.u_normalMatrix = register(DefaultShader$Inputs.normalMatrix, DefaultShader$Setters.normalMatrix);
        i2 = -1;
        if (renderable.bones != null) {
        }
        this.u_bones = i;
        this.u_shininess = register(DefaultShader$Inputs.shininess, DefaultShader$Setters.shininess);
        this.u_opacity = register(DefaultShader$Inputs.opacity);
        this.u_diffuseColor = register(DefaultShader$Inputs.diffuseColor, DefaultShader$Setters.diffuseColor);
        this.u_diffuseTexture = register(DefaultShader$Inputs.diffuseTexture, DefaultShader$Setters.diffuseTexture);
        this.u_diffuseUVTransform = register(DefaultShader$Inputs.diffuseUVTransform, DefaultShader$Setters.diffuseUVTransform);
        this.u_specularColor = register(DefaultShader$Inputs.specularColor, DefaultShader$Setters.specularColor);
        this.u_specularTexture = register(DefaultShader$Inputs.specularTexture, DefaultShader$Setters.specularTexture);
        this.u_specularUVTransform = register(DefaultShader$Inputs.specularUVTransform, DefaultShader$Setters.specularUVTransform);
        this.u_emissiveColor = register(DefaultShader$Inputs.emissiveColor, DefaultShader$Setters.emissiveColor);
        this.u_emissiveTexture = register(DefaultShader$Inputs.emissiveTexture, DefaultShader$Setters.emissiveTexture);
        this.u_emissiveUVTransform = register(DefaultShader$Inputs.emissiveUVTransform, DefaultShader$Setters.emissiveUVTransform);
        this.u_reflectionColor = register(DefaultShader$Inputs.reflectionColor, DefaultShader$Setters.reflectionColor);
        this.u_reflectionTexture = register(DefaultShader$Inputs.reflectionTexture, DefaultShader$Setters.reflectionTexture);
        this.u_reflectionUVTransform = register(DefaultShader$Inputs.reflectionUVTransform, DefaultShader$Setters.reflectionUVTransform);
        this.u_normalTexture = register(DefaultShader$Inputs.normalTexture, DefaultShader$Setters.normalTexture);
        this.u_normalUVTransform = register(DefaultShader$Inputs.normalUVTransform, DefaultShader$Setters.normalUVTransform);
        this.u_ambientTexture = register(DefaultShader$Inputs.ambientTexture, DefaultShader$Setters.ambientTexture);
        this.u_ambientUVTransform = register(DefaultShader$Inputs.ambientUVTransform, DefaultShader$Setters.ambientUVTransform);
        this.u_alphaTest = register(DefaultShader$Inputs.alphaTest);
        if (this.lighting) {
        }
        this.u_ambientCubemap = this.lighting ? register(DefaultShader$Inputs.ambientCube, new ACubemap(config.numDirectionalLights, config.numPointLights)) : -1;
        if (this.environmentCubemap) {
            i2 = register(DefaultShader$Inputs.environmentCubemap, DefaultShader$Setters.environmentCubemap);
        }
        this.u_environmentCubemap = i2;
    }

    public void init() {
        ShaderProgram program = this.program;
        this.program = null;
        init(program, this.renderable);
        this.renderable = null;
        this.dirLightsLoc = loc(this.u_dirLights0color);
        this.dirLightsColorOffset = loc(this.u_dirLights0color) - this.dirLightsLoc;
        this.dirLightsDirectionOffset = loc(this.u_dirLights0direction) - this.dirLightsLoc;
        this.dirLightsSize = loc(this.u_dirLights1color) - this.dirLightsLoc;
        if (this.dirLightsSize < 0) {
            this.dirLightsSize = 0;
        }
        this.pointLightsLoc = loc(this.u_pointLights0color);
        this.pointLightsColorOffset = loc(this.u_pointLights0color) - this.pointLightsLoc;
        this.pointLightsPositionOffset = loc(this.u_pointLights0position) - this.pointLightsLoc;
        this.pointLightsIntensityOffset = has(this.u_pointLights0intensity) ? loc(this.u_pointLights0intensity) - this.pointLightsLoc : -1;
        this.pointLightsSize = loc(this.u_pointLights1color) - this.pointLightsLoc;
        if (this.pointLightsSize < 0) {
            this.pointLightsSize = 0;
        }
    }

    private static final boolean and(long mask, long flag) {
        return (mask & flag) == flag;
    }

    private static final boolean or(long mask, long flag) {
        return (mask & flag) != 0;
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

    public static String createPrefix(Renderable renderable, DefaultShader$Config config) {
        Attributes attributes = combineAttributes(renderable);
        String prefix = "";
        long attributesMask = attributes.getMask();
        long vertexMask = renderable.mesh.getVertexAttributes().getMask();
        if (and(vertexMask, 1)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append("#define positionFlag\n");
            prefix = stringBuilder.toString();
        }
        if (or(vertexMask, 6)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append("#define colorFlag\n");
            prefix = stringBuilder.toString();
        }
        if (and(vertexMask, 256)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append("#define binormalFlag\n");
            prefix = stringBuilder.toString();
        }
        if (and(vertexMask, 128)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append("#define tangentFlag\n");
            prefix = stringBuilder.toString();
        }
        if (and(vertexMask, 8)) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(prefix);
            stringBuilder2.append("#define normalFlag\n");
            prefix = stringBuilder2.toString();
        }
        if ((and(vertexMask, 8) || and(vertexMask, 384)) && renderable.environment != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append("#define lightingFlag\n");
            prefix = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append("#define ambientCubemapFlag\n");
            prefix = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append("#define numDirectionalLights ");
            stringBuilder.append(config.numDirectionalLights);
            stringBuilder.append("\n");
            prefix = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(prefix);
            stringBuilder.append("#define numPointLights ");
            stringBuilder.append(config.numPointLights);
            stringBuilder.append("\n");
            prefix = stringBuilder.toString();
            if (attributes.has(ColorAttribute.Fog)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(prefix);
                stringBuilder.append("#define fogFlag\n");
                prefix = stringBuilder.toString();
            }
            if (renderable.environment.shadowMap != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(prefix);
                stringBuilder.append("#define shadowMapFlag\n");
                prefix = stringBuilder.toString();
            }
            if (attributes.has(CubemapAttribute.EnvironmentMap)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(prefix);
                stringBuilder.append("#define environmentCubemapFlag\n");
                prefix = stringBuilder.toString();
            }
        }
        int n = renderable.mesh.getVertexAttributes().size();
        for (int i = 0; i < n; i++) {
            VertexAttribute attr = renderable.mesh.getVertexAttributes().get(i);
            StringBuilder stringBuilder3;
            if (attr.usage == 64) {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(prefix);
                stringBuilder3.append("#define boneWeight");
                stringBuilder3.append(attr.unit);
                stringBuilder3.append("Flag\n");
                prefix = stringBuilder3.toString();
            } else if (attr.usage == 16) {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(prefix);
                stringBuilder3.append("#define texCoord");
                stringBuilder3.append(attr.unit);
                stringBuilder3.append("Flag\n");
                prefix = stringBuilder3.toString();
            }
        }
        if ((attributesMask & BlendingAttribute.Type) == BlendingAttribute.Type) {
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define blendedFlag\n");
            prefix = stringBuilder4.toString();
        }
        if ((attributesMask & TextureAttribute.Diffuse) == TextureAttribute.Diffuse) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define diffuseTextureFlag\n");
            prefix = stringBuilder4.toString();
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define diffuseTextureCoord texCoord0\n");
            prefix = stringBuilder4.toString();
        }
        if ((attributesMask & TextureAttribute.Specular) == TextureAttribute.Specular) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define specularTextureFlag\n");
            prefix = stringBuilder4.toString();
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define specularTextureCoord texCoord0\n");
            prefix = stringBuilder4.toString();
        }
        if ((attributesMask & TextureAttribute.Normal) == TextureAttribute.Normal) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define normalTextureFlag\n");
            prefix = stringBuilder4.toString();
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define normalTextureCoord texCoord0\n");
            prefix = stringBuilder4.toString();
        }
        if ((attributesMask & TextureAttribute.Emissive) == TextureAttribute.Emissive) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define emissiveTextureFlag\n");
            prefix = stringBuilder4.toString();
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define emissiveTextureCoord texCoord0\n");
            prefix = stringBuilder4.toString();
        }
        if ((attributesMask & TextureAttribute.Reflection) == TextureAttribute.Reflection) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define reflectionTextureFlag\n");
            prefix = stringBuilder4.toString();
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define reflectionTextureCoord texCoord0\n");
            prefix = stringBuilder4.toString();
        }
        if ((attributesMask & TextureAttribute.Ambient) == TextureAttribute.Ambient) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define ambientTextureFlag\n");
            prefix = stringBuilder4.toString();
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define ambientTextureCoord texCoord0\n");
            prefix = stringBuilder4.toString();
        }
        if ((attributesMask & ColorAttribute.Diffuse) == ColorAttribute.Diffuse) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define diffuseColorFlag\n");
            prefix = stringBuilder4.toString();
        }
        if ((attributesMask & ColorAttribute.Specular) == ColorAttribute.Specular) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define specularColorFlag\n");
            prefix = stringBuilder4.toString();
        }
        if ((attributesMask & ColorAttribute.Emissive) == ColorAttribute.Emissive) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define emissiveColorFlag\n");
            prefix = stringBuilder4.toString();
        }
        if ((attributesMask & ColorAttribute.Reflection) == ColorAttribute.Reflection) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define reflectionColorFlag\n");
            prefix = stringBuilder4.toString();
        }
        if ((attributesMask & FloatAttribute.Shininess) == FloatAttribute.Shininess) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define shininessFlag\n");
            prefix = stringBuilder4.toString();
        }
        if ((attributesMask & FloatAttribute.AlphaTest) == FloatAttribute.AlphaTest) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(prefix);
            stringBuilder4.append("#define alphaTestFlag\n");
            prefix = stringBuilder4.toString();
        }
        if (renderable.bones == null || config.numBones <= 0) {
            return prefix;
        }
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(prefix);
        stringBuilder4.append("#define numBones ");
        stringBuilder4.append(config.numBones);
        stringBuilder4.append("\n");
        return stringBuilder4.toString();
    }

    public boolean canRender(Renderable renderable) {
        if (this.attributesMask != (combineAttributes(renderable).getMask() | optionalAttributes) || this.vertexMask != renderable.mesh.getVertexAttributes().getMask()) {
            return false;
        }
        return (renderable.environment != null) == this.lighting;
    }

    public int compareTo(Shader other) {
        if (other == null) {
            return -1;
        }
        return other == this ? 0 : 0;
    }

    public boolean equals(Object obj) {
        return obj instanceof DefaultShader ? equals((DefaultShader) obj) : false;
    }

    public boolean equals(DefaultShader obj) {
        return obj == this;
    }

    public void begin(Camera camera, RenderContext context) {
        super.begin(camera, context);
        for (DirectionalLight dirLight : this.directionalLights) {
            dirLight.set(0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f);
        }
        for (PointLight pointLight : this.pointLights) {
            pointLight.set(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
        this.lightsSet = false;
        if (has(this.u_time)) {
            int i = this.u_time;
            float deltaTime = this.time + Gdx.graphics.getDeltaTime();
            this.time = deltaTime;
            set(i, deltaTime);
        }
    }

    public void render(Renderable renderable, Attributes combinedAttributes) {
        if (!combinedAttributes.has(BlendingAttribute.Type)) {
            this.context.setBlending(false, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        bindMaterial(combinedAttributes);
        if (this.lighting) {
            bindLights(renderable, combinedAttributes);
        }
        super.render(renderable, combinedAttributes);
    }

    public void end() {
        super.end();
    }

    protected void bindMaterial(Attributes attributes) {
        int cullFace = this.config.defaultCullFace == -1 ? defaultCullFace : this.config.defaultCullFace;
        int depthFunc = this.config.defaultDepthFunc == -1 ? defaultDepthFunc : this.config.defaultDepthFunc;
        float depthRangeNear = 0.0f;
        float depthRangeFar = 1.0f;
        boolean depthMask = true;
        Iterator i$ = attributes.iterator();
        while (i$.hasNext()) {
            Attribute attr = (Attribute) i$.next();
            long t = attr.type;
            if (BlendingAttribute.is(t)) {
                this.context.setBlending(true, ((BlendingAttribute) attr).sourceFunction, ((BlendingAttribute) attr).destFunction);
                set(this.u_opacity, ((BlendingAttribute) attr).opacity);
            } else if ((t & IntAttribute.CullFace) == IntAttribute.CullFace) {
                cullFace = ((IntAttribute) attr).value;
            } else if ((t & FloatAttribute.AlphaTest) == FloatAttribute.AlphaTest) {
                set(this.u_alphaTest, ((FloatAttribute) attr).value);
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

    protected void bindLights(Renderable renderable, Attributes attributes) {
        int i;
        DefaultShader defaultShader = this;
        Attributes attributes2 = attributes;
        Environment lights = renderable.environment;
        DirectionalLightsAttribute dla = (DirectionalLightsAttribute) attributes2.get(DirectionalLightsAttribute.class, DirectionalLightsAttribute.Type);
        Array<PointLight> points = null;
        Array<DirectionalLight> dirs = dla == null ? null : dla.lights;
        PointLightsAttribute pla = (PointLightsAttribute) attributes2.get(PointLightsAttribute.class, PointLightsAttribute.Type);
        if (pla != null) {
            points = pla.lights;
        }
        float f = 1.0f;
        float f2 = 0.0f;
        if (defaultShader.dirLightsLoc >= 0) {
            i = 0;
            while (i < defaultShader.directionalLights.length) {
                int idx;
                if (dirs != null) {
                    if (i < dirs.size) {
                        if (defaultShader.lightsSet && defaultShader.directionalLights[i].equals((DirectionalLight) dirs.get(i))) {
                            i++;
                            f = 1.0f;
                        } else {
                            defaultShader.directionalLights[i].set((DirectionalLight) dirs.get(i));
                            idx = defaultShader.dirLightsLoc + (defaultShader.dirLightsSize * i);
                            defaultShader.program.setUniformf(defaultShader.dirLightsColorOffset + idx, defaultShader.directionalLights[i].color.f4r, defaultShader.directionalLights[i].color.f3g, defaultShader.directionalLights[i].color.f2b);
                            defaultShader.program.setUniformf(defaultShader.dirLightsDirectionOffset + idx, defaultShader.directionalLights[i].direction.f120x, defaultShader.directionalLights[i].direction.f121y, defaultShader.directionalLights[i].direction.f122z);
                            if (defaultShader.dirLightsSize <= 0) {
                                break;
                            }
                            i++;
                            f = 1.0f;
                        }
                    }
                }
                if (!defaultShader.lightsSet || defaultShader.directionalLights[i].color.f4r != 0.0f || defaultShader.directionalLights[i].color.f3g != 0.0f || defaultShader.directionalLights[i].color.f2b != 0.0f) {
                    defaultShader.directionalLights[i].color.set(0.0f, 0.0f, 0.0f, f);
                    idx = defaultShader.dirLightsLoc + (defaultShader.dirLightsSize * i);
                    defaultShader.program.setUniformf(defaultShader.dirLightsColorOffset + idx, defaultShader.directionalLights[i].color.f4r, defaultShader.directionalLights[i].color.f3g, defaultShader.directionalLights[i].color.f2b);
                    defaultShader.program.setUniformf(defaultShader.dirLightsDirectionOffset + idx, defaultShader.directionalLights[i].direction.f120x, defaultShader.directionalLights[i].direction.f121y, defaultShader.directionalLights[i].direction.f122z);
                    if (defaultShader.dirLightsSize <= 0) {
                        break;
                    }
                    i++;
                    f = 1.0f;
                } else {
                    i++;
                    f = 1.0f;
                }
            }
        }
        if (defaultShader.pointLightsLoc >= 0) {
            int i2 = 0;
            while (true) {
                i = i2;
                if (i >= defaultShader.pointLights.length) {
                    break;
                }
                int idx2;
                if (points != null) {
                    if (i < points.size) {
                        if (defaultShader.lightsSet && defaultShader.pointLights[i].equals((PointLight) points.get(i))) {
                            i2 = i + 1;
                            f2 = 0.0f;
                        } else {
                            defaultShader.pointLights[i].set((PointLight) points.get(i));
                            idx2 = defaultShader.pointLightsLoc + (defaultShader.pointLightsSize * i);
                            defaultShader.program.setUniformf(defaultShader.pointLightsColorOffset + idx2, defaultShader.pointLights[i].color.f4r * defaultShader.pointLights[i].intensity, defaultShader.pointLights[i].color.f3g * defaultShader.pointLights[i].intensity, defaultShader.pointLights[i].color.f2b * defaultShader.pointLights[i].intensity);
                            defaultShader.program.setUniformf(defaultShader.pointLightsPositionOffset + idx2, defaultShader.pointLights[i].position.f120x, defaultShader.pointLights[i].position.f121y, defaultShader.pointLights[i].position.f122z);
                            if (defaultShader.pointLightsIntensityOffset >= 0) {
                                defaultShader.program.setUniformf(defaultShader.pointLightsIntensityOffset + idx2, defaultShader.pointLights[i].intensity);
                            }
                            if (defaultShader.pointLightsSize <= 0) {
                                break;
                            }
                            i2 = i + 1;
                            f2 = 0.0f;
                        }
                    }
                }
                if (!defaultShader.lightsSet || defaultShader.pointLights[i].intensity != f2) {
                    defaultShader.pointLights[i].intensity = f2;
                    idx2 = defaultShader.pointLightsLoc + (defaultShader.pointLightsSize * i);
                    defaultShader.program.setUniformf(defaultShader.pointLightsColorOffset + idx2, defaultShader.pointLights[i].color.f4r * defaultShader.pointLights[i].intensity, defaultShader.pointLights[i].color.f3g * defaultShader.pointLights[i].intensity, defaultShader.pointLights[i].color.f2b * defaultShader.pointLights[i].intensity);
                    defaultShader.program.setUniformf(defaultShader.pointLightsPositionOffset + idx2, defaultShader.pointLights[i].position.f120x, defaultShader.pointLights[i].position.f121y, defaultShader.pointLights[i].position.f122z);
                    if (defaultShader.pointLightsIntensityOffset >= 0) {
                        defaultShader.program.setUniformf(defaultShader.pointLightsIntensityOffset + idx2, defaultShader.pointLights[i].intensity);
                    }
                    if (defaultShader.pointLightsSize <= 0) {
                        break;
                    }
                    i2 = i + 1;
                    f2 = 0.0f;
                } else {
                    i2 = i + 1;
                    f2 = 0.0f;
                }
            }
        }
        if (attributes2.has(ColorAttribute.Fog)) {
            set(defaultShader.u_fogColor, ((ColorAttribute) attributes2.get(ColorAttribute.Fog)).color);
        }
        if (!(lights == null || lights.shadowMap == null)) {
            set(defaultShader.u_shadowMapProjViewTrans, lights.shadowMap.getProjViewTrans());
            set(defaultShader.u_shadowTexture, lights.shadowMap.getDepthMap());
            set(defaultShader.u_shadowPCFOffset, 1.0f / (((float) lights.shadowMap.getDepthMap().texture.getWidth()) * 2.0f));
        }
        defaultShader.lightsSet = true;
    }

    public void dispose() {
        this.program.dispose();
        super.dispose();
    }

    public int getDefaultCullFace() {
        return this.config.defaultCullFace == -1 ? defaultCullFace : this.config.defaultCullFace;
    }

    public void setDefaultCullFace(int cullFace) {
        this.config.defaultCullFace = cullFace;
    }

    public int getDefaultDepthFunc() {
        return this.config.defaultDepthFunc == -1 ? defaultDepthFunc : this.config.defaultDepthFunc;
    }

    public void setDefaultDepthFunc(int depthFunc) {
        this.config.defaultDepthFunc = depthFunc;
    }
}
