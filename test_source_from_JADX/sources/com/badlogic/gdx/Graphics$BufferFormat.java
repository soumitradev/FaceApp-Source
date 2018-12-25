package com.badlogic.gdx;

public class Graphics$BufferFormat {
    /* renamed from: a */
    public final int f25a;
    /* renamed from: b */
    public final int f26b;
    public final boolean coverageSampling;
    public final int depth;
    /* renamed from: g */
    public final int f27g;
    /* renamed from: r */
    public final int f28r;
    public final int samples;
    public final int stencil;

    public Graphics$BufferFormat(int r, int g, int b, int a, int depth, int stencil, int samples, boolean coverageSampling) {
        this.f28r = r;
        this.f27g = g;
        this.f26b = b;
        this.f25a = a;
        this.depth = depth;
        this.stencil = stencil;
        this.samples = samples;
        this.coverageSampling = coverageSampling;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("r: ");
        stringBuilder.append(this.f28r);
        stringBuilder.append(", g: ");
        stringBuilder.append(this.f27g);
        stringBuilder.append(", b: ");
        stringBuilder.append(this.f26b);
        stringBuilder.append(", a: ");
        stringBuilder.append(this.f25a);
        stringBuilder.append(", depth: ");
        stringBuilder.append(this.depth);
        stringBuilder.append(", stencil: ");
        stringBuilder.append(this.stencil);
        stringBuilder.append(", num samples: ");
        stringBuilder.append(this.samples);
        stringBuilder.append(", coverage sampling: ");
        stringBuilder.append(this.coverageSampling);
        return stringBuilder.toString();
    }
}
