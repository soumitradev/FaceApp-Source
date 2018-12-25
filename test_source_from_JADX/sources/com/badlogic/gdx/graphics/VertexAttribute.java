package com.badlogic.gdx.graphics;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public final class VertexAttribute {
    public String alias;
    public final boolean normalized;
    public final int numComponents;
    public int offset;
    public final int type;
    public int unit;
    public final int usage;
    private final int usageIndex;

    public VertexAttribute(int usage, int numComponents, String alias) {
        this(usage, numComponents, alias, 0);
    }

    public VertexAttribute(int usage, int numComponents, String alias, int index) {
        this(usage, numComponents, usage == 4 ? GL20.GL_UNSIGNED_BYTE : GL20.GL_FLOAT, usage == 4, alias, index);
    }

    private VertexAttribute(int usage, int numComponents, int type, boolean normalized, String alias) {
        this(usage, numComponents, type, normalized, alias, 0);
    }

    private VertexAttribute(int usage, int numComponents, int type, boolean normalized, String alias, int index) {
        this.usage = usage;
        this.numComponents = numComponents;
        this.type = type;
        this.normalized = normalized;
        this.alias = alias;
        this.unit = index;
        this.usageIndex = Integer.numberOfTrailingZeros(usage);
    }

    public static VertexAttribute Position() {
        return new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE);
    }

    public static VertexAttribute TexCoords(int unit) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ShaderProgram.TEXCOORD_ATTRIBUTE);
        stringBuilder.append(unit);
        return new VertexAttribute(16, 2, stringBuilder.toString(), unit);
    }

    public static VertexAttribute Normal() {
        return new VertexAttribute(8, 3, ShaderProgram.NORMAL_ATTRIBUTE);
    }

    public static VertexAttribute ColorPacked() {
        return new VertexAttribute(4, 4, GL20.GL_UNSIGNED_BYTE, true, ShaderProgram.COLOR_ATTRIBUTE);
    }

    public static VertexAttribute ColorUnpacked() {
        return new VertexAttribute(2, 4, GL20.GL_FLOAT, false, ShaderProgram.COLOR_ATTRIBUTE);
    }

    public static VertexAttribute Tangent() {
        return new VertexAttribute(128, 3, ShaderProgram.TANGENT_ATTRIBUTE);
    }

    public static VertexAttribute Binormal() {
        return new VertexAttribute(256, 3, ShaderProgram.BINORMAL_ATTRIBUTE);
    }

    public static VertexAttribute BoneWeight(int unit) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("a_boneWeight");
        stringBuilder.append(unit);
        return new VertexAttribute(64, 2, stringBuilder.toString(), unit);
    }

    public boolean equals(Object obj) {
        if (obj instanceof VertexAttribute) {
            return equals((VertexAttribute) obj);
        }
        return false;
    }

    public boolean equals(VertexAttribute other) {
        return other != null && this.usage == other.usage && this.numComponents == other.numComponents && this.alias.equals(other.alias) && this.unit == other.unit;
    }

    public int getKey() {
        return (this.usageIndex << 8) + (this.unit & 255);
    }

    public int hashCode() {
        return (((getKey() * 541) + this.numComponents) * 541) + this.alias.hashCode();
    }
}
