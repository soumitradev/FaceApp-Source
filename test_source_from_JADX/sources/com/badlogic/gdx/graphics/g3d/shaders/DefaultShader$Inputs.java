package com.badlogic.gdx.graphics.g3d.shaders;

import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Uniform;

public class DefaultShader$Inputs {
    public static final Uniform alphaTest = new Uniform("u_alphaTest");
    public static final Uniform ambientCube = new Uniform("u_ambientCubemap");
    public static final Uniform ambientTexture = new Uniform("u_ambientTexture", TextureAttribute.Ambient);
    public static final Uniform ambientUVTransform = new Uniform("u_ambientUVTransform", TextureAttribute.Ambient);
    public static final Uniform bones = new Uniform("u_bones");
    public static final Uniform cameraDirection = new Uniform("u_cameraDirection");
    public static final Uniform cameraPosition = new Uniform("u_cameraPosition");
    public static final Uniform cameraUp = new Uniform("u_cameraUp");
    public static final Uniform diffuseColor = new Uniform("u_diffuseColor", ColorAttribute.Diffuse);
    public static final Uniform diffuseTexture = new Uniform("u_diffuseTexture", TextureAttribute.Diffuse);
    public static final Uniform diffuseUVTransform = new Uniform("u_diffuseUVTransform", TextureAttribute.Diffuse);
    public static final Uniform dirLights = new Uniform("u_dirLights");
    public static final Uniform emissiveColor = new Uniform("u_emissiveColor", ColorAttribute.Emissive);
    public static final Uniform emissiveTexture = new Uniform("u_emissiveTexture", TextureAttribute.Emissive);
    public static final Uniform emissiveUVTransform = new Uniform("u_emissiveUVTransform", TextureAttribute.Emissive);
    public static final Uniform environmentCubemap = new Uniform("u_environmentCubemap");
    public static final Uniform normalMatrix = new Uniform("u_normalMatrix");
    public static final Uniform normalTexture = new Uniform("u_normalTexture", TextureAttribute.Normal);
    public static final Uniform normalUVTransform = new Uniform("u_normalUVTransform", TextureAttribute.Normal);
    public static final Uniform opacity = new Uniform("u_opacity", BlendingAttribute.Type);
    public static final Uniform pointLights = new Uniform("u_pointLights");
    public static final Uniform projTrans = new Uniform("u_projTrans");
    public static final Uniform projViewTrans = new Uniform("u_projViewTrans");
    public static final Uniform projViewWorldTrans = new Uniform("u_projViewWorldTrans");
    public static final Uniform reflectionColor = new Uniform("u_reflectionColor", ColorAttribute.Reflection);
    public static final Uniform reflectionTexture = new Uniform("u_reflectionTexture", TextureAttribute.Reflection);
    public static final Uniform reflectionUVTransform = new Uniform("u_reflectionUVTransform", TextureAttribute.Reflection);
    public static final Uniform shininess = new Uniform("u_shininess", FloatAttribute.Shininess);
    public static final Uniform specularColor = new Uniform("u_specularColor", ColorAttribute.Specular);
    public static final Uniform specularTexture = new Uniform("u_specularTexture", TextureAttribute.Specular);
    public static final Uniform specularUVTransform = new Uniform("u_specularUVTransform", TextureAttribute.Specular);
    public static final Uniform viewTrans = new Uniform("u_viewTrans");
    public static final Uniform viewWorldTrans = new Uniform("u_viewWorldTrans");
    public static final Uniform worldTrans = new Uniform("u_worldTrans");
}
