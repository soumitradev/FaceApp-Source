package com.badlogic.gdx.graphics.g3d.shaders;

import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DirectionalLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.AmbientCubemap;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.GlobalSetter;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.LocalSetter;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class DefaultShader$Setters {
    public static final Setter ambientTexture = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, shader.context.textureBinder.bind(((TextureAttribute) combinedAttributes.get(TextureAttribute.Ambient)).textureDescription));
        }
    };
    public static final Setter ambientUVTransform = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            TextureAttribute ta = (TextureAttribute) combinedAttributes.get(TextureAttribute.Ambient);
            shader.set(inputID, ta.offsetU, ta.offsetV, ta.scaleU, ta.scaleV);
        }
    };
    public static final Setter cameraDirection = new C12235();
    public static final Setter cameraPosition = new C12224();
    public static final Setter cameraUp = new C12246();
    public static final Setter diffuseColor = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, ((ColorAttribute) combinedAttributes.get(ColorAttribute.Diffuse)).color);
        }
    };
    public static final Setter diffuseTexture = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, shader.context.textureBinder.bind(((TextureAttribute) combinedAttributes.get(TextureAttribute.Diffuse)).textureDescription));
        }
    };
    public static final Setter diffuseUVTransform = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            TextureAttribute ta = (TextureAttribute) combinedAttributes.get(TextureAttribute.Diffuse);
            shader.set(inputID, ta.offsetU, ta.offsetV, ta.scaleU, ta.scaleV);
        }
    };
    public static final Setter emissiveColor = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, ((ColorAttribute) combinedAttributes.get(ColorAttribute.Emissive)).color);
        }
    };
    public static final Setter emissiveTexture = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, shader.context.textureBinder.bind(((TextureAttribute) combinedAttributes.get(TextureAttribute.Emissive)).textureDescription));
        }
    };
    public static final Setter emissiveUVTransform = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            TextureAttribute ta = (TextureAttribute) combinedAttributes.get(TextureAttribute.Emissive);
            shader.set(inputID, ta.offsetU, ta.offsetV, ta.scaleU, ta.scaleV);
        }
    };
    public static final Setter environmentCubemap = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            if (combinedAttributes.has(CubemapAttribute.EnvironmentMap)) {
                shader.set(inputID, shader.context.textureBinder.bind(((CubemapAttribute) combinedAttributes.get(CubemapAttribute.EnvironmentMap)).textureDescription));
            }
        }
    };
    public static final Setter normalMatrix = new LocalSetter() {
        private final Matrix3 tmpM = new Matrix3();

        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, this.tmpM.set(renderable.worldTransform).inv().transpose());
        }
    };
    public static final Setter normalTexture = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, shader.context.textureBinder.bind(((TextureAttribute) combinedAttributes.get(TextureAttribute.Normal)).textureDescription));
        }
    };
    public static final Setter normalUVTransform = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            TextureAttribute ta = (TextureAttribute) combinedAttributes.get(TextureAttribute.Normal);
            shader.set(inputID, ta.offsetU, ta.offsetV, ta.scaleU, ta.scaleV);
        }
    };
    public static final Setter projTrans = new C12191();
    public static final Setter projViewTrans = new C12213();
    public static final Setter projViewWorldTrans = new C12279();
    public static final Setter reflectionColor = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, ((ColorAttribute) combinedAttributes.get(ColorAttribute.Reflection)).color);
        }
    };
    public static final Setter reflectionTexture = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, shader.context.textureBinder.bind(((TextureAttribute) combinedAttributes.get(TextureAttribute.Reflection)).textureDescription));
        }
    };
    public static final Setter reflectionUVTransform = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            TextureAttribute ta = (TextureAttribute) combinedAttributes.get(TextureAttribute.Reflection);
            shader.set(inputID, ta.offsetU, ta.offsetV, ta.scaleU, ta.scaleV);
        }
    };
    public static final Setter shininess = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, ((FloatAttribute) combinedAttributes.get(FloatAttribute.Shininess)).value);
        }
    };
    public static final Setter specularColor = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, ((ColorAttribute) combinedAttributes.get(ColorAttribute.Specular)).color);
        }
    };
    public static final Setter specularTexture = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, shader.context.textureBinder.bind(((TextureAttribute) combinedAttributes.get(TextureAttribute.Specular)).textureDescription));
        }
    };
    public static final Setter specularUVTransform = new LocalSetter() {
        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            TextureAttribute ta = (TextureAttribute) combinedAttributes.get(TextureAttribute.Specular);
            shader.set(inputID, ta.offsetU, ta.offsetV, ta.scaleU, ta.scaleV);
        }
    };
    public static final Setter viewTrans = new C12202();
    public static final Setter viewWorldTrans = new C12268();
    public static final Setter worldTrans = new C12257();

    /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters$1 */
    static class C12191 extends GlobalSetter {
        C12191() {
        }

        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, shader.camera.projection);
        }
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters$2 */
    static class C12202 extends GlobalSetter {
        C12202() {
        }

        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, shader.camera.view);
        }
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters$3 */
    static class C12213 extends GlobalSetter {
        C12213() {
        }

        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, shader.camera.combined);
        }
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters$4 */
    static class C12224 extends GlobalSetter {
        C12224() {
        }

        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, shader.camera.position.f120x, shader.camera.position.f121y, shader.camera.position.f122z, 1.1881f / (shader.camera.far * shader.camera.far));
        }
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters$5 */
    static class C12235 extends GlobalSetter {
        C12235() {
        }

        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, shader.camera.direction);
        }
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters$6 */
    static class C12246 extends GlobalSetter {
        C12246() {
        }

        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, shader.camera.up);
        }
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters$7 */
    static class C12257 extends LocalSetter {
        C12257() {
        }

        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, renderable.worldTransform);
        }
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters$8 */
    static class C12268 extends LocalSetter {
        final Matrix4 temp = new Matrix4();

        C12268() {
        }

        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, this.temp.set(shader.camera.view).mul(renderable.worldTransform));
        }
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters$9 */
    static class C12279 extends LocalSetter {
        final Matrix4 temp = new Matrix4();

        C12279() {
        }

        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            shader.set(inputID, this.temp.set(shader.camera.combined).mul(renderable.worldTransform));
        }
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters$ACubemap */
    public static class ACubemap extends LocalSetter {
        private static final float[] ones = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
        private static final Vector3 tmpV1 = new Vector3();
        private final AmbientCubemap cacheAmbientCubemap = new AmbientCubemap();
        public final int dirLightsOffset;
        public final int pointLightsOffset;

        public ACubemap(int dirLightsOffset, int pointLightsOffset) {
            this.dirLightsOffset = dirLightsOffset;
            this.pointLightsOffset = pointLightsOffset;
        }

        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            if (renderable.environment == null) {
                shader.program.setUniform3fv(shader.loc(inputID), ones, 0, ones.length);
                return;
            }
            int i;
            renderable.worldTransform.getTranslation(tmpV1);
            if (combinedAttributes.has(ColorAttribute.AmbientLight)) {
                this.cacheAmbientCubemap.set(((ColorAttribute) combinedAttributes.get(ColorAttribute.AmbientLight)).color);
            }
            if (combinedAttributes.has(DirectionalLightsAttribute.Type)) {
                Array<DirectionalLight> lights = ((DirectionalLightsAttribute) combinedAttributes.get(DirectionalLightsAttribute.Type)).lights;
                for (i = this.dirLightsOffset; i < lights.size; i++) {
                    this.cacheAmbientCubemap.add(((DirectionalLight) lights.get(i)).color, ((DirectionalLight) lights.get(i)).direction);
                }
            }
            if (combinedAttributes.has(PointLightsAttribute.Type)) {
                Array<PointLight> lights2 = ((PointLightsAttribute) combinedAttributes.get(PointLightsAttribute.Type)).lights;
                for (i = this.pointLightsOffset; i < lights2.size; i++) {
                    this.cacheAmbientCubemap.add(((PointLight) lights2.get(i)).color, ((PointLight) lights2.get(i)).position, tmpV1, ((PointLight) lights2.get(i)).intensity);
                }
            }
            this.cacheAmbientCubemap.clamp();
            shader.program.setUniform3fv(shader.loc(inputID), this.cacheAmbientCubemap.data, 0, this.cacheAmbientCubemap.data.length);
        }
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters$Bones */
    public static class Bones extends LocalSetter {
        private static final Matrix4 idtMatrix = new Matrix4();
        public final float[] bones;

        public Bones(int numBones) {
            this.bones = new float[(numBones * 16)];
        }

        public void set(BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
            for (int i = 0; i < this.bones.length; i++) {
                float f;
                int idx = i / 16;
                float[] fArr = this.bones;
                if (renderable.bones != null && idx < renderable.bones.length) {
                    if (renderable.bones[idx] != null) {
                        f = renderable.bones[idx].val[i % 16];
                        fArr[i] = f;
                    }
                }
                f = idtMatrix.val[i % 16];
                fArr[i] = f;
            }
            shader.program.setUniformMatrix4fv(shader.loc(inputID), this.bones, 0, this.bones.length);
        }
    }
}
