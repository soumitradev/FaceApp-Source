package com.badlogic.gdx.graphics.g3d.shaders;

public class DefaultShader$Config {
    public int defaultCullFace = -1;
    public int defaultDepthFunc = -1;
    public String fragmentShader = null;
    public boolean ignoreUnimplemented = true;
    public int numBones = 12;
    public int numDirectionalLights = 2;
    public int numPointLights = 5;
    public int numSpotLights = 0;
    public String vertexShader = null;

    public DefaultShader$Config(String vertexShader, String fragmentShader) {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
    }
}
