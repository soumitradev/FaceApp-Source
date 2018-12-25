package com.badlogic.gdx.graphics;

public enum Texture$TextureWrap {
    MirroredRepeat(GL20.GL_MIRRORED_REPEAT),
    ClampToEdge(GL20.GL_CLAMP_TO_EDGE),
    Repeat(GL20.GL_REPEAT);
    
    final int glEnum;

    private Texture$TextureWrap(int glEnum) {
        this.glEnum = glEnum;
    }

    public int getGLEnum() {
        return this.glEnum;
    }
}
