package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DepthShader;
import com.badlogic.gdx.graphics.g3d.shaders.DepthShader.Config;

public class DepthShaderProvider extends BaseShaderProvider {
    public final Config config;

    public DepthShaderProvider(Config config) {
        this.config = config == null ? new Config() : config;
    }

    public DepthShaderProvider(String vertexShader, String fragmentShader) {
        this(new Config(vertexShader, fragmentShader));
    }

    public DepthShaderProvider(FileHandle vertexShader, FileHandle fragmentShader) {
        this(vertexShader.readString(), fragmentShader.readString());
    }

    public DepthShaderProvider() {
        this(null);
    }

    protected Shader createShader(Renderable renderable) {
        return new DepthShader(renderable, this.config);
    }
}
