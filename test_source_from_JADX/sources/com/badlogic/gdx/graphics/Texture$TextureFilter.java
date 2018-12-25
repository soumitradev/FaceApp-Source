package com.badlogic.gdx.graphics;

public enum Texture$TextureFilter {
    Nearest(GL20.GL_NEAREST),
    Linear(GL20.GL_LINEAR),
    MipMap(GL20.GL_LINEAR_MIPMAP_LINEAR),
    MipMapNearestNearest(GL20.GL_NEAREST_MIPMAP_NEAREST),
    MipMapLinearNearest(GL20.GL_LINEAR_MIPMAP_NEAREST),
    MipMapNearestLinear(GL20.GL_NEAREST_MIPMAP_LINEAR),
    MipMapLinearLinear(GL20.GL_LINEAR_MIPMAP_LINEAR);
    
    final int glEnum;

    private Texture$TextureFilter(int glEnum) {
        this.glEnum = glEnum;
    }

    public boolean isMipMap() {
        return (this.glEnum == GL20.GL_NEAREST || this.glEnum == GL20.GL_LINEAR) ? false : true;
    }

    public int getGLEnum() {
        return this.glEnum;
    }
}
