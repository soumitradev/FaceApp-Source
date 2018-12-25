package com.badlogic.gdx.graphics.g3d.decals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;

public class SimpleOrthoGroupStrategy implements GroupStrategy {
    private static final int GROUP_BLEND = 1;
    private static final int GROUP_OPAQUE = 0;
    private Comparator comparator = new Comparator();

    class Comparator implements java.util.Comparator<Decal> {
        Comparator() {
        }

        public int compare(Decal a, Decal b) {
            if (a.getZ() == b.getZ()) {
                return 0;
            }
            return a.getZ() - b.getZ() < 0.0f ? -1 : 1;
        }
    }

    public int decideGroup(Decal decal) {
        return decal.getMaterial().isOpaque() ^ 1;
    }

    public void beforeGroup(int group, Array<Decal> contents) {
        if (group == 1) {
            Sort.instance().sort((Array) contents, this.comparator);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glDepthMask(false);
        }
    }

    public void afterGroup(int group) {
        if (group == 1) {
            Gdx.gl.glDepthMask(true);
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void beforeGroups() {
        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
    }

    public void afterGroups() {
        Gdx.gl.glDisable(GL20.GL_TEXTURE_2D);
    }

    public ShaderProgram getGroupShader(int group) {
        return null;
    }
}
