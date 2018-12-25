package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public abstract class BaseShaderProvider implements ShaderProvider {
    protected Array<Shader> shaders = new Array();

    protected abstract Shader createShader(Renderable renderable);

    public Shader getShader(Renderable renderable) {
        Shader suggestedShader = renderable.shader;
        if (suggestedShader != null && suggestedShader.canRender(renderable)) {
            return suggestedShader;
        }
        Iterator i$ = this.shaders.iterator();
        while (i$.hasNext()) {
            Shader shader = (Shader) i$.next();
            if (shader.canRender(renderable)) {
                return shader;
            }
        }
        Shader shader2 = createShader(renderable);
        shader2.init();
        this.shaders.add(shader2);
        return shader2;
    }

    public void dispose() {
        Iterator i$ = this.shaders.iterator();
        while (i$.hasNext()) {
            ((Shader) i$.next()).dispose();
        }
        this.shaders.clear();
    }
}
